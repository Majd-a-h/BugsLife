//this interface is an ADT for the Comment class
public interface Comments_ADT {


    /**
     * get the id of the comment
     * Precondition: the comment already has an id
     * Postcondition: the id of the comment is returned
     * @return the id of the comment
     */
    public abstract int getComment_id();

    /**
     * get the comment through passing the id of it
     * Precondition: there is at least one comment in the issue
     * Postcondition: the information of the comment will be printed
     */
    public abstract void display_Comment();

    /**
     * add comment to the issue
     * Precondition: The issue existed
     * Postcondition: a new comment will be added to the issue
     */
    public abstract void add_newComment();

    /**
     * get the text of the comment's id
     * Precondition: the comment id number
     * Postcondition: the text of the comment
     * @return the text of the comment
     */
    public abstract String getText();

    /**
     * to add the text to a new comment
     * Precondition: the id of the comment you want to add the text to and the text you want to add
     * Postcondition: the text will be added to the comment
     * @param text the text you want to add
     */
    public abstract void setText(String text);

    /**
     * to get the reactions of a comment
     * Precondition: there is at least one reaction in the comment and the id number of the comment
     * Postcondition: all the reaction of a comment
     * @return React arraylist
     */
    public abstract java.util.ArrayList<React> getReact();

    /**
     * to add a reaction to the comment
     * Precondition: the comment exists
     * Postcondition: the reaction is added to the arraylist reactions of the comment
     * @param reaction the reaction you want to add to the arraylist reactions of the comment
     */
    public abstract void setReact(React reaction);

    /**
     * get the timestamp and then convert it to a date
     * Precondition: the comment id number
     * Postcondition: the date of when the comment was created.
     * @return the date of the comment
     */
    public abstract java.util.Date getCommentDate();

    /**
     * add the time of when the comment is created
     * Precondition: the comment id number
     * Postcondition: the date will be added to the comment as timestamp
     */
    public abstract void setTimestamp();

    /**
     * get the name of the user who created the comment
     * Precondition: the comment id number
     * Postcondition: the name of the user who created the comment
     * @return the name of the user who created the comment
     */
    public abstract String getUser();

    /**
     * set the name of the user who is creating the comment
     * Precondition: the name of the user from the user class
     * Postcondition: the name of the user will be added to the comment information
     * @param user to assign the user who made the comment
     */
    public abstract void setUser(String user);
}