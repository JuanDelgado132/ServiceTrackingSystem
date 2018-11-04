package servicetrackclient.controllers;


import java.util.Optional;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import servicetrackclient.clientviews.ViewUserView;
import servicetrackclient.models.ViewUserModel;
import servicetrackdata.User;

public class ViewUserController implements BaseController{
	
	private ViewUserView viewUserView;
	private ViewUserModel viewUserModel;
	
	public ViewUserController() {
		viewUserView = new ViewUserView();
		viewUserModel = new ViewUserModel();
	}

	
	@Override
	public void setupView() {
		// TODO Auto-generated method stub
		viewUserView.initializeView();
		viewUserView.addUpdateUserListener(event -> {
			int result = 0;
			String message = "";
			String pass = viewUserView.getPassword();
			String verPass = viewUserView.getVerifiedPassword();
			
			if(pass.compareTo(verPass) != 0) {
				viewUserView.showDialog(-1, "Passwords do not match");
				return;
			}
			String firstName = viewUserView.getFirstName();
			String lastName = viewUserView.getLastName();
			int id = Integer.parseInt(viewUserView.getUserID());
			String email = viewUserView.getEmail();
			String role = viewUserView.getRole();
			String address = viewUserView.getAddress();
			String phoneNumber = viewUserView.getPhoneNumber();
			
			if(pass.equals("")|| firstName.equals("")|| lastName.equals("") || email.equals("")|| role.equals("")|| address.equals("") || phoneNumber.equals("")) {
				viewUserView.showDialog(-1, "All fields must filled. Please do not leave a field blank.");
				return;
			}
			
			try {
				result = viewUserModel.updateUser(firstName, lastName, id, email, role, pass, address, phoneNumber);
			} catch (Exception e) {
				viewUserView.showDialog(-1, e.getMessage());
				return;
			}
			if(result == -1)
				message = viewUserModel.getMessage();
			else if(result == 1)
				message = viewUserModel.getMessage() + "\n" + viewUserModel.printUserInfo();
			viewUserView.showDialog(result, message);
			viewUserView.clearView();
			
			MasterController.getMaster().fireEvent("C");
			viewUserModel.deleteUserFile();
		});
		
		viewUserView.deleteUserListener(event -> {
			
			Alert alert = viewUserView.deleteConfirm(viewUserModel.readUser());
			Optional<ButtonType> response = alert.showAndWait();
			if(response.isPresent() && response.get() == ButtonType.OK) {
				int success = 0;
				try {
					success = viewUserModel.deleteUser(viewUserModel.readUser());
				} catch (Exception e) {
					viewUserView.showDialog(-1, e.getMessage());
					return;
				}
				viewUserView.showDialog(success, "The user was successfully deleted.");
			}
			else if (response.isPresent() && response.get() == ButtonType.CANCEL)
				return;
			
			viewUserView.clearView();
			MasterController.getMaster().fireEvent("C");
			viewUserModel.deleteUserFile();
			
		});
		
	}

	@Override
	public Scene getViewScene() {
		return viewUserView.getScene();
	}
	public void setUserInfo() {
		User user = viewUserModel.readUser();
		viewUserView.setFirstName(user.getFirstName());
		viewUserView.setLastName(user.getLastName());
		viewUserView.setID(user.getId());
		viewUserView.setEmail(user.getEmail());
		viewUserView.setRole(user.getRole());
		viewUserView.setAddress(user.getAddress());
		viewUserView.setPhoneNumber(user.getPhoneNumber());
		viewUserView.setPassword(user.getPassword());
	}

}
