package sample;

public class FoodItem {
    private String id;
    private String name;
    private double price;

    public FoodItem(String id, String name, double price){
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public double getPrice(){
        return price;
    }



}
