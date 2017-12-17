package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class FilterAddController implements Initializable{
    @FXML
    TextField filterName;
    @FXML
    ListView<String> newListFilter;
    @FXML
    ListView<String> allListFilter;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeAllFoodList();
    }

    public void addFilterFood(){
        if(!newListFilter.getItems().contains(allListFilter.getSelectionModel().getSelectedItem())){
            newListFilter.getItems().add(allListFilter.getSelectionModel().getSelectedItem());
        }
    }

    public void deleteFilterFood(){
        String deleteItem = newListFilter.getSelectionModel().getSelectedItem();
        newListFilter.getItems().remove(deleteItem);
    }

    public void addFilter(ActionEvent event){
        if(filterName.getText().isEmpty()){

        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setContentText("Are you sure, that you want to add this filter?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                try {
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("dataFiles/filterList.txt",true));
                    bufferedWriter.write(filterName.getText());
                    bufferedWriter.newLine();
                    bufferedWriter.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }


                PrintWriter writer = null;
                try {
                    writer = new PrintWriter("filterList/" + filterName.getText() + ".txt", "UTF-8");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                for(String i : newListFilter.getItems()){
                    writer.println(i);
                }
                writer.close();

                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.close();
            }
        }
    }

    public void initializeAllFoodList(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("dataFiles/foodList.txt"));
            String line = null;
            while((line = bufferedReader.readLine())!= null){
                String[] food = line.split(",");
                allListFilter.getItems().add(food[1]);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
