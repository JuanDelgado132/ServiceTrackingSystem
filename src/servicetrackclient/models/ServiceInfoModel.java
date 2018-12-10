package servicetrackclient.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import servicetrackclient.ClientNetworkFunctions;
import servicetrackdata.Service;
import servicetrackdirectories.DirectoryStructure;

public class ServiceInfoModel {
	
	private ClientNetworkFunctions client;
	
	public ServiceInfoModel() {
		client = new ClientNetworkFunctions();
	}
	
	public Service readServiceFile() {
		Service requestedService = null;
		File serviceFile = new File(DirectoryStructure.getServiceFile());
		FileInputStream serviceFileStream = null;
		ObjectInputStream serviceObjectStream = null;
		try {
			serviceFileStream = new FileInputStream(serviceFile);
			serviceObjectStream =  new ObjectInputStream(serviceFileStream);
			requestedService = (Service) serviceObjectStream.readObject();
		} catch (FileNotFoundException e) {
			
			
		} catch (IOException e) {
			//Ignore. Should not be thrown.
			
		} catch (ClassNotFoundException e) {
			//Ignore. Should not be thrown.
		} finally {
			closeQuietly(serviceObjectStream);
			closeQuietly(serviceFileStream);
		}
		
		return requestedService;
	}
	public int updateService(Service serviceToUpdate) throws IOException, ClassNotFoundException  {
		client.addActionCode("US");
		client.addService(serviceToUpdate);
		
		try {
			client.establishConnection();
			client.sendPacketToServer();
			client.receivePacketFromServer();
		} catch (IOException ex) {
			throw ex;
		} catch (ClassNotFoundException ex) {
			throw ex;
		} finally {
			client.closeConnection();
		}
		
		return client.getErrorFlag();
	}
	public String getMessage() {
		
		return client.message();
	}
	public int changeActiveStatus(Service serviceToUpdate) throws IOException, ClassNotFoundException {
		client.addActionCode("CHS");
		client.addService(serviceToUpdate);
		try {
			client.establishConnection();
			client.sendPacketToServer();
			client.receivePacketFromServer();
		} catch (IOException ex) {
			throw ex;
		} catch (ClassNotFoundException ex) {
			throw ex;
		} finally {
			client.closeConnection();
		}
		return client.getErrorFlag();
	}
	public void deleteOldFile() {
		DirectoryStructure.deleteServiceFile();
	}
	public void writeServiceFile() {
		Service updatedService = client.getService();
		try {
			DirectoryStructure.createServiceFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
		File serviceFile = new File(DirectoryStructure.getServiceFile());
		try {
			var output = new ObjectOutputStream(new FileOutputStream(serviceFile));
			output.writeObject(updatedService);
			output.close();
		} catch (FileNotFoundException e) {
			// ignore should not be thrown.
			
		} catch (IOException e) {
			// ignore should not be thrown.
			
		}
	}
	private void closeQuietly(InputStream input) {
		if(input == null)
			return;
		try {
			input.close();
		} catch (IOException e) {
			//Ignore. Should not be thrown.
		}
	}

}
