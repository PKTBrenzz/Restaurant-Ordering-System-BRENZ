package sample;

public class FoodListItem {
    private String name;
    private int quantity;
    private double subtotal;

    public FoodListItem(String name, int quantity, double subtotal){
        this.name = name;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setSubtotal(double subtotal){
        this.subtotal = subtotal;
    }

    public double getSubtotal(){
        return subtotal;
    }
}
