package servicetrackclient;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import servicetrackdata.Person;
import servicetrackdata.Service;
import servicetracknetwork.ServiceTrackProtocol;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ClientNetworkFunctions {
	
	private Socket serverConnection;
	private ServiceTrackProtocol packet;
	public static String serverIP = "192.168.2.6";
	
	
	
	
	public ClientNetworkFunctions() {
		
		packet = new ServiceTrackProtocol();
		
	}
	
	public void establishConnection() throws UnknownHostException, IOException {
		serverConnection = new Socket(serverIP, 4500);
		
	}
	
	public void addPerson(Person person) {
		packet.setPerson(person);
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
		return packet.getPerson();
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
	public int getErrorFlag() {
		return packet.getErrorFlag();
	}
	public byte[] getExportFile() {
		return packet.getFileBytes();
	}
	public String getExportFileExtentsion() {
		return packet.getFileExtension();
	}
	public void setSelectedDate(String date) {
		packet.setSelectedDate(date);
	}
	public void sendPacketToServer() throws IOException {
		var clientOutput = new ObjectOutputStream(serverConnection.getOutputStream());
		clientOutput.writeObject(packet);
	}
	public void receivePacketFromServer() throws IOException, ClassNotFoundException {
		var serverInput = new ObjectInputStream(serverConnection.getInputStream());
		packet = (ServiceTrackProtocol)serverInput.readObject();
	}
	
	
	public void closeConnection() {
		if(serverConnection == null)
			return;
		else {
			try {
				serverConnection.close();
			} catch (IOException e) {
				//Ignore
			}
		}
	}
}
