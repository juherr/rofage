package rofage.ihm.actions.common;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;

import rofage.common.Engine;

public class ExtendedMenuListener implements MouseListener {

	private Engine engine = null;
		
	public ExtendedMenuListener (Engine engine) {
		this.engine = engine;
	}
	
	public void mouseClicked(MouseEvent e) {
		if (e.getButton()==MouseEvent.BUTTON3) {
			JTable table = engine.getMainWindow().getJTable();
			
			// First we select the new row
			int rowIndex = table.rowAtPoint(e.getPoint());
			for (int i=0; i<table.getRowCount(); i++) {
				table.changeSelection(rowIndex, i, false, false);
			}
			table.updateUI();
			
			// Then we popup the menu
			engine.getMainWindow().getPopMenu().setLocation(e.getLocationOnScreen());
			engine.getMainWindow().getPopMenu().setVisible(true);
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

}
