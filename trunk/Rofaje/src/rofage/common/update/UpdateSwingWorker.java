package rofage.common.update;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import rofage.common.Consts;
import rofage.common.Engine;
import rofage.common.SerializationHelper;
import rofage.common.files.FileToolkit;
import rofage.common.files.ZipToolkit;
import rofage.common.helper.ConfigurationHelper;
import rofage.common.helper.GameDBHelper;
import rofage.common.helper.GameDisplayHelper;
import rofage.common.object.Configuration;
import rofage.common.object.Game;
import rofage.common.parser.DatParser;
import rofage.common.url.URLToolkit;
import rofage.ihm.Messages;

public class UpdateSwingWorker extends DownloadSwingWorker {
	private List<Configuration> listConfToUpdate;
	
	/**
	 * Constructs a SW to update the configuration files contained in listConfToUpdate
	 * @param engine
	 * @param listConfToUpdate : List<Configuration>
	 */
	public UpdateSwingWorker (Engine engine, List<Configuration> listConfToUpdate, 
			JProgressBar jProgressBar, JTextArea jTextArea) {
		super(engine, jProgressBar, jTextArea);
		this.listConfToUpdate = listConfToUpdate;
	}
	
	@Override
	protected Integer doInBackground() throws Exception {
		Iterator<Configuration> iterConfs = listConfToUpdate.iterator();
		while (iterConfs.hasNext()) {
			Configuration conf = iterConfs.next();
			// We update the DAT file
			updateDATFile(conf);
			
			// Now we update the image files (and icons) 
			updateImages(conf);
		}

		engine.getMainWindow().getJTable().updateUI();
		return 0;
	}
	
	/**
	 * updates the images
	 *
	 */
	private void updateImages(Configuration conf) {
		// First of all we check whether the image folder exists
		
		publish (Messages.getString("UpdateSwingWorker.1")); //$NON-NLS-1$
		String imageFolder = Consts.HOME_FOLDER+File.separator+conf.getImageFolder();
		String iconFolder  = imageFolder+File.separator+Consts.ICO_FOLDER;
		
		// We check/create if the recipient folders exist
		FileToolkit.checkAndCreateFolder(imageFolder);
		FileToolkit.checkAndCreateFolder(iconFolder);
		
		// Now for each game in the game collection we check if the image files exist
		// If they don't we download them
		// If they do we check their CRC against the crc in the gameCollection
		// If they do not match we download the file
		Collection<Game> colGames = engine.getGameDB().getGameCollections().get(conf.getConfName()).values();
		Iterator<Game> iterGames = colGames.iterator();

		while (iterGames.hasNext() && !stopAction) {
			setProgress(0);
			Game currentGame = iterGames.next();
			
			checkAndDownloadFile(imageFolder, GameDisplayHelper.constructFileName(currentGame, URLToolkit.TYPE_IMAGE_1), 
					currentGame, URLToolkit.TYPE_IMAGE_1, conf.getImageUrl());
			checkAndDownloadFile(imageFolder, GameDisplayHelper.constructFileName(currentGame, URLToolkit.TYPE_IMAGE_2), 
					currentGame, URLToolkit.TYPE_IMAGE_2, conf.getImageUrl());			
			// We also try to get the icon for this game if possible
			if (conf.getIcoUrl()!=null && !conf.getIcoUrl().isEmpty()) {
				checkAndDownloadFile(iconFolder, GameDisplayHelper.constructFileName(currentGame, URLToolkit.TYPE_ICON), 
						currentGame, URLToolkit.TYPE_ICON, conf.getIcoUrl());
			}
		}
	}
	
	/**
	 * updates the DAT file
	 * @return true if the dat file has been updated
	 */
	private boolean updateDATFile(Configuration conf) {
		// Let's retrieve the version number of the last release
		int latestVersionNb = getLatestVersionNb(conf);
		
		// Is this a newer version than the one we have ?
		if (latestVersionNb>conf.getVersion()) {
			publish (Messages.getString("UpdateSwingWorker.4")+conf.getConfName()+Messages.getString("UpdateSwingWorker.5")+latestVersionNb+")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			publish (Messages.getString("UpdateSwingWorker.7")); //$NON-NLS-1$
			downloadFile(conf.getNewDatUrl(), false, conf.getFileName());
			
			// The file should now be uncompressed
			List<String> extractedFiles = ZipToolkit.uncompressFile(Consts.HOME_FOLDER+File.separator+conf.getFileName(), null);
			if (extractedFiles.size()>1) {
				publish (Messages.getString("UpdateSwingWorker.8")); //$NON-NLS-1$
				throw new RuntimeException();
			}
			publish(Messages.getString("UpdateSwingWorker.9")); //$NON-NLS-1$
			
			// Now we have to load this dat instead of the former one
			// So we have to renew the configuration !
			DatParser datParser = new DatParser(extractedFiles.get(0));
			Configuration newConfig = ConfigurationHelper.createConfFromDatParser(engine, datParser);
			
			// We also have to renew the gameDB for this configuration
			GameDBHelper.createGameCollectionInEngine(engine, newConfig.getConfName(), datParser);
			
			// We have to save the configuration & the gameDB
			// In case the scan worker is not performed afterwards
			// Otherwise, the old values will still be used 
			SerializationHelper.saveGameDB(engine.getGameDB());
			SerializationHelper.saveGlobalConfiguration(engine.getGlobalConf());
			return true;
		} else {
			publish (Messages.getString("UpdateSwingWorker.10")+conf.getConfName()+Messages.getString("UpdateSwingWorker.11")+conf.getVersion()+")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			return false;
		}		
	}
	
	/**
	 * Retrieves the version number of the latest dat file from the internet
	 * @param currentDat
	 * @return
	 */
	private Integer getLatestVersionNb (Configuration conf) {
		publish (Messages.getString("UpdateSwingWorker.17")+conf.getConfName()); //$NON-NLS-1$
		try {
			URL url = new URL (conf.getNewVersionUrl());
			URLConnection urlConnection = url.openConnection();
			urlConnection.setUseCaches(false);
			urlConnection.connect();
			
			InputStreamReader is = new InputStreamReader(urlConnection.getInputStream());
			// Reading page contents
			char [] cbuff = new char [4];
			is.read(cbuff);
			// Conversion into string
			StringBuffer strBuff = new StringBuffer();
			for (int i=0; i<cbuff.length; i++) {
				strBuff.append(cbuff[i]);
			}
						
			return Integer.parseInt(strBuff.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
