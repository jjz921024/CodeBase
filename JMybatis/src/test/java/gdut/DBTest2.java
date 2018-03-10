package gdut;

import gdut.db.DBAccess;
import gdut.pojo.Title;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import gdut.pojo.Employee;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 测试主键生成，多条插入，动态SQL，枚举类型处理
 * Created by Jun on 2018/2/10.
 */
public class DBTest2 {
    private static DBAccess dbAccess = new DBAccess();

    @Test
    public void testInsertKey() {
        SqlSession sqlSession = null;
        try {
            sqlSession = dbAccess.getSqlSession();
            Employee employee = new Employee(new Date(100L), "J", "JZ", Employee.Gender.M, new Date());
            System.out.println("The key before insert: " + employee.getEmpNo());
            sqlSession.insert("insertEmp2", employee);
            sqlSession.commit();
            System.out.println("The key after insert: " + employee.getEmpNo());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null)
                sqlSession.close();
        }
    }

    @Test
    public void testInsertMult() {
        SqlSession sqlSession = null;
        try {
            sqlSession = dbAccess.getSqlSession();
            Employee employee1 = new Employee(new Date(100L), "J", "JZ", new Date());
            Employee employee2 = new Employee(new Date(100L), "J1", "JZ1", new Date());
            List<Employee> employees = Arrays.asList(employee1, employee2);
            int insertMult = sqlSession.insert("insertMult", employees);
            sqlSession.commit();
            System.out.println("affect: " + insertMult);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null)
                sqlSession.close();
        }
    }

    @Test
    public void testSelectDynamic() {
        SqlSession sqlSession = null;
        try {
            sqlSession = dbAccess.getSqlSession();
            Employee employee = new Employee();
            List<Employee> selectByName = sqlSession.selectList("selectByOr", employee);
            for (Employee e : selectByName) {
                System.out.println(e.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null)
                sqlSession.close();
        }
    }

    @Test
    public void testUpdate() {
        SqlSession sqlSession = null;
        try {
            sqlSession = dbAccess.getSqlSession();
            Employee employee = new Employee();
            employee.setEmpNo(500008);
            employee.setFirstName("JJ");
            int updateEmp = sqlSession.update("updateEmp", employee);
            sqlSession.commit();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null)
                sqlSession.close();
        }
    }

    @Test
    public void testSelectLike() {
        SqlSession sqlSession = null;
        try {
            sqlSession = dbAccess.getSqlSession();
            Employee employee = new Employee();
            employee.setFirstName("ar");
            List<Employee> selectEmpLike = sqlSession.selectList("selectEmpLike", employee);
            for (Employee e : selectEmpLike) {
                System.out.println(e.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null)
                sqlSession.close();
        }
    }

    @Test
    public void testJoin() {
        try (SqlSession sqlSession = dbAccess. getSqlSession()){
            Employee employee = new Employee();
            employee.setEmpNo(10009);
            List<Employee> selectEmpTitleList = sqlSession.selectList("selectEmpTitleList", employee);
            List<Title> titleList = selectEmpTitleList.get(0).getTitleList();
            System.out.println("The " + employee.getEmpNo() + " employee's title: ");
            for (Title t : titleList) {
                System.out.println("\t" +t.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //多对一
    @Test
    public void testJoin2() {
        try (SqlSession sqlSession = dbAccess. getSqlSession()){
            Title title = new Title(10009, null, null);
            Title selectTitleEmp = sqlSession.selectOne("selectTitleEmp", title);
            System.out.println(selectTitleEmp.getEmployee().toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
