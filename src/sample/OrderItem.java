package sample;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OrderItem implements Serializable {
    private int orderId;
    private int tableNo;
    private Date orderDate;
    private int noItems;
    private double totalCharge;
    private List<String> itemList;
    private List<Integer> quantityEach;

    public OrderItem(){}

    public OrderItem(int orderId, int tableNo, Date orderDate, int noItems){
        this.orderId = orderId;
        this.tableNo = tableNo;
        this.orderDate = orderDate;
        this.noItems = noItems;
    }

    public void setOrderId(int orderId){
        this.orderId = orderId;
    }

    public int getOrderId(){
        return orderId;
    }

    public void setTableNo(int tableNo){
        this.tableNo = tableNo;
    }

    public int getTableNo(){
        return tableNo;
    }

    public void setOrderDate(Date orderDate){
        this.orderDate = orderDate;
    }

    public Date getOrderDate(){
        return orderDate;
    }

    public void setNoItems(int noItems){
        this.noItems = noItems;
    }

    public int getNoItems(){
        return noItems;
    }

    public void setTotalCharge(double totalCharge){
        this.totalCharge = totalCharge;
    }

    public double getTotalCharge(){
        return totalCharge;
    }

    public void setItemList(List<String> itemList){
        this.itemList = itemList;
    }

    public List<String> getItemList(){
        return itemList;
    }

    public void setQuantityEach(List<Integer> quantityEach){
        this.quantityEach = quantityEach;
    }

    public List<Integer> getQuantityEach(){
        return quantityEach;
    }
}
