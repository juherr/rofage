package rofage.ihm;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class GameListTableDefaultJLabelRenderer implements TableCellRenderer {
	protected DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();

	public Component getTableCellRendererComponent(JTable table, Object value,
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
