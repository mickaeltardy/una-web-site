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
	public String dateToPrint;
	public String content;
	public String status;
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
	
	public Date getPublicationDate(){
		return publicationDate;
	}
	
	public String getDateToPrint(){
		return dateToPrint;
	}
	
	public String getContent(){
		return content;
	}
	
	public String getArticleId(){
		return articleId;
	}
	
	public void setAuthor(User user){
		author = user;
	}
	
	public void setPublicationDate(Date date){
		publicationDate = date;
	}
	
	public void setDateToPrint(String dateToPrint){
		this.dateToPrint = dateToPrint;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	
	public void setArticleId(String articleId){
		this.articleId = articleId;
	}
}