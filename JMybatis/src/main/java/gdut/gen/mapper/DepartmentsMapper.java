package gdut.gen.mapper;

import gdut.gen.pojo.Departments;
import gdut.gen.pojo.DepartmentsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DepartmentsMapper {
    long countByExample(DepartmentsExample example);

    int deleteByExample(DepartmentsExample example);

    int insert(Departments record);

    int insertSelective(Departments record);

    List<Departments> selectByExample(DepartmentsExample example);

    int updateByExampleSelective(@Param("record") Departments record, @Param("example") DepartmentsExample example);

    int updateByExample(@Param("record") Departments record, @Param("example") DepartmentsExample example);
}