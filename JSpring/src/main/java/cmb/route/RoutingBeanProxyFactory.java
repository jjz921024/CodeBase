package cmb.route;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.util.StringUtils;


import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Jun on 2018/8/20.
 */
public class RoutingBeanProxyFactory {
    //创建动态代理对象
    public static Object createProxy(Class targetClass, Map<String, Object> beans) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(targetClass);
        proxyFactory.addAdvice(new VersionRoutingMethodInterceptor(targetClass, beans));
        return proxyFactory.getProxy();
    }

    // 对每个方法进行aop，路由配置支持到方法级别
    static class VersionRoutingMethodInterceptor implements MethodInterceptor {
        private String classSwitch;
        private Object beanOfSwitchOn;
        private Object beanOfSwitchOff;

        public VersionRoutingMethodInterceptor(Class targetClass, Map<String, Object> beans) {
            String interfaceName = StringUtils.uncapitalize(targetClass.getSimpleName());
            if(targetClass.isAnnotationPresent(RoutingSwitch.class)){
                this.classSwitch =((RoutingSwitch)targetClass.getAnnotation(RoutingSwitch.class)).value();
            }
            // 仅支持2类，AB测试    设true为V2版本
            this.beanOfSwitchOn = beans.get(this.buildBeanName(interfaceName, true));
            this.beanOfSwitchOff = beans.get(this.buildBeanName(interfaceName, false));
        }

        private String buildBeanName(String interfaceName, boolean isSwitchOn) {
            return interfaceName + "Impl" + (isSwitchOn ? "V2" : "V1");
        }

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            Method method = invocation.getMethod();
            String switchName = this.classSwitch;
            if (method.isAnnotationPresent(RoutingSwitch.class)) {
                switchName = method.getAnnotation(RoutingSwitch.class).value();
            }
            if (StringUtils.isEmpty(switchName)) {
                throw new IllegalStateException("RoutingSwitch's value is blank, method:" + method.getName());
            }
            return invocation.getMethod().invoke(getTargetBean(switchName), invocation.getArguments());
        }

        // 根据注释的内容返回不同的bean
        public Object getTargetBean(String switchName) {
            boolean switchOn;
            if ("A".equals(switchName)) {
                switchOn = false;
            } else {
                switchOn = true;
            }
            return switchOn ? beanOfSwitchOn : beanOfSwitchOff;
        }
    }
}
