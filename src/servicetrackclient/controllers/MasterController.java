package servicetrackclient.controllers;



import java.io.File;

import javafx.application.Platform;
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
	private StaffController staff;
	private CreateUserController createUser;
	private CreateClientController createClient;
	private ViewUserController viewUser;
	private ClientInfoController clientInfo;
	private CreateServiceController createService;
	private RegisterServiceController register;
	private ServiceInfoController serviceInfo;
	private ExportDataController exportData;
	private SetupController setup;
	
	//This will be the initial stage.
	private static Stage primaryStage;
	
	//This stage will be used to open a second window.
	private static Stage secondaryStage;
	
	/**
	 * Constructor for the MasterController initializes all objects.
	 */
	private MasterController() {
		
		logIn = new LogInController(new LogInModel(), new LogInView());
		admin = new AdminController();
		staff = new StaffController();
		createUser = new CreateUserController();
		createClient = new CreateClientController();
		viewUser = new ViewUserController();
		clientInfo = new ClientInfoController();
		createService = new CreateServiceController();
		register = new RegisterServiceController();
		serviceInfo = new ServiceInfoController();
		exportData = new ExportDataController();
		setup = new SetupController();
		secondaryStage = new Stage();
		secondaryStage.setTitle("Good Neighbor");
		secondaryStage.getIcons().add(new Image("file:///" + new File("C:\\ServiceTracking\\Client\\images\\good_neighbor.png").getAbsolutePath().replace("\\", "/")));
		secondaryStage.setResizable(false);
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
		primaryStage.getIcons().add(new Image("file:///" + new File(DirectoryStructure.getMainDir() + DirectoryStructure.CLIENT_DIR + DirectoryStructure.CLIENT_IMAGES_DIR + "good_neighbor.png").getAbsolutePath().replace("\\", "/")));
		primaryStage.setOnCloseRequest(event -> {
			if(new File(DirectoryStructure.getLoggedInFile()).exists())
				DirectoryStructure.deleteLoggedInFile();
			//Platform.exit();
	        //System.exit(0);
		});
	}
	/**
	 * Ensures that the second stage will remain tied to the main stage.
	 * @author juand
	 */
	private void setUpSecondStage() {
		clearAllViews();
		secondaryStage.close();
		secondaryStage = new Stage();
		secondaryStage.setTitle("Good Neighbor");
		secondaryStage.getIcons().add(new Image("file:///" + new File(DirectoryStructure.getMainDir() + DirectoryStructure.CLIENT_DIR + DirectoryStructure.CLIENT_IMAGES_DIR + "good_neighbor.png").getAbsolutePath().replace("\\", "/")));
		secondaryStage.setResizable(false);
		if(new File(DirectoryStructure.getClientFile()).exists())
			DirectoryStructure.deleteClientFile();
		if (new File(DirectoryStructure.getUserFile()).exists())
			DirectoryStructure.deleteUserFile();
		if(new File(DirectoryStructure.getServiceFile()).exists())
			DirectoryStructure.deleteServiceFile();
		
		secondaryStage.setOnCloseRequest(event ->{
			//secondaryStage.close();
			setUpSecondStage();
		});
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
		staff.setupView();
		createUser.setupView();
		createClient.setupView();
		viewUser.setupView();
		clientInfo.setupView();
		createService.setupView();
		register.setupView();
		serviceInfo.setupView();
		exportData.setupView();
		setup.setupView();
	}
	public void clearAllViews() {
		logIn.clearTheView();
		admin.clearTheView();
		staff.clearTheView();
		createUser.clearTheView();
		createClient.clearTheView();
		viewUser.clearTheView();
		clientInfo.clearTheView();
		createService.clearTheView();
		register.clearTheView();
		serviceInfo.clearTheView();
	}
	/**
	 * The main part of master controller class.
	 * Here is where each event is fired when a controller needs to open another view.
	 * @param event
	 */
	public void fireEvent(String event) {
		
		switch (event) {
		case "A":
			//close current view. We do not delete the nodes of the view as remaking nodes is a costly operation.
			primaryStage.close();
			showMainView(admin);
			break;
		case "U":
			primaryStage.close();
			showMainView(staff);
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
		//View requested service event.
		case "VS":
			serviceInfo.setUpServiceInfo();
			showSecondaryView(serviceInfo);
			break;
		//Export data view.
		case "ED":
			showSecondaryView(exportData);
			break;
		//Close the app;
		case "CP":
			primaryStage.close();
			Platform.exit();
	        System.exit(0);
	        break;
	    //Setup view
		case "SU":
			primaryStage.close();
			showMainView(setup);
		}
		
	}
	
	public void setRequestedPerson(Person person) {
		requestedPerson = person;
	}
	
	public Person getRequestedPerson() {
		return requestedPerson;
	}
}




