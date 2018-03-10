package gdut.pojo;

import java.util.Date;
import java.util.List;

/**
 * Created by Jun on 2018/2/10.
 */
public class Employee {
    private int empNo;
    private Date birthDate;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Date hireDate;


    public enum Gender {
        M, F;
    }

    public Employee() {
    }

    public Employee(Date birthDate, String firstName, String lastName, Date hireDate) {
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hireDate = hireDate;
    }

    public Employee(Date birthDate, String firstName, String lastName, Gender gender, Date hireDate) {
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.hireDate = hireDate;
    }


    public int getEmpNo() {
        return empNo;
    }

    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    @Override
    public String toString() {
        return "EmployeeMapper{" +
                "empNo=" + empNo +
                ", birthDate=" + birthDate +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", hireDate=" + hireDate +
                '}';
    }
}
