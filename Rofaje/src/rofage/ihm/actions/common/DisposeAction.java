package rofage.ihm.actions.common;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class DisposeAction extends AbstractAction {
	
	JFrame window = null;
	
	public DisposeAction (JFrame window) {
		this.window = window;
	}

	public void actionPerformed(ActionEvent arg0) {
		window.dispose();
	}

}
