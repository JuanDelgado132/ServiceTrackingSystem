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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class CreateUserView extends AnchorPane implements BaseView{
	private Label firstName;
	private Label lastName;
	private Label email;
	private Label role;
	private Label address;
	private Label phoneNumber;
	private Label password;
	private Label verifyPassword;
	private Label title;
	
	private TextField firstNameField;
	private TextField lastNameField;
	private TextField emailField;
	private TextField roleField;
	private TextField addressField;
	private TextField phoneNumberField;
	private PasswordField passwordField;
	private PasswordField verifyPasswordField;
	
	private Alert dialog;
	
	private GridPane enterUserInfoPane;
	private BorderPane mainScreen;
	
	private Button addUserButton;
	
	private Scene scene;
	
	public CreateUserView() {
		
	}
	@Override
	public void initializeView() {
		//Initialzie the fields.
		title = new Label("Please Fill Out The Information Below To Add a New User.");
		firstName = new Label("First Name: ");
		lastName = new Label("Last Name: ");
		email = new Label("Email: ");
		role = new Label("Role: ");
		address = new Label("Address: ");
		phoneNumber = new Label("Phone Number: ");
		password = new Label("Password: ");
		verifyPassword = new Label("Verify Password: ");
		
		firstNameField = new TextField();
		lastNameField = new TextField();
		emailField = new TextField();
		roleField = new TextField();
		addressField = new TextField();
		phoneNumberField = new TextField();
		passwordField = new PasswordField();
		verifyPasswordField = new PasswordField();
		
		addUserButton = new Button("Create New User");
		

		
		enterUserInfoPane = new GridPane();
		enterUserInfoPane.setPadding(new Insets(10.0, 10.0, 10.0, 10.0));
		enterUserInfoPane.setVgap(10);
		enterUserInfoPane.prefWidthProperty().bind(this.widthProperty());
		
		mainScreen = new BorderPane();
		mainScreen.setPadding(new Insets(10,10,10,10));
		
		//Add the fields to the gridpane
		enterUserInfoPane.add(firstName, 0, 0);
		enterUserInfoPane.add(lastName, 0, 1);
		enterUserInfoPane.add(email, 0, 2);
		enterUserInfoPane.add(role, 0, 3);
		enterUserInfoPane.add(address, 0, 4);
		enterUserInfoPane.add(phoneNumber, 0, 5);
		enterUserInfoPane.add(password, 0, 6);
		enterUserInfoPane.add(verifyPassword, 0, 7);
		
		enterUserInfoPane.add(firstNameField, 1, 0);
		enterUserInfoPane.add(lastNameField, 1, 1);
		enterUserInfoPane.add(emailField, 1, 2);
		enterUserInfoPane.add(roleField, 1, 3);
		enterUserInfoPane.add(addressField, 1, 4);
		enterUserInfoPane.add(phoneNumberField, 1, 5);
		enterUserInfoPane.add(passwordField, 1, 6);
		enterUserInfoPane.add(verifyPasswordField, 1, 7);
		enterUserInfoPane.add(addUserButton, 1, 8);

		
		//Set GridPane properties
		GridPane.setHgrow(firstNameField, Priority.ALWAYS);
		GridPane.setHgrow(lastNameField, Priority.ALWAYS);
		GridPane.setHgrow(emailField, Priority.ALWAYS);
		GridPane.setHgrow(roleField, Priority.ALWAYS);
		GridPane.setHgrow(addressField, Priority.ALWAYS);
		GridPane.setHgrow(phoneNumberField, Priority.ALWAYS);
		GridPane.setHgrow(passwordField, Priority.ALWAYS);
		GridPane.setHgrow(verifyPasswordField, Priority.ALWAYS);
		GridPane.setHalignment(addUserButton, HPos.RIGHT);
		
		mainScreen.setTop(title);
		mainScreen.setCenter(enterUserInfoPane);
		BorderPane.setAlignment(enterUserInfoPane, Pos.CENTER);
		BorderPane.setAlignment(title, Pos.CENTER);
		
		getChildren().add(mainScreen);
		AnchorPane.setBottomAnchor(mainScreen, 0.0);
		AnchorPane.setTopAnchor(mainScreen, 0.0);
		AnchorPane.setRightAnchor(mainScreen, 0.0);
		AnchorPane.setLeftAnchor(mainScreen, 0.0);
		
		scene = new Scene(this, 500, 400);
		
		scene.getStylesheets().add("file:///" + new File("C:\\ServiceTracking\\Client\\css\\bootstrap3.css").getAbsolutePath().replace("\\", "/"));
	}

	@Override
	public Scene getViewScene() {
		
		return scene;
	}
	
	public String getFirstName() {
		return firstNameField.getText();
	}
	public String getLastName() {
		return lastNameField.getText();
	}
	public String getEmail() {
		return emailField.getText();
	}
	public String getRole() {
		return roleField.getText();
	}
	public String getAddress() {
		return addressField.getText();
	}
	public String getPhoneNumber() {
		return phoneNumber.getText();
	}
	public String getPassword() {
		return passwordField.getText();
	}
	public String getVerifiedPassword() {
		return verifyPasswordField.getText();
	}

	@Override
	public void clearView() {
		firstNameField.clear();
		lastNameField.clear();
		emailField.clear();
		addressField.clear();
		roleField.clear();
		phoneNumberField.clear();
		passwordField.clear();
		verifyPasswordField.clear();
		
	}
	public void addCreateNewUserButtonListener(EventHandler<ActionEvent> event) {
		addUserButton.setOnAction(event);
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

}
