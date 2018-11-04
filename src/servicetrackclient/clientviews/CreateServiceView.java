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

public class CreateServiceView extends AnchorPane implements BaseView{
	private Label serviceNameLabel;
	private Label serviceDescriptionLabel;
	private Label serviceDaysLabel;
	private Label serviceTimeLabel;
	private Label title;
	
	private TextField serviceNameField;
	private TextField serviceDaysField;
	private TextField serviceTimeField;
	
	private TextArea serviceDescriptionField;
	
	private Button createServiceButton;
	private Button closeButton;
	
	private GridPane fieldPane;
	private BorderPane mainScreen;
	private HBox buttonPane;
	
	private Alert dialog;
	
	private Scene scene;
	
	public CreateServiceView() {
		
	}

	@Override
	public void initializeView() {
		serviceNameLabel = new Label("Service Name: ");
		serviceDescriptionLabel = new Label("Service Description");
		serviceDaysLabel = new Label("Days Available: ");
		serviceTimeLabel = new Label("Service Time");
		title = new Label("Please Fill Out The Information Below To create a new service.");
		
		serviceNameField = new TextField();
		serviceDaysField = new TextField();
		serviceTimeField = new TextField();
		
		serviceDescriptionField = new TextArea();
		
		createServiceButton = new Button("Create Service");
		closeButton = new Button("Close Window");
		
		dialog = new Alert(AlertType.NONE);
		
		buttonPane = new HBox(10);
		buttonPane.getChildren().addAll(createServiceButton, closeButton);
		buttonPane.setAlignment(Pos.BASELINE_RIGHT);
		
		fieldPane = new GridPane();
		fieldPane.setVgap(10.0);
		fieldPane.setPadding(new Insets(10.0,10.0,10.0,10.0));
		fieldPane.add(serviceNameLabel, 0, 0);
		fieldPane.add(serviceDaysLabel, 0, 1);
		fieldPane.add(serviceTimeLabel, 0, 2);
		fieldPane.add(serviceDescriptionLabel, 1, 3);
		
		fieldPane.add(serviceNameField, 1, 0);
		fieldPane.add(serviceDaysField, 1, 1);
		fieldPane.add(serviceTimeField, 1, 2);
		fieldPane.add(serviceDescriptionField, 1, 4);
		fieldPane.add(buttonPane, 1, 5);
		
		GridPane.setHgrow(serviceNameField, Priority.ALWAYS);
		GridPane.setHgrow(serviceDaysField, Priority.ALWAYS);
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
		
		scene = new Scene(this, 600, 420);
		scene.getStylesheets().add("file:///" + new File("C:\\ServiceTracking\\Client\\css\\bootstrap3.css").getAbsolutePath().replace("\\", "/"));
		
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
	@Override
	public Scene getViewScene() {
		return scene;
	}

	@Override
	public void clearView() {
		serviceNameField.clear();
		serviceDaysField.clear();
		serviceTimeField.clear();
		serviceDescriptionField.clear();
	}
	public void showDialog(int code, String message) {
		if (code == -1)
			dialog.setAlertType(AlertType.ERROR);
		else
			dialog.setAlertType(AlertType.INFORMATION);
		
		dialog.setTitle("Good Neighbor Alert");
		dialog.setContentText(message);
		dialog.showAndWait();
	}
	public void addCreateServiceListener(EventHandler<ActionEvent> event) {
		createServiceButton.setOnAction(event);
		
	}
	public void addCloseListener(EventHandler<ActionEvent> event) {
		closeButton.setOnAction(event);
	}

}
