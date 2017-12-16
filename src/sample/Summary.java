package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class Summary implements Initializable {

    @FXML
    TextArea summarytext;
    @FXML
    Button comfirmbutton;
    @FXML
    Button cancelbutton;

    boolean comfirmation = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void comfirmPressed(ActionEvent event){
        comfirmation = true;
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void cancelPressed(ActionEvent event){
        comfirmation = false;
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setSummarytext(ObservableList<FoodListItem> finalizedItems){
        summarytext.appendText("Restaurant\n");
        for(FoodListItem i : finalizedItems){
            summarytext.appendText(new Integer(i.getQuantity()).toString() + i.getName() + new Double(i.getSubtotal()).toString() + "\n");
        }
        summarytext.appendText("\nTotal: " + new Double(getTotal(finalizedItems)).toString());
    }

    public double getTotal(ObservableList<FoodListItem> finalizedItems){
        double total = 0;
        for(FoodListItem i : finalizedItems){
            total += i.getSubtotal();
        }
        return total;
    }

    public boolean getComfirmation(){
        return comfirmation;
    }

}
