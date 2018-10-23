/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicetracknetwork;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import servicetrackdata.Person;
import servicetrackdata.Service;

/**
 *
 * @author juand
 */
public class ServiceTrackProtocol implements Serializable {
    
    private String actionCode;
    private Person user;
    private Service service;
    private String message;
    private HashMap<Integer,Service> services;
    private ArrayList<Person> persons;
    
    public ServiceTrackProtocol(){
        actionCode = null;
        user = null;
        service = null;
        message = null;
    }
    public ServiceTrackProtocol(String actionCode, Person user, Service service) {
        this.actionCode = actionCode;
        this.user = user;
        this.service = service;
        message = null;
        services = null;
        persons = null;
    }
    
    public ServiceTrackProtocol(String actionCode, Person user, Service service, String message) {
        this.actionCode = actionCode;
        this.user = user;
        this.service = service;
        this.message = message;
    }
    public ServiceTrackProtocol(String actionCode, Person user, Service service, String message, HashMap<Integer,Service> services) {
        this.actionCode = actionCode;
        this.user = user;
        this.service = service;
        this.message = message;
        this.services = services;
        persons = null;
    }
    public ServiceTrackProtocol(String actionCode, Person user, Service service, String message, HashMap<Integer, Service> services, ArrayList<Person> persons) {
    	this.actionCode = actionCode;
        this.user = user;
        this.service = service;
        this.message = message;
        this.services = services;
        this.persons = persons;
    }
    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
	public HashMap<Integer, Service> getServices() {
		return services;
	}
	public void setServices(HashMap<Integer, Service> services) {
		this.services = services;
	}
	public ArrayList<Person> getPersons() {
		return persons;
	}
	public void setPersons(ArrayList<Person> persons) {
		this.persons = persons;
	}
    
    
    
    
}