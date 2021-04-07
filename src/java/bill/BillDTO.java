/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bill;

/**
 *
 * @author IT
 */
public class BillDTO {
   private String idBill;
   private String email;
   private int total;
   private String date;
   private String discountID;

    public BillDTO(String idBill, int total, String date, String discountID) {
        this.idBill = idBill;
        this.total = total;
        this.date = date;
        this.discountID = discountID;
    }

    public String getIdBill() {
        return idBill;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }
   
   
}
