package rofage.ihm;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import rofage.common.object.GenericDropDownEntry;

@SuppressWarnings("serial")
public class GenericDropDownEntryRenderer implements ListCellRenderer {
	protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	
	public GenericDropDownEntryRenderer () {
		
	}
	
	public Component getListCellRendererComponent(
						JList list,
						Object value,
						int index,
						boolean isSelected,
						boolean cellHasFocus )	{
		
		JLabel label = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if (value!=null) {
			label.setText( ((GenericDropDownEntry)value).getText() );
			label.setIcon( ((GenericDropDownEntry)value).getIcon() );
		}
				
		return label;
	}
}
