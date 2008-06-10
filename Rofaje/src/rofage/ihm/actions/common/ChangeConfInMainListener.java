package rofage.ihm.actions.common;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import rofage.common.Engine;
import rofage.common.MainSwingWorker;
import rofage.common.object.Configuration;

public class ChangeConfInMainListener implements ItemListener {
	
	private Engine engine = null;
	
	public ChangeConfInMainListener (Engine engine) {
		this.engine = engine;
	}

	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			// When the user change the item in the combo box 
			// We have to change the displayed configuration
			String newConfName = (String)engine.getMainWindow().getComboConf().getSelectedItem();
			Configuration newConf = engine.getGlobalConf().getMapDatConfigs().get(newConfName);
						
			engine.getGlobalConf().setSelectedConf(newConf);
			
			MainSwingWorker mainSW = new MainSwingWorker(engine);
			mainSW.execute();
		} else if (e.getStateChange() == ItemEvent.DESELECTED) {
			// It is possible we removed a conf
			// So we have to check if it was the last one to clean up the ui
			if (engine.getGlobalConf().getMapDatConfigs().size()==0) {
				engine.getGlobalConf().setSelectedConf(null);
				MainSwingWorker mainSW = new MainSwingWorker(engine);
				mainSW.execute();
			}
		}
	}

}
