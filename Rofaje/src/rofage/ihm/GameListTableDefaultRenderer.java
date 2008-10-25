package rofage.ihm;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import rofage.common.object.Game;
import rofage.ihm.helper.IconHelper;

public class GameListTableDefaultRenderer implements TableCellRenderer {
	
	protected DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		switch (column) {
			// JLabel
			case 0 : return showIconLocation (value);
			case 3 : return showIconGotRom (value);
			case 5 : return showCleanDumpIcon(value);
			// Notes (panel)
			case 1 : return showNote(table, value,
					isSelected, hasFocus, row, column);
			case 2 : return showNote(table, value,
					isSelected, hasFocus, row, column);
			default : return defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}
	}
	
	/**
	 * Displays an icon relevant to the current game
	 * A game can be a clean dump or a bad dump
	 * If a game is not in the database (which means we haven't found the game
	 * on the filesystem, then we check if it has been marked as owned)
	 * @param value
	 * @return
	 */
	private JLabel showCleanDumpIcon (Object value) {
		Game game = (Game) value;
		if (game.isGotRom()) {
			return IconHelper.getCleanDumpIcon(game);
		} else {
			return IconHelper.getOwnedRomIcon(game);
		}
	}
	
	private JLabel showIconGotRom (Object value) {
		Game game = (Game) value;
		return IconHelper.getGotRomIcon(game);
	}
		
	
	private JLabel showIconLocation (Object value) {
		String location = (String) value;
		JLabel label = new JLabel();
		label.setIcon(IconHelper.getLocationIcon(location));
		return label;
	}
	
	private NoteDisplay showNote (JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		//JLabel label = (JLabel) defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		Float note = (Float) value;
		return new NoteDisplay (note);	
	}
}
