package rofage.ihm.actions.common;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import rofage.common.Engine;
import rofage.common.MainSwingWorker;
import rofage.common.object.Configuration;
import rofage.ihm.helper.ComboFilterHelper;

public class ChangeConfInMainListener implements ItemListener {
	
	private Engine engine = null;
	
	public ChangeConfInMainListener (Engine engine) {
		this.engine = engine;
	}

	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			// When the user change the item in the combo box 
			// We have to change the displayed configuration
			String newConfName = (String)engine.getMainWindow().getMainMenuBar().getComboConf().getSelectedItem();
			Configuration newConf = engine.getGlobalConf().getMapDatConfigs().get(newConfName);
						
			engine.getGlobalConf().setSelectedConf(newConf);
			
			MainSwingWorker mainSW = new MainSwingWorker(engine);
			mainSW.execute();
			
			// We also have to reinit the filters values
			ComboFilterHelper.setFilterLanguage(engine.getMainWindow().getComboLanguage());
			ComboFilterHelper.setFilterLocation(engine, engine.getMainWindow().getComboLocation());
			ComboFilterHelper.setFilterSize(engine, engine.getMainWindow().getComboRomSize());
			
			// We should also change the size of the pictures !
			engine.getMainWindow().getJPanelImage1().setPreferredSize(new Dimension(engine.getGlobalConf().getSelectedConf().getScreenshotWidth(), engine.getGlobalConf().getSelectedConf().getScreenshotHeight()));
			engine.getMainWindow().getJPanelImage2().setPreferredSize(new Dimension(engine.getGlobalConf().getSelectedConf().getScreenshotWidth(), engine.getGlobalConf().getSelectedConf().getScreenshotHeight()));
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
