import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//this class is for the sign up GUI
public class Sign_Up implements ActionListener {
    private static JTextField userText;
    private static JPasswordField passText;
    private static JLabel message;
    private static JFrame Sframe;

    //call this method when you want to close a frame (GUI), if you give true then it will open, if false then it will close
    public static void setFrame_to_Close(boolean e){Sframe.setVisible(e);}


    //create the look of the sign up:
    public static void main() {
        JPanel panel = new JPanel();
        Sframe = new JFrame();
        Sframe.setSize(350, 200);
        Sframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Sframe.add(panel);

        panel.setLayout(null);

        JLabel userLabel = new JLabel("New User:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        userText = new JTextField(18);
        userText.setBounds(100, 20, 180, 25);
        panel.add(userText);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(10, 50, 80, 25);
        panel.add(passLabel);

        passText = new JPasswordField();
        passText.setBounds(100, 50, 180, 25);
        panel.add(passText);

        JButton login_page = new JButton("Already have an Account");
        login_page.setBounds(102, 105, 180, 20);
        login_page.addActionListener(new Login_go());
        panel.add(login_page);

        JButton signIn = new JButton("Sign Up");
        signIn.setBounds(153, 83, 79, 19);
        signIn.addActionListener(new Sign_Up());
        panel.add(signIn);

        message = new JLabel("");
        message.setBounds(10, 123, 300, 25);
        panel.add(message);
        Login log = new Login();

        setFrame_to_Close(true);
    }


    //this method is for the function of the entering the new user name and the password
    @Override
    public void actionPerformed(ActionEvent e) {
        //if the user does not exist, create a new user:
        Users user1 = new Users();
        String newuser = userText.getText(); //take the user name
        user1.setUsername(newuser); //assign it to the object user1 of the Users class
        String pass = passText.getText();  //take the input for the password
        user1.setPassword(pass); //assign it to the object user1 of the Users class

        Company.setUser_who_entered(newuser); //this to know in other classes and places in the code, who singed up.

        Company company = new Json().get_company_info(); //to take all the information from the JSON file

        if (company!=null){ //check if the JSON file is not empty
            user1.setUserid(company.getUsers().size()+1);
            boolean check_if_user_existed = false;
            for(int x=0; x<company.getUsers().size(); x++) { //to check for the user if already existed
                if (company.getUsers().get(x).getUsername().equalsIgnoreCase(newuser)){
                    check_if_user_existed= true;
                    break;
                }
            }

            if(check_if_user_existed){
                message.setText("Username exists! ");
                System.out.println("User name already chosen, please enter another username:");
                new Sign_Up();
            }
            else if(newuser.matches(".*\\s.*")|| newuser.isEmpty()||pass.matches(".*\\s.*")||pass.isEmpty()){
            /*
               - The first ".*" says that there can be zero or more instances of any character in front of the space.
               - The "\\s" says it must contain any whitespace character.
               - The last ".*" says there can be zero or more instances of any character after the space.
                */
                message.setText("Enter correct username!");
            }

            else{//if the user not already existed:
                company.addUsers(user1);
                message.setText("User added successful");
                System.out.println( newuser + " added successfully.");
                Sframe.setVisible(false);
                new Json().update_company_info(company);//to write all the values in a json file
                Main_Page.Bugs_Life(); //to run after singing up
            }
        }

        else { //if the JSON file is empty then:
            if (newuser.matches(".*\\s.*") || newuser.isEmpty()|| pass.matches(".*\\s.*")||pass.isEmpty()) {
                message.setText("Enter correct username!");
                new Sign_Up();
            } else {
                Company company1 = new Company(); //create a new object of the company
                user1.setUserid(1); //set the user id to 1, because there are no users before the json is new
                company1.addUsers(user1); //add the user to the arraylist of users in the company object
                message.setText("User added successful");
                System.out.println(newuser + " added successfully.");
                Sframe.setVisible(false); //close the Sing-up GUI
                new Json().update_company_info(company1);//to write all the values in a json file
                Main_Page.Bugs_Life(); //to run after singing up
            } //end of the else
        }

    } // end of the method

} //end of the class