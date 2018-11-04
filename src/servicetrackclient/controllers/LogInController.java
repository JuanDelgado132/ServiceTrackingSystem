package servicetrackclient.controllers;
import java.net.ConnectException;


import javafx.scene.Scene;
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
				logInScreen.showDialog("The email field or password field may not be blank");
				return;
			}
			try {
				if(logIn.logIn(email, pass)) {
					logIn.writeUser();
					User user = logIn.getUser();
					logInScreen.clearView();
					if(user.getRole().equalsIgnoreCase("Administrator"))
						MasterController.getMaster().fireEvent("A");
					else
						MasterController.getMaster().fireEvent("U");
				}
				else {
					if(logIn.getUser() == null)
						message = "Username or password is incorrect. Please try and log in again.";
					logInScreen.showDialog(message);
					logInScreen.clearView();
					
				}
			} catch (ConnectException ex) {
				logInScreen.showDialog("The connection to the server could no be established.");
			}
		});
	}
	
	public Scene getViewScene() {
		return logInScreen.getScene();
	}
	
	

}
