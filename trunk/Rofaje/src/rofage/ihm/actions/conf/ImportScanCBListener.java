package rofage.ihm.actions.conf;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import rofage.ihm.windows.conf.PanelImport;

/**
 * Manages the UI changes on the import panel for the Scan Check box
 * @author pierrot
 *
 */
public class ImportScanCBListener implements ItemListener {
	private PanelImport panelImport;

	public ImportScanCBListener (PanelImport panelImport) {
		this.panelImport = panelImport;
	}
	
	/**
	 * If the scan checkbox is unselected, we have to uncheck the rename CB
	 * and disable it
	 */
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange()==ItemEvent.DESELECTED) {
			// We uncheck the rename CB and disable it
			panelImport.getCBRename().setSelected(false);
			panelImport.getCBRename().setEnabled(false);
			panelImport.getCBClean().setSelected(false);
			panelImport.getCBClean().setEnabled(false);
		} else {
			panelImport.getCBRename().setEnabled(true);
			panelImport.getCBClean().setEnabled(true);
		}
	}

}
