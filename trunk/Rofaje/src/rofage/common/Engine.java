package rofage.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.swing.JOptionPane;

import rofage.common.clean.CleanSwingWorker;
import rofage.common.compress.CompressSwingWorker;
import rofage.common.export.ExportSwingWorker;
import rofage.common.object.Configuration;
import rofage.common.object.GameDB;
import rofage.common.object.GlobalConfiguration;
import rofage.common.rename.RenameSwingWorker;
import rofage.common.scan.ScanSwingWorker;
import rofage.common.update.ImportSwingWorker;
import rofage.common.update.UpdateSwingWorker;
import rofage.ihm.MainWindow;
import rofage.ihm.Messages;
import rofage.ihm.clean.CleanWindow;
import rofage.ihm.compress.CompressWindow;
import rofage.ihm.conf.ConfWindow;
import rofage.ihm.export.ExportWindow;
import rofage.ihm.rename.ImportWindow;
import rofage.ihm.rename.RenameWindow;
import rofage.ihm.scan.ScanWindow;
import rofage.ihm.update.UpdateWindow;

public class Engine {
	private UpdateWindow updateWindow 	= null;
	private ConfWindow confWindow 		= null;
	private ScanWindow scanWindow 		= null;
	private MainWindow mainWindow 		= null;
	private RenameWindow renameWindow 	= null;
	private CleanWindow cleanWindow 	= null;
	private ExportWindow exportWindow 	= null;
	private CompressWindow compressWindow = null;
	private ImportWindow importWindow 	= null;
	
	private GlobalConfiguration globalConf = null;
	private GameDB gameDB = null;
	
	private UpdateSwingWorker updateSW 	= null;
	private ScanSwingWorker scanSW 		= null;
	private RenameSwingWorker renameSW 	= null;
	private CleanSwingWorker cleanSW 	= null;
	private ExportSwingWorker exportSW 	= null;
	private CompressSwingWorker compressSW = null;
	private ImportSwingWorker importSW 	= null;
		
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
	
	public void changeLanguage() {
		if (mainWindow!=null) {
			mainWindow.dispose();
			mainWindow = null;
		}
		if (updateWindow!=null) {
			updateWindow.dispose();
			updateWindow = null;
		}
		if (confWindow!=null) {
			confWindow.dispose();
			confWindow = null;
		}
		if (scanWindow!=null) {
			scanWindow.dispose();
			scanWindow = null;
		}
		if (renameWindow!=null) {
			renameWindow.dispose();
			renameWindow = null;
		}
		if (cleanWindow!=null) {
			cleanWindow.dispose();
			cleanWindow = null;
		}
		Consts.reloadConsts();
		getMainWindow().setVisible(true);
		
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
			updateSW = new UpdateSwingWorker(this, listConfToUpdate, getUpdateWindow().getJProgressBar(),
					getUpdateWindow().getJTextArea());
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
			Locale.setDefault(globalConf.getSelectedLocale());
			// Then we show the configuration window so it gets populated
			getConfWindow().setVisible(true);
		} else {
			Locale.setDefault(globalConf.getSelectedLocale());
		}
		// We have to reload the Consts class to update the static values with the good locale
		Consts.reloadConsts();
		
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

	public CleanWindow getCleanWindow() {
		if (cleanWindow==null) {
			this.cleanWindow = new CleanWindow(this);
		}
		return cleanWindow;
	}

	public void setCleanWindow(CleanWindow cleanWindow) {
		this.cleanWindow = cleanWindow;
	}

	public CleanSwingWorker getCleanSW() {
		return cleanSW;
	}

	public void setCleanSW(CleanSwingWorker cleanSW) {
		this.cleanSW = cleanSW;
	}

	public ExportSwingWorker getExportSW() {
		return exportSW;
	}

	public void setExportSW(ExportSwingWorker exportSW) {
		this.exportSW = exportSW;
	}

	public ExportWindow getExportWindow() {
		if (exportWindow==null) {
			exportWindow = new ExportWindow(this);
		}
		return exportWindow;
	}

	public CompressWindow getCompressWindow() {
		if (compressWindow==null) {
			compressWindow = new CompressWindow(this);
		}
		return compressWindow;
	}

	public CompressSwingWorker getCompressSW() {
		return compressSW;
	}

	public void setCompressSW(CompressSwingWorker compressSW) {
		this.compressSW = compressSW;
	}

	public ImportSwingWorker getImportSW() {
		return importSW;
	}

	public void setImportSW(ImportSwingWorker importSW) {
		this.importSW = importSW;
	}

	public ImportWindow getImportWindow() {
		if (importWindow==null) {
			importWindow = new ImportWindow();
		}
		return importWindow;
	}
}
