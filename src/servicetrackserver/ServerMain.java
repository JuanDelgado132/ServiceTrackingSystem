package servicetrackserver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Properties;

import database.DBOperations;
import servicetrackdata.User;
import servicetrackdirectories.*;

public class ServerMain {
	
	public static void main(String[] args) {
		ServiceTrackingServer server = new ServiceTrackingServer();
		if(!DirectoryStructure.checkForConfigFile()) {
			server.performFirstTimeSetup();
			server.startServer();
		}
		else {
			server.startServer();
		}
		
		/*DBOperations db = DBOperations.getDBSingleton();
		
		//User user = db.fetchLogInInfo("jdelgado5443@gmail.com", "Skyl@r5106");
		User user = db.getUser(1708403);
		System.out.println(user.toString());*/
		
		
	}

}
