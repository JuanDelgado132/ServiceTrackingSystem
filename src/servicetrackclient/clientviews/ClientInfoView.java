package servicetrackclient.clientviews;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import servicetrackdata.Service;
import servicetrackdirectories.DirectoryStructure;

public class ClientInfoView extends AnchorPane implements BaseView{
	private Label firstName;
	private Label lastName;
	private Label gender;
	private Label birthDay;
	private Label comments;
	private Label id;
	private Label age;
	private Label title;
	private Label userService;
	
	private TextField firstNameField;
	private TextField lastNameField;
	private TextField ageField;
	private DatePicker birthDayField;
	private TextField idField;
	private TextArea commentField;
	
	private ToggleGroup genderToggle;
	private RadioButton male;
	private RadioButton female;
	
	private ComboBox<Service> services;
	
    private Alert dialog;
	
	private GridPane enterClientInfoPane;
	private BorderPane mainScreen;
	private HBox genderPane;
	private HBox controlPane;
	
	private Button updateClientButton;
	private Button deleteClientButton;
	private Button generateBarcodeButton;
	private Button closeButton;
	private Button useServiceButton;
	
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
		userService = new Label("Select the service to use.");
		
		firstNameField = new TextField();
		lastNameField = new TextField();
		ageField = new TextField();
		ageField.setEditable(false);
		idField = new TextField();
		idField.setEditable(false);
		
		birthDayField = new DatePicker();
		birthDayField.setConverter(new StringConverter<LocalDate>(){
			String pattern = "yyyy-MM-dd";
		     DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

		     {
		    	 birthDayField.setPromptText(pattern.toLowerCase());
		     }

		     @Override 
		     public String toString(LocalDate date) {
		         if (date != null) {
		             return dateFormatter.format(date);
		         } else {
		             return "";
		         }
		     }

		     @Override 
		     public LocalDate fromString(String string) {
		         if (string != null && !string.isEmpty()) {
		             return LocalDate.parse(string, dateFormatter);
		         } else {
		             return null;
		         }
		     }
		});
		commentField = new TextArea();
		
		
		updateClientButton = new Button("Update Client");
		deleteClientButton = new Button("Delete This Client");
		generateBarcodeButton = new Button("Generate Client Barcode");
		useServiceButton = new Button("Use Selected Service");
		closeButton = new Button("Close Window");
		
		genderToggle = new ToggleGroup();
		male = new RadioButton("Male");
		female = new RadioButton("Female");
		male.setToggleGroup(genderToggle);
		female.setToggleGroup(genderToggle);
		
		services = new ComboBox<>();
		services.setMaxHeight(300);
		services.setPlaceholder(new Label("Select a Service to Use"));
		services.setPadding(new Insets(10, 10, 10, 10));
		
		genderPane = new HBox(10);
		genderPane.getChildren().addAll(male, female);
		
		controlPane = new HBox(10);
		controlPane.getChildren().addAll(generateBarcodeButton, useServiceButton, updateClientButton, deleteClientButton, closeButton);
		controlPane.setAlignment(Pos.BASELINE_RIGHT);
		
		dialog = new Alert(AlertType.NONE);
		dialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
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
		enterClientInfoPane.add(userService, 0, 6);
		enterClientInfoPane.add(comments, 1, 7);
		
		enterClientInfoPane.add(firstNameField, 1, 0);
		enterClientInfoPane.add(lastNameField, 1, 1);
		enterClientInfoPane.add(idField, 1, 2);
		enterClientInfoPane.add(genderPane, 1, 3);
		enterClientInfoPane.add(ageField, 1, 4);
		enterClientInfoPane.add(birthDayField, 1, 5);
		enterClientInfoPane.add(services, 1, 6);
		enterClientInfoPane.add(commentField, 1, 8);
		enterClientInfoPane.add(controlPane, 1, 9);
		
		GridPane.setHgrow(firstNameField, Priority.SOMETIMES);
		GridPane.setHgrow(lastNameField, Priority.SOMETIMES);
		//GridPane.setHgrow(genderField, Priority.ALWAYS);
		GridPane.setHgrow(birthDayField, Priority.SOMETIMES);
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
		
		scene = new Scene(this, 960, 600);
		
		scene.getStylesheets().add("file:///" + new File(DirectoryStructure.getMainDir() + "Client\\css\\bootstrap3.css").getAbsolutePath().replace("\\", "/"));
	}
	
	public String getFirstName() {
		return firstNameField.getText();
	}
	
	public String getLastName() {
		return lastNameField.getText();
	}
	
	public String getBirthDay() {
		return birthDayField.getConverter().toString(birthDayField.getValue());
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
	public Service getSelectedService() {
		//services.getSele
		return services.getValue();
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
		birthDayField.setValue(LocalDate.parse(birthDay));
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
	public void setRegisteredService(HashMap<Integer, Service> services) {
		if(services == null) {
			//If no services are registered yet then the combo box is disabled..
			this.services.setDisable(true);
			//Disable the use service button.
			useServiceButton.setDisable(true);
			//No services are available to user, so the method exits.
			return;
		}
		//ArrayList<Service> registeredServices = new ArrayList<>(services.values());
		this.services.getItems().addAll(services.values());
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
		birthDayField.setValue(null);
		commentField.clear();
		dialog.setAlertType(AlertType.NONE);
		
		services.getItems().clear();
		services.setDisable(false);
		useServiceButton.setDisable(false);
		
		
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
	public void addUserServiceListener(EventHandler<ActionEvent> event) {
		useServiceButton.setOnAction(event);
	}

}
