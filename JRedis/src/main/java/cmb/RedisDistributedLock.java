package cmb;

import redis.clients.jedis.Jedis;

/**
 * Created by Jun on 2018/8/4.
 */
public class RedisDistributedLock {
    private Jedis jedis;
    private Thread deamon;

    public RedisDistributedLock(Jedis jedis) {
        this.jedis = jedis;
    }

    public boolean lock(String key, int time) {
        long threadId = Thread.currentThread().getId();
        String res = jedis.set(key, String.valueOf(threadId), "NX", "EX", time);
        if (res != null) {
            // 加锁成功才开启守护线程
            deamon = new RenewThread(jedis, key);
            //deamon.setDaemon(true);
            deamon.start();
            return true;
        }
        return false;
    }

    // 非原子操作，lua脚本
    public boolean unlock(String key) {
        long threadId = Thread.currentThread().getId();
        if (String.valueOf(threadId).equals(jedis.get(key))) {
            // 释放锁时关闭线程
            if (jedis.del(key) != null) {
                deamon.interrupt();
                return true;
            }
        }
        return false;
    }
}
