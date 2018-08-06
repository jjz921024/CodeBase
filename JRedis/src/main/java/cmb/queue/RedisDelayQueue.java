package cmb.queue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.UUID;

/**
 * Redis实现延时队列
 * Created by Jun on 2018/8/6.
 */
public class RedisDelayQueue<T> {
    private Jedis jedis;
    private String queueName;

    public RedisDelayQueue(Jedis jedis, String queueName) {
        this.jedis = jedis;
        this.queueName = queueName;
    }

    // 反序列化对象中存在范型时
    private Type MessageType = new TypeReference<Message<T>>() {}.getType();

    public void delay(T msg) {
        Message<T> tMessage = new Message<>();
        tMessage.id = UUID.randomUUID().toString(); //分配唯一id
        tMessage.msg = msg;
        String s = JSON.toJSONString(tMessage);
        jedis.zadd(queueName, (double) System.currentTimeMillis(), s); //以当前时间为score
    }

    public void loop() {
        while (!Thread.interrupted()) {
            Set<String> values = jedis.zrangeByScore(queueName, 0.0, (double) System.currentTimeMillis(), 0, 1);
            if (values.isEmpty()) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    break;
                }
                continue;
            }
            String value = values.iterator().next();
            if (jedis.zrem(queueName, value) > 0) {
                // handle
                System.out.println(JSON.parseObject(value, MessageType).toString());
            }
        }
    }
}
