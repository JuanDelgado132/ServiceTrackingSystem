package servicetrackclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javafx.application.Application;
import javafx.stage.Stage;
import servicetrackclient.controllers.MasterController;
import servicetrackdirectories.DirectoryStructure;

public class ClientDriver extends Application{

	public void start(Stage stage) throws Exception {
		MasterController control = MasterController.getMaster();
		//control.setUpPrimaryStage();
		if(!firstTimeSetupNeeded()) {
			control.setupAllViews();
			control.setPrimaryStage(stage);
			control.fireEvent("SU");
		}
		else {
			initSettings();
			control.setupAllViews();
			control.setPrimaryStage(stage);
			control.setUpPrimaryStage();
			control.fireEvent("O");
		}
		
	
		
		
		
	}
	
	public static void main(String[] args) {
		
		Application.launch(args);
		
	}
	
	public static boolean firstTimeSetupNeeded() {
		return new File("client.config").exists();
	}
	private static void initSettings() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("client.config"));
			DirectoryStructure.setMainDir(prop.getProperty("clientDir"));
			ClientNetworkFunctions.serverIP = prop.getProperty("IP");
		} catch (IOException ex) {
			//Ignore, the file will be there.
		}
		prop = null;
	}
}
