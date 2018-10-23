package servicetrackclient.controllers;
import java.net.ConnectException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import servicetrackclient.ServiceTrackClient;
import servicetrackclient.clientviews.LogInView;
import servicetrackclient.models.LogInModel;
import servicetrackdata.User;

public class LogInController implements BaseController{
	
	private LogInView logInScreen;
	private LogInModel logIn;
	
	public LogInController() {
		logIn = new LogInModel();
		logInScreen = new LogInView();
	}
	
	public LogInController(LogInModel logIn, LogInView logInScreen) {
		
		this.logIn = logIn;
		this.logInScreen = logInScreen;
		
	}
	@Override
	public void setupView() {
		logInScreen.initializeView();
		logInScreen.addLogInButtonListener(event ->{
			String email = logInScreen.getEmail();
			String pass = logInScreen.getPassword();
			String message = null;
			if(email.equalsIgnoreCase("") || pass.equalsIgnoreCase("")) {
				logInScreen.error("The email field or password field may not be blank");
				return;
			}
			try {
				if(logIn.logIn(email, pass)) {
					logIn.writeUser();
					User user = logIn.getUser();
					System.out.println(user.toString());
					logInScreen.clearView();
					if(user.getRole().equalsIgnoreCase("admin"))
						MasterController.getMaster().fireEvent("A");
					else
						MasterController.getMaster().fireEvent("U");
					
					
					
				}
				else {
					if(logIn.getUser() == null)
						message = "Username or password is incorrect. Please try and log in again.";
					logInScreen.error(message);
					logInScreen.clearView();
					
				}
			} catch (ConnectException e) {
				logInScreen.error("The connection to the server could no be established.");
			}
		});
	}
	
	public Scene getViewScene() {
		return logInScreen.getScene();
	}
	
	

}
