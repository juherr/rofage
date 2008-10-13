package rofage.ihm.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import rofage.common.Engine;
import rofage.common.rename.RenameSwingWorker;

/**
 * Class to trigger the update
 * @author pierrot
 *
 */
@SuppressWarnings("serial")
public class RenameAction extends AbstractAction {
	private Engine engine = null;
	
	public RenameAction(Engine engine) {
		this.engine = engine;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		engine.setRenameSW(new RenameSwingWorker(engine,
				engine.getRenameWindow().getJProgressBar(),
				engine.getRenameWindow().getJTextArea(),
				null));
		engine.getRenameSW().execute();
	}

}
