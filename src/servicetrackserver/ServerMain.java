package servicetrackserver;
import servicetrackdirectories.*;

public class ServerMain {
	
	public static void main(String[] args) {
		ServiceTrackingServer server = new ServiceTrackingServer();
		DirectoryStructure.setMainDir("C:\\ServiceTracking\\");
		if(!DirectoryStructure.checkForConfigFile()) {
			server.performFirstTimeSetup();
			server.startServer();
		}
		else {
			server.startServer();
		}
		
		
		
		
	}

}
