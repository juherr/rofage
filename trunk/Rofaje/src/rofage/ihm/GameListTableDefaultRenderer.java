package rofage.ihm;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class GameListTableDefaultRenderer implements TableCellRenderer {
	
	protected DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		switch (column) {
		// JLabel
		case 3 : return showLabel (table, value,
				isSelected, hasFocus, row, column);
		case 5 : return showLabel (table, value,
				isSelected, hasFocus, row, column);
		// JButton
		case 1 : return showNote(table, value,
				isSelected, hasFocus, row, column);
		case 2 : return showNote(table, value,
				isSelected, hasFocus, row, column);
		default : return defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}
	}
	
	private NoteDisplay showNote (JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		//JLabel label = (JLabel) defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		Float note = (Float) value;
		return new NoteDisplay (note);	
	}
	
	private JLabel showLabel (JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = (JLabel) defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (value!=null) {
			label.setIcon( ((JLabel)value).getIcon() );
			label.setToolTipText( ((JLabel)value).getToolTipText() );
			label.setText( ((JLabel)value).getText() );
		}
		return label;	
	}
}
