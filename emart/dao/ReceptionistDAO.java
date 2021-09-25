/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emart.dao;

import emart.dbutil.DBconnection;
import emart.pojo.ReceptionistPojo;
import emart.pojo.UserPojo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceptionistDAO {
    public static Map<String,String> getUnregisteredReceptionist()throws SQLException{
        Connection conn = DBconnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select empid,empname from employees where job='Receptionist' and empid not in (select empid from users where usertype='Receptionist')");
        HashMap<String,String> receptionistMap = new HashMap<>();
        while(rs.next()){
            String id=rs.getString(1);
            String name = rs.getString(2);
            receptionistMap.put(id,name);
        }
        return receptionistMap;
    }
    public static boolean addReceptionist(UserPojo user)throws SQLException{
         Connection conn = DBconnection.getConnection();
         PreparedStatement ps = conn.prepareStatement("Insert into users values(?,?,?,?,?)");
         ps.setString(1,user.getUserid());
         ps.setString(2, user.getEmpid());
         ps.setString(3,user.getPassword());
         ps.setString(4,user.getUsertype());
         ps.setString(5,user.getUsername());
         int res=ps.executeUpdate();
         return res==1;
    }
    public static List<ReceptionistPojo> getAllReceptionist()throws SQLException{
        Connection conn = DBconnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select users.empid,users.userid,empname,salary,job from users,employees where usertype='Receptionist' and users.empid=employees.empid order by users.empid");
        
        ArrayList<ReceptionistPojo> receptionistList = new ArrayList<>();
        while(rs.next()){
            ReceptionistPojo resp = new ReceptionistPojo();
            resp.setEmpid(rs.getString(1));
            resp.setUserid(rs.getString(2));
            resp.setJob(rs.getString(5));
            resp.setUsername(rs.getString(3));
            resp.setSalary(rs.getString(4));
            receptionistList.add(resp);
        }
        return receptionistList;
    }
    public static List<String> getAllRegisterReceptionistId()throws SQLException{
        Connection conn = DBconnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from users where userType='Receptionist'");
        ArrayList<String> recepList = new ArrayList<>();
        while(rs.next()){
            recepList.add(rs.getString(1));
        }
        return recepList;
    }
    public static boolean removeEmployeeById(String userid)throws SQLException{
        Connection conn = DBconnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("delete from users where userid=?");
        ps.setString(1, userid);
        int res = ps.executeUpdate();
        return res==1;
    }
    public static Map<String,String> getAllRegisteredRecetionist()throws SQLException{
        Connection conn = DBconnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select userid,username from users where usertype='Receptionist'");
        HashMap<String,String> receptionistMap = new HashMap<>();
        while(rs.next()){
            String id=rs.getString(1);
            String name = rs.getString(2);
            receptionistMap.put(id,name);
        }
        return receptionistMap;
    }
    public static boolean updateReceptionist(String userid,String password)throws SQLException{
        Connection conn = DBconnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("update users set password=? where userid=?");
        ps.setString(1, password);
        ps.setString(2, userid);
        return ps.executeUpdate() == 1;
    }
}
