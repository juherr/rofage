package rofage.common.duplicate;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import rofage.common.Consts;
import rofage.common.Engine;
import rofage.common.SerializationHelper;
import rofage.common.helper.GameDisplayHelper;
import rofage.common.object.Configuration;
import rofage.common.object.Game;
import rofage.common.swingworker.StoppableSwingWorker;
import rofage.ihm.Messages;
import rofage.ihm.helper.StatusBarHelper;
import de.schlichtherle.io.File;

public class DuplicateSwingWorker extends StoppableSwingWorker<Integer, String> {
	
	private Engine engine;
	private Configuration selConf;
	private TreeMap<Integer, Game> gameCollection;
	private JProgressBar jProgressBar;
	private JTextArea jTextArea;
	private int nbTotalFiles;
	private List<String> listPreferedVersions;
	
	public DuplicateSwingWorker (Engine engine,
			JProgressBar jProgressBar,
			JTextArea jTextArea) {
		this.engine = engine;
		this.selConf = engine.getGlobalConf().getSelectedConf();
		this.gameCollection = engine.getGameDB().getGameCollections().get(selConf.getConfName());
		this.jTextArea = jTextArea;
		this.jProgressBar = jProgressBar;
		this.nbTotalFiles = 0;
		this.listPreferedVersions = selConf.getListLocations();

		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if("progress".equals(evt.getPropertyName())) { //$NON-NLS-1$
					getJProgressBar().setValue((Integer) evt.getNewValue());
				}
            }
		});
	}
	
	@Override
    protected void process(List<String> strings) {
        /* Affichage des publications reçues dans le textarea. */
        for(String s : strings)
        	jTextArea.append(s + '\n');
    }
	
	public JProgressBar getJProgressBar() {
		return jProgressBar;
	}

	@Override
	protected Integer doInBackground() throws Exception {
		setProgress(0);
		publish (Messages.getString("DuplicateSwingWorker.0")); //$NON-NLS-1$
		HashMap<String, List<Game>> listDuplicates = generateDuplicateList();
		publish (Messages.getString("DuplicateSwingWorker.1")+nbTotalFiles+Messages.getString("DuplicateSwingWorker.2")+listDuplicates.size()+Messages.getString("DuplicateSwingWorker.3")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		int res = JOptionPane.showConfirmDialog(engine.getDuplicateWindow(), Messages.getString("DuplicateSwingWorker.4") + //$NON-NLS-1$
				Messages.getString("DuplicateSwingWorker.5") + //$NON-NLS-1$
				Messages.getString("DuplicateSwingWorker.6"),  //$NON-NLS-1$
				Messages.getString("DuplicateSwingWorker.7"),  //$NON-NLS-1$
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.WARNING_MESSAGE);
		if (res==JOptionPane.NO_OPTION) {
			publish (Messages.getString("DuplicateSwingWorker.8")); //$NON-NLS-1$
		} else {
			publish (Messages.getString("DuplicateSwingWorker.9")); //$NON-NLS-1$
			deleteDuplicates(listDuplicates);
			publish (Messages.getString("DuplicateSwingWorker.10")); //$NON-NLS-1$
			File.umount();
			publish (Messages.getString("DuplicateSwingWorker.11")); //$NON-NLS-1$
			SerializationHelper.saveGameDB(engine.getGameDB());
			publish (Messages.getString("DuplicateSwingWorker.12")); //$NON-NLS-1$
			
			// we update the UI
			engine.getMainWindow().getJTable().updateUI();
//			 We also update the status bar
			StatusBarHelper.updateStatusBar(gameCollection, engine);
		}
		
		
		return 0;
	}
	
	/**
	 * This method will track the prefered version for a rom and remove others
	 * @param listDuplicates
	 */
	private void deleteDuplicates (HashMap<String, List<Game>> listDuplicates) {
		int nbFiles = 0;
		Iterator<Entry<String, List<Game>>> iterDuplicates = listDuplicates.entrySet().iterator();
		while (iterDuplicates.hasNext() && !stopAction) {
			Entry<String, List<Game>> entry = iterDuplicates.next();
			Game prefGame = getPreferedGame(entry.getValue());
			if (prefGame!=null) {
				publish (Messages.getString("DuplicateSwingWorker.13")+prefGame.getTitle()+" ("+GameDisplayHelper.getLocation(prefGame)+")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				// We delete the other versions
				Iterator<Game> iterGames = entry.getValue().iterator();
				while (iterGames.hasNext() && !stopAction) {
					Game game = iterGames.next();
					if (!game.getReleaseNb().equals(prefGame.getReleaseNb())) {
						// We delete this game it is not the prefered version
						deleteGame(game);
						nbFiles++;
						setProgress(nbFiles*100/nbTotalFiles);
					}
				}
			}
			nbFiles++; // We add the prefGame as a treated file
			setProgress(nbFiles*100/nbTotalFiles);
		}
	}
	
	/**
	 * Delete a game from the file system and update the GameDB accordingly
	 * If the game is located in an archive, we delete the related entry and the archive 
	 * if the resulting archive is emtpy
	 * NOTE : The gameDB is not saved on the HDD at this point !
	 * @param game to delete
	 */
	private void deleteGame (Game game) {
		publish (Messages.getString("DuplicateSwingWorker.16")+game.getTitle()+" ("+GameDisplayHelper.getLocation(game)+")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		if (game.getEntryFullPath()==null) {
			// This game is not located in an archive
			File f = new File(game.getContainerPath());
			f.delete();
		} else {
			// The game is located in an archive, we have to delete this entry
			File f = new File(game.getEntryFullPath());
			File archive = f.getTopLevelArchive();
			f.delete();
			if (archive.list().length==0) {
				publish(Messages.getString("DuplicateSwingWorker.19")+archive.getName()+Messages.getString("DuplicateSwingWorker.20")); //$NON-NLS-1$ //$NON-NLS-2$
				// We also delete the archive
				archive.delete();
			}
		}
		// Now we update the game DB
		game.setContainerPath(""); //$NON-NLS-1$
		game.setEntryFullPath(""); //$NON-NLS-1$
		game.setGotRom(false);
		game.setScannedFromSerial(false);
		gameCollection.put(Integer.parseInt(game.getReleaseNb()), game);
	}
	
	/**
	 * Get the prefered game in a list of versions of the same game
	 * It looks if it finds the prefered version, if not he try with the 2nd one and so on
	 * Once it finds the prefered version it returns it
	 * @param listGames
	 * @return
	 */
	private Game getPreferedGame (List<Game> listGames) {
		Iterator<String> iterLocations = listPreferedVersions.iterator();
		while (iterLocations.hasNext()) {
			String location = iterLocations.next();
			Iterator<Game> iterGames = listGames.iterator();
			while (iterGames.hasNext()) {
				Game game = iterGames.next();
				if (Consts.COUNTRY_CODES.get(game.getLocation()).equalsIgnoreCase(location)) {
					// It's the prefered version
					return game;
				}
			}
		}
		return null; // This should never happen since we have several version of a game
	}
	
	/**
	 * Browses the game collection to found the game we have and adding them the duplicate list
	 * First we go through the collection and we add each game we have in the hashmap with the duplicate
	 * id as the key
	 * Then we clean up this by going through one more time and removing any entry which has only one entry
	 * @return
	 */
	private HashMap<String, List<Game>> generateDuplicateList() {
		// HashMap <duplicateId, Game>
		HashMap<String, List<Game>> duplicateList = new HashMap<String, List<Game>>();
		Iterator<Game> iterGames = gameCollection.values().iterator();
		while (iterGames.hasNext()) {
			Game game = iterGames.next();
			if (game.isGotRom()) {
				if (game.getDuplicateId()!=null && !game.getDuplicateId().trim().isEmpty()
						&& !"0".equals(game.getDuplicateId()) ) { // The duplicate Id 0 means there is no duplicate ! //$NON-NLS-1$
					String duplicateId = game.getDuplicateId();
					List <Game> listGames = duplicateList.get(duplicateId);
					// If the list is null (it's the first time we add this duplicate Id,
					// we create the list
					if (listGames==null) listGames = new ArrayList<Game>();
					listGames.add(game);
					duplicateList.put(game.getDuplicateId(), listGames);
				}
			}
		}
		
		// Now we have to clean this list : we remove the entries where the list contains less than 2 elements
		Iterator<Entry<String, List<Game>>> iterListGames = duplicateList.entrySet().iterator();
		while (iterListGames.hasNext()) {
			Entry<String, List<Game>> entry = iterListGames.next();
			List<Game> listGames = entry.getValue();
			if (listGames.size()<2) {
				iterListGames.remove();
			} else {
				nbTotalFiles += listGames.size();
			}
		}
		return duplicateList;
	}
}
