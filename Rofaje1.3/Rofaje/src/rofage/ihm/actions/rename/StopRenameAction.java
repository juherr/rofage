package rofage.ihm.actions.rename;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import rofage.common.Engine;

/**
 * Class to trigger the update
 * @author pierrot
 *
 */
@SuppressWarnings("serial")
public class StopRenameAction extends AbstractAction {
	private Engine engine = null;
	
	public StopRenameAction(Engine engine) {
		this.engine = engine;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		engine.getRenameSW().setStopAction(true);
	}

}
