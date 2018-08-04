import cmb.RedisDistributedLock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.embedded.RedisServer;

import java.io.IOException;

/**
 * Created by Jun on 2018/8/4.
 */
public class LockTest {

    private RedisServer redisServer;
    private Jedis jedis;

    @Before
    public void init() {
        try {
            redisServer = new RedisServer(6379);
            redisServer.start();
            jedis = new Jedis("localhost", 6379);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void release() {
        redisServer.stop();
        jedis.close();
    }

    @Test
    public void testLock() throws InterruptedException {
        RedisDistributedLock lock = new RedisDistributedLock(jedis);
        System.out.println(lock.lock("lock", 3));
        Thread.sleep(3000);
        System.out.println(lock.lock("lock", 3));
        Thread.sleep(3000);
        System.out.println(lock.unlock("lock"));
    }
}
