package rofage.ihm.actions.clean;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import rofage.common.Engine;
import rofage.common.clean.CleanSwingWorker;

/**
 * Class to trigger the update
 * @author pierrot
 *
 */
@SuppressWarnings("serial")
public class CleanAction extends AbstractAction {
	private Engine engine = null;
	
	public CleanAction(Engine engine) {
		this.engine = engine;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		engine.setCleanSW(new CleanSwingWorker(engine, 
				engine.getCleanWindow().getJProgressBar(),
				engine.getCleanWindow().getJTextArea(),
				null));
		engine.getCleanSW().execute();
	}

}
