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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import servicetrackdirectories.DirectoryStructure;

public class ServiceInfoView extends AnchorPane implements BaseView{
	
	private Label serviceNameLabel;
	private Label serviceDescriptionLabel;
	private Label serviceDaysLabel;
	private Label serviceTimeLabel;
	private Label title;
	private Label serviceID;
	private Label statusLabel;
	
	private TextField serviceNameField;
	private TextField serviceDaysField;
	private TextField serviceTimeField;
	private TextField idField;
	private TextField statusField;
	
	private TextArea serviceDescriptionField;
	
	private Button updateServiceButton;
	private Button deactivateServiceButton;
	private Button closeButton;
	
	private GridPane fieldPane;
	private BorderPane mainScreen;
	private HBox buttonPane;
	
	private Alert dialog;
	
	private Scene scene;
	
	public ServiceInfoView() {
		
	}
	
	@Override
	public void initializeView() {
		serviceNameLabel = new Label("Service Name: ");
		serviceDescriptionLabel = new Label("Service Description");
		serviceDaysLabel = new Label("Days Available: ");
		serviceTimeLabel = new Label("Service Time");
		serviceID = new Label("Service ID: ");
		statusLabel = new Label("Status: ");
		title = new Label("Please Fill Out The Information Below To create a new service.");
		
		serviceNameField = new TextField();
		serviceDaysField = new TextField();
		serviceTimeField = new TextField();
		idField = new TextField();
		idField.setEditable(false);
		statusField = new TextField();
		statusField.setEditable(false);
		serviceDescriptionField = new TextArea();
		
		updateServiceButton = new Button("Update Service");
		//Name dependent on status so that will be set once we set the values that are from the service.
		deactivateServiceButton = new Button();
		closeButton = new Button("Close Window");
		
		dialog = new Alert(AlertType.NONE);
		dialog.setTitle("Good Neighbor Alert");
		dialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		
		buttonPane = new HBox(10);
		buttonPane.getChildren().addAll(deactivateServiceButton, updateServiceButton, closeButton);
		buttonPane.setAlignment(Pos.BASELINE_RIGHT);
		
		fieldPane = new GridPane();
		fieldPane.setVgap(10.0);
		fieldPane.setPadding(new Insets(10.0,10.0,10.0,10.0));
		fieldPane.add(serviceNameLabel, 0, 0);
		fieldPane.add(serviceID, 0, 1);
		fieldPane.add(serviceDaysLabel, 0, 2);
		fieldPane.add(serviceTimeLabel, 0, 3);
		fieldPane.add(statusLabel, 0, 4);
		fieldPane.add(serviceDescriptionLabel, 1, 5);
		
		fieldPane.add(serviceNameField, 1, 0);
		fieldPane.add(idField, 1, 1);
		fieldPane.add(serviceDaysField, 1, 2);
		fieldPane.add(serviceTimeField, 1, 3);
		fieldPane.add(statusField, 1, 4);
		fieldPane.add(serviceDescriptionField, 1, 6);
		fieldPane.add(buttonPane, 1, 7);
		
		GridPane.setHgrow(serviceNameField, Priority.ALWAYS);
		GridPane.setHgrow(serviceDaysField, Priority.ALWAYS);
		GridPane.setHgrow(idField, Priority.ALWAYS);
		GridPane.setHgrow(serviceTimeField, Priority.ALWAYS);
		GridPane.setHgrow(serviceDescriptionField, Priority.ALWAYS);
		//GridPane.setHalignment(buttonPane, HPos.RIGHT);
		GridPane.setHalignment(serviceDescriptionLabel, HPos.CENTER);
		
		mainScreen = new BorderPane();
		mainScreen.setTop(title);
		mainScreen.setCenter(fieldPane);
		BorderPane.setAlignment(title, Pos.CENTER);
		BorderPane.setAlignment(fieldPane, Pos.CENTER);
		
		getChildren().add(mainScreen);
		AnchorPane.setBottomAnchor(mainScreen, 0.0);
		AnchorPane.setTopAnchor(mainScreen, 0.0);
		AnchorPane.setRightAnchor(mainScreen, 0.0);
		AnchorPane.setLeftAnchor(mainScreen, 0.0);
		
		scene = new Scene(this, 600, 450);
		scene.getStylesheets().add("file:///" + new File(DirectoryStructure.getMainDir() + "Client\\css\\bootstrap3.css").getAbsolutePath().replace("\\", "/"));
		
		
	}

	@Override
	public Scene getViewScene() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setServiceName(String serviceName) {
		serviceNameField.setText(serviceName);
	}
	public void setServiceDescription(String serviceDescription) {
		serviceDescriptionField.setText(serviceDescription);
	}
	public void setServiceDays(String serviceDays) {
		serviceDaysField.setText(serviceDays);
	}
	public void setServiceTime(String serviceTime) {
		serviceTimeField.setText(serviceTime);
	}
	public void setServiceID(int id) {
		idField.setText(String.valueOf(id));
	}
	public void setStatus(int status) {
		String statusMessage;
		if(status == 1) {
			deactivateServiceButton.setText("Deactivate");
			statusMessage = "Active";
		}
		else {
			deactivateServiceButton.setText("Activate");
			statusMessage = "Inactive";
		}
		
		statusField.setText(statusMessage);
	}
	public String getServiceName() {
		return serviceNameField.getText();
	}
	public String getServiceDescription() {
		return serviceDescriptionField.getText();
	}
	public String getServiceDays() {
		return serviceDaysField.getText();	
	}
	public String getServiceTime() {
		return serviceTimeField.getText();
	}
	public int getServiceID() {
		return Integer.valueOf(idField.getText());
	}
	public int getStatus() {
		if(deactivateServiceButton.getText().equals("Deactivate"))
			return 1;
		else 
			return 0;
	}
	@Override
	public void clearView() {
		serviceNameField.clear();
		serviceDaysField.clear();
		serviceTimeField.clear();
		serviceDescriptionField.clear();
		idField.clear();
		statusField.clear();
		
	}
	
	public void showDialog(int code, String message) {
		if(code == -1)
			dialog.setAlertType(AlertType.ERROR);
		else
			dialog.setAlertType(AlertType.INFORMATION);
		dialog.setContentText(message);
		dialog.showAndWait();
	}
	
	public void addUpdateButtonListener(EventHandler<ActionEvent> event) {
		updateServiceButton.setOnAction(event);
	}
	public void addDeactivateOrActivateButtonListener(EventHandler<ActionEvent> event) {
		deactivateServiceButton.setOnAction(event);
	}
	public void closeButtonListener(EventHandler<ActionEvent> event) {
		closeButton.setOnAction(event);
	}

}
