/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import servicetrackdata.Service;
import servicetrackdata.Client;
import servicetrackdata.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.opencsv.CSVWriter;

import java.util.Random;
import servicetrackdirectories.DirectoryStructure;

/**
 * This class will be used for all database calls, and updates to the database 
 * from the service tracker app.
 */

/**
 *
 * @author juand
 */
public class DBOperations {
    
    private String hostName;
    private String dbName;
    private String dbUsername;
    private String dbPassword;
   // private String sqlQuery;
    /**
     * It is better to use a database connection pool, but for the scope of this project I will be creating a connection in each call along with statements.
     * Do note that in a real world setting it is advised to have a database connection pool as it is costly to keep making connection at each function call.
     */
    //private Connection conn;
    //private PreparedStatement pstmt;
   // private Statement stmt;
    //private ResultSet results;
    private static DBOperations DBOpsSingleton;

    
    
    private DBOperations() throws IOException{
    	Properties databaseSettings = new Properties();

            //load database settings from config.cfg file.
            databaseSettings.load(new FileInputStream(DirectoryStructure.getConfigFile()));
            dbName = databaseSettings.getProperty("DBName");
            dbUsername = databaseSettings.getProperty("DBUsername");
            dbPassword = databaseSettings.getProperty("DBPassword");
            hostName = databaseSettings.getProperty("Host");
            //int tablesExists = Integer.parseInt(databaseSettings.getProperty("DatabaseSetup"));
            //On the off chance that the tables were not created. It will be setup first by the server.
            //if(tablesExists != 1){
              //  initializeDatabaseAndTables();
            
            
            
 
    }
    
    
    public void initializeDatabaseAndTables() throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException{
        String sqlQuery = "CREATE DATABASE ServiceTrackDatabase";
        PreparedStatement pstmt = null;
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(hostName, dbUsername, dbPassword);
            /*pstmt = conn.prepareStatement(sqlQuery);
            System.out.println(dbName);
            pstmt.setString(1, dbName);
            pstmt.executeUpdate();*/
            stmt = conn.createStatement();
            stmt.executeUpdate(sqlQuery);
            releaseResources(conn);
            releaseResources(stmt);
            conn = DriverManager.getConnection(hostName + dbName, dbUsername, dbPassword);
            
            String userTable = "CREATE TABLE USERS(id int(7) NOT NULL, first_name VARCHAR(40), last_name VARCHAR (40),email VARCHAR(50), role VARCHAR(25), address VARCHAR(50), phone VARCHAR(15), user_password VARCHAR (255), salt VARCHAR(255), PRIMARY KEY (id), UNIQUE KEY unique_email (email));";
            String clientTable = "CREATE TABLE CLIENTS (id int(7) NOT NULL, first_name VARCHAR(40), last_name VARCHAR (40), gender VARCHAR (15),  comments VARCHAR(200), birthday DATE, active int(1), PRIMARY KEY (id));";
            String serviceTable = "CREATE TABLE SERVICES (service_id int(7) NOT NULL, service_name VARCHAR(50), service_description VARCHAR(50), days VARCHAR(50), time VARCHAR(50), active int(1), PRIMARY KEY (service_id));";
            String createMonthProcedure =  "CREATE PROCEDURE generate_this_months_table()\r\n" + 
            		"BEGIN\r\n" + 
            		"	\r\n" + 
            		"	SET @tbName = DATE_FORMAT(CURRENT_DATE, 'client_service_%M_%Y');\r\n" + 
            		"    SET @prevTable = DATE_FORMAT(CURRENT_DATE - INTERVAL 1 MONTH, 'client_service_%M_%Y');\r\n" + 
            		"   \r\n" + 
            		"    SET @createTable = CONCAT(\"CREATE TABLE \", @tbName, \" (id int(7), service_id int(7), times_used int(5) DEFAULT 0, last_used DATE, PRIMARY KEY (id, service_id), FOREIGN KEY (id) REFERENCES CLIENTS(id) ON DELETE CASCADE, FOREIGN KEY (service_id) REFERENCES SERVICES(service_id) ON DELETE CASCADE);\");\r\n" + 
            		"    PREPARE stmt FROM @createTable;\r\n" + 
            		"    EXECUTE stmt;\r\n" + 
            		"    \r\n" + 
            		"    SET @doesPrevTableExists = CONCAT(\"SELECT COUNT(*) INTO @doesPrevMonthTableExists FROM information_schema.TABLES WHERE(TABLE_SCHEMA = 'servicetrackdatabase') AND (TABLE_NAME = '\",@prevTable,\"');\");\r\n" + 
            		"    PREPARE stmt FROM @doesPrevTableExists;\r\n" + 
            		"    EXECUTE stmt;\r\n" + 
            		"    \r\n" + 
            		"    IF @doesPrevMonthTableExists = 1 THEN\r\n" + 
            		"    	SET @copyDataFromOldTable = CONCAT(\"INSERT \", @tbName, \" SELECT * FROM \", @prevTable, \" WHERE \", @prevTable, \".id IN(SELECT clients.id FROM clients WHERE clients.active = 1\");\r\n" + 
            		"        PREPARE stmt FROM @copyDataFromOldTable;\r\n" + 
            		"        EXECUTE stmt;\r\n" + 
            		"        SET @clearData = CONCAT(\"UPDATE \", @tbName, \" SET \", @tbname, \".times_used = 0, \", @tbName , \".last_used = null;\");\r\n" + 
            		"   		PREPARE stmt FROM @clearData;\r\n" + 
            		"   		EXECUTE stmt;    \r\n" + 
            		"     END IF;\r\n" + 
            		"     \r\n" + 
            		"     \r\n" + 
            		"        DEALLOCATE PREPARE stmt;\r\n" + 
            		"    \r\n" + 
            		"  END\r\n";
            String createYearProcedure = "CREATE PROCEDURE generate_this_years_table()\r\n" + 
            		"BEGIN\r\n" + 
            		"	\r\n" + 
            		"	SET @tbName = DATE_FORMAT(CURRENT_DATE, 'client_service_%Y');\r\n" + 
            		"	SET @prevTable = DATE_FORMAT(CURRENT_DATE - INTERVAL 1 YEAR, 'client_service_%Y');\r\n" + 
            		"	\r\n" + 
            		"	SET @createTable = CONCAT(\"CREATE TABLE \", @tbName, \" (id int(7), service_id int(7), times_used int(5) DEFAULT 0, last_used DATE, PRIMARY KEY (id, service_id), FOREIGN KEY (id) REFERENCES CLIENTS(id) ON DELETE CASCADE, FOREIGN KEY (service_id) REFERENCES SERVICES(service_id) ON DELETE CASCADE);\");\r\n" + 
            		"    PREPARE stmt FROM @createTable;\r\n" + 
            		"    EXECUTE stmt;\r\n" + 
            		"	\r\n" + 
            		"	SET @doesPrevTableExists = CONCAT(\"SELECT COUNT(*) INTO @doesPrevYearTableExists FROM information_schema.TABLES WHERE(TABLE_SCHEMA = 'servicetrackdatabase') AND (TABLE_NAME = '\",@prevTable,\"');\");\r\n" + 
            		"    PREPARE stmt FROM @doesPrevTableExists;\r\n" + 
            		"    EXECUTE stmt;\r\n" + 
            		"    \r\n" + 
            		"    IF @doesPrevYearTableExists = 1 THEN\r\n" + 
            		"    	SET @copyDataFromOldTable = CONCAT(\"INSERT \", @tbName, \" SELECT * FROM \", @prevTable, \" WHERE \", @prevTable, \".id IN(SELECT clients.id FROM clients WHERE clients.active = 1\");\r\n" + 
            		"        PREPARE stmt FROM @copyDataFromOldTable;\r\n" + 
            		"        EXECUTE stmt;\r\n" + 
            		"        SET @clearData = CONCAT(\"UPDATE \", @tbName, \" SET \", @tbname, \".times_used = 0, \", @tbName , \".last_used = null;\");\r\n" + 
            		"   		PREPARE stmt FROM @clearData;\r\n" + 
            		"   		EXECUTE stmt;    \r\n" + 
            		"     END IF;\r\n" + 
            		"     \r\n" + 
            		"     \r\n" + 
            		"        DEALLOCATE PREPARE stmt;\r\n" + 
            		"    \r\n" + 
            		"  END";
            String createUseServiceProcedure = " CREATE PROCEDURE use_service(IN clientID INT(7), IN serviceID INT(7))\r\n" + 
            		"  BEGIN\r\n" + 
            		"	SET @monthTable = DATE_FORMAT(CURRENT_DATE, 'client_service_%M_%Y');\r\n" + 
            		"	SET @yearTable = DATE_FORMAT(CURRENT_DATE, 'client_service_%Y');\r\n" + 
            		"	SET @currentTime = DATE_FORMAT(CURRENT_TIMESTAMP, '%Y-%m-%d %H:%i:%s');\r\n" + 
            		"    \r\n" + 
            		"	SET @incrementMonthTableService = CONCAT(\"UPDATE \", @monthTable,  \" SET times_used = times_used + 1 , last_used = '\", @currentTime ,\"' WHERE \", @monthTable, \".id = \", clientID ,\" AND \", @monthTable, \".service_id = \", serviceID, \";\");\r\n" + 
            		"	PREPARE stmt FROM @incrementMonthTableService;\r\n" + 
            		"	EXECUTE stmt;\r\n" + 
            		"	\r\n" + 
            		"	SET @incrementYearTableService = CONCAT(\"UPDATE \", @yearTable,  \" SET times_used = times_used + 1 , last_used = '\", @currentTime ,\"' WHERE \", @yearTable, \".id = \", clientID, \" AND \", @yearTable, \".service_id = \", serviceID, \";\");\r\n" + 
            		"	PREPARE stmt FROM @incrementYearTableService;\r\n" + 
            		"	EXECUTE stmt;\r\n" + 
            		"	\r\n" + 
            		"	DEALLOCATE PREPARE stmt;\r\n" + 
            		"END ";
            
            String scheduleMonthService = "CREATE EVENT generate_monthly_table\r\n" + 
            		"	ON SCHEDULE EVERY 1 MONTH\r\n" + 
            		"	STARTS NOW()\r\n" + 
            		"	DO\r\n" + 
            		"		CALL generate_this_months_table();\r\n" + 
            		"\r\n"; 
            String scheduleYearService = "CREATE EVENT generate_yearly_table\r\n" + 
            		"	ON SCHEDULE EVERY 1 YEAR\r\n" + 
            		"	STARTS NOW()\r\n" + 
            		"	DO\r\n" + 
            		"		CALL generate_this_years_table();";
            String initMonthTable = "Call generate_this_months_table();";
            String initYearTable = "CALL generate_this_years_table();";
            
            stmt = conn.createStatement();
            stmt.executeUpdate(userTable);
            stmt.executeUpdate(clientTable);
            stmt.executeUpdate(serviceTable);
           
            stmt.execute(createYearProcedure);
            stmt.execute(createMonthProcedure);
            stmt.execute(createUseServiceProcedure);
            stmt.execute(scheduleMonthService);
            stmt.execute(scheduleYearService);
            stmt.execute(initMonthTable);
            stmt.execute(initYearTable);
            
        } 
        catch (SQLException ex) {
        	throw ex;
        }
        finally{
            releaseResources(conn);
            releaseResources(pstmt);
            releaseResources(stmt);
            
            
        }
        
       /* //Create the default admin user.
        var user = new User("admin", "admin", 78787878, "admin@goodneighborsh.org", "Administrator", "@dmin45", "542 E. Street", "(956)656-9822");
        addNewUser(user);*/
        
    }
    /**
     * Get our singleton.
     * @return 
     * @author Juan Delgado
     * @throws IOException 
     */
    public static DBOperations getDBSingleton() throws IOException{
        if(DBOpsSingleton == null){ //Does the singleton exist.
            synchronized(DBOperations.class){ //We check if it exists again, but this time only one thread may check. And if it does not then it will create, so when it releases the lock the other thread will detect that it was created and skip over it.
                if(DBOpsSingleton == null)
                    DBOpsSingleton = new DBOperations();
            }
        }
        
        return DBOpsSingleton;
    }
    
    /*public static synchronized DBOperations getDBSingleton() throws IOException {
    	if(DBOpsSingleton == null) {
    		DBOpsSingleton = new DBOperations();
    	}
    	
    	return DBOpsSingleton;
    }*/
    
    public void addNewUser(User newUser) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
        String sqlQuery = "INSERT INTO USERS (id, first_name, last_name, email, role, address, phone, user_password, salt) VALUES (?,?,?,?,?,?,?,?,?)";
        String salt;
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            salt = DBUtilities.generateSalt();
            newUser.setPassword(DBUtilities.encryptedPassword(newUser.getPassword(), salt));
            newUser.setId(generateRandomID('u'));
            conn = DriverManager.getConnection(hostName + dbName, dbUsername, dbPassword);
            pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setInt(1, newUser.getId());
            pstmt.setString(2, newUser.getFirstName());
            pstmt.setString(3, newUser.getLastName());
            pstmt.setString(4, newUser.getEmail());
            pstmt.setString(5, newUser.getRole());
            pstmt.setString(6, newUser.getAddress());
            pstmt.setString(7, newUser.getPhoneNumber());
            pstmt.setString(8, newUser.getPassword());
            pstmt.setString(9, salt);
            pstmt.executeUpdate();
            
        } catch (NoSuchAlgorithmException ex) {
        	throw ex;
        } catch (InvalidKeySpecException ex) {
        	throw ex;
        } 
        catch(SQLIntegrityConstraintViolationException ex) {
        	throw ex;
        }catch (SQLException ex) {
        	throw ex;
        }
        finally{
           releaseResources(conn);
           releaseResources(pstmt);
        }
        
    }
    
    /**
     * 
     * @param user 
     * @author Juan Delgado
     * Update user query, cant change id, or name.
     * @throws SQLException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeySpecException 
     */
    public void updateUserInfo(User user) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException{
        String sqlQuery = "UPDATE USERS SET USERS.email = ?, USERS.role = ?, USERS.address = ?, USERS.phone = ?, USERS.user_password = ?, USERS.salt = ? WHERE USERS.id = ?";
        PreparedStatement pstmt = null;
        Connection conn = null;
        String salt = null;
        try {
        	salt = DBUtilities.generateSalt();
            user.setPassword(DBUtilities.encryptedPassword(user.getPassword(), salt));
            conn = DriverManager.getConnection(hostName + dbName, dbUsername, dbPassword);
            pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getRole());
            pstmt.setString(3, user.getAddress());
            pstmt.setString(4, user.getPhoneNumber());
            pstmt.setString(5, user.getPassword());
            pstmt.setString(6, salt);
            pstmt.setInt(7, user.getId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
        	throw ex; //Server will take care of the exception we just want to make sure the database connection closses.
        } catch (NoSuchAlgorithmException ex) {
			// TODO Auto-generated catch block
        	throw ex;
		} catch (InvalidKeySpecException ex) {
			// TODO Auto-generated catch block
			throw ex;
		}
        finally {
            releaseResources(conn);
            releaseResources(pstmt);
        }
    }
    /**
     * Returns the user based on the id.
     * @param userId
     * @return
     * @author Juan Delgado
     * Standard query update stmt.
     * @throws SQLException 
     */
    public User getUser(int userId) throws SQLException{
        String sqlQuery = "SELECT * FROM USERS WHERE USERS.id = ?";
        User user = null;
        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet results = null;
        try {
            conn = DriverManager.getConnection(hostName + dbName, dbUsername, dbPassword);
            pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setInt(1, userId);
            results = pstmt.executeQuery();
            if(results.first())
            	user = new User(results.getString("first_name"), results.getString("last_name"), results.getInt("id"), results.getString("email"), results.getString("role"), results.getString("user_password"), results.getString("address"), results.getString("phone"));
        } catch (SQLException ex) {
            throw ex;
        }
        finally {
            releaseResources(conn);
            releaseResources(pstmt);
            releaseResources(results);
        }
        
        return user;
    }
    /**
     * Retrieve client based on id.
     * @param clientID
     * @return
     * @throws SQLException
     */
    public Client getClient(int clientID) throws SQLException {
    	String sqlQuery = "SELECT * FROM CLIENTS WHERE CLIENTS.id = ?";
    	Client client = null;
    	PreparedStatement pstmt = null;
    	Connection conn = null;
    	ResultSet results = null;
    	try {
			conn = DriverManager.getConnection(hostName + dbName, dbUsername, dbPassword);
			pstmt = conn.prepareStatement(sqlQuery);
			pstmt.setInt(1, clientID);
			results = pstmt.executeQuery();
			if(results.first())
				client = new Client(results.getString("first_name"), results.getString("last_name"), results.getInt("id"), results.getString("gender"), results.getString("birthday"), results.getString("comments"), results.getInt("active"));
			
		} catch (SQLException ex) {
			throw ex;
		} finally {
            releaseResources(conn);
            releaseResources(pstmt);
            releaseResources(results);
        }
    	return client;
    }
    
    /**
     * Query that will be used once user log in.
     * @param email
     * @param password
     * @return 
     * @throws SQLException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeySpecException 
     */
    public User fetchLogInInfo(String email, String password) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException{
        String sqlQuery = "SELECT * FROM USERS WHERE USERS.email = ?";
        User user = null;
        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet results = null;
        try {
            conn = DriverManager.getConnection(hostName + dbName, dbUsername, dbPassword);
            pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setString(1, email);
            results = pstmt.executeQuery();
            //Check if anything was returned.
            if(results.first()) {
            	if(DBUtilities.verifyCredentials(password, results.getString("salt"), results.getString("user_password"))){
            		user = new User(results.getString("first_name"), results.getString("last_name"), results.getInt("id"), results.getString("email"), results.getString("role"), results.getString("user_password"), results.getString("address"), results.getString("phone"));
            	}
            }
        } catch (SQLException ex) {
            throw ex;
        } catch (NoSuchAlgorithmException ex) {
            throw ex;
        } catch (InvalidKeySpecException ex) {
            throw ex;
        }
        finally {
            releaseResources(conn);
            releaseResources(pstmt);
            releaseResources(results);
        }
        
        return user;
    }
    /**
     * Set new password for the user and set a new salt.
     * @param user
     * @author Juan Delgado
     * @throws SQLException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeySpecException 
     */
    public void updateUserPassword(User user) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        
        String sqlQuery = "UPDATE USERS SET USERS.user_password = ?, USERS.salt = ? WHERE USERS.id = ?";
        String salt;
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            salt = DBUtilities.generateSalt();
            user.setPassword(DBUtilities.encryptedPassword(user.getPassword(), salt));
            conn = DriverManager.getConnection(hostName + dbName, dbUsername, dbPassword);
            pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, salt);
            pstmt.setInt(3, user.getId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        } catch (NoSuchAlgorithmException ex) {
            throw ex;
        } catch (InvalidKeySpecException ex) {
            throw ex;
        }
        finally {
            releaseResources(conn);
            releaseResources(pstmt);
        }   
    }
    /**
     * Add a new service to the current service list.
     * @param newService 
     * @author Juan Delgado
     * @throws SQLException 
     */
    public void addNewService(Service newService) throws SQLException{
        String sqlQuery = "INSERT INTO SERVICES(service_id, service_name, service_description,days, time, active)VALUES(?,?,?,?,?,?)";
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
           conn = DriverManager.getConnection(hostName + dbName, dbUsername, dbPassword);
           newService.setServiceID(generateRandomID('S'));
           pstmt = conn.prepareStatement(sqlQuery);
           pstmt.setInt(1, newService.getServiceID());
           pstmt.setString(2, newService.getServiceName());
           pstmt.setString(3, newService.getServiceDescription());
           pstmt.setString(4, newService.getDays());
           pstmt.setString(5, newService.getTime());
           pstmt.setInt(6, newService.getActiveStatus());
           pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        }
        finally {
            releaseResources(conn);
            releaseResources(pstmt);
        }
    }
    
    public Service getRequestedService(int serviceID) throws SQLException{
        String sqlQuery = "SELECT * FROM SERVICES WHERE SERVICES.service_id = ?";
        Service service = null;
        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet results = null;
        try {
            conn = DriverManager.getConnection(hostName + dbName, dbUsername, dbPassword);
            pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setInt(1, serviceID);
            results = pstmt.executeQuery();
            if(results.first()){
                service = new Service(results.getInt("service_id"),results.getString("service_name"), results.getString("service_description"), results.getString("days"), results.getString("time"), results.getInt("active"));
            }
        } catch (SQLException ex) {
            throw ex;
        }
        finally {
            releaseResources(conn);
            releaseResources(pstmt);
            releaseResources(results);
        }
        
        return service;      
    }
    
    public void registerNewService(Client client, Service serviceToRegister) throws SQLException{
    	String yearPattern = "yyyy";
    	String monthPattern = "MMMM_yyyy";
    	String yearTable = null;
    	String monthTable = null;
    	SimpleDateFormat formatter = new SimpleDateFormat(yearPattern);
    	yearTable = "client_service_" + formatter.format(new Date());
    	formatter.applyPattern(monthPattern);
    	monthTable = "client_service_" + formatter.format(new Date());
        String insertIntoYearQuery = "INSERT INTO @tableName (id, service_id)VALUES(?,?)";
        String insertIntoMonthQuery = "INSERT INTO @tableName (id, service_id)VALUES(?,?)";
        insertIntoYearQuery = insertIntoYearQuery.replace("@tableName", yearTable);
        insertIntoMonthQuery = insertIntoMonthQuery.replace("@tableName", monthTable);
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(hostName + dbName, dbUsername, dbPassword);
            pstmt = conn.prepareStatement(insertIntoYearQuery);
            pstmt.setInt(1, client.getId());
            pstmt.setInt(2, serviceToRegister.getServiceID());
            pstmt.executeUpdate();
            pstmt.close();
            pstmt = conn.prepareStatement(insertIntoMonthQuery);
            pstmt.setInt(1, client.getId());
            pstmt.setInt(2, serviceToRegister.getServiceID());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        }
        finally{
            releaseResources(conn);
            releaseResources(pstmt);
        }
        
        
    }
    /**
     * Return a hashmap of a services a client is registered to.
     * @param client
     * @return
     * @author Juan Delgado
     * @throws SQLException 
     */
    public HashMap<Integer, Service> getRegisteredServices(Client client) throws SQLException{
    	String yearPattern = "yyyy";
    	String yearTable = null;
    	SimpleDateFormat formatter = new SimpleDateFormat(yearPattern);
    	yearTable = "client_service_" + formatter.format(new Date());
    	String sqlQuery = "SELECT * FROM SERVICES WHERE SERVICES.service_id IN (SELECT @tableName.service_id FROM  @tableName WHERE  @tableName.id = ?)";
        //SQL injection is not possible since user is not inputing table name.
        sqlQuery = sqlQuery.replace("@tableName", yearTable);
        HashMap<Integer, Service> services = new HashMap<>();
        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet results = null;
        try {
            conn = DriverManager.getConnection(hostName + dbName, dbUsername, dbPassword);
            pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setInt(1, client.getId());
            results = pstmt.executeQuery();
            //We do not want to show the inactive services for use to the clients.
            while(results.next()){
            	if(results.getInt("active") == 0)
            		continue;
                services.put(results.getInt("service_id"), new Service(results.getInt("service_id"),results.getString("service_name"), results.getString("service_description"), results.getString("days"), results.getString("time"), results.getInt("active")));
            }
        } catch (SQLException ex) {
            throw ex;
        }
        finally{
            releaseResources(conn);
            releaseResources(pstmt);
            releaseResources(results);
        }
        return services;
    }
    /**
     * Get all services that are not registered to a client.
     * @param client
     * @return 
     * @author Juan Delgado
     * @throws SQLException 
     */
    public HashMap<Integer, Service> getNonRegisteredServices(Client client) throws SQLException{
    	String yearPattern = "yyyy";
    	String yearTable = null;
    	SimpleDateFormat formatter = new SimpleDateFormat(yearPattern);
    	yearTable = "client_service_" + formatter.format(new Date());
        String sqlQuery = "SELECT * FROM SERVICES WHERE SERVICES.service_id NOT IN (SELECT @tableName.service_id FROM  @tableName WHERE  @tableName.id = ?)";
        //SQL injection is not possible since user is not inputing table name.
        sqlQuery = sqlQuery.replace("@tableName", yearTable);
        HashMap<Integer, Service> services = new HashMap<>();
        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet results = null;
        try {
            conn = DriverManager.getConnection(hostName + dbName, dbUsername, dbPassword);
            pstmt = conn.prepareStatement(sqlQuery);
           
            pstmt.setInt(1, client.getId());
            results = pstmt.executeQuery();
            while(results.next()){
            	//If a service is inactive we do not want to put it up for registration.
            	if(results.getInt("active") == 0)
            		continue;
                services.put(results.getInt("service_id"), new Service(results.getInt("service_id"),results.getString("service_name"), results.getString("service_description"), results.getString("days"), results.getString("time"), results.getInt("active")));
            }
        } catch (SQLException ex) {
            throw ex;
        }
        finally{
            releaseResources(conn);
            releaseResources(pstmt);
            releaseResources(results);
        }
        return services;
    }
    /**
     * Remove services from a client. Might not be used to preserve client data.
     * @param client
     * @param serviceToRemove
     * @author Juan Delgado
     * @throws SQLException 
     */
    public void removeServiceFromClient(Client client, Service serviceToRemove) throws SQLException{
        String sqlQuery = "DELETE FROM CLIENT_SERVICE_REL WHERE CLIENT_SERVICE_REL.id = ? AND CLIENT_SERVICE_REL.service_id = ?";
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(hostName + dbName, dbUsername, dbPassword);
            pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setInt(1, client.getId());
            pstmt.setInt(2, serviceToRemove.getServiceID());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        }
        finally{
            releaseResources(conn);
            releaseResources(pstmt);
        }
        
    }
    /**
     * Update the requested service.
     * @param serviceToUpdate
     * @throws SQLException
     */
    public void updateService(Service serviceToUpdate) throws SQLException {
    	String sqlQuery = "UPDATE SERVICES SET SERVICES.service_name = ?, SERVICES.days = ?, SERVICES.time = ?, SERVICES.service_description = ? WHERE SERVICES.service_id = ?;";
    	PreparedStatement pstmt = null;
    	Connection conn = null;
    	
    	try {
			conn = DriverManager.getConnection(hostName + dbName, dbUsername, dbPassword);
			pstmt = conn.prepareStatement(sqlQuery);
			pstmt.setString(1, serviceToUpdate.getServiceName());
			pstmt.setString(2, serviceToUpdate.getDays());
			pstmt.setString(3, serviceToUpdate.getTime());
			pstmt.setString(4, serviceToUpdate.getServiceDescription());
			pstmt.setInt(5, serviceToUpdate.getServiceID());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			releaseResources(conn);
			releaseResources(pstmt);
		}
    }
    /**
     * Register a new client to system, so that they can begin to enroll in our services.
     * @param clientToAdd
     * @author Juan Delgado
     * @throws SQLException 
     */
    public void addNewClient(Client clientToAdd) throws SQLException{
        String sqlQuery = "INSERT INTO CLIENTS(id, first_name, last_name, gender, comments, birthday, active)VALUES(?,?,?,?,?,?,?)";
        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet results = null;
        try {
        	clientToAdd.setId(generateRandomID('c'));
            conn = DriverManager.getConnection(hostName + dbName, dbUsername, dbPassword);
            pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setInt(1, clientToAdd.getId());
            pstmt.setString(2, clientToAdd.getFirstName());
            pstmt.setString(3, clientToAdd.getLastName());
            pstmt.setString(4, clientToAdd.getGender());
            pstmt.setString(5, clientToAdd.getComments());
            pstmt.setString(6, clientToAdd.getBirthDay());
            pstmt.setInt(7, clientToAdd.getActiveStatus());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        }
        finally{
           releaseResources(conn);
           releaseResources(pstmt);
           releaseResources(results);
        }
    }
    
    /**
     * The only mutable field is the comments section.
     * @param client 
     * @throws SQLException 
     */
    public void updateClientInfo(Client client) throws SQLException{
        String sqlQuery = "UPDATE CLiENTS SET CLIENTS.comments = ?";
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(hostName + dbName, dbUsername, dbPassword);
            pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setString(1, client.getComments());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
        	throw ex;
        }
    }
    
    public void deleteUser(User userToDelete) throws SQLException{
        String sqlQuery = "DELETE FROM USERS WHERE USERS.id = ?";
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(hostName + dbName, dbUsername, dbPassword);
            pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setInt(1, userToDelete.getId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        }
        finally{
            releaseResources(pstmt); 
            releaseResources(conn);
        }
        
    }
    
    /**
     * Marks the service as inactive or active.
     * @param serviceToDeactivate
     * @throws SQLException
     */
    public void changeServiceStatus(Service serviceToDeactivate) throws SQLException{
        String sqlQuery = "UPDATE SERVICES SET SERVICES.active = ? WHERE SERVICES.service_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection(hostName + dbName, dbUsername, dbPassword);
            pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setInt(1, serviceToDeactivate.getActiveStatus());
            pstmt.setInt(2, serviceToDeactivate.getServiceID());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        }
        finally{
            releaseResources(pstmt);
            releaseResources(conn);
        }
        
        
    }
    
     public void deactivateClient(Client clientToDeactivate) throws SQLException{
        String sqlQuery = "UPDATE CLIENTS SET CLIENTS.active = ? WHERE CLIENTS.id = ?";
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(hostName + dbName, dbUsername, dbPassword);
            pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setInt(1, clientToDeactivate.getId());
            pstmt.setInt(2, clientToDeactivate.getActiveStatus());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        }
        finally{
            releaseResources(pstmt);
            releaseResources(conn);
        }
        
        
    }
     public void userService(Client client, Service serviceToUse) throws SQLException {
    	 String sqlQuery = "CALL use_service(?,?)";
    	 CallableStatement stmt = null;
    	 Connection conn = null;
    	 try {
			conn = DriverManager.getConnection(hostName + dbName, dbUsername, dbPassword);
			stmt = conn.prepareCall(sqlQuery);
			stmt.setInt(1, client.getId());
			stmt.setInt(2, serviceToUse.getServiceID());
			stmt.execute();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			releaseResources(stmt);
			releaseResources(conn);
		}
     }
     /**
      * 
      * @return
      * @author JuanD
     * @throws SQLException 
     * @throws IOException 
      */
     public byte[] exportAllData(String requestedDate) throws SQLException, IOException {
    	
    	 String selectedTable = "client_service_" + requestedDate;
    	 String sqlQuery = "SELECT clients.*, services.*, @tableName.times_used, @tableName.last_used FROM "
    	 		+ "@tableName INNER JOIN clients ON @tableName.id = clients.id "
    	 		+ "INNER JOIN services ON @tableName.service_id = services.service_id;" ;
    	 String fileName = "service_use.csv";
    	 sqlQuery = sqlQuery.replace("@tableName", selectedTable);
    	 Statement stmt = null;
    	 File csvFile = new File(fileName);
    	 ResultSet results = null;
    	 Connection conn = null;
    	 byte[] fileBytes = null;
    	 
    	 try {
    		 FileWriter csvFileWriter = new FileWriter(csvFile);
    		 CSVWriter csvWriter = new CSVWriter(csvFileWriter);
			conn = DriverManager.getConnection(hostName + dbName, dbUsername, dbPassword);
			stmt = conn.createStatement();
			results = stmt.executeQuery(sqlQuery);
			if(results.first())
				csvWriter.writeAll(results, true);
			else
				csvWriter.writeNext(new String[] {"No data has been created yet."});
			csvWriter.close();
			fileBytes = Files.readAllBytes(csvFile.toPath());
			
		} catch (SQLException ex) {
			throw ex;
		} catch (IOException ex) {
			throw ex;
			
		} finally {
			releaseResources(conn);
			releaseResources(results);
			releaseResources(stmt);
		}
    	 //Delete the file we do not have to store it in the server, this is just a temp file.
    	 csvFile.delete();
    	 return fileBytes;
     }
    /**
     * This will generate a  random ID for any new user.
     * @param table
     * @return
     * @throws SQLException 
     * @author Juan Delgado.
     */        
    /**
     * This will generate a  random ID for any new user.
     * @param table
     * @return
     * @throws SQLException 
     * @author Juan Delgado.
     */
    private int generateRandomID(char table) throws SQLException{
        //switch case to differentiate between the different tables.
    	String sqlQuery;
        switch (table){
            case 'u':
            case 'U':
                sqlQuery = "SELECT * FROM USERS WHERE USERS.id = ?";
                break;
            case 'c':
            case 'C':
            sqlQuery = "SELECT * FROM CLIENTS WHERE CLIENTS.id = ?";
            break;
            case 's':
            case 'S':
                sqlQuery = "SELECT * FROM SERVICES WHERE SERVICES.id = ?";
                break;
        }
        
        Random randomNum = new Random();
        int id =  randomNum.nextInt(9000000) + 1000000; //get a 7 digit int from 0 to 9999999
        boolean idAvailable = true;
        sqlQuery = "SELECT * FROM USERS WHERE id = ?";
        Connection conn = DriverManager.getConnection(hostName + dbName, dbUsername, dbPassword);
        ResultSet results = null;
        do {
            
           PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
           pstmt.setInt(1, id);
           results = pstmt.executeQuery();
           if(results.first()){ //If there was any row returned with the resultset we must run it again. To ensure that the id is unique, and not trigger a database error.
               idAvailable = false; //idAvailble set to false;
               id = randomNum.nextInt(9000000) + 1000000; //Re-calculate the id.
               releaseResources(conn);
           }
           releaseResources(pstmt);
           releaseResources(results);
           
        }while(!idAvailable);
        
        return id;
    }
    /**
     * Closes ResultSet, Statement, and database connection variables.
     * @param dbOp 
     * @author Juan Delgado.
     */
    private void releaseResources (AutoCloseable dbOp){
    	if(dbOp != null) {
    		try {
    			dbOp.close();
    		} catch (Exception ex) {
    			Logger.getLogger(DBOperations.class.getName()).log(Level.SEVERE, null, ex);
    		}
    	}
    }
    
}