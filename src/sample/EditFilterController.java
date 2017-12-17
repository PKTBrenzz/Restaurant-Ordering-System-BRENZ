package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditFilterController implements Initializable {
    @FXML
    ComboBox<String> filterbox;
    @FXML
    ListView<String> currentList;
    @FXML
    ListView<String> allFoodList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeAllFoodList();
        initializeFilterList();
    }

    public void deleteFoodItem(){
        String deleteItem = currentList.getSelectionModel().getSelectedItem();
        currentList.getItems().remove(deleteItem);
    }

    public void addItem(){
        if(!currentList.getItems().contains(allFoodList.getSelectionModel().getSelectedItem())){
            currentList.getItems().add(allFoodList.getSelectionModel().getSelectedItem());
        }
    }

    public void resetList(){
        initializeCurrentList();
    }

    public void confirmationExecute(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Are you sure to make this changes?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("filterList/" + filterbox.getValue() + ".txt"));
                for(String i : currentList.getItems()){
                    bufferedWriter.write(i);
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    public void initializeCurrentList(){
        currentList.getItems().clear();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("filterList/" + filterbox.getValue() + ".txt"));
            String line = null;
            while((line = bufferedReader.readLine()) != null){
                currentList.getItems().add(line);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeAllFoodList(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("dataFiles/foodList.txt"));
            String line = null;
            while((line = bufferedReader.readLine())!= null){
                String[] food = line.split(",");
                allFoodList.getItems().add(food[1]);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void initializeFilterList() {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("dataFiles/filterList.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line = null;
        try {
            while((line = bufferedReader.readLine())!= null){
                filterbox.getItems().add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
