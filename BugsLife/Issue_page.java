import java.util.Scanner;

public class Issue_page {
    public void show_issue_page(){
        Company company = new Json().get_company_info(); //assign the information from the JSON file to the company object
        Scanner enter = new Scanner(System.in);
        while (true){

            //print issue information
            System.out.println(
                            "Issue information:"
                            +"\n-----------------"
                            +"\nIssue ID: "+Company.getIssueIndex_entered()+1
                            +"\nStatues: "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getStatus()
                            +"\nTag: "+ company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getTag()
                            +"\nPriority: "+ company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getPriority()
                            +"\nCreated On: "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getDate()
                            +"\nIssue Title: "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getTitle()
                            +"\nAssigned to: "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getAssignee()
                            +"\nCreated by: " +company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getCreatedBy()
                            +"\n"
            ); //end of the print

            //print issue description
            System.out.println("Issue Description\n" + "---------------------");
            //this string is to delete all the two spaces in the issue description
            String descriptionText = company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getDescriptionText().replaceAll("\\s\\s","");
            for (int x=0; x<descriptionText.length();x++){ //to print it in a good way:
                if(descriptionText.charAt(x)=='.') ///if . then it means the end of the sentence go to the next line
                    System.out.print(descriptionText.charAt(x)+"\n");
                else System.out.print(descriptionText.charAt(x)); //print the letter
            } //end of the for loop

            //print comments of the issue
            System.out.println("\n\nComments\n" +"--------");
            for(int x = 0; x<company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getComments().size(); x++)
                company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getComments().get(x).display_Comment();


            //choose what to do:
            System.out.print(
                    "\na. Add a comment\n" +
                    "e. Edit comment [Redo & Undo]\n"+
                    "r. React\n" +
                    "c. Change issue statues to resolved\n"+
                    "d. Edit description [Redo & Undo]\n"+
                    "b. Go back\n" +
                    "m. More\n" +
                    "\n" +
                    "Enter the letter of your choice: ");
            String letter = enter.next(); //take the input as String


            //add a comment
            if (letter.equalsIgnoreCase("a")){
                Comment new_comment = new Comment(); //create a new comment object
                new_comment.add_newComment();
                company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).setComments(new_comment);
                if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getStatus().equalsIgnoreCase("responded"))
                    company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).setStatus("in progress");
                else company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).setStatus("responded");
                new Json().update_company_info(company); //save the changes to the json file
            } //end of the if


            //Edit comment or redo or undo
            if (letter.equalsIgnoreCase("e")){
                int id_edit;
                All: while (true){
                    System.out.print("Enter the id of the comment: ");
                    id_edit = enter.nextInt();
                    for(int x=0;x<company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getComments().size();x++) //check if there is a comment with the id entered
                        if (company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getComments().get(x).getComment_id()==id_edit)
                            break All;
                    System.out.println("Enter correct id!\n");
                } //end of the while
                if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getComments().get(id_edit-1).getUser().equalsIgnoreCase(Company.getUser_who_entered())){
                    enter.nextLine();
                    while (true) {
                        System.out.print(
                                "C. Replace the old comment\n" +
                                "R. Redo\n" +
                                "U. Undo\n" +
                                "B. Go Back\n" +
                                "\n" +
                                "Enter the letter of your choice: ");

                        String choice = enter.nextLine();

                        if (choice.equalsIgnoreCase("R")) { //if redo
                            if (!Company.getUndoRedoStackComment().is_empty_redoStack()) { //check if the redo is not empty
                                Company.getUndoRedoStackComment().push_to_undoStack(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getComments().get(id_edit-1).getText());
                                System.out.println("The comment is redo to: " + Company.getUndoRedoStackComment().peek_from_redoStack());
                                company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getComments().get(id_edit-1).setText(Company.getUndoRedoStackComment().pop_from_redoStack());
                                new Json().update_company_info(company); //update all info
                            } //end of the if
                            else System.out.println("The redo is empty!");
                        } //end of the if
                        else if (choice.equalsIgnoreCase("U")) {
                            if (!Company.getUndoRedoStackComment().is_empty_undoStack()) {
                                if (Company.getUndoRedoStackComment().size_undoStack() != 1) Company.getUndoRedoStackComment().pop_from_undoStack();
                                if(!Company.getUndoRedoStackComment().peek_from_undoStack().equalsIgnoreCase(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getComments().get(id_edit-1).getText())) {
                                    Company.getUndoRedoStackComment().push_to_redoStack(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getComments().get(id_edit - 1).getText());
                                    System.out.println("The comment is undo to: " + Company.getUndoRedoStackComment().peek_from_undoStack());
                                    company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getComments().get(id_edit - 1).setText(Company.getUndoRedoStackComment().pop_from_undoStack());
                                    new Json().update_company_info(company); //update all info
                                } //end of the it
                            } else System.out.println("The undo is empty!");
                        } //end of the if
                        else if(choice.equalsIgnoreCase("C")){
                            System.out.println("Enter:");
                            String new_text = enter.nextLine();
                            Company.getUndoRedoStackComment().push_to_undoStack(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getComments().get(id_edit-1).getText());
                            company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getComments().get(id_edit-1).setText(new_text);
                            Company.getUndoRedoStackComment().push_to_undoStack(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getComments().get(id_edit-1).getText());
                            //change the comment
                            System.out.print("The comment has been changed to: "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getComments().get(id_edit-1).getText()+"\n");
                            new Json().update_company_info(company); //update all info
                        }
                        else if(choice.equalsIgnoreCase("b"))break;
                        else System.out.println("Enter again!\n"); //if the user entered wrong choice
                    } //end of the while
                }
                else System.out.println("You cannot edit this comment!\n");

            }//end of the if

            //add a reaction:
            else if(letter.equalsIgnoreCase("r")&&company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getComments().size()!=0){
                int commentID_entered=0; //the id number of the one you want to add a reaction to

                All: while (true){
                    System.out.print("Enter the id of the comment you want to react to: ");
                    commentID_entered = enter.nextInt(); //take the id number of the one you want to add a reaction to

                    boolean check_if_id_correct= false;
                    //check if there is a comment with the id, the user entered:
                    for(int x = 0; x<company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getComments().size(); x++){
                        if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getComments().get(x).getComment_id()==commentID_entered){
                            check_if_id_correct=true;
                            break All; //if you find it then break the for and while loop.
                        }
                    } //end of the for loop
                    if(!check_if_id_correct) System.out.println("Enter correct comment ID number! "); //if you do not find it then go back and take the id input again
                } //end of the wile loop

                enter.nextLine(); //to get the enter press

                String reaction_name="";
                String input_letter="";

                while(true){
                    System.out.print("\n" +
                            "a. angry\n" +
                            "d. sad\n" +
                            "h. happy\n" +
                            "s. smile\n" +
                            "\n" +
                            "Enter the letter of your choice: ");
                    input_letter = enter.nextLine(); //to get the letter of the reaction you want to add

                    //make the letter of choice as string
                    if(input_letter.equalsIgnoreCase("h")){
                        reaction_name="happy";
                        break;
                    }
                    else if(input_letter.equalsIgnoreCase("a")){
                        reaction_name="angry";
                        break;
                    }
                    else if(input_letter.equalsIgnoreCase("d")){
                        reaction_name="sad";
                        break;
                    }
                    else if(input_letter.equalsIgnoreCase("s")){
                        reaction_name="smile";
                        break;
                    }
                    else System.out.println("Enter correct letter!");
                }

                boolean check_if_reaction_exists = false;

                ///check for the reaction if existed:
                for(int x = 0; x<company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getComments().get(commentID_entered-1).getReact().size(); x++){
                    //to check if the reaction already existed then just say that check_if_reaction_exists=true and increase the number of the reactions
                    if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getComments().get(commentID_entered-1).getReact().get(x).getReaction().equalsIgnoreCase(reaction_name)){
                        company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getComments().get(commentID_entered-1).getReact().get(x).setCount();
                        check_if_reaction_exists=true;
                        break;
                    }
                } //end of the for loop
                if (!check_if_reaction_exists){ //if the reaction is not existed then add a new reaction
                    React new_reaction = new React();
                    new_reaction.setReaction(input_letter);
                    company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getComments().get(commentID_entered-1).setReact(new_reaction);
                }
                new Json().update_company_info(company); //save the changes to the json file
            } //end of the whole else if for adding a reaction

            //edit the description or redo or undo
            else if(letter.equalsIgnoreCase("d")){
                if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getCreatedBy().equalsIgnoreCase(Company.getUser_who_entered())){
                    enter.nextLine();
                    while (true) {
                        System.out.print(
                                "C. Replace the description\n" +
                                        "R. Redo\n" +
                                        "U. Undo\n" +
                                        "B. Go Back\n" +
                                        "\n" +
                                        "Enter the letter of your choice: ");

                        String choice = enter.nextLine();

                        if (choice.equalsIgnoreCase("R")) { //if redo
                            if (!Company.getUndoRedoStackDescription().is_empty_redoStack()) { //check if the redo is not empty
                                Company.getUndoRedoStackDescription().push_to_undoStack(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getDescriptionText());
                                System.out.println("The comment is redo to: " + Company.getUndoRedoStackDescription().peek_from_redoStack());
                                company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).setDescriptionText(Company.getUndoRedoStackDescription().pop_from_redoStack());
                                new Json().update_company_info(company); //update all info
                            } //end of the if
                            else System.out.println("The redo is empty!");
                        } //end of the if
                        else if (choice.equalsIgnoreCase("U")) {
                            if (!Company.getUndoRedoStackDescription().is_empty_undoStack()) {
                                if (Company.getUndoRedoStackDescription().size_undoStack() != 1) Company.getUndoRedoStackDescription().pop_from_undoStack();
                                if(!Company.getUndoRedoStackDescription().peek_from_undoStack().equalsIgnoreCase(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getDescriptionText())) {
                                    Company.getUndoRedoStackDescription().push_to_redoStack(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getDescriptionText());
                                    System.out.println("The comment is undo to: " + Company.getUndoRedoStackDescription().peek_from_undoStack());
                                    company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).setDescriptionText(Company.getUndoRedoStackDescription().pop_from_undoStack());
                                    new Json().update_company_info(company); //update all info
                                } //end of the it
                            } else System.out.println("The undo is empty!");
                        } //end of the if
                        else if(choice.equalsIgnoreCase("C")){
                            System.out.println("Enter:");
                            String new_text = enter.nextLine();
                            Company.getUndoRedoStackDescription().push_to_undoStack(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getDescriptionText());
                            company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).setDescriptionText(new_text);
                            Company.getUndoRedoStackDescription().push_to_undoStack(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getDescriptionText());
                            //change the comment
                            System.out.print("The comment has been changed to: "+company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getDescriptionText()+"\n");
                            new Json().update_company_info(company); //update all info
                        }
                        else if(choice.equalsIgnoreCase("b"))break;
                        else System.out.println("Enter again!\n"); //if the user entered wrong choice
                    } //end of the while
                }
                else System.out.println("You cannot edit this issue's description!\n");
            }

            // Change issue statues to resolved, only the one who made the comment can this feature
            else if(letter.equalsIgnoreCase("c")){
                //check if the user who wanna do the change is the same as the user who created the issue"
                if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getCreatedBy().equalsIgnoreCase(Company.getUser_who_entered())||
                        Company.getUser_who_entered().equalsIgnoreCase("admin")){
                    company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).setStatus("resolved"); //if he is the same user, then change it
                    new Json().update_company_info(company); //update the information to the json file
                    System.out.println("The statue changes to resolved.");
                }
                else System.out.println("Only the user who made the issue can do this feature! "); //if not the same then print this
                exit(); //to check if the user wants to exit
            }


            //go the the issue dashboard page
            else if(letter.equalsIgnoreCase("b")){
                new Issue_Dashboard().display_IssueDashboard();
            }



            //more features:
            else if(letter.equalsIgnoreCase("m")){
                enter.nextLine(); //just to take the press of the enter button
                //first of all check if the user is the admin or the one who made the issue, if ues then he can access this features:
                if(Company.getUser_who_entered().equalsIgnoreCase("admin")||
                        company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getCreatedBy().equalsIgnoreCase(Company.getUser_who_entered())){
                    while (true){
                        System.out.print("\n" +
                                "A. Change issue statues to closed\n" + //only the admin
                                "B. Change issue statues to reopen\n"+ //only the createdBy user or the admin
                                "C. Go back\n"+
                                "\n" +
                                "Enter the letter of your choice: ");
                        String input_letter = enter.nextLine(); //take the choice input
                        if(input_letter.equalsIgnoreCase("A")){ //change issue statues to closed
                            if(Company.getUser_who_entered().equalsIgnoreCase("admin")){
                                company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).setStatus("closed");
                                new Json().update_company_info(company); //update the information
                                System.out.println("The statue changes to closed.");
                                exit(); //to check if the user wants to exit
                            }
                            else System.out.println("You do not have an access!");
                        }
                        else if(input_letter.equalsIgnoreCase("B")){
                            company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).setStatus("Reopen");
                            new Json().update_company_info(company); //update the information
                            System.out.println("The statue changes to Reopen.");
                            exit(); //to check if the user wants to exit
                        }
                        else if(input_letter.equalsIgnoreCase("C"))break; // go back to the all choices of the issue page
                        else System.out.println("Enter correct number!\n"); //if entered wrong letter
                    } //end of the while loop
                } // end of the if
                else System.out.println("You do not have an access!"); //if it is not the admin
            }

            else System.out.println("Enter again!\n"); //if the user entered wrong choice
            new Json().update_company_info(company); //update all info
        } //end of the while loop
    } //end of the method

    //this method is to ask the user if he wants to exit or continue
    private static void exit(){
        java.util.Scanner enter = new java.util.Scanner(System.in);
        System.out.print("Type anything to go back to the choices OR type 'e' to exit: ");
        String check_if_exit = enter.nextLine();
        if (check_if_exit.equalsIgnoreCase("e"))
            System.exit(0); //exit the program
    } //end of the method
} //end of the class