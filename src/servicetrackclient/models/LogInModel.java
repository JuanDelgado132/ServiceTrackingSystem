package servicetrackclient.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.UnknownHostException;

import servicetrackclient.ClientNetworkFunctions;
import servicetrackdata.User;
import servicetrackdirectories.DirectoryStructure;

public class LogInModel {
	
	private User loggedInUser;
	private ClientNetworkFunctions client;
	
	public LogInModel() {
		client = new ClientNetworkFunctions();
	}
	
	public boolean logIn(String email, String password) throws ConnectException {
		boolean success = false;
		loggedInUser = new User();
		loggedInUser.setEmail(email);
		loggedInUser.setPassword(password);
		client.addActionCode("LG");
		client.addPerson(loggedInUser);
		try {
			client.establishConnection();
			client.sendPacketToServer();
			client.receivePacketFromServer();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectException e) {
			throw e;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.closeConnection();
		
		//Ensure we have a different object and that it is not null.
		if((client.getPerson() != null) && !(loggedInUser.equals(client.getPerson()))) {
			loggedInUser = (User)client.getPerson();
			success = true;
		}
		else
			loggedInUser = null;
		
		return success;
		
	}
	
	public User getUser() {
		return loggedInUser;
	}
	/**
	 * Write the user data that will be used throughout the windows.
	 */
	public void writeUser() {
		try {
			DirectoryStructure.createLoggedInFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File userFile = new File(DirectoryStructure.getLoggedInFile());
		try {
			var output = new ObjectOutputStream(new FileOutputStream(userFile));
			output.writeObject(loggedInUser);
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
