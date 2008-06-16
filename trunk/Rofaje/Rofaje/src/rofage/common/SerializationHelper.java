package rofage.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import rofage.common.object.GameDB;
import rofage.common.object.GlobalConfiguration;
import rofage.ihm.Messages;

public abstract class SerializationHelper {
	
	public static void saveGlobalConfiguration (GlobalConfiguration conf) {
		save (conf, GlobalConfiguration.GLOBAL_CONFIG_FILE_NAME);
	}
	
	/**
	 * Try to load the globalConfiguration object
	 * If it doesn't exist, null is returned
	 * @return {@link GlobalConfiguration}
	 */
	public static GlobalConfiguration loadGlobalConfiguration () {
		return (GlobalConfiguration) load(GlobalConfiguration.GLOBAL_CONFIG_FILE_NAME); 
	}
	
	public static void saveGameDB (GameDB gameDB) {
		save (gameDB, GameDB.GAMEDB_FILE_NAME);
	}
	
	/**
	 * Try to load the gameDatabase object
	 * If it doesn't exist, null is return
	 * @return {@link GameDB}
	 */
	public static GameDB loadGameDB () {
		return (GameDB) load(GameDB.GAMEDB_FILE_NAME);
	}
	
	/**
	 * Save an object on in the home folder
	 * @param object to save
	 * @param fileName name of the saved file
	 */
	private static void save (Object object, String fileName) {
		try {
			FileOutputStream fichier = new FileOutputStream(Consts.HOME_FOLDER+File.separator+fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fichier);
			oos.writeObject(object);
			oos.flush();
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * load an object from the home folder
	 * @param fileName name of the file to load (located in the Home Folder)
	 * @return the object
	 */
	private static Object load (String fileName) {
		Object object = null;
		try {
			FileInputStream fichier = new FileInputStream(Consts.HOME_FOLDER+File.separator+fileName);
			ObjectInputStream ois = new ObjectInputStream(fichier);
			object = ois.readObject();
		} catch (FileNotFoundException e) {
			System.out.println(Messages.getString("SerializationHelper.0")+fileName+Messages.getString("SerializationHelper.1")); //$NON-NLS-1$ //$NON-NLS-2$
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return object;    
	}
}
