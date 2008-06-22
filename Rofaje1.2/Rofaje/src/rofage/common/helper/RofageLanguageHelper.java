package rofage.common.helper;

import java.util.TreeSet;

import rofage.ihm.Messages;

public class RofageLanguageHelper {
	public final static String LANG_FR	= "french.properties"; //$NON-NLS-1$
	
	public final static TreeSet<String> availableLanguages = new TreeSet<String>();
	
	static {
		availableLanguages.add(Messages.getString("RofageLanguageHelper.1")); //$NON-NLS-1$
	}
}
