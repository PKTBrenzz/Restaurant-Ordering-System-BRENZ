package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    FlowPane flowtest;
    @FXML
    TableView<FoodListItem> itemlist;
    @FXML
    TableColumn item_column;
    @FXML
    TableColumn no_column;
    @FXML
    VBox categorylist;
    @FXML
    Button deletebutton;
    @FXML
    Button test;


    List<FoodItem> foods = new ArrayList<FoodItem>();
    List<FoodListItem> list = new ArrayList<FoodListItem>();


    public void deleteTableItem(){
        FoodListItem deleteItem = itemlist.getSelectionModel().getSelectedItem();
        itemlist.getItems().remove(deleteItem);
    }


    public void testpress(javafx.event.ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("main_menu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource() ).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        item_column.setCellValueFactory(new PropertyValueFactory<FoodListItem, String>("name"));
        no_column.setCellValueFactory(new PropertyValueFactory<FoodListItem, Integer>("number"));

        Button allB = new Button("ALL");
        allB.setPrefSize(200,75);
        allB.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                flowtest.getChildren().clear();
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
        });
        Button abcB = new Button("abc");
        abcB.setPrefSize(200,75);
        abcB.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                flowtest.getChildren().clear();
                for(FoodItem i : foods){
                    if(i.getName() == abcB.getText()) {
                        Button button = new Button(i.getName());
                        button.setPrefSize(100, 100);
                        button.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                            @Override
                            public void handle(javafx.event.ActionEvent event) {
                                FoodListItem temp = new FoodListItem(i.getName(), i.getShortName(), 1);
                                itemlist.getItems().add(temp);
                            }
                        });
                        flowtest.getChildren().add(button);
                    }
                }
            }
        });
        categorylist.getChildren().add(allB);
        categorylist.getChildren().add(abcB);



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
}
