package servicetrackclient.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import servicetrackclient.ClientNetworkFunctions;
import servicetrackdata.Client;
import servicetrackdirectories.DirectoryStructure;

public class CreateClientModel {

	private ClientNetworkFunctions client;
	
	public CreateClientModel() {
		client = new ClientNetworkFunctions();
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
	public void writeClient() {
		
		Client clientToWrite = (Client) client.getPerson();
		try {
			DirectoryStructure.createClientFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File userFile = new File(DirectoryStructure.getClientFile());
		try {
			var output = new ObjectOutputStream(new FileOutputStream(userFile));
			output.writeObject(clientToWrite);
			output.close();
		} catch (FileNotFoundException e) {
			// ignore should not be thrown.
			e.printStackTrace();
		} catch (IOException e) {
			// ignore should not be thrown.
			e.printStackTrace();
		}
	}
	
}
