package servicetrackclient;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import servicetrackdata.Person;
import servicetrackdata.Service;
import servicetrackdata.User;
import servicetracknetwork.ServiceTrackProtocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServiceTrackClient {
	
	private Socket serverConnection;
	private ServiceTrackProtocol packet;
	
	
	
	
	public ServiceTrackClient() {
		
		packet = new ServiceTrackProtocol();
		
	}
	
	public void establishConnection() throws UnknownHostException, IOException {
		serverConnection = new Socket("172.28.147.26", 4500);
		
	}
	
	public void addPerson(Person person) {
		packet.setUser(person);
	}
	public void addService(Service service) {
		packet.setService(service);
	}
	public void addServices(HashMap<Integer,Service> services) {
		packet.setServices(services);
	}
	
	public void addActionCode(String actionCode) {
		packet.setActionCode(actionCode);
	}
	public Person getPerson() {
		return packet.getUser();
	}
	public Service getService() {
		return packet.getService();
	}
	public HashMap<Integer,Service> getServices(){
		return packet.getServices();
	}
	public ArrayList<Person> getAllUsersOrClients(){
		return packet.getPersons();
	}
	public String getCode() {
		return packet.getActionCode();
	}
	public String message() {
		return packet.getMessage();
	}
	public void sendPacketToServer() throws IOException {
		var clientOutput = new ObjectOutputStream(serverConnection.getOutputStream());
		clientOutput.writeObject(packet);
		//System.out.println("packet sent");
		
		
	}
	public void receivePacketFromServer() throws IOException, ClassNotFoundException {
		var serverInput = new ObjectInputStream(serverConnection.getInputStream());
		packet = (ServiceTrackProtocol)serverInput.readObject();
		//System.out.println("packet received.");
		
	}
	
	public void closeConnection() {
		if(serverConnection == null)
			return;
		else {
			try {
				serverConnection.close();
			} catch (IOException e) {
				//Ignore.
			}
		}
	}
}
