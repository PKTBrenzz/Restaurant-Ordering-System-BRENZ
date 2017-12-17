package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditFoodItemController implements Initializable {

    @FXML
    TableView<FoodItem> foodTable;
    @FXML
    TextField idField;
    @FXML
    TextField namefield;
    @FXML
    TextField pricefield;
    @FXML
    TableColumn foodID;
    @FXML
    TableColumn foodName;
    @FXML
    TableColumn foodPrice;
    @FXML
    Label errorlabel;

    List<FoodItem> foods = new ArrayList<FoodItem>();
    String oldId;
    String oldName;
    double oldPrice;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        foodID.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("id"));
        foodName.setCellValueFactory(new PropertyValueFactory<FoodItem, Integer>("name"));
        foodPrice.setCellValueFactory(new PropertyValueFactory<FoodItem, Double>("price"));

        foodItemInput();

        for(FoodItem i : foods){
            foodTable.getItems().add(i);
        }
    }

    public void selectOneItem(){
        FoodItem selectedItem = foodTable.getSelectionModel().getSelectedItem();

        idField.setText(selectedItem.getId());
        namefield.setText(selectedItem.getName());
        pricefield.setText(new Double(selectedItem.getPrice()).toString());

        oldId = selectedItem.getId();
        oldName = selectedItem.getName();
        oldPrice = selectedItem.getPrice();
    }

    public void editExecute() throws IOException {
        if(!isDouble(pricefield.getText())){
            errorlabel.setText("Error!");
        }
        else{
            for(FoodItem i : foodTable.getItems()){
                if(oldId == i.getId()){
                    i.setName(oldName);
                    i.setPrice(oldPrice);
                    foodTable.refresh();
                }
            }
        }
    }

    public void resetList(){
        foodTable.getItems().clear();
        for(FoodItem i : foods){
            foodTable.getItems().add(i);
        }
    }

    public void deleteExecute(){
        FoodItem deleteItem = foodTable.getSelectionModel().getSelectedItem();
        foodTable.getItems().remove(deleteItem);
    }

    public void confirmChange(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Are you sure, that you want to add this filter?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            String newFoods = "";
            for(FoodItem i : foodTable.getItems()){
                newFoods = newFoods + i.getId() + "," + i.getName() + "," + new Double(i.getPrice()).toString() + "\r\n";
            }
            System.out.print(newFoods);

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("dataFiles/foodList.txt"));
            bufferedWriter.write(newFoods);
            bufferedWriter.close();

            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
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

    public boolean isDouble(String d){
        try{
            Double.parseDouble(d);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
