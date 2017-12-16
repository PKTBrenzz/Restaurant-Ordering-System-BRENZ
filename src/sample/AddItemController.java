package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddItemController implements Initializable {
    @FXML
    TextField field1;
    @FXML
    TextField field2;
    @FXML
    TextField idfield;
    @FXML
    ComboBox<String> filterbox;
    @FXML
    ListView<String> filterlistview;

    List<String> categoryList = new ArrayList<String>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeFilterBox();
    }

    public void clear() {
        idfield.clear();
        field1.clear();
        field2.clear();
        filterlistview.getItems().clear();
    }

    public void save(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Are you sure this item want to add to the food list?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            FileWriter fw = null;
            try
            {
                fw = new FileWriter("dataFiles/foodList.txt",true);
                fw.write(idfield.getText() + "," +field1.getText() + "," + field2.getText() + "\n");

                for(String i : filterlistview.getItems()){
                    FileWriter ffw = new FileWriter("filterList/" + i + ".txt", true);
                    ffw.write(field1.getText() + "\n");
                    ffw.close();
                }

                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.close();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                if (fw!=null)
                {
                    fw.close();
                }
            }
        }
        else {
        }
    }

    public void addFoodFilter(){
        if(!filterlistview.getItems().contains(filterbox.getValue())){
            if(filterbox.getValue()!= null){
                filterlistview.getItems().add(filterbox.getValue());
            }
        }

    }

    public void deleteFoodFilter(){
        String deleteItem = filterlistview.getSelectionModel().getSelectedItem();
        filterlistview.getItems().remove(deleteItem);
    }

    public void initializeFilterBox(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("dataFiles/filterList.txt"));
            String line = null;
            while((line = bufferedReader.readLine())!= null){
                filterbox.getItems().add(line);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
