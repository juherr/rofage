package rofage.ihm.actions.conf;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import rofage.common.Engine;
import rofage.common.helper.ConfigurationHelper;
import rofage.common.helper.GameDBHelper;
import rofage.common.object.Configuration;
import rofage.common.parser.DatParser;
import rofage.ihm.Messages;

@SuppressWarnings("serial") //$NON-NLS-1$
public class AddConfAction extends AbstractAction {

	private Engine engine;
	
	public AddConfAction (Engine engine) {
		this.engine = engine;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		// Before we try to load a new file, we have to save the current options
		if (engine.getGlobalConf().getSelectedConf()!=null) {
			int res = JOptionPane.showConfirmDialog(engine.getConfWindow(), Messages.getString("AddConfAction.1"), Messages.getString("AddConfAction.2"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
			if (res==JOptionPane.YES_OPTION) {
				// We have to save the configuration for this dat
				ConfigurationHelper.saveCurrentConfigurationInEngine(engine);
			}
		}
		
		// First of all we have to choose the dat file
		int returnVal = engine.getConfWindow().getXMLChooser().showOpenDialog(engine.getConfWindow());
		
		if (returnVal==JFileChooser.APPROVE_OPTION) {
			// When a NEW xml file (dat file) is added we add its configuration to the global conf
			// First we check whether this config already exist
			File datFile = engine.getConfWindow().getXMLChooser().getSelectedFile();
			DatParser datParser = new DatParser(datFile.getAbsolutePath());
			String datFileName = datParser.getDatName();
			
			if (engine.getGlobalConf().getMapDatConfigs().containsKey(datFileName)) {
				JOptionPane.showMessageDialog(engine.getConfWindow(), Messages.getString("AddConfAction.3"), Messages.getString("AddConfAction.4"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				// We update the selected conf of the engine
				Configuration newConfig = ConfigurationHelper.createConfFromDatParser(engine, datParser);
				engine.getGlobalConf().setSelectedConf(newConfig);

//				 We also have to create the related gameDB for this configuration
				GameDBHelper.createGameCollectionInEngine(engine, datFileName, datParser);				
				// We add this conf in the combo
				engine.getConfWindow().getComboConf().addItem(datFileName);
				// We add this conf in the combo in the main window
				engine.getMainWindow().getMainMenuBar().getComboConf().addItem(datFileName);
				
				// We also add this datas in the Jtable of the main window
				//((GameListTableModel) engine.getMainWindow().getJTable().getModel()).setGameCollectionAndDatas(gameCollection);
				
				// We show the additional panes (if necessary)
				engine.getConfWindow().getJTabbedPane().setVisible(true);
				engine.getConfWindow().getPanelGlobalButtons().setVisible(true);
				
				// We also have to show the remove button in any cases
				engine.getConfWindow().getButtonRemoveConf().setVisible(true);
				
				// We select the newly created conf in the combo box
				engine.getConfWindow().getComboConf().setSelectedItem(datFileName);
				engine.getMainWindow().getMainMenuBar().getComboConf().setSelectedItem(datFileName);
				
				// We update the UI
				engine.getConfWindow().update(engine.getConfWindow().getGraphics());
				engine.getMainWindow().update(engine.getMainWindow().getGraphics());
			}
		}
	}
}
