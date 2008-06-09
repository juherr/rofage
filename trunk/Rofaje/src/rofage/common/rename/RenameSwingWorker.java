package rofage.common.rename;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.swing.SwingWorker;

import rofage.common.Engine;
import rofage.common.SerializationHelper;
import rofage.common.files.FileToolkit;
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
				publish ("Le fichier "+gameFile.getAbsolutePath()+" n'existe pas !");
			} else {
				// Let's rename it
				String oldFileName = gameFile.getName();
				String newFileName = GameDisplayHelper.buildTitle(game, selConf.getTitlePattern())+FileToolkit.getFileExtension(gameFile.getName());
				String destPath = gameFile.getParent()+File.separator+newFileName;
				FileToolkit.moveFile(gameFile, destPath);
				game.setGoodName(true);
				publish (oldFileName+" => "+ newFileName);
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
