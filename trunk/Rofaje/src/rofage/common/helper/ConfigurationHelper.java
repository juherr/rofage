package rofage.common.helper;

import rofage.common.Engine;
import rofage.common.object.Configuration;
import rofage.common.parser.DatParser;
import rofage.ihm.conf.ConfWindow;

public abstract class ConfigurationHelper {

	/**
	 * Saves the shown configuration into the engine
	 * @param engine
	 */
	public static void saveCurrentConfigurationInEngine (Engine engine) {
		ConfWindow confWindow = engine.getConfWindow();
		Configuration conf = engine.getGlobalConf().getSelectedConf();
		
		// We update the configuration with the form datas
		conf.setRomFolder(confWindow.getFieldRomFolder().getText());
		conf.setRomFolderMove(confWindow.getFieldUnknownRomFolder().getText());
		conf.setMoveUnknownRoms(confWindow.getCBmoveUnknownRoms().isSelected());
		conf.setUpdateAtStartup(confWindow.getCBAutoUpdate().isSelected());
		conf.setRenameInside(confWindow.getCBRenameInside().isSelected());
		if (confWindow.getFieldTitlePattern().getText().trim().isEmpty()) {
			confWindow.getFieldTitlePattern().setText(Configuration.DEFAULT_TITLEPATTERN);
		} else {
			conf.setTitlePattern(confWindow.getFieldTitlePattern().getText().trim());
		}
		conf.setInAppUpdate(confWindow.getCBInAppUpdate().isSelected());
				
		// We save this conf into the global conf
		engine.getGlobalConf().getMapDatConfigs().put(conf.getConfName(), conf);
		engine.setConfSaved(true);
	}
	
	/**
	 * Creates the internal option values of the configuration from the dat file
	 *
	 */
	private static void populateConfigurationInternalOptions (Configuration conf, DatParser datParser) {
		conf.setImageFolder(datParser.getImageFolderName());
		conf.setImageUrl(datParser.getImageUrl());
		conf.setNewDatUrl(datParser.getNewDatUrl());
		conf.setNewVersionUrl(datParser.getNewDatVersionUrl());
		conf.setVersion(datParser.getVersion());
		conf.setAllowedExtensions(datParser.getAllowedExtensions());
		conf.setFileName(datParser.getFileName());
		conf.setIcoUrl(datParser.getIcoUrl());
	}
	
	/**
	 * Creates a Configuration object from a already created DatParser
	 * This function also create/update the configuration entry in the 
	 * GlobalConfiguration object
	 * @param engine
	 * @param datParser
	 * @return the newly created Configuration
	 * @see Configuration
	 */
	public static Configuration createConfFromDatParser (Engine engine, DatParser datParser) {
//		 The dat file is not configured yet, we can create its configuration
		Configuration newConfig = new Configuration();
		String datFileName = datParser.getDatName();
		
		newConfig.setConfName(datFileName);
		// We also get additional configuration values from the datfiles
		populateConfigurationInternalOptions(newConfig, datParser);
		
		// We add/replace the conf to the engine global config
		engine.getGlobalConf().getMapDatConfigs().put(datFileName, newConfig);
		return newConfig;
	}
}
