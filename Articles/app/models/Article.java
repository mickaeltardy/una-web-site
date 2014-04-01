package models;

import java.util.*;
import play.modules.mongodb.jackson.MongoDB;
import net.vz.mongodb.jackson.*;
import org.codehaus.jackson.annotate.JsonProperty;


@MongoCollection(name="articles")
public class Article{
	@Id
	@ObjectId
	public String id;
	
	public String name;
	public User author;
	public Date publicationDate;
	public String content;
	public String status;
	
	private static JacksonDBCollection<Article, String> coll = MongoDB.getCollection("articles", Article.class, String.class);

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
	    Article article = Article.coll.findOneById(id);
	    if (article != null)
	        coll.remove(article);
	}
}