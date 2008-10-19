package rofage.ihm.actions.common;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.TreeMap;

import javax.swing.AbstractAction;

import rofage.common.Consts;
import rofage.common.Engine;
import rofage.common.helper.GameDisplayHelper;
import rofage.common.object.Game;
import rofage.common.object.GenericDropDownEntry;
import rofage.ihm.GameListTableModel;
import rofage.ihm.MainWindow;

@SuppressWarnings("serial")
public class FilterGameCollectionAction extends AbstractAction {

	Engine engine = null;
	
	public FilterGameCollectionAction(Engine engine) {
		this.engine = engine;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		engine.getMainWindow().getJTable().clearSelection();
		// We get the value of the form to filter the game Collection
		TreeMap<Integer, Game> gameCollection = engine.getGameDB().getGameCollections().get(engine.getGlobalConf().getSelectedConf().getConfName());
		TreeMap<Integer, Game> filteredGameCollection = new TreeMap<Integer, Game>();
		
		MainWindow w = engine.getMainWindow();
		// Let's get the form entries
		String title 		= w.getFieldTitle().getText().toUpperCase();
		String romSize 		= ((GenericDropDownEntry) w.getComboRomSize().getSelectedItem()).getValue();
		String location 	= ((GenericDropDownEntry) w.getComboLocation().getSelectedItem()).getValue();
		String publisher 	= w.getFieldPublisher().getText().toUpperCase();
		String source 		= w.getFieldSource().getText().toUpperCase();
		String language 	= ((GenericDropDownEntry) w.getComboLanguage().getSelectedItem()).getValue();
		String genre 		= ((GenericDropDownEntry) w.getComboGenre().getSelectedItem()).getValue();
		boolean showOwned 	= w.getCBOwned().isSelected();
		boolean showBadNamed = w.getCBBadName().isSelected();
		boolean showNotOwned = w.getCBNotOwned().isSelected();
		boolean showWifi	= w.getCBWifi().isSelected();
		boolean showDemo 	= w.getCBDemo().isSelected();
		boolean showUnclean = w.getCBNotClean().isSelected();
		
		// Let's see what we have to test
		boolean testTitle = title!=null && !title.trim().isEmpty();
		boolean testPublisher = publisher!=null && !publisher.trim().isEmpty();
		boolean testSource = source!=null && !source.trim().isEmpty();
		boolean testRomSize = !romSize.isEmpty();
		boolean testLocation = !location.isEmpty();
		boolean testLanguage = !language.isEmpty();
		boolean testGenre = !genre.isEmpty();
		
		// Now we parse the game collection to filter the entries
		Iterator<Game> iterGames = gameCollection.values().iterator();
		while (iterGames.hasNext()) {
			Game game = iterGames.next();
			boolean mayAdd = true;
			if (testTitle) {
				if (!game.getTitle().toUpperCase().contains(title)) {
					mayAdd = false;
				}
			}
			if (testPublisher) {
				if (!game.getPublisher().toUpperCase().contains(publisher)) {
					mayAdd = false;
				}
			}
			if (testSource) {
				if (!game.getSourceRom().toUpperCase().contains(source)) {
					mayAdd = false;
				}
			}
			if (testRomSize) {
				if (!game.getRomSize().equals(romSize)) {
					mayAdd = false;
				}
			}
			if (testLanguage) {
				if (!GameDisplayHelper.getLanguage(game).contains(Consts.LANG_NAMES.get(Integer.parseInt(language)))) {
					mayAdd = false;
				}
			}
			if (testLocation) {
				if (!game.getLocation().equals(location)) {
					mayAdd = false;
				}
			}
			if (testGenre) {
				if (!game.getGenre().equals(genre)) {
					mayAdd = false;
				}
			}
			if (!showWifi) {
				if (game.getWifi()==null || !game.getWifi().booleanValue()) {
					mayAdd = false;
				}
			}
			if (!showDemo) {
				// A demo can be known as demo if its genre is Demo if release numbers are base on the releaseNb field
				// Otherwise (comment field) we test "xxxx"
				if (game.getGenre().equals("Demo")) {
					mayAdd = false;
				}
			}
			if (!showUnclean) {
				if (game.isScannedFromSerial()) {
					mayAdd = false;
				}
			}
			
			if (game.isGotRom()) {
				if (!game.isGoodName()) {
					if (!showBadNamed) {
						// A Bad named game is only considered "bad named" if it's an owned game!
						mayAdd = false;
					}
				} else {
					if (!showOwned) {
						mayAdd = false;
					}
				}
			} else {
				if (!showNotOwned) {
					mayAdd = false;
				}
			}
		
			if (mayAdd) {
				filteredGameCollection.put(Integer.parseInt(game.getReleaseNb()), game);
			}
		}
		
		// The game collection is filtered and sorted, lets show it ! 
		GameListTableModel gameListTableModel = (GameListTableModel) w.getJTable().getModel();
		gameListTableModel.setGameCollectionAndDatas(filteredGameCollection);
		
		engine.getMainWindow().getJTable().updateUI();
	}

}
