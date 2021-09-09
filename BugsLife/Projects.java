import java.util.ArrayList;

//this class is just to hold the information of the projects of the program, has three variables with their setters and getters
public class Projects {
     private int id;
     private String name; //name of the project
     private ArrayList<Issues> issues = new ArrayList<>();

     //to assign the id and the name for the project
     public Projects(int id, String name) {
        this.id = id;
        this.name = name;
    }

     public Projects() {}


    //return the issues arraylist
     public ArrayList<Issues> getIssues() { return issues; }

     public void setIssues(ArrayList<Issues> issues) { this.issues = issues;}

     public int getId() {return id;}
     public String getName() { return name;}
}