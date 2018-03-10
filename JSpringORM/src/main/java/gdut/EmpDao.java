package gdut;

import gdut.pojo.Employee;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Spring-JDBC demo
 * Created by Jun on 2018/2/13.
 */
public class EmpDao extends JdbcDaoSupport {

    public int createEmp(Employee employee) {
        String sql = "insert into employees (birth_date, first_name, last_name, hire_date) VALUES (?, ?, ?, ?)";
        return this.getJdbcTemplate().update(sql, employee.getBirthDate(), employee.getFirstName(), employee.getLastName(), employee.getHireDate());
    }
}
