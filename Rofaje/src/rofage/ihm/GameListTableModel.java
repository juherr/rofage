package rofage.ihm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import rofage.common.Consts;
import rofage.common.Engine;
import rofage.common.helper.GameDisplayHelper;
import rofage.common.object.Game;
import rofage.common.url.URLToolkit;

@SuppressWarnings("serial") //$NON-NLS-1$
public class GameListTableModel extends AbstractTableModel implements TableModel {
	private TreeMap<Integer, Game> gameCollection = new TreeMap<Integer, Game>();
	private List<Game> tableDatas = new ArrayList<Game>(gameCollection.values());
	private String[] columnNames = {"L", "R", "I", "C", Messages.getString("GameListTableModel.3")}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	private String titlePattern;
	private Engine engine;
	
	public GameListTableModel (String titlePattern, Engine engine) {
		super();
		this.titlePattern = titlePattern;
		this.engine = engine;
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
		if (row!=-1) {
			return tableDatas.get(row);
		} else {
			// we build a dummy game since no game is selected
			return new Game();
		}
	}
	
	public Object getValueAt(int row, int col) {
		Game game = tableDatas.get(row);
		if (game!=null) {
			if (col==0) {
				// Location
				return new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/flags/"+Consts.FLAG_NAMES.get(game.getLocation())+".png")); //$NON-NLS-1$ //$NON-NLS-2$
			} else if (col==1) {
				if (game.isGotRom()) {
					if (game.isGoodName()) {
						return new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/rom.png")); //$NON-NLS-1$
					} else {
						return new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/rom_badname.png")); //$NON-NLS-1$
					}
				}
				return new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/no_rom.png")); //$NON-NLS-1$
			} else if (col==2) {
				// We show the icon if available
				String iconName = GameDisplayHelper.constructFileName(game, URLToolkit.TYPE_ICON);
				String folderPath = Consts.HOME_FOLDER+File.separator+engine.getGlobalConf().getSelectedConf().getImageFolder()+File.separator+Consts.ICO_FOLDER+File.separator;
				File iconeFile = new File (folderPath+iconName);
				if (iconeFile.exists()) return new ImageIcon(folderPath+iconName);
			} else if (col==3) {
				if (!game.isGotRom()) {
					return new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/no_rom.png")); //$NON-NLS-1$					
				} else {
					if (game.isScannedFromSerial()) {
						return new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/rom_notclean.png"));
					} else {
						return new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/rom_clean.png"));
					}
				}
			} else if (col==4) {
				return GameDisplayHelper.buildTitle(game, titlePattern);
			}
		}
		return 0;
	}
	
	public Class<?> getColumnClass(int col) {
		if (col==0 || col==1 || col==2 || col==3) return ImageIcon.class;
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
