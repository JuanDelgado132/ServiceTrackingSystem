package servicetrackclient.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

import servicetrackclient.ClientNetworkFunctions;
import servicetrackdata.Client;
import servicetrackdata.Service;
import servicetrackdirectories.DirectoryStructure;

public class RegisterServiceModel {
	
	private ClientNetworkFunctions network;
//	private HashMap<Integer, Service> services;
//	private  Client client;
	
	public RegisterServiceModel() {
		network = new ClientNetworkFunctions();
	}
	public int registerClient(Client client, Service service) throws Exception {
		network.addActionCode("RS");
		network.addPerson(client);
		network.addService(service);
		
		try {
			network.establishConnection();
			network.sendPacketToServer();
			network.receivePacketFromServer();
			System.out.println("ok");

		} catch (IOException | ClassNotFoundException ex) {
			throw ex;
		}finally {
			network.closeConnection();
		}
		return network.getErrorFlag();
		
	}
	public Client readClient() {
		Client client = null;
		try {
			var clientFile = new FileInputStream(DirectoryStructure.getClientFile());
			var clientInputStream = new ObjectInputStream(clientFile);
			client = (Client) clientInputStream.readObject();
			clientInputStream.close();
		} catch (FileNotFoundException e) {

		} catch (IOException e) {
			
		} catch (ClassNotFoundException e) {
			
		}

	return client;
	}
	public HashMap<Integer, Service> readServices(){
		HashMap<Integer,Service> services = null;
		try {
			var input = new ObjectInputStream(new FileInputStream(new File(DirectoryStructure.getServiceFile())));
			services = (HashMap<Integer,Service>)input.readObject();
			input.close();
			} catch (IOException | ClassNotFoundException ex) {
				//Nothing should be thrown.
			}
		return services;
			
		
	}
	
	

}
