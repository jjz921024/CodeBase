import gdut.ConnectWatcher;
import gdut.ZkBaseOpt;
import org.apache.zookeeper.KeeperException;
import org.junit.*;

import java.io.IOException;

/**
 * Created by Jun on 2018/2/20.
 */
public class BaseOptTest {
    private ZkBaseOpt zkClient;
    private String testPath = "/test";

    @Before
    public void init() throws IOException, InterruptedException {
        zkClient = new ZkBaseOpt();
        zkClient.connect("119.23.43.240:2181");
    }

    @After
    public void release() throws InterruptedException {
        zkClient.close();
    }


    @Test
    public void testOpt() throws KeeperException, InterruptedException {
        for (int i=0; i<10; i++) {
            zkClient.create(testPath + "/node" + String.valueOf(i));
        }
        zkClient.list(testPath);
        zkClient.delete(testPath);
    }




}
