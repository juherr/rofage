package rofage.common.compress;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rofage.common.Engine;
import rofage.common.SerializationHelper;
import rofage.common.files.FileToolkit;
import rofage.common.files.ZipToolkit;
import rofage.common.object.Configuration;
import rofage.common.object.Game;
import rofage.common.swingworker.StoppableSwingWorker;
import rofage.ihm.Messages;
import de.schlichtherle.io.File;

public class CompressSwingWorker extends StoppableSwingWorker<Integer, String> {
	
	private Configuration selConf;
	private List<Game> listGamesToCompress;
	
	public CompressSwingWorker (Engine engine, List<Game> listGamesToCompress) {
		this.engine = engine;
		this.stopAction = false;
		this.selConf = engine.getGlobalConf().getSelectedConf();
		this.listGamesToCompress = listGamesToCompress;
		
		
		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if("progress".equals(evt.getPropertyName())) { //$NON-NLS-1$
					getEngine().getCompressWindow().getJProgressBar().setValue((Integer) evt.getNewValue());
				}
            }
		});
	}
	
	@Override
	protected Integer doInBackground() throws Exception {
		// Now we scan folders
		// We prevent additional run of this SW
		engine.getCompressWindow().getJButton().setEnabled(false);
		engine.getCompressWindow().getButtonStop().setEnabled(true);
		setProgress(0);
		publish(Messages.getString("CompressSwingWorker.1")); //$NON-NLS-1$
		
		// If no list is provided, we have to compress not compressed filed
		if (listGamesToCompress==null) {
			generateCompressList();
		}
		
		publish (listGamesToCompress.size()+Messages.getString("CompressSwingWorker.2")); //$NON-NLS-1$
		
		compressFilesInList();
		
		setProgress(100);
		publish (Messages.getString("CompressSwingWorker.3")); //$NON-NLS-1$
		
		// We save the results
		SerializationHelper.saveGameDB(engine.getGameDB());
		engine.getCompressWindow().getJButton().setEnabled(true);
		engine.getCompressWindow().getButtonStop().setEnabled(false);
		return 0;
	}
	
	@Override
    protected void process(List<String> strings) {
        /* Affichage des publications reçues dans le textarea. */
        for(String s : strings)
        	engine.getCompressWindow().getJTextArea().append(s + '\n');
    }
	
	/**
	 * Generates the list of files to be compressed
	 * Only files which are not already compressed are considered
	 *
	 */
	private void generateCompressList () {
		listGamesToCompress = new ArrayList<Game>();
		Iterator<Game> iterGames = engine.getGameDB().getGameCollections().get(selConf.getConfName()).values().iterator();
		while (iterGames.hasNext()) {
			Game game = iterGames.next();
			// We must have this game and no reference to a potential archive
			if (game.isGotRom() 
					&& (game.getEntryFullPath()==null 
					|| game.getEntryFullPath().isEmpty())) {
				listGamesToCompress.add(game);
			}
		}
	}
	
	/**
	 * Compress the files in the list
	 *
	 */
	private void compressFilesInList () {
		int nbFilesCompressed = 0;
		int totalFiles = listGamesToCompress.size();
		
		Iterator<Game> iterGames = listGamesToCompress.iterator();
		while (iterGames.hasNext() && !stopAction) {
			Game game = iterGames.next();
			File gameFile = new File (game.getContainerPath());
			publish (Messages.getString("CompressSwingWorker.7")+gameFile.getName());
			
			// We check whether the file still exists !
			if (!gameFile.exists()) {
				publish (Messages.getString("CompressSwingWorker.5")+gameFile.getAbsolutePath()+Messages.getString("CleanSwingWorker.6")); //$NON-NLS-1$ //$NON-NLS-2$
			} else if (gameFile.isArchive()) {
				publish (Messages.getString("CompressSwingWorker.8"));
			} else {
				// We create a new archive
				String archivePath = FileToolkit.removeFileExtension(gameFile.getAbsolutePath())+".zip";
				List<String> listPaths = new ArrayList<String>();
				listPaths.add(gameFile.getAbsolutePath());
				ZipToolkit.compress(listPaths, archivePath, selConf.getCompressValue());
				
				// Then we have to update the gameDB
				game.setContainerPath(archivePath);
				game.setEntryFullPath(game.getContainerPath()+File.separator+gameFile.getName());
				if (selConf.isDeleteSource()) {
					// We also delete the source file
					gameFile.delete();
				}
				
				publish (gameFile.getName()+Messages.getString("CompressSwingWorker.9")); //$NON-NLS-1$
				
				nbFilesCompressed++;
				setProgress(nbFilesCompressed*100/totalFiles);
			}
		}
	}
	
	public Engine getEngine() {
		return engine;
	}

}
