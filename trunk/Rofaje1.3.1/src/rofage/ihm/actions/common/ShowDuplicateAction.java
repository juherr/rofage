package rofage.ihm.actions.common;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import rofage.common.Engine;

@SuppressWarnings("serial")
public class ShowDuplicateAction extends AbstractAction {

	private Engine engine = null;
	
	public ShowDuplicateAction (Engine engine) {
		this.engine = engine;
	}
	public void actionPerformed(ActionEvent e) {
		engine.getDuplicateWindow().setVisible(true);
	}

}
