/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emart.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class DBconnection {
    private static Connection conn;
    static
    {
       try
       {
           Class.forName("oracle.jdbc.OracleDriver");
           conn=DriverManager.getConnection("jdbc:oracle:thin:@//DESKTOP-S24F9TV:1521/xe","grocery","grocery");
           JOptionPane.showMessageDialog(null,"Connection done successfully");
        }
       catch(ClassNotFoundException cnfe)
       {
           JOptionPane.showMessageDialog(null,"can not load the driver!","Error!",JOptionPane.ERROR_MESSAGE);
           cnfe.printStackTrace();
       }
        catch(SQLException sqlex)
       {
           JOptionPane.showMessageDialog(null,"Error in DB","Error!",JOptionPane.ERROR_MESSAGE);
           sqlex.printStackTrace();
       }
    }
    public static Connection getConnection()
    {
        return conn;
    }
    public static void closeConnection()
    {
        try{
            if(conn!=null)
            {
                conn.close();
                JOptionPane.showMessageDialog(null,"connection closed successfuly!!");

            }
        }
         catch(SQLException sqlex)
         {
           JOptionPane.showMessageDialog(null,"problem in closing the connection!!");
           sqlex.printStackTrace();
          }    
            
        }
    }
          
          

