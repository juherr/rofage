package rofage.toolkits;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import rofage.tasks.TaskCheckJavaVersion;

/**
 * This class is a helper to read the properties files
 * @author Pierre Chastagner
 */
public abstract class PropertiesToolkit {
    private final static Log logger = LogFactory.getLog(TaskCheckJavaVersion.class);
   	private final static ResourceBundle bundle = ResourceBundle.getBundle("RoFage");

    public static String getProperty(String key) {
        try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
    }
}
