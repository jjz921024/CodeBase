package gdut;

import com.sun.xml.internal.ws.api.model.CheckedException;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Jun on 2018/2/20.
 */
public class ZkBaseOpt extends ConnectWatcher {
    private static final Charset CHARSET = Charset.forName("UTF-8");

    public void create(String path) throws KeeperException, InterruptedException {
        path = checkPath(path);
        String s = zk.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("Create: " + s);
    }

    public void list(String path) {
        path = checkPath(path);
        try {
            List<String> children = zk.getChildren(path, false);
            if (children.isEmpty()) {
                System.out.println("No children under the path: " + path);
                return;
            }
            for (String c : children) {
                System.out.println(c);
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(String path) {
        path = checkPath(path);
        try {
            List<String> children = zk.getChildren(path, false);
            // 递归删除子节点
            for (String c : children) {
                zk.delete(path+"/"+c, -1);
            }
            zk.delete(path, -1);

        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void write(String path, String value) throws KeeperException, InterruptedException {
        path = checkPath(path);
        Stat stat = zk.exists(path, false);
        if (stat == null) {
            zk.create(path, value.getBytes(CHARSET), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } else {
            zk.setData(path, value.getBytes(CHARSET), -1);
        }
    }

    public String read(String path, Watcher watcher) throws KeeperException, InterruptedException {
        path = checkPath(path);
        byte[] data = zk.getData(path, watcher, null);

        return new String(data, CHARSET);
    }


    private String checkPath(String path) {
        return path.matches("/.+") ? path : "/" + path;
    }
}
