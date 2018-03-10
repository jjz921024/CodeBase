import gdut.EmpDao;
import gdut.pojo.Employee;
import gdut.Service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Jun on 2018/2/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:ApplicationContext.xml")
public class SpringJDBCTest {

    @Resource
    private EmpDao empDao;
    @Resource
    private Service service;

    @Test
    public void testTemplate() {
        Employee employee = new Employee(new Date(), "Spring", "jdbc", new Date());
        System.out.println(empDao.createEmp(employee));
    }

    @Test
    public void testTransaction() {
        Employee employee1 = new Employee(new Date(), "spring", "tx", new Date());
        Employee employee2 = new Employee(new Date(), "spring22", "tx22", new Date());
        service.doService(employee1, employee2);
    }
}
