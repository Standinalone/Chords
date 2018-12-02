package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
 
 
public class GUIFX extends Application {
	private static Stage primaryStage;
	private static Stage webStage;
	private static WebView browser;
	public static Stage getPrimaryStage() {
		return primaryStage;
	}
	public static Stage getWebStage() {
		return webStage;
	}
	public static WebView getBrowser() {
		return browser;
	}
    @Override public void start(Stage primaryStage) {
    	this.primaryStage = primaryStage;

    	 try {
	            primaryStage.setTitle("График");
	            BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("GUI.fxml"));
	            Scene scene = new Scene(root, 850, 600);
	            scene.getStylesheets().add(getClass().getResource("Stylesheet.css").toExternalForm());
	            primaryStage.setScene(scene);
	            primaryStage.show();

                ScrollPane root2 = (ScrollPane)FXMLLoader.load(getClass().getResource("WEB.fxml"));
                browser = new WebView();
                root2.setContent(browser);
                webStage = new Stage();
                webStage.setTitle("My New Stage Title");
                webStage.setScene(new Scene(root2, 650, 600));
                //webStage.show();

	        } 
	        catch(Exception e) {
	            e.printStackTrace();
	        }  
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}