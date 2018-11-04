package servicetrackclient.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import servicetrackclient.ClientNetworkFunctions;
import servicetrackdata.Client;
import servicetrackdata.Service;
import servicetrackdata.User;
import servicetrackdirectories.DirectoryStructure;

public class AdminModel {
	
	private ClientNetworkFunctions client;
	
	public AdminModel() {
		client = new ClientNetworkFunctions();
	}
	
	public void logOut() {
		File userFile = new File(DirectoryStructure.getLoggedInFile());
		userFile.delete();
	}
	//Get the searched for user.
	public User requestUser(User requestedUser) throws Exception {
		client.addActionCode("GU");
		client.addPerson(requestedUser);
		try {
			client.establishConnection();
			client.sendPacketToServer();
			client.receivePacketFromServer();
		} catch (IOException | ClassNotFoundException e) {
			throw e;
		} finally {
			client.closeConnection();
		}
		
		return (User)client.getPerson();	
	}
	//Get the searched for client.
	public Client requestClient(Client requestedClient) throws Exception {
		client.addActionCode("GC");
		client.addPerson(requestedClient);
		try {
			client.establishConnection();
			client.sendPacketToServer();
			client.receivePacketFromServer();
		} catch (IOException | ClassNotFoundException e) {
			throw e;
		} finally {
			client.closeConnection();
		}
		return (Client)client.getPerson();
	}
	public HashMap<Integer,Service> getNonRegisteredServices(Client requestedClient) throws Exception{
		client.addActionCode("NRS");
		client.addPerson(requestedClient);
		try {
			client.establishConnection();
			client.sendPacketToServer();
			client.receivePacketFromServer();
		} catch (IOException | ClassNotFoundException ex) {
			throw ex;
		} finally {
			client.closeConnection();
		}
		
		return client.getServices();
		
	}
	//Write the the requested data to be used by other views. It will be destroyed later on.
	public void writeRequestedObject(Object requestedObject) {
		if(requestedObject instanceof User) {
			User requestedUser = (User) requestedObject;
			try {
				DirectoryStructure.createUserFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			File file = new File(DirectoryStructure.getUserFile());
			try {
				var output = new ObjectOutputStream(new FileOutputStream(file));
				output.writeObject(requestedUser);
				output.close();
			} catch (FileNotFoundException e) {
				// ignore should not be thrown.
				e.printStackTrace();
			} catch (IOException e) {
				// ignore should not be thrown.
				e.printStackTrace();
			}
		}
		else if (requestedObject instanceof Client) {
			Client requestedClient = (Client) requestedObject;
			try {
				DirectoryStructure.createClientFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			File file = new File(DirectoryStructure.getClientFile());
			try {
				var output = new ObjectOutputStream(new FileOutputStream(file));
				output.writeObject(requestedClient);
				output.close();
			} catch (FileNotFoundException e) {
				// ignore should not be thrown.
				e.printStackTrace();
			} catch (IOException e) {
				// ignore should not be thrown.
				e.printStackTrace();
			}
		}
		else if(requestedObject instanceof Service) {
			Service requestedService = (Service) requestedObject;
			try {
				DirectoryStructure.createServiceFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			File file = new File(DirectoryStructure.getServiceFile());
			try {
				var output = new ObjectOutputStream(new FileOutputStream(file));
				output.writeObject(requestedService);
				output.close();
			} catch (FileNotFoundException e) {
				// ignore should not be thrown.
				e.printStackTrace();
			} catch (IOException e) {
				// ignore should not be thrown.
				e.printStackTrace();
			}
		}
		else if(requestedObject instanceof HashMap) {
			HashMap<Integer, Service> services = (HashMap<Integer, Service>) requestedObject;
			try {
				DirectoryStructure.createServiceFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			File file = new File(DirectoryStructure.getServiceFile());
			try {
				var output = new ObjectOutputStream(new FileOutputStream(file));
				output.writeObject(services);
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
}
