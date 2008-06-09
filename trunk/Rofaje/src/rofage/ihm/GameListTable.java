package rofage.ihm;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

@SuppressWarnings("serial")
public class GameListTable extends JTable {
	public GameListTable (String titlePattern) {
		super();
		setAutoscrolls(true);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setVisible(true);
		setFillsViewportHeight(true);
		setModel(new GameListTableModel(titlePattern));
		setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		getColumnModel().getColumn(0).setMaxWidth(25);
		getColumnModel().getColumn(1).setMaxWidth(25);
	}
	
	
	
}
