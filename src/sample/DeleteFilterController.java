package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DeleteFilterController implements Initializable {

    @FXML
    ListView<String> allFilterList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeFilterList();
    }


    public void deleteSelectedItem(){
        String deleteItem = allFilterList.getSelectionModel().getSelectedItem();
        allFilterList.getItems().remove(deleteItem);
    }

    public void resetList(){
        allFilterList.getItems().clear();
        initializeFilterList();
    }

    public void confirmDeletion(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Are you sure to make this changes?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("dataFiles/filterList.txt"));
                for(String i : allFilterList.getItems()){
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

    public void initializeFilterList(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("dataFiles/filterList.txt"));
            String line = null;
            while((line = bufferedReader.readLine())!= null){
                allFilterList.getItems().add(line);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
