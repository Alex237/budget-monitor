package fr.alvini.insta.budgetmonitor.model;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ObjectModel {

	public ObjectModel() {
		// TODO Auto-generated constructor stub
	}
	
	public static String formatDate(GregorianCalendar date, boolean forDB) {
		String dateFormatted = String.valueOf(date.get(Calendar.YEAR));
		if (forDB)
			dateFormatted += "/"+String.valueOf(date.get(Calendar.MONTH)+1);
		else	
			dateFormatted += "/"+String.valueOf(date.get(Calendar.MONTH));
		dateFormatted += "/"+String.valueOf(date.get(Calendar.DAY_OF_MONTH));
		
		return dateFormatted;
	}
	
	public static String getDateElement(String element,String dateInString) {
		String delimiter = "/";
		String[] tmp = dateInString.split(delimiter);
		String elementToReturn = "";
		if (element == "year")
			elementToReturn = tmp[0];
		else if (element == "month")
			elementToReturn = tmp[1];
		else if (element == "day")
			elementToReturn = tmp[2];			
		
		return elementToReturn;
	}

	public static String generate(int length) {
	    String chars = "ABCDEF1234567890"; 
	    String pass = "";
	    for(int x=0;x<length;x++)   {
	       int i = (int)Math.floor(Math.random() * chars.length() -1);
//	       System.out.println("Test longueur chars : "+chars.length());
//	       System.out.println("Test color : "+Math.floor(Math.random() * chars.length() -1));
//	       System.out.println("Test i : "+i);
	       if (i < 0 || i > chars.length())
	    	   i = 0;
	       pass += chars.charAt(i);
	    }
	    System.out.println(pass);
	    return pass;
	}
}
