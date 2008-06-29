package rofage.common.helper;

import java.util.TreeMap;

import rofage.common.Engine;
import rofage.common.object.Game;
import rofage.common.parser.DatParser;

public abstract class GameDBHelper {

	/**
	 * Creates the Game Collection related to this configuration
	 *
	 */
	public static TreeMap<Integer, Game> createGameCollectionInEngine (Engine engine, String datFileName, DatParser datParser) {
		// We create a new GameCollection
		// the datParser.retrieveGameCollection returns a hashmap which is converted later
		// into a TreeMap so the sort is done only once.
		TreeMap<Integer, Game> gameCollection = new TreeMap<Integer, Game> (datParser.retrieveGameCollection());
		// TODO checks if a game is already marked as owned if this is replaced
		// if so find a good way to keep those datas !
		engine.getGameDB().getGameCollections().put(datFileName, gameCollection);
		return gameCollection;
	}
}
