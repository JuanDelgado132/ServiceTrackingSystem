package servicetrackclient.clientviews;

import java.io.File;

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
import javafx.stage.Screen;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class AdminView extends AnchorPane implements BaseView{
	
	private Label searchLabel;
	private TextField searchBar;
	private Button viewInfoButton;
	private Button viewUserButton;
	private Button registerServiceButton;
	private Button addNewServiceButton;
	private Button exportAllDataButton;
	private Button addNewUserButton;
	private Button addNewClientButton;
	private Button viewServiceButton;
	private Button deregisterServiceButton;
	private HBox searchPane;
	private FlowPane optionPane;
	private HBox menuPane;
	private HBox logoViewPane;
	private Scene sc;
	private MenuBar mainMenu;
	private Menu settings;
	private Menu help;
	private Menu logout;
	private MenuItem gettingStarted;
	private MenuItem profile;
	private Group menuGroup;
	private BorderPane mainScreen;
	private Image logo;
	private ImageView logoView;
	private Screen primaryScreen;
	private Label logOutLabel;
	private GridPane mainPane;
	
	private Alert dialog;
	
	public AdminView() {
		
	}

	@Override
	public void initializeView() {
		//Initialize all private variables.
		searchLabel = new Label("Enter user, client id");
		logOutLabel = new Label("Logout");
		searchBar = new TextField();
		addNewUserButton = new Button("Add New User");
		viewUserButton = new Button("View User");
		addNewClientButton = new Button("Add New Client");
		viewInfoButton = new Button("View Client Info");
		registerServiceButton = new Button("Register Service To Client");
		deregisterServiceButton = new Button("Remove Service From Client");
		viewServiceButton = new Button("View Service");
		addNewServiceButton = new Button("Create New Service");
		exportAllDataButton = new Button("Generate Report");
		mainScreen = new BorderPane();
		searchPane = new HBox(10);
		optionPane = new FlowPane();
		optionPane.setHgap(10.0);
		optionPane.setVgap(10.0);
		menuPane = new HBox();
		logoViewPane = new HBox();
		menuGroup = new Group();
		mainMenu = new MenuBar();
		settings = new Menu("Settings");
		help = new Menu("Help");
		profile = new MenuItem("Edit Profile");
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
		
		
		
		
		settings.getItems().add(profile);
		help.getItems().add(gettingStarted);
		
		mainMenu.getMenus().addAll(settings,help,logout);
		menuPane.getChildren().addAll(mainMenu);
		HBox.setHgrow(mainMenu, Priority.ALWAYS);
		menuGroup.getChildren().addAll( logoViewPane, menuPane);
		
		
		
		
		
		//Add the search bar and the label to the searchPane.
		searchPane.getChildren().addAll(searchLabel, searchBar);
		searchPane.setAlignment(Pos.BASELINE_LEFT);
		searchPane.setPadding(new Insets(20,230,0,200));
		HBox.setHgrow(searchBar, Priority.ALWAYS);
		
		optionPane.getChildren().addAll(addNewUserButton, viewUserButton,  addNewClientButton, viewInfoButton, addNewServiceButton, viewServiceButton, registerServiceButton, exportAllDataButton);
		optionPane.setAlignment(Pos.CENTER);
		
		HBox.setHgrow(addNewUserButton, Priority.ALWAYS);
		HBox.setHgrow(viewUserButton, Priority.ALWAYS);
		HBox.setHgrow(addNewClientButton, Priority.ALWAYS);
		HBox.setHgrow(viewInfoButton, Priority.ALWAYS);
		HBox.setHgrow(registerServiceButton, Priority.ALWAYS);
		HBox.setHgrow(addNewServiceButton, Priority.ALWAYS);
		HBox.setHgrow(exportAllDataButton, Priority.ALWAYS);
		
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
			dialog = new Alert(AlertType.ERROR);
		else
			dialog = new Alert(AlertType.INFORMATION);
		
		dialog.setTitle("Good Neighbor Alert");
		dialog.setContentText(message);
		dialog.showAndWait();
	}
	public String getID() {
		return searchBar.getText();
	}
	//TODO implement listeners.
	
	//Log out functionality
	public void setLogOutListener(EventHandler<MouseEvent> logoutEvent) {
		
		logOutLabel.setOnMouseClicked(logoutEvent);
	}
	//Create a new user functionality
	public void createUserListener(EventHandler<ActionEvent> event) {
		addNewUserButton.setOnAction(event);
	}
	//Update a user.
	public void updateUserListener(EventHandler<ActionEvent> event) {
		viewUserButton.setOnAction(event);
	}
	//create client functionality.
	public void createClientListener(EventHandler<ActionEvent> event) {
		addNewClientButton.setOnAction(event);
	}
	//View client
	public void viewClientListener(EventHandler<ActionEvent> event) {
		viewInfoButton.setOnAction(event);
	}
	//Create new service.
	public void createServiceListener(EventHandler<ActionEvent> event) {
		addNewServiceButton.setOnAction(event);
	}
	//view service
	public void viewServiceListener(EventHandler<ActionEvent> event) {
		viewServiceButton.setOnAction(event);
	}
	//Register service button
	public void registerServiceListener(EventHandler<ActionEvent> event) {
		registerServiceButton.setOnAction(event);
	}

}
