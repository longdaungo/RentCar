/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feedback;

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
public class FeedBackDAO {

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

    public void insertFeedBack(String id, String carID, String comment, String rating, String name) throws NamingException, SQLException {
        String sql = "Insert into tblFeedBack(FeedBackID, CarID, Comment, Rating,Name) values(?,?,?,?,?)";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, id);
            pre.setString(2, carID);
            pre.setString(3, comment);
            pre.setString(4, rating);
            pre.setString(5, name);
            pre.executeUpdate();
        } finally {
            closeConnection();
        }
    }

    public int countRow() throws NamingException, SQLException {
        String sql = "Select count(*) as count from tblFeedBack";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } finally {
            closeConnection();
        }
        return 0;
    }

    public List<FeedBackDTO> getListFeedBack(String carID) throws NamingException, SQLException {
        List<FeedBackDTO> list = null;
        String sql = "select FeedBackID, CarID, Comment, Rating, Name from tblFeedBack where CarID = ?";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, carID);
            rs = pre.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                String feedBackID = rs.getString("FeedBackID");
                String comment = rs.getString("Comment");
                int rating = rs.getInt("Rating");
                String name = rs.getString("Name");
                FeedBackDTO dto = new FeedBackDTO(feedBackID, carID, comment, rating, name);
                list.add(dto);
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    public int getAverage(String carID) throws SQLException, NamingException {
        String sql = "select AVG(Rating) as avgrating\n"
                + "from tblFeedBack\n"
                + "where CarID = ?";       
        try{
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, carID);
            rs = pre.executeQuery();
            if(rs.next()){
                int average = rs.getInt("avgrating");
                return average;
            }
        }
        finally{
            closeConnection();
        }
        return 0;
    }
    
    
}
