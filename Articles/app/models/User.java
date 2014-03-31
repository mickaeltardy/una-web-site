package models;

import java.util.*;
import play.modules.mongodb.jackson.MongoDB;
import net.vz.mongodb.jackson.*;
import org.codehaus.jackson.annotate.JsonProperty;


@MongoCollection(name="users")
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

	public static List<User> all() {
	    return coll.find().toArray();
	}

	public static void create(User user) {
		coll.save(user);
	}

	public static void delete(String id) {
	    User user = User.coll.findOneById(id);
	    if (user != null)
	        coll.remove(user);
	}
}