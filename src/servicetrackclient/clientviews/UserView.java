package servicetrackclient.clientviews;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;

public class UserView extends AnchorPane implements BaseView{
	private Label searchLabel;
	private TextField searchBar;
	private Button viewInfoButton;
	private Button updateClientInfoButton;
	private Button addServiceButton;
	private Button exportAllDataButton;
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
	private Group searchGroup;
	private Group menuGroup;
	private StackPane mainScreen;
	private Image logo;
	private ImageView logoView;
	private Screen primaryScreen;
	private Label logOutLabel;
	
	public UserView() {
		
	}

	@Override
	public void initializeView() {
		//Initialize all private variables.
		searchLabel = new Label("Enter user, client id");
		logOutLabel = new Label("Logout");
		searchBar = new TextField();
		addNewClientButton = new Button("Add New Client");
		viewInfoButton = new Button("View Client Info");
		updateClientInfoButton = new Button("Update Client Info");
		addServiceButton = new Button("Add Service To Client");
		exportAllDataButton = new Button("Generate Report");
		mainScreen = new StackPane();
		searchPane = new HBox(10);
		optionPane = new HBox(10);
		menuPane = new HBox();
		logoViewPane = new HBox();
		searchGroup = new Group();
		menuGroup = new Group();
		mainMenu = new MenuBar();
		settings = new Menu("Settings");
		help = new Menu("Help");
		profile = new MenuItem("Edit Profile");
		gettingStarted = new MenuItem("Getting Started");
		logout = new Menu("", logOutLabel);
		logo = new Image("file:///" + new File("C:\\ServiceTracking\\Client\\images\\good_neighbor.png").getAbsolutePath().replace("\\", "/"));
		logoView = new ImageView();
		primaryScreen = Screen.getPrimary();
		
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
		
		
		
		
		
		//Add the search bar and the label to the searchPane.
		searchPane.getChildren().addAll(searchLabel, searchBar);
		searchPane.setAlignment(Pos.BASELINE_LEFT);
		searchPane.setPadding(new Insets(20,230,0,200));
		HBox.setHgrow(searchBar, Priority.ALWAYS);
		
		optionPane.getChildren().addAll(addNewClientButton, updateClientInfoButton, viewInfoButton, addServiceButton, exportAllDataButton);
		optionPane.setPadding(new Insets(80,50,50,50));
		optionPane.setAlignment(Pos.CENTER);
		HBox.setHgrow(addNewClientButton, Priority.ALWAYS);
		HBox.setHgrow(updateClientInfoButton, Priority.ALWAYS);
		HBox.setHgrow(viewInfoButton, Priority.ALWAYS);
		HBox.setHgrow(addServiceButton, Priority.ALWAYS);
		HBox.setHgrow(exportAllDataButton, Priority.ALWAYS);
		
		searchGroup.getChildren().addAll(searchPane, optionPane);
		menuGroup.getChildren().addAll( logoViewPane, menuPane);
		mainScreen.getChildren().addAll(menuGroup, searchGroup);
		StackPane.setAlignment(searchGroup, Pos.CENTER);
		StackPane.setAlignment(menuGroup, Pos.TOP_LEFT);
		
		
		
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

}
