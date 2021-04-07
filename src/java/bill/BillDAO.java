/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bill;

import connection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author IT
 */
public class BillDAO {
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
    
    public int countBills() throws NamingException, SQLException {
        String sql = "select count(*) as count\n"
                + "from tblBill";
        int count = 0;
        try{
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            if(rs.next()){
                count = rs.getInt("count");
            }
        }finally{
            closeConnection();
        }
        return count;
    }
    
    public void insertBillWithDiscount(String idBill,int total, String date, String email, String discountID) throws NamingException, SQLException{
        String sql = "Insert into tblBill(IDBill, Email, Total, Date, DiscountID, Status) values(?,?,?,?,?,1)";
        try{
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, idBill);
            pre.setString(2,email);
            pre.setInt(3,total);
            pre.setString(4, date);
            pre.setString(5,discountID);
            pre.executeUpdate();
        }finally{
            closeConnection();
        }
    }
    
    public void insertBillWithoutDiscount(String idBill,int total, String date, String email) throws NamingException, SQLException{
        String sql = "Insert into tblBill(IDBill, Email, Total, Date,Status) values(?,?,?,?,1)";
        try{
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, idBill);
            pre.setString(2,email);
            pre.setInt(3,total);
            pre.setString(4, date);
            pre.executeUpdate();
        }finally{
            closeConnection();
        }
    } 
    
    public List<BillDTO> takeOverHistory(String email, String searchDate) throws NamingException, SQLException{
        List<BillDTO> list = null;
        String sql = "Select IDBill, Total, Date, DiscountID from tblBill where Email = ? and Date like ? and Status = 1";
        try{
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, email);
            pre.setString(2, "%"+searchDate+"%");
            rs = pre.executeQuery();
            list = new ArrayList<>();
            while(rs.next()){
                String idBill = rs.getString("IDBill");
                int total = rs.getInt("Total");
                String date = rs.getString("Date");
                String discountID = rs.getString("DiscountID");
                BillDTO dto = new BillDTO(idBill, total, date, discountID);
                list.add(dto);
            }
        }finally{
            closeConnection();
        }
        return list;
    }
    
    public void updateStatus(String idBill) throws NamingException, SQLException{
        String sql = "Update tblBill set Status = 0 where IDBill = ?";
        try{
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1,idBill);
            pre.executeUpdate();
        }
        finally{
            closeConnection();
        }
    }
    
}
