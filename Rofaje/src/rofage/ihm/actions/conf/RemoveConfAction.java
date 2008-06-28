package rofage.ihm.actions.conf;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import de.schlichtherle.io.File;

import rofage.common.Engine;
import rofage.common.SerializationHelper;
import rofage.common.files.FileToolkit;
import rofage.ihm.Messages;

@SuppressWarnings("serial") //$NON-NLS-1$
public class RemoveConfAction extends AbstractAction {

	private Engine engine;
	
	public RemoveConfAction (Engine engine) {
		this.engine = engine;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		// We delete the selected configuration
		String confName = (String)engine.getConfWindow().getComboConf().getSelectedItem();
		
		// We ask confirmation
		int delete = JOptionPane.showConfirmDialog(engine.getConfWindow(), Messages.getString("RemoveConfConfirmation")+" "+confName+" ?", Messages.getString("RemoveConfConfirmationTitle"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE); //$NON-NLS-1$
		
		if (delete==JOptionPane.YES_OPTION) {
		
			// We ask if we delete the image folder
			int res = JOptionPane.showConfirmDialog(engine.getConfWindow(), Messages.getString("RemoveConfAction.1"), Messages.getString("RemoveConfAction.2"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
			
			if (res==JOptionPane.YES_OPTION) {
				// We have to delete the folder
				// We get the conf
				File imageFolder = new File(engine.getGlobalConf().getSelectedConf().getImageFolder());
				FileToolkit.deleteDirectory(imageFolder);
			}
			
			engine.setConfSaved(true);
			// We delete the conf from the main engine
			engine.getGlobalConf().getMapDatConfigs().remove(confName);
			// We delete the conf entry from the combos
			engine.getConfWindow().getComboConf().removeItem(confName);
			engine.getMainWindow().getMainMenuBar().getComboConf().removeItem(confName);
			
			// We delete the related gameDB
			engine.getGameDB().getGameCollections().remove(confName);
			
			// We save the new conf and the gameDB objects
			SerializationHelper.saveGameDB(engine.getGameDB());
			SerializationHelper.saveGlobalConfiguration(engine.getGlobalConf());
			
			// If it was the last config, we remove the remove button
			if (engine.getGlobalConf().getMapDatConfigs().size()==0) {
				engine.getConfWindow().getButtonRemoveConf().setVisible(false);
				engine.getConfWindow().getJTabbedPane().setVisible(false);
			}
			
			// We update the UI
			engine.getConfWindow().update(engine.getConfWindow().getGraphics());
			engine.getMainWindow().update(engine.getMainWindow().getGraphics());
		}
	}
}
