package servicetrackclient.clientviews;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class CreateClientView extends AnchorPane implements BaseView{
	private Label firstName;
	private Label lastName;
	private Label gender;
	private Label birthDay;
	private Label comments;
	private Label title;
	
	private TextField firstNameField;
	private TextField lastNameField;
	private TextField genderField;
	private TextField birthDayField;
	private TextArea commentField;
	
    private Alert dialog;
	
	private GridPane enterClientInfoPane;
	private BorderPane mainScreen;
	
	private Button addClientButton;
	
	private Scene scene;
	@Override
	public void initializeView() {
		firstName = new Label("First Name: ");
		lastName = new Label("Last Name: ");
		gender = new Label("Gender: ");
		birthDay = new Label("Enter Birth Day(yyyy-mm-dd): ");
		comments = new Label("Comments");
		title = new Label("Please Fill Out The Information Below To Add a New Client");
		
		firstNameField = new TextField();
		lastNameField = new TextField();
		genderField = new TextField();
		birthDayField = new TextField();
		commentField = new TextArea();
		
		addClientButton = new Button("Create Client");
		
		enterClientInfoPane = new GridPane();
		enterClientInfoPane.setPadding(new Insets(10.0, 10.0, 10.0, 10.0));
		enterClientInfoPane.setVgap(10);
		enterClientInfoPane.prefWidthProperty().bind(this.widthProperty());
		enterClientInfoPane.add(firstName, 0, 0);
		enterClientInfoPane.add(lastName, 0, 1);
		enterClientInfoPane.add(gender, 0, 2);
		enterClientInfoPane.add(birthDay, 0, 3);
		enterClientInfoPane.add(comments, 1, 4);
		
		enterClientInfoPane.add(firstNameField, 1, 0);
		enterClientInfoPane.add(lastNameField, 1, 1);
		enterClientInfoPane.add(genderField, 1, 2);
		enterClientInfoPane.add(birthDayField, 1, 3);
		enterClientInfoPane.add(commentField, 1, 5);
		enterClientInfoPane.add(addClientButton, 1, 6);
		
		GridPane.setHgrow(firstNameField, Priority.ALWAYS);
		GridPane.setHgrow(lastNameField, Priority.ALWAYS);
		GridPane.setHgrow(genderField, Priority.ALWAYS);
		GridPane.setHgrow(birthDayField, Priority.ALWAYS);
		GridPane.setHalignment(comments, HPos.CENTER);
		GridPane.setHalignment(commentField, HPos.LEFT);
		GridPane.setHalignment(addClientButton, HPos.RIGHT);
		
		mainScreen = new BorderPane();
		mainScreen.setPadding(new Insets(10,10,10,10));
		
		mainScreen.setTop(title);
		mainScreen.setCenter(enterClientInfoPane);
		BorderPane.setAlignment(enterClientInfoPane, Pos.CENTER);
		BorderPane.setAlignment(title, Pos.CENTER);
		
		getChildren().add(mainScreen);
		AnchorPane.setBottomAnchor(mainScreen, 0.0);
		AnchorPane.setTopAnchor(mainScreen, 0.0);
		AnchorPane.setRightAnchor(mainScreen, 0.0);
		AnchorPane.setLeftAnchor(mainScreen, 0.0);
		
		scene = new Scene(this, 700, 400);
		
		scene.getStylesheets().add("file:///" + new File("C:\\ServiceTracking\\Client\\css\\bootstrap3.css").getAbsolutePath().replace("\\", "/"));
		
	}
	
	public String getFirstName() {
		return firstNameField.getText();
	}
	
	public String getLastName() {
		return lastNameField.getText();
	}
	
	public String getBirthDay() {
		return birthDayField.getText();
	}
	
	public String getGender() {
		return genderField.getText();
	}
	public String getComments() {
		return commentField.getText();
	}
	

	@Override
	public Scene getViewScene() {
		// TODO Auto-generated method stub
		return scene;
	}

	@Override
	public void clearView() {
		// TODO Auto-generated method stub
		firstNameField.clear();
		lastNameField.clear();
		genderField.clear();
		birthDayField.clear();
		commentField.clear();
		
	}
	public void showDialog(int code, String message) {
		if (code == -1)
			dialog = new Alert(AlertType.ERROR);
		else
			dialog = new Alert(AlertType.INFORMATION);
		
		dialog.setTitle("Good Neighbor Alert");
		dialog.setContentText(message);
		dialog.showAndWait();
	}
	public void addCreateCleintListener(EventHandler<ActionEvent> event) {
		
		addClientButton.setOnAction(event);
		
	}

}
