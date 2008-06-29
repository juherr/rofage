package rofage.ihm.actions.clean;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import rofage.common.Engine;

/**
 * Class to trigger the update
 * @author pierrot
 *
 */
@SuppressWarnings("serial")
public class StopCleanAction extends AbstractAction {
	private Engine engine = null;
	
	public StopCleanAction(Engine engine) {
		this.engine = engine;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		engine.getCleanSW().setStopAction(true);
	}

}
