package rofage.ihm.helper;

import java.util.Iterator;
import java.util.TreeMap;

import rofage.common.Engine;
import rofage.common.object.Game;
import rofage.ihm.MainWindow;

public abstract class StatusBarHelper {
	public static void updateStatusBar(TreeMap<Integer, Game> gameCollection, Engine engine) {
		int gameOwned = 0;
		int gameNotOwned = 0;
		int gameBadName = 0;
		int clean = 0;
		int notclean = 0;
		Iterator<Game> iterGames = gameCollection.values().iterator();
		while (iterGames.hasNext()) {
			Game game = iterGames.next();
			if (game.isGotRom()) {
				if (game.isGoodName()) {
					gameOwned++;
				} else {
					gameBadName++;
				}
				if (game.isScannedFromSerial()) {
					notclean++;
				} else {
					clean++;
				}
			} else {
				gameNotOwned++;
			}
		}
		
		MainWindow w = engine.getMainWindow();
		w.getLabelStatusIconBadNamed().setText(String.valueOf(gameBadName));
		w.getLabelStatusIconOwned().setText(String.valueOf(gameOwned));
		w.getLabelStatusIconNotOwned().setText(String.valueOf(gameNotOwned));
		w.getLabelStatusIconClean().setText(String.valueOf(clean));
		w.getLabelStatusIconNotClean().setText(String.valueOf(notclean));
		
		w.getPanelStatusBar().updateUI();
	}
}
