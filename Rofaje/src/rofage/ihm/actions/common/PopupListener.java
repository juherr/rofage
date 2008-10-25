package rofage.ihm.actions.common;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import rofage.common.Engine;
import rofage.ihm.GameListTableModel;
import rofage.ihm.Messages;

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
        	// We may have to change the text of the "mark as owned menuItem"
        	// We write "Mark as owned" when we have at least on game which is not owned
        	// We write "Mark as not owned" otherwise
        	boolean markAsOwned = false;
        	
        	int [] selRows = engine.getMainWindow().getJTable().getSelectedRows();
    		GameListTableModel model = (GameListTableModel)engine.getMainWindow().getJTable().getModel();
    		int i = 0;
    		while (i<selRows.length && !markAsOwned) {    		
    			if (!model.getGameAt(selRows[i]).isOwnedRom()) {
    				markAsOwned = true;
    			}
    			i++;
    		}
    		if (markAsOwned) {
    			engine.getMainWindow().getPopMenuItemMarkAsOwned().setText(Messages.getString("MainWindow.markAsOwned"));
    		} else {
    			engine.getMainWindow().getPopMenuItemMarkAsOwned().setText(Messages.getString("MainWindow.markAsNotOwned"));
    		}
			engine.getMainWindow().getPopMenu().show(e.getComponent(), e.getX(), e.getY());
        }
    }
}
