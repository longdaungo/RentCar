/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detailbill;

import bill.BillDTO;
import car.CarDTO;
import connection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;

/**
 *
 * @author IT
 */
public class DetailBillDAO {

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

//    public void insertDetailBill() throws NamingException, SQLException {
//        String sql = "Insert into tblDetailBill(DetailID, IDBill, IDCar, Start_Date_Rental, End_Date_Rental, Amount, Total) values(?,?,?,?,?,?,?)";
//        try {
//            con = MyConnection.Connection();
//            pre = con.prepareStatement(sql);
//            pre.setString(1, "1");
//            pre.setString(2, "1");
//            pre.setString(3, "1");
//            pre.setString(4, "02/17/2021");
//            pre.setString(5, "02/18/2021");
//            pre.setInt(6, 1);
//            pre.setInt(7, 10);
//            pre.executeUpdate();
//        } finally {
//            con = MyConnection.Connection();
//            pre = con.prepareStatement(sql);
//        }
//    }
    public Map<String, CarDTO> getMapCarReducedAmount(String startRental, String endRental) throws NamingException, SQLException {
        Map<String, CarDTO> map = null;
        String sql = "select CarID, Name, Color, Year, Category, Price, Quantity - amount as Quantity\n" +
"                from (select tblCar.CarID, Name, Color, Year, Category, Price, tblCar.Quantity, sum(Amount) as amount\n" +
"                            	from tblCar INNER JOIN tblDetailBill ON tblCar.CarID = tblDetailBill.CarID\n" +
"                            	where tblDetailBill.Start_Date_Rental <= ? and  ? < tblDetailBill.End_Date_Rental or tblDetailBill.Start_Date_Rental < ? and ? <= tblDetailBill.End_Date_Rental or ? < tblDetailBill.Start_Date_Rental and ? > tblDetailBill.End_Date_Rental\n" +
"              				Group By tblCar.CarID, Name, Color, Year, Category, Price, Quantity, Amount\n" +
"             				) as x";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, startRental);
            pre.setString(2, startRental);
            pre.setString(3, endRental);
            pre.setString(4, endRental);
            pre.setString(5, startRental);
            pre.setString(6, endRental);
            rs = pre.executeQuery();
            map = new HashMap<>();
            while (rs.next()) {
                int quantity = rs.getInt("Quantity");
                String id = rs.getString("CarID");
                String name = rs.getString("Name");
                String color = rs.getString("Color");
                String year = rs.getString("Year");
                String category = rs.getString("Category");
                int price = rs.getInt("Price");
                CarDTO dto = new CarDTO(id, name, color, year, category, price, quantity);
                map.put(id, dto);
            }
        } finally {
            closeConnection();
        }
        return map;
    }

    public int getMapCarReducedAmountById(String id, String startRental, String endRental) throws NamingException, SQLException, ParseException {
//        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
//        java.util.Date dateStart  = formater.parse(startRental);
//        java.util.Date dateEnd = formater.parse(endRental);
        int amount = -1;
        String sql = "select x.Quantity\n"
                + " from (select tblCar.CarID , tblCar.Quantity - tblDetailBill.Amount as Quantity\n"
                + "                 	from tblCar INNER JOIN tblDetailBill ON tblCar.CarID = tblDetailBill.CarID\n"
                + "                	where tblDetailBill.Start_Date_Rental <= ? and  ? < tblDetailBill.End_Date_Rental or tblDetailBill.Start_Date_Rental < ? and ? <= tblDetailBill.End_Date_Rental or ? < tblDetailBill.Start_Date_Rental and ? > tblDetailBill.End_Date_Rental) as x\n"
                + "where x.CarID = ?";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
//            pre.setDate(1, new java.sql.Date(dateStart.getTime()));
//            pre.setDate(2, new java.sql.Date(dateStart.getTime()));
//            pre.setDate(3, new java.sql.Date(dateEnd.getTime()));
//            pre.setDate(4, new java.sql.Date(dateEnd.getTime()));
            pre.setString(1, startRental);
            pre.setString(2, startRental);
            pre.setString(3, endRental);
            pre.setString(4, endRental);
            pre.setString(5, startRental);
            pre.setString(6, endRental);
            pre.setString(7, id);
            rs = pre.executeQuery();
            if (rs.next()) {
                amount = rs.getInt("Quantity");
            }
        } finally {
            closeConnection();
        }
        return amount;
    }

    public void insertDetailBill(String detailID, String IDBill, String CarID, String StartDate, String EndDate, int amount, int total) throws NamingException, SQLException {
        String sql = "Insert into tblDetailBill(DetailID, IDBill, CarID, Start_Date_Rental, End_Date_Rental, Amount, Total) values(?,?,?,?,?,?,?)";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, detailID);
            pre.setString(2, IDBill);
            pre.setString(3, CarID);
            pre.setString(4, StartDate);
            pre.setString(5, EndDate);
            pre.setInt(6, amount);
            pre.setInt(7, total);
            pre.executeUpdate();
        } finally {
            closeConnection();
        }
    }

    public int countDetailBill() throws NamingException, SQLException {
        String sql = "select count(*) as count\n"
                + "from tblDetailBill";
        int count = 0;
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } finally {
            closeConnection();
        }
        return count;
    }

    public Map<String, List<DetailBillDTO>> getMapDetailBill(List<BillDTO> listBills) throws SQLException, NamingException {
        Map<String, List<DetailBillDTO>> map = new HashMap<>();
        String sql = "Select DetailID, CarID, Start_Date_Rental, End_Date_Rental, Amount, Total from tblDetailBill where IDBill = ?";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            for (BillDTO listBill : listBills) {
                pre.setString(1, listBill.getIdBill());
                rs = pre.executeQuery();
                List<DetailBillDTO> listdetailBill = new ArrayList<>();
                while (rs.next()) {
                    String detailID = rs.getString("DetailID");
                    String carID = rs.getString("CarID");
                    String startDate = rs.getString("Start_Date_Rental");
                    String endDate = rs.getString("End_Date_Rental");
                    int amount = rs.getInt("Amount");
                    int total = rs.getInt("Total");
                    DetailBillDTO dto = new DetailBillDTO(detailID, carID, startDate, endDate, amount, total);
                    listdetailBill.add(dto);
                }
                map.put(listBill.getIdBill(), listdetailBill);
            }
        } finally {
            closeConnection();
        }
        return map;
    }

    
}
