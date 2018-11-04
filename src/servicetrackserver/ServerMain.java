package servicetrackserver;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import database.DBOperations;
import servicetrackdirectories.*;

public class ServerMain {
	
	public static void main(String[] args) throws IOException {
		ServiceTrackingServer server = new ServiceTrackingServer();
		/*if(!DirectoryStructure.checkForConfigFile()) {
			server.performFirstTimeSetup();
			server.startServer();
		}
		else {
			server.startServer();
		}
		*/
		DBOperations db = DBOperations.getDBSingleton();
		byte[] r = db.exportData();
		
		var testStream = new FileOutputStream(new File("build_from_byte.csv"));
		var bufStream = new BufferedOutputStream(testStream);
		
		bufStream.write(r,0,r.length);
		bufStream.close();
		/*DBOperations db = DBOperations.getDBSingleton();
		
		//User user = db.fetchLogInInfo("jdelgado5443@gmail.com", "Skyl@r5106");
		User user = db.getUser(1708403);
		System.out.println(user.toString());*/
		
		
	}

}
