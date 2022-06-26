
public class Passenger 
{
    private String firstName;
    private String surname;
    private int secondsInQueue;
    
    Passenger(String firstName, String surname)
    {
        this.firstName = firstName;
        this.surname = surname; 
        secondsInQueue = 0;
    }
    
    public String getName()
    {
        return firstName + " " + surname; 
    }
    
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
        
    }
    
    public void setSurName(String surname)
    {
        
        this.surname = surname;
    }
    public void setName(String firstName, String surname)
    {
        setFirstName(firstName);
        setSurName(surname);
    }

    public void display()
    {
            System.out.println(firstName + " " + surname + " " + secondsInQueue);
        
    }
        
    
    public void setSecondInQueue (int seconds) {
        this.secondsInQueue = seconds;
    
    }
    
    public int getsecondsinqueue () {
        return this.secondsInQueue;
    }
    
}
