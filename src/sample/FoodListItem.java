package sample;

public class FoodListItem {
    private String name;
    private String shortName;
    private int number;

    public FoodListItem(String name, String shortName, int number){
        this.name = name;
        this.shortName = shortName;
        this.number = number;
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

    public void setNumber(int number){
        this.number = number;
    }

    public int getNumber(){
        return number;
    }
}
