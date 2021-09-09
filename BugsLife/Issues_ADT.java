//this interface is an ADT for the Issues class
public interface Issues_ADT {

    /**
     * return the id of the issue
     * Precondition: The id has been initialized
     * Postcondition: the id is returned
     * @return issue's id
     */
    public abstract int getId();

    /**
     * to give the id a value
     * Precondition: value of the id
     * Postcondition: the id is initialized
     * @param id the value for the id
     */
    public abstract void setId(int id);

    /**
     * to get the title of the issue
     * Precondition: the title has been already initialized
     * Postcondition: get the title of the issue
     * @return tht title of the issue
     */
    public abstract String getTitle();


    /**
     * to set the title of the issue
     * Precondition: the string to be the title of the issue
     * Postcondition: the title of the issue is initialized
     * @param title the string that will be the title
     */
    public abstract void setTitle(String title);

    /**
     * to get the number of the priority of the issue
     * Precondition: the priority of the issue has been already initialized
     * Postcondition: the number of priority is retrieved
     * @return the priority number of the issue
     */
    public abstract int getPriority();

    /**
     *  to set the priority of the issue
     * Precondition: the number of priority is given
     * Postcondition: the priority for the issue has been initialized
     * @param priority the number that will be initialized to be the priority
     */
    public abstract void setPriority(int priority);

    /**
     * to return the statue of the issue
     * Precondition: the status has been initialized
     * Postcondition: the statue of the issue has been returned
     * @return the statue of the issue
     */
    public abstract String getStatus();

    /**
     * to set the statue of the issue from this following choices:
     * In Progress || Open || Closed || Resolved || Reopen
     * Precondition: the statue is given
     * Postcondition: the statue is initialized
     * @param status String contains the status
     */
    public abstract void setStatus(String status);

    /**
     * the tag of the issue, can be Fronted || Backend
     * Precondition: the tag is not empty
     * Postcondition: the tag is returned
     * @return the tag
     */
    public abstract java.util.ArrayList<String> getTag();

    /**
     * to set the tag
     * Precondition: the tag is given
     * Postcondition: the tag is initialized
     * @param entered_tag is given
     */
    public abstract void setTag(String entered_tag);

    /**
     * to return the Description text
     * Precondition: the description text is not empty
     * Postcondition: the description text is returned
     * @return the description text
     */
    public abstract String getDescriptionText();

    /**
     * to set the description text
     * Precondition: the description text is given
     * Postcondition: the description text is set
     * @param descriptionText string is passed
     */
    public abstract void setDescriptionText(String descriptionText);

    /**
     * get the user who created the issue
     * Precondition: the createdBy var is not empty
     * Postcondition: the createdBy user is returned
     * @return the user who made the issue is returned
     */
    public abstract String getCreatedBy();

    /**
     * set the user who created the issue as createdBY
     * Precondition:the user name is given
     * Postcondition: the user is set
     * @param createdBy the user who creates the issue
     */
    public abstract void setCreatedBy(String createdBy);

    /**
     * get the user who will solve the issue
     * Precondition: there is already Assignee
     * Postcondition: the Assignee is returned
     * @return the user who you want to assignee him the problem
     */
    public abstract String getAssignee();

    /**
     * set the Assignee
     * Precondition: the Assignee name is given
     * Postcondition: the name of the Assignee is set
     * @param assignee the name of the one is gonna solve the issue
     */
    public abstract void setAssignee(String assignee);


    /**
     * get the timestamp and then convert it to a date
     * Precondition: the time already initialized
     * Postcondition: the date of when the issue was created.
     * @return the date of the issue
     */
    public abstract String getDate();

    /**
     * add the time of when the issue is created
     * Precondition: the timestamp is giving
     * Postcondition: the date will be added to the issue as timestamp
     */
    public abstract void setTimestamp();

    /**
     * get the timestamp
     * Precondition: the time already initialized
     * Postcondition: the timestamp.
     * @return the timestamp
     */
    public abstract long getTimestamp();


    /**
     * return the Comment of the issue
     * Precondition: the issue has Comment
     * Postcondition:the Comment arraylist is returned
     * @return the arraylist of the Comment
     */
    public abstract java.util.ArrayList<Comment> getComments();

    /**
     * to add a comment to the Comment of the issue
     * Precondition: a new comment is created
     * Postcondition: the comment is added to the arraylist
     * @param Comment a new comment is created and passed to the comment arraylist
     */
    public abstract void setComments(Comment Comment);
}