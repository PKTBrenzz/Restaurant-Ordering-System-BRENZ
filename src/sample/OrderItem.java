package sample;

import java.util.List;

public class OrderItem {
    private int orderId;
    private int tableNo;
    private int noItems;
    private List<String> itemList;
    private List<Integer> quantityEach;

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

    public void setNoItems(int noItems){
        this.noItems = noItems;
    }

    public int getNoItems(){
        return noItems;
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
