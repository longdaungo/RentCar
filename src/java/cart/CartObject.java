/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cart;

import car.CarDAO;
import detailbill.DetailBillDAO;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;


/**
 *
 * @author IT
 */
public class CartObject {
    Map<String, Item> map;

    public CartObject() {
        map = new HashMap<>();
    }
    
    public void addItem(String id,Item newItem){
        if(map.containsKey(id)){
           Item itemInCart = map.get(id);
           int oldamountCar= itemInCart.getAmount();
           int oldprice = itemInCart.getPrice();
           int priceEachCar = oldprice / oldamountCar;
           int newamountCar = ++oldamountCar;
           int newPrice = priceEachCar*newamountCar;
           itemInCart.setAmount(newamountCar);
           itemInCart.setPrice(newPrice);
        }
        else{
            map.put(id, newItem);
        }
    }
    
    public void removeItem(String id){
        map.remove(id);
    }
    
    public int getSize(){
        return map.size();
    }

    public Map<String, Item> getMap() {
        return map;
    }
    
    public void setAmount(String id, int amount){    
        Item item = map.get(id);
        int oldprice = item.getPrice();
        int oldAmount = item.getAmount();
        int priceEachCar = oldprice / oldAmount;
        item.setPrice(priceEachCar*amount);
        item.setAmount(amount);
    }
    
    public void checkQuantityCars() throws NamingException, SQLException, Exception{
        boolean rs = false;
        Set<String> keys = map.keySet();
        String error = "";
        DetailBillDAO detailDAO = new DetailBillDAO();
        CarDAO carDAO = new CarDAO();
        for (String key : keys) {
            Item item = map.get(key);
            String dateStart = item.getDatestart();
            String dateEnd = item.getDateend();
            int amount = item.getAmount();
            int remainamount = detailDAO.getMapCarReducedAmountById(key, dateStart, dateEnd);
            if(remainamount < 0){
                remainamount = carDAO.getQuantityCar(key);
            }
            if(amount > remainamount){
                error += item.getName()+" amount is out of stock</br> ";
                rs = true;
            }
        }
        if(rs){
            throw new Exception(error);
        }
    }
    
    public void insertDetailBill(String idBill) throws NamingException, SQLException{
        DetailBillDAO dao = new DetailBillDAO();
        int idDetailBill = dao.countDetailBill();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            idDetailBill++;
            String stringidDetailBill = String.valueOf(idDetailBill);
            Item item = map.get(key);
            String dateStart = item.getDatestart();
            String dateEnd = item.getDateend();
            String idCar = key;
            int amount = item.getAmount();
            int price = item.getPrice();
            dao.insertDetailBill(stringidDetailBill, idBill, idCar, dateStart, dateEnd, amount, price);
        }
    }
    
    public boolean checkDateStartAndDateEnd(String inputDateStart, String inputDateEnd){
       Item item = map.get(map.keySet().toArray()[0]);
       String dateStart = item.getDatestart();
       String dateEnd = item.getDateend();
       if(inputDateStart.equals(dateStart) & inputDateEnd.equals(dateEnd))
           return true;
       return false;
    }
}
