package cmb.limiter;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.io.IOException;

/**
 * Created by Jun on 2018/8/6.
 */
public class WindowsRateLimiter {
    private Jedis jedis;

    public WindowsRateLimiter(Jedis jedis) {
        this.jedis = jedis;
    }

    public boolean isActionAllowed(String userId, String actionId, int period, int maxCount) throws IOException {
        String key = String.format("%s:%s", userId, actionId);
        long now = System.currentTimeMillis();
        Pipeline pipeline = jedis.pipelined();
        pipeline.multi();

        pipeline.zadd(key, now, now+"");  //score为当前时间，value无意义保证唯一即可
        // 移除时间窗口之前的行为记录
        pipeline.zremrangeByScore(key, 0, now - period * 1000);
        // 返回窗口时间内的记录数
        Response<Long> zcard = pipeline.zcard(key);
        // 设置过期时间比period略大，过期则表示在窗口时间内无记录
        pipeline.expire(key, period + 1);

        pipeline.exec();
        pipeline.close();
        return zcard.get() < maxCount;
    }
}
