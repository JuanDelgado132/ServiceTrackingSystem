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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import servicetrackdirectories.DirectoryStructure;

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
	private TextField addressField;
	private TextField phoneNumberField;
	private PasswordField passwordField;
	private PasswordField verifyPasswordField;
	
	private ToggleGroup roleToggle;
	private RadioButton admin;
	private RadioButton staff;
	
	private Alert dialog;
	
	private GridPane enterUserInfoPane;
	private BorderPane mainScreen;
	private HBox rolePane;
	private FlowPane buttonPane;
	
	private Button addUserButton;
	private Button closeButton;
	
	
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
		addressField = new TextField();
		phoneNumberField = new TextField();
		passwordField = new PasswordField();
		verifyPasswordField = new PasswordField();
		
		addUserButton = new Button("Create New User");
		closeButton = new Button("Close Window");
		roleToggle = new ToggleGroup();
		admin = new RadioButton("Administrator");
		staff = new RadioButton("Staff");
		admin.setToggleGroup(roleToggle);
		staff.setToggleGroup(roleToggle);
		
		rolePane = new HBox(10);
		rolePane.getChildren().addAll(admin, staff);
		
		buttonPane = new FlowPane();
		buttonPane.setAlignment(Pos.CENTER_RIGHT);
		buttonPane.getChildren().addAll(addUserButton, closeButton);
		
		enterUserInfoPane = new GridPane();
		enterUserInfoPane.setPadding(new Insets(10.0, 10.0, 10.0, 10.0));
		enterUserInfoPane.setVgap(10);
		enterUserInfoPane.prefWidthProperty().bind(this.widthProperty());
		
		mainScreen = new BorderPane();
		mainScreen.setPadding(new Insets(10,10,10,10));
		
		dialog = new Alert(AlertType.NONE);
		dialog.setTitle("Good Neighbor Alert");
		dialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		
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
		enterUserInfoPane.add(rolePane, 1, 3);
		enterUserInfoPane.add(addressField, 1, 4);
		enterUserInfoPane.add(phoneNumberField, 1, 5);
		enterUserInfoPane.add(passwordField, 1, 6);
		enterUserInfoPane.add(verifyPasswordField, 1, 7);
		enterUserInfoPane.add(buttonPane, 1, 8);

		
		//Set GridPane properties
		GridPane.setHgrow(firstNameField, Priority.ALWAYS);
		GridPane.setHgrow(lastNameField, Priority.ALWAYS);
		GridPane.setHgrow(emailField, Priority.ALWAYS);
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
		
		scene = new Scene(this, 500, 420);
		scene.getStylesheets().add("file:///" + new File(DirectoryStructure.getMainDir() + "Client\\css\\bootstrap3.css").getAbsolutePath().replace("\\", "/"));	}

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
		if(admin.isSelected())
			return admin.getText();
		else if(staff.isSelected())
			return staff.getText();
		
		return "";
	}
	public String getAddress() {
		return addressField.getText();
	}
	public String getPhoneNumber() {
		return phoneNumberField.getText();
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
		admin.setSelected(false);
		staff.setSelected(false);
		phoneNumberField.clear();
		passwordField.clear();
		verifyPasswordField.clear();
		
	}
	public void addCreateNewUserButtonListener(EventHandler<ActionEvent> event) {
		addUserButton.setOnAction(event);
	}
	public void addCloseButtonListener(EventHandler<ActionEvent> event) {
		closeButton.setOnAction(event);
	}
	public void showDialog(int code, String message) {
		if (code == -1)
			dialog.setAlertType(AlertType.ERROR);
		else
			dialog.setAlertType(AlertType.INFORMATION);
		
		dialog.setContentText(message);
		dialog.showAndWait();
	}

}
