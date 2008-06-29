package rofage.ihm.actions.common;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class HideAction extends AbstractAction {
	
	JFrame window = null;
	
	public HideAction (JFrame window) {
		this.window = window;
	}

	public void actionPerformed(ActionEvent arg0) {
		window.setVisible(false);
	}

}
