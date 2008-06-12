package rofage.common.scan;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.zip.ZipEntry;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import rofage.common.Engine;
import rofage.common.SerializationHelper;
import rofage.common.files.FileToolkit;
import rofage.common.files.ZipToolkit;
import rofage.common.helper.GameDisplayHelper;
import rofage.common.object.Configuration;
import rofage.common.object.Game;
import rofage.common.object.GlobalConfiguration;

public class ScanSwingWorker extends SwingWorker<Integer, String> {
	private boolean stopScan;
	private Engine engine;
	private Configuration selConf;
	private HashMap<String, String> cRCRomDB; // <CRC from the DAT file, releaseNb>
	private TreeMap<Integer, Game> gameCollection; // <releaseNb, game>
	
	public ScanSwingWorker (Engine engine) {
		this.engine = engine;
		this.stopScan = false;
		this.selConf = engine.getGlobalConf().getSelectedConf();
		this.gameCollection = engine.getGameDB().getGameCollections().get(selConf.getConfName());
		
		// We generate the cRCRomDB for this conf
		generateCRCDB();
		
		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if("progress".equals(evt.getPropertyName())) {
					getEngine().getScanWindow().getJProgressBar().setValue((Integer) evt.getNewValue());
				}
            }
		});
	}
	
	@Override
	protected Integer doInBackground() throws Exception {
		// Now we scan folders
		setProgress(0);
		publish("Scan des dossiers... Cela peut prendre du temps !");
		scanFolders();
		setProgress(100);
		publish ("Scan Terminé !");
		
		// We save the results
		SerializationHelper.saveGameDB(engine.getGameDB());
		
		// We update the ui
		engine.getMainWindow().getJTable().updateUI();
		
		// The stop button may habe been disabled by an other process,
		// We enable it again
		engine.getScanWindow().getButtonStop().setEnabled(true);
		return 0;
	}
	
	@Override
    protected void process(List<String> strings) {
        /* Affichage des publications reçues dans le textarea. */
        for(String s : strings)
        	engine.getScanWindow().getJTextArea().append(s + '\n');
    }
	
	/**
	 * Builds the CRC list <String:CRC, Integer releaseNb>
	 *
	 */
	private void generateCRCDB() {
		cRCRomDB = new HashMap<String, String>();
		Iterator <Game> iterGame = engine.getGameDB().getGameCollections().get(selConf.getConfName()).values().iterator(); 
		while (iterGame.hasNext()) {
			Game game = iterGame.next();
			cRCRomDB.put(game.getCrc(), game.getReleaseNb());
		}
	}
	
	/**
	 * Moves the rom to the moveRomFolder if needed
	 * This method is safe to be called withouth knowning the configuration
	 * @param filePath
	 */
	private void moveRom (File file) {
		// If it's not a rom we may move this file
		if (selConf.isMoveUnknownRoms()) {
			String moveRomFolder = selConf.getRomFolderMove();
			if (moveRomFolder!=null && !selConf.getRomFolderMove().trim().isEmpty()) {
				FileToolkit.moveFile(file, moveRomFolder+File.separator+file.getName());
			}
		}
	}
	
	/**
	 * 
	 * @param file
	 * @param releaseNb
	 */
	private void updateGameDatasFromScan (File file, int releaseNb) {
		// First we get the game
		Game currGame = gameCollection.get(releaseNb);
		currGame.setGotRom(true);
		currGame.setContainerPath(file.getAbsolutePath());
		
		// We also check the name
		String fileName = FileToolkit.removeFileExtension(file.getName());
		String goodRomName = GameDisplayHelper.buildTitle(currGame, selConf.getTitlePattern());
		if (fileName.equals(goodRomName)) {
			currGame.setGoodName(true);
		} else {
			currGame.setGoodName(false);
		}
	}
	
	/**
	 * Scans a zipEntry and update its data if necessary
	 * @param file
	 */
	private void scanZipEntry (ZipEntry zipEntry, String containerName, String containerPath) {
		String extension = FileToolkit.getFileExtension(zipEntry.getName());
		if (selConf.getAllowedExtensions().contains(extension.toLowerCase()) 
				|| selConf.getAllowedExtensions().contains(extension.toUpperCase())) {

			String zipEntryCRC = Long.toHexString(zipEntry.getCrc());
			String releaseNb = cRCRomDB.get(zipEntryCRC.toUpperCase());
			if (releaseNb==null) releaseNb = cRCRomDB.get(zipEntryCRC.toLowerCase());
			if (releaseNb!=null) {
				// It's a game, we have to update its data
				// First we get the game
				Game currGame = gameCollection.get(Integer.parseInt(releaseNb));
				currGame.setGotRom(true);
				currGame.setContainerPath(containerPath);
				
				// We also check the name
				String gameTitle = FileToolkit.removeFileExtension(containerName);
				String goodRomName = GameDisplayHelper.buildTitle(currGame, selConf.getTitlePattern());
				if (gameTitle.equals(goodRomName)) {
					currGame.setGoodName(true);
				} else {
					// The name is bad an we haven't renamed the file
					currGame.setGoodName(false);
				}
			} else {
				// This is not a rom, we move the file
				moveRom(new File(containerPath));
			}
		}
	}
	
	/**
	 * Scans a file and update its data if necessary
	 * @param file
	 * @param moveFile indicates whether we TRY to move the file
	 * @param fileCRC crc of the file to scan. This is passed to speed up the scan, no mutliple calculation of the CRC
	 * @return Integer which indicates whether a game has been found (releaseNb) null otherwise
	 */
	private Integer scanFile (File file, String fileCRC) {
		// The global crc value must 
		Integer relNb = null;
		String extension = FileToolkit.getFileExtension(file.getName());
		if (selConf.getAllowedExtensions().contains(extension.toLowerCase()) 
				|| selConf.getAllowedExtensions().contains(extension.toUpperCase())) {
			
			String releaseNb = cRCRomDB.get(fileCRC);
			if (releaseNb==null) releaseNb = cRCRomDB.get(fileCRC);
			if (releaseNb!=null) {
				// It's a game, we have to update its data
				updateGameDatasFromScan(file, Integer.parseInt(releaseNb));
				relNb = Integer.parseInt(releaseNb);
			} else {
				// it's not a game we may habe to move the file
				moveRom(file);
			}
		}
		return relNb;
	}
	
	private void scanCompressedFile (File zipFile) {
		// First let's see if the zip file contains a file which could be a rom
		boolean isGood = false;
		Iterator<String> iterRomExtensions = selConf.getAllowedExtensions().iterator();
		while (iterRomExtensions.hasNext() && !isGood) {
			String extension = iterRomExtensions.next();
			if (ZipToolkit.containsType(zipFile.getAbsolutePath(), extension)) {
				// We can uncompress the file to scan its content
				isGood = true;
			}
		}
		
		if (isGood) {
			// We have to check each entry of the compressed file to check their crc
			List<ZipEntry> entriesCRC = ZipToolkit.getEntries(zipFile.getAbsolutePath());
			Iterator<ZipEntry> iterZipEntries = entriesCRC.iterator();
			while (iterZipEntries.hasNext()) {
				ZipEntry zipEntry = iterZipEntries.next();
				scanZipEntry(zipEntry, zipFile.getName(), zipFile.getAbsolutePath());
			}
		} else {
			// This cannot be a rom file we move it if necessary
			moveRom(zipFile);
		}
	}
	
	private int scanFolderForRoms (int nbFiles, int NbFilesScanned, File topDirectory) {
//		 We have a directory we can scan
		List<File> listFiles = new ArrayList<File> (Arrays.asList(topDirectory.listFiles()));;
		Iterator<File> iterFiles = listFiles.iterator();
		
		List<File> subDirectories = new ArrayList<File>();
		while (iterFiles.hasNext() && !stopScan) {
			File curFile = iterFiles.next();
			
			// If it's a folder we save it for deeper scanning
			if (curFile.isDirectory()) {
				subDirectories.add(curFile);
			} else {
				publish ("Scan de "+curFile.getName());
				String extension = FileToolkit.getFileExtension(curFile.getName());
				if (selConf.getAllowedExtensions().contains(extension.toLowerCase()) 
						|| selConf.getAllowedExtensions().contains(extension.toUpperCase())) {
					String fileCRC = FileToolkit.getCRC32(curFile.getAbsolutePath());
					scanFile(curFile, fileCRC);
				} else {
					// We check if it's a supported compressed file
					if (GlobalConfiguration.allowedCompressedExtensions.contains(extension.toLowerCase()) ||
							GlobalConfiguration.allowedCompressedExtensions.contains(extension.toUpperCase())) {
						// This is a supported compressed file, we have to scan it
						scanCompressedFile(curFile);
					}
				}
				
				// We do not touch any other file, we do not clean the rom directory !
				NbFilesScanned++;
				setProgress(NbFilesScanned*100/nbFiles);
			}
		}
		Iterator<File> iterSubDir = subDirectories.iterator();
		while (iterSubDir.hasNext()) {
			File subDir = iterSubDir.next();
			NbFilesScanned += scanFolderForRoms(nbFiles, NbFilesScanned, subDir);
		}
		
		return NbFilesScanned;
	}
	
	private void scanFolders () {
		// We scan folders for .nds and .zip files
		File topDirectory = new File(selConf.getRomFolder());
		
		if (!topDirectory.isDirectory()) {
			JOptionPane.showMessageDialog(engine.getScanWindow(), "Le répertoire n'est pas valide!", "Erreur", JOptionPane.ERROR_MESSAGE);
			publish ("Abandon... Répertoire invalide!");
		} else {
			// We count the total number of files
			int nbFiles = FileToolkit.getFileNb(topDirectory);
			publish("Préparation du scan... "+nbFiles+" fichiers à scanner");
			// We have to put all value to false (gotRom, goodName)
			// They will be reset during the scan
			Iterator<Game> iterGames = gameCollection.values().iterator();
			while (iterGames.hasNext()) {
				Game game = iterGames.next();
				game.setGoodName(false);
				game.setGotRom(false);
			}
			scanFolderForRoms(nbFiles, 0, topDirectory);
		}	
	}

	public boolean isStopScan() {
		return stopScan;
	}

	public void setStopScan(boolean stopScan) {
		this.stopScan = stopScan;
	}

	public Engine getEngine() {
		return engine;
	}

}
