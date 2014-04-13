package models;

import java.util.*;
import play.data.format.*;
import play.data.validation.Constraints.*;
import play.data.validation.*;
import play.modules.mongodb.jackson.MongoDB;
import net.vz.mongodb.jackson.*;
import org.codehaus.jackson.annotate.JsonProperty;

@MongoCollection(name="commentaries")
public class Commentary{
	
	@Id
	@ObjectId
	public String id;
	
	public Date publicationDate;
	public String content;
	public User author;
	public String articleId;
	/* Attribute Access Methods
	 * 
	 */
	
	public String getId(){
		return id;
	}
	
	public User getAuthor(){
		return author;
	}
	
	public void setAuthor(User user){
		author = user;
	}
	
	public void setPublicationDate(Date date){
		publicationDate = date;
	}
	
	public void setArticleId(String articleId){
		this.articleId = articleId;
	}
}