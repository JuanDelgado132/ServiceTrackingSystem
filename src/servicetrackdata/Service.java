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
 * Service will inherit Serializable so it can be transfered to the server later.
 */
public class Service implements Serializable{
    
    private int serviceID;
    private String serviceName;
    private String serviceDescription;
    private String restriction;
    
    public Service(){
        serviceID = 0;
        serviceName = " ";
        serviceDescription = " ";
        restriction = " ";
    }

    public Service(int serviceID, String serviceName, String serviceDescription, String restriction) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.serviceDescription = serviceDescription;
        this.restriction = restriction;
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

    public String getRestriction() {
        return restriction;
    }

    public void setRestriction(String restriction) {
        this.restriction = restriction;
    }

    @Override
    public String toString() {
        return "serviceID=" + serviceID + ", serviceName=" + serviceName + ", serviceDescription=" + serviceDescription + ", restriction=" + restriction;
    }
    
    
    
}
