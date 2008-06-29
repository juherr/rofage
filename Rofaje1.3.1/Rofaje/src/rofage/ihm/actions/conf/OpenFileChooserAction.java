package rofage.ihm.actions.conf;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import rofage.common.Engine;

@SuppressWarnings("serial")
public class OpenFileChooserAction extends AbstractAction {

	private Engine engine = null;
	
	public OpenFileChooserAction (Engine engine) {
		this.engine = engine;
	}
	
	public void actionPerformed(ActionEvent e) {
		int returnVal = engine.getConfWindow().getFolderChooser().showOpenDialog(engine.getConfWindow());
		
		if (returnVal==JFileChooser.APPROVE_OPTION) {
			File file = engine.getConfWindow().getFolderChooser().getSelectedFile();
			if (e.getSource() == engine.getConfWindow().getButtonOpenRomFolder()) {
				engine.getConfWindow().getFieldRomFolder().setText(file.getAbsolutePath());
			} else {
				engine.getConfWindow().getFieldUnknownRomFolder().setText(file.getAbsolutePath());
			}	
		}
	}
}
