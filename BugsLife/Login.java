import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login implements ActionListener {

    private static JTextField userText;
    private static JPasswordField passText;
    private static JLabel message;
    private static JFrame frame;


    //if the parameter is false then the GUI will close, if the parameter is true then the GUI will be visible
    public static void setFrame_to_Close(boolean e){frame.setVisible(e);}


    public static void main() {  //you need to call this to start the run and create the login
        JPanel panel = new JPanel();
        frame = new JFrame();
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(null);

        JLabel userLabel = new JLabel("User:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        userText = new JTextField(18);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(10, 50, 80, 25);
        panel.add(passLabel);

        passText = new JPasswordField();
        passText.setBounds(100,50,165,25);
        panel.add(passText);

        JButton button = new JButton("Login");
        button.setBounds(100, 83, 79, 17);
        button.addActionListener(new Login());
        panel.add(button);


        JButton signUp = new JButton("Sign Up");
        signUp.setBounds(185, 83, 79, 17);
        signUp.addActionListener(new Sign_Up_go()); //go to sign_up_go, that will go to the sign up page.


        panel.add(signUp);

        message = new JLabel("");
        message.setBounds(10,110,300,25);
        panel.add(message);

        setFrame_to_Close(true);
    } //end of the method


    @Override
    public void actionPerformed(ActionEvent e) { //this method to take the input of the user and password

        String user = userText.getText(); //get the input of the user name
        String pass = passText.getText(); //get the input of the password

        Company.setUser_who_entered(user); //this will be used in other classes to know who are making changes

        Company company = new Json().get_company_info(); //assign the info from the js

        boolean check_if_username_existed=false;
        if(company!=null){ //if the json file is empty then the company is null, but if it is not then this will run:
            for(int x=0; x<company.getUsers().size(); x++){ //to check for the user if already existed, by going through the users
                if(company.getUsers().get(x).getUsername().equalsIgnoreCase(user)){ //if the user name is exited then:
                    if(company.getUsers().get(x).getPassword().equalsIgnoreCase(pass)) { //if the password is also correct then:
                        message.setText("Login Successful!");
                        System.out.println("ACCESS GRANTED!");
                        frame.setVisible(false);
                        Main_Page.Bugs_Life();
                        check_if_username_existed=true;
                    } //end of the if
                    else{ //if the password not correct
                        message.setText("Incorrect Password" + " (try again)");
                        System.out.println("ACCESS DENIED!");
                        check_if_username_existed=true;
                        new Login(); //open the login for the user for him to choose the sign up if he want or to close the program or to enter the password again
                    } //end of the else
                } //end of the if
        } // end of the for loop
            if(check_if_username_existed==false){
                message.setText("The username does not exist! Please sign-up");
                System.out.println("ACCESS DENIED!");
                new Login(); //open the login for the user for him to choose the sign up if he want or to close the program
            }
        } //end of the if
        else {//go to sign in
            message.setText("The username does not exist! Please sign-up");
            System.out.println("ACCESS DENIED!");
            new Login(); //open the login for the user for him to choose the sign up if he want or to close the program
        }
    }

}