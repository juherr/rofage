package rofage.common.clean;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.zip.ZipFile;

import javax.swing.SwingWorker;

import rofage.common.Consts;
import rofage.common.Engine;
import rofage.common.SerializationHelper;
import rofage.common.files.FileToolkit;
import rofage.common.files.ZipToolkit;
import rofage.common.object.Configuration;
import rofage.common.object.Game;

public class CleanSwingWorker extends SwingWorker<Integer, String> {
	private boolean stopAction;
	private Engine engine;
	private Configuration selConf;
	private TreeMap<Integer, Game> gameCollection; // <releaseNb, game>
	private List<Game> listArchivesToCleanUp;
	
	public CleanSwingWorker (Engine engine) {
		this.engine = engine;
		this.stopAction = false;
		this.selConf = engine.getGlobalConf().getSelectedConf();
		this.gameCollection = engine.getGameDB().getGameCollections().get(selConf.getConfName());
		
		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if("progress".equals(evt.getPropertyName())) {
					getEngine().getCleanWindow().getJProgressBar().setValue((Integer) evt.getNewValue());
				}
            }
		});
	}
	
	@Override
	protected Integer doInBackground() throws Exception {
		// Now we scan folders
		setProgress(0);
		publish("Préparation du nettoyage...");
		
		generateCleanList();
		
		publish (listArchivesToCleanUp.size()+" archives à nettoyer.");
		
		cleanUpArchivesInList();
		
		setProgress(100);
		publish ("Nettoyage Terminé !");
		
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
        	engine.getCleanWindow().getJTextArea().append(s + '\n');
    }
	
	/**
	 * Generates the list of archives to be cleaned up
	 *
	 */
	private void generateCleanList () {
		listArchivesToCleanUp = new ArrayList<Game>();
		Iterator<Game> iterGames = gameCollection.values().iterator();
		while (iterGames.hasNext()) {
			Game game = iterGames.next();
			if (game.isGotRom() 
					&& game.getContainerPath().endsWith(".zip")) {
				// We look into each archive to see if there are more than one file
				try {
					ZipFile zipFile = new ZipFile(game.getContainerPath());
					if (zipFile.size()>1) {
						listArchivesToCleanUp.add(game);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Cleans up the archive in the list
	 * It removes any other files than the rom file
	 *
	 */
	private void cleanUpArchivesInList () {
		int nbFilesRenamed = 0;
		int totalFiles = listArchivesToCleanUp.size();
		
		Iterator<Game> iterGames = listArchivesToCleanUp.iterator();
		while (iterGames.hasNext() && !stopAction) {
			Game game = iterGames.next();
			File gameFile = new File (game.getContainerPath());
			
			// We check whether the file still exists !
			if (!gameFile.exists()) {
				publish ("Le fichier "+gameFile.getAbsolutePath()+" n'existe plus !");
			} else {
				String destPath;
				
				// We unpack the full archive
				List<String> zipEntriesPaths = ZipToolkit.uncompressFile(gameFile.getAbsolutePath(), Consts.TMP_FOLDER);
				Iterator<String> iterPath = zipEntriesPaths.iterator();
				while (iterPath.hasNext()) {
					String path = iterPath.next();
					if (!selConf.getAllowedExtensions().contains(FileToolkit.getFileExtension(path))) {
						iterPath.remove();
					}
				}
				
				// We pack back the rom
				ZipToolkit.compress(zipEntriesPaths, Consts.TMP_FOLDER+File.separator+gameFile.getName()+".zip");
				File newArchive = new File (Consts.TMP_FOLDER+File.separator+gameFile.getName()+".zip");
				
				// We cleanup the tmp folder
				iterPath = zipEntriesPaths.iterator();
				while (iterPath.hasNext()) {
					String path = iterPath.next();
					(new File(path)).delete();
				}
				
				// We remove the old archive
				gameFile.delete();
				
				destPath = gameFile.getAbsolutePath();
				// We move the new archive to the rom folder
				FileToolkit.moveFile(newArchive, destPath);
				
				publish (destPath+" nettoyé");
				
				nbFilesRenamed++;
				setProgress(nbFilesRenamed*100/totalFiles);
			}
		}
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
