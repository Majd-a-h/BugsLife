import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
public class Json {
    public Company get_company_info(){ //to invoke it: Company company = new Json().get_company_info();
        Company company = new Company(); //make an object of Company class to hold the information form the JSON file.
        try{
            Reader reader = Files.newBufferedReader(Paths.get("data.json")); //read from the JSON file
            company = new Gson().fromJson(reader, Company.class); //assign the information from the JSON file to the company object
            reader.close(); //close the reader
        } catch (Exception c){ //if there is no file already, then create a one
            try {
                PrintWriter create_json = new PrintWriter(new FileOutputStream("data.json"));
                create_json.close();
            }
            catch (IOException e) { e.printStackTrace(); }
        }
        return company;
    } //end of the method
    public void update_company_info(Company company){ //to invoke it: new Json().update_company_info(company);
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create(); //make a new GSON object and set it to be pretty printing when you save it to the JSON file.
            Writer writer = Files.newBufferedWriter(Paths.get("data.json")); //write on this file
            gson.toJson(company, writer);  //write the object company that you got from the parameter on the json file as json type.
            writer.close(); //close the writer
        } catch (Exception ex) { System.out.println("There is an error in Json class in update_company_info method"); } //if there is an error with writing on the JSON file
    } //end of the method
} //end of the class