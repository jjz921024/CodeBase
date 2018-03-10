package gdut.utils;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.Properties;

/**
 * 拦截器
 * 给未按顺序查询的sql加上order字段
 * Created by Jun on 2018/2/18.
 *
 * 拦截StatementHandler类下的  Statement prepare(Connection connection, Integer transactionTimeout)
 */
@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}))
public class OrderInterceptor implements Interceptor {

    //经过过滤后的对象会执行intercept方法
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //获得被拦截的对象 StatementHandler
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        //再判断StatementHandler中的一些属性，确定要拦截的方法
        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY,
                SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());

        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        String id = mappedStatement.getId();
        if (id.matches(".+selectAllDept")) {
            BoundSql boundSql = statementHandler.getBoundSql();
            String sql = boundSql.getSql();
            String orderSql = sql + " order by dept_no";
            metaObject.setValue("delegate.boundSql.sql", orderSql);
        }
        return invocation.proceed();
    }

    //对目标进行过滤，过滤出需要拦截的对象
    //获得需要被拦截的对象的 代理对象
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
