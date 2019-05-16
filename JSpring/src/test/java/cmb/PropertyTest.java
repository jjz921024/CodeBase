package cmb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试如何读取properties文件里的值
 * Created by Jun on 2018/8/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan(basePackages = "cmb")
@ContextConfiguration(classes = AppConfig.class)
public class PropertyTest {

    @Autowired
    private Environment environment;

    @Value("${sourceLocation: /temp/input}")
    private String source;

    @Test
    public void testReadValues() {
        System.out.println("Getting property via Spring Environment :"
                + environment.getProperty("jdbc.driverClassName"));

        System.out.println("Source Location : " + source);

    }

}
