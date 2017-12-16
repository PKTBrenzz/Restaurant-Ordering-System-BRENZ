package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    FlowPane flowtest;
    @FXML
    TableView<FoodListItem> itemtable;
    @FXML
    TableColumn item_column;
    @FXML
    TableColumn qty_column;
    @FXML
    TableColumn subtotal_column;
    @FXML
    VBox filterlist;
    @FXML
    TextField totalfield;
    @FXML
    Button deletebutton;
    @FXML
    Button checkoutButton;
    @FXML
    Button test;


    List<FoodItem> foods = new ArrayList<FoodItem>();
    ObservableList<FoodListItem> list = FXCollections.observableArrayList();


    public void deleteTableItem(){
        FoodListItem deleteItem = itemtable.getSelectionModel().getSelectedItem();
        itemtable.getItems().remove(deleteItem);
        setTotal();
    }


    public void testpress(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("main_menu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource() ).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        item_column.setCellValueFactory(new PropertyValueFactory<FoodListItem, String>("name"));
        qty_column.setCellValueFactory(new PropertyValueFactory<FoodListItem, Integer>("quantity"));
        subtotal_column.setCellValueFactory(new PropertyValueFactory<FoodListItem, Double>("subtotal"));

        foodItemInput();

        Button filterAll = new Button("ALL");
        filterAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                flowtest.getChildren().clear();
                for(FoodItem i : foods){
                    Button button = new Button(i.getName());
                    button.setPrefSize(100,100);
                    button.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                        @Override
                        public void handle(javafx.event.ActionEvent event) {
                            ObservableList<FoodListItem> templist = itemtable.getItems();
                            boolean found = false;
                            for(FoodListItem j : templist){
                                if(j.getName() == i.getName()){
                                    found = true;
                                    j.setQuantity(j.getQuantity()+1);
                                    j.setSubtotal(j.getSubtotal()+i.getPrice());
                                    itemtable.refresh();
                                }
                            }
                            if(!found) {
                                FoodListItem temp = new FoodListItem(i.getName(), 1, i.getPrice());
                                itemtable.getItems().add(temp);
                            }
                            setTotal();
                        }
                    });
                    flowtest.getChildren().add(button);
                }
            }
        });
        filterlist.getChildren().add(filterAll);

        Button filterFood = new Button("Food");
        filterFood.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<String> filter = getFilterList(filterFood.getText());

                flowtest.getChildren().clear();
                for(FoodItem i : foods){
                    if(filter.contains(i.getName())) {
                        Button button = new Button(i.getName());
                        button.setPrefSize(100, 100);
                        button.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                            @Override
                            public void handle(javafx.event.ActionEvent event) {
                                ObservableList<FoodListItem> templist = itemtable.getItems();
                                boolean found = false;
                                for (FoodListItem j : templist) {
                                    if (j.getName() == i.getName()) {
                                        found = true;
                                        j.setQuantity(j.getQuantity() + 1);
                                        j.setSubtotal(j.getSubtotal() + i.getPrice());
                                        itemtable.refresh();
                                    }
                                }
                                if (!found) {
                                    FoodListItem temp = new FoodListItem(i.getName(), 1, i.getPrice());
                                    itemtable.getItems().add(temp);
                                }
                                setTotal();
                            }
                        });
                        flowtest.getChildren().add(button);
                    }
                }

            }
        });
        filterlist.getChildren().add(filterFood);



        for(FoodItem i : foods){
            Button button = new Button(i.getName());
            button.setPrefSize(100,100);
            button.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                @Override
                public void handle(javafx.event.ActionEvent event) {
                    ObservableList<FoodListItem> templist = itemtable.getItems();
                    boolean found = false;
                    for(FoodListItem j : templist){
                        if(j.getName() == i.getName()){
                            found = true;
                            j.setQuantity(j.getQuantity()+1);
                            j.setSubtotal(j.getSubtotal()+i.getPrice());
                            itemtable.refresh();
                        }
                    }
                    if(!found) {
                        FoodListItem temp = new FoodListItem(i.getName(), 1, i.getPrice());
                        itemtable.getItems().add(temp);
                    }
                    setTotal();
                }
            });
            flowtest.getChildren().add(button);
        }
    }

    public void setTotal(){
        ObservableList<FoodListItem> templist = itemtable.getItems();
        double total = 0;
        for(FoodListItem i : templist){
            total += i.getSubtotal();
        }
        totalfield.setText(new Double(total).toString());
    }

    public List<String> getFilterList(String text){
        List<String> filter = new ArrayList<String>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("filterList/" +  text + ".txt"));
            String line = null;
            while((line = bufferedReader.readLine())!= null){
                filter.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return filter;
    }

    public void foodItemInput(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\vegeg\\Documents\\NetBeansProjects\\Restaurant-Ordering-System-BRENZ\\src\\sample\\foodList.txt"));
            String line = null;
            while((line = bufferedReader.readLine())!= null){
                String[] data = line.split(",");
                foods.add(new FoodItem(data[0], data[1],  Double.parseDouble(data[2])));
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
