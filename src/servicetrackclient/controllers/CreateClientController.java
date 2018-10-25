package servicetrackclient.controllers;

import javafx.scene.Scene;
import servicetrackclient.clientviews.CreateClientView;
import servicetrackclient.models.CreateClientModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;  
import java.util.Date; 

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
				Date date =new SimpleDateFormat("yyyy-MM-dd").parse(birthDay);
			} catch (ParseException ex) {
				createClientView.showDialog(-1, "Please enter a date in the following format yyyy-MM-dd.");
				return;
				
			}
			if(!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female")) {
				createClientView.showDialog(-1, "You must enter male or female.");
				return;
			}
			
			try {
				result = createClientModel.createClient(firstName, lastName, gender, birthDay, comments);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				createClientView.showDialog(-1, e.getMessage());
			}
			createClientView.showDialog(result, createClientModel.getMessage() + "\n" + createClientModel. printClientInfo());
			createClientView.clearView();
			
			MasterController master = MasterController.getMaster();
			master.fireEvent("C");
		});
		
	}

	@Override
	public Scene getViewScene() {
		// TODO Auto-generated method stub
		return createClientView.getViewScene();
	}

}
