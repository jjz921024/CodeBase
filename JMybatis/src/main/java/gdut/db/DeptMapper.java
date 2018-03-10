package gdut.db;

import org.apache.ibatis.annotations.Select;
import gdut.pojo.Department;

import java.util.List;

/**
 * 接口的全类名 --> 配置文件namespace
 * 方法名       --> sql语句的id
 * 方法参数     --> sql的参数
 * 返回值       --> 查询结果
 * Created by Jun on 2018/2/9.
 */
public interface DeptMapper {

    List<Department> selectAllDept();


    /**
     *   在mybatis配置sqlSessionFactory时可以配置
         <bean id="scannerMapper" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
         <property name="basePackage" value="gdut"/>
         <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
         </bean>
        或者
        在mybatis主配置文件中
        <mapper class="gdut.db.DeptMapper"/>

     //todo: 查不出数据
     */
    @Select("SELECT * FROM departments WHERE dept_no = #{dept_no}")
    Department getDeptByNo(String dept_no);
}
