import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

//this class to hold the information of the comment object
public class Comment implements Comments_ADT {
     private int comment_id; //start from 1
     private String text;
     private ArrayList<React> react= new ArrayList<>(); //to hold the reactions to a comment
     private Long timestamp; //the time when the comment is created
     private String user;

    public Comment() {}

    @Override
    public void display_Comment() {
        Company company = new Json().get_company_info();
        System.out.println("#"+comment_id+"      Created on: " + getCommentDate()+"     By: "+ getUser()+"\n"+ getText());
        for(int x=0;x<react.size();x++) System.out.println(getReact().get(x).displayReaction()); //print the reactions of the comment
        System.out.println();
    }

    @Override
    public void add_newComment() {
        Company company = new Json().get_company_info();

        //set the comment id
        if(company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getComments().size()==0) //if not comment before
            comment_id=1;
        else //if there is Comment before
            comment_id= company.getProjects().get(Company.getProjectIndex_entered()).getIssues().get(Company.getIssueIndex_entered()).getComments().size()+1;

        //set text
        Scanner enter = new Scanner(System.in);
        System.out.print("Enter comment:");
        String input_text = enter.nextLine();
        setText(input_text);


        //set timestamp
        setTimestamp();

        //set User
        setUser(Company.getUser_who_entered());

    }

    @Override
    public int getComment_id() { return comment_id; }

    @Override
    public String getUser() { return user; }

    @Override
    public void setUser(String user) { this.user=user; }

    @Override
    public String getText() { return text; }

    @Override
    public void setText(String text) { this.text=text; }

    @Override
    public ArrayList<React> getReact() { return react; } //return the arraylist react

    @Override
    public void setReact(React reaction) { react.add(reaction); } //add the reaction object to the react arraylist

    @Override
    public Date getCommentDate() { return new Date(timestamp); } //return the timestamp as a date

    @Override
    public void setTimestamp() { //create the timestamp from the current date
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(); //take the current date
        String date_as_string = formatter.format(date); //convert the current date to a string
        Timestamp create_timestamp = Timestamp.valueOf(date_as_string); //convert the string to a timestamp object
        timestamp = create_timestamp.getTime(); //get the value of the timestamp object to the timestamp variable in the class
    }

}