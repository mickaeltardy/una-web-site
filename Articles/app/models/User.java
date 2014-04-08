package models;

import java.util.*;

import play.modules.mongodb.jackson.MongoDB;
import net.vz.mongodb.jackson.*;
import play.data.format.*;
import play.data.validation.Constraints.*;
import play.data.validation.*;

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
	
	//Attribute Access methods
		public String getLogin(){
			return login;
		}
		
		public String getPassw(){
			return passw;
		}
	
}