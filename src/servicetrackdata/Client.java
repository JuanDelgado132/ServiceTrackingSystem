/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicetrackdata;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.Period;

/**
 *
 * @author juand
 * 
 * 
 * This class will hold a client information.
 */
public class Client extends Person{
    private String gender;
    private int age;
    private String birthDay;
    private String comments;
    
    
    public Client() {
        super();
    }
    
    public Client(String firstName, String lastName, int id, String gender, String birthDay, String comments){
        super(firstName, lastName, id);
        this.gender = gender;
        this.birthDay = birthDay;
        this.comments = comments;
        this.age = calculateAge(); //age goes last since it is calculated.
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    public String toString(){
       return  super.toString()
               +"\nGender: " + gender
               + "\nAge: " + age
               + "\nBirthday " + birthDay
               + "\nComments " + comments; 
    }
    /**
     * Age is calculated since it is not stored in the database.
     * 
     * @return int
     * @author Juan Delgado
     */
    private int calculateAge(){
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //Format of the date, to match MySQL default date format.
        LocalDate current = LocalDate.now(); //Getting the current date.
        String currentDateFormatText = current.format(dateFormat); //This is important here we get the current day in the following format, yyyy-MM-dd. Must match SQL
        current = LocalDate.parse(currentDateFormatText, dateFormat); //we have to parse the date to a LocalDate variable.
        LocalDate dateOfBirth = LocalDate.parse(birthDay, dateFormat); // We parse our birthday to a LocalDate variable.
        
        
        return Period.between(dateOfBirth, current).getYears();
    }
}
