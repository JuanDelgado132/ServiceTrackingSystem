package servicetrackclient.models;

import java.io.File;

import servicetrackclient.ServiceTrackClient;
import servicetrackdirectories.DirectoryStructure;

public class AdminModel {
	
	private ServiceTrackClient client;
	
	public AdminModel() {
		client = new ServiceTrackClient();
	}
	
	public void logOut() {
		File userFile = new File(DirectoryStructure.getUserFile());
		userFile.delete();
	}
	
}
