package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class MainMenuController {


    public void startOrder(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ordering.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage = (Stage) ((Node)event.getSource() ).getScene().getWindow();
        Controller controller = loader.getController();
        controller.setTableNumber(10);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void startReport() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("filter_add.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void startAddItem(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("add_item.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.showAndWait();
    }
}
