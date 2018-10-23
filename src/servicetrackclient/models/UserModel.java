package servicetrackclient.models;

import java.io.File;

import servicetrackdirectories.DirectoryStructure;

public class UserModel {

	public UserModel() {
		
	}
	/**
	 * This method will delete the user.data file that is used to keep a session open.
	 * @author juand
	 */
	public void logOut() {
		File userFile = new File(DirectoryStructure.getUserFile());
		userFile.delete();
	}
}
