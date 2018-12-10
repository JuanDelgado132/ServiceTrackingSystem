package servicetrackclient.controllers;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import javafx.scene.Scene;
import serviceclassexception.InvalidIDException;
import servicetrackclient.clientviews.StaffView;
import servicetrackclient.models.StaffModel;
import servicetrackdata.Client;
import servicetrackdata.Service;

public class StaffController implements BaseController{
	private StaffModel staffModel;
	private StaffView staffView;
	
	public StaffController() {
		staffModel = new StaffModel();
		staffView = new StaffView();
	}

	@Override
	public void setupView() {
		staffView.initializeView();
		//Implement Listeners.
		
		//Logout listener
		staffView.setLogOutListener(event -> {
			//Delete the user file.
			staffModel.logOut();
			//Fire logout event.
			MasterController.getMaster().fireEvent("O");
			
		});
		//Create client listener
		staffView.createClientListener(event -> {
			MasterController master = MasterController.getMaster();
			master.fireEvent("NC");
		});
		//View Client Listener
		staffView.viewClientListener(event -> {
			String searchID = staffView.getID();
			Client requestedClient = new Client();
			HashMap<Integer,Service> services = null;
			int id = 0;
			try {
				id = checkSearchBarInput(searchID);
			} catch (InvalidIDException ex) {
				staffView.showDialog(-1, ex.getMessage());
				return;
			}
			requestedClient.setId(id);
			
			try {
				requestedClient = staffModel.requestClient(requestedClient);
				
			} catch (Exception e) {
				staffView.showDialog(-1, "The operation could not be completed, at this time.");
				return;
			}
			if(requestedClient == null) {
				staffView.showDialog(1, "The requested client was not found.");
				return;
			}
			//Method will continue even if there is an error getting services from the server. Since the user may still want to update client info.
			try {
				services = staffModel.getRegisteredService(requestedClient);
			} catch (UnknownHostException e) {
				staffView.showDialog(-1, "Could not connect to server. Ensure server ip address is correct.");
			} catch (ClassNotFoundException e) {
				staffView.showDialog(-1, "Could not load Service class.");
			} catch (IOException e) {
				staffView.showDialog(-1, "Error sending packet to server");
			}
			MasterController master = MasterController.getMaster();
			//master.setRequestedPerson(requestedUser);
			staffModel.writeRequestedObject(requestedClient);
			staffModel.writeRequestedObject(services);
			master.fireEvent("VC");
		});
		//Get non registered services.
		staffView.registerServiceListener(event -> {
			String searchID = staffView.getID();
			int id = 0;
			HashMap<Integer, Service> services = null;
			try {
				id = checkSearchBarInput(searchID);
			} catch (InvalidIDException ex) {
				staffView.showDialog(-1, ex.getMessage());
				return;
			}
			Client requestedClient = new Client();
			requestedClient.setId(id);
			
			try {
				requestedClient = staffModel.requestClient(requestedClient);
				
			} catch (Exception e) {
				staffView.showDialog(-1, "The operation could not be completed, at this time.");
				return;
			}
			if(requestedClient == null) {
				staffView.showDialog(1, "The requested client was not found.");
				return;
			}
			staffModel.writeRequestedObject(requestedClient);
			
			try {
				services = staffModel.getNonRegisteredServices(requestedClient);
			} catch (Exception ex) {
				staffView.showDialog(-1, ex.getMessage());
				return;
			}
			if(staffModel.getFlag() == -1) {
				staffView.showDialog(staffModel.getFlag(), staffModel.getMessage());
				return;
			}
			if(services.size() == 0) {
				staffView.showDialog(1, "The client has all available services registered.");
				return;
			}
			staffModel.writeRequestedObject(services);
			MasterController.getMaster().fireEvent("RS");
		});
		//Export data
		staffView.exportDataListener(event -> {
			
			MasterController.getMaster().fireEvent("ED");
			
		});
		
		
	}
	@Override
	public Scene getViewScene() {
		// TODO Auto-generated method stub
		return staffView.getScene();
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

	@Override
	public void clearTheView() {
		staffView.clearView();
		
	}
	
	
	

}
