package rofage.ihm.actions.common;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import rofage.common.Engine;

@SuppressWarnings("serial")
public class ShowCompressAction extends AbstractAction {

	private Engine engine = null;
	
	public ShowCompressAction (Engine engine) {
		this.engine = engine;
	}
	public void actionPerformed(ActionEvent e) {
		engine.getCompressWindow().setVisible(true);
	}

}
