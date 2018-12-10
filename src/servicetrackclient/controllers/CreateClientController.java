package servicetrackclient.controllers;

import javafx.scene.Scene;
import servicetrackclient.clientviews.CreateClientView;
import servicetrackclient.models.CreateClientModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;  

public class CreateClientController implements BaseController{
	private CreateClientModel createClientModel;
	private CreateClientView createClientView;

	public CreateClientController() {
		createClientModel = new CreateClientModel();
		createClientView = new CreateClientView();
	}
	@Override
	public void setupView() {
		// TODO Auto-generated method stub
		createClientView.initializeView();
		//Listener to create a client.
		createClientView.addCreateCleintListener(event ->{
			String firstName = createClientView.getFirstName();
			String lastName = createClientView.getLastName();
			String gender = createClientView.getGender();
			String birthDay = createClientView.getBirthDay();
			String comments = createClientView.getComments();
			int result = 0;
			if(firstName.equals("")|| lastName.equals("") ||gender.equals("")|| birthDay.equals("")|| comments.equals("")) {
				createClientView.showDialog(-1, "All fields must filled. Please do not leave a field blank.");
				return;
			}
			try {
				//I used this to ensure a user entered a date in the correct format. This was much simpler that using a regex expression.
				new SimpleDateFormat("yyyy-MM-dd").parse(birthDay);
			} catch (ParseException ex) {
				createClientView.showDialog(-1, "Please enter a date in the following format yyyy-MM-dd.");
				return;
				
			}
			
			try {
				result = createClientModel.createClient(firstName, lastName, gender, birthDay, comments);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				createClientView.showDialog(-1, e.getMessage());
				return;
			}
			if(result == -1) {
				createClientView.showDialog(result, createClientModel.getMessage());
				createClientView.clearView();
				MasterController.getMaster().fireEvent("C");
				return;
			}
			//Get our master controller
			MasterController master = MasterController.getMaster();
			//Notify the user of the success of the operation
			createClientView.showDialog(result, createClientModel.getMessage() + "\n" + createClientModel.printClientInfo());
			//Clear the view so that it can be used again
			createClientView.clearView();
			//Fire an event signaling that the view should close.
			master.fireEvent("C");
			//Write the client file to be used by the client info view
			createClientModel.writeClient();
			//Write the service file to be used by the client info view.
			createClientModel.writeEmptyServiceList();
			//Fire an event to signal the launch of the client info view.
			master.fireEvent("VC");
		});
		
		createClientView.closeButtonListener(event ->{
			MasterController.getMaster().fireEvent("C");
			
		});
		
	}

	@Override
	public Scene getViewScene() {
		// TODO Auto-generated method stub
		return createClientView.getScene();
	}
	@Override
	public void clearTheView() {
		createClientView.clearView();
	}

}
