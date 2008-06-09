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

@SuppressWarnings("serial")
public class AddConfAction extends AbstractAction {

	private Engine engine;
	
	public AddConfAction (Engine engine) {
		this.engine = engine;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		// Before we try to load a new file, we have to save the current options
		if (engine.getGlobalConf().getSelectedConf()!=null) {
			int res = JOptionPane.showConfirmDialog(engine.getConfWindow(), "Voulez-vous sauvegarder vos paramètres pour ce DAT avant d'en rajouter un ?", "Sauvegarde des paramètres", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
				JOptionPane.showMessageDialog(engine.getConfWindow(), "Ce fichier dat est déjà configuré.\n\nVous pouvez modifier sa configuration directement.", "Erreur", JOptionPane.ERROR_MESSAGE);
			} else {
				// We update the selected conf of the engine
				Configuration newConfig = ConfigurationHelper.createConfFromDatParser(engine, datParser);
				engine.getGlobalConf().setSelectedConf(newConfig);
				// We add this conf in the combo
				engine.getConfWindow().getComboConf().addItem(datFileName);
				// We add this conf in the combo in the main window
				engine.getMainWindow().getComboConf().addItem(datFileName);
				
				// We also have to create the related gameDB for this configuration
				GameDBHelper.createGameCollectionInEngine(engine, datFileName, datParser);
				
				// We also add this datas in the Jtable of the main window
				//((GameListTableModel) engine.getMainWindow().getJTable().getModel()).setGameCollectionAndDatas(gameCollection);
				
				// We add the additional panes if necessary
				if (!engine.getConfWindow().isPanesCreated()) {
					engine.getConfWindow().addAdditionalPanes();
				}
				
				// We select the newly created conf in the combo box
				engine.getConfWindow().getComboConf().setSelectedItem(datFileName);
				engine.getMainWindow().getComboConf().setSelectedItem(datFileName);
				
				// We update the UI
				engine.getConfWindow().update(engine.getConfWindow().getGraphics());
				engine.getMainWindow().update(engine.getMainWindow().getGraphics());
			}
		}
	}
}
