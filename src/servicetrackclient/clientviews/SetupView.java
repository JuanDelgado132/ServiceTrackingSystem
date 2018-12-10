package servicetrackclient.clientviews;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.DirectoryChooser;

public class SetupView extends AnchorPane implements BaseView{
	private Label ipLabel;
	private Label directoryLabel;
	private Label title;
	private TextField ipField;
	private TextField dirField;
	private DirectoryChooser clientDir;
	private Button createFileButton;
	private Button closeButton;
	private Button chooseDirButton;
	private GridPane mainPane;
	private FlowPane buttonPane;
	private BorderPane mainScreen;
	private Scene scene;
	private Alert dialog;
	
	public SetupView() {
		
	}

	@Override
	public void initializeView() {
		title = new Label("Please enter the info below.");
		ipLabel = new Label("Enter Server IP: ");
		directoryLabel = new Label("Enter client Directory");
		ipField = new TextField();
		dirField = new TextField();
		clientDir = new DirectoryChooser();
		clientDir.setTitle("Good Neighbor");
		clientDir.setInitialDirectory(new File(System.getProperty("user.home")));
		createFileButton = new Button("Create Config File");
		closeButton = new Button("Close Window");
		chooseDirButton = new Button("Choose Directory");
		
		dialog = new Alert(AlertType.NONE);
		dialog.setTitle("Good Neighbor Alert");
		dialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		
		mainPane = new GridPane();
		mainPane.setHgap(10);
		mainPane.setVgap(10);
		mainPane.setAlignment(Pos.CENTER);
		
		mainPane.add(ipLabel, 0, 0);
		mainPane.add(ipField, 1, 0);
		mainPane.add(directoryLabel, 0, 1);
		mainPane.add(chooseDirButton, 1, 1);
		mainPane.add(dirField, 2, 1);
		
		buttonPane = new FlowPane();
		buttonPane.setHgap(10);
		buttonPane.setVgap(10);
		buttonPane.getChildren().addAll(createFileButton, closeButton);
		buttonPane.setAlignment(Pos.CENTER_RIGHT);
		
		mainScreen = new BorderPane();
		mainScreen.setCenter(mainPane);
		mainScreen.setTop(title);
		mainScreen.setBottom(buttonPane);
		BorderPane.setAlignment(buttonPane, Pos.CENTER_RIGHT);
		BorderPane.setAlignment(mainPane, Pos.CENTER);
		BorderPane.setAlignment(title, Pos.CENTER);
		
		this.getChildren().add(mainScreen);
		AnchorPane.setTopAnchor(mainScreen, 0.0);
		AnchorPane.setRightAnchor(mainScreen, 0.0);
		AnchorPane.setBottomAnchor(mainScreen, 0.0);
		AnchorPane.setLeftAnchor(mainScreen, 0.0);
		
		scene = new Scene(this, 500, 420);

		
	}

	@Override
	public Scene getViewScene() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearView() {
		ipField.clear();
		dirField.clear();
		
	}
	public void setDirPath(String path) {
		dirField.setText(path);
	}
	public void chooseDir() {
		File dir = clientDir.showDialog(scene.getWindow());
		//If the user presses cancel of closes the window.
		if(dir == null) {
			showDialog(-1, "No directory was chosen. Please select a valid directory.");
			return;
		}
		dirField.setText(clientDir.showDialog(scene.getWindow()).getAbsolutePath().toString());
	}
	public String getIP() {
		return ipField.getText();
	}
	public String getDir() {
		return dirField.getText();
	}
	public void addChooseDrectoryListener(EventHandler<ActionEvent> event) {
		chooseDirButton.setOnAction(event);
	}
	public void addCreateConfigButtonListener(EventHandler<ActionEvent> event) {
		createFileButton.setOnAction(event);
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
