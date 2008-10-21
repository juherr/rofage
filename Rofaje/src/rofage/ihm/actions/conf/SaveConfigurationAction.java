package rofage.ihm.actions.conf;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import rofage.common.Engine;
import rofage.common.MainSwingWorker;
import rofage.common.SerializationHelper;
import rofage.common.helper.ConfigurationHelper;
import rofage.common.object.Configuration;
import rofage.common.scan.ScanSwingWorker;
import rofage.ihm.GameListTableModel;

@SuppressWarnings("serial")
public class SaveConfigurationAction extends AbstractAction {
	
	private Engine engine = null;
	
	public SaveConfigurationAction(Engine engine) {
		this.engine = engine;
	}

	public void actionPerformed(ActionEvent e) {
		engine.getConfWindow().getProgressPanel().start();
		// We save the current value of the rom folder
		// We use this value at a later time to trigger a scan if needed
		String oldRomFolder = engine.getGlobalConf().getSelectedConf().getRomFolder();
		String oldTitlePattern = engine.getGlobalConf().getSelectedConf().getTitlePattern();
		
		// We update the configuration with the form datas
		ConfigurationHelper.saveCurrentConfigurationInEngine(engine);
		
		// Then we write the file
		SerializationHelper.saveGlobalConfiguration(engine.getGlobalConf());
		
		// When a DAT is added, the gameDB may have changed, we save it again
		SerializationHelper.saveGameDB(engine.getGameDB());
		
		Configuration newConf = engine.getGlobalConf().getSelectedConf();
		((GameListTableModel) engine.getMainWindow().getJTable().getModel()).setTitlePattern(newConf.getTitlePattern());
		engine.getMainWindow().getJTable().updateUI();
		
		// Then we hide the configuration window
		engine.getConfWindow().setVisible(false);
		
		// We may have to trigger a scan if the rom folder value has changed
		// Conditions are : 
		// 1- rom folder has changed
		// 2- title pattern has changed
		if (oldRomFolder!=null &&
				(!newConf.getRomFolder().equals(oldRomFolder) 
				&& !newConf.getRomFolder().trim().isEmpty())
			|| (!oldTitlePattern.equals(newConf.getTitlePattern()))) {
			ScanSwingWorker scanSW = new ScanSwingWorker(engine, 
					engine.getScanWindow().getJProgressBar(), 
					engine.getScanWindow().getJTextArea(),
					null);
			engine.getScanWindow().setVisible(true);
			scanSW.execute();
		}
		engine.getConfWindow().getProgressPanel().stop();
		MainSwingWorker mainSW = new MainSwingWorker(engine);
		mainSW.execute();
	}

}
