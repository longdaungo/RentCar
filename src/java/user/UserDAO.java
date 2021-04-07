package user;


import connection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author IT
 */
public class UserDAO {
    Connection con;
    PreparedStatement pre;
    ResultSet rs;
    
    public void closeConnection() throws SQLException{
        if(rs != null){
            rs.close();
        }
        if(pre != null){
            pre.close();
        }
        if(con != null){
            con.close();
        }
    }
    
    public String checkLogin(String email, String password) throws NamingException, SQLException {
        String name = null;
        String sql = "select Name from tblUser where Email =? and Password =? and Status =1";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, email);
            pre.setString(2, password);
            rs = pre.executeQuery();
            if (rs.next()) {
                name = rs.getString("Name");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            closeConnection();
        }
        return name;
    }
    
     public boolean checkAdmin(String email) throws SQLException, NamingException{
         boolean admin = false;
        String sql = "select Role from tblUser where Email =? and Status =1";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, email);
            rs = pre.executeQuery();
            if (rs.next()) {
                admin = rs.getBoolean("Role");
            }
        } finally {
            closeConnection();
        }
        return admin;
     }
     
      public void insertUser(String email, String passowrd, Boolean role, String name, Boolean status, String phone, String address, String createdDate) throws NamingException, SQLException{
        String sql = "insert into tblUser(Email, Password, Role, Name, Status, Phone, Address, CreateDate) values(?,?,?,?,?,?,?,?)";
        try{
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, email);
            pre.setString(2, passowrd);
            pre.setBoolean(3, role);
            pre.setString(4, name);
            pre.setBoolean(5, status);
            pre.setString(6, phone);
            pre.setString(7, address);
            pre.setString(8, createdDate);
            pre.executeUpdate();
        }finally{
            closeConnection();
        }
    }
      
      public boolean getUser(String email) throws SQLException, NamingException{
          String sql = "Select Email from tblUser where Email = ?";
          try{
              con = MyConnection.Connection();
              pre = con.prepareStatement(sql);
              pre.setString(1, email);
              rs = pre.executeQuery();
              if(rs.next()){
                  return true;
              }
          }
          finally{
              closeConnection();
          }
          return false;
      }
      
      public void insertUserByFaceBook(String email, Boolean role, String name, Boolean status, String createdDate) throws NamingException, SQLException{
        String sql = "insert into tblUser(Email, Role, Name, Status, CreateDate) values(?,?,?,?,?)";
        try{
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, email);
            pre.setBoolean(2, role);
            pre.setString(3, name);
            pre.setBoolean(4, status);
            pre.setString(5, createdDate);
            pre.executeUpdate();
        }finally{
            closeConnection();
        }
    }
}
