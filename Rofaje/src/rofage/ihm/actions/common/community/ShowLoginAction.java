package rofage.ihm.actions.common.community;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import rofage.common.Engine;

@SuppressWarnings("serial")
public class ShowLoginAction extends AbstractAction {

	private Engine engine = null;
	
	public ShowLoginAction (Engine engine) {
		this.engine = engine;
	}
	public void actionPerformed(ActionEvent e) {
		engine.getLoginWindow().setVisible(true);
	}

}
