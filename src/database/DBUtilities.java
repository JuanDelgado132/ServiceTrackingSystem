/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author juand
 */
public final class DBUtilities {
    
    private final static int SALT_SIZE = 25;
    private  static SecureRandom rand;
    private final static int ITERATIONS = 1000;
    
    //Make the constructor static as to prevent instantiation.
    private DBUtilities(){
        
    }
    
    public static String encryptedPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException{
       PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), ITERATIONS, password.length() * 8);
       SecretKeyFactory secretKey = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
       byte[] encodedPassword = secretKey.generateSecret(keySpec).getEncoded();
       
       return new String(encodedPassword);
    }
    
    public static String generateSalt() throws NoSuchAlgorithmException{
        
        byte[] salt = new byte[SALT_SIZE];
        rand = SecureRandom.getInstance("SHA1PRNG");
        rand.nextBytes(salt);
        return new String(salt);
    }
    
    public static boolean verifyCredentials(String password, String salt, String userPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //boolean isUser = false; not used.
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), ITERATIONS, password.length() * 8);
        SecretKeyFactory secretKey = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        String passwordToVerify = new String(secretKey.generateSecret(keySpec).getEncoded());
        
        return passwordToVerify.equalsIgnoreCase(userPassword);
        
        
    }
    
}
