package rofage.common.scan;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import rofage.common.Engine;
import rofage.common.SerializationHelper;
import rofage.common.files.FileToolkit;
import rofage.common.helper.GameDisplayHelper;
import rofage.common.object.Configuration;
import rofage.common.object.Game;
import rofage.common.swingworker.StoppableSwingWorker;
import rofage.ihm.Messages;
import rofage.ihm.helper.StatusBarHelper;
import de.schlichtherle.io.File;
import de.schlichtherle.io.FileReader;
import de.schlichtherle.util.zip.ZipEntry;
import de.schlichtherle.util.zip.ZipFile;

public class ScanSwingWorker extends StoppableSwingWorker<Integer, String> {

	private Configuration selConf;
	private HashMap<String, String> cRCRomDB; // <CRC from the DAT file, releaseNb>
	private HashMap<String, String> serialRomDB; // <internalName, releaseNb>
	private TreeMap<Integer, Game> gameCollection; // <releaseNb, game>
	
	public ScanSwingWorker (Engine engine) {
		this.selConf = engine.getGlobalConf().getSelectedConf();
		this.gameCollection = engine.getGameDB().getGameCollections().get(selConf.getConfName());
		this.engine = engine;
		
		// We generate the cRCRomDB for this conf
		generateCRCDB();
		
		// We generate the serialRomDB for this conf
		generateSerialRomDB();
		
		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if("progress".equals(evt.getPropertyName())) { //$NON-NLS-1$
					getEngine().getScanWindow().getJProgressBar().setValue((Integer) evt.getNewValue());
				}
            }
		});
	}
	
	@Override
	protected Integer doInBackground() throws Exception {
		// Now we scan folders
		setProgress(0);
		publish(Messages.getString("ScanSwingWorker.1")); //$NON-NLS-1$
		scanFolders();
		setProgress(100);
		publish (Messages.getString("ScanSwingWorker.2")); //$NON-NLS-1$
		
		// We save the results
		SerializationHelper.saveGameDB(engine.getGameDB());
		
		// We update the ui
		engine.getMainWindow().getJTable().updateUI();
		
		// We also update the status bar
		StatusBarHelper.updateStatusBar(gameCollection, engine);
		
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
	 * Builds the internal name list <String:internalName, Integer releaseNb>
	 *
	 */
	private void generateSerialRomDB() {
		serialRomDB = new HashMap<String, String>();
		Iterator <Game> iterGame = engine.getGameDB().getGameCollections().get(selConf.getConfName()).values().iterator(); 
		while (iterGame.hasNext()) {
			Game game = iterGame.next();
			String serial = game.getSerial();
			if (serial!=null) {
				int index1 = serial.indexOf('-');
				int index2 = serial.lastIndexOf('-');
				if (index1==-1 || index2==-1) {
					System.out.println("There is an error in the DAT file for the game "+game.getTitle()+"-(crc:"+game.getCrc()+")\nThe serial :'"+game.getSerial()+"' is not valid!");
				} else {
					serial = serial.substring(serial.indexOf('-')+1, serial.lastIndexOf('-'));
					serialRomDB.put(serial, game.getReleaseNb());
				}
			}
		}
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
	 * Update the game object
	 * @param file
	 * @param releaseNb
	 * @param scannedFromSerial : the release nb has been found with the serial instead of the crc
	 * @param entryFullPath : fullPath to the entry. It uses the TrueZip format
	 */
	private void updateGameDatasFromScan (File file, int releaseNb, boolean scannedFromSerial, String entryFullPath) {
		// First we get the game
		Game currGame = gameCollection.get(releaseNb);
		currGame.setGotRom(true);
		if (file.isEntry()) {
			currGame.setContainerPath(file.getTopLevelArchive().getAbsolutePath());
		} else {
			currGame.setContainerPath(file.getAbsolutePath());
		}
		currGame.setScannedFromSerial(scannedFromSerial);
		
		// We also check the name
		String fileName = FileToolkit.removeFileExtension(file.getName());
		String goodRomName = GameDisplayHelper.buildTitle(currGame, selConf.getTitlePattern());
		if (fileName.equals(goodRomName)) {
			currGame.setGoodName(true);
		} else {
			currGame.setGoodName(false);
		}
		currGame.setEntryFullPath(entryFullPath);
	}
	
	private String detectFileWithInternalName (File file) {
		try {
			BufferedReader buff = new BufferedReader(new FileReader(file));
			try  {
				char[] cbuff = new char[16];
				buff.read(cbuff);
				// The serial is the last 4 digits
				String serial = new String(cbuff);
				serial = serial.substring(12);
				if (serialRomDB.containsKey(serial)) {
					return serialRomDB.get(serial);
				}
			} finally {
				buff.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * Scans a file and update its data if necessary
	 * @param file
	 * @param moveFile indicates whether we TRY to move the file
	 * @return Integer which indicates whether a game has been found (releaseNb) null otherwise
	 */
	private Integer scanFile (File file) {
		String fileCRC = "";
		String entryFullPath = null;
		// The file given here can be inside an archive !
		if (file.isEntry()) {
			// It is inside an archive, we could use the already calculate CRC 
			// This speeds up the scan a LOT !
			ZipFile zipFile = null;
			try {
				try {
					// We convert this file to a ZipEntry
					zipFile = new ZipFile(file.getTopLevelArchive().getAbsolutePath());
					ZipEntry zipEntry = zipFile.getEntry(file.getName());
					fileCRC = FileToolkit.convertLongCRCToStringCRC(zipEntry.getCrc());
					// We save the zipEntryName for adding it in the game object
					// So if we have multiple roms in a single archive, we can know which one it is
					entryFullPath = file.getAbsolutePath();
				} finally {
					if (zipFile!=null) {
						zipFile.close();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			// This is a regular file, we have to calculate the CRC
			fileCRC = FileToolkit.getCRC32(file.getAbsolutePath());
		}

		Integer relNb = null;
		boolean scannedFromSerial = false;
		
		String releaseNb = cRCRomDB.get(fileCRC.toLowerCase());
		if (releaseNb==null) releaseNb = cRCRomDB.get(fileCRC.toUpperCase());
		if (releaseNb==null) {
			// If the CRC cannot be found in the DB it may be a trimmed or patched rom
			// We try the alternate method
			if (selConf.isInternalName()) {
				// We try to detect the rom with its internal name
				releaseNb = detectFileWithInternalName(file);
				if (releaseNb!=null) scannedFromSerial = true;
			}
		}
			
		if (releaseNb!=null) {
			// It's a game, we have to update its data
			updateGameDatasFromScan(file, Integer.parseInt(releaseNb), scannedFromSerial, entryFullPath);
			relNb = Integer.parseInt(releaseNb);
		} else {
			// It's not a game we may have to move the file
			moveRom(file);
		}
		return relNb;
	}
	
	private int scanFolderForRoms (int nbFiles, int NbFilesScanned, File topDirectory) {
		// We have a directory we can scan
		String[] tabFilenames = topDirectory.list(FileToolkit.getFileNameFilter(FileToolkit.FILTER_ROMS_ARCHIVES, selConf));
		
		// We convert the array to a list
		List<String> listFilenames = new ArrayList<String>();
		for (int i=0; i<tabFilenames.length; i++) {
			listFilenames.add(tabFilenames[i]);
		}
				
		Iterator<String> iterFilenames = listFilenames.iterator();
		
		List<String> subDirectories = new ArrayList<String>();
		while (iterFilenames.hasNext() && !stopAction) {
			String curFilename = iterFilenames.next();
			
			File curFile = new File(topDirectory.getAbsolutePath()+File.separator+curFilename);
			// If it's a folder we save it for deeper scanning
			if (curFile.isDirectory()) {
				subDirectories.add(curFile.getAbsolutePath());
			} else {
				if (curFile.isEntry()) {
					publish (Messages.getString("ScanSwingWorker.3")+curFile.getName()+" "+Messages.getString("In")+" "+curFile.getTopLevelArchive().getName()); //$NON-NLS-1$
				} else {
					publish (Messages.getString("ScanSwingWorker.3")+curFile.getName()); //$NON-NLS-1$
				}
				scanFile(curFile);
				NbFilesScanned++;
				setProgress(NbFilesScanned*100/nbFiles);
			}
		}
		Iterator<String> iterSubDirNames = subDirectories.iterator();
		while (iterSubDirNames.hasNext()) {
			File subDir = new File(iterSubDirNames.next());
			NbFilesScanned = scanFolderForRoms(nbFiles, NbFilesScanned, subDir);
		}
		
		return NbFilesScanned;
	}
	
	private void scanFolders () {
		// We scan folders for .nds and .zip files
		File topDirectory = new File(selConf.getRomFolder());
		
		if (!topDirectory.isDirectory()) {
			JOptionPane.showMessageDialog(engine.getScanWindow(), Messages.getString("ScanSwingWorker.4"), Messages.getString("ScanSwingWorker.5"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
			publish (Messages.getString("ScanSwingWorker.6")); //$NON-NLS-1$
		} else {
			// We count the total number of files
			int nbFiles = FileToolkit.getFileNb(topDirectory, FileToolkit.getFileNameFilter(FileToolkit.FILTER_ROMS_ARCHIVES, selConf));
			
			publish(Messages.getString("ScanSwingWorker.7")+nbFiles+Messages.getString("ScanSwingWorker.8")); //$NON-NLS-1$ //$NON-NLS-2$
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

}
