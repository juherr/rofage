package rofage.ihm.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import rofage.common.Engine;
import rofage.common.scan.ScanSwingWorker;

/**
 * Class to trigger the update
 * @author pierrot
 *
 */
@SuppressWarnings("serial")
public class ScanAction extends AbstractAction {
	private Engine engine = null;
	
	public ScanAction(Engine engine) {
		this.engine = engine;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		engine.setScanSW(new ScanSwingWorker(engine, 
				engine.getScanWindow().getJProgressBar(), 
				engine.getScanWindow().getJTextArea(), 
				null));
		engine.getScanSW().execute();
	}

}
