/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author vegeg
 */
public class ReportGeneratorController implements Initializable {

    @FXML
    private ComboBox<String> comparisonCombo;
    @FXML
    private PieChart pieChart;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private Button backButton;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> comparison;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        comparisonCombo.getItems().addAll("1", "3", "7", "1");

        comparisonCombo.setValue("1");

        comparison.getItems().addAll("Money", "Food Type");

        comparison.setValue("Money");
        
        datePicker.setValue(LocalDate.now());
        
        barChart.setLegendSide(Side.TOP);
        
        // Changing random data after every 1 second.
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                
            }
        }));
        // Repeat indefinitely until stop() method is called.
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();
        
        XYChart.Series<String, Number> asd = new XYChart.Series<String, Number>();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        barChart.getData().clear();
        for (int i = 0; i <= Integer.parseInt(comparisonCombo.getValue()); i++) {
            File f = new File("C:\\Users\\vegeg\\Documents\\NetBeansProjects\\Restaurant-Ordering-System-BRENZ\\src\\sample\\Log-" + java.sql.Date.valueOf(datePicker.getValue().minusDays(i)) + ".txt");
            if(f.exists() && !f.isDirectory()){
            asd.getData().add(generate(java.sql.Date.valueOf(datePicker.getValue().minusDays(i))));
            pieChartData.add(new PieChart.Data(dateFormat.format(java.sql.Date.valueOf(datePicker.getValue().minusDays(i))), generate1(java.sql.Date.valueOf(datePicker.getValue().minusDays(i)))));

            }
        }
        asd.setName("Revenue");
        barChart.getData().addAll(asd);

        pieChart.setData(pieChartData);
        
        comparisonCombo.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                XYChart.Series<String, Number> asd = new XYChart.Series<String, Number>();
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                
                barChart.getData().clear();
                for (int i = 0; i <= Integer.parseInt(comparisonCombo.getValue()); i++) {
                    File f = new File("C:\\Users\\vegeg\\Documents\\NetBeansProjects\\Restaurant-Ordering-System-BRENZ\\src\\sample\\Log-" + java.sql.Date.valueOf(datePicker.getValue().minusDays(i)) + ".txt");
                    if(f.exists() && !f.isDirectory()){
                    asd.getData().add(generate(java.sql.Date.valueOf(datePicker.getValue().minusDays(i))));
                    pieChartData.add(new PieChart.Data(dateFormat.format(java.sql.Date.valueOf(datePicker.getValue().minusDays(i))), generate1(java.sql.Date.valueOf(datePicker.getValue().minusDays(i)))));
                   
                    }
                }   
                asd.setName("Revenue");
                barChart.getData().addAll(asd);
        
                pieChart.setData(pieChartData);
            }
        });

        comparison.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                XYChart.Series<String, Number> asd = new XYChart.Series<String, Number>();
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                
                barChart.getData().clear();
                for (int i = 0; i <= Integer.parseInt(comparisonCombo.getValue()); i++) {
                    File f = new File("C:\\Users\\vegeg\\Documents\\NetBeansProjects\\Restaurant-Ordering-System-BRENZ\\src\\sample\\Log-" + java.sql.Date.valueOf(datePicker.getValue().minusDays(i)) + ".txt");
                    if(f.exists() && !f.isDirectory()){
                    asd.getData().add(generate(java.sql.Date.valueOf(datePicker.getValue().minusDays(i))));
                    pieChartData.add(new PieChart.Data(dateFormat.format(java.sql.Date.valueOf(datePicker.getValue().minusDays(i))), generate1(java.sql.Date.valueOf(datePicker.getValue().minusDays(i)))));
                   
                    }
                }
                asd.setName("Revenue");
                barChart.getData().addAll(asd);
        
                pieChart.setData(pieChartData);
            }
        });

        datePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                XYChart.Series<String, Number> asd = new XYChart.Series<String, Number>();
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                
                barChart.getData().clear();
                for (int i = 0; i <= Integer.parseInt(comparisonCombo.getValue()); i++) {
                    File f = new File("C:\\Users\\vegeg\\Documents\\NetBeansProjects\\Restaurant-Ordering-System-BRENZ\\src\\sample\\Log-" + java.sql.Date.valueOf(datePicker.getValue().minusDays(i)) + ".txt");
                    if(f.exists() && !f.isDirectory()){
                    asd.getData().add(generate(java.sql.Date.valueOf(datePicker.getValue().minusDays(i))));
                    pieChartData.add(new PieChart.Data(dateFormat.format(java.sql.Date.valueOf(datePicker.getValue().minusDays(i))), generate1(java.sql.Date.valueOf(datePicker.getValue().minusDays(i)))));
                   
                    }
                }
                asd.setName("Revenue");
                barChart.getData().addAll(asd);
        
                pieChart.setData(pieChartData);
            }
        });

    }

    public XYChart.Data generate(Date date) {
        XYChart.Data data = new XYChart.Data();
        BufferedReader read = null;
        
        try {
            read = new BufferedReader(new FileReader("C:\\Users\\vegeg\\Documents\\NetBeansProjects\\Restaurant-Ordering-System-BRENZ\\src\\sample\\Log-" + date.toString() + ".txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportGeneratorController.class.getName()).log(Level.SEVERE, null, ex);
        }

        String mainDateRead;
        String mainDate[] = null;

        try {
            mainDateRead = read.readLine();
            mainDate = mainDateRead.split("x");
        } catch (IOException ex) {
            Logger.getLogger(ReportGeneratorController.class.getName()).log(Level.SEVERE, null, ex);
        }

        double TotalPrice = 0;

        for (int i = 0; i < mainDate.length; i = i + 3) {
            TotalPrice = TotalPrice + new Double(Double.parseDouble(mainDate[i + 1]));
        }
        
        data = new XYChart.Data(date.toString(), TotalPrice);
        return data;
    }
    
    public double generate1(Date date) {
        XYChart.Data data = new XYChart.Data();
        BufferedReader read = null;
        
        try {
            read = new BufferedReader(new FileReader("C:\\Users\\vegeg\\Documents\\NetBeansProjects\\Restaurant-Ordering-System-BRENZ\\src\\sample\\Log-" + date.toString() + ".txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportGeneratorController.class.getName()).log(Level.SEVERE, null, ex);
        }

        String mainDateRead;
        String mainDate[] = null;

        try {
            mainDateRead = read.readLine();
            mainDate = mainDateRead.split("x");
        } catch (IOException ex) {
            Logger.getLogger(ReportGeneratorController.class.getName()).log(Level.SEVERE, null, ex);
        }

        double TotalPrice = 0;

        for (int i = 0; i < mainDate.length; i = i + 3) {
            TotalPrice = TotalPrice + new Double(Double.parseDouble(mainDate[i + 1]));
        }
        
        
        return TotalPrice;
    }

}
