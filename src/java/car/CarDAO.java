/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car;

import connection.MyConnection;
import detailbill.DetailBillDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;

/**
 *
 * @author IT
 */
public class CarDAO {

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

    public List<CarDTO> getListCarWithNameAndCategory(int pageIndex, String searchName, String searchCategory, Map<String, CarDTO> map) throws SQLException, NamingException {
        List<CarDTO> list = null;
        String sql = "with x as\n"
                + "(select ROW_NUMBER() over(order by CarID asc) as r,\n"
                + "* from tblCar\n"
                + "where Status = 1 and Name like ? and Category like ?)\n"
                + "select CarID, Name, Color, Year, Category, Price, Quantity, Status\n"
                + "from x\n"
                + "where r between ? and ?";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, "%" + searchName + "%");
            pre.setString(2, "%" + searchCategory + "%");
            pre.setInt(3, pageIndex * 3 - 2);
            pre.setInt(4, pageIndex * 3);
            rs = pre.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("CarID");
                int quantity = 0;
                if (map.containsKey(id)) {
                    quantity = map.get(id).getQuantity();
                } else {
                    quantity = rs.getInt("Quantity");
                }
                String name = rs.getString("Name");
                String color = rs.getString("Color");
                String year = rs.getString("Year");
                String category = rs.getString("Category");
                int price = rs.getInt("Price");
                CarDTO dto = new CarDTO(id, name, color, year, category, price, quantity);
                list.add(dto);
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    public int countCarsWithNameAndCategory(String searchName, String searchCategory) throws SQLException, NamingException {
        String sql = "select count(*) as count\n"
                + "from tblCar\n"
                + "where Status = 1 and Name like ? and Category like ?";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, "%" + searchName + "%");
            pre.setString(2, "%" + searchCategory + "%");
            rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } finally {
            closeConnection();
        }
        return 0;
    }

    public List<String> getAllCategory() throws SQLException, NamingException {
        String sql = "select Distinct Category\n"
                + "from tblCar";
        List<String> list = null;
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                String category = rs.getString("Category");
                list.add(category);
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    public int getQuantityCar(String id) throws NamingException, SQLException {
        int quantity = 0;
        String sql = "Select Quantity from tblCar where CarID = ?";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, id);
            rs = pre.executeQuery();
            if (rs.next()) {
                quantity = rs.getInt("Quantity");
            }
        } finally {
            closeConnection();
        }
        return quantity;
    }

    public Map<String, List<DetailBillDTO>> replaceCarIDbyCarName(Map<String, List<DetailBillDTO>> map, String searchName) throws SQLException, NamingException {
        String sql = "select Name from tblCar where CarID = ?";
        try {
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            Set<String> keys = map.keySet();
            for (int j = 0 ; j < keys.size();j++) {
                List<DetailBillDTO> listDetailBill = map.get(keys.toArray()[j]);
                for (int i = 0 ; i < listDetailBill.size(); i++) {
                    pre.setString(1, listDetailBill.get(i).getCarID());
                    rs = pre.executeQuery();
                    if (rs.next()) {
                        String name = rs.getString("Name");
                        if (name.contains(searchName)) {
                            listDetailBill.get(i).setCarID(name);
                        }
                        else {
                            listDetailBill.remove(i);
                            i--;
                        }
                    }
                }
                if(listDetailBill.size() == 0 ){
                    map.remove(keys.toArray()[j]);
                    j--;
                }
            }
        } finally {
            closeConnection();
        }
        return map;
    }
    
    public String getCarID(String detailBill) throws NamingException, SQLException{
        String sql = "Select CarID from tblDetailBill where DetailID = ?";
        try{
            con = MyConnection.Connection();
            pre = con.prepareStatement(sql);
            pre.setString(1, detailBill);
            rs = pre.executeQuery();
            if(rs.next()){
                return rs.getString("CarID");
            }
        }
        finally{
            closeConnection();
        }
        return null;
    }
}
