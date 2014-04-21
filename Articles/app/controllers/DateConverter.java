package controllers;

import java.util.Locale;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

public class DateConverter{
	
	//Convert date to ideal format
	public static String dateToPrint(Date date){
		
		//Get local type of display
		Locale locale = Locale.getDefault();
		
		//Define final date format
		SimpleDateFormat format = new SimpleDateFormat("EEE dd MM yyyy HH:mm:ss", locale);
		
		//Creates a String containing the display defined above
		String dateToPrint = format.format(date);
		
		return dateToPrint;
	}
}