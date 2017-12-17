package sample;

import java.io.BufferedWriter;
import java.io.FileWriter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrontPageController implements Initializable{
    @FXML
    FlowPane tableAllList;

    int tableNo = 10;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        BufferedWriter writefile = null;
//        BufferedWriter write = null;
//        try {
//            write = new BufferedWriter(new FileWriter("C:\\Users\\vegeg\\Documents\\NetBeansProjects\\Restaurant-Ordering-System-BRENZ\\src\\sample\\metadataTemp.txt"));
//        } catch (IOException ex) {
//            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try {
//            writefile = new BufferedWriter(new FileWriter("C:\\Users\\vegeg\\Documents\\NetBeansProjects\\Restaurant-Ordering-System-BRENZ\\src\\sample\\metadata.txt"));
//        } catch (IOException ex) {
//            Logger.getLogger(FrontPageController.class.getName()).log(Level.SEVERE, null, ex);
//        }
;
        for(int i = 1; i <= 10; i++){
            Button tablebutton = new Button("Table " + new Integer(i).toString());
            tablebutton.setPrefSize(100,100);
            int finalI = i;
            tablebutton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ordering.fxml"));
                    Parent root = null;
                    try {
                        root = (Parent) loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage stage = (Stage) ((Node)event.getSource() ).getScene().getWindow();
                    Controller controller = loader.getController();
                    controller.setTableNumber(finalI);
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            });
            tableAllList.getChildren().add(tablebutton);
            
            
//            try {
//                writefile.write("Table " + i + "\r\n");
//            } catch (IOException ex) {
//                Logger.getLogger(FrontPageController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//                System.out.println("hey");
//            
//        }
//        try {
//            writefile.close();
//        } catch (IOException ex) {
//            Logger.getLogger(FrontPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startReport() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReportGenerator.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void startAddFoodItem() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("add_item.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void startAddFilter() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("filter_add.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.showAndWait();
    }
}
