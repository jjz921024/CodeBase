package cmb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by Jun on 2018/8/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan(basePackages = "cmb")
@ContextConfiguration(locations = {"classpath:spring-bean.xml"})
public class BeanLifeTest {

    /**
     * 自动注入上下文，或者继承AbstractJUnit4SpringContextTests类
     */
    @Autowired
    AbstractApplicationContext context;

    @Resource
    private BeanLife beanLife;

    @Test
    public void test() {
        beanLife.run();
        context.close();
    }

}
