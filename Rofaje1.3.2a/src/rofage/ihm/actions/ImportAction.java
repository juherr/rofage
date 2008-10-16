package rofage.ihm.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import rofage.common.Engine;
import rofage.common.object.Configuration;
import rofage.common.update.ImportSwingWorker;
import rofage.ihm.Messages;
import rofage.ihm.windows.conf.FileChooserFilter;

@SuppressWarnings("serial")
public class ImportAction extends AbstractAction {
	
	private Engine engine	= null;
	
	public ImportAction (Engine engine) {
		this.engine = engine;
	}

	public void actionPerformed(ActionEvent arg0) {
		Configuration selConf = engine.getGlobalConf().getSelectedConf();
		String[] tabRomExtensions = selConf.getAllowedExtensions().toArray(new String[0]);
		FileChooserFilter romFilter = new FileChooserFilter(tabRomExtensions, Messages.getString("RomFiles"));
		JFileChooser romChooser = new JFileChooser();
		romChooser.setFileFilter(romFilter);
		romChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		romChooser.setMultiSelectionEnabled(true);
		romChooser.setVisible(true);
		
		int returnVal = romChooser.showOpenDialog(engine.getMainWindow());
		
		if (returnVal==JFileChooser.APPROVE_OPTION) {
			List<String> listPaths = new ArrayList<String>();
			File[] tabFiles = romChooser.getSelectedFiles();
			// We just get the paths
			for (int i=0; i<tabFiles.length; i++) {
				listPaths.add(tabFiles[i].getAbsolutePath());
			}
			
			engine.setImportSW(new ImportSwingWorker(engine, 
					listPaths, 
					engine.getImportWindow().getJProgressBar(),
					engine.getImportWindow().getJTextArea()));
			engine.getImportSW().execute();
		}
		
	}

}
