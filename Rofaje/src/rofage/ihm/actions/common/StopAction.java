package rofage.ihm.actions.common;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import rofage.common.Engine;

/**
 * Class to trigger the update
 * @author pierrot
 *
 */
@SuppressWarnings("serial")
public class StopAction extends AbstractAction {
	public final static int SW_UPDATE 	= 1;
	public final static int SW_SCAN 	= 2;
	public final static int SW_CLEAN 	= 3;
	public final static int SW_RENAME 	= 4;
	public final static int SW_EXPORT 	= 5;
	
	private Engine engine;
	private int type;
	
	public StopAction(Engine engine, int type) {
		this.type = type;
		this.engine = engine;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		switch (type) {
			case SW_CLEAN : engine.getCleanSW().setStopAction(true);
				break;
			case SW_EXPORT : engine.getExportSW().setStopAction(true);
				break;
			case SW_RENAME : engine.getRenameSW().setStopAction(true);
				break;
			case SW_SCAN : engine.getScanSW().setStopAction(true);
				break;
			case SW_UPDATE : engine.getUpdateSW().setStopAction(true);
				break;
		}
	}

}
