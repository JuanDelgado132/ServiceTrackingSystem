package servicetrackclient.controllers;

import java.io.IOException;

import javafx.scene.Scene;
import servicetrackclient.clientviews.CreateUserView;
import servicetrackclient.models.CreateUserModel;

public class CreateUserController implements BaseController{
	
	private CreateUserView createUserView;
	private CreateUserModel createUserModel;
	
	public CreateUserController() {
		createUserView = new CreateUserView();
		createUserModel = new CreateUserModel();
	}

	@Override
	public void setupView() {
		createUserView.initializeView();
		createUserView.addCreateNewUserButtonListener(actionEvent ->{
			int result = 0;
			String message = "";
			String pass = createUserView.getPassword();
			String verPass = createUserView.getVerifiedPassword();
			if(pass.compareTo(verPass) != 0) {
				createUserView.showDialog(-1, "Passwords do not match");
				return;
			}
			String firstName = createUserView.getFirstName();
			String lastName = createUserView.getLastName();
			String email = createUserView.getEmail();
			String role = createUserView.getRole();
			String address = createUserView.getAddress();
			String phoneNumber = createUserView.getPhoneNumber();
			
			if(pass.equals("")|| firstName.equals("")|| lastName.equals("") || email.equals("")|| role.equals("")|| address.equals("") || phoneNumber.equals("")) {
				createUserView.showDialog(-1, "All fields must filled. Please do not leave a field blank.");
				return;
			}
			
			/*if(!role.equalsIgnoreCase("admin") && !role.equalsIgnoreCase("staff")) {
				createUserView.showDialog(-1, "The user role must either be staff of admin");
				return;
			}*/
			try {
				result = createUserModel.createUser(firstName, lastName, email, role, address, phoneNumber, pass);
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				createUserView.showDialog(-1, e.getMessage());
				return;
			}
			if(result == -1)
				message = createUserModel.getMessage();
			else if(result == 1)
				message = createUserModel.getMessage() + "\n" + createUserModel.printUserInfo();
			createUserView.showDialog(result, message);
			createUserView.clearView();
			
			MasterController master = MasterController.getMaster();
			master.fireEvent("C");
			
		});
		
	}

	@Override
	public Scene getViewScene() {
		return createUserView.getScene();
	}

}
