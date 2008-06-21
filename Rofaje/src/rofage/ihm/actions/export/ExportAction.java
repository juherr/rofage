package rofage.ihm.actions.export;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import de.schlichtherle.io.File;

import rofage.common.Engine;
import rofage.common.export.ExportSwingWorker;
import rofage.ihm.MainWindow;

@SuppressWarnings("serial")
public class ExportAction extends AbstractAction {

	private Engine engine = null;
	
	public ExportAction (Engine engine) {
		this.engine = engine;
	}
	
	public void actionPerformed(ActionEvent e) {
		MainWindow w = engine.getMainWindow();
				
		int returnVal = w.getFolderChooser().showOpenDialog(w);
		
		if (returnVal==JFileChooser.APPROVE_OPTION) {
			File file = new File(w.getFolderChooser().getSelectedFile());
			engine.setExportSW(new ExportSwingWorker(engine, file));
			engine.getExportSW().execute();
		}
	}
}
