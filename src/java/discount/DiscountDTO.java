/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package discount;

/**
 *
 * @author IT
 */
public class DiscountDTO {
    private String discountID;
    private String discountCode;
    private boolean status;
    private int percent_Discount;
    private String expiry_date;

    public DiscountDTO(String discountID, String discountCode, int percent_Discount) {
        this.discountID = discountID;
        this.discountCode = discountCode;
        this.percent_Discount = percent_Discount;
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getPercent_Discount() {
        return percent_Discount;
    }

    public void setPercent_Discount(int percent_Discount) {
        this.percent_Discount = percent_Discount;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }
    
    
}
