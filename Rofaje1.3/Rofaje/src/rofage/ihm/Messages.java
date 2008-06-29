package rofage.ihm;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static final String BUNDLE_NAME = "rofage.ihm.messages"; //$NON-NLS-1$

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return ResourceBundle.getBundle(BUNDLE_NAME).getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
