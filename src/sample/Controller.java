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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    TextField cashAmount;
    @FXML
    Button deletebutton;
    @FXML
    Button checkoutButton;
    @FXML
    Button test;
    @FXML
    Label tableNumber;
    @FXML
    Label errorlabel;

    List<FoodItem> foods = new ArrayList<FoodItem>();
    List<String> filters = new ArrayList<String>();
    ObservableList<FoodListItem> list = FXCollections.observableArrayList();


    public void deleteTableItem(){
        FoodListItem deleteItem = itemtable.getSelectionModel().getSelectedItem();
        itemtable.getItems().remove(deleteItem);
        setTotal();
    }

    public void checkOutExecute(){
        if(Double.parseDouble(totalfield.getText()) > Double.parseDouble(cashAmount.getText())){
            errorlabel.setText("ERROR!!! Cash inserted is not enough!");
        }
        else {
            try {
                boolean comfirm = false;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("summary.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(loader.load(), 350, 600);
                Summary summary = loader.getController();
                summary.setSummarytext(itemtable.getItems());
                stage.setTitle("Summary");
                stage.setScene(scene);
                stage.showAndWait();
                comfirm = summary.getComfirmation();
                if (comfirm) {
                    errorlabel.setText("true");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
        getFilterInput();

        Button filterAll = new Button("ALL");
        filterAll.setPrefSize(150,60);
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

        for(String i : filters){
            Button filterButton = new Button(i);
            filterButton.setPrefSize(150,60);
            filterButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    List<String> filteredFoods = getFilterList(i);

                    flowtest.getChildren().clear();
                    for(FoodItem i : foods){
                        if(filteredFoods.contains(i.getName())) {
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
            filterlist.getChildren().add(filterButton);
        }



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
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filter;
    }

    public void getFilterInput(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("dataFiles/filterList.txt"));
            String line = null;
            while((line = bufferedReader.readLine())!= null){
                filters.add(line);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void foodItemInput(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("dataFiles/foodList.txt"));
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

    public void setTableNumber(int tableno){
        tableNumber.setText(new Integer(tableno).toString());
    }
}
