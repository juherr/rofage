package rofage.ihm.actions.common;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
			engine.getMainWindow().getPopMenu().show(e.getComponent(), e.getX(), e.getY());
        }
    }
}
