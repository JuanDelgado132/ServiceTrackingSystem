package servicetrackclient.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import servicetrackclient.clientviews.AdminView;
import servicetrackclient.models.AdminModel;

public class AdminController implements BaseController{
	
	private AdminModel adminModel;
	private AdminView adminView;
	
	public AdminController() {
		adminModel = new AdminModel();
		adminView = new AdminView();
	}

	@Override
	public void setupView() {
		adminView.initializeView();
		//Implement Listeners.
		adminView.setLogOutListener(event ->{
			//Delete the user file.
			adminModel.logOut();
			//Fire logout event.
			MasterController.getMaster().fireEvent("O");
			//Destroy my view
			adminView.clearView();
		});
		
	}

	@Override
	public Scene getViewScene() {
		// TODO Auto-generated method stub
		return adminView.getScene();
	}
	
	

}
