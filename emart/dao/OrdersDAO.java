/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emart.dao;

import emart.dbutil.DBconnection;
import emart.pojo.ProductsPojo;
import emart.pojo.UserProfile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrdersDAO {
    public static String generateNextOrderId() throws SQLException{
        Connection conn = DBconnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select max(order_id) from orders");
        String id = "O101";
        if(rs.next()){
            String userID = rs.getString(1).substring(1);
            int oid = Integer.parseInt(userID);
            oid = oid+1;
            id = "O"+oid;
        }
        return id;
    }
    public static boolean addOrders(List<ProductsPojo> productList,String orderID) throws SQLException{
        Connection conn = DBconnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("insert into orders values(?,?,?,?,?)");
        int result = 0;
        for(ProductsPojo product:productList){
            ps.setString(1, orderID);
            ps.setString(2, product.getProductID());
            ps.setInt(3,product.getQuantity());
            ps.setString(4,UserProfile.getUserid());
            ps.setDouble(5,product.getTotal());
            result+=ps.executeUpdate();
        }
        return result == productList.size();
    }
    public static List<ProductsPojo> getOrdersById(String orderId,String userId) throws SQLException{
        Connection conn = DBconnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select products.p_id,products.p_name,products.p_price,products.our_price,products.p_companyname,orders.quantity,products.p_tax,orders.grand_total from products inner join orders on products.p_id = orders.p_id where order_id=? and userid=?");
        ps.setString(1, orderId);
        ps.setString(2, userId);
        ResultSet rs = ps.executeQuery();
        List<ProductsPojo> productList = new ArrayList<>();
        while(rs.next()){
            ProductsPojo product = new ProductsPojo();
            product.setProductID(rs.getString(1));
            product.setProductName(rs.getString(2));
            product.setProductPrice(rs.getDouble(3));
            product.setOurPrice(rs.getDouble(4));
            product.setProductCompany(rs.getString(5));
            product.setQuantity(rs.getInt(6));
            product.setTax(rs.getInt(7));
            product.setTotal(rs.getDouble(8));
            productList.add(product);
        }
       return productList;
    }
    public static List<String> getOrdersID(String userId) throws SQLException{
        Connection conn = DBconnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select distinct order_id from orders where userid = userId");
        List<String> userIdList = new ArrayList<>();
        while(rs.next()){
            String id = rs.getString(1);
            userIdList.add(id);
        }
        return userIdList;
    }
    public static List<ProductsPojo> getAllOrders()throws SQLException{
        Connection conn = DBconnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select products.p_id,products.p_name,products.p_price,products.our_price,products.p_companyname,orders.quantity,products.p_tax,orders.grand_total from products inner join orders on products.p_id = orders.p_id order by order_id");
        List<ProductsPojo> productList = new ArrayList<>();
        while(rs.next()){
            ProductsPojo product = new ProductsPojo();
            product.setProductID(rs.getString(1));
            product.setProductName(rs.getString(2));
            product.setProductPrice(rs.getDouble(3));
            product.setOurPrice(rs.getDouble(4));
            product.setProductCompany(rs.getString(5));
            product.setQuantity(rs.getInt(6));
            product.setTax(rs.getInt(7));
            product.setTotal(rs.getDouble(8));
            productList.add(product);
        }
       return productList;
    }
    public static List<String> getAllOrderId()throws SQLException{
        Connection conn = DBconnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select order_id from orders");
        List<String> userIdList = new ArrayList<>();
        while(rs.next()){
            String id = rs.getString(1);
            userIdList.add(id);
        }
        return userIdList;
    }
}