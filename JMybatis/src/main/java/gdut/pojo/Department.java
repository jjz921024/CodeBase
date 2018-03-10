package gdut.pojo;

import java.io.Serializable;

/**
 * Created by Jun on 2018/2/9.
 */
public class Department implements Serializable{
    private String deptNo;
    private String deptName;

    public Department() {
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public String toString() {
        return "Department{" +
                "deptNo='" + deptNo + '\'' +
                ", deptName='" + deptName + '\'' +
                '}';
    }
}
