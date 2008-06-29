package rofage.ihm.actions.duplicate;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import rofage.common.Engine;
import rofage.common.duplicate.DuplicateSwingWorker;

/**
 * Class to trigger the update
 * @author pierrot
 *
 */
@SuppressWarnings("serial")
public class DuplicateAction extends AbstractAction {
	private Engine engine = null;
	
	public DuplicateAction(Engine engine) {
		this.engine = engine;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		engine.setDuplicateSW(new DuplicateSwingWorker(engine,
				engine.getDuplicateWindow().getJProgressBar(),
				engine.getDuplicateWindow().getJTextArea()));
		engine.getDuplicateSW().execute();
	}

}
