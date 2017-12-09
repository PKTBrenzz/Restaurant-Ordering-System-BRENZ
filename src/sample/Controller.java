package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    @FXML
    Button test;
    @FXML
    FlowPane flowtest;
    @FXML
    TableView<FoodListItem> itemlist;
    @FXML
    TableColumn item_column;
    @FXML
    TableColumn no_column;

    List<FoodItem> foods = new ArrayList<FoodItem>();
    List<FoodListItem> list = new ArrayList<FoodListItem>();

    @FXML
    private void initialize(){
        item_column.setCellValueFactory(new PropertyValueFactory<FoodListItem, String>("name"));
        no_column.setCellValueFactory(new PropertyValueFactory<FoodListItem, Integer>("number"));

        foods.add(new FoodItem("1","abc","abc",10.00, "big"));
        foods.add(new FoodItem("2","def","def",5.00, "big"));
        foods.add(new FoodItem("3","ghi","ghi",7.00, "big"));
        foods.add(new FoodItem("4","jkl","jkl",12.00, "big"));

        for(FoodItem i : foods){
            Button button = new Button(i.getName());
            button.setPrefSize(100,100);
            button.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                @Override
                public void handle(javafx.event.ActionEvent event) {
                    FoodListItem temp = new FoodListItem(i.getName(),i.getShortName(),1);
                    itemlist.getItems().add(temp);
                }
            });
            flowtest.getChildren().add(button);
        }
    }

    public void testPressed(){
        Button button = new Button("YES");
        button.setPrefSize(100,100);
        button.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {

            }
        });

        flowtest.getChildren().add(button);
    }


}
