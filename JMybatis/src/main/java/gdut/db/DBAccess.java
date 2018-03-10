package gdut.db;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;


/**
 * Created by Jun on 2018/2/9.
 */
public class DBAccess {
    private volatile static SqlSessionFactory sqlSessionFactory;

    private SqlSessionFactory getFactory() throws IOException {
        // Factory对象应为单例
        if (sqlSessionFactory == null) {
            synchronized (DBAccess.class) {
                if (sqlSessionFactory == null) {
                    Reader reader = Resources.getResourceAsReader("Configuration.xml");
                    // Builder对象创建Factory后就应该被回收，应为方法作用域/局部变量
                    sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
                }
            }
        }
        return sqlSessionFactory;
    }

    // 每个线程或每个http请求拥有一个SqlSession，线程不安全
    public SqlSession getSqlSession() throws IOException {
        if (sqlSessionFactory == null) {
            getFactory();
        }
        return sqlSessionFactory.openSession();

    }

}
