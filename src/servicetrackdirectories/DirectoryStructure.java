/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicetrackdirectories;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class will reference all of our directories that will be used my the server.
 * @author juand
 */
public final class DirectoryStructure {
    
    private static  String MAIN_DIR;
    public static final String SETTINGS_DIR = "settings\\";
    public static final String LOG_FILE_DIR = "logs\\";
    public static final String CONFIG_FILE = "config.properties";
    public static final String LOG = "log.txt";
    public static final String CLIENT_DIR = "Client\\";
    public static final String CLIENT_CSS_DIR = "css\\";
    public static final String CLIENT_IMAGES_DIR = "images\\";
    public static final String LOGGED_IN_USER_FILE = "LoggedInUser.data";
    public static final String USER_FILE = "user.data";
    public static final String CLIENT_FILE = "client.data";
    public static final String SERVICE_FILE = "service.data";
    //private static final 
    private DirectoryStructure(){
        
    }
    /**
     * Check to see if the config file exists.
     * @return
     */
    public static boolean checkForConfigFile(){
        return new File(MAIN_DIR + SETTINGS_DIR + CONFIG_FILE).exists();
        
    }
    
    
    
    /**
     * @deprecated 
     * @throws IOException 
     */
    public static void createFiles() throws IOException{
        File serverConfig = new File(MAIN_DIR + SETTINGS_DIR);
        serverConfig.mkdirs();
        serverConfig = new File(MAIN_DIR + SETTINGS_DIR + CONFIG_FILE);
        serverConfig.createNewFile();
        File logFile = new File(MAIN_DIR + LOG_FILE_DIR);
        logFile.mkdirs();
        logFile = new File(MAIN_DIR + LOG_FILE_DIR + LOG);
        logFile.createNewFile();
  
    }
    
    public static void createLogFile(){
        File logFile = new File(MAIN_DIR + LOG_FILE_DIR);
        logFile.mkdirs();
        logFile = new File(MAIN_DIR + LOG_FILE_DIR + LOG);
        try {
            logFile.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(DirectoryStructure.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void createConfigFile() throws IOException{
        File serverConfig = new File(MAIN_DIR + SETTINGS_DIR);
        serverConfig.mkdirs();
        serverConfig = new File(MAIN_DIR + SETTINGS_DIR + CONFIG_FILE);
        serverConfig.createNewFile();
    }
    public static void createLoggedInFile() throws IOException {
    	File userFile = new File(MAIN_DIR + CLIENT_DIR);
    	userFile.mkdirs();
    	userFile = new File(MAIN_DIR + CLIENT_DIR + LOGGED_IN_USER_FILE);
    	userFile.createNewFile();
    }
    
    public static void deleteLoggedInFile() {
    	File userFile = new File(MAIN_DIR + CLIENT_DIR + LOGGED_IN_USER_FILE);
    	userFile.delete();
    }
    public static void createUserFile() throws IOException {
    	File userFile = new File(MAIN_DIR + CLIENT_DIR);
    	userFile.mkdirs();
    	userFile = new File(MAIN_DIR + CLIENT_DIR + USER_FILE);
    	userFile.createNewFile();
    }
    
    public static void deleteUserFile() {
    	File userFile = new File(MAIN_DIR + CLIENT_DIR + USER_FILE);
    	userFile.delete();
    }
    public static void createClientFile() throws IOException {
    	File userFile = new File(MAIN_DIR + CLIENT_DIR);
    	userFile.mkdirs();
    	userFile = new File(MAIN_DIR + CLIENT_DIR + CLIENT_FILE);
    	userFile.createNewFile();
    }
    
    public static void deleteClientFile() {
    	File userFile = new File(MAIN_DIR + CLIENT_DIR + CLIENT_FILE);
    	userFile.delete();
    }
    public static void createServiceFile() throws IOException {
    	File userFile = new File(MAIN_DIR + CLIENT_DIR);
    	userFile.mkdirs();
    	userFile = new File(MAIN_DIR + CLIENT_DIR + SERVICE_FILE);
    	userFile.createNewFile();
    }
    
    public static void deleteServiceFile() {
    	File userFile = new File(MAIN_DIR + CLIENT_DIR + SERVICE_FILE);
    	userFile.delete();
    }
    public static void createClientDirs() {
    	File file = new File(MAIN_DIR + CLIENT_DIR + CLIENT_CSS_DIR);
    	file.mkdirs();
    	file = new File(MAIN_DIR + CLIENT_DIR + CLIENT_IMAGES_DIR);
    	file.mkdirs();
    }
    public static void setMainDir(String mainDir) {
    	MAIN_DIR = mainDir;
    }
    public static String getMainDir(){
        return MAIN_DIR;
    }
    public static  String getConfigFile(){
    	
        return MAIN_DIR + SETTINGS_DIR + CONFIG_FILE;
    }
    public static String getLogFile() {
        return MAIN_DIR + LOG_FILE_DIR + LOG;
    }
    public static String getLoggedInFile(){
    	return MAIN_DIR + CLIENT_DIR + LOGGED_IN_USER_FILE;
    }
    public static String getUserFile() {
    	return MAIN_DIR + CLIENT_DIR + USER_FILE;
    }
    public static String getClientFile(){
    	return MAIN_DIR + CLIENT_DIR + CLIENT_FILE;
    }
    public static String getServiceFile() {
    	return MAIN_DIR + CLIENT_DIR + SERVICE_FILE;
    }
    
}
