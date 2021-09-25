/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emart.dao;

import emart.dbutil.DBconnection;
import emart.pojo.ProductsPojo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductsDAO {
    public static String getNextProductId()throws SQLException{
        Connection conn = DBconnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select max(p_id) from products");
        if(rs.next()== false){
            return "P101";
        }
        String productID = rs.getString(1).substring(1);
        
        int pid = Integer.parseInt(productID);
        pid = pid+1;
        return "P"+pid;
    }
    public static boolean addProduct(ProductsPojo product)throws SQLException{
        Connection conn = DBconnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("insert into products values(?,?,?,?,?,?,?,'Y')");
        ps.setString(1,product.getProductID());
        ps.setString(2,product.getProductName());
        ps.setString(3,product.getProductCompany());
        ps.setDouble(4,product.getProductPrice());
        ps.setDouble(5,product.getOurPrice());
        ps.setInt(6, product.getTax());
        ps.setInt(7,product.getQuantity());
        return ps.executeUpdate()==1;
    }
    public static List<ProductsPojo> getAllProducts()throws SQLException{
        Connection conn = DBconnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from products where status='Y'");
        ArrayList<ProductsPojo> productsList = new ArrayList<>();
        while(rs.next()){
            ProductsPojo product = new ProductsPojo();
            product.setProductID(rs.getString(1));
            product.setProductName(rs.getString(2));
            product.setProductCompany(rs.getString(3));
            product.setProductPrice(rs.getDouble(4));
            product.setOurPrice(rs.getDouble(5));
            product.setTax(rs.getInt(6));
            product.setQuantity(rs.getInt(7));
            productsList.add(product);
        }
        return productsList;
    }
    public static boolean deleteProduct(String pid) throws SQLException{
        Connection conn = DBconnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("update products set status='N' where p_id=?");
        ps.setString(1,pid);
        return ps.executeUpdate()==1;
    }
    public static boolean updateProduct(ProductsPojo product)throws SQLException{
        Connection conn = DBconnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("update products set p_name=?,p_companyname=?,p_price=?,our_price=?,p_tax=?,quantity=? where p_id=?");
        ps.setString(1, product.getProductName());
        ps.setString(2,product.getProductCompany());
        ps.setDouble(3, product.getProductPrice());
        ps.setDouble(4,product.getOurPrice());
        ps.setInt(5,product.getTax());
        ps.setInt(6,product.getQuantity());
        ps.setString(7,product.getProductID());
        return ps.executeUpdate()==1;
        
    }
    public static ProductsPojo getProductById(String id)throws SQLException{
        Connection conn = DBconnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Select * from products where p_id=? and status='Y'");
        ps.setString(1,id);
        ResultSet rs = ps.executeQuery();
        ProductsPojo product = null;
        if(rs.next()){
            product = new ProductsPojo();
            product.setProductID(rs.getString(1));
            product.setProductName(rs.getString(2));
            product.setProductCompany(rs.getString(3));
            product.setProductPrice(rs.getDouble(4));
            product.setOurPrice(rs.getDouble(5));
            product.setTax(rs.getInt(6));
            product.setQuantity(1);
            product.setTotal(product.getOurPrice()+(product.getOurPrice()*(product.getTax())/100));
        }
        return product;
    }
    public static boolean updateQuantity(ArrayList<ProductsPojo> productsList)throws SQLException{
        Connection conn = DBconnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("update products set quantity = quantity-? where p_id=?");
        int count = 0;
        for(ProductsPojo product:productsList){
            ps.setInt(1,product.getQuantity());
            ps.setString(2,product.getProductID());
            if(ps.executeUpdate()==1){
                count++;
            }
        }
        return count == productsList.size();
    }
}
