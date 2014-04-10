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
    	return ok(authenticationPage.render());
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
				return badRequest(authenticationPage.render());
			}
		}
		else{
			return badRequest(authenticationPage.render());
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
    	return ok(articles.render(ArticleDAO.all(UserDAO.getByLogin(session("login"))),sessionExists()));
    }
    
    //Displays New Article Page
    public static Result newArticlePage(){
    	//@param: Passes to the HTML page the User loged in
    	//and 'true' because there is a User loged in if we get to this point
    	return ok(newArticlePage.render(UserDAO.getByLogin(session("login")),true));
    }
    
    //Displays the Modify Article Page
    public static Result modifyArticlePage(String articleToBeModifiedId){
    	//Same parameters as previously
    	//But we had an argument containing the Article to be modified
    	return ok(modifyArticlePage.render(UserDAO.getByLogin(session("login")),true,ArticleDAO.getById(articleToBeModifiedId)));
    }
    
    public static Result createArticle(){
		 Form<Article> articleForm = Form.form(Article.class).bindFromRequest();  
		 if(!articleForm.hasErrors()){
			 //Collects data from the form
			 Article art = articleForm.get();
			 
			 //Set author and date fields
			 art.setAuthor(UserDAO.getByLogin(session("login")));
			 Date date = new Date(); // Gets current date
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
			 
			 //Set author and date fields
			 art.setAuthor(ArticleDAO.getById(articleToBeModifiedId).getAuthor());
			 Date date = new Date(); // Gets current date
			 art.setPublicationDate(date);
			 art.setStatus("Modified by "+UserDAO.getByLogin(session("login")).getRole()+" "+UserDAO.getByLogin(session("login")).getName());
			 
			 //Deletes the previous Article
			 ArticleDAO.delete(articleToBeModifiedId);
			 
			 //And adds the modified Article to the database
			 ArticleDAO.create(art);
		 }
		 return redirect(routes.Application.articlesManager());  
    }
    
    public static Result deleteArticle(String id){
		 ArticleDAO.delete(id);
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
			 UserDAO.create(it);  
		 }
		 return userManager();  
	 }
    
    public static Result removeUser(String id){
		 UserDAO.delete(id);
		 return redirect(routes.Application.userManager());
	 }
    
}
