package rofage.common.rename;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.swing.SwingWorker;

import rofage.common.Consts;
import rofage.common.Engine;
import rofage.common.SerializationHelper;
import rofage.common.files.FileToolkit;
import rofage.common.files.ZipToolkit;
import rofage.common.helper.GameDisplayHelper;
import rofage.common.object.Configuration;
import rofage.common.object.Game;

public class RenameSwingWorker extends SwingWorker<Integer, String> {
	private boolean stopAction;
	private Engine engine;
	private Configuration selConf;
	private TreeMap<Integer, Game> gameCollection; // <releaseNb, game>
	private List<Game> listGamesToRename;
	
	public RenameSwingWorker (Engine engine) {
		this.engine = engine;
		this.stopAction = false;
		this.selConf = engine.getGlobalConf().getSelectedConf();
		this.gameCollection = engine.getGameDB().getGameCollections().get(selConf.getConfName());
		
		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if("progress".equals(evt.getPropertyName())) {
					getEngine().getRenameWindow().getJProgressBar().setValue((Integer) evt.getNewValue());
				}
            }
		});
	}
	
	@Override
	protected Integer doInBackground() throws Exception {
		// Now we scan folders
		setProgress(0);
		publish("Préparation du renommage...");
		
		generateRenameList();
		
		publish (listGamesToRename.size()+" roms à renommer.");
		
		renameGamesInList();
		
		setProgress(100);
		publish ("Renommage Terminé !");
		
		// We update the UI
		engine.getMainWindow().getJTable().updateUI();
		
		// We save the results
		SerializationHelper.saveGameDB(engine.getGameDB());
		
		return 0;
	}
	
	@Override
    protected void process(List<String> strings) {
        /* Affichage des publications reçues dans le textarea. */
        for(String s : strings)
        	engine.getRenameWindow().getJTextArea().append(s + '\n');
    }
	
	/**
	 * Generates the list of game to rename
	 *
	 */
	private void generateRenameList () {
		listGamesToRename = new ArrayList<Game>();
		Iterator<Game> iterGames = gameCollection.values().iterator();
		while (iterGames.hasNext()) {
			Game game = iterGames.next();
			if (!game.isGoodName() && game.isGotRom()) {
				listGamesToRename.add(game);
			}
		}
	}
	
	/**
	 * Renames the game in the list listGamesToRename
	 * The name used will be the one set in the configuration of this DAT
	 * Once the game renamed, its goodName flag is set to true
	 *
	 */
	private void renameGamesInList () {
		int nbFilesRenamed = 0;
		int totalFiles = listGamesToRename.size();
		
		Iterator<Game> iterGames = listGamesToRename.iterator();
		while (iterGames.hasNext() && !stopAction) {
			Game game = iterGames.next();
			File gameFile = new File (game.getContainerPath());
			
			// We check whether the file still exists !
			if (!gameFile.exists()) {
				publish ("Le fichier "+gameFile.getAbsolutePath()+" n'existe plus !");
			} else {
				// Let's rename it
				String oldFileName = gameFile.getName();
				String newFileName = GameDisplayHelper.buildTitle(game, selConf.getTitlePattern());
				String destPath = gameFile.getParent()+File.separator+newFileName+FileToolkit.getFileExtension(oldFileName);
				// We have two choices, the file can either be an archive or the rom itself
				// If it's the rom, we just have to rename the file
				// Otherwise, we have to unpack the archive, 
				// find the rom file,
				// rename it
				// repack the rom with the other files of the archive
				// delete the extracted files
				// Rename the parcked file
				// And, finally, delete the old file
				
				// We also have to see whether the user wishs to rename inside the rom
				if (selConf.getAllowedExtensions().contains(FileToolkit.getFileExtension(oldFileName)) 
						|| !selConf.isRenameInside()) {
					// This is a rom file, we just rename it
					FileToolkit.moveFile(gameFile, destPath);
				} else {
					// This is a compressed file, it's a bit more complicated... and longer !
					File newArchive = renameInsideCompressedFile(gameFile, newFileName);
					// We move back the archive
					FileToolkit.moveFile(newArchive, destPath);
					// We delete the old archive
					gameFile.delete();
				}
				
				game.setContainerPath(destPath);
				game.setGoodName(true);
				publish (oldFileName+" => "+ newFileName);
				nbFilesRenamed++;
				setProgress(nbFilesRenamed*100/totalFiles);
			}
		}
	}
	
	 /**
	  * renaming routine for a compress file
	  * If it's the rom, we just have to rename the file
	  * Otherwise, we have to unpack the archive,
	  * find the rom file,
	  * rename it
	  * repack the rom with the other files of the archive
	  * delete the extracted files
	  * Rename the packed file
	  * And, finally, delete the old file
	  * @param gameFile File pointing to the archive containing the rom
	  * @param newFileName name we have to rename the file / rom to
	  * @return File pointing to the newly created archive
	  */
	private File renameInsideCompressedFile (File gameFile, String newFileName) {
		// We unpack the full archive
		List<String> zipEntriesPaths = ZipToolkit.uncompressFile(gameFile.getAbsolutePath(), Consts.TMP_FOLDER);
		
		// We look for the rom
		Iterator<String> iterZipEntriesPaths = zipEntriesPaths.iterator();
		boolean gameFound = false;

		String fileExt = "";
		while (iterZipEntriesPaths.hasNext() && !gameFound) {
			String path = iterZipEntriesPaths.next();
			fileExt = FileToolkit.getFileExtension(path);
			if (selConf.getAllowedExtensions().contains(fileExt)) {
				// We found the rom
				gameFound = true;
				// We remove this entry from the zipEntry list
				iterZipEntriesPaths.remove();
				// We rename this entry
				FileToolkit.moveFile(new File(path), Consts.TMP_FOLDER+File.separator+newFileName+fileExt);
			}
		}
		
		// Now we have to recompress the files
		// We add the renamed file (the file which has been renamed has been deleted from this list)
		zipEntriesPaths.add(Consts.TMP_FOLDER+File.separator+newFileName+fileExt);
		
		ZipToolkit.compress(zipEntriesPaths, Consts.TMP_FOLDER+File.separator+newFileName+".zip");
		
		// We cleanup the tmp folder
		iterZipEntriesPaths = zipEntriesPaths.iterator();
		while (iterZipEntriesPaths.hasNext()) {
			String path = iterZipEntriesPaths.next();
			(new File(path)).delete();
		}
		File archive = new File(Consts.TMP_FOLDER+File.separator+newFileName+".zip");
		return archive;
	}
	
	public boolean isStopAction() {
		return stopAction;
	}

	public void setStopAction(boolean stopAction) {
		this.stopAction = stopAction;
	}

	public Engine getEngine() {
		return engine;
	}

}
