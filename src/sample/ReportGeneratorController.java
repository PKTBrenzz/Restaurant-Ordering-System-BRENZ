/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
    private ChoiceBox<String> comparisonCombo;
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
        

        XYChart.Series<String, Number> asd = new XYChart.Series<String, Number>();
        
        comparisonCombo.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                barChart.getData().clear();
                for (int i = 0; i <= Integer.parseInt(comparisonCombo.getValue()); i++) {
                    asd.getData().add(generate(java.sql.Date.valueOf(datePicker.getValue().minusDays(i))));
                }
                asd.setName("Revenue");
                barChart.getData().addAll(asd);
            }
        });

        comparison.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                barChart.getData().clear();
                for (int i = 0; i <= Integer.parseInt(comparisonCombo.getValue()); i++) {
                    asd.getData().add(generate(java.sql.Date.valueOf(datePicker.getValue().minusDays(i))));
                }
                asd.setName("Revenue");
                barChart.getData().addAll(asd);
            }
        });

        datePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                barChart.getData().clear();
                for (int i = 0; i <= Integer.parseInt(comparisonCombo.getValue()); i++) {
                    asd.getData().add(generate(java.sql.Date.valueOf(datePicker.getValue().minusDays(i))));
                }
                asd.setName("Revenue");
                barChart.getData().addAll(asd);
            }
        });

//        final String austria = "Austria";
//        final String brazil = "Brazil";
//        final String france = "France";
//        final String italy = "Italy";
//        final String usa = "USA";
//        xAxis.setLabel("Food");
//        yAxis.setLabel("Revenue");
//
//        XYChart.Series series1 = new XYChart.Series();
//        series1.setName("2003");
//        series1.getData().add(new XYChart.Data(austria, 25601.34));
//        series1.getData().add(new XYChart.Data(brazil, 20148.82));
//        series1.getData().add(new XYChart.Data(france, 10000));
//        series1.getData().add(new XYChart.Data(italy, 35407.15));
//        series1.getData().add(new XYChart.Data(usa, 12000));
//
//        XYChart.Series series2 = new XYChart.Series();
//        series2.setName("2004");
//        series2.getData().add(new XYChart.Data(austria, 57401.85));
//        series2.getData().add(new XYChart.Data(brazil, 41941.19));
//        series2.getData().add(new XYChart.Data(france, 45263.37));
//        series2.getData().add(new XYChart.Data(italy, 117320.16));
//        series2.getData().add(new XYChart.Data(usa, 14845.27));
//
//        XYChart.Series series3 = new XYChart.Series();
//        series3.setName("2005");
//        series3.getData().add(new XYChart.Data(austria, 45000.65));
//        series3.getData().add(new XYChart.Data(brazil, 44835.76));
//        series3.getData().add(new XYChart.Data(france, 18722.18));
//        series3.getData().add(new XYChart.Data(italy, 17557.31));
//        series3.getData().add(new XYChart.Data(usa, 92633.68));
//        barChart.getData().addAll(series1, series2, series3);
        barChart.setLegendSide(Side.TOP);
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("A", 1),
                        new PieChart.Data("B", 2),
                        new PieChart.Data("C", 5));
        pieChart.setData(pieChartData);

        // Changing random data after every 1 second.
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for (XYChart.Series<String, Number> series : barChart.getData()) {
                    for (XYChart.Data<String, Number> data : series.getData()) {
                        Number yValue = data.getYValue();
                        Number randomValue = yValue.doubleValue() * (0.5 + Math.random());
                        data.setYValue(randomValue);
                    }
                }

                for (PieChart.Data series2 : pieChart.getData()) {
                    Number yValue = series2.getPieValue();
                    Number randomValue = yValue.doubleValue() * (0.5 + Math.random());
                    series2.setPieValue((double) randomValue);

                }
            }
        }));
        // Repeat indefinitely until stop() method is called.
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();
    }

    public XYChart.Data generate(Date date) {
        XYChart.Data data = new XYChart.Data();
        BufferedReader read = null;
        System.out.print(date);
        
        try {
            read = new BufferedReader(new FileReader("log/Log-" + date.toString() + ".txt"));
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

}
