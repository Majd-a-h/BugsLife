import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//this class just a connection when you want to go from the sign up to the login.
public class Login_go implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Traveling to Log in page...");
        Sign_Up.setFrame_to_Close(false); //close the sign up gui
        Login.main(); //open the login
    }
}