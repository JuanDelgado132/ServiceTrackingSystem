package servicetrackclient; 

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import servicetrackdata.User;
import servicetracknetwork.ServiceTrackProtocol;

public class test {
	
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		//User user = new User("John", "Doe", 1111111, "john@gmail.com", "staff", "#PIE%^&?", "E Street", "(956) 544-4600");
		User user = new User();
		user.setEmail("jdelgado5443@gmail.com");
		user.setPassword("poop");
		ServiceTrackProtocol p = new ServiceTrackProtocol();
		p.setActionCode("LG");
		p.setPerson(user);
		
		Socket socket = new Socket("192.168.1.11", 4500);
		var out = new ObjectOutputStream(socket.getOutputStream());
		var in = new ObjectInputStream(socket.getInputStream());
		out.writeObject(p);
		p = (ServiceTrackProtocol) in.readObject();
		System.out.println(p.getMessage());
		System.out.println(p.getPerson().toString());
	}
	
}
