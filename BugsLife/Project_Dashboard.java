import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//this class to show the project dashboard and with some features
public class Project_Dashboard {

    //to display the projects
    public void DisplayProject() {
        Company company = new Json().get_company_info(); //get all the info from the json file and assign it to company object
        java.util.Scanner enter = new java.util.Scanner(System.in);

        while(true){

            //to Display all the Projects:
            System.out.println("\n\nHere is the list of the Projects:\n");

            System.out.println(
                    "+----+-------------------+--------+\n" +
                    "| ID |    Project Name   | Issues |\n" +
                    "+----+-------------------+--------+");

            for(int x=0; x<company.getProjects().size();x++){ //to go through each project and print its id and the name and the number of issues in it.


                //print the project id
                if(company.getProjects().size()>9){ //if the id number is two digit, it will remove one space
                    System.out.print("| "+company.getProjects().get(x).getId()+" | ");
                }
                else System.out.print("| "+company.getProjects().get(x).getId()+"  | "); //if the id is one digit


                //print project name
                if(company.getProjects().get(x).getName().length()<17){ //if the name length is less then 17 letters
                    System.out.print(company.getProjects().get(x).getName()); //print the name
                    for(int y=0; y<17-company.getProjects().get(x).getName().length();y++){ //print the spaces remain by subtracting 17 from the length of the name, then the remaining is the spaces
                        System.out.print(" ");
                    }
                    System.out.print(" |   "); //the end of the name
                }
                //if the name is longer than 16, then print the title till letter 16.
                else System.out.print(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getTitle().substring(0,15)+" |   ");



                //print Issues number
                if(company.getProjects().get(x).getIssues().size()>9){ //if the issues number is two digit, it will remove one space
                    System.out.println(company.getProjects().get(x).getIssues().size()+"   |");
                }
                else System.out.println(company.getProjects().get(x).getIssues().size()+"    |"); //if the issues number is one digit
                    System.out.println("+----+-------------------+--------+"); //the end of the each project, will print this, to separate between each project
                } //end of the for loop



            //the choices after showing the project dashboard
            System.out.print("\n" +
                    "1. check project's Issues\n" +
                    "2. Add a project\n" +
                    "3. Report Generation\n" +
                    "4. Exit\n"+
                    "\n" + "Enter the number of choice: ");

            String number_temp = enter.nextLine (); //take the integer first as a string
            int choice=0;
            try { //for the user if he enter a string instead of a number
                choice = Integer.parseInt (number_temp); //convert the string to an integer, if the user entered string then it will go to catch
            }
            catch (NumberFormatException e) { //if there is an error with try then come here
                System.out.println("\nPlease do not enter a letter!");
            }

            if(choice==1){ //open the projects' issues
                if(company.getProjects().size()!=0){ //if there is already a projects then you can go

                    int entered_id = 0; //id of the project to show the issues of it

                    All: while (true){
                        System.out.print("Enter project ID: ");
                        entered_id = enter.nextInt();
                        for(int x=0;x<company.getProjects().size();x++){ //go though all the projects
                            if (company.getProjects().get(x).getId()==entered_id) //check if there is an id as the entered, if there is one then break
                                break All; //break the whole label, the while and for loop
                        } //end of the for loop
                        System.out.println("Enter correctly!");
                    } //end of the while loop
                    Company.setProjectIndex_entered(entered_id-1); //for the issue class to can access and open the Issues of the project the user want to see
                    new Issue_Dashboard().display_IssueDashboard(); //go to the issues page to show the issues of the project
                    break; //break the whole while loop
                } //end of the if loop
                else System.out.println("You do not have any Projects yet!");
                exit(); //to check if the user wants to exit
            } //end of the if loop

            else if(choice==2){ //if it is the admin then he can add a project:
                if(Company.getUser_who_entered().equalsIgnoreCase("Admin")){ //first check if the entered is the admin, coz only him can add a project
                    int id= company.getProjects().size()+1; //set the id of the project as size+1, if there is no project before then it will be 1, if there was one then it will be 2, etc.
                    System.out.print("Enter the name of the project: ");
                    String project_name= enter.nextLine();
                    Projects new_project = new Projects(id,project_name); //set the id and the name of the project and assign to the new_project object
                    company.addProjects(new_project); //add the new_project object to the arraylist in the company object
                    new Json().update_company_info(company); //update the info in the json file.
                    System.out.println("The project was added successfully.\n");
                }
                else System.out.println("\nYou do not have an access"); //if it is not the admin
                exit(); //to check if the user wants to exit
            }

            else if(choice==3){ //only the admin can generate the Report Generation:
                if(Company.getUser_who_entered().equalsIgnoreCase("Admin")&&company.getProjects().size()!=0){ //if it is the admin and there is project then generate the report
                    report_generation();
                }
                else System.out.println("\nYou do not have an access\n"); //if it is no the admin
                exit(); //to check if the user wants to exit
            }

            else if (choice==4){System.exit(0);} //exit the program

            else System.out.println("Enter correct number!\n"); //if he entered wrong number, and the while loop will go again
        } //end of the while loop

        new Json().update_company_info(company); //update all info on the json file
    } //end of the method


    //this method to generate the weekly report for the admin
    public static void report_generation(){

        Company company = new Json().get_company_info(); //get the information from the JSON file and assign it to the comany object

        //get numbers of Issues: resolved, unresolved, in progress
        int resolved_issues=0;
        int inProgress_issues=0;
        int unresolved_issues=0; //unresolved: Open or closed or reopen
        for(int x=0; x<company.getProjects().size();x++){ //to go in each project
            for(int y=0; y<company.getProjects().get(x).getIssues().size();y++){ //to go in each project Issues
                if(company.getProjects().get(x).getIssues().get(y).getStatus().equalsIgnoreCase("resolved"))
                    resolved_issues++; //if the statue of the issue equal resolved then increase of the resolved_issues variable
                else if(company.getProjects().get(x).getIssues().get(y).getStatus().equalsIgnoreCase("in progress"))
                    inProgress_issues++; //if the statue of the issue equal in progress then increase of the inProgress_issues variable
                else unresolved_issues++; //if not resolved and in progress then increase unresolved_issues
            } //end of the project's Issues for loop (nested for loop)
        } //end of the Projects for loop





        //Top performer
        /*
        - top performer in team: The one resolved most ticket in this week is top performer.
        - if the issue is resolved then get the assignee and give him a point, the member with the most points will be the top performer.
        */
        ArrayList<Members> members = new ArrayList<>(); //arraylist of members objects, each member will hold two two things: name, points
        for(int x=0; x<company.getProjects().size();x++){ //to go in each project
            for(int y=0; y<company.getProjects().get(x).getIssues().size();y++){ //to go in each project Issues
                boolean check_if_the_assignee_already_added=false;
                //for the isDateInCurrentWeek method
                if(company.getProjects().get(x).getIssues().get(y).getStatus().equalsIgnoreCase("resolved")&&
                        isDateInCurrentWeek(company.getProjects().get(x).getIssues().get(y).getDate())){ //if the issue is resolved and it is in this week then:
                    for(int z=0; z<members.size();z++){ //go through the members to see if the assignee is already in the members arraylist, then add for him a point
                        if(members.get(z).getName().equalsIgnoreCase(company.getProjects().get(x).getIssues().get(y).getAssignee())){
                            check_if_the_assignee_already_added =true; //if he already in the arraylist, then no need to add him again, just add for him a point
                            members.get(z).setPoints(); //add one point
                            break;
                        }
                    }
                    if(!check_if_the_assignee_already_added){ //if the assignee is not in the members then add him and add for him a point
                        Members new_member1= new Members(company.getProjects().get(x).getIssues().get(y).getAssignee(),1);
                        members.add(new_member1);
                    }
                } //end of the if
            } //end of the project's Issues for loop (nested for loop)
        } //end of the Projects for loop

        //now after you got the members and their points, you need to get the highest one
        Members highest = new Members();
        if(!members.isEmpty()){
            highest = members.get(0);  //assign the first as the highest member with the most points
            for(int x=0;x<members.size();x++){ //go through all the members and check
                if(highest.getPoints()<members.get(x).getPoints()){ //check if the current highest is smaller than the following member then change the highest
                    highest = members.get(x);
                }
            } //end of the for loop
        }

        if(highest.getName()==null){
            highest.setName("Evaluating");
        }




        //the number of Issues for each tag
        int frontend=0, backend=0;
        for(int x=0; x<company.getProjects().size();x++){ //to go in each project
            for(int y=0; y<company.getProjects().get(x).getIssues().size();y++){ //to go in each project Issues
                if(company.getProjects().get(x).getIssues().get(y).getTag().get(0).equalsIgnoreCase("backend"))
                    backend++; //if the tag of the issue is equal backend then increase the backend
                else if(company.getProjects().get(x).getIssues().get(y).getTag().get(0).equalsIgnoreCase("frontend"))
                    frontend++; //if the tag of the issue is equal backend then increase the frontend
            } //end of the project's Issues for loop (nested for loop)
        } //end of the Projects for loop


        //print the report:
        System.out.print("This is the weekly report:\n"+
                        "(-------------------------------- weekly Report -------------------------------) \n" +
                        "|                                                                              |\n" +
                        "|                                                                              |\n" +
                        "|               - - - - -    Number of Issues (Statues)   - - - - -            |\n" +
                        "|                                                                              |\n" +
                        "|  - Unresolved Issues: "+unresolved_issues+"                                                      |\n" +
                        "|  - In Progress Issues: "+inProgress_issues+"                                                     |\n" +
                        "|  - Resolved Issues: "+resolved_issues+"                                                        |\n" +
                        "|                                                                              |\n" +
                        "|                                                                              |\n" +
                        "|                   - - - -        Top Performer         - - - -               |\n" +
                        "|                                                                              |\n");
    System.out.print("|                              ***    "+highest.getName()+"   ***");
        for(int x=0;x<35-highest.getName().length();x++){ System.out.print(" "); } //to print the spaces after the name.
        System.out.print("|\n"+
                        "|                                                                              |\n" +
                        "|                                                                              |\n" +
                        "|                   - - -    Number of Issues (Tags)     - - -                 |\n" +
                        "|  - Frontend Issues: "+frontend+"                                                        |\n" +
                        "|  - Backend Issues: "+backend+"                                                         |\n" +
                        "|                                                                              |\n" +
                        "(______________________________________________________________________________)\n"
        ); //end of the print

    } //end of the method


    /*
    this method will be used to check if the date in the current week,
    it will be invoked in the report_generation method to help us,
    in getting the top performer in team in this week.
     */
    public static boolean isDateInCurrentWeek(String date_string)  {
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm"); //the format of the date in the string
        Date date = new Date(); //convert the sate from string to date object
        try {
            date = formatter.parse(date_string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar currentCalendar = Calendar.getInstance(); //get the calendar
        int week = currentCalendar.get(Calendar.WEEK_OF_YEAR); //to get the current week
        int year = currentCalendar.get(Calendar.YEAR); //to get the current year
        Calendar targetCalendar = Calendar.getInstance(); //get the calendar to use it on the date entered in the parameter
        targetCalendar.setTime(date); //set the date entered
        int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR); //get the week of the year from the date entered
        int targetYear = targetCalendar.get(Calendar.YEAR); //get the year from the date entered
        return week == targetWeek && year == targetYear; //check if the week of the date entered is the same as the current week and if the year of the date entered is the sa the current year.
    } //end of the method


    //this method is to ask the user if he wants to exit or continue
    public static void exit(){
        java.util.Scanner enter = new java.util.Scanner(System.in);
        System.out.print("Type anything to go back to the choices OR type 'e' to exit: ");
        String check_if_exit = enter.nextLine();
        if (check_if_exit.equalsIgnoreCase("e"))
            System.exit(0); //exit the program
    } //end of the method
}