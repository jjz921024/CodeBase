import cmb.queue.Message;
import cmb.queue.RedisDelayQueue;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.embedded.RedisServer;

import java.io.IOException;

/**
 * Created by Jun on 2018/8/6.
 */
public class QueueTest {
    private static RedisServer redisServer;
    private static Jedis jedis;

    @SuppressWarnings("Duplicates")
    @BeforeClass
    public static void init() {
        try {
            redisServer = new RedisServer(6379);
            redisServer.start();
            jedis = new Jedis("localhost", 6379);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void release() {
        redisServer.stop();
        jedis.close();
    }

    @Test
    public void testQueue() {
        RedisDelayQueue<String> queue = new RedisDelayQueue<>(jedis, "delay-queue");

        Thread producer = new Thread() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    queue.delay("msg" + i);
                }
            }
        };

        Thread consumer = new Thread() {
            public void run() {
                queue.loop();
            }
        };

        producer.start();
        consumer.start();
        try {
            producer.join();
            Thread.sleep(5000);
            consumer.interrupt();
            consumer.join();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
