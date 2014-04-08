package models;

import java.util.*;
import play.data.format.*;
import play.data.validation.Constraints.*;
import play.data.validation.*;
import play.modules.mongodb.jackson.MongoDB;
import net.vz.mongodb.jackson.*;
import org.codehaus.jackson.annotate.JsonProperty;

@MongoCollection(name="articles")
public class Article{
	
	@Id
	@ObjectId
	public String id;
	
	public String name;
	public Date publicationDate;
	public String content;
	public String tag;
	public String status;
	public User author;
	
	/* Attribute Access Methods
	 * 
	 */
	public String getName(){
		return name;
	}
	
	public void setAuthor(User user){
		author = user;
	}
	
	public void setPublicationDate(Date date){
		publicationDate = date;
	}
}