package cmb.route;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Jun on 2018/8/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan(basePackages = "cmb.route")
@ContextConfiguration(classes = RouteTest.class)
public class RouteTest {

    @RoutingInjected
    private HelloService helloService;

    @Test
    public void sayHello(){
        helloService.sayHello();
    }

    @Test
    public void sayHi(){
        helloService.sayHi();
    }
}
