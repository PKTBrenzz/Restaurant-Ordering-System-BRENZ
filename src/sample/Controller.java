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
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {

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
    int tableNum;

    public void deleteTableItem() {
        FoodListItem deleteItem = itemtable.getSelectionModel().getSelectedItem();
        itemtable.getItems().remove(deleteItem);
        setTotal();
    }

    public void checkOutExecute() {

        if (cashAmount.getText().equals("")) {
            errorlabel.setText("Please insert cash");
        } else if (Double.parseDouble(totalfield.getText()) > Double.parseDouble(cashAmount.getText())) {
            errorlabel.setText("ERROR!!! Cash inserted is not enough!");
        } else {
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
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    File yourFile = new File("log/Log-" + dateFormat.format(date) + ".txt");
                    yourFile.createNewFile();
                    BufferedReader readEnd = new BufferedReader(new FileReader(yourFile.getAbsolutePath()));
                    String everything;
                    
                    if((everything = readEnd.readLine()) != null){
                        String split[] = everything.split("x");
                        ArrayList<String> newItems = new ArrayList<String>();
                        boolean xxx = false;
                        boolean found = false;
                        for(FoodListItem i : itemtable.getItems()){
                            boolean foundMatch = false;
                            
                            for(int j=0;j<split.length;j=j+3){
                                if(i.getName().equals(split[j])){
                                    
                                    double data = Double.parseDouble(split[j+1])+i.getQuantity();
                                    System.out.println(data);
                                    split[j+1]=Double.toString(data);
                                    split[j+2]=new Double(i.getSubtotal()/i.getQuantity()).toString();
                                    foundMatch = true;
                                }
                            }
                            
                            if(foundMatch == false){
                                
                                newItems.add(i.getName());
                                newItems.add(new Double(i.getQuantity()).toString());
                                newItems.add(new Double(i.getSubtotal()/i.getQuantity()).toString());
                                found = true;
                            }
                        }

                        BufferedWriter writeEnd = new BufferedWriter(new FileWriter(yourFile.getAbsolutePath()));
                        for(int i=0;i<split.length;i++){
                            writeEnd.write(split[i]);
                            if(i<split.length-1){
                                writeEnd.write("x");
                                System.out.println("1");
                            }
                        }
                        for(int i=0;i<newItems.size();i++){
                            
                            if(found == true && xxx ==false){
                                writeEnd.write("x");
                                System.out.println("2");
                                xxx = true;
                            }
                            
                            writeEnd.write(newItems.get(i).toString());
                            if(i<newItems.size()-1){
                                writeEnd.write("x");
                            }
                        }
                        
                        writeEnd.close();
                    }
                    else{
                        ArrayList<String> newItems = new ArrayList<String>();
                        
                        for(FoodListItem i : itemtable.getItems()){
                            newItems.add(i.getName());
                            newItems.add(new Double(i.getQuantity()).toString());
                            newItems.add(new Double(i.getSubtotal()/i.getQuantity()).toString());
                        }
                        
                        BufferedWriter writeEnd = new BufferedWriter(new FileWriter(yourFile.getAbsolutePath()));
                        
                        for(int x=0;x<newItems.size();x++){
                            
                            writeEnd.write(newItems.get(x).toString());
                            if(x<newItems.size()-1){
                                writeEnd.write("x");
                            }
                        }
                        
                        writeEnd.close();
                    }
                    
                    
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void testpress(ActionEvent event) throws IOException {

        BufferedReader read = new BufferedReader(new FileReader("metadata.txt"));
        BufferedWriter write = new BufferedWriter(new FileWriter("metadataTemp.txt"));
        boolean found = false;
        String line;
        while ((line = read.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            } else if (line.equals("Table " +  new Integer(tableNum).toString())) {
                write.write("Table " + tableNumber.getText().toString() + "\r\n");
                for (FoodListItem i : itemtable.getItems()) {
                    write.write(new Integer(i.getQuantity()).toString() + "x" + i.getName() + "=" + new Double(i.getSubtotal()).toString() + "\r\n");
                    
                }
                found = true;
            } else if (line != null && found == false) {
                write.write(line + "\r\n");
            } else if (line != null) {
                if (line.startsWith("Table")) {
                    found = false;
                    write.write(line + "\r\n");
                }
            }
        }
        read.close();
        write.close();

        BufferedReader readEnd = new BufferedReader(new FileReader("metadataTemp.txt"));
        BufferedWriter writeEnd = new BufferedWriter(new FileWriter("metadata.txt"));

        while ((line = readEnd.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            } else {
                writeEnd.write(line + "\r\n");
            }

        }

        readEnd.close();
        writeEnd.close();

        Parent root = FXMLLoader.load(getClass().getResource("front_page.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
        filterAll.setPrefSize(150, 60);
        filterAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                flowtest.getChildren().clear();
                for (FoodItem i : foods) {
                    Button button = new Button(i.getName());
                    button.setPrefSize(100, 100);
                    button.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                        @Override
                        public void handle(javafx.event.ActionEvent event) {
                            ObservableList<FoodListItem> templist = itemtable.getItems();
                            boolean found = false;
                            for (FoodListItem j : templist) {
                                if (j.getName().equals(i.getName())) {
                                    found = true;
                                    j.setQuantity(j.getQuantity() + 1);
                                    j.setSubtotal(j.getSubtotal()+ i.getPrice());
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
        );
        filterlist.getChildren()
                .add(filterAll);

        for (String i : filters) {
            Button filterButton = new Button(i);
            filterButton.setPrefSize(150, 60);
            filterButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    List<String> filteredFoods = getFilterList(i);

                    flowtest.getChildren().clear();
                    for (FoodItem i : foods) {
                        if (filteredFoods.contains(i.getName())) {
                            Button button = new Button(i.getName());
                            button.setPrefSize(100, 100);
                            button.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                                @Override
                                public void handle(javafx.event.ActionEvent event) {
                                    ObservableList<FoodListItem> templist = itemtable.getItems();
                                    boolean found = false;
                                    for (FoodListItem j : templist) {
                                        if (j.getName().equals(i.getName())) {
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
         
        //Food item button
        for (FoodItem i : foods) {
            Button button = new Button(i.getName());
            button.setPrefSize(100, 100);
            button.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                @Override
                public void handle(javafx.event.ActionEvent event) {
                    ObservableList<FoodListItem> templist = itemtable.getItems();
                    boolean found = false;
                    for (FoodListItem j : templist) {
                        if (j.getName().equals(i.getName())) {
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

    public void setTotal() {
        ObservableList<FoodListItem> templist = itemtable.getItems();
        double total = 0;
        for (FoodListItem i : templist) {
            total += i.getSubtotal();
        }
        totalfield.setText(new Double(total).toString());
    }

    public List<String> getFilterList(String text) {
        List<String> filter = new ArrayList<String>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("filterList/" + text + ".txt"));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
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

    public void getFilterInput() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("dataFiles/filterList.txt"));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                filters.add(line);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void foodItemInput() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("dataFiles/foodList.txt"));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                foods.add(new FoodItem(data[0], data[1], Double.parseDouble(data[2])));
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTableNumber(int tableno) {
        tableNum = tableno;
        BufferedReader read = null;
        try {
            read = new BufferedReader(new FileReader("metadata.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedWriter write = null;
        try {
            write = new BufferedWriter(new FileWriter("metadataTemp.txt"));
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean found = false;
        String line;
        try {
            while ((line = read.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                } else if (line.equals("Table " +  new Integer(tableNum).toString())) {
                    try {
                        write.write(line + "\r\n");
                    } catch (IOException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    found = true;
                } else if (found == true) {
                    if (line.startsWith("Table")) {
                        found = false;
                        try {
                            write.write(line + "\r\n");
                        } catch (IOException ex) {
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else{
                        String foodQuantity[] = line.split("x");
                        String foodName[] = foodQuantity[1].split("=");
                   
                       for (FoodItem i : foods){
                            if(i.getName().equals(foodName[0])){
                                
                                 FoodListItem temp = new FoodListItem(foodName[0], Integer.parseInt(foodQuantity[0]), i.getPrice());
                                 itemtable.getItems().add(temp);
                            }
                        }
                    }
                    }
                else if (found == false) {
                    try {
                        write.write(line + "\r\n");
                    } catch (IOException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            read.close();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            write.close();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        BufferedReader readEnd = null;
        try {
            readEnd = new BufferedReader(new FileReader("metadataTemp.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedWriter writeEnd = null;
        try {
            writeEnd = new BufferedWriter(new FileWriter("metadata.txt"));
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            while ((line = readEnd.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                } else {
                    writeEnd.write(line + "\r\n");
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            readEnd.close();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            writeEnd.close();
//            while ((line = read.readLine()) != null) {
//                if (line.trim().isEmpty()) {
//                    continue;
//                } else if (line.equals("Table " + tableNumber.getText())) {
//                    write.write("Table " + tableNumber.getText().toString() + "\n");
//                    for (FoodListItem i : itemtable.getItems()) {
//                        write.write(new Integer(i.getQuantity()).toString() + " x " + i.getName() + " = " + new Double(i.getSubtotal()).toString() + "\n");
//                        System.out.println(line);
//                    }
//                    found = true;
//                } else if (line != null && found == false) {
//                    write.write(line + "\n");
//                    System.out.println(line);
//                } else if (line != null) {
//                    if (line.equals("Table.*")) {
//                        found = false;
//                        write.write(line + "\n");
//                        System.out.println(line);
//                    }
//                }
//            }
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableNumber.setText(new Integer(tableno).toString());
    }
}
