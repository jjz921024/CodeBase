package gdut;


import gdut.mapper.EmployeeMapper;
import gdut.pojo.Employee;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * Created by Jun on 2018/2/19.
 */
@Component
public class SpringService {

    @Resource
    private EmployeeMapper employeeMapper;

    public void doService() {
        Employee employee = new Employee();
        employee.setFirstName("J");
        gdut.pojo.Employee[] result = employeeMapper.selectByName(employee);
        System.out.println(result[5].toString());
    }


}
