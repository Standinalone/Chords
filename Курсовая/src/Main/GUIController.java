package Main;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBException;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import Function.Model.AbstractFFunction;
import Function.Model.AbstractGFunction;
import Function.Model.xml.XMLEquation;
import Function.Model.xml.XMLEquation.FileReadException;
import Function.Model.xml.XMLEquation.FileWriteException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.util.converter.IntegerStringConverter;

public class GUIController implements Initializable {
	XMLEquation equation = new XMLEquation("src/Function/Model/xml/samples/TwoRoots.xml");
	private String title;
	@FXML Label formula;
	@FXML LineChart<Number, Number> chart;
	@FXML NumberAxis xAxis;
	@FXML NumberAxis yAxis;
	@FXML TableView tableViewTopics;
	@FXML TextArea textAreaRoots;
	@FXML TextField textFieldLeft;
	@FXML TextField textFieldRight;
	@FXML TextField textFieldDivision;
	@FXML TextField textFieldEps;
	@FXML TextField textFieldF;
	@FXML TextField textFieldG;
	@FXML BorderPane bp;
	public static void main(String[] args) {
		GUIFX.main(args);
	}
    public void doExit(ActionEvent event) {
        Platform.exit();
    }
    public void updateView() {
        textFieldLeft.clear();
        textFieldRight.clear();
        textFieldEps.clear();
        textFieldDivision.clear();
        textFieldF.clear();
        textFieldG.clear();
        textAreaRoots.clear();
    }
    @FXML public void doScreen() {
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("Отчеты"));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("html (*.html)", "*.html"));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Все файлы (*.*)", "*.*"));
        fileChooser.setTitle("Составить отчет");
        File file;
        // Encoding an image (the screen of a BorderPane) to Base64 format and generating report
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        WritableImage image = bp.snapshot(new SnapshotParameters(), null);
        
        if ((file = fileChooser.showSaveDialog(null)) != null) {
            try {
            	ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", os);
            	os.close();
            	String base64 = Base64.getEncoder().encodeToString(os.toByteArray());
            	equation.saveReport(file.getCanonicalPath(), base64);
                showMessage("Отчет сохранен");
            }
            catch (IOException | FileWriteException e) {
                showError("Ошибка записи");
            }
        }
    }
	@FXML public void dataChanged(ActionEvent event) {
		equation.clearEquation();
		
		StringTokenizer st;
		// Parsing coefficient values for f(x)
		st = new StringTokenizer(textFieldF.getText().isEmpty()?textFieldF.getPromptText():textFieldF.getText(),",");
		while (st.hasMoreTokens()) {
			equation.getFFunction().addCoef(Double.parseDouble(st.nextToken()));
			//System.out.print(st.nextToken()+' ');
		}
		System.out.println();
		// Parsing points for g(x)
		st = new StringTokenizer(textFieldG.getText().isEmpty()?textFieldG.getPromptText():textFieldG.getText(),",");
		while (st.hasMoreTokens()) {
			equation.getGFunction().addPoint(Double.parseDouble(st.nextToken()),Double.parseDouble(st.nextToken()));
			//System.out.print(st.nextToken()+' ');
		}
        drawLines();
    }

    @FXML public void doNew(ActionEvent event) {
    	GUIFX.getStage().setTitle(title+" - Новый");
    	equation = new XMLEquation("src/Function/Model/xml/samples/blank.xml");
    	updateView();
    	drawLines();
    }
    public static FileChooser getFileChooser(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("src/Function/Model/xml/samples"));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML-файлы (*.xml)", "*.xml"));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Все файлы (*.*)", "*.*"));
        fileChooser.setTitle(title);
        return fileChooser;
    }
    public static void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(":(");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
    @FXML
    public void doSave(ActionEvent event) {
        FileChooser fileChooser = getFileChooser("Сохранить XML-файл");
        File file;
        if ((file = fileChooser.showSaveDialog(null)) != null) {
            try {
                equation.writeToFile(file.getCanonicalPath());
                showMessage("Сохранено");
            }
            catch (Exception e) {
                showError("Помилка запису в файл");
            }
        }
    }
    public static void showMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
	@FXML
	public void doOpen(ActionEvent event) {
		 FileChooser fileChooser = getFileChooser("Открыть XML-файл");
	        File file;
	        if ((file = fileChooser.showOpenDialog(null)) != null) {
	            try {
	            	GUIFX.getStage().setTitle(title+" - "+file.getName());
	                equation.readFromFile(file.getCanonicalPath());
	                updateView();
	                drawLines();
	            }
	            catch (IOException | FileReadException e) {
	                showError("Файл не найден");
	            }
	        }
	}
	public void drawLines(){
		chart.getData().clear();
		XYChart.Series fX = new XYChart.Series();
        XYChart.Series gX = new XYChart.Series();
        XYChart.Series input = new XYChart.Series();
        fX.setName("График f(x)");
        gX.setName("График g(x)");
        input.setName("Исходные точки g(x)");
        
        
        
		// if textFields have no text sets the parameters to the prompt properties of corresponding fields
		double left = textFieldLeft.getText().isEmpty()?Double.parseDouble(textFieldLeft.getPromptText()):Double.parseDouble(textFieldLeft.getText());
		double right = textFieldRight.getText().isEmpty()?Double.parseDouble(textFieldRight.getPromptText()):Double.parseDouble(textFieldRight.getText());
		int n = textFieldDivision.getText().isEmpty()?Integer.parseInt(textFieldDivision.getPromptText()):Integer.parseInt(textFieldDivision.getText());
		double eps = textFieldEps.getText().isEmpty()?Double.parseDouble(textFieldEps.getPromptText()):Double.parseDouble(textFieldEps.getText()); 
		
		
		List<Double> rootsX = equation.solve(left, right, eps, n).getRoots();
		List<Double> rootsY = new ArrayList<Double>();
		for (Double rootX : rootsX) {
			rootsY.add(equation.getFFunction().y(rootX));
		}
		java.util.Collections.sort(rootsY);
		//System.out.println(rootsY);
		textAreaRoots.setText(rootsX.toString());

		xAxis.setAutoRanging(false);
	    xAxis.setTickUnit(1);
	    yAxis.setAutoRanging(false);
	    yAxis.setTickUnit(1);
	    
		if (rootsX.size()>0) {
		    xAxis.setLowerBound(left);
		    xAxis.setUpperBound(right);
	
		    yAxis.setLowerBound(Math.round((rootsY.get(0)-5)));
		    yAxis.setUpperBound(Math.round(rootsY.get(rootsY.size()-1)+5));
		}
	    
	    
		formula.setText("f(x) = "+equation.getFFunction().getFormula()+"\n"+
				"g(x) = "+equation.getGFunction().getFormula());
		
		String str="";
		for (double i=-10; i<10; i+=0.1) {
			fX.getData().add(new XYChart.Data(i, equation.getFFunction().y(i)));
			gX.getData().add(new XYChart.Data(i, equation.getGFunction().y(i)));
		}
		for (int i = 0; i<equation.getGFunction().getPointsCount(); i++) {
			input.getData().add(new XYChart.Data(equation.getGFunction().getX(i),equation.getGFunction().getY(i)));
			str+=equation.getGFunction().getX(i)+","+equation.getGFunction().getY(i)+", ";
		}
		textFieldG.setText(str.substring(0, str.length() - 2));
		str="";
		for (int i = 0; i<equation.getFFunction().getCoefCount(); i++) {
			str+=equation.getFFunction().getCoef(i)+", ";
		}
		textFieldF.setText(str.substring(0, str.length() - 2));
        try {
        	chart.getData().add(fX);
        	chart.getData().add(gX);
        	chart.getData().add(input);
        }
        catch(Exception e) {
            e.printStackTrace();
        }  

	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		title = GUIFX.getStage().getTitle();
		    
        //defining the axes
//        final NumberAxis xAxis = new NumberAxis();
//        final NumberAxis yAxis = new NumberAxis();
        drawLines();
	}
}
