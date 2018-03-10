package gdut.utils;

import gdut.pojo.BaseCodeEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Jun on 2018/2/11.
 */
public class CodeEnumTypeHandler<E extends Enum<?> & BaseCodeEnum> extends BaseTypeHandler<BaseCodeEnum> {
    private Class<E> type;

    public CodeEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    //设置sql语句的参数时，把java类型转成数据库类型
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BaseCodeEnum parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    // 下面三个方法调用数据库后，根据数据库类型转成对应java类型
    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        return rs.wasNull() ? null : codeOf(code);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        return rs.wasNull() ? null : codeOf(code);
    }

    // 存储过程
    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return cs.wasNull() ? null : codeOf(code);
    }

    private E codeOf(int code){
        try {
            return CodeEnumUtil.codeOf(type, code);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cannot convert " + code + " to " + type.getSimpleName() + " by code value.", ex);
        }
    }
}
