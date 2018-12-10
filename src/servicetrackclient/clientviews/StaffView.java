package servicetrackclient.clientviews;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Screen;

public class StaffView extends AnchorPane implements BaseView{
	private Label searchLabel;
	private TextField searchBar;
	private Button viewClientInfoButton;
	private Button registerServiceButton;
	private Button addNewClientButton;
	private HBox searchPane;
	private FlowPane optionPane;
	private HBox menuPane;
	private HBox logoViewPane;
	private Scene sc;
	private MenuBar mainMenu;
	private Menu fileMenu;
	private Menu help;
	private Menu logout;
	private MenuItem gettingStarted;
	private MenuItem profile;
	private MenuItem exportData;
	private Group menuGroup;
	private BorderPane mainScreen;
	private Image logo;
	private ImageView logoView;
	private Screen primaryScreen;
	private Label logOutLabel;
	private GridPane mainPane;
	
	private Alert dialog;
	
	public StaffView() {
		
	}

	@Override
	public void initializeView() {
		//Initialize all private variables.
		searchLabel = new Label("Enter client id: ");
		logOutLabel = new Label("Logout");
		searchBar = new TextField();
		addNewClientButton = new Button("Add New Client");
		viewClientInfoButton = new Button("View Client Info");
		registerServiceButton = new Button("Register Service To Client");
		mainScreen = new BorderPane();
		searchPane = new HBox(10);
		optionPane = new FlowPane();
		optionPane.setHgap(10.0);
		optionPane.setVgap(10.0);
		menuPane = new HBox();
		logoViewPane = new HBox();
		menuGroup = new Group();
		mainMenu = new MenuBar();
		fileMenu = new Menu("File");
		help = new Menu("Help");
		profile = new MenuItem("Edit Profile");
		exportData = new MenuItem("Export data");
		gettingStarted = new MenuItem("Getting Started");
		logout = new Menu("",logOutLabel);
		logo = new Image("file:///" + new File("C:\\ServiceTracking\\Client\\images\\good_neighbor.png").getAbsolutePath().replace("\\", "/"));
		logoView = new ImageView();
		primaryScreen = Screen.getPrimary();
		mainPane = new GridPane();
		
		
		
		logoView.setImage(logo);
		logoView.setFitWidth(250);
		logoView.setFitHeight(250);
		logoView.setPreserveRatio(true);
		logoView.setSmooth(true);
		logoViewPane.getChildren().add(logoView);
		logoViewPane.setAlignment(Pos.CENTER);
		logoViewPane.setPadding(new Insets(50,50,50,50));
		
		dialog = new Alert(AlertType.NONE);
		dialog.setTitle("Good Neighbor Alert");
		dialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		
		
		fileMenu.getItems().addAll(profile, exportData);
		
		help.getItems().add(gettingStarted);
		
		mainMenu.getMenus().addAll(fileMenu,help,logout);
		menuPane.getChildren().addAll(mainMenu);
		HBox.setHgrow(mainMenu, Priority.ALWAYS);
		menuGroup.getChildren().addAll( logoViewPane, menuPane);
		
		
		
		
		
		//Add the search bar and the label to the searchPane.
		searchPane.getChildren().addAll(searchLabel, searchBar);
		searchPane.setAlignment(Pos.BASELINE_LEFT);
		searchPane.setPadding(new Insets(20,230,0,200));
		HBox.setHgrow(searchBar, Priority.ALWAYS);
		
		optionPane.getChildren().addAll(addNewClientButton, viewClientInfoButton, registerServiceButton);
		optionPane.setAlignment(Pos.CENTER);
		
		
		HBox.setHgrow(addNewClientButton, Priority.ALWAYS);
		HBox.setHgrow(viewClientInfoButton, Priority.ALWAYS);
		HBox.setHgrow(registerServiceButton, Priority.ALWAYS);
		
		//Initialize gridpane
		mainPane.add(searchPane, 0, 0);
		mainPane.add(optionPane, 0, 1);
		mainPane.setVgap(10.0);
		GridPane.setHalignment(searchPane, HPos.CENTER);
		GridPane.setHalignment(optionPane, HPos.CENTER);
		
		mainScreen.setTop(menuGroup);
		mainScreen.setCenter(mainPane);
		BorderPane.setAlignment(menuGroup, Pos.CENTER);
		BorderPane.setAlignment(mainPane, Pos.CENTER);
		
		
		
		//Has to be the HBox.
		searchPane.prefWidthProperty().bind(this.widthProperty());
		optionPane.prefWidthProperty().bind(this.widthProperty());
		menuPane.prefWidthProperty().bind(this.widthProperty());
		logoViewPane.prefWidthProperty().bind(this.widthProperty());
		
		this.getChildren().addAll(mainScreen);
		AnchorPane.setBottomAnchor(mainScreen, 0.0);
		AnchorPane.setTopAnchor(mainScreen, 0.0);
		AnchorPane.setRightAnchor(mainScreen, 0.0);
		AnchorPane.setLeftAnchor(mainScreen, 0.0);
				
		
		sc = new Scene(this,primaryScreen.getVisualBounds().getWidth(),primaryScreen.getVisualBounds().getHeight());
		
		
		 
		
		sc.getStylesheets().add("file:///" + new File("C:\\ServiceTracking\\Client\\css\\bootstrap3.css").getAbsolutePath().replace("\\", "/"));
		 
		
	}

	@Override
	public Scene getViewScene() {
		// TODO Auto-generated method stub
		return sc;
	}

	@Override
	public void clearView() {
		searchBar.clear();
		
	}
	public void showDialog(int code, String message) {
		if (code == -1)
			dialog.setAlertType(AlertType.ERROR);
		else
			dialog.setAlertType(AlertType.INFORMATION);
		
		dialog.setContentText(message);
		dialog.showAndWait();
	}
	public String getID() {
		return searchBar.getText();
	}
	//Log out functionality
	public void setLogOutListener(EventHandler<MouseEvent> logoutEvent) {
		
		logOutLabel.setOnMouseClicked(logoutEvent);
	}
	//create client functionality.
	public void createClientListener(EventHandler<ActionEvent> event) {
		addNewClientButton.setOnAction(event);
	}
	//View client
	public void viewClientListener(EventHandler<ActionEvent> event) {
		viewClientInfoButton.setOnAction(event);
	}
	//Register service button
	public void registerServiceListener(EventHandler<ActionEvent> event) {
		registerServiceButton.setOnAction(event);
	}
	//Export data 
	public void exportDataListener(EventHandler<ActionEvent> event) {
		exportData.setOnAction(event);
	}
}
