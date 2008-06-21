package rofage.ihm;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import rofage.common.Engine;

@SuppressWarnings("serial")
public class GameListTable extends JTable {
	public GameListTable (String titlePattern, Engine engine) {
		super();
		setAutoscrolls(true);
		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		setVisible(true);
		setFillsViewportHeight(true);
		setModel(new GameListTableModel(titlePattern, engine));
		setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		setRowHeight(32);
		getColumnModel().getColumn(0).setMaxWidth(25);
		getColumnModel().getColumn(0).setMinWidth(25);
		getColumnModel().getColumn(1).setMaxWidth(25);
		getColumnModel().getColumn(1).setMinWidth(25);
		getColumnModel().getColumn(2).setMaxWidth(32);
		getColumnModel().getColumn(2).setMinWidth(32);
	}
	
	
	
}
