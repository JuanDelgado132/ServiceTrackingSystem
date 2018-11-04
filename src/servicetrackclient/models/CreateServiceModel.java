package servicetrackclient.models;

import java.io.IOException;

import servicetrackclient.ClientNetworkFunctions;
import servicetrackdata.Service;

public class CreateServiceModel {
	
	private ClientNetworkFunctions client;
	
	public CreateServiceModel() {
		client = new ClientNetworkFunctions();
	}
	
	public int createService(String serviceName, String serviceDescription, String days, String time) throws Exception {
		
		Service newService = new Service(70000, serviceName, serviceDescription, days, time);
		client.addActionCode("NS");
		client.addService(newService);
		
		try {
			client.establishConnection();
			client.sendPacketToServer();
			client.receivePacketFromServer();
		} catch (IOException | ClassNotFoundException ex) {
			throw ex;
		} finally {
			client.closeConnection();
		}
		
		return client.getErrorFlag();
		
	}
	
	public String getMessage() {
		return client.message();
	}
	public String getServiceInfo() {
		return client.getService().toString();
	}

}
