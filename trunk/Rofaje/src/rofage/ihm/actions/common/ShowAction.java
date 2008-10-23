package rofage.ihm.actions.common;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class ShowAction extends AbstractAction {

	private Component c = null;
	
	public ShowAction (Component c) {
		this.c = c;
	}
	public void actionPerformed(ActionEvent e) {
		c.setVisible(true);
	}

}
