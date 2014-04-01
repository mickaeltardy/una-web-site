package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import views.html.*;
import models.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
    public static Result show() {
    	return ok(show.render("Articles","Coucou"));
    }
    
    
  //Displays the front page
    public static Result frontPage(){
    	return ok(frontPage.render());
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
		Form<User> userForm = Form.form(User.class).bindFromRequest(); 
		if(!userForm.hasErrors()){
			session().clear();  
			session("login", userForm.get().login);  
            return redirect(routes.Application.frontPage());
        }
		else{
			return badRequest(authenticationPage.render());
		}
	}
    
    
    
    //Displays Article Manager (Temporary)
    public static Result articlesManager(){
    	return ok(articles.render(Article.all()));
    }
    
    
    
    /* Temporary User Management
     * 
     */
    
    //Displays User Manager
    public static Result userManager(){
    	return ok(userManagement.render(User.all()));
    }
    
    public  static  Result insertUser(){
		 Form<User> userForm = Form.form(User.class).bindFromRequest();  
		 if(!userForm.hasErrors()){
			 User it = userForm.get();
			 
			 User.create(it);  
		 }  
		 return userManager();  
	 }
    
    public static Result removeUser(String id){
		 User.delete(id);
		 return redirect(routes.Application.userManager());
	 }
    
    
    
    

}
