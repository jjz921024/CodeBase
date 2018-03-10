package gdut.mapper;

import gdut.pojo.Employee;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Jun on 2018/2/20.
 */
public interface EmployeeMapper {
    Employee[] selectByName(Employee employee);
}
