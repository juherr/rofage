package rofage.common.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents the global configuration of the app
 * Main entry point to the dat configurations
 * @author pierrot
 *
 */
public class GlobalConfiguration implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1483214775475585969L;
	/**
	 * mapDatConfigs is a map of (datName, Configuration)
	 * @see Configuration
	 */
	private HashMap<String, Configuration> mapDatConfigs = null;
	private Configuration selectedConf = null;
	
	public final static List<String> allowedCompressedExtensions = new ArrayList<String>();
	public final static String GLOBAL_CONFIG_FILE_NAME = "globalConf";
	
	static {
		allowedCompressedExtensions.add(".zip");
	}
	
	public GlobalConfiguration () {
		mapDatConfigs = new HashMap<String, Configuration>();
	}

	public HashMap<String, Configuration> getMapDatConfigs() {
		return mapDatConfigs;
	}

	public Configuration getSelectedConf() {
		return selectedConf;
	}

	public void setSelectedConf(Configuration selectedConf) {
		this.selectedConf = selectedConf;
	}

}
