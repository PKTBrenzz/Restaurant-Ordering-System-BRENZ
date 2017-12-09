package sample;

public class FoodItem {
    private String id;
    private String name;
    private String shortName;
    private String category;
    private double price;
    private String description;

    public FoodItem(String id, String name, String shortName, double price, String description){
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.price = price;
        this.description = description;
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

    public void setShortName(String shortName){
        this.shortName = shortName;
    }

    public String getShortName(){
        return shortName;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getCategory(){
        return category;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public double getPrice(){
        return price;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

}
