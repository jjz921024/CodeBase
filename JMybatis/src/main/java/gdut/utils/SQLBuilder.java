package gdut.utils;

import org.apache.ibatis.jdbc.SQL;

/**
 * Created by Jun on 2018/2/11.
 */
public class SQLBuilder {

    private String createSql() {
        return new SQL().SELECT("id","name")
                .FROM("table1")
                .INNER_JOIN("table2 on id = name")
                .toString();
    }

    public static void main(String[] args) {
        SQLBuilder sqlBuilder = new SQLBuilder();
        System.out.println(sqlBuilder.createSql());
    }
}
