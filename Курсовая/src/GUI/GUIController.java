package GUI;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import Function.Model.xml.EquationData;
import Function.Model.xml.EquationData.Coefs.Coef;
import Function.Model.xml.EquationData.Points.XYCoef;
import Function.Model.xml.XMLEquation;
import Function.Model.xml.XMLEquation.FileReadException;
import Function.Model.xml.XMLEquation.FileWriteException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.util.converter.DoubleStringConverter;

public class GUIController implements Initializable {
	XMLEquation equation = new XMLEquation("src/Function/Model/xml/samples/FourRoots.xml");
	private String title;
	private ObservableList<Coef> observableListF;
	private ObservableList<XYCoef> observableListG;
	@FXML Label formula;
	@FXML LineChart<Number, Number> chart;
	@FXML NumberAxis xAxis;
	@FXML NumberAxis yAxis;
	@FXML TableView<XYCoef> tableViewPoints;
	@FXML TableView<Coef> tableViewCoefs;
	@FXML TextArea textAreaRoots;
	@FXML TextField textFieldLeft;
	@FXML TextField textFieldRight;
	@FXML TextField textFieldDivision;
	@FXML TextField textFieldEps;
	@FXML TextField textFieldAddCoef;
	@FXML TextField textFieldAddPointX;
	@FXML TextField textFieldAddPointY;
	@FXML TableColumn<EquationData.Coefs.Coef, Double> tableColumnCoef;
	@FXML TableColumn<EquationData.Points.XYCoef, Double> tableColumnX;
	@FXML TableColumn<EquationData.Points.XYCoef, Double> tableColumnY;
	@FXML BorderPane bp;
	public static void main(String[] args) {
		GUIFX.main(args);
	}
	@FXML public void doAbout(ActionEvent event) {
		showMessage("Курсовая работа. 2 курс\nПоиск корней методом хорд");
	}
    public void doExit(ActionEvent event) {
        Platform.exit();
    }
    public void updateView() {
        textFieldLeft.clear();
        textFieldRight.clear();
        textFieldEps.clear();
        textFieldDivision.clear();
        textAreaRoots.clear();
        updateCoefTable();
        updatePointsTable();
    }
    @FXML public void doAddCoef() {
    	equation.getFFunction().addCoef(Double.valueOf(textFieldAddCoef.getText()));
    	textFieldAddCoef.setText(textFieldAddCoef.getPromptText());
    	updateCoefTable();
    	drawLines();
    }
    private void updateCoefTable() {
        List<EquationData.Coefs.Coef> list = new ArrayList<>();
        for (int i = 0; i < equation.getFFunction().getCoefCount(); i++) {
            list.add(equation.getData().getCoefs().getCoef().get(i));
        }
        observableListF = FXCollections.observableList(list);
        tableViewCoefs.setItems(observableListF);
        
        tableColumnCoef.setCellValueFactory(new PropertyValueFactory<>("Value"));
        tableColumnCoef.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        tableColumnCoef.setOnEditCommit(t -> updateCoef(t));
    }
    private void updateCoef(CellEditEvent<EquationData.Coefs.Coef, Double> t) {
        // Checking if a new coefficient value doens't equal to null else sets it to 0.0
        EquationData.Coefs.Coef f = t.getTableView().getItems().get(t.getTablePosition().getRow());
    	if (t.getNewValue()!=null) {
	        f.setValue(t.getNewValue());
    	}
    	else
    	{
	        f.setValue(0);
    	}
        updateCoefTable();
        equation.clearRoots();
        drawLines();
    }
    private void updateX(CellEditEvent<EquationData.Points.XYCoef, Double> t) {
        EquationData.Points.XYCoef g = t.getTableView().getItems().get(t.getTablePosition().getRow());
        // Checking if a new X value doens't equal to null else removes the whole point and updates data
        if (t.getNewValue()!=null) {
        	boolean lock=true;
        	for (int i =0; i<equation.getGFunction().getPointsCount(); i++) {
        		if (equation.getGFunction().getX(i)==t.getNewValue()) lock=false;
        	}
	        if (lock) g.setX(t.getNewValue());
    	}
    	else
    	{
    		equation.getGFunction().removePoint(t.getTablePosition().getRow());
    	}
    	updatePointsTable();
        equation.clearRoots();
        drawLines();
    }
    private void updateY(CellEditEvent<EquationData.Points.XYCoef, Double> t) {
        EquationData.Points.XYCoef g = t.getTableView().getItems().get(t.getTablePosition().getRow());
        // Checking if a new Y value doens't equal to null else removes the whole point and updates data
    	if (t.getNewValue()!=null) {
	        g.setY(t.getNewValue());
    	}
    	else
    	{
    		equation.getGFunction().removePoint(t.getTablePosition().getRow());
    	}
    	updatePointsTable();
        equation.clearRoots();
        drawLines();
    	
    }
    private void updatePointsTable() {
        List<EquationData.Points.XYCoef> list = new ArrayList<>();
        for (int i = 0; i < equation.getGFunction().getPointsCount(); i++) {
            list.add(equation.getData().getPoints().getXYCoef().get(i));
        }
        observableListG = FXCollections.observableList(list);
        tableViewPoints.setItems(observableListG);
        
        // Setting corresponding parameters to columns
        tableColumnX.setCellValueFactory(new PropertyValueFactory<>("X"));
        tableColumnX.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        tableColumnX.setOnEditCommit(t -> updateX(t));
        
        tableColumnY.setCellValueFactory(new PropertyValueFactory<>("y"));
        tableColumnY.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        tableColumnY.setOnEditCommit(t -> updateY(t));
    }
    @FXML public void doAddPoint() {
    	// Boolean variable to prevent from creating a point with existing X value
    	boolean lock=true;
    	for (int i=0; i<equation.getGFunction().getPointsCount(); i++) {
    		if (equation.getGFunction().getX(i)==Double.parseDouble(textFieldAddPointX.getText())) lock=false;
    	}
        
    	if(lock&&!textFieldAddPointX.getText().equals("")&&!textFieldAddPointY.getText().equals("")) {
			equation.getGFunction().addPoint(Double.parseDouble(textFieldAddPointX.getText()),Double.parseDouble(textFieldAddPointY.getText()));
			textFieldAddPointX.setText(textFieldAddPointX.getPromptText());
			textFieldAddPointY.setText(textFieldAddPointY.getPromptText());
	    	updatePointsTable();
	    	drawLines();
    	}
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
        // Encoding image (the screen of a LineChart) to Base64 format and generating report
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        WritableImage image = chart.snapshot(new SnapshotParameters(), null);
        
        if ((file = fileChooser.showSaveDialog(null)) != null) {
            try {
            	ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", os);
            	os.close();
            	String base64 = Base64.getEncoder().encodeToString(os.toByteArray());
            	equation.saveReport(file.getCanonicalPath(), base64);
                showMessage("Отчет сохранен");
                // Creating confirmation dialog to show the report
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Подтверждение");
                alert.setHeaderText("");
                alert.setContentText("Открыть отчет?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    showReport(file);
                }
            }
            catch (IOException | FileWriteException e) {
                showError("Ошибка записи");
            }
        }
    }
    private void showReport(File file) {
    	// Getting Browser and WebView from GUIFX class and opening the WebView
        GUIFX.getBrowser().getEngine().load(file.toURI().toString());
        GUIFX.getWebStage().show();
    }
	@FXML public void doNew(ActionEvent event) {
    	GUIFX.getPrimaryStage().setTitle(title+" - Новый");
    	equation = new XMLEquation("src/Function/Model/xml/samples/blank.xml");
    	updateView();
    	chart.getData().clear();
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
	            	GUIFX.getPrimaryStage().setTitle(title+" - "+file.getName());
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
		equation.clearRoots();
		chart.getData().clear();
		XYChart.Series fX = new XYChart.Series();
        XYChart.Series gX = new XYChart.Series();
        XYChart.Series input = new XYChart.Series();
        fX.setName("График f(x)");
        gX.setName("График g(x)");
        input.setName("Исходные точки g(x)");
        
		// if textFields have no text sets the parameters to the prompt properties of the corresponding fields
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
		String result="";
		for (Double root : rootsX) {
			result+=(new String().format("%.2f       %.2f\n",root, equation.getFFunction().y(root)));
		}
		textAreaRoots.setText(result);

		// Setting appropriate ticks and boundaries for the Axis
		xAxis.setAutoRanging(false);
	    yAxis.setAutoRanging(false);
		if (rootsX.size()>0) {
		    xAxis.setLowerBound(left);
		    xAxis.setUpperBound(right);
		    yAxis.setLowerBound(Math.round((rootsY.get(0)-5)));
		    yAxis.setUpperBound(Math.round(rootsY.get(rootsY.size()-1)+5));
		}
	    xAxis.setTickUnit(1);
	    yAxis.setTickUnit(Math.round((yAxis.getUpperBound()-yAxis.getLowerBound())/5));
	    
	    // Setting the formula field
		formula.setText("f(x) = "+equation.getFFunction().getFormula()+"\n"+
				"g(x) = "+equation.getGFunction().getFormula());

		for (double i=left; i<right; i+=0.1) {
			fX.getData().add(new XYChart.Data(i, equation.getFFunction().y(i)));
			gX.getData().add(new XYChart.Data(i, equation.getGFunction().y(i)));
		}
		for (int i = 0; i<equation.getGFunction().getPointsCount(); i++) {
			input.getData().add(new XYChart.Data(equation.getGFunction().getX(i),equation.getGFunction().getY(i)));
		}
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
		title = GUIFX.getPrimaryStage().getTitle();
		updateCoefTable();
		updatePointsTable();
        drawLines();
	}
}
