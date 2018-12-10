package servicetrackclient.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import servicetrackclient.ClientNetworkFunctions;
import servicetrackdirectories.DirectoryStructure;

public class SetupModel {
	
	public SetupModel() {
		
	}
	
	public boolean createConfigFile(String ip, String dirPath) {
		Properties prop = new Properties();
		if(!isIPValid(ip)) {
			return false;
		}
		prop.setProperty("IP", ip);
		prop.setProperty("clientDir", dirPath);
		File file = new File("client.config");
		try {
			file.createNewFile();
			prop.store(new FileOutputStream(file), null);
		} catch (IOException ex) {
			
		}
		
		return true;
	}
	public void initSettings() {
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
	
	private boolean isIPValid(String ip) {
		Pattern ptn = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");
        Matcher mtch = ptn.matcher(ip);
        return mtch.find();
	}
}
