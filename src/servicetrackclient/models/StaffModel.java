package servicetrackclient.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;
import java.util.HashMap;

import servicetrackclient.ClientNetworkFunctions;
import servicetrackdata.Client;
import servicetrackdata.Service;
import servicetrackdata.User;
import servicetrackdirectories.DirectoryStructure;

public class StaffModel {
	private ClientNetworkFunctions client;

	public StaffModel() {
		client = new ClientNetworkFunctions();
	}

	public int getFlag() {
		return client.getErrorFlag();
	}

	public String getMessage() {
		return client.message();
	}

	public void logOut() {
		File userFile = new File(DirectoryStructure.getLoggedInFile());
		userFile.delete();
	}
	/**
	 * Get the client the staff member searched for/
	 * @param requestedClient
	 * @return
	 * @throws Exception
	 * @author juand
	 */
	public Client requestClient(Client requestedClient) throws Exception {
		client.addActionCode("GC");
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
		return (Client) client.getPerson();
	}
	/**
	 * Get services that client has not registered yet.
	 * @param requestedClient
	 * @return
	 * @throws Exception
	 * @author juand
	 */
	public HashMap<Integer, Service> getNonRegisteredServices(Client requestedClient) throws Exception {
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
	/**
	 * Get the services that the client has, so that we can mark it as used.
	 * @param requestedClient
	 * @return
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @author Juand
	 */
	public HashMap<Integer, Service> getRegisteredService(Client requestedClient) throws UnknownHostException, IOException, ClassNotFoundException{
		client.addActionCode("RG");
		client.addPerson(requestedClient);
		try {
			client.establishConnection();
			client.sendPacketToServer();
			client.receivePacketFromServer();
		} catch (UnknownHostException ex) {
			throw ex;
		} catch (IOException ex) {
			throw ex;
		} catch (ClassNotFoundException ex) {
			throw ex;
		} finally {
			client.closeConnection();
		}
		
		return client.getServices();
	}

	/**
	 * Writes the requested data that will be shared among the views. Will be
	 * destroyed later on.
	 * 
	 * @param requestedObject
	 */
	public void writeRequestedObject(Object requestedObject) {
		String filePath = null;
		//We need to check if requestedObjec is an instance of user, service, client, or a hashmap of clients. So we can create the appropiate file and get
		// the correct file path.
		if (requestedObject instanceof User) {
			try {
				DirectoryStructure.createUserFile();
			} catch (IOException ex) {
				// No exception should be thrown.
			}
			filePath = DirectoryStructure.getUserFile();
		} else if (requestedObject instanceof Client) {
			try {
				DirectoryStructure.createClientFile();
			} catch (IOException ex) {
				
			}
			filePath = DirectoryStructure.getClientFile();
		} else if (requestedObject instanceof Service) {
			try {
				DirectoryStructure.createServiceFile();
			} catch (IOException ex) {
				
			}
			filePath = DirectoryStructure.getServiceFile();
			//Need to find a way to differentiate. Type erasure makes it impossible to do this.
		} else if (requestedObject instanceof HashMap) {

			try {
				DirectoryStructure.createServiceFile();
			} catch (IOException ex) {
				
			}
			filePath = DirectoryStructure.getServiceFile();
		}

		File file = new File(filePath);
		try {
			var output = new ObjectOutputStream(new FileOutputStream(file));
			output.writeObject(requestedObject);
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
