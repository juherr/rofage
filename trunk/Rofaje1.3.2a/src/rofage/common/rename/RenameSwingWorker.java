package rofage.common.rename;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import rofage.common.Engine;
import rofage.common.SerializationHelper;
import rofage.common.files.FileToolkit;
import rofage.common.helper.GameDisplayHelper;
import rofage.common.object.Configuration;
import rofage.common.object.Game;
import rofage.common.swingworker.StoppableSwingWorker;
import rofage.ihm.Messages;
import rofage.ihm.helper.StatusBarHelper;
import de.schlichtherle.io.ArchiveException;
import de.schlichtherle.io.File;

public class RenameSwingWorker extends StoppableSwingWorker<Integer, String> {
	private Configuration selConf;
	private TreeMap<Integer, Game> gameCollection; // <releaseNb, game>
	private List<Game> listGamesToRename;
	private JProgressBar jProgressBar;
	private JTextArea jTextArea;
		
	public RenameSwingWorker (Engine engine, 
			JProgressBar jProgressBar, 
			JTextArea jTextArea, 
			List<Game> listGamesToRename) {
		this.engine = engine;
		this.stopAction = false;
		this.selConf = engine.getGlobalConf().getSelectedConf();
		this.gameCollection = engine.getGameDB().getGameCollections().get(selConf.getConfName());
		this.listGamesToRename = listGamesToRename;
		this.jProgressBar = jProgressBar;
		this.jTextArea = jTextArea;
		
		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if("progress".equals(evt.getPropertyName())) { //$NON-NLS-1$
					getJProgressBar().setValue((Integer) evt.getNewValue());
				}
            }
		});
	}
	
	@Override
	protected Integer doInBackground() throws Exception {
		// Now we scan folders
		engine.getRenameWindow().getJButton().setEnabled(false);
		engine.getRenameWindow().getButtonStop().setEnabled(true);
		setProgress(0);
		publish(Messages.getString("RenameSwingWorker.1")); //$NON-NLS-1$
		
		// If we haven't specified a list of game, we perform a full renaming operation
		if (listGamesToRename==null) {
			generateRenameList();
		}
		
		publish (listGamesToRename.size()+Messages.getString("RenameSwingWorker.2")); //$NON-NLS-1$
		
		renameGamesInList();
		
		setProgress(100);
		
		// We update the UI
		engine.getMainWindow().getJTable().updateUI();
		
		StatusBarHelper.updateStatusBar(gameCollection, engine);
		publish (Messages.getString("RenameSwingWorker.3")); //$NON-NLS-1$
		// We save the results
		publish (Messages.getString("Saving"));
		SerializationHelper.saveGameDB(engine.getGameDB());
		engine.getRenameWindow().getJButton().setEnabled(true);
		engine.getRenameWindow().getButtonStop().setEnabled(false);
		return 0;
	}
	
	@Override
    protected void process(List<String> strings) {
        /* Affichage des publications reçues dans le textarea. */
        for(String s : strings)
        	jTextArea.append(s + '\n');
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
				publish (Messages.getString("RenameSwingWorker.4")+gameFile.getAbsolutePath()+Messages.getString("RenameSwingWorker.5")); //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				// Let's rename it
				String oldFileName = gameFile.getName();
				String newFileName = GameDisplayHelper.buildTitle(game, selConf.getTitlePattern(), selConf);
				String destPath = gameFile.getParent()+File.separator+newFileName+FileToolkit.getFileExtension(oldFileName);
				// If we have to update inside the archive, we will have to update this value
				String newEntryFullPath = null;
				// We have two choices, the file can either be an archive or the rom itself
				
				// First we have to check if we need to rename inside the archive
				if (gameFile.isArchive() && selConf.isRenameInside()) {
					// We get the related entry
					File entry = new File (game.getEntryFullPath());
					String newEnclPath = newFileName+FileToolkit.getFileExtension(entry.getName());
					entry.renameTo(new File(entry.getEnclArchive().getAbsolutePath()+File.separator+newEnclPath));
					newEntryFullPath = destPath+File.separator+newEnclPath;
				}
				
				// We rename the file. It can either be an archive or a rom file
				FileToolkit.moveFile(gameFile, destPath);
				
				// We update the game object values
				game.setContainerPath(destPath);
				game.setEntryFullPath(newEntryFullPath);
				game.setGoodName(true);
				try {
					File.umount();
				} catch (ArchiveException e) {
					e.printStackTrace();
				}
				
				publish (oldFileName+" => "+ newFileName+FileToolkit.getFileExtension(oldFileName)); //$NON-NLS-1$
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

	public JProgressBar getJProgressBar() {
		return jProgressBar;
	}

}
