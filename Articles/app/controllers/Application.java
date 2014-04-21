package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import java.util.Date;
import views.html.*;
import models.*;

public class Application extends Controller {

    //Displays the front page
    public static Result frontPage(){  
		 return ok(frontPage.render(sessionExists()));
	 }
    
    
    /* Authentication Management
     * 
     */
    
    //Displays authentication page
    public static Result authenticationPage(){
    	return ok(authenticationPage.render(false));
    }
    
    //Authentication method
    public static Result authenticate(){  
    	
    	Control control = new Control(); // Will allow to check login and password
    	
    	Form<User> userForm = Form.form(User.class).bindFromRequest(); // Get the entry
    	
    	if(!userForm.hasErrors()){// If the entry has no errors
    		
			if(control.authenticate(userForm.get())){// Check login and password
				session().clear();  
				session("login", userForm.get().login);  
				return redirect(routes.Application.frontPage());
				
			}
			else{
				return badRequest(authenticationPage.render(true));
			}
		}
		else{
			return badRequest(authenticationPage.render(true));
		}
	}
    
    //Logout method
    public static Result logout(){
		 session().clear();
		 return redirect(routes.Application.frontPage());
	 }
    
    //Checks whether there is an user loged in or not
    public static boolean sessionExists(){
    	String user = session("login");
    	
		 if(user==null){return false;} // Returns false if there is no user loged in
		 
		 else{return true;}// Returns true if there is an user loged in
    }
    
    /* Article Management
     * 
     */
    
    //Displays Article Manager (Temporary)
    public static Result articlesManager(){
    	return ok(articles.render(ArticleDAO.all(UserDAO.getByLogin(session("login"))),sessionExists(), UserDAO.getByLogin(session("login")).getRole(), UserDAO.getByLogin(session("login")).getName()));
    }
    
    //Displays New Article Page
    public static Result newArticlePage(){
    	//@param: Passes to the HTML page the User loged in
    	//and 'true' because there is a User loged in if we get to this point
    	return ok(newArticlePage.render(true, UserDAO.getByLogin(session("login")).getRole(), UserDAO.getByLogin(session("login")).getName()));
    }
    
    //Displays the Modify Article Page
    public static Result modifyArticlePage(String articleToBeModifiedId){
    	//Same parameters as previously
    	//But we had an argument containing the Article to be modified
    	return ok(modifyArticlePage.render(true, ArticleDAO.getById(articleToBeModifiedId), UserDAO.getByLogin(session("login")).getRole(), UserDAO.getByLogin(session("login")).getName()));
    }
    
    public static Result createArticle(){
		 Form<Article> articleForm = Form.form(Article.class).bindFromRequest();  
		 if(!articleForm.hasErrors()){
			 //Collects data from the form
			 Article art = articleForm.get();
			 
			 //Set author and date fields
			 art.setAuthor(UserDAO.getByLogin(session("login")));
			 Date date = new Date(); // Gets current date
			 art.setDateToPrint(DateConverter.dateToPrint(date)); //Converts date to ideal display
			 art.setPublicationDate(date);
			 art.setStatus("Checked");
			 
			 //Adds the Article to the database
			 ArticleDAO.create(art);
		 }
		 return redirect(routes.Application.articlesManager());  
	}
    
    public static Result modifyArticle(String articleToBeModifiedId){
    	 Form<Article> articleForm = Form.form(Article.class).bindFromRequest();  
		 if(!articleForm.hasErrors()){
			 //Collects data from the form
			 Article art = articleForm.get();
			 Article articleToBeModified = ArticleDAO.getById(articleToBeModifiedId);
			 
			 //Sets all the changed fields
			 articleToBeModified.setName(art.getName());
			 articleToBeModified.setContent(art.getContent());
			 articleToBeModified.setTag(art.getTag());
			 articleToBeModified.setStatus("Modified by "+UserDAO.getByLogin(session("login")).getRole()+" "+UserDAO.getByLogin(session("login")).getName());
			 
			 ArticleDAO.updateArticle(articleToBeModified);
		 }
		 return redirect(routes.Application.articlesManager());  
    }
    
    public static Result deleteArticle(String id){
    	CommentaryDAO.deleteArticleCommentaries(id);
		ArticleDAO.delete(id);
		return redirect(routes.Application.articlesManager());
	}
    
    
    /* Commentary Managemnt
     * 
     */
    
    public static Result addCommentary(String id){
   	 	Form<Commentary> commentaryForm = Form.form(Commentary.class).bindFromRequest();  
		if(!commentaryForm.hasErrors()){
			 //Collects data from the form
			 Commentary com = commentaryForm.get();
			 
			 //Set author, date and Article fields
			 com.setAuthor(UserDAO.getByLogin(session("login")));
			 Date date = new Date(); // Gets current date
			 com.setDateToPrint(DateConverter.dateToPrint(date)); //Converts date to ideal display
			 com.setPublicationDate(date);
			 com.setStatus("");
			 com.setArticleId(id);
			 
			 //Adds the commentary to the database
			 CommentaryDAO.create(com);
			 ArticleDAO.addCommentary(id,CommentaryDAO.getByDateAndAuthor(date, UserDAO.getByLogin(session("login"))).getId());
		 }
		 return redirect(routes.Application.articlesManager());  
   }

   public static Result modifyCommentary(String id){
	   Form<Commentary> commentaryForm = Form.form(Commentary.class).bindFromRequest();  
	   if(!commentaryForm.hasErrors()){
		   //Collects data from the form
		   Commentary comForm = commentaryForm.get();
		   //Collects data from the original Commentary
		   Commentary comOld = CommentaryDAO.getById(id);
		   
		   //Updates fields that have changed
		   comOld.setContent(comForm.getContent());
		   comOld.setStatus("Modified");
		   
		   //Removes the old Commentary
		   deleteCommentary(id);
		   
		   //Adds the updated one
		   CommentaryDAO.create(comOld);
		   ArticleDAO.addCommentary(comOld.getArticleId(),CommentaryDAO.getByDateAndAuthor(comOld.getPublicationDate(), comOld.getAuthor()).getId());
	   }
	   return redirect(routes.Application.articlesManager());
   }
    
   public static Result deleteCommentary(String id){
    	ArticleDAO.removeCommentary(CommentaryDAO.getById(id).getArticleId(),id);
		CommentaryDAO.delete(id);
		return redirect(routes.Application.articlesManager());
   }
    
   /* Temporary User Management
    * 
    */
    
    //Displays User Manager
    public static Result userManager(){
    	return ok(userManagement.render(UserDAO.all()));
    }
    
    public  static  Result insertUser(){
		 Form<User> userForm = Form.form(User.class).bindFromRequest();  
		 if(!userForm.hasErrors()){
			 User it = userForm.get();
			 it.setPassw(Md5.encode(it.getPassw()));
			 UserDAO.create(it);  
		 }
		 return userManager();  
	 }
    
    public static Result removeUser(String id){
		 UserDAO.delete(id);
		 return redirect(routes.Application.userManager());
	 }
    
}
