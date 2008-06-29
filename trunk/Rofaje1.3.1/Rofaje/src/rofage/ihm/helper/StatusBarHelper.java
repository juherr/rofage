package rofage.ihm.helper;

import java.util.Iterator;
import java.util.TreeMap;

import rofage.common.Engine;
import rofage.common.object.Game;

public abstract class StatusBarHelper {
	public static void updateStatusBar(TreeMap<Integer, Game> gameCollection, Engine engine) {
		int gameOwned = 0;
		int gameNotOwned = 0;
		int gameBadName = 0;
		Iterator<Game> iterGames = gameCollection.values().iterator();
		while (iterGames.hasNext()) {
			Game game = iterGames.next();
			if (game.isGotRom()) {
				if (game.isGoodName()) {
					gameOwned++;
				} else {
					gameBadName++;
				}
			} else {
				gameNotOwned++;
			}
		}
		engine.getMainWindow().getLabelStatusIconBadNamed().setText(String.valueOf(gameBadName));
		engine.getMainWindow().getLabelStatusIconOwned().setText(String.valueOf(gameOwned));
		engine.getMainWindow().getLabelStatusIconNotOwned().setText(String.valueOf(gameNotOwned));
		
		engine.getMainWindow().getPanelStatusBar().updateUI();
	}
}
