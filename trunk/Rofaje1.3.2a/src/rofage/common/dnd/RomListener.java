package rofage.common.dnd;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import net.iharder.dnd.FileDrop.Listener;
import rofage.common.Engine;
import rofage.common.object.Configuration;
import rofage.common.update.ImportSwingWorker;
import rofage.ihm.windows.ImportWindow;

public class RomListener implements Listener {
	private Engine engine;
	
	public RomListener (Engine engine) {
		this.engine = engine;
	}

	public void filesDropped(java.io.File[] files) {
		Configuration selConf = engine.getGlobalConf().getSelectedConf();
		if (selConf==null || selConf.getRomFolder()==null
				|| selConf.getRomFolder().trim().isEmpty()) {
			JOptionPane.showMessageDialog(engine.getMainWindow(), 
					"Le dossier des roms doit être configuré pour l'importation", 
					"Erreur d'importation", 
					JOptionPane.ERROR_MESSAGE);
		} else {
			// We get the list of the filepaths
			List<String> listPaths = new ArrayList<String>();
			for (int i=0; i<files.length; i++) {
				listPaths.add(files[i].getAbsolutePath());
			}
			ImportWindow w = engine.getImportWindow();
			engine.setImportSW(new ImportSwingWorker(engine, listPaths, w.getJProgressBar(), w.getJTextArea()));
			engine.getImportSW().execute();
		}
	}

}
