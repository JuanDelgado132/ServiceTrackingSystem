package servicetrackclient.models;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import servicetrackclient.ClientNetworkFunctions;
import servicetrackdata.User;
import servicetrackdirectories.DirectoryStructure;

public class ViewUserModel {
	
	private ClientNetworkFunctions client;
	
	public ViewUserModel() {
		client = new ClientNetworkFunctions();
	}
	
	public int updateUser(String firstName, String lastName, int id, String email, String role, String password, String address, String phoneNumber) throws Exception {
		User updatedUser = new User(firstName, lastName, id, email, role, password, address, phoneNumber);
		client.addActionCode("UU");
		client.addPerson(updatedUser);
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
	
	public int deleteUser(User user) throws Exception {
		client.addPerson(user);
		client.addActionCode("DU");
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
	
	public String printUserInfo(){
		return client.getPerson().toString();
	}
	public String getMessage() {
		return client.message();
	}
	
	public User readUser() {
		User user = null;
		try {
			var userFile = new FileInputStream(DirectoryStructure.getUserFile());
			var userFileStream = new ObjectInputStream(userFile);
			user = (User)userFileStream.readObject();
			userFileStream.close();
		} catch (FileNotFoundException e) {
			//No error will be thrown.
		} catch (IOException e) {
			//Should not be thrown, Ignored
		} catch (ClassNotFoundException e) {
			//Should not be thrown, ignored
		} finally {
		}
		
		return user;
		
	}
	
	public void deleteUserFile() {
		DirectoryStructure.deleteUserFile();
	}
	
	
}
