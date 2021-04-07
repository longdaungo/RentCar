/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package discount;

import bill.BillDTO;
import connection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;

/**
 *
 * @author IT
 */
public class DiscountDAO {
    Connection con;
    PreparedStatement pre;
    ResultSet rs;

    public void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (pre != null) {
            pre.close();
        }
        if (con != null) {
            con.close();
        }
    }
    
    public int getPercentDiscount(String discountCode, String dateCurrent) throws SQLException, NamingException{
        String sql = "Select Percent_Discount from tblDiscount where DiscountCode = ? and Status = 1 and Expiry_date >= ?";
        try{
           con = MyConnection.Connection();
           pre = con.prepareStatement(sql);
           pre.setString(1, discountCode);
           pre.setString(2,dateCurrent);
           rs = pre.executeQuery();
           if(rs.next()){
               return rs.getInt("Percent_Discount");
           }
        }finally{
            closeConnection();
        }
        return 0;
    }
    
    public String getDiscountID(String discountCode, String dateCurrent) throws NamingException, SQLException{
        String sql = "Select DiscountID from tblDiscount where DiscountCode = ? and Status = 1 and Expiry_date >= ?";
        try{
           con = MyConnection.Connection();
           pre = con.prepareStatement(sql);
           pre.setString(1, discountCode);
           pre.setString(2,dateCurrent);
           rs = pre.executeQuery();
           if(rs.next()){
               return rs.getString("DiscountID");
           }
        }finally{
            closeConnection();
        }
        return null;
    }
    
    public void updateStatus(String discountID) throws NamingException, SQLException{
        String sql = "update tblDiscount set Status = 0 where DiscountID = ?";
        try{
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, discountID);
            pre.executeUpdate();
        }
        finally{
            closeConnection();
        }
    }
    
    public Map<String,DiscountDTO> getMapDiscount(List<BillDTO> listBill) throws NamingException, SQLException{
        Map<String,DiscountDTO> map =  new HashMap<>();
        String sql = "Select DiscountCode, Percent_Discount from tblDiscount where DiscountID = ?";
        try{
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            for (BillDTO billDTO : listBill) {
                pre.setString(1, billDTO.getDiscountID());
                rs = pre.executeQuery();
                if(rs.next()){
                    String discountCode = rs.getString("DiscountCode");
                    int percent = rs.getInt("Percent_Discount");
                    DiscountDTO dto = new DiscountDTO(billDTO.getDiscountID(), discountCode, percent);
                    map.put(billDTO.getDiscountID(), dto);
                }
            }
        }
        finally{
            closeConnection();
        }
        return map;
    }
}
