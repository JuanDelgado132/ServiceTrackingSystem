package servicetrackclient.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;
import java.util.HashMap;

import servicetrackclient.ClientNetworkFunctions;
import servicetrackdata.Client;
import servicetrackdata.Service;
import servicetrackdirectories.DirectoryStructure;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

public class ClientInfoModel {
	private ClientNetworkFunctions network;
	
	public ClientInfoModel() {
		network =  new ClientNetworkFunctions();
	}
	/**
	 * This method will update our network with the new information provided to us by the user.
	 * @param firstName
	 * @param lastName
	 * @param id
	 * @param gender
	 * @param birthDay
	 * @param comments
	 * @return
	 * @throws Exception
	 */
	public int updateClient(String firstName, String lastName, int id, String gender, String birthDay, String comments) throws Exception {
		Client updatedClient = new Client(firstName, lastName, id, gender, birthDay, comments);
		network.addActionCode("UC");
		network.addPerson(updatedClient);
		try {
			network.establishConnection();
			network.sendPacketToServer();
			network.receivePacketFromServer();
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			network.closeConnection();
		}
		return network.getErrorFlag();
		
	}
	/**
	 * Delete the network provided to us by the user.
	 * @param clientToDelete
	 * @return
	 * @throws Exception
	 */
	public int deleteClient(Client clientToDelete) throws Exception {
		network.addPerson(clientToDelete);
		network.addActionCode("DC");
		try {
			network.establishConnection();
			network.sendPacketToServer();
			network.receivePacketFromServer();
		} catch (IOException | ClassNotFoundException ex) {
			throw ex;
		} finally {
			network.closeConnection();
		}
		
		return network.getErrorFlag();	
	}
	/**
	 * This method allows the user to use the selected service.
	 * @param client
	 * @param serviceToUse
	 * @return
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public int userService(Client client, Service serviceToUse) throws UnknownHostException, IOException, ClassNotFoundException {
		network.addActionCode("CUS");
		network.addPerson(client);
		network.addService(serviceToUse);
		
		try {
			network.establishConnection();
			network.sendPacketToServer();
			network.receivePacketFromServer();
		} catch (UnknownHostException ex) {
			throw ex;
		} catch (IOException ex) {
			throw ex;
		} catch (ClassNotFoundException ex) {
			throw ex;
		} finally {
			network.closeConnection();
		}
		return network.getErrorFlag();
	}
	/**
	 * Generate the Barcode for the network that contains a representation of their id.
	 * @param barcodeFile
	 * @param network
	 * @throws BarcodeException
	 * @throws OutputException
	 */
	public void createBarcodeFile(File barcodeFile, Client client) throws BarcodeException, OutputException {
		Barcode barcode = BarcodeFactory.createCode128(Integer.toString(client.getId()));
		BarcodeImageHandler.savePNG(barcode, barcodeFile);
	}
	/**
	 * Read the network file that is temporarily saved when viewing the user.
	 * @return
	 */
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
	@SuppressWarnings("unchecked")
	public HashMap<Integer,Service> readRegisteredServices(){
		HashMap<Integer, Service> registeredServices = null;
		
		try {
			var serviceFile = new FileInputStream(DirectoryStructure.getServiceFile());
			var serviceInputStream = new ObjectInputStream(serviceFile);
			registeredServices = (HashMap<Integer,Service>)serviceInputStream.readObject();
			serviceInputStream.close();
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
			
		} catch (ClassNotFoundException e) {
			
		}
		
		return registeredServices;
		
	}
	/**
	 * Used to rewrite the updated network that was sent to the server.
	 * @author juand
	 */
	public void writeUpdatedClient() {
		Client updatedClient = (Client) network.getPerson();
		try {
			DirectoryStructure.createClientFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File clientFile = new File(DirectoryStructure.getClientFile());
		try {
			var output = new ObjectOutputStream(new FileOutputStream(clientFile));
			output.writeObject(updatedClient);
			output.close();
		} catch (FileNotFoundException e) {
			// ignore should not be thrown.
			
		} catch (IOException e) {
			// ignore should not be thrown.
			
		}
	}
	public String printUserInfo(){
		return network.getPerson().toString();
	}
	public String getMessage() {
		return network.message();
	}
	public void deleteClientFile() {
		DirectoryStructure.deleteClientFile();
	}
	

}
