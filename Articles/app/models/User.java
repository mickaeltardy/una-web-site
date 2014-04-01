package models;

import java.util.*;

import play.modules.mongodb.jackson.MongoDB;
import net.vz.mongodb.jackson.*;
import play.data.format.*;
import play.data.validation.*;

import org.codehaus.jackson.annotate.JsonProperty;


@MongoCollection(name="User")
public class User{
	@Id
	@ObjectId
	public String id;
	
	//Personal Data
	public String name;
	public int age;
	public String nationality;
	
	//Login Data
	public String login;
	public String passw;
	
	//Specific Data
	public String role; // = "Visitor" or "Member" or "Supervisor" or "Admin" (for now)
	
	private static JacksonDBCollection<User, String> coll = MongoDB.getCollection("users", User.class, String.class);
	
	//Attribute Access methods
	public String getLogin(){
		return login;
	}
	
	/*Methods allowing DataBase Management
	 * 
	 */
	
	// Method to collect all users from DataBase
	public static List<User> all() {
	    return coll.find().toArray();
	}

	// Method to add an user to the DataBase
	public static void create(User user) {
		coll.save(user);
	}

	// Method to delete an user from the DataBase
	public static void delete(String id) {
	    User user = User.coll.findOneById(id);
	    if (user != null)
	        coll.remove(user);
	}
	
	
	
	/*Methods allowing Authentication
	 * 
	 */
	
	//Method to get a user by his login
	public static User getByLogin(String loginEntered){
		
		
		System.out.println(loginEntered);
		
		for(User x : coll.find().toArray()){
			
			System.out.println(x.getLogin().equals(loginEntered));
			
			if(x.getLogin().equals(loginEntered)){
				System.out.println("Ca marche");
				return x;
			}
		}
		
		return null;
	}
	
	// Method for login validation
	public String validate(){
		User u = this.getByLogin(login);
		if(u!=null){
		if(login.equals(getByLogin(login).login)  &&   passw.equals(getByLogin(login).passw))  
			return null;  
		else
			return  "Invalid login  or password";  
		}
		else
			return  "Invalid login  or password";
	}
	
}