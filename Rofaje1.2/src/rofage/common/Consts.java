package rofage.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rofage.ihm.Messages;

/**
 * Container for internal consts
 * @author pierrot
 *
 */
public abstract class Consts {
	public final static String HOME_FOLDER 				= System.getProperty("user.dir"); //$NON-NLS-1$
	public final static String TMP_FOLDER				= System.getProperty("java.io.tmpdir");
	public final static String ICO_FOLDER				= "icons";
	
	public static HashMap<String,String> COUNTRY_NAMES 		= new HashMap<String,String>();
	public final static HashMap<String,String> FLAG_NAMES			= new HashMap<String,String>();
	public static List <String> LANG_NAMES					= new ArrayList<String>();
	public final static HashMap<String, String> COUNTRY_CODES		= new HashMap<String, String>();
	
	static {
		COUNTRY_CODES.put("0", "EU"); 
		COUNTRY_CODES.put("1", "US"); 
		COUNTRY_CODES.put("2", "DE"); 
		COUNTRY_CODES.put("3", "CN"); 
		COUNTRY_CODES.put("4", "ES"); 
		COUNTRY_CODES.put("5", "FR"); 
		COUNTRY_CODES.put("6", "IT"); 
		COUNTRY_CODES.put("7", "JP"); 
		COUNTRY_CODES.put("8", "NE"); 
		COUNTRY_CODES.put("9", "EN"); 
		COUNTRY_CODES.put("10", "DK"); 
		COUNTRY_CODES.put("11", "FN"); 
		COUNTRY_CODES.put("12", "NW"); 
		COUNTRY_CODES.put("13", "PL"); 
		COUNTRY_CODES.put("14", "PO"); 
		COUNTRY_CODES.put("15", "SW"); 
		COUNTRY_CODES.put("16", "EU/US"); 
		COUNTRY_CODES.put("17", "EU/US/JP"); 
		COUNTRY_CODES.put("18", "US/JP"); 
		COUNTRY_CODES.put("19", "AU"); 
		COUNTRY_CODES.put("20", "KRN"); 
		COUNTRY_CODES.put("21", "BR"); 
		COUNTRY_CODES.put("22", "KRS"); 
		COUNTRY_CODES.put("23", "EU/BR"); 
		COUNTRY_CODES.put("24", "EU/US/BR"); 
		COUNTRY_CODES.put("25", "US/BR"); 
		COUNTRY_CODES.put("26", "RU");
		COUNTRY_CODES.put("27", "GR");
		
		FLAG_NAMES.put("0", "eu"); //$NON-NLS-1$ //$NON-NLS-2$
		FLAG_NAMES.put("1", "us"); //$NON-NLS-1$ //$NON-NLS-2$
		FLAG_NAMES.put("2", "de"); //$NON-NLS-1$ //$NON-NLS-2$
		FLAG_NAMES.put("3", "cn"); //$NON-NLS-1$ //$NON-NLS-2$
		FLAG_NAMES.put("4", "es"); //$NON-NLS-1$ //$NON-NLS-2$
		FLAG_NAMES.put("5", "fr"); //$NON-NLS-1$ //$NON-NLS-2$
		FLAG_NAMES.put("6", "it"); //$NON-NLS-1$ //$NON-NLS-2$
		FLAG_NAMES.put("7", "jp"); //$NON-NLS-1$ //$NON-NLS-2$
		FLAG_NAMES.put("8", "ne"); //$NON-NLS-1$ //$NON-NLS-2$
		FLAG_NAMES.put("19", "au"); //$NON-NLS-1$ //$NON-NLS-2$
		FLAG_NAMES.put("22", "kr"); //$NON-NLS-1$ //$NON-NLS-2$
		FLAG_NAMES.put("26", "ru"); //$NON-NLS-1$ //$NON-NLS-2$
		FLAG_NAMES.put("27", "gr"); //$NON-NLS-1$ //$NON-NLS-2$
		
		reloadConsts(); // Load the constants depending on the locale
	}
	
	public static void reloadConsts () {
		COUNTRY_NAMES.clear();
		COUNTRY_NAMES.put("0", Messages.getString("Consts.5")); //$NON-NLS-1$ //$NON-NLS-2$
		COUNTRY_NAMES.put("1", Messages.getString("Consts.4")); //$NON-NLS-1$ //$NON-NLS-2$
		COUNTRY_NAMES.put("2", Messages.getString("Consts.6")); //$NON-NLS-1$ //$NON-NLS-2$
		COUNTRY_NAMES.put("3", Messages.getString("Consts.8")); //$NON-NLS-1$ //$NON-NLS-2$
		COUNTRY_NAMES.put("4", Messages.getString("Consts.10")); //$NON-NLS-1$ //$NON-NLS-2$
		COUNTRY_NAMES.put("5", Messages.getString("Consts.12")); //$NON-NLS-1$ //$NON-NLS-2$
		COUNTRY_NAMES.put("6", Messages.getString("Consts.14")); //$NON-NLS-1$ //$NON-NLS-2$
		COUNTRY_NAMES.put("7", Messages.getString("Consts.16")); //$NON-NLS-1$ //$NON-NLS-2$
		COUNTRY_NAMES.put("8", Messages.getString("Consts.18")); //$NON-NLS-1$ //$NON-NLS-2$
		COUNTRY_NAMES.put("9", Messages.getString("Consts.20")); //$NON-NLS-1$ //$NON-NLS-2$
		COUNTRY_NAMES.put("10", Messages.getString("Consts.22")); //$NON-NLS-1$ //$NON-NLS-2$
		COUNTRY_NAMES.put("11", Messages.getString("Consts.24")); //$NON-NLS-1$ //$NON-NLS-2$
		COUNTRY_NAMES.put("12", Messages.getString("Consts.26")); //$NON-NLS-1$ //$NON-NLS-2$
		COUNTRY_NAMES.put("13", Messages.getString("Consts.28")); //$NON-NLS-1$ //$NON-NLS-2$
		COUNTRY_NAMES.put("14", Messages.getString("Consts.30")); //$NON-NLS-1$ //$NON-NLS-2$
		COUNTRY_NAMES.put("15", Messages.getString("Consts.32")); //$NON-NLS-1$ //$NON-NLS-2$
		COUNTRY_NAMES.put("16", Messages.getString("Consts.34")); //$NON-NLS-1$ //$NON-NLS-2$
		COUNTRY_NAMES.put("17", Messages.getString("Consts.36")); //$NON-NLS-1$ //$NON-NLS-2$
		COUNTRY_NAMES.put("18", Messages.getString("Consts.38")); //$NON-NLS-1$ //$NON-NLS-2$
		COUNTRY_NAMES.put("19", Messages.getString("Consts.40")); //$NON-NLS-1$ //$NON-NLS-2$
		COUNTRY_NAMES.put("20", Messages.getString("Consts.42")); //$NON-NLS-1$ //$NON-NLS-2$
		COUNTRY_NAMES.put("21", Messages.getString("Consts.44")); //$NON-NLS-1$ //$NON-NLS-2$
		COUNTRY_NAMES.put("22", Messages.getString("Consts.46")); //$NON-NLS-1$ //$NON-NLS-2$
		COUNTRY_NAMES.put("23", Messages.getString("Consts.48")); //$NON-NLS-1$ //$NON-NLS-2$
		COUNTRY_NAMES.put("24", Messages.getString("Consts.50")); //$NON-NLS-1$ //$NON-NLS-2$
		COUNTRY_NAMES.put("25", Messages.getString("Consts.52")); //$NON-NLS-1$ //$NON-NLS-2$
		COUNTRY_NAMES.put("26", Messages.getString("Consts.54"));
		COUNTRY_NAMES.put("27", Messages.getString("Consts.56"));
		
		
		LANG_NAMES.clear();
		LANG_NAMES.add(Messages.getString("Consts.75")); //$NON-NLS-1$
		LANG_NAMES.add(Messages.getString("Consts.76")); //$NON-NLS-1$
		LANG_NAMES.add(Messages.getString("Consts.77")); //$NON-NLS-1$
		LANG_NAMES.add(Messages.getString("Consts.78")); //$NON-NLS-1$
		LANG_NAMES.add(Messages.getString("Consts.79")); //$NON-NLS-1$
		LANG_NAMES.add(Messages.getString("Consts.80")); //$NON-NLS-1$
		LANG_NAMES.add(Messages.getString("Consts.81")); //$NON-NLS-1$
		LANG_NAMES.add(Messages.getString("Consts.82")); //$NON-NLS-1$
		LANG_NAMES.add(Messages.getString("Consts.83")); //$NON-NLS-1$
		LANG_NAMES.add(Messages.getString("Consts.84")); //$NON-NLS-1$
		LANG_NAMES.add(Messages.getString("Consts.85")); //$NON-NLS-1$
		LANG_NAMES.add(Messages.getString("Consts.86")); //$NON-NLS-1$
		LANG_NAMES.add(Messages.getString("Consts.87")); //$NON-NLS-1$
		LANG_NAMES.add(Messages.getString("Consts.88")); //$NON-NLS-1$
		LANG_NAMES.add(Messages.getString("Consts.89")); //$NON-NLS-1$
		LANG_NAMES.add(Messages.getString("Consts.90")); //$NON-NLS-1$
		LANG_NAMES.add(Messages.getString("Consts.91")); //$NON-NLS-1$
		LANG_NAMES.add(Messages.getString("Consts.92"));
		LANG_NAMES.add(Messages.getString("Consts.93"));
	}
	
}

