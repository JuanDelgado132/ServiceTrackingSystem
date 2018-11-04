package servicetrackclient.controllers;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javafx.scene.Scene;
import servicetrackclient.clientviews.AdminView;
import servicetrackclient.models.AdminModel;
import servicetrackdata.Client;
import servicetrackdata.InvalidIDException;
import servicetrackdata.User;
import servicetrackdirectories.DirectoryStructure;
import servicetrackdata.Service;

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
		
		//Logout listener
		adminView.setLogOutListener(event -> {
			//Delete the user file.
			adminModel.logOut();
			//Fire logout event.
			MasterController.getMaster().fireEvent("O");
			//Destroy my view
			adminView.clearView();
		});
		//Create User Listener
		adminView.createUserListener( event -> {
			MasterController master = MasterController.getMaster();
			master.fireEvent("NU");
		});
		//Create client listener
		adminView.createClientListener(event -> {
			MasterController master = MasterController.getMaster();
			master.fireEvent("NC");
		});
		//View User Listener
		adminView.updateUserListener(event -> {
			
			String searchID = adminView.getID();
			int id = 0;
			
			/*if(searchID.length() < 7 || searchID.length() > 7) {
				adminView.showDialog(-1, "The id must be 7 numbers in length");
				return;
			}
			//We parse it and catch the exception thrown in case one of the characters in the string is not a number.
			try {
				id = Integer.parseInt(searchID);
			}
			catch (NumberFormatException ex) {
				adminView.showDialog(-1, "The id must be a 7 digit number, please try again.");
				return;
				
			}*/
			try {
				id = checkSearchBarInput(searchID);
			} catch (InvalidIDException ex) {
				adminView.showDialog(-1, ex.getMessage());
				return;
			}
			User requestedUser = new User();
			requestedUser.setId(id);
			
			try {
				requestedUser = adminModel.requestUser(requestedUser);
				
			} catch (Exception e) {
				adminView.showDialog(-1, "The operation could not be completed, at this time.");
				return;
			}
			if(requestedUser == null) {
				adminView.showDialog(1, "The requested user was not found.");
				return;
			}
			MasterController master = MasterController.getMaster();
			//master.setRequestedPerson(requestedUser);
			adminModel.writeRequestedObject(requestedUser);
			master.fireEvent("VU");
		});
		//View Client Listener
		adminView.viewClientListener(event -> {
			String searchID = adminView.getID();
			int id = 0;
			
			/*if(searchID.length() < 7 || searchID.length() > 7) {
				adminView.showDialog(-1, "The id must be 7 numbers in length");
				return;
			}
			//We parse it and catch the exception thrown in case one of the characters in the string is not a number.
			try {
				id = Integer.parseInt(searchID);
			}
			catch (NumberFormatException ex) {
				adminView.showDialog(-1, "The id must be a 7 digit number, please try again.");
				return;
				
			}*/
			try {
				id = checkSearchBarInput(searchID);
			} catch (InvalidIDException ex) {
				adminView.showDialog(-1, ex.getMessage());
				return;
			}
			Client requestedClient = new Client();
			requestedClient.setId(id);
			
			try {
				requestedClient = adminModel.requestClient(requestedClient);
				
			} catch (Exception e) {
				adminView.showDialog(-1, "The operation could not be completed, at this time.");
				return;
			}
			if(requestedClient == null) {
				adminView.showDialog(1, "The requested client was not found.");
				return;
			}
			MasterController master = MasterController.getMaster();
			//master.setRequestedPerson(requestedUser);
			adminModel.writeRequestedObject(requestedClient);
			master.fireEvent("VC");
		});
		//Create a new service
		adminView.createServiceListener(event ->{
			MasterController.getMaster().fireEvent("NS");
		});
		//Get non registered services.
		adminView.registerServiceListener(event -> {
			String searchID = adminView.getID();
			int id = 0;
			HashMap<Integer, Service> services = null;
			try {
				id = checkSearchBarInput(searchID);
			} catch (InvalidIDException ex) {
				adminView.showDialog(-1, ex.getMessage());
				return;
			}
			Client requestedClient = new Client();
			requestedClient.setId(id);
			
			try {
				requestedClient = adminModel.requestClient(requestedClient);
				
			} catch (Exception e) {
				adminView.showDialog(-1, "The operation could not be completed, at this time.");
				return;
			}
			if(requestedClient == null) {
				adminView.showDialog(1, "The requested client was not found.");
				return;
			}
			adminModel.writeRequestedObject(requestedClient);
			
			try {
				services = adminModel.getNonRegisteredServices(requestedClient);
			} catch (Exception ex) {
				adminView.showDialog(-1, ex.getMessage());
				return;
			}
			if(services.size() == 0) {
				adminView.showDialog(1, "The client has all available services registered.");
				return;
			}
			adminModel.writeRequestedObject(services);
			/*try {
				var read = new ObjectInputStream(new FileInputStream(new File(DirectoryStructure.getServiceFile())));
				boolean cont = true;
				services = (HashMap<Integer,Service>)read.readObject();
				Iterator<Entry<Integer,Service>> it = services.entrySet().iterator();
				while(it.hasNext()) {
					Map.Entry pair = (Map.Entry)it.next();
					System.out.println(pair.getValue().toString());
				}
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			MasterController.getMaster().fireEvent("RS");
		});
		
	}
	@Override
	public Scene getViewScene() {
		// TODO Auto-generated method stub
		return adminView.getScene();
	}
	
	/**
	 * Make sure the number supplied in the id is correct, if not it will throw an exception.
	 * @author juand
	 * @throws InvalidIDException 
	 */
	private int checkSearchBarInput(String searchID) throws InvalidIDException {
		int id = 0;
		if(searchID.equals(""))
			throw new InvalidIDException("ID field can not be blank.");
		if(searchID.length() < 7 || searchID.length() > 7) {
			throw new InvalidIDException("The ID must be 7 digits in length.");
		}
		//We parse it and catch the exception thrown in case one of the characters in the string is not a number.
		try {
			id = Integer.parseInt(searchID);
		}
		catch (NumberFormatException ex) {
			throw new InvalidIDException("The id must be a 7 digit number, please try again.");
		}
		
		return id;
	}
	
	
	

}
