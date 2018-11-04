package Main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class GUIController implements Initializable {

	@FXML Label formula;
	@FXML LineChart<String, Number> chart;
	@FXML NumberAxis y;
	@FXML CategoryAxis x;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        //xAxis.setLabel("x");
        //chart.setTitle("Function");
        XYChart.Series series = new XYChart.Series();
        //series.setName("");
        //populating the series with data
		ChordMethod cm = new ChordMethod();
	    cm.points = new double[] {0,1, 1,3, 3,27, 4,81};
		formula.setText(cm.getFormula());
        for (double i = -5; i<5; i+=1) {
            series.getData().add(new XYChart.Data<String, Number>(Double.toString(i),cm.polynomial(i)));
        }
        try {
        	chart.getData().add(series);
        }
        catch(Exception e) {
            e.printStackTrace();
        }  
        
	}

}
