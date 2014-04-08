package models;

import java.util.*;
import play.modules.mongodb.jackson.MongoDB;
import net.vz.mongodb.jackson.*;
import org.codehaus.jackson.annotate.JsonProperty;

public class ArticleDAO{
	
	private static JacksonDBCollection<Article, String> coll = MongoDB.getCollection("articles", Article.class, String.class);

	
	/* Basic methods to handle DAO
	 * 
	 */
	
	// Method to collect all articles from DataBase
	public static List<Article> all() {
	    return coll.find().toArray();
	}

	// Method to add an article to the DataBase
	public static void create(Article article) {
		coll.save(article);
	}

	// Method to delete an article from the DataBase
	public static void delete(String id) {
	    Article article = coll.findOneById(id);
	    if (article != null)
	        coll.remove(article);
	}
	
	
	/* Methods more specific below
	 * 
	 */
	
	public static List<Article> all(User author){
		return coll.find(DBQuery.is("author",author)).toArray();
	}
	
}