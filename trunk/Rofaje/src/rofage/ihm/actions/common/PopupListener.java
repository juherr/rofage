package rofage.ihm.actions.common;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

import rofage.common.Engine;

public class PopupListener extends MouseAdapter {
	
    private Engine engine = null;
    
	public PopupListener (Engine engine) {
    	this.engine = engine;
    }
	
	public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }
	
    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }
    
    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
        	JTable table = engine.getMainWindow().getJTable();
			
			// First we select the new row
			int rowIndex = table.rowAtPoint(e.getPoint());
			for (int i=0; i<table.getRowCount(); i++) {
				table.changeSelection(rowIndex, i, false, false);
			}
			table.updateUI();
			
			engine.getMainWindow().getPopMenu().show(e.getComponent(), e.getX(), e.getY());
        }
    }
}
