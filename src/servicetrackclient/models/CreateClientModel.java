package servicetrackclient.models;

import java.io.IOException;

import servicetrackclient.ServiceTrackClient;
import servicetrackdata.Client;

public class CreateClientModel {

	private ServiceTrackClient client;
	
	public CreateClientModel() {
		client = new ServiceTrackClient();
	}
	public int createClient(String firstName, String lastName,  String gender, String birthDay, String comments) throws Exception {
		Client clientToAdd = new Client(firstName, lastName, 789456, gender, birthDay, comments);
		client.addActionCode("NC");
		client.addPerson(clientToAdd);
		try {
			client.establishConnection();
			client.sendPacketToServer();
			client.receivePacketFromServer();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			client.closeConnection();
		}
		
		return client.getErrorFlag();
	}
	public String getMessage() {
		return client.message();
	}
	
	public String printClientInfo() {
		return client.getPerson().toString();
	}
	
}
