package servicetrackclient.clientviews;

import javafx.css.Style;
import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javafx.scene.Scene;
import java.io.File;

/**
 * 
 * @author juand
 * 
 * Will be our log in view.
 *
 */


public class LogInView extends Pane implements BaseView{
	
	private TextField emailField;
	private PasswordField passField;
	private Button logInButton;
	private Label emailLabel;
	private Label passLabel;
	
	
	private GridPane logInCredPane;
	private BorderPane logInScreen;
	
	private Scene scene;
	private Alert errorDialog;
	private Image logo;
	private ImageView logoView;
	
	
	
	public LogInView() {
	
	}
	
	public void initializeView() {
		
		emailField = new TextField();
		passField = new PasswordField();
		
		emailLabel = new Label("Email: ");
		passLabel = new Label("Password: ");
		logInButton = new Button("Log In");
		
		logo = new Image("file:///" + new File("C:\\ServiceTracking\\Client\\images\\good_neighbor.png").getAbsolutePath().replace("\\", "/"));
		logoView = new ImageView();
		
		logoView.setImage(logo);
		logoView.setFitWidth(150);
		logoView.setFitHeight(150);
		logoView.setPreserveRatio(true);
		logoView.setSmooth(true);
		
		
		
		logInCredPane = new GridPane();
		
		logInCredPane.setHgap(10);
		logInCredPane.setVgap(10);
		logInCredPane.add(emailLabel, 0, 0);
		logInCredPane.add(emailField, 1, 0);
		
		logInCredPane.add(passLabel, 0, 1);
		logInCredPane.add(passField, 1, 1);
		logInCredPane.add(logInButton, 1, 2);
		logInCredPane.setId("log-cred");
		
		logInScreen = new BorderPane();
		logInScreen.setId("main-screen");
		logInScreen.setCenter(logInCredPane);
		logInScreen.setTop(logoView);
		GridPane.setHalignment(logInButton, HPos.RIGHT);
		BorderPane.setAlignment(logoView, Pos.CENTER);
		
		getChildren().add(logInScreen);
		scene =  new Scene(this, 425, 400);
		
		File file = new File("C:\\ServiceTracking\\Client\\css\\bootstrap3.css");
		
		scene.getStylesheets().add("file:///" + file.getAbsolutePath().replace("\\", "/"));
		
		scene.getStylesheets().add("file:///" + new File("C:\\ServiceTracking\\Client\\css\\login.css").getAbsolutePath().replace("\\", "/"));
		
	}
	
	public void addLogInButtonListener(EventHandler<ActionEvent> event) {
		
		logInButton.setOnAction( event);
	}
	
	
	
	public Scene getViewScene() {
		
		return scene;
	}
	
	@Override
	public void clearView() {
		emailField.clear();
		passField.clear();
		
		
		
	}
	
	public String getEmail() {
		return emailField.getText();
	}
	public String getPassword() {
		return passField.getText();
	}
	
	public void error(String message) {
		errorDialog = new Alert(AlertType.ERROR);
		errorDialog.setTitle("Log In Error");
		errorDialog.setContentText(message);
		errorDialog.showAndWait();
		
	}


	
	

}
