package servicetrackclient.models;

import java.io.IOException;
import java.net.UnknownHostException;

import servicetrackclient.ServiceTrackClient;
import servicetrackdata.User;

public class CreateUserModel {
	
	private ServiceTrackClient client;
	
	public CreateUserModel() {
		client = new ServiceTrackClient();
	}
	
	public int createUser(String firstName, String lastName, String email, String role, String address, String phoneNumber, String password) throws UnknownHostException, IOException, ClassNotFoundException {
		
		//Random ID as the server assigns a unique id to the users.
		User userToAdd = new User(firstName, lastName, 7000, email, role, password, address, phoneNumber);
		client.addActionCode("NU");
		client.addPerson(userToAdd);
		client.establishConnection();
		client.sendPacketToServer();
		client.receivePacketFromServer();
		
		
		
		client.closeConnection();
		return client.getErrorFlag();
		
	}

	public String getMessage() {
		// TODO Auto-generated method stub
		return client.message();
	}
	
	public String printUserInfo() {
		return client.getPerson().toString();
	}
}
