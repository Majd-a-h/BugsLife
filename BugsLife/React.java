import java.util.Scanner;

//this class for the reactions on the comment
public class React {
    private String reaction; // happy || angry || smile || sad
    private int count; //how many reacts

    //the count of the reaction start from zero
    public React() { count=0; }

    //a method to print the reaction under its comment
    public String displayReaction() { return "$$ " + getCount() +" people React with "+ reaction; }

    public int getCount() {return count; }


    //each time this method called, that means a reaction is created then increase the count by 1
    public void setCount() {count++;}


    public String getReaction() { return reaction; }

    // this method is to get the letter is a parameter and then set the reaction based on it
    public void setReaction(String letter) {
        while(true){
            if(letter.equalsIgnoreCase("a")){
                reaction= "angry";
                setCount(); //increase by 1
                break;
            }
            else if(letter.equalsIgnoreCase("h")){
                reaction="happy";
                setCount(); //increase by 1
                break;
            }
            else if(letter.equalsIgnoreCase("s")){
                reaction= "smile";
                setCount(); //increase by 1
                break;
            }
            else if(letter.equalsIgnoreCase("d")){
                reaction = "sad";
                setCount(); //increase by 1
                break;
            }
            else { //if the parameter is a wrong letter
                System.out.print("Wrong input! Enter the input again:");
                Scanner input = new Scanner(System.in);
                letter = input.next(); //take the letter again, and then the program will go again in the while loop and check
            }
        }
    }
}
