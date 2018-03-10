package gdut;

import gdut.db.DBAccess;
import gdut.db.DeptMapper;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import gdut.pojo.Department;

import java.io.IOException;
import java.util.*;

/**
 * 简单的增删改查操作  about the class Department
 * Created by Jun on 2018/2/9.
 */
public class DBTest {
    private static DBAccess dbAccess = new DBAccess();

    @Test
    public void testSelect1() {
        SqlSession sqlSession = null;
        try {
            sqlSession = dbAccess.getSqlSession();
            // 限制范围
            List<Department> depts = sqlSession.selectList("selectAllDept", null, new RowBounds(1,5));
            for (Department d : depts) {
                System.out.println(d.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null)
                sqlSession.close();
        }
    }

    /**
     * 测试结果返回给HashMap
     */
    @Test
    public void testSelect2() {
        SqlSession sqlSession = null;
        try {
            sqlSession = dbAccess.getSqlSession();
            /*HashMap result = (HashMap)sqlSession.selectOne("selectOneDeptMap", "d005");
            Set<Map.Entry<String,String>> set = result.entrySet();
            Iterator<Map.Entry<String,String>> iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                System.out.println("key:" + next.getKey() + " value:" + next.getValue());
            }*/

            Department department = new Department();
            department.setDeptNo("t001");
            HashMap result = (HashMap)sqlSession.selectOne("selectOneDeptMapByDept", department);
            if (result != null) {
                Set<Map.Entry<String,String>> set = result.entrySet();
                Iterator<Map.Entry<String,String>> iterator = set.iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> next = iterator.next();
                    System.out.println("key:" + next.getKey() + " value:" + next.getValue());
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null)
                sqlSession.close();
        }
    }

    @Test
    public void testInsert() {
        SqlSession sqlSession = null;
        try {
            sqlSession = dbAccess.getSqlSession();
            Department department = new Department();
            department.setDeptNo("t002");
            department.setDeptName("Tech1");
            sqlSession.insert("insertDept", department);
            sqlSession.commit();


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
            Department department = new Department();
            department.setDeptNo("t001");
            department.setDeptName("Tech3");
            sqlSession.update("updateDeptName", department);
            sqlSession.commit();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null)
                sqlSession.close();
        }
    }

    @Test
    public void testDelete() {
        SqlSession sqlSession = null;
        try {
            sqlSession = dbAccess.getSqlSession();
            sqlSession.delete("deleteDeptByNo", "t001");
            sqlSession.commit();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null)
                sqlSession.close();
        }
    }


    /**
     * 通过接口实现与配置文件相对应，实现查询
     */
    @Test
    public void testMapper() {
        SqlSession sqlSession = null;
        try {
            sqlSession = dbAccess.getSqlSession();
            DeptMapper deptMapper = sqlSession.getMapper(DeptMapper.class);
            List<Department> depts = deptMapper.selectAllDept();
            for (Department d : depts) {
                System.out.println(d.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null)
                sqlSession.close();
        }
    }

    @Test public void testMapperAuto() {
        SqlSession sqlSession = null;
        try {
            sqlSession = dbAccess.getSqlSession();
            DeptMapper mapper = sqlSession.getMapper(DeptMapper.class);
            Department dept = mapper.getDeptByNo("d002");
            dept.toString();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null)
                sqlSession.close();
        }
    }

}
