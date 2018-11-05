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
 
    @Override public void start(Stage primaryStage) {
    	
    	 try {
	            BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("GUI.fxml"));
	            Scene scene = new Scene(root, 1000, 500);
	            scene.getStylesheets().add(getClass().getResource("Stylesheet.css").toExternalForm());

	            primaryStage.setScene(scene);
	            primaryStage.setTitle("График");

	            primaryStage.show();
	        } 
	        catch(Exception e) {
	            e.printStackTrace();
	        }  
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}