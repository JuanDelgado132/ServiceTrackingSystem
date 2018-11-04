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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;

public class ClientInfoView extends AnchorPane implements BaseView{
	private Label firstName;
	private Label lastName;
	private Label gender;
	private Label birthDay;
	private Label comments;
	private Label id;
	private Label age;
	private Label title;
	
	private TextField firstNameField;
	private TextField lastNameField;
	private TextField ageField;
	private TextField birthDayField;
	private TextField idField;
	private TextArea commentField;
	
	private ToggleGroup genderToggle;
	private RadioButton male;
	private RadioButton female;
	
    private Alert dialog;
	
	private GridPane enterClientInfoPane;
	private BorderPane mainScreen;
	private HBox genderPane;
	private HBox controlPane;
	
	private Button updateClientButton;
	private Button deleteClientButton;
	private Button generateBarcodeButton;
	private Button closeButton;
	
	private FileChooser barcodeFileChooser;
	
	private Scene scene;
	@Override
	public void initializeView() {
		firstName = new Label("First Name: ");
		lastName = new Label("Last Name: ");
		gender = new Label("Gender: ");
		age = new Label("Age: ");
		birthDay = new Label("Enter Birth Day(yyyy-mm-dd): ");
		comments = new Label("Comments");
		id = new Label("ID: ");
		title = new Label("Please Fill Out The Information Below To Add a New Client");
		
		firstNameField = new TextField();
		lastNameField = new TextField();
		ageField = new TextField();
		ageField.setEditable(false);
		idField = new TextField();
		idField.setEditable(false);
		
		birthDayField = new TextField();
		commentField = new TextArea();
		
		
		updateClientButton = new Button("Update Client");
		deleteClientButton = new Button("Delete This Client");
		generateBarcodeButton = new Button("Generate Client Barcode");
		closeButton = new Button("Close Window");
		
		genderToggle = new ToggleGroup();
		male = new RadioButton("Male");
		female = new RadioButton("Female");
		male.setToggleGroup(genderToggle);
		female.setToggleGroup(genderToggle);
		
		genderPane = new HBox(10);
		genderPane.getChildren().addAll(male, female);
		
		controlPane = new HBox(10);
		controlPane.getChildren().addAll(generateBarcodeButton, updateClientButton, deleteClientButton, closeButton);
		controlPane.setAlignment(Pos.BASELINE_RIGHT);
		
		dialog = new Alert(AlertType.NONE);
		dialog.setTitle("Good Neighbor Alert");
		
		barcodeFileChooser = new FileChooser();
		barcodeFileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
		barcodeFileChooser.setTitle("Good Neighbor");
		barcodeFileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		
		enterClientInfoPane = new GridPane();
		enterClientInfoPane.setPadding(new Insets(10.0, 10.0, 10.0, 10.0));
		enterClientInfoPane.setVgap(10);
		enterClientInfoPane.prefWidthProperty().bind(this.widthProperty());
		enterClientInfoPane.add(firstName, 0, 0);
		enterClientInfoPane.add(lastName, 0, 1);
		enterClientInfoPane.add(id, 0, 2);
		enterClientInfoPane.add(gender, 0, 3);
		enterClientInfoPane.add(age, 0, 4);
		enterClientInfoPane.add(birthDay, 0, 5);
		enterClientInfoPane.add(comments, 1, 6);
		
		enterClientInfoPane.add(firstNameField, 1, 0);
		enterClientInfoPane.add(lastNameField, 1, 1);
		enterClientInfoPane.add(idField, 1, 2);
		enterClientInfoPane.add(genderPane, 1, 3);
		enterClientInfoPane.add(ageField, 1, 4);
		enterClientInfoPane.add(birthDayField, 1, 5);
		enterClientInfoPane.add(commentField, 1, 7);
		enterClientInfoPane.add(controlPane, 1, 8);
		
		GridPane.setHgrow(firstNameField, Priority.ALWAYS);
		GridPane.setHgrow(lastNameField, Priority.ALWAYS);
		//GridPane.setHgrow(genderField, Priority.ALWAYS);
		GridPane.setHgrow(birthDayField, Priority.ALWAYS);
		GridPane.setHalignment(comments, HPos.CENTER);
		GridPane.setHalignment(commentField, HPos.LEFT);
		GridPane.setHalignment(controlPane, HPos.RIGHT);
		GridPane.setHalignment(female, HPos.LEFT);
		
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
		
		scene = new Scene(this, 780, 400);
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
	public int getID() {
		return Integer.parseInt(idField.getText());
	}
	
	public String getGender() {
		if(male.isSelected())
			return male.getText();
		else if (female.isSelected())
			return female.getText();
		
		return "";
	}
	public String getComments() {
		return commentField.getText();
	}
	public void setFirstName(String firstName) {
		firstNameField.setText(firstName);
	}
	public void setLastName(String lastName) {
		lastNameField.setText(lastName);
	}
	public void setAge(String age) {
		ageField.setText(age);
	}
	public void setBirthday(String birthDay) {
		birthDayField.setText(birthDay);
	}
	public void setIDField(String id) {
		idField.setText(id);
	}
	public void setGender(String gender) {
		if(gender.equals("Male"))
			male.setSelected(true);
		else
			female.setSelected(true);
	}
	public void setComments(String comments) {
		commentField.setText(comments);
	}
	@Override
	public Scene getViewScene() {
		
		return scene;
	}

	@Override
	public void clearView() {
		// TODO Auto-generated method stub
		firstNameField.clear();
		lastNameField.clear();
		idField.clear();
		male.setSelected(false);
		female.setSelected(false);
		ageField.clear();
		birthDayField.clear();
		commentField.clear();
		dialog.setAlertType(AlertType.NONE);
		
	}
	public void showDialog(int code, String message) {
		if (code == -1)
			dialog.setAlertType(AlertType.ERROR);
		else
			dialog.setAlertType(AlertType.INFORMATION);
		
		dialog.setContentText(message);
		dialog.showAndWait();
	}
	public Alert getDialogConfirmation(String info) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Good Neighbor Alert");
		alert.setContentText("Are you sure you wanted to delete the following user(This operation can not be undone)?" + "\n" + info);
		return alert;
	}
	public File selectSaveFile() {
		return barcodeFileChooser.showSaveDialog(scene.getWindow());
	}
	public void addUpdateClientListener(EventHandler<ActionEvent> event) {
		
		updateClientButton.setOnAction(event);
	}
	public void addDeleteClientListener(EventHandler<ActionEvent> event) {
		deleteClientButton.setOnAction(event);
	}
	public void addGenerateBarcodeListener(EventHandler<ActionEvent> event) {
		generateBarcodeButton.setOnAction(event);
	}
	public void addCloseWindowListener(EventHandler<ActionEvent> event) {
		closeButton.setOnAction(event);
	}

}
