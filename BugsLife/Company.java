import java.util.ArrayList;

public class Company {
    private ArrayList<Projects> projects= new ArrayList<>(); //arraylist to hold all the projects in the company
    private ArrayList<Users> users = new ArrayList<>(); //arraylist to hold all the users in the company
    private static String user_who_entered; //the name of the user who signed up or logged in the program
    private static int projectIndex_entered; //the index of the project that user wants to open
    private static int issueIndex_entered; //the index of the issue that user wants to open
    private static UndoRedoStack_Comment undoRedoStackComment = new UndoRedoStack_Comment(); //stack to hold the comment
    private static UndoRedoStack_Description undoRedoStackDescription = new UndoRedoStack_Description(); //stack to hold the description

    public Company() {}

    public ArrayList<Projects> getProjects() {return projects;}  //return the projects arraylist

    public void addProjects(Projects project) {projects.add(project);} //add the project object to the arraylist of the projects

    public ArrayList<Users> getUsers() {return users;}

    public void addUsers(Users user) {users.add(user);}

    public static String getUser_who_entered() { return user_who_entered; }

    public static void setUser_who_entered(String user_who_entered) { Company.user_who_entered = user_who_entered; }

    public static int getProjectIndex_entered() { return projectIndex_entered; }

    public static void setProjectIndex_entered(int projectIndex_entered) { Company.projectIndex_entered = projectIndex_entered; }

    public static int getIssueIndex_entered() { return issueIndex_entered; }

    public static void setIssueIndex_entered(int issueIndex_entered) { Company.issueIndex_entered = issueIndex_entered; }

    public static UndoRedoStack_Comment getUndoRedoStackComment() { return undoRedoStackComment; }

    public static UndoRedoStack_Description getUndoRedoStackDescription() { return undoRedoStackDescription; }
}