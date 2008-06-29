package rofage.ihm.actions.scan;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import rofage.common.Engine;

/**
 * Class to trigger the update
 * @author pierrot
 *
 */
@SuppressWarnings("serial")
public class StopScanAction extends AbstractAction {
	private Engine engine = null;
	
	public StopScanAction(Engine engine) {
		this.engine = engine;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		engine.getScanSW().setStopScan(true);
	}

}
