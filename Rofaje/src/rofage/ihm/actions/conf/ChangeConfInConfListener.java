package rofage.ihm.actions.conf;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JOptionPane;

import rofage.common.Engine;
import rofage.common.helper.ConfigurationHelper;
import rofage.common.object.Configuration;
import rofage.ihm.Messages;
import rofage.ihm.conf.ConfWindow;

public class ChangeConfInConfListener implements ItemListener {
	
	private Engine engine;
	
	public ChangeConfInConfListener (Engine engine) {
		this.engine = engine;
	}

	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
	//			 When the user change the item in the combo box 
			// We have to change the displayed configuration
			String newConfName = (String)engine.getConfWindow().getComboConf().getSelectedItem();
			Configuration newConf = engine.getGlobalConf().getMapDatConfigs().get(newConfName);
			
			// We change the values of the forms
			ConfWindow confWindow = engine.getConfWindow();
			// Scan panel
			confWindow.getFieldRomFolder().setText(newConf.getRomFolder());
			confWindow.getFieldUnknownRomFolder().setText(newConf.getRomFolderMove());
			confWindow.getCBInternalName().setSelected(newConf.isInternalName());
			confWindow.getCBmoveUnknownRoms().setSelected(newConf.isMoveUnknownRoms());
			// Rom Title panel
			confWindow.getFieldTitlePattern().setText(newConf.getTitlePattern());
			confWindow.getCBRenameInside().setSelected(newConf.isRenameInside());
			confWindow.getComboReleaseNbField().setSelectedItem(newConf.getReleaseNbField());
			// Update panel
			confWindow.getCBAutoUpdate().setSelected(newConf.isUpdateAtStartup());
			confWindow.getCBInAppUpdate().setSelected(newConf.isInAppUpdate());
			// Compression panel
			confWindow.getCBDeleteSource().setSelected(newConf.isDeleteSource());
			confWindow.getCompressSlider().setValue(newConf.getCompressValue());
			// Import panel
			confWindow.getPanelImport().getCBRename().setSelected(newConf.isImportRename());
			confWindow.getPanelImport().getCBScan().setSelected(newConf.isImportScan());
			confWindow.getPanelImport().getCBClean().setSelected(newConf.isImportClean());

			
			engine.getGlobalConf().setSelectedConf(newConf);
			
			// We update the ui
			engine.getConfWindow().update(engine.getConfWindow().getGraphics());
		}else if(e.getStateChange() == ItemEvent.DESELECTED) {
			if (!engine.isConfSaved()) {
				int res = JOptionPane.showConfirmDialog(engine.getConfWindow(), Messages.getString("ChangeConfInConfListener.0"), Messages.getString("ChangeConfInConfListener.1"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
				if (res==JOptionPane.YES_OPTION) {
					// We save the conf
					ConfigurationHelper.saveCurrentConfigurationInEngine(engine);
				}
			}
			engine.setConfSaved(false);
		}
	}
}
