/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicetrackdata;

import java.io.Serializable;

/**
 *
 * @author juand
 * 
 * class is abstract to prevent any instantiation of the class.
 */
public abstract class Person implements Serializable {
    private String firstName;
    private String lastName;
    private int id;
    
    
    public Person(){
        firstName = " ";
        lastName = " ";
        id = 0;
    }
    public Person(String firstName, String lastName, int id){
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String toString(){
        return "First Name: " + firstName
                + "\nLast Name: " + lastName
                + "\nID: " + id;
    }
}
