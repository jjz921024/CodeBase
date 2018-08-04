package cmb;

import redis.clients.jedis.Jedis;

/**
 * Created by Jun on 2018/8/4.
 */
public class RenewThread extends Thread {

    private Jedis jedis;
    private String key;
    //private volatile boolean exit;

    public RenewThread(Jedis jedis, String key) {
        this.jedis = jedis;
        this.key = key;
    }


    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                Long ttl = jedis.ttl(key);
                Thread.sleep((ttl-1)*1000);
                jedis.expire(key, 3);
                System.out.println("expire");
            }

        } catch (InterruptedException e) {
            System.out.println("thread exit.");
        }
    }
}
