package rofage.common.object;

import java.io.Serializable;
import java.util.HashMap;
import java.util.TreeMap;

public class GameDB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5683382717957341983L;
	public final static String GAMEDB_FILE_NAME = "gameDB";
	/**
	 * The gameCollections is a map of (datName, gameCollection)
	 * A gameCollection is a treemap of (releaseNb, game)
	 * @see Game
	 */
	private HashMap<String, TreeMap<Integer, Game>> gameCollections = null;
		
	public GameDB () {
		gameCollections = new HashMap<String, TreeMap<Integer,Game>>();
	}

	public HashMap<String, TreeMap<Integer, Game>> getGameCollections() {
		return gameCollections;
	}
}
