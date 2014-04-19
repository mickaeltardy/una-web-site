package controllers;

import java.util.*;

import models.*;

public class Control{
	
	/* Attribute allowing to check whether or not 
	 * current user is allowed to access what he requests
	 */
	public boolean ok;
	
	//Default Constructor
	public Control(){
		ok = false;
	}
	
	/* Authentication check method
	 * 
	 * @param : Takes User created in the entry form
	 * @result : Returns whether the user exists or not
	 */
	public boolean authenticate(User user){
		User userFittingLogin = UserDAO.getByLogin(user.getLogin());
		
		if((userFittingLogin!=null)
				&&(userFittingLogin.getLogin().equals(user.getLogin()))
				&&(userFittingLogin.getPassw().equals(Md5.encode(user.getPassw())))){
			ok = true;
		}
		
		return ok;
	}
	
	
	
}