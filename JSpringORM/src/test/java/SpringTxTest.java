import gdut.pojo.Employee;
import gdut.Service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Jun on 2018/2/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:AppContextTx.xml")
public class SpringTxTest {

    // 注入代理对象
    @Autowired(required = true)
    private Service serviceProxy;

    @Test
    public void testTransaction() {
        Employee employee1 = new Employee(new Date(), "springtx", "tx", new Date());
        Employee employee2 = new Employee(new Date(), "springtx2", "tx2", new Date());
        serviceProxy.doTxProxyService(employee1, employee2);
    }



    // 不是代理对象，但应用了增强
    @Resource(name = "service")
    private Service service;

    @Test
    public void testAspectJ() {
        Employee employee1 = new Employee(new Date(), "springAspectJ", "AspectJ", new Date());
        Employee employee2 = new Employee(new Date(), "springAspectJ2", "AspectJ2", new Date());
        service.doTxAOPService(employee1, employee2);
    }
}
