import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Issues implements Issues_ADT {
     private int id; //id of the issue
     private String title; //title of the issue
     private int priority; //the priority of the issue
     private String status; // In Progress || Open || Closed || Resolved || Reopen
     private ArrayList<String> tag = new ArrayList<>(); // Fronted || Backend
     private String descriptionText; //what is the issue about
     private String createdBy; //who created the issue
     private String assignee; //who will solve the issue
     private Long timestamp;  // the time when the issue is created
     private ArrayList<Comment> Comment= new ArrayList<>(); //the comments of the issue

    public Issues() {}

    @Override
    public int getId() {return id;}

    @Override
    public void setId(int id) { this.id=id; }

    @Override
    public String getTitle() { return title; }

    @Override
    public void setTitle(String title) { this.title=title; }

    @Override
    public int getPriority() { return priority; }

    @Override
    public void setPriority(int priority) { this.priority=priority; }

    @Override
    public String getStatus() { return status; }

    @Override
    public void setStatus(String status) { this.status=status; }

    @Override
    public ArrayList<String> getTag() { return tag; }

    @Override
    public void setTag(String entered_tag) {tag.add(entered_tag);} //add the string entered in the tag arraylist

    @Override
    public String getDescriptionText() { return descriptionText; }

    @Override
    public void setDescriptionText(String descriptionText) {this.descriptionText=descriptionText; }

    @Override
    public String getCreatedBy() { return createdBy; }

    @Override
    public void setCreatedBy(String createdBy) { this.createdBy=createdBy; }

    @Override
    public String getAssignee() {return assignee;}

    @Override
    public void setAssignee(String assignee) {this.assignee=assignee;}

    @Override
    public String getDate() { //return the timestamp as a date format
        Date date = new Date(timestamp); //create an object of the date using the timestamp
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy/MM/dd hh:mm"); //format of the look of the date
        String date_formatted= formatDate.format(date); //make the format and assign it to a string
        return date_formatted; //return the string
    } //end of the method

    @Override
    public void setTimestamp() {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //set the format
        String date_as_string = formatter.format(new Date()); //format the now Date as the formatter
        Timestamp create_timestamp = Timestamp.valueOf(date_as_string); //the value of the date in milliseconds
        this.timestamp = create_timestamp.getTime(); //Milliseconds as Long type
    } //end of the method

    @Override
    public long getTimestamp() { return timestamp; }

    @Override
    public ArrayList<Comment> getComments() { return Comment; } //return comment arraylist

    @Override
    public void setComments(Comment Comment) { this.Comment.add(Comment); } //add the comment object to the arraylist

}