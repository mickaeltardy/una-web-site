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
	public ArrayList<Commentary> commentaries = new ArrayList(100);
	
	/* Attribute Access Methods
	 * 
	 */
	public String getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public String getContent(){
		return content;
	}
	
	public String getTag(){
		return tag;
	}
	
	public User getAuthor(){
		return author;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public void setTag(String tag){
		this.tag = tag;
	}
	
	public void setAuthor(User user){
		author = user;
	}
	
	public void setPublicationDate(Date date){
		publicationDate = date;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	
	//Particular method: allow to add a commentary to the attribute List
	public void addCommentary(Commentary commentary){
		commentaries.add(commentary);
	}
	
	//Particular method: allow to remove a commentary from the attribute List
	public void removeCommentary(Commentary commentary){
		String id = commentary.getId();
		
		//The remove List method does not work with the Commentary in arguemnt here. Therefore, I had to make a remove method from scratch.
		//It goes throught the List searching for a Commentary fitting the given id. When it founds it, it removes the fitting Commentary from the List.
		for(Commentary x : commentaries){
			if(x.getId().equals(id)){
				commentaries.remove(x);
				break;
			}
		}
	}
	
}