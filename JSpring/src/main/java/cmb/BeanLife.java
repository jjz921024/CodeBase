package cmb;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * Bean的生命周期
 *
 *  1. 构造函数
 *  2. 属性注入
 *  3. 若实现aware接口，则执行对应方法
 *
 *  4. postProcessBeforeInitialization(切面)
 *
 *  5. @PostConstruct   (此时成员变量已经注入)
 *  6. InitializatingBean接口的afterPropertiesSet方法
 *  7. init-method方法   (只用注解怎么指定)
 *
 *  8. postProcessAfterInitialization(切面)
 *
 *  9. 使用bean
 *
 *  10. @PreDestroy
 *  11. DisposableBean的destroy方法
 *  12. destroy-method方法    (多例时关闭context不会执行)
 *
 * Created by Jun on 2018/8/20.
 */
@Component
@Scope(scopeName = "singleton")
public class BeanLife implements BeanNameAware, BeanFactoryAware, ApplicationContextAware,
        InitializingBean, DisposableBean, BeanPostProcessor {

    @Value("${jdbc.username: defaultName}")
    private String username;

    public BeanLife() {
        System.out.println("构造函数执行了----> username: " + username);
    }

    public void run() {
        System.out.println("bean is running....");
    }


    /**
     * 在xml的<bean>中指定的init和destroy方法
     *
     *
     */
    public void runInit() {
        System.out.println("Init method....");
    }

    public void runDestroy() {
        System.out.println("Destroy method....");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("@PostConstruct.....----> username: " + username);
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("@PreDestroy.....");
    }

    @Override
    public void setBeanName(String s) {
        //传入参数为bean的id
        System.out.println("Bean name aware: " + s);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        //通过工厂 根据bean的id可以获取bean的相关信息
        System.out.println("Bean factory: " + beanFactory.isSingleton("beanLife"));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("app context: "+ applicationContext.getDisplayName());
    }


    //通过切面
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("post before: "+ beanName);
        return null;
    }
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("post after: "+ beanName);
        return null;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializatingBean's afterPropertiesSet is called");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean's destroy is called");
    }
}
