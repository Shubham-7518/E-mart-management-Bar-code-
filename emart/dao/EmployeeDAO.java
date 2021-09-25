/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emart.dao;

import emart.dbutil.DBconnection;
import emart.pojo.EmployeePojo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dell
 */
public class EmployeeDAO {
    public static String getNextEmployeeId()throws SQLException{
        Connection conn = DBconnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select max(empid) from employees");
        rs.next();
        String userID = rs.getString(1).substring(1);
        int uid = Integer.parseInt(userID);
        uid = uid+1;
        return "E"+uid;
    }
    public static boolean addEmployee(EmployeePojo emp)throws SQLException{
        Connection conn = DBconnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("insert into employees values(?,?,?,?)");
        ps.setString(1,emp.getEmpid());
        ps.setString(2,emp.getEmpname());
        ps.setString(3,emp.getEmpjob());
        ps.setDouble(4,emp.getEmpsal());
        int result = ps.executeUpdate();
        return result==1;
    }
    public static List<EmployeePojo> getAllEmployees()throws SQLException{
        Connection conn = DBconnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from employees order by empid");
        ArrayList<EmployeePojo> empList = new ArrayList<>();
        while(rs.next()){
            EmployeePojo emp = new EmployeePojo();
            emp.setEmpid(rs.getString(1));
            emp.setEmpname(rs.getString(2));
            emp.setEmpjob(rs.getString(3));
            emp.setEmpsal(rs.getDouble(4));
            empList.add(emp);
        }
        return empList;
    }
    public static List<String> getAllEmployeesId()throws SQLException{
        Connection conn = DBconnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select empid from employees order by empid");
        ArrayList<String> empIdList = new ArrayList<>();
        while(rs.next()){
            empIdList.add(rs.getString(1));
        }
        return empIdList;
    }
    public static EmployeePojo getEmployeeById(String empId)throws SQLException{
        Connection conn = DBconnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Select * from employees where empid=?");
        ps.setString(1,empId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        EmployeePojo emp = new EmployeePojo();
        emp.setEmpname(rs.getString(2));
        emp.setEmpjob(rs.getString(3));
        emp.setEmpsal(rs.getDouble(4));
        return emp;
    }
    public static boolean updateEmployee(EmployeePojo emp)throws SQLException{
        Connection conn = DBconnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("update employees set empname=?,job=?,salary=? where empid=?");
        ps.setString(1,emp.getEmpname());
        ps.setString(2,emp.getEmpjob());
        ps.setDouble(3,emp.getEmpsal());
        ps.setString(4,emp.getEmpid());
        int result = ps.executeUpdate();
        if(result == 0){
            return false;
        }else{
            boolean res = UserDAO.isUserPresent(emp.getEmpid());
            if(!res)
                return true;
            ps=conn.prepareStatement("Update users set username=?,usertype=? where empid=?");
            ps.setString(1, emp.getEmpname());
            ps.setString(2, emp.getEmpjob());
            ps.setString(3,emp.getEmpid());
            int y=ps.executeUpdate();
            return y==1;
        }
    }
    public static boolean deleteEmployeeById(String empid) throws SQLException{
        Connection conn = DBconnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("delete from employees where empid=?");
        ps.setString(1,empid);
        int res = ps.executeUpdate();
        return res==1;
    }
}
