public class Issue_Dashboard {

    public void display_IssueDashboard(){
       java.util.Scanner enter = new java.util.Scanner(System.in);
        Company company = new Json().get_company_info();
       if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().size()!=0){
           while(true) {
               //to Display all the project's Issues:
               System.out.println("\nIssue board\n"+
                       "+-----+-------------------------+-------------+----------+----------+------------------+-----------+-------------+\n" +
                       "| ID  |          Title          |    Status   |   Tag    | Priority |       Time       |  Assignee |  CreatedBy  |\n" +
                       "+-----+-------------------------+-------------+----------+----------+------------------+-----------+-------------+");

               for(int x = 0; x<company.getProjects().get(Company.getProjectIndex_entered()).getIssues().size(); x++){  //display all the Issues
                   display_the_issue(x);
               }

               System.out.print(
                       "1. Open an issue\n" +
                               "2. Search by title\n" +
                               "3. Create an issue\n" +
                               "4. Sort by priority\n" +
                               "5. Sort by Time\n" +
                               "6. Filter Issues by status\n" +
                               "7. Filter Issues by tag\n" +
                               "8. Go back\n"+
                               "\n" +
                               "Enter the number of choice: ");
               String number_temp = enter.nextLine (); //take the integer first as a string
               int choice=0;
               try { //for the user if he enter a string instead of a number
                   choice = Integer.parseInt (number_temp); //convert the string to an integer, if the user entered string then it will go to catch
               }
               catch (NumberFormatException e) { //if there is an error with try then come here
                   System.out.println("Please do not enter a letter!");
               }

               if (choice == 1) { //open an issue
                   int issueID_toOpen=0;
                   All: while (true){
                       System.out.print("Enter issue's ID: ");
                       issueID_toOpen = enter.nextInt(); //the id of the issue
                       for (int x=0; x<company.getProjects().get(Company.getProjectIndex_entered()).getIssues().size();x++) //to go through all Issues to check if there is an issue with the id enterd
                           if (company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getId()==issueID_toOpen)
                               break All; //if the issue existed then break while and for loop
                           else System.out.println("Enter correct issue ID!");
                   } //end of the while loop

                   Company.setIssueIndex_entered(issueID_toOpen-1); // Issues'id-1: because the id always more than index by 1


                   /*
                   - to check if the issue's statue = closed:
                       - check if the user who wants to enter is:
                               - The admin OR the one who created the issue
                                               - true: then can enter
                                               - false: can not enter this issue
                    */
                   if (company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getStatus().equalsIgnoreCase("Closed")
                   && (!Company.getUser_who_entered().equalsIgnoreCase("admin")
                   ||(!company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getCreatedBy().equalsIgnoreCase(Company.getUser_who_entered()))))
                   {
                       System.out.println("\n!!Sorry you can not access this issue because it is closed. Only the admin or the creator of the issue can access.");
                       enter.nextLine(); //to take the enter press
                       continue;
                   }
                   new Issue_page().show_issue_page();
                   break;
               } // end of the if

               else if(choice==2){search_by_title();
                   exit(); //to check if the user wants to exit
               }
               else if(choice==3){create_issue();
                   exit(); //to check if the user wants to exit
               }
               else if(choice==4){sort_by_priority();
                   exit(); //to check if the user wants to exit
               }
               else if(choice==5){sort_by_time();
                   exit(); //to check if the user wants to exit
               }
               else if(choice==6){Filter_issues_by_status();
                   exit(); //to check if the user wants to exit
               }
               else if(choice==7){Filter_issues_by_tag();
                   exit(); //to check if the user wants to exit
               }
               else if (choice==8){new Project_Dashboard().DisplayProject();}
               else System.out.println("Please choose correct number\n");
           }
       } //end of the if
        else {
           System.out.print("There is no Issues in this project.\nWant to add an issue? [Y for yes] or enter any letter to go back: ");
           String add_issue = enter.nextLine();
           if(add_issue.equalsIgnoreCase("y"))
               create_issue();
           else new Project_Dashboard().DisplayProject();
       }
   }

    private static void create_issue(){

        Company company = new Json().get_company_info();
        java.util.Scanner enter = new java.util.Scanner(System.in);
        Issues new_issue = new Issues();


        new_issue.setId(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().size()+1); //set the id


        //set the title
        System.out.print("\nEnter the title of the issue: ");
        String title_entered = enter.nextLine();
        new_issue.setTitle(title_entered);


        new_issue.setStatus("Open"); //set statue


        //set the tag
        System.out.print("Enter the tag [Frontend or Backend]: ");
        String tag_entered = enter.nextLine();
        while (true){
            if(!tag_entered.equalsIgnoreCase("Frontend")&&!tag_entered.equalsIgnoreCase("Backend")){
                System.out.print("Enter correct tag: ");
                tag_entered = enter.nextLine();
            }
            else break;
        }
        new_issue.setTag(tag_entered);


        //set the priority
        System.out.print("Enter the priority number [1 (low) to 9 (high)]: ");
        int priority_entered = enter.nextInt();
        while (true){
            if(priority_entered>9||priority_entered<1){
                System.out.print("Enter correct number: ");
                priority_entered = enter.nextInt();
            }
            else break;
        }
        new_issue.setPriority(priority_entered);


        new_issue.setTimestamp(); //set the date

        new_issue.setCreatedBy(Company.getUser_who_entered()); //set createdBy

        enter.nextLine(); //to take the enter press

        //set Description text
        System.out.print("Enter the description text of the issue: ");
        String description_entered = enter.nextLine();
        new_issue.setDescriptionText(description_entered);



        //set the Assignee
        if(company.getUsers().size()==1){ //if there is only one user, then he cannot assign himself
            new_issue.setAssignee("Unknown");
        }
        else { //if there is a users to choose from

            System.out.println("choose the Assignee of the issue: ");
            for (int x = 0; x < company.getUsers().size(); x++) {
                if (!company.getUsers().get(x).getUsername().equalsIgnoreCase("admin"))
                    System.out.println("- " + company.getUsers().get(x).getUsername());
            }
            System.out.print("Enter the name of the Assignee: ");
            String Assignee_entered = enter.nextLine();
            boolean check_if_user_entered_correct_assignee_name = false;
            while (true) { //to check if the user entered wrong name then to enter again
                for (int x = 0; x < company.getUsers().size(); x++) { //to check if the user entered correct name
                    if (company.getUsers().get(x).getUsername().equalsIgnoreCase(Assignee_entered)) {
                        check_if_user_entered_correct_assignee_name = true;
                        break;
                    }
                }
                if (check_if_user_entered_correct_assignee_name = false) { //if the user entered wrong name then he must enter again
                    System.out.print("Enter correct Assignee name: ");
                    Assignee_entered = enter.nextLine();
                } else break; //if enter correct name then break
            }
            new_issue.setAssignee(Assignee_entered);
        }

        //add the new issue to the JSON file
        company.getProjects().get(Company.getProjectIndex_entered()).getIssues().add(new_issue);
        new Json().update_company_info(company);

        System.out.println("The issue has been added successfully");
        new Issue_Dashboard().display_IssueDashboard(); //to go back and show the Issues
    }

    private static void search_by_title(){
        Company company = new Json().get_company_info();

        java.util.Scanner enter = new java.util.Scanner(System.in);
        System.out.print("Enter the title of the issue: ");
        String title_entered = enter.nextLine();

        boolean check_if_title_exists = false;
        for(int x = 0; x<company.getProjects().get(Company.getProjectIndex_entered()).getIssues().size(); x++){ //to go through the issues and check if there is an issue with the title entered
            if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getTitle().equalsIgnoreCase(title_entered)){
                check_if_title_exists=true;
                break;
            }
        } //end of the for loop
        if(check_if_title_exists){ //if there is an issue with the title entered, then print it
            System.out.println("\nHere is the Issues with the titles you entered:");
            System.out.println(
                    "+-----+-------------------------+-------------+----------+----------+------------------+-----------+-------------+\n" +
                            "| ID  |          Title          |    Status   |   Tag    | Priority |       Time       |  Assignee |  CreatedBy  |\n" +
                            "+-----+-------------------------+-------------+----------+----------+------------------+-----------+-------------+");
            for(int x = 0; x<company.getProjects().get(Company.getProjectIndex_entered()).getIssues().size(); x++){
                if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getTitle().equalsIgnoreCase(title_entered)){
                    display_the_issue(x);
                }
            }
        }
        else System.out.println("There is no Issues with the title you have entered!\n");
    }

    private static void sort_by_priority(){
        Company company = new Json().get_company_info();

        //sort, if the issue in index 0 has lower priority than issue in index 1 then change the places
        for(int x = 0; x<company.getProjects().get(Company.getProjectIndex_entered()).getIssues().size(); x++){
            for(int y = 0; y<company.getProjects().get(Company.getProjectIndex_entered()).getIssues().size()-1; y++){
                if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(y).getPriority()
                        <company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(y+1).getPriority()){
                    Issues issues_hold = company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(y);
                    company.getProjects().get(Company.getProjectIndex_entered()).getIssues().set(y,company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(y+1));
                    company.getProjects().get(Company.getProjectIndex_entered()).getIssues().set(y+1,issues_hold);
                }
            } // end of the nested for loop
        } //end of the first for loop


        //print the Issues sorted:
        System.out.println("\nHere is the Issues sorted by priority:");
        System.out.println(
                "+-----+-------------------------+-------------+----------+----------+------------------+-----------+-------------+\n" +
                "| ID  |          Title          |    Status   |   Tag    | Priority |       Time       |  Assignee |  CreatedBy  |\n" +
                "+-----+-------------------------+-------------+----------+----------+------------------+-----------+-------------+");
        for(int x = 0; x<company.getProjects().get(Company.getProjectIndex_entered()).getIssues().size(); x++){
            //print the id
            if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getId()>9)
                System.out.print("| "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getId()+"  |"); //means the id is more than one digit
            else System.out.print("| "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getId()+"   |"); //if the id is only one digit

            //print the title
            if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getTitle().length()<25){
                System.out.print(" "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getTitle()); //print the title
                print_spaces(24-(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getTitle().length())); //print the spaces
                System.out.print("|");
            }
            else System.out.print(" "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getTitle().substring(0,23)+" |"); //print the title till letter 24

            //print the status
            if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getStatus().equalsIgnoreCase("In Progress")) System.out.print(" In Progress |");
            else if (company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getStatus().equalsIgnoreCase("Open")) System.out.print("     Open    |");
            else if (company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getStatus().equalsIgnoreCase("Closed")) System.out.print("    Closed   |");
            else if (company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getStatus().equalsIgnoreCase("Resolved")) System.out.print("   Resolved  |");
            else if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getStatus().equalsIgnoreCase("responded")) System.out.print("  Responded  |");
            else System.out.print("    Reopen   |");


            //print the tag
            if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getTag().get(0).equalsIgnoreCase("Frontend"))
                System.out.print(" Frontend |");
            else System.out.print(" Backend  |");


            //print the priority
            if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getPriority()>9)
                System.out.print("    "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getPriority()+"    | "); //means the id is more than one digit
            else System.out.print("    "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getPriority()+"     | ");

            //print the time
            System.out.print(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getDate()+" | ");


            //print the Assignee
            if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getAssignee().length()<10){
                System.out.print("  "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getAssignee());
                print_spaces(8-(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getAssignee().length())); //print the spaces
                System.out.print("|");
            }
            else System.out.print(" "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getAssignee().substring(0,9)+" |"); //print the assignee till letter 9

            //print CreatedBy
            if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getCreatedBy().length()<12){
                System.out.print("    "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getCreatedBy());
                print_spaces(9-(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getCreatedBy().length())); //print the spaces
                System.out.println("|");
            }
            else System.out.println(" "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getCreatedBy().substring(0,11)+" |"); //print the createdBy till letter 9
            System.out.println("+-----+-------------------------+-------------+----------+----------+------------------+-----------+-------------+");
        }
    }

    private static void sort_by_time(){
        Company company = new Json().get_company_info();


        //sort, if the issue in index 0 has lower time than issue in index 1 then change the places
        for(int x = 0; x<company.getProjects().get(Company.getProjectIndex_entered()).getIssues().size(); x++){
            for(int y = 0; y<company.getProjects().get(Company.getProjectIndex_entered()).getIssues().size()-1; y++){
                if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(y).getTimestamp()
                        <company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(y+1).getTimestamp()){
                    Issues issues_hold = company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(y);
                    company.getProjects().get(Company.getProjectIndex_entered()).getIssues().set(y,company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(y+1));
                    company.getProjects().get(Company.getProjectIndex_entered()).getIssues().set(y+1,issues_hold);
                }
            }
        } //end of the first for loop

        //print the issues sorted by time:
        System.out.println("\nHere is the Issues sorted by time:");
        System.out.println(
                "+-----+-------------------------+-------------+----------+----------+------------------+-----------+-------------+\n" +
                "| ID  |          Title          |    Status   |   Tag    | Priority |       Time       |  Assignee |  CreatedBy  |\n" +
                "+-----+-------------------------+-------------+----------+----------+------------------+-----------+-------------+");
        for(int x = 0; x<company.getProjects().get(Company.getProjectIndex_entered()).getIssues().size(); x++){
            //print the id
            if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getId()>9)
                System.out.print("| "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getId()+"  |"); //means the id is more than one digit
            else System.out.print("| "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getId()+"   |"); //if the id is only one digit

            //print the title
            if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getTitle().length()<25){
                System.out.print(" "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getTitle()); //print the title
                print_spaces(24-(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getTitle().length())); //print the spaces
                System.out.print("|");
            }
            else System.out.print(" "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getTitle().substring(0,23)+" |"); //print the title till letter 24

            //print the status
            if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getStatus().equalsIgnoreCase("In Progress")) System.out.print(" In Progress |");
            else if (company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getStatus().equalsIgnoreCase("Open")) System.out.print("     Open    |");
            else if (company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getStatus().equalsIgnoreCase("Closed")) System.out.print("    Closed   |");
            else if (company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getStatus().equalsIgnoreCase("Resolved")) System.out.print("   Resolved  |");
            else if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getStatus().equalsIgnoreCase("responded")) System.out.print("  Responded  |");
            else System.out.print("    Reopen   |");


            //print the tag
            if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getTag().get(0).equalsIgnoreCase("Frontend"))
                System.out.print(" Frontend |");
            else System.out.print(" Backend  |");


            //print the priority
            if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getPriority()>9)
                System.out.print("    "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getPriority()+"    | "); //means the id is more than one digit
            else System.out.print("    "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getPriority()+"     | ");

            //print the time
            System.out.print(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getDate()+" | ");


            //print the Assignee
            if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getAssignee().length()<10){
                System.out.print("  "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getAssignee());
                print_spaces(8-(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getAssignee().length())); //print the spaces
                System.out.print("|");
            }
            else System.out.print(" "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getAssignee().substring(0,9)+" |"); //print the assignee till letter 9

            //print CreatedBy
            if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getCreatedBy().length()<12){
                System.out.print("    "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getCreatedBy());
                print_spaces(9-(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getCreatedBy().length())); //print the spaces
                System.out.println("|");
            }
            else System.out.println(" "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getCreatedBy().substring(0,11)+" |"); //print the createdBy till letter 9
            System.out.println("+-----+-------------------------+-------------+----------+----------+------------------+-----------+-------------+");
        }
    }

    private static void Filter_issues_by_status(){
        java.util.Scanner enter = new java.util.Scanner(System.in);
        String statue = null;
        while (true){
            System.out.print("1. In Progress\n" +
                    "2. Open\n" +
                    "3. Closed\n" +
                    "4. Resolved\n" +
                    "5. Reopen\n" +
                    "6. Responded\n"+
                    "\n" +
                    "Enter the number of choice: ");
            String statue_choice = enter.nextLine();
            if (statue_choice.equalsIgnoreCase("1")){
                statue = "In Progress";
                break;
            }
            else if(statue_choice.equalsIgnoreCase("2")){
                statue="Open";
                break;
            }
            else if(statue_choice.equalsIgnoreCase("3")){
                statue="closed";
                break;
            }
            else if(statue_choice.equalsIgnoreCase("4")){
                statue="Resolved";
                break;
            }
            else if(statue_choice.equalsIgnoreCase("5")){
                statue="Reopen";
                break;
            }
            else if(statue_choice.equalsIgnoreCase("6")){
                statue="responded";
                break;
            }
            else System.out.println("Enter correct number!");
        }


        Company company = new Json().get_company_info();

        boolean check_if_title_exists = false;
        for(int x = 0; x<company.getProjects().get(Company.getProjectIndex_entered()).getIssues().size(); x++){
            if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getStatus().equalsIgnoreCase(statue))
                check_if_title_exists=true;
        }
        if(check_if_title_exists){
            System.out.println("\nHere is the Issues with the statues you entered:");
            System.out.println(
                    "+-----+-------------------------+-------------+----------+----------+------------------+-----------+-------------+\n" +
                            "| ID  |          Title          |    Status   |   Tag    | Priority |       Time       |  Assignee |  CreatedBy  |\n" +
                            "+-----+-------------------------+-------------+----------+----------+------------------+-----------+-------------+");
            for(int x = 0; x<company.getProjects().get(Company.getProjectIndex_entered()).getIssues().size(); x++){
                if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getStatus().equalsIgnoreCase(statue)){
                    display_the_issue(x);
                }
            }
        }
        else System.out.println("There is no Issues with the statues you have entered!\n");
        System.out.println("\n");
    }

    private static void Filter_issues_by_tag(){
        java.util.Scanner enter = new java.util.Scanner(System.in);

        System.out.print("Enter the tag [Frontend or Backend]: ");
        String tag_entered = enter.nextLine();
        while (true){ //to check if the tag is correct:
            if(!tag_entered.equalsIgnoreCase("Frontend")&&!tag_entered.equalsIgnoreCase("Backend")){
                System.out.print("Enter correct tag: ");
                tag_entered = enter.nextLine();
            }
            else break; //if the user entered correct tag then break the while loop
        } //end of the while loop

        Company company = new Json().get_company_info(); //get the information of the company from the json file

        boolean check_if_title_exists = false;

        for(int x = 0; x<company.getProjects().get(Company.getProjectIndex_entered()).getIssues().size(); x++){ //check if there is Issues with the tag that the user has entered
            if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getTag().get(0).equalsIgnoreCase(tag_entered)){
                check_if_title_exists=true;
                break;
            }
        } // end of the for loop

        if(check_if_title_exists){ //if there is an Issues with the tag that the user have entered:
            System.out.println("\nHere is the Issues with the statues you entered:");
            System.out.println(
                    "+-----+-------------------------+-------------+----------+----------+------------------+-----------+-------------+\n" +
                    "| ID  |          Title          |    Status   |   Tag    | Priority |       Time       |  Assignee |  CreatedBy  |\n" +
                    "+-----+-------------------------+-------------+----------+----------+------------------+-----------+-------------+");

            for(int x = 0; x<company.getProjects().get(Company.getProjectIndex_entered()).getIssues().size(); x++){ //display the Issues with the tag that you have entered
                if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getTag().get(0).equalsIgnoreCase(tag_entered)){
                    display_the_issue(x);
                }
            } //end of the for loop
        } //end of the if
        else System.out.println("\nThere is no Issues with the tag you have entered!\n");
        System.out.println("\n");
    }

    private static void display_the_issue(int x){ //display the info for the issue in the issue dashboard
        Company company = new Json().get_company_info();

        //print the id
        if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getId()>9)
            System.out.print("| "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getId()+"  |"); //means the id is more than one digit
        else System.out.print("| "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getId()+"   |"); //if the id is only one digit

        //print the title
        if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getTitle().length()<25){
            System.out.print(" "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getTitle()); //print the title
            print_spaces(24-(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getTitle().length())); //print the spaces
            System.out.print("|");
        }
        else System.out.print(" "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getTitle().substring(0,23)+" |"); //print the title till letter 24

        //print the status
        if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getStatus().equalsIgnoreCase("In Progress")) System.out.print(" In Progress |");
        else if (company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getStatus().equalsIgnoreCase("Open")) System.out.print("     Open    |");
        else if (company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getStatus().equalsIgnoreCase("Closed")) System.out.print("    Closed   |");
        else if (company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getStatus().equalsIgnoreCase("Resolved")) System.out.print("   Resolved  |");
        else if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getStatus().equalsIgnoreCase("responded")) System.out.print("  Responded  |");
        else System.out.print("    Reopen   |");


        //print the tag
        if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getTag().get(0).equalsIgnoreCase("Frontend"))
            System.out.print(" Frontend |");
        else System.out.print(" Backend  |");


        //print the priority
        if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getPriority()>9)
            System.out.print("    "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getPriority()+"    | "); //means the id is more than one digit
        else System.out.print("    "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getPriority()+"     | ");

        //print the time
        System.out.print(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getDate()+" | ");


        //print the Assignee
        if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getAssignee().length()<10){
            System.out.print("  "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getAssignee());
            print_spaces(8-(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getAssignee().length())); //print the spaces
            System.out.print("|");
        }
        else System.out.print(" "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getAssignee().substring(0,9)+" |"); //print the assignee till letter 9

        //print CreatedBy
        if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getCreatedBy().length()<12){
            System.out.print("    "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getCreatedBy());
            print_spaces(9-(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getCreatedBy().length())); //print the spaces
            System.out.println("|");
        }
        else System.out.println(" "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(x).getCreatedBy().substring(0,11)+" |"); //print the createdBy till letter 9
        System.out.println("+-----+-------------------------+-------------+----------+----------+------------------+-----------+-------------+");
    } //end of the method

    private static void print_spaces(int x){ for(int y=0;y<x;y++)System.out.print(" "); } //to print space as the x entered

    //this method is to ask the user if he wants to exit or continue
    private static void exit(){
        java.util.Scanner enter = new java.util.Scanner(System.in);
        System.out.print("Type anything to go back to the choices OR type 'e' to exit: ");
        String check_if_exit = enter.nextLine();
        if (check_if_exit.equalsIgnoreCase("e"))
            System.exit(0); //exit the program
    }
}