package Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.IntegerStringConverter;

public class GUIController implements Initializable {

	@FXML Label formula;
	@FXML LineChart<Number, Number> chart;
	@FXML NumberAxis xAxis;
	@FXML NumberAxis yAxis;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
    @FXML public void doAdd(ActionEvent event) {
    }
    private void updateTable() {
//        List<AbstractTopic> list = new ArrayList<>();
//        for (int i = 0; i < subject.topicsCount(); i++) {
//            list.add(subject.getTopic(i));
//        }
//        observableList = FXCollections.observableList(list);
//        tableViewTopics.setItems(observableList);
//        
//        // Вказуємо для колонок зв'язану з ними властивість і механізм редагування
//        // залежно від типу комірок:
//        
//        tableColumnDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
//        tableColumnDate.setCellFactory(TextFieldTableCell.forTableColumn());
//        tableColumnDate.setOnEditCommit(t -> updateDate(t));
//        
//        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
//        tableColumnName.setCellFactory(TextFieldTableCell.forTableColumn());
//        tableColumnName.setOnEditCommit(t -> updateName(t));
//        
//        tableColumnAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
//        tableColumnAmount.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
//        tableColumnAmount.setOnEditCommit(t -> updateAmount(t));
    }
	@Override
	
	public void initialize(URL location, ResourceBundle resources) {
		 xAxis.setAutoRanging(false);
		    xAxis.setLowerBound(-10);
		    xAxis.setUpperBound(10);
		    xAxis.setTickUnit(1);

		    yAxis.setAutoRanging(false);
		    yAxis.setLowerBound(-10);
		    yAxis.setUpperBound(10);
		    yAxis.setTickUnit(1);
		    
		    
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        //xAxis.setLabel("x");
        //chart.setTitle("Function");
        XYChart.Series series = new XYChart.Series();
        XYChart.Series points = new XYChart.Series();
        XYChart.Series lion = new XYChart.Series();
        lion.setName("График функции");
        points.setName("Заданные точки");
        //populating the series with data
		MyFunction cm = new MyFunction();
		MyFunction lio = new MyFunction();
	    lio.points = new double[] {-10,-10, -6,-9, -4,8.5, -3,9, -1,9, 1,8.5, 3,2, 4,-3, 5,-4, 6,-4.5, 8,-4};
	    //lio.points = new double[] {0,1, 1,3, 3,27, 4,81};
	    cm.points = new double[] {0,1, 1,3, 3,27, 4,81};
		formula.setText(lio.getFormula());
		for (int i = 0; i<lio.points.length; i+=2) {
			points.getData().add(new XYChart.Data(lio.points[i], lio.points[i+1]));
		}
        for (double i = -10; i<7; i+=1) {
        	lion.getData().add(new XYChart.Data<Number, Number>(i,lio.polynomial(i)));
        }
        try {
        	chart.getData().add(lion);
        	chart.getData().add(points);
        }
        catch(Exception e) {
            e.printStackTrace();
        }  
        
	}

}
