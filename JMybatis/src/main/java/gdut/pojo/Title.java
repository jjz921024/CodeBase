package gdut.pojo;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * Created by Jun on 2018/2/12.
 */
public class Title {
    private int empNo;
    private String title;
    private Date fromDate;
    private Date toDate;

    private Employee employee;

    public Title() {
    }

    public Title(int empNo, String title, Date fromDate) {
        this.empNo = empNo;
        this.title = title;
        this.fromDate = fromDate;
    }

    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Title{" +
                "empNo=" + empNo +
                ", title='" + title + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}';
    }
}
