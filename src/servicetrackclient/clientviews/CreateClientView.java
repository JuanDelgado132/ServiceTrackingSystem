package servicetrackclient.clientviews;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
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
import javafx.util.StringConverter;
import servicetrackdirectories.DirectoryStructure;

public class CreateClientView extends AnchorPane implements BaseView{
	private Label firstName;
	private Label lastName;
	private Label gender;
	private Label birthDay;
	private Label comments;
	private Label title;
	
	private TextField firstNameField;
	private TextField lastNameField;
	private DatePicker birthDayField; 
	private TextArea commentField;
	
	private ToggleGroup genderToggle;
	private RadioButton male;
	private RadioButton female;
	
    private Alert dialog;
	
	private GridPane enterClientInfoPane;
	private BorderPane mainScreen;
	private HBox genderPane;
	private FlowPane buttonPane;
	
	private Button addClientButton;
	private Button closeButton;
	
	private Scene scene;
	@Override
	public void initializeView() {
		firstName = new Label("First Name: ");
		lastName = new Label("Last Name: ");
		gender = new Label("Gender: ");
		birthDay = new Label("Enter Birth Day: ");
		comments = new Label("Comments");
		title = new Label("Please Fill Out The Information Below To Add a New Client");
		
		firstNameField = new TextField();
		lastNameField = new TextField();
		
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
		
		
		addClientButton = new Button("Create Client");
		closeButton = new Button("Close Window");
		buttonPane = new FlowPane();
		buttonPane.setHgap(10.0);
		buttonPane.setVgap(10.0);
		buttonPane.setPadding(new Insets(10,10,10,10));
		buttonPane.getChildren().addAll(addClientButton, closeButton);
		buttonPane.setAlignment(Pos.BASELINE_RIGHT);
		
		genderToggle = new ToggleGroup();
		male = new RadioButton("Male");
		female = new RadioButton("Female");
		male.setToggleGroup(genderToggle);
		female.setToggleGroup(genderToggle);
		
		genderPane = new HBox(10);
		genderPane.getChildren().addAll(male, female);
		
		dialog = new Alert(AlertType.NONE);
		dialog.setTitle("Good Neighbor Alert");
		dialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		
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
		enterClientInfoPane.add(genderPane, 1, 2);
		enterClientInfoPane.add(birthDayField, 1, 3);
		enterClientInfoPane.add(commentField, 1, 5);
		enterClientInfoPane.add(buttonPane, 1, 6);
		
		GridPane.setHgrow(firstNameField, Priority.ALWAYS);
		GridPane.setHgrow(lastNameField, Priority.ALWAYS);
		//GridPane.setHgrow(genderField, Priority.ALWAYS);
		GridPane.setHgrow(birthDayField, Priority.ALWAYS);
		GridPane.setHalignment(comments, HPos.CENTER);
		GridPane.setHalignment(commentField, HPos.LEFT);
		GridPane.setHalignment(addClientButton, HPos.RIGHT);
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
		
		scene = new Scene(this, 700, 420);
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
		male.setSelected(false);
		female.setSelected(false);
		birthDayField.setValue(null);
		commentField.clear();
		
	}
	public void showDialog(int code, String message) {
		if (code == -1)
			dialog.setAlertType(AlertType.ERROR);
		else
			dialog.setAlertType(AlertType.INFORMATION);
		
		dialog.setContentText(message);
		dialog.showAndWait();
	}
	public void addCreateCleintListener(EventHandler<ActionEvent> event) {
		
		addClientButton.setOnAction(event);
		
	}
	public void closeButtonListener(EventHandler<ActionEvent> event) {
		closeButton.setOnAction(event);
	}

}
