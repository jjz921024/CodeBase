package gdut.example;

import gdut.ZkBaseOpt;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.io.IOException;

/**
 * Created by Jun on 2018/2/20.
 */
public class DataWatcher implements Watcher {

    private ZkBaseOpt zkBaseOpt = new ZkBaseOpt();

    @Override
    public void process(WatchedEvent event) {
        if(event.getType() == Event.EventType.NodeDataChanged){
            try {
                watch();

            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public DataWatcher() throws IOException, InterruptedException {
        zkBaseOpt = new ZkBaseOpt();
        zkBaseOpt.connect("119.23.43.240:2181");
    }

    public void watch() throws KeeperException, InterruptedException {
        String data = zkBaseOpt.read("/test", this);
        System.out.println("Data changed: " + data);

    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        DataWatcher dataWatcher = new DataWatcher();
        dataWatcher.watch();
        Thread.sleep(Long.MAX_VALUE);
    }
}
