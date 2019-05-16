package cmb.route;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by Jun on 2018/8/20.
 */
@Component
public class RoutingBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 检查RoutingInjected注解
     * 对有注解的类生成动态代理类
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(RoutingInjected.class)) {
                if (!f.getType().isInterface()) {
                    throw new BeanCreationException("RoutingInjected field must be declared as an interface:" + f.getName()
                            + " @Class " + clazz.getName());
                }
                try {
                    this.handleRoutingInjected(f, bean, f.getType());
                } catch (IllegalAccessException e) {
                    throw new BeanCreationException("Exception thrown when handleAutowiredRouting", e);
                }
            }
        }
        return bean;
    }

    /**
     *
     */
    private void handleRoutingInjected(Field field, Object bean, Class type) throws IllegalAccessException {
        // 在上下文中获取与被代理类相同类型的bean  type是HelloService
        Map<String, Object> candidates = this.applicationContext.getBeansOfType(type);
        field.setAccessible(true);
        if (candidates.size() == 1) {
            // 将指定bean对象上的field对象表示的成员变量赋新值
            field.set(bean, candidates.values().iterator().next());
        } else if (candidates.size() == 2) {
            Object proxy = RoutingBeanProxyFactory.createProxy(type, candidates);
            field.set(bean, proxy);
        } else {
            throw new IllegalArgumentException("Find more than 2 beans for type: " + type);
        }
    }
}
