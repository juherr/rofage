package rofage.ihm.actions.common;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import rofage.common.Engine;

@SuppressWarnings("serial")
public class ShowUpdateAction extends AbstractAction {

	private Engine engine = null;
	
	public ShowUpdateAction (Engine engine) {
		this.engine = engine;
	}
	public void actionPerformed(ActionEvent e) {
		engine.getUpdateWindow().setVisible(true);
	}

}
