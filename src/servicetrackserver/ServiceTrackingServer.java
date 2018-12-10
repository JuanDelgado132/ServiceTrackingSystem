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
import java.util.Scanner;
import servicetrackdata.Client;
import servicetrackdata.Person;
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
	private ServerSocket serverConnection; // use in case we need to shutdown the server we must keep track of it in the
											// main thread to close the method.
	private int numOfThreads;
	private int port;
	private Scanner input;
	private static PrintWriter log;
	private static Properties prop;
	private static DBOperations DB;

	public ServiceTrackingServer() {
		// initialize the scanner for first time setup.
		input = new Scanner(System.in);

	}

	public void startServer() {
		// Initialize all server settings.
		init();
		masterThread = new Thread(new ConnectionManager());
		masterThread.start();
		mainMenu();

	}

	public void init() {

		// If the object remains as null, then we know the operation for whatever reason
		// failed.
		prop = new Properties();
		boolean serverSettingsLoaded = false;

		try {
			prop.load(new FileInputStream(DirectoryStructure.getConfigFile()));
			port = Integer.parseInt(prop.getProperty("Port"));
			numOfThreads = Integer.parseInt(prop.getProperty("NumberOfThreads"));
			serverSettingsLoaded = true;
		} catch (IOException ex) {
			logError(ex);

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
		while (start) {
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Please choose one of the following options.");
			System.out.println("1 to enter a new user.");
			System.out.println("2 to shutdown the server.");
			System.out.println("-----------------------------------------------------------------");
			if (input.hasNextInt())
				choice = input.nextInt();
			else
				continue;

			try {
				processChoice(choice);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException | SQLException ex) {
				logError(ex);
				ex.printStackTrace();
			}

		}
	}

	protected void processChoice(int choice) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
		switch (choice) {
		case 1:
			DB.addNewUser(addUser());
			break;
		case 2:

			break;
		default:
			System.out.println("Error re-enter your choice.");

		}
	}

	/**
	 * Method creates a new user.
	 * 
	 * @return User
	 */
	protected User addUser() {
		// clear buffer
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
		} while (!(role.equalsIgnoreCase("staff")) && !(role.equalsIgnoreCase("Admin")));

		System.out.println("Please enter the user's password");
		password = input.nextLine();

		System.out.println("Please enter the user's address");
		address = input.nextLine();

		System.out.println("Please enter the user's phone number");
		phoneNumber = input.nextLine();

		// Clear the buffer
		// input.nextLine();

		userToAdd = new User(firstName, lastName, id, email, role, password, address, phoneNumber);

		return userToAdd;
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
		
		//Create the log file, so that it can be ready for use.
		DirectoryStructure.createLogFile();

		

		try {
			// DirectoryStructure.createFiles();
			DirectoryStructure.createConfigFile();

			prop.store(new FileOutputStream(DirectoryStructure.getConfigFile()), null);

		} catch (FileNotFoundException ex) {

			logError(ex);
			ex.printStackTrace();
		} catch (IOException ex) {
			// Logger.getLogger(ServiceTrackingServer.class.getName()).log(Level.SEVERE,
			// null, ex);
			logError(ex);
			ex.printStackTrace();
		}
		// No longer needed. Have the JVM Garbage collector remove it from memory.
		prop = null;

		try {
			var db = DBOperations.getDBSingleton();
			db.initializeDatabaseAndTables();
		} catch (IOException ex) {
			logError(ex);
			ex.printStackTrace();
		} catch (SQLException ex) {
			logError(ex);
			ex.printStackTrace();
			
		} catch (NoSuchAlgorithmException ex) {
			logError(ex);
			ex.printStackTrace();
		} catch (InvalidKeySpecException ex) {
			logError(ex);
			ex.printStackTrace();
		} 
		successful = true;
		return successful;

	}
	/**
	 * Function that will log all errors should they occur. Synchronized since multiple threads may be using this function if they encounter and 
	 * error.
	 * @param exception
	 */
	public synchronized void logError(Exception exception) {
		try {
			log = new PrintWriter(new FileOutputStream(DirectoryStructure.getLogFile(), true));
			} catch (FileNotFoundException ex) {
				// If file does not exist then we catch it and create the file.
				DirectoryStructure.createLogFile();
				try {
					log = new PrintWriter(new FileOutputStream(DirectoryStructure.getLogFile(), true));
					} catch (FileNotFoundException e) {
						// Ignore, the file was created, so it will not throw an error.
						}
				}
		//Skip a line so that the log remains properly formated.
		log.println();
		log.println("Error");
		log.println(exception.getMessage());
		exception.printStackTrace(log);
		log.println("--------------------------------------------------------------------------------------------------");
		
		log.close();
		
	}

	class ConnectionManager implements Runnable {

		private Socket clientConnection;

		public ConnectionManager() {
			try {
				//Creates the server socket at the specified port.
				serverConnection = new ServerSocket(port, 50, InetAddress.getLocalHost());
			} catch (UnknownHostException ex) {
				//Log the error.
				logError(ex);

			} catch (IOException ex) {
				//Log the error.
				logError(ex);

			}

		}

		@Override
		public void run() {

			try {
				while (true) {
					//Accept the connection from client.
					clientConnection = serverConnection.accept();
					//Add the client connection to the thread pool.
					serverThreadPool.execute(new ServerTask(clientConnection.getOutputStream(), clientConnection.getInputStream()));
				}
			} catch (SocketException ex) {
				// serverThreadPool. HandleShutdown.
				logError(ex);
			} catch (IOException ex) {
				logError(ex);
			}
		}

	}
	/**
	 * Main functionality for the server.
	 * Handle all client requests and process accordingly.
	 * @author juand
	 *
	 */
	class ServerTask implements Runnable {

		private ObjectOutputStream output;
		private ObjectInputStream input;
		private ServiceTrackProtocol packet;
		private String message;
		private DBOperations db;

		public ServerTask(OutputStream output, InputStream input) {
			try {
				//Save the output stream and the input stream of the server. They are object streams since we will be writing only objects to the server.
				this.output = new ObjectOutputStream(output);
				this.input = new ObjectInputStream(input);
				//Get the Database singleton.
				db = DBOperations.getDBSingleton();
			} catch (IOException ex) {
				logError(ex);
				// Destroy thread.
			} 
		}
		@Override
		/**
		 * Client packet is received and process accordingly.
		 * Any errors that are caught are logged and added to the packer as an error message. A code of -1 is added to the packet to 
		 * let the client know that the operation has failed.
		 * @author juand
		 */
		public void run() {
			try {
				// get the packet that was sent to us. Any changes to the user will be added to
				// the packet. This way we do not recreate a packet each time.
				packet = (ServiceTrackProtocol) input.readObject();
				processClientPacket();
			} catch (IOException ex) {
				logError(ex);
				packet.setMessage(ex.getMessage());
				packet.setErrorFlag(-1);
			} catch (ClassNotFoundException ex) {
				logError(ex);
				packet.setMessage(ex.getMessage());
				packet.setErrorFlag(-1);
			} catch (NoSuchAlgorithmException ex) {
				logError(ex);
				message = "Error encrypting password. Notify System Admin.";
				packet.setMessage(message);
				packet.setErrorFlag(-1);
			} catch (InvalidKeySpecException ex) {
				logError(ex);
				message = "Error encrypting password. Notify System Admin.";
				packet.setMessage(message);
				packet.setErrorFlag(-1);
			} catch(SQLIntegrityConstraintViolationException ex) {
				logError(ex);
				message = "The email " + ((User) packet.getPerson()).getEmail()
						+ " is not available.";
				packet.setMessage(message);
				packet.setErrorFlag(-1);
			} catch (SQLException ex) {
				logError(ex);
				message = "Error processing current operation.";
				packet.setMessage(ex.getMessage());
				packet.setErrorFlag(-1);
			} 
			
			try {
				output.writeObject(packet);
			} catch (IOException ex) {
				logError(ex);
			}
			
			closeQuietly(input);
			closeQuietly(output);

		}

		/**
		 * Process the query code that was sent in the packet. Any exceptions will be
		 * thrown and dealt with in the run portion of our ServerTask class.
		 * 
		 * @throws SQLException
		 * @throws NoSuchAlgorithmException
		 * @throws InvalidKeySpecException
		 * @throws IOException 
		 */
		private void processClientPacket() throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException, IOException {
			// get the action code.
			String code = packet.getActionCode();
			// This could be a client or a user
			Person person = packet.getPerson();
			//Perform the action specified by the code.
			switch (code) {

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
				db.deactivateClient((Client) person);
				message = "Success in deleting client.";
				break;
			case "CHS":
				db.changeServiceStatus(packet.getService());
				message = "Success in changing service status.";

				break;
			case "DU":
				db.deleteUser((User) person);
				message = "Success in deleting the selected user.";
				break;
			case "RM":
				
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
			case "US":
				db.updateService(packet.getService());
				message = "Service has been updated.";
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
				packet.setPerson((Person) (db.getUser(((User) person).getId())));
				message = "Success in getting user.";
				break;
			case "GC":
				packet.setPerson(db.getClient(((Client) person).getId()));
				message = "Success in getting client.";
				break;
			case "EAD":
				packet.setFileBytes(db.exportAllData(packet.getSelectedDate()));
				break;
			case "CUS":
				db.userService((Client) person, packet.getService());
				message = "Service has been marked as used.";
			}

			packet.setMessage(message);
			packet.setErrorFlag(1);
		}

		private void closeQuietly(Closeable resource) {
			if (resource != null) {
				try {
					resource.close();
				} catch (IOException e) {
					// This will be ignored.
				}
			}
		}

	}
}
