/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicetrackdata;

import java.io.Serializable;

/**
 * Will serve as the representation for the services that the organization provides.
 * @author juand
 */
@SuppressWarnings("serial")
public class Service implements Serializable{
    
    private int serviceID;
    private String serviceName;
    private String serviceDescription;
    private String days;
    private String time;
    
    public Service(){
        serviceID = 0;
        serviceName = " ";
        serviceDescription = " ";
        days = " ";
        time = " ";
    }

    public Service(int serviceID, String serviceName, String serviceDescription, String days, String time) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.serviceDescription = serviceDescription;
        this.days = days;
        this.time = time;
    }
    
    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }
    

    public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
    public String toString() {
    	return "ID: " + serviceID +
    			"\nName: " + serviceName +
    			"\nDescription: " + serviceDescription +
    			"\nDays: " + days + 
    			"\nTime: " + time;
    }
    
    
    
}
