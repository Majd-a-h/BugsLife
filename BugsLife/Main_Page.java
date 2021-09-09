public class Main_Page { //here where the program will be run from
    public static void main(String[] args) { Login.main(); } //the main method to run the login and then the flow will go from place to place
    public static void Bugs_Life(){ //this method will be called in two places, the login and sign up, to go after he done with one of this two, will gp to project dashboard.
        System.out.println("\n   *** Welcome to Bugs Life ***\n");
        System.out.println("          Welcome, " + Company.getUser_who_entered() );
        new Project_Dashboard().DisplayProject(); //go to the project dashboard
    } //end of the method
} //end of the