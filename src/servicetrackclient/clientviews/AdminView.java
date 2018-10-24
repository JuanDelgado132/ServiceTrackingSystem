package servicetrackclient.clientviews;

import java.io.File;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.event.Event;

public class AdminView extends AnchorPane implements BaseView{
	
	private Label searchLabel;
	private TextField searchBar;
	private Button viewInfoButton;
	private Button updateClientInfoButton;
	private Button updateUserInfoButton;
	private Button addServiceButton;
	private Button addNewServiceButton;
	private Button exportAllDataButton;
	private Button addNewUserButton;
	private Button addNewClientButton;
	private HBox searchPane;
	private HBox optionPane;
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
	
	public AdminView() {
		
	}

	@Override
	public void initializeView() {
		//Initialize all private variables.
		searchLabel = new Label("Enter user, client id");
		logOutLabel = new Label("Logout");
		searchBar = new TextField();
		addNewUserButton = new Button("Add New User");
		updateUserInfoButton = new Button("Update User Info");
		addNewClientButton = new Button("Add New Client");
		viewInfoButton = new Button("View Client Info");
		updateClientInfoButton = new Button("Update Client Info");
		addServiceButton = new Button("Add Service To Client");
		addNewServiceButton = new Button("Enter New Service");
		exportAllDataButton = new Button("Generate Report");
		mainScreen = new BorderPane();
		searchPane = new HBox(10);
		optionPane = new HBox(10);
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
		
		optionPane.getChildren().addAll(addNewUserButton, updateUserInfoButton,  addNewClientButton, updateClientInfoButton, viewInfoButton, addServiceButton, addNewServiceButton, exportAllDataButton);
		optionPane.setAlignment(Pos.CENTER);
		
		HBox.setHgrow(addNewUserButton, Priority.ALWAYS);
		HBox.setHgrow(updateUserInfoButton, Priority.ALWAYS);
		HBox.setHgrow(addNewClientButton, Priority.ALWAYS);
		HBox.setHgrow(updateClientInfoButton, Priority.ALWAYS);
		HBox.setHgrow(viewInfoButton, Priority.ALWAYS);
		HBox.setHgrow(addServiceButton, Priority.ALWAYS);
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
		//sc.getStylesheets().add("file:///" + new File("C:\\ServiceTracking\\Client\\css\\main.css").getAbsolutePath().replace("\\", "/"));
		
		
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
	//TODO implement listeners.
	public void setLogOutListener(EventHandler<MouseEvent> logoutEvent) {
		
		logOutLabel.setOnMouseClicked(logoutEvent);
	}
	public void createUserListener(EventHandler<ActionEvent> event) {
		addNewUserButton.setOnAction(event);
	}
	

}
