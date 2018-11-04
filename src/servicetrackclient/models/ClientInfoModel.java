package servicetrackclient.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import servicetrackclient.ClientNetworkFunctions;
import servicetrackdata.Client;
import servicetrackdirectories.DirectoryStructure;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

public class ClientInfoModel {
	private ClientNetworkFunctions client;
	
	public ClientInfoModel() {
		client =  new ClientNetworkFunctions();
	}
	/**
	 * This method will update our client with the new information provided to us by the user.
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
		client.addActionCode("UC");
		client.addPerson(updatedClient);
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
	/**
	 * Delete the client provided to us by the user.
	 * @param clientToDelete
	 * @return
	 * @throws Exception
	 */
	public int deleteClient(Client clientToDelete) throws Exception {
		client.addPerson(clientToDelete);
		client.addActionCode("DC");
		try {
			client.establishConnection();
			client.sendPacketToServer();
			client.receivePacketFromServer();
		} catch (IOException | ClassNotFoundException e) {
			throw e;
		} finally {
			client.closeConnection();
		}
		
		return client.getErrorFlag();	
	}
	/**
	 * Generate the Barcode for the client that contains a representation of their id.
	 * @param barcodeFile
	 * @param client
	 * @throws BarcodeException
	 * @throws OutputException
	 */
	public void createBarcodeFile(File barcodeFile, Client client) throws BarcodeException, OutputException {
		Barcode barcode = BarcodeFactory.createCode128(Integer.toString(client.getId()));
		BarcodeImageHandler.savePNG(barcode, barcodeFile);
	}
	/**
	 * Read the client file that is temporarily saved when viewing the user.
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
	/**
	 * Used to rewrite the updated client that was sent to the server.
	 * @author juand
	 */
	public void writeUpdatedClient() {
		Client updatedClient = (Client) client.getPerson();
		try {
			DirectoryStructure.createClientFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File userFile = new File(DirectoryStructure.getClientFile());
		try {
			var output = new ObjectOutputStream(new FileOutputStream(userFile));
			output.writeObject(updatedClient);
			output.close();
		} catch (FileNotFoundException e) {
			// ignore should not be thrown.
			e.printStackTrace();
		} catch (IOException e) {
			// ignore should not be thrown.
			e.printStackTrace();
		}
	}
	public String printUserInfo(){
		return client.getPerson().toString();
	}
	public String getMessage() {
		return client.message();
	}
	public void deleteClientFile() {
		DirectoryStructure.deleteClientFile();
	}
	

}
