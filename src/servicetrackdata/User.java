/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicetrackdata;

/**
 *
 * @author juand
 */
@SuppressWarnings("serial")
public class User extends Person{
    private String email;
    private String role;
    private String password;
    private String address;
    private String phoneNumber;
    //private String salt;
    
    public User(){
        super();
        email = " ";
        role = " "; 
        password = " ";
        address = " ";
        phoneNumber = "";
    }
    
    public User(String firstName, String lastName, int id, String email, String role, String password, String address, String phoneNumber){
        super(firstName, lastName, id);
        this.email = email;
        this.role = role;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
    
    public User(User userCopy) {
    	super(userCopy.getFirstName(), userCopy.getLastName(), userCopy.getId());
    	this.email = userCopy.email;
    	this.role = userCopy.role;
    	this.password = userCopy.password;
    	this.address = userCopy.getAddress();
    	this.phoneNumber = userCopy.getPhoneNumber();
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    /**
     * Calculate hash code for the user object.
     * @override
     */
    public int hashCode() {
    	int hash = 18;
    	int weight = 35;
    	
    	hash = weight *  hash + super.getFirstName().hashCode();
    	hash = weight *  hash + super.getLastName().hashCode();
    	hash = weight * hash + super.getId();
    	hash = weight * hash + email.hashCode();
    	hash = weight * hash + role.hashCode();
    	hash = weight * hash + password.hashCode();
    	hash = weight * hash + phoneNumber.hashCode();
    	hash = weight * hash + address.hashCode();
    	
    	return hash;
    	
    	
    }
    /**
     * Check if two user object are equal.
     * @override
     * 
     */
    public boolean equals(Object obj) {
    	if(!(obj instanceof User)) {
    		return false;
    	}
    	User user = (User) obj;
    	
    	return super.getFirstName().equalsIgnoreCase(user.getFirstName()) 
    			&& super.getLastName().equalsIgnoreCase(user.getLastName())
    			&& super.getId() == user.getId()
    			&& email.equalsIgnoreCase(user.email)
    			&& role.equalsIgnoreCase(user.role)
    			&& password.equalsIgnoreCase(user.password)
    			&& phoneNumber.equalsIgnoreCase(user.phoneNumber)
    			&& address.equalsIgnoreCase(user.address);
    	
    }
    /**
     * Returns the object in string.
     */
    public String toString(){
        return super.toString() 
                + "\nEmail: " + email
                + "\nRole " + role
                + "\nAddress: " + address
                + "\n Phone: " + phoneNumber;
    }
}
