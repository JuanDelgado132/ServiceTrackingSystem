/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicetrackserver;

import database.DBOperations;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import servicetrackdata.Client;
import servicetrackdata.Person;
//import servicetrackdata.Service;
import servicetrackdata.User;
import servicetrackdirectories.DirectoryStructure;
import servicetracknetwork.ServiceTrackProtocol;

/**
 * This class will be our server from here we will conduct all of our server
 * operations. Along with managing the client and server connection.
 *
 * @author juand
 */
public class ServiceTrackingServer {

    private ExecutorService serverThreadPool;
    private Thread masterThread;
    private ServerSocket serverConnection; //use in case we need to shutdown the server we must keep track of it in the main thread to close the method.
    private int numOfThreads;
    private int port;
    private Scanner input;
    private static PrintWriter log;
    private static Properties prop;
    private static DBOperations DB;

    public ServiceTrackingServer() {
    	//initialize the scanner for first time setup.
    	input = new Scanner(System.in);

    }
    public void startServer() {
    	//Initialize all server settings.
    	init();
    	masterThread = new Thread(new ConnectionManager());
    	masterThread.start();
    	mainMenu();
    	
    	
    }
    
    public void init() {

        //If the object remains as null, then we know the operation for whatever reason failed.
        prop = new Properties();
        boolean serverSettingsLoaded = false;
        log = null;
        try {
            log = new PrintWriter(new FileOutputStream(DirectoryStructure.getLogFile()), true);
        } catch (FileNotFoundException ex) {
            //If file does not exist then we catch it and create the file.
            DirectoryStructure.createLogFile();
        } finally {
            try {
                log = new PrintWriter(new FileOutputStream(DirectoryStructure.getLogFile()), true);
            } catch (FileNotFoundException ex) {

            }
        }

        try {
            prop.load(new FileInputStream(DirectoryStructure.getConfigFile()));
            port = Integer.parseInt(prop.getProperty("Port"));
            System.out.println(port);
            numOfThreads = Integer.parseInt(prop.getProperty("NumberOfThreads"));
            serverSettingsLoaded = true;
        } catch (IOException ex) {

        }

        if (!serverSettingsLoaded) {
            serverThreadPool = Executors.newFixedThreadPool(10);
        } else {
            serverThreadPool = Executors.newFixedThreadPool(numOfThreads);
        }

    }
    public void mainMenu() {
    	boolean start = false;
    	try {
			DB = DBOperations.getDBSingleton();
			start = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	int choice = 0;
    	while(start) {
    		System.out.println("-----------------------------------------------------------------");
    		System.out.println("Please choose one of the following options.");
    		System.out.println("1 to enter a new user.");
    		System.out.println("2 to enter a new client.");
    		System.out.println("3 to enter a new service.");
    		System.out.println("5 to delete a user.");
    		System.out.println("6 to delete a client.");
    		System.out.println("7 to delete a service.");
    		System.out.println("8 to see all users.");
    		System.out.println("9 to see all clients.");
    		System.out.println("10 to see all services");
    		System.out.println("-----------------------------------------------------------------");
    		if(input.hasNextInt())
    			choice = input.nextInt();
    		else 
    			continue;
    		
    		try {
				processChoice(choice);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException | SQLException ex) {
				log.println("Error");
				log.println(ex.getMessage());
	            ex.printStackTrace(log);
	            log.println("--------------------------------------------------------------------------------------------------");
	            ex.printStackTrace();
			} finally {
				log.flush();
			}
    		
    	}
    }
    private void processChoice(int choice) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
    	switch(choice) {
    	case 1:
    		DB.addNewUser(addUser());
    		break;
    	case 2:
    		DB.addNewClient(addClient());
    		break;
    	default :
    			System.out.println("Error re-enter your choice.");
    		
    	}
    }
    /**
     * Method creates a new user.
     * @return User
     */
    private User addUser() {
    	//clear buffer
    	input.nextLine();
    	
    	User userToAdd = null;
    	String firstName;
    	String lastName;
    	int id = 000000;
    	String email;
    	String password;
    	String address;
    	String role;
    	String phoneNumber;
    	
    	System.out.println("Please enter the user's first name");
    	firstName = input.nextLine();
    	
    	System.out.println("Please enter the user's last name.");
    	lastName = input.nextLine();
    	
    	System.out.println("Please enter the user's email");
    	email = input.nextLine();
    	do {
    		System.out.println("Please enter the user's role. Please note the role must be admin or staff");
    		role = input.nextLine();
    	}
    	while(!(role.equalsIgnoreCase("staff")) && !(role.equalsIgnoreCase("Admin")));
    	
    	System.out.println("Please enter the user's password");
    	password = input.nextLine();
    	
    	System.out.println("Please enter the user's address");
    	address = input.nextLine();
    	
    	System.out.println("Please enter the user's phone number");
    	phoneNumber = input.nextLine();
    	
    	//Clear the buffer
    	//input.nextLine();
    	
    	userToAdd = new User(firstName, lastName, id, email, role, password, address, phoneNumber);
    	
    	return userToAdd;
    }
    /**
     * Add a service.
     * @return
     */
   
    private Client addClient() {
    	//Clear the buffer.
    	input.nextLine();
    	String firstName;
    	String lastName;
    	int id = 00000000;
    	String gender;
    	String comments;
    	String dateOfBirth;
    	
    	System.out.println("Please enter the client's first name.");
    	firstName = input.nextLine();
    	
    	System.out.println("Please enter the client's last name.");
    	lastName = input.nextLine();
    	do {
    		System.out.println("Please enter the client's gender.");
    		gender = input.nextLine();
    	}while(!(gender.equalsIgnoreCase("female")) && !(gender.equalsIgnoreCase("male")));
    	
    	System.out.println("Please enter the client's date of birth.");
    	dateOfBirth = input.nextLine();
    	
    	System.out.println("Any comments? Press enter when done.");
    	comments = input.nextLine();
    	
    	return new Client(firstName, lastName, id, gender, dateOfBirth, comments);
    	
    }
    /**
     * Initialize settings for the server and database.
     *
     * @return
     * @author Juan Delgado
     */
    public boolean performFirstTimeSetup() {
        boolean successful = false;
        prop = new Properties();
        
        System.out.println("Please enter the port for the server.");
        int port = input.nextInt();
        System.out.println("Please enter the number of thereads the server will use.");
        int numOfThreads = input.nextInt();
        prop.setProperty("Port", String.valueOf(port));
        prop.setProperty("DBName", "ServiceTrackDatabase");
        prop.setProperty("DBUsername", "root");
        prop.setProperty("DBPassword", "");
        prop.setProperty("Host", "jdbc:mysql://localhost/");
        prop.setProperty("Setup", "true");
        prop.setProperty("DatabaseSetup", "0");
        prop.setProperty("NumberOfThreads", String.valueOf(numOfThreads));
        //clear the buffer.
        //scan.nextLine();
        
        DirectoryStructure.createLogFile();
        
        try {
			log = new PrintWriter(new FileOutputStream(DirectoryStructure.getLogFile()), true);
		} catch (FileNotFoundException e) {
			
		}
        
        try {
            //DirectoryStructure.createFiles();
            DirectoryStructure.createConfigFile();
           
            prop.store(new FileOutputStream(DirectoryStructure.getConfigFile()), null);
            
        } catch (FileNotFoundException ex) {
        	
            log.println(ex.getMessage());
            ex.printStackTrace(log);
            ex.printStackTrace();
        } catch (IOException ex) {
            //Logger.getLogger(ServiceTrackingServer.class.getName()).log(Level.SEVERE, null, ex);
            log.println(ex.getMessage());
            ex.printStackTrace(log);
            ex.printStackTrace();
        } finally {
        	
        	log.flush();
        }
        //No longer needed. Have the JVM Garbage collector remove it from memory.
        prop = null;
        
        try {
			var db = DBOperations.getDBSingleton();
			db.initializeDatabaseAndTables();
		} catch (IOException ex) {
			log.println("Error");
			log.println(ex.getMessage());
            ex.printStackTrace(log);
            log.println("--------------------------------------------------------------------------------------------------");
            ex.printStackTrace();
		} catch (SQLException ex) {
			//log.println("Error");
			//log.println(ex.getMessage());
            //ex.printStackTrace(log);
            //log.println("--------------------------------------------------------------------------------------------------");
			ex.printStackTrace();
			ex.getMessage();
		} catch (NoSuchAlgorithmException ex) {
			log.println("Error");
			log.println(ex.getMessage());
            ex.printStackTrace(log);
            log.println("--------------------------------------------------------------------------------------------------");
            ex.printStackTrace();
		} catch (InvalidKeySpecException ex) {
			log.println("Error");
			log.println(ex.getMessage());
            ex.printStackTrace(log);
            log.println("--------------------------------------------------------------------------------------------------");
            ex.printStackTrace();
		}
        finally {
        	log.flush();
        }
        successful = true;
        return successful;

    }
    
    
    
    class ConnectionManager implements Runnable {
    	
        private Socket clientConnection;

        public ConnectionManager() {
            try {
            	
                serverConnection = new ServerSocket(port, 50, InetAddress.getLocalHost());
            } catch (UnknownHostException ex) {
            	log.println("Error");
            	log.println(ex.getMessage());
                ex.printStackTrace(log);
                log.println("--------------------------------------------------------------------------------------------------");
                
            } catch (IOException ex) {
            	log.println("Error");
            	log.println(ex.getMessage());
                ex.printStackTrace(log);
                log.println("--------------------------------------------------------------------------------------------------");
                
            }
            finally {
            	log.flush();
            }

        }

        @Override
        public void run() {

            try {
            	while(true) {
            		
            		clientConnection = serverConnection.accept();
            		serverThreadPool.execute(new ServerTask(clientConnection.getOutputStream(), clientConnection.getInputStream()));
            	}
            } catch (SocketException ex) {
                //serverThreadPool. HandleShutdown.
            } catch (IOException ex) {
                Logger.getLogger(ServiceTrackingServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    class ServerTask implements Runnable {

        private ObjectOutputStream output;
        private ObjectInputStream input;
        private ServiceTrackProtocol packet;
        private String message;
        private DBOperations db;
        
        public ServerTask(OutputStream output, InputStream input) {
            try {
                this.output = new ObjectOutputStream(output);
                this.input = new ObjectInputStream(input);
                db = DBOperations.getDBSingleton();
            } catch (IOException ex) {
            	log.println("Error");
            	log.println(ex.getMessage());
                ex.printStackTrace(log);
                log.println("--------------------------------------------------------------------------------------------------");
                //Destroy thread.
            }
            finally {
            	log.flush();
            }
        }

        @Override
        public void run() {
            try {
                // get the packet that was sent to us. Any changes to the user will be added to the packet.
                 packet = (ServiceTrackProtocol) input.readObject();
                 processClientPacket();
            } catch (IOException ex) {
            	log.println("Error");
            	log.println(ex.getMessage());
                ex.printStackTrace(log);
                log.println("--------------------------------------------------------------------------------------------------");
                packet.setMessage(ex.getMessage());
                packet.setErrorFlag(-1);
            } catch (ClassNotFoundException ex) {
            	log.println("Error");
            	log.println(ex.getMessage());
                ex.printStackTrace(log);
                log.println("--------------------------------------------------------------------------------------------------");
                packet.setMessage(ex.getMessage());
                packet.setErrorFlag(-1);
            } catch (NoSuchAlgorithmException ex) {
            	log.println("Error");
            	log.println(ex.getMessage());
                ex.printStackTrace(log);
                log.println("--------------------------------------------------------------------------------------------------");
                message = "Error encrypting password. Notify System Admin.";
                packet.setMessage(message);
                packet.setErrorFlag(-1);
			} catch (InvalidKeySpecException ex) {
				log.println("Error");
            	log.println(ex.getMessage());
                ex.printStackTrace(log);
                log.println("--------------------------------------------------------------------------------------------------");
                message = "Error encrypting password. Notify System Admin.";
                packet.setMessage(message);
                packet.setErrorFlag(-1);
			} catch (SQLException ex) {
				log.println("Error");
            	log.println(ex.getMessage());
                ex.printStackTrace(log);
                log.println("--------------------------------------------------------------------------------------------------");
                if(ex instanceof SQLIntegrityConstraintViolationException && packet.getPerson() instanceof User)
                	message = "The email " + ((User)packet.getPerson()).getEmail() + " is already registered to a different user";
                else
                	message = "Error processing current operation.";
                packet.setMessage(ex.getMessage());
                packet.setErrorFlag(-1);
			}
            finally {
            	log.flush();
            }
            try {
				output.writeObject(packet);
			} catch (IOException ex) {
				log.println("Error");
            	log.println(ex.getMessage());
                ex.printStackTrace(log);
                log.println("--------------------------------------------------------------------------------------------------");
			} 
            finally {
            	log.flush();
            }
            
            closeQuietly(input);
            closeQuietly(output);
            

        }
        /**
         * Process the query code that was sent in the packet. Any exceptions will be thrown and dealt with in the run portion of our server.
         * @throws SQLException
         * @throws NoSuchAlgorithmException
         * @throws InvalidKeySpecException
         */
        private void processClientPacket() throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
            //get the action code.
        	String code = packet.getActionCode();
        	//This could be a client or a user
           Person person =  packet.getPerson();
            
            
            switch (code){
                //only server specific
                case "NC":
                    db.addNewClient((Client) person);
                    message = "Success in adding a new client.";
                    break;
                case "NS":
                    db.addNewService(packet.getService());
                    message = "The service has been createds.";
                    break;
                case "RS":
                	db.registerNewService((Client) person, packet.getService());
                	message = "Success in registering a new service.";
                	break;
                case "NU":
                	db.addNewUser((User) person);
                	message = "Success the user has been created.";
                	break;
                case "DC":
                	db.deleteClient((Client) person);
                	message = "Success in deleting client.";
                	break;
                case "DS":
                	db.deleteService(packet.getService());
                	message = "Success in deleting the selected service.";
                	break;
                case "DU":
                	db.deleteUser((User) person);
                	message = "Success in deleting the selected user."; 
                	break;
                case "RM":
                	//Will need to loop for this, if removing more than one service.
                	db.removeServiceFromClient((Client) person, packet.getService());
                	message = "success in removing the service";
                	break;
                case "RQS":
                	packet.setService(db.getRequestedService(packet.getService().getServiceID()));
                	message = "Success in getting the service";
                	break;
                case "UC":
                	db.updateClientInfo((Client) person);
                	message = "Success in updating client info.";
                	break;
                case "UU":
                	db.updateUserInfo((User) person);
                	message = "Success in updating user info.";
                	break;
                case "UP":
                	db.updateUserPassword((User) person);
                	message = "Success in upading your password.";
                	break;
                case "NRS":
                	packet.setServices(db.getNonRegisteredServices((Client) person));
                	message = "Success in getting all non registered services.";
                	break;
                case "RG":
                	packet.setServices(db.getRegisteredServices((Client) person));
                	message = "Success in gettng all registered services.";
                	break;
                case "LG":
                	packet.setPerson(db.fetchLogInInfo(((User) person).getEmail(), ((User) person).getPassword()));
                	message = "Success in logging in.";
                	break;
                case "GU":
                	packet.setPerson((Person)(db.getUser(((User) person).getId())));
                	message = "Success in getting user.";
                	break;
                case "GC":
                	packet.setPerson(db.getClient(((Client) person).getId()));
                	message = "Success in getting client.";
                	break;
                case "EAD":
                	packet.setFileBytes(db.exportAllData());
                	break;
                	
            }
            
            packet.setMessage(message);
            packet.setErrorFlag(1);
        }
        
        private void closeQuietly(Closeable resource) {
        	if (resource != null) {
        		try {
					resource.close();
				} catch (IOException e) {
					//This will be ignored.
				}
        	}
        }

    }
}
