package rofage.ihm.actions.update;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import rofage.common.Engine;

/**
 * Class to trigger the update
 * @author pierrot
 *
 */
@SuppressWarnings("serial")
public class StopUpdateAction extends AbstractAction {
	private Engine engine = null;
	
	public StopUpdateAction(Engine engine) {
		this.engine = engine;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		engine.getUpdateSW().setStopUpdate(true);
	}

}
