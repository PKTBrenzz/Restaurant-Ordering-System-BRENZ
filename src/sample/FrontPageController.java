package sample;

import java.io.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrontPageController implements Initializable{
    @FXML
    FlowPane tableAllList;
    @FXML
    TextField tableNumberEdit;
    @FXML
    Label errorlabel;

    int tableNo;

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
        getTable();

        for(int i = 1; i <= tableNo; i++){
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
    public void startModifyFoodItem() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("edit_fooditem.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void startEditFilter() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("edit_filter.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void startDeleteFilter() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("delete_filter.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void changeTable(){
        if(tableNumberEdit.getText().isEmpty()){
            errorlabel.setText("Error! Please insert number!");
        }
        else if(!isInteger(tableNumberEdit.getText())){
            errorlabel.setText("Error! Invalid input!");
        }
        else if(Integer.parseInt(tableNumberEdit.getText()) < 1){
            errorlabel.setText("Error! Invalid number!");
        }
        else if(Integer.parseInt(tableNumberEdit.getText()) > 100){
            errorlabel.setText("Error! Too many tables!");
        }
        else{
            errorlabel.setText("");
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("table_no.txt"));
                bufferedWriter.write(tableNumberEdit.getText());
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            tablerefresh();
            updateMetadata();
            updateMetadateTemp();
        }
    }

    public void tablerefresh(){
        tableAllList.getChildren().clear();
        getTable();
        for(int i = 1; i <= tableNo; i++) {

            Button tablebutton = new Button("Table " + new Integer(i).toString());
            tablebutton.setPrefSize(100, 100);
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
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Controller controller = loader.getController();
                    controller.setTableNumber(finalI);
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            });
            tableAllList.getChildren().add(tablebutton);
        }
    }

    public boolean isInteger(String str){
        try{
            Integer.parseInt(str);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public void getTable(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("table_no.txt"));
            tableNo = Integer.parseInt(bufferedReader.readLine());
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateMetadata(){

        try {
            BufferedWriter metadata = new BufferedWriter(new FileWriter("metadata.txt"));

            for(int i = 1; i <= tableNo;i++){
                metadata.write("Table "+ new Integer(i).toString());
                metadata.newLine();
            }
            metadata.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateMetadateTemp(){
        try {
            BufferedWriter metadataTemp = new BufferedWriter(new FileWriter("metadataTemp.txt"));

            for(int i = 1; i <= tableNo;i++){
                metadataTemp.write("Table "+ new Integer(i).toString());
                metadataTemp.newLine();
            }
            metadataTemp.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
