package fr.alvini.insta.budgetmonitor.model;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ObjectModel {

	public ObjectModel() {
		// TODO Auto-generated constructor stub
	}
	
	public static String formatDate(GregorianCalendar date) {
		String dateFormatted = String.valueOf(date.get(Calendar.YEAR));
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

}
