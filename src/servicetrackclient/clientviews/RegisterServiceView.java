package servicetrackclient.clientviews;

import java.io.File;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import servicetrackdata.Service;

public class RegisterServiceView extends AnchorPane implements BaseView {
	
	private Label mainTitle;
	private Label subTitle;
	private Button registerButton;
	private Button closeButton;
	private BorderPane mainScreen;
	private GridPane mainPane;
	private ArrayList<CheckBox> services;
	private FlowPane serviceListPane;
	private HBox buttonPane;
	private Scene scene;
	private Alert dialog;
	
	public RegisterServiceView() {
		
	}

	@Override
	public void initializeView() {
		mainTitle = new Label("Please Select Services To Register");
		subTitle = new Label();
		registerButton = new Button("Register");
		closeButton = new Button("Close");
		services = new ArrayList<>();
		serviceListPane = new FlowPane();
		serviceListPane.setHgap(10.0);
		serviceListPane.setAlignment(Pos.BASELINE_LEFT);
		buttonPane = new HBox(10);
		buttonPane.getChildren().addAll(registerButton, closeButton);
		buttonPane.setAlignment(Pos.BASELINE_RIGHT);
		mainPane = new GridPane();
		mainPane.setPadding(new Insets(10,10,10,10));
		mainPane.add(subTitle, 0, 0);
		mainPane.add(serviceListPane, 0, 1);
		mainPane.add(buttonPane, 0, 2);
		GridPane.setHalignment(subTitle, HPos.CENTER);
		mainScreen = new BorderPane();
		mainScreen.setTop(mainTitle);
		mainScreen.setCenter(mainPane);
		BorderPane.setAlignment(mainTitle, Pos.CENTER);
		BorderPane.setAlignment(mainPane, Pos.CENTER);
		
		dialog = new Alert(AlertType.NONE);
		
		this.getChildren().addAll(mainScreen);
		AnchorPane.setBottomAnchor(mainScreen, 0.0);
		AnchorPane.setTopAnchor(mainScreen, 0.0);
		AnchorPane.setRightAnchor(mainScreen, 0.0);
		AnchorPane.setLeftAnchor(mainScreen, 0.0);
		
		scene = new Scene(this, 600, 420);
		scene.getStylesheets().add("file:///" + new File("C:\\ServiceTracking\\Client\\css\\bootstrap3.css").getAbsolutePath().replace("\\", "/"));
		
		
		
	}
	public void setServices(ArrayList<Service> availableServices) {
		for(int i = 0; i < availableServices.size(); i++) {
			
			services.add(i,new CheckBox(availableServices.get(i).getServiceID() + " " + availableServices.get(i).getServiceName()));
			serviceListPane.getChildren().add(services.get(i));
			
		}
		
	}
	public void setSubTitle(String title) {
		subTitle.setText(title);
	}
	public ArrayList<Integer> getSelectedServices(){
		ArrayList<Integer> selectedServices = new ArrayList<>();
		
		for(int i = 0; i < services.size(); i++) {
			if(services.get(i).isSelected()) {
				
				selectedServices.add(Integer.parseInt(services.get(i).getText().substring(0, 7)));
			}
		}
		return selectedServices;
	}
	@Override
	public Scene getViewScene() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearView() {
		serviceListPane.getChildren().clear();
		subTitle.setText("");
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
	public void registerListener(EventHandler<ActionEvent> event) {
		registerButton.setOnAction(event);
	}
	public void closeListener(EventHandler<ActionEvent> event) {
		closeButton.setOnAction(event);
	}

}
