package rofage.common.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class StringHelper {
	
	/**
	 * Checks whether the string is a valid email
	 * @param email
	 * @return true if this is an email, false otherwise
	 */
	public static boolean isEMail (String email) {
	      //Set the email pattern string
	      Pattern p = Pattern.compile(".+@.+\\.[a-z]+");

	      //Match the given string with the pattern
	      Matcher m = p.matcher(email);

	      //check whether match is found 
	      return m.matches();
	}
}
