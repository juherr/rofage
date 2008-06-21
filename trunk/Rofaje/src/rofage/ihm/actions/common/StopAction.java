package rofage.ihm.actions.common;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import rofage.common.swingworker.StoppableSwingWorker;

/**
 * Class to trigger the update
 * @author pierrot
 *
 */
@SuppressWarnings("serial")
public class StopAction extends AbstractAction {
	private StoppableSwingWorker<?, ?> sw;
	
	public StopAction(StoppableSwingWorker<?, ?> sw) {
		this.sw = sw;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		sw.setStopAction(true);
	}

}
