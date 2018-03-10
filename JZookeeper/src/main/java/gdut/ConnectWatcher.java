package gdut;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Jun on 2018/2/20.
 */
public class ConnectWatcher implements Watcher {

    private static final  int SESSION_TIMEOUT = 5000;
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    //子类可访问
    protected ZooKeeper zk;

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            countDownLatch.countDown();
        }
    }

    public void connect(String host) throws IOException, InterruptedException {
        zk = new ZooKeeper(host, SESSION_TIMEOUT, this);
        countDownLatch.await();
    }

    public void close() throws InterruptedException {
        zk.close();
    }


}
