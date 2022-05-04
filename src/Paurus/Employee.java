package Paurus;

public class Employee {
    protected String ID;
    protected String FirstName;
    protected String LastName;
    protected String email;

    public Employee(){

    }
    public Employee(String ID, String FirstName, String LastName, String email){
        this.ID=ID;
        this.FirstName=FirstName;
        this.LastName=LastName;
        this.email=email;
    }

    public String getID() {
        return ID;
    }
    public String getFirstName() {
        return FirstName;
    }
    public String getLastName() {
        return LastName;
    }

    public String getEmail() {
        return email;
    }

}
