package rofage.ihm.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import javax.swing.JComboBox;

import rofage.common.Consts;
import rofage.common.Engine;
import rofage.common.helper.GameDisplayHelper;
import rofage.common.object.Game;
import rofage.common.object.GenericDropDownEntry;

public abstract class ComboFilterHelper {
	public static void setFilterSize (Engine engine, JComboBox combo) {
		combo.removeAllItems();
		if (engine.getGlobalConf().getSelectedConf()!=null) {
			Iterator<Game> iterGames = engine.getGameDB().getGameCollections().get(engine.getGlobalConf().getSelectedConf().getConfName()).values().iterator();
			// We have to sort the items before showing them
			TreeSet<Integer> treeSizes = new TreeSet<Integer>();
						
			while (iterGames.hasNext()) {
				Game game = iterGames.next();
				int size = Integer.parseInt(game.getRomSize());
				if (!treeSizes.contains(size)) {
					treeSizes.add(size);
				}
			}
			// Now we display the treeset
			combo.addItem(new GenericDropDownEntry("",""));
			Iterator<Integer> iterSizes = treeSizes.iterator();
			while (iterSizes.hasNext()) {
				int size = iterSizes.next();
				combo.addItem(new GenericDropDownEntry(String.valueOf(size), String.valueOf(size/(1024*1024)+ " MB")));
			}
		}
	}
	
	public static void setFilterLocation (Engine engine, JComboBox combo) {
		combo.removeAllItems();
//		 We add the items into the list (we have to retrieve any possible game location)
		if (engine.getGlobalConf().getSelectedConf()!=null) {
			combo.addItem(new GenericDropDownEntry("",""));
			Iterator<Game> iterGames = engine.getGameDB().getGameCollections().get(engine.getGlobalConf().getSelectedConf().getConfName()).values().iterator();
			List<String> list = new ArrayList<String>(); 
			while (iterGames.hasNext()) {
				Game game = iterGames.next();
				if (!list.contains(game.getLocation())) {
					list.add(game.getLocation());
					String locationText = GameDisplayHelper.getLocation(game);
					combo.addItem(new GenericDropDownEntry(game.getLocation(), locationText));
				}
			}
		}
	}
	
	public static void setFilterLanguage (JComboBox combo) {
		combo.removeAllItems();
//		 We sort the item in natural order for the ui
		TreeMap<String, String> langSet = new TreeMap<String, String>();
		for (int i=0; i<Consts.LANG_NAMES.size(); i++) {
			langSet.put(Consts.LANG_NAMES.get(i), String.valueOf(i));
		}
		
		combo.addItem(new GenericDropDownEntry("",""));
		Iterator<Entry<String, String>> iterLang = langSet.entrySet().iterator();
		while (iterLang.hasNext()) {
			Entry<String, String> entry = iterLang.next();
			combo.addItem(new GenericDropDownEntry(entry.getValue(), entry.getKey()));
		}
	}
}
