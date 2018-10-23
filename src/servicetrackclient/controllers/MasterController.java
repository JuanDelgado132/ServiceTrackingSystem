package servicetrackclient.controllers;



import java.io.File;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import servicetrackclient.clientviews.LogInView;
import servicetrackclient.models.LogInModel;
/**
 * This is the MasterController class. It is in charge of showing the user the correct view. they want to see.
 * @author juand
 *
 */

public final class MasterController {
	
	
	private static MasterController manager;
	
	private LogInController logIn;
	private AdminController admin;
	private UserController user;
	
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
	/**
	 * This will setup all of our views, so that they can be ready to show once called.
	 * @author juand
	 */
	public void setupAllViews() {
		logIn.setupView();
		admin.setupView();
		user.setupView();
	}
	/*
	 * create the main menu view.
	 */
	public void closeLogIn() {
		
	}
	
	public void fireEvent(String event) {
		
		switch (event) {
		case "A":
		case "a":
			//close current view. We do not delete the nodes of the view as remaking nodes is a costly operation.
			primaryStage.close();
			showAdmin();
			break;
		case "U":
		case "u":
			primaryStage.close();
			showUser();
			break;
		case "O":
		case "o":
			primaryStage.close();
			startLogIn();
			break;
		}
		
	}
}




