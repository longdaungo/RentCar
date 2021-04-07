/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detailbill;

/**
 *
 * @author IT
 */
public class DetailBillDTO {
    private String detailID;
    private String IDBill;
    private String carID;
    private String startDate;
    private String endDate;
    private int amount;
    private int total;

    

    public DetailBillDTO(String detailID, String carID, String startDate, String endDate, int amount, int total) {
        this.detailID = detailID;
        this.carID = carID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.total = total;
    }
    
    

    public String getDetailID() {
        return detailID;
    }

    public void setDetailID(String detailID) {
        this.detailID = detailID;
    }

    public String getIDBill() {
        return IDBill;
    }

    public void setIDBill(String IDBill) {
        this.IDBill = IDBill;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
    
}
