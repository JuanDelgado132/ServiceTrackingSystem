package servicetrackclient.controllers;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import servicetrackclient.clientviews.UserView;
import servicetrackclient.models.UserModel;


public class UserController implements BaseController{
	
	private UserModel userModel;
	private UserView userView;
	
	public UserController() {
		userModel = new UserModel();
		userView = new UserView();
	}
	
	public UserController(UserModel userModel, UserView userView) {
		this.userModel = userModel;
		this.userView = userView;
	}

	@Override
	public void setupView() {
		userView.initializeView();
		//Implement Listeners.
		//Logout function
		userView.setLogOutListener(event ->{
			//Delete session
			userModel.logOut();
			//Fire logout event.
			MasterController.getMaster().fireEvent("O");
			//Clear the view of a user inputs
			userView.clearView();
		});
		
	}

	@Override
	public Scene getViewScene() {
		// TODO Auto-generated method stub
		return userView.getScene();
	}
}
