package servicetrackclient.controllers;



import java.io.File;

import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import servicetrackclient.clientviews.LogInView;
import servicetrackclient.models.LogInModel;
import servicetrackdata.Person;
import servicetrackdata.User;
import servicetrackdirectories.DirectoryStructure;
/**
 * This is the MasterController class. It is in charge of showing the user the correct view. they want to see.
 * @author juand
 *
 */

public final class MasterController {
	
	
	private static MasterController manager;
	//Data
	private Person requestedPerson;
	
	//Controllers
	private LogInController logIn;
	private AdminController admin;
	private UserController user;
	private CreateUserController createUser;
	private CreateClientController createClient;
	private ViewUserController viewUser;
	private ClientInfoController clientInfo;
	private CreateServiceController createService;
	private RegisterServiceController register;
	
	//This will be the initial stage.
	private static Stage primaryStage;
	
	//This stage will be used to open a second window.
	private static Stage secondaryStage;
	
	//Prevent instantiation.
	//Set up all controllers.
	private MasterController() {
		
		logIn = new LogInController(new LogInModel(), new LogInView());
		admin = new AdminController();
		user = new UserController();
		createUser = new CreateUserController();
		createClient = new CreateClientController();
		viewUser = new ViewUserController();
		clientInfo = new ClientInfoController();
		createService = new CreateServiceController();
		register = new RegisterServiceController();
		secondaryStage = new Stage();
		secondaryStage.setTitle("Good Neighbor");
		secondaryStage.getIcons().add(new Image("file:///" + new File("C:\\ServiceTracking\\Client\\images\\good_neighbor.png").getAbsolutePath().replace("\\", "/")));
		secondaryStage.setOnCloseRequest(event ->{
			secondaryStage.close();
			setUpSecondStage();
		});
	}
	
	public static MasterController getMaster() {
		if(manager == null) {
			manager = new MasterController();
		}
		return manager;
	}
	
	public void setPrimaryStage(Stage stage) {
		primaryStage = stage;
		
	}
	protected Stage getSecondaryStage() {
		return secondaryStage;
	}
	public User getUser() {
		return (User) requestedPerson;
	}
	public void setUpPrimaryStage() {
		primaryStage.setTitle("Good Neighbor");
		primaryStage.getIcons().add(new Image("file:///" + new File("C:\\ServiceTracking\\Client\\images\\good_neighbor.png").getAbsolutePath().replace("\\", "/")));
		
	}
	/**
	 * Ensures that the second stage will remain tied to the main stage.
	 * @author juand
	 */
	private void setUpSecondStage() {
		secondaryStage.close();
		secondaryStage = new Stage();
		secondaryStage.setTitle("Good Neighbor");
		secondaryStage.getIcons().add(new Image("file:///" + new File("C:\\ServiceTracking\\Client\\images\\good_neighbor.png").getAbsolutePath().replace("\\", "/")));
		if(new File(DirectoryStructure.getClientFile()).exists())
			DirectoryStructure.deleteClientFile();
		if (new File(DirectoryStructure.getUserFile()).exists())
			DirectoryStructure.deleteUserFile();
		if(new File(DirectoryStructure.getServiceFile()).exists())
			DirectoryStructure.deleteServiceFile();
		secondaryStage.setOnCloseRequest(event ->{
			secondaryStage.close();
			setUpSecondStage();
		});
	}
	public void startLogIn() {
		primaryStage.setScene(logIn.getViewScene());
		primaryStage.setTitle("Good Neighbor");
		primaryStage.getIcons().add(new Image("file:///" + new File("C:\\ServiceTracking\\Client\\images\\good_neighbor.png").getAbsolutePath().replace("\\", "/")));
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}
	
	public void showAdmin() {
		primaryStage.setScene(admin.getViewScene());
		primaryStage.setTitle("Good Neighbor");
		primaryStage.getIcons().add(new Image("file:///" + new File("C:\\ServiceTracking\\Client\\images\\good_neighbor.png").getAbsolutePath().replace("\\", "/")));
		primaryStage.show();
	}
	public void showUser() {
		primaryStage.setScene(user.getViewScene());
		primaryStage.setTitle("Good Neighbor");
		primaryStage.getIcons().add(new Image("file:///" + new File("C:\\ServiceTracking\\Client\\images\\good_neighbor.png").getAbsolutePath().replace("\\", "/")));
		primaryStage.show();
	}
	
	private void showMainView(BaseController main) {
		primaryStage.setScene(main.getViewScene());
		if(main instanceof LogInController)
			primaryStage.setResizable(false);
		else
			primaryStage.setResizable(true);
		primaryStage.show();
	}
	
	
	private void showSecondaryView(BaseController secondary) {
		secondaryStage.initOwner(primaryStage.getScene().getWindow());
		secondaryStage.initModality(Modality.WINDOW_MODAL);
		secondaryStage.setScene(secondary.getViewScene());
		secondaryStage.show();
	}
	/**
	 * This will setup all of our views, so that they can be ready to show once called.
	 * @author juand
	 */
	public void setupAllViews() {
		logIn.setupView();
		admin.setupView();
		user.setupView();
		createUser.setupView();
		createClient.setupView();
		viewUser.setupView();
		clientInfo.setupView();
		createService.setupView();
		register.setupView();
	}
	public void fireEvent(String event) {
		
		switch (event) {
		case "A":
			//close current view. We do not delete the nodes of the view as remaking nodes is a costly operation.
			primaryStage.close();
			showMainView(admin);
			break;
		case "U":
			primaryStage.close();
			showMainView(user);
			break;
		case "O":
			primaryStage.close();
			showMainView(logIn);
			break;
		case "C":
			setUpSecondStage();
			break;
		//Create new user.
		case "NU":
			showSecondaryView(createUser);
			break;
		//Create new client
		case "NC":
			showSecondaryView(createClient);
			break;
		//View requested user
		case "VU":
			viewUser.setUserInfo();
			showSecondaryView(viewUser);
			break;
		//View requested client.
		case "VC":
			clientInfo.setClientInfo();
			showSecondaryView(clientInfo);
			break;
		case "NS":
			showSecondaryView(createService);
			break;
		case "RS":
			register.setViewInfo();
			showSecondaryView(register);
			break;
			
		}
		
	}
	
	public void setRequestedPerson(Person person) {
		requestedPerson = person;
	}
	
	public Person getRequestedPerson() {
		return requestedPerson;
	}
}




