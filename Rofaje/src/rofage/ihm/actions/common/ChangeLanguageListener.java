package rofage.ihm.actions.common;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Locale;

import rofage.common.Engine;
import rofage.common.SerializationHelper;

public class ChangeLanguageListener implements ItemListener {
	
	private Engine engine = null;
	
	public ChangeLanguageListener (Engine engine) {
		this.engine = engine;
	}
	
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			// We change the locale of the JVM
			if (engine.getMainWindow().getMainMenuBar().getMenuConf().getMLanguage().getMItemLnEn().isSelected()) {
				engine.getGlobalConf().setSelectedLocale(Locale.ENGLISH);
			} else if (engine.getMainWindow().getMainMenuBar().getMenuConf().getMLanguage().getMItemLnFr().isSelected()) {
				engine.getGlobalConf().setSelectedLocale(Locale.FRENCH);
			} else if (engine.getMainWindow().getMainMenuBar().getMenuConf().getMLanguage().getMItemLnDe().isSelected()) {
				engine.getGlobalConf().setSelectedLocale(Locale.GERMAN);
			}
			Locale.setDefault(engine.getGlobalConf().getSelectedLocale());
			SerializationHelper.saveGlobalConfiguration(engine.getGlobalConf());
			
			engine.changeLanguage();
									
		}
	}

}
