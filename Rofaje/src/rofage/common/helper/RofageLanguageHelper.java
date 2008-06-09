package rofage.common.helper;

import java.util.TreeSet;

public class RofageLanguageHelper {
	public final static String LANG_FR	= "french.properties";
	
	public final static TreeSet<String> availableLanguages = new TreeSet<String>();
	
	static {
		availableLanguages.add("Français");
	}
}
