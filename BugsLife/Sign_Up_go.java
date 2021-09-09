import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//this class just a connection when you want to go from the login to the sign ip.
public class Sign_Up_go implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Traveling to sign up page...");
        Login.setFrame_to_Close(false); //to close the login gui
        Sign_Up.main(); //to open the sign up page
    }
}