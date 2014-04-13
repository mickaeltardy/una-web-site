package models;

import java.util.*;
import play.modules.mongodb.jackson.MongoDB;
import net.vz.mongodb.jackson.*;
import org.codehaus.jackson.annotate.JsonProperty;

public class CommentaryDAO{
	
	private static JacksonDBCollection<Commentary, String> coll = MongoDB.getCollection("commentaries", Commentary.class, String.class);

	
	/* Basic methods to handle DAO
	 * 
	 */
	
	// Method to collect all Commentaries from DataBase
	public static List<Commentary> all() {
	    return coll.find().toArray();
	}

	// Method to add a Commentary to the DataBase
	public static void create(Commentary commentary) {
		coll.save(commentary);
	}

	// Method to delete a Commentary from the DataBase
	public static void delete(String id) {
	    Commentary commentary = coll.findOneById(id);
	    if (commentary != null)
	        coll.remove(commentary);
	}
	
	
	/* Methods more specific below
	 * 
	 */

	//Get the Commentary corresponding to the Id in argument
	public static Commentary getById(String commentaryId){
		return coll.findOneById(commentaryId);
	}
	
	// Get all the Commentaries written by this specific User
	public static List<Commentary> all(User author){
		return coll.find(DBQuery.is("author",author)).toArray();
	}
	
	//Get all the Commentaries corresponding to the given Article
	public static List<Commentary> all(String articleId){
		return coll.find(DBQuery.is("articleId",articleId)).toArray();
	}

	//Remove from the database all the Commentaries corresponding to the given Article
	public static void deleteArticleCommentaries(String articleId){
		for(Commentary commentary : all(articleId)){
			if(commentary != null)
		        coll.remove(commentary);
		}
	}
	
	//Get the commentary fitting the specified date and author
	//Necessary to be able to search for a commentary which was just added to the database, without knowing its Id
	public static Commentary getByDateAndAuthor(Date date, User author){
		return coll.findOne(DBQuery.and(DBQuery.is("publicationDate",date), DBQuery.is("author",author)));
	}
}