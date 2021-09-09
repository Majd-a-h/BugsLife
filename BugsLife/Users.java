//this class is just to hold the information of the users of the program, has three variables with their setters and getters
public class Users {
    private int userid;
    private String username;
    private String password;

    public Users() {}

    public int getUserid(String username) {return userid;}
    public void setUserid(int userid) {this.userid = userid;}

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
}