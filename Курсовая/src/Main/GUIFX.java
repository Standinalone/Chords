package Main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
 
 
public class GUIFX extends Application {
 
    @Override public void start(Stage stage) {
    	

       
        stage.setTitle("Line Chart Samplei");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("x");
        //creating the chart
        final LineChart<Number,Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);
                
        lineChart.setTitle("Function");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        //series.setName("");
        //populating the series with data
        ChordMethod cm = new ChordMethod();
        cm.points = new double[] {0,1,1,3,3,27,4,81};
        
        for (double i = -5; i<5; i+=0.1) {
            series.getData().add(new XYChart.Data(i,cm.polynomial(i)));
        }
                
        
    	BorderPane root;
		try {
			root = (BorderPane)FXMLLoader.load(getClass().getResource("GUI.fxml"));
			root.setRight(lineChart);
	        Scene scene  = new Scene(root,800,600);
	        lineChart.getData().add(series);
	        stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
 
    public static void main(String[] args) {
        launch(args);
    }
}