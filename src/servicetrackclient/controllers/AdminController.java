package servicetrackclient.controllers;


import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import javafx.scene.Scene;
import serviceclassexception.InvalidIDException;
import servicetrackclient.clientviews.AdminView;
import servicetrackclient.models.AdminModel;
import servicetrackdata.Client;
import servicetrackdata.User;
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
			Client requestedClient = new Client();
			HashMap<Integer,Service> services = null;
			int id = 0;
			try {
				id = checkSearchBarInput(searchID);
			} catch (InvalidIDException ex) {
				adminView.showDialog(-1, ex.getMessage());
				return;
			}
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
			//Method will continue even if there is an error getting services from the server. Since the user may still want to update client info.
			try {
				services = adminModel.getRegisteredService(requestedClient);
			} catch (UnknownHostException e) {
				adminView.showDialog(-1, "Could not connect to server. Ensure server ip address is correct.");
			} catch (ClassNotFoundException e) {
				adminView.showDialog(-1, "Could not load Service class.");
			} catch (IOException e) {
				adminView.showDialog(-1, "Error sending packet to server");
			}
			MasterController master = MasterController.getMaster();
			//master.setRequestedPerson(requestedUser);
			adminModel.writeRequestedObject(requestedClient);
			adminModel.writeRequestedObject(services);
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
			if(adminModel.getFlag() == -1) {
				adminView.showDialog(adminModel.getFlag(), adminModel.getMessage());
				return;
			}
			if(services.size() == 0) {
				adminView.showDialog(1, "The client has all available services registered.");
				return;
			}
			adminModel.writeRequestedObject(services);
			MasterController.getMaster().fireEvent("RS");
		});
		adminView.exportDataListener(event -> {
			
			MasterController.getMaster().fireEvent("ED");
			
		});
		//Create the view service scene.
		adminView.viewServiceListener(event->{
			int serviceID = 0;
			try {
				serviceID = checkSearchBarInput(adminView.getID());
			} catch (InvalidIDException ex) {
				adminView.showDialog(-1, ex.getMessage());
			}
			Service requestedService = new Service();
			requestedService.setServiceID(serviceID);
			try {
				requestedService = adminModel.getRequestedService(requestedService);
			} catch (ClassNotFoundException e) {
				adminView.showDialog(-1, "Service class not found. Contact System Administrator.");
				return;
			} catch (IOException e) {
				adminView.showDialog(-1, "Connection to server failed");
				return;
			}
			if(requestedService == null) {
				adminView.showDialog(-1, "The specified service could not be found.");
				return;
			}
			
			adminModel.writeRequestedObject(requestedService);
			MasterController.getMaster().fireEvent("VS");
			adminView.clearView();
			
			
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

	@Override
	public void clearTheView() {
		adminView.clearView();
		
	}
	
	
	

}
