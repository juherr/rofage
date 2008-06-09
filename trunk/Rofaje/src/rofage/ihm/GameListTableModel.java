package rofage.ihm;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import rofage.common.Consts;
import rofage.common.helper.GameDisplayHelper;
import rofage.common.object.Game;

@SuppressWarnings("serial")
public class GameListTableModel extends AbstractTableModel implements TableModel {
	TreeMap<Integer, Game> gameCollection = new TreeMap<Integer, Game>();
	List<Game> tableDatas = new ArrayList<Game>(gameCollection.values());
	String[] columnNames = {"L", "R", "Titre"};
	String titlePattern;
	
	public GameListTableModel (String titlePattern) {
		super();
		this.titlePattern = titlePattern; 
	}
	
	public String getColumnName (int col) {
		return columnNames[col].toString();
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return tableDatas.size();
	}

	public Game getGameAt (int row) {
		return tableDatas.get(row);
	}
	
	public Object getValueAt(int row, int col) {
		Game game = tableDatas.get(row);
		if (col==0) {
			// Location
			return new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/flags/"+Consts.FLAG_NAMES.get(game.getLocation())+".png"));
		} else if (col==1) {
			if (game.isGotRom()) {
				if (game.isGoodName()) {
					return new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/rom.png"));
				} else {
					return new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/rom_badname.png"));
				}
			}
			return new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/no_rom.png"));
		} if (col==2) {
			return GameDisplayHelper.buildTitle(game, titlePattern);
		}
		return 0;
	}
	
	public Class<?> getColumnClass(int col) {
		if (col==0 || col==1) return ImageIcon.class;
		else return String.class;
    }

	
	public void setGameCollectionAndDatas (TreeMap<Integer, Game> gameCollection) {
		setGameCollection(gameCollection);
		tableDatas.clear();
		tableDatas.addAll(gameCollection.values());
	}

	public TreeMap<Integer, Game> getGameCollection() {
		return gameCollection;
	}

	public void setGameCollection(TreeMap<Integer, Game> gameCollection) {
		this.gameCollection = gameCollection;
	}

	public List<Game> getTableDatas() {
		return tableDatas;
	}

	public void setTableDatas(List<Game> tableDatas) {
		this.tableDatas = tableDatas;
	}

	public String getTitlePattern() {
		return titlePattern;
	}

	public void setTitlePattern(String titlePattern) {
		this.titlePattern = titlePattern;
	}

}
