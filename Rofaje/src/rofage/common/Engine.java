package rofage.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import rofage.common.object.Configuration;
import rofage.common.object.GameDB;
import rofage.common.object.GlobalConfiguration;
import rofage.common.rename.RenameSwingWorker;
import rofage.common.scan.ScanSwingWorker;
import rofage.common.update.UpdateSwingWorker;
import rofage.ihm.MainWindow;
import rofage.ihm.Messages;
import rofage.ihm.conf.ConfWindow;
import rofage.ihm.rename.RenameWindow;
import rofage.ihm.scan.ScanWindow;
import rofage.ihm.update.UpdateWindow;

public class Engine {
	private UpdateWindow updateWindow = null;
	private ConfWindow confWindow = null;
	private ScanWindow scanWindow = null;
	private MainWindow mainWindow = null;
	private RenameWindow renameWindow = null;
	
	private GlobalConfiguration globalConf = null;
	private GameDB gameDB = null;
	
	private UpdateSwingWorker updateSW = null;
	private ScanSwingWorker scanSW = null;
	private RenameSwingWorker renameSW = null;
	
	private boolean confSaved = false;
	
	public Engine () {
		// We try to load the gameDatabase
		gameDB = SerializationHelper.loadGameDB();
		if (gameDB == null) {
			// If the gameDatabase does not exist, we create it
			gameDB = new GameDB();
		}
		
		startupConf();
		
		// Then we initialize the UI
		getMainWindow().setVisible(true);
		
		// Let's see whether we should launch an update
		startupUpdate();
	}
		
	/**
	 * Checks whether an update should be launched
	 *
	 */
	private void startupUpdate () {
		// We browse each config file to see if we have to update it
		// We store each configuration to be update in the List listConfToUpdate
		List<Configuration> listConfToUpdate = new ArrayList<Configuration>();
		Iterator<Configuration> iterConfigs = getGlobalConf().getMapDatConfigs().values().iterator();
		while (iterConfigs.hasNext()) {
			Configuration conf = iterConfigs.next();
			if (conf.isUpdateAtStartup()) {
				listConfToUpdate.add(conf);
			}
		}
		
		// Let's update those configs !
		if (listConfToUpdate.size()>0) {
			// We create the UpdateSW
			getUpdateWindow().setVisible(true);
			updateSW = new UpdateSwingWorker(this, listConfToUpdate);
			updateSW.execute();
		}
	}
	
	/**
	 * Startup configuration routine
	 * It tries to load the configuration, if unsucessful, it creates it
	 */
	private void startupConf () {
		globalConf = SerializationHelper.loadGlobalConfiguration();
		if (globalConf==null) {
			// We need to create the configuration
			JOptionPane.showMessageDialog(null, Messages.getString("Engine.0"), Messages.getString("Engine.1"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
			// We create the global configuration object
			globalConf = new GlobalConfiguration();
			// Then we show the configuration window so it gets populated
			getConfWindow().setVisible(true);
		}
	}
	

	public ConfWindow getConfWindow() {
		if (confWindow==null) {
			confWindow = new ConfWindow(this);
		}
		return confWindow;
	}

	public ScanWindow getScanWindow() {
		if (scanWindow==null) {
			scanWindow = new ScanWindow(this);
		}
		return scanWindow;
	}

	public UpdateWindow getUpdateWindow() {
		if (updateWindow==null) {
			updateWindow = new UpdateWindow(this);
		}
		return updateWindow;
	}

	public MainWindow getMainWindow() {
		if (mainWindow==null) {
			mainWindow = new MainWindow(this);
		}
		return mainWindow;
	}

	public GlobalConfiguration getGlobalConf() {
		return globalConf;
	}

	public GameDB getGameDB() {
		return gameDB;
	}

	public UpdateSwingWorker getUpdateSW() {
		return updateSW;
	}

	public void setUpdateSW(UpdateSwingWorker updateSW) {
		this.updateSW = updateSW;
	}

	public boolean isConfSaved() {
		return confSaved;
	}

	public void setConfSaved(boolean confSaved) {
		this.confSaved = confSaved;
	}

	public ScanSwingWorker getScanSW() {
		return scanSW;
	}

	public void setScanSW(ScanSwingWorker scanSW) {
		this.scanSW = scanSW;
	}

	public RenameWindow getRenameWindow() {
		if (renameWindow==null) {
			renameWindow = new RenameWindow(this);
		}
		return renameWindow;
	}

	public void setRenameWindow(RenameWindow renameWindow) {
		this.renameWindow = renameWindow;
	}

	public RenameSwingWorker getRenameSW() {
		return renameSW;
	}

	public void setRenameSW(RenameSwingWorker renameSW) {
		this.renameSW = renameSW;
	}
}
