package models;

import java.util.*;
import play.modules.mongodb.jackson.MongoDB;
import net.vz.mongodb.jackson.*;
import org.codehaus.jackson.annotate.JsonProperty;

public class UserDAO{
	
	
private static JacksonDBCollection<User, String> coll = MongoDB.getCollection("users", User.class, String.class);
	
	
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
	    User user = UserDAO.coll.findOneById(id);
	    if (user != null){
	        coll.remove(user);
	    }
	}
	
	//Method to get a user by his login
	public static User getByLogin(String loginEntered){
		
		//Loop running on all the registered users
		for(User x : coll.find().toArray()){
			
			if(x.getLogin().equals(loginEntered)){
				return x;
			}
		}
		
		return null;
	}
}