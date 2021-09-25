/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emart.pojo;

/**
 *
 * @author dell
 */
public class EmployeePojo {
    private String empid;
    private String empname;
    private String empjob;
    private double empsal;

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getEmpjob() {
        return empjob;
    }

    public void setEmpjob(String empjob) {
        this.empjob = empjob;
    }

    public double getEmpsal() {
        return empsal;
    }

    public void setEmpsal(double empsal) {
        this.empsal = empsal;
    }

    @Override
    public String toString() {
        return "EmployeePojo{" + "empid=" + empid + ", empname=" + empname + ", empjob=" + empjob + ", empsal=" + empsal + '}';
    }
    
}
