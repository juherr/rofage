package rofage.common.update;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import rofage.common.Engine;
import rofage.common.clean.CleanSwingWorker;
import rofage.common.files.FileToolkit;
import rofage.common.object.Configuration;
import rofage.common.object.Game;
import rofage.common.object.GlobalConfiguration;
import rofage.common.rename.RenameSwingWorker;
import rofage.common.scan.ScanSwingWorker;
import rofage.ihm.Messages;
import de.schlichtherle.io.ArchiveException;
import de.schlichtherle.io.File;

public class ImportSwingWorker extends SwingWorker<Integer, String> {
	private List<String> listPaths; // Paths to the file to import
	private JProgressBar jProgressBar;
	private JTextArea jTextArea;
	private Engine engine;
	private Configuration selConf;
	
	/**
	 * Constructs a SW to update the configuration files contained in listConfToUpdate
	 * @param engine
	 * @param listConfToUpdate : List<Configuration>
	 */
	public ImportSwingWorker (Engine engine, List<String> listPaths, 
			JProgressBar jProgressBar, JTextArea jTextArea) {
		this.engine = engine;
		this.listPaths = listPaths;
		this.jProgressBar = jProgressBar;
		this.jTextArea = jTextArea;
		this.selConf = engine.getGlobalConf().getSelectedConf();
		
		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if("progress".equals(evt.getPropertyName())) { //$NON-NLS-1$
					getJProgressBar().setValue((Integer) evt.getNewValue());
				}
            }
		});
	}
	
	@Override
    protected void process(List<String> strings) {
        /* Affichage des publications reçues dans le textarea. */
		if (jTextArea != null) {
	        for(String s : strings)
	            jTextArea.append(s + '\n');
		}
    }
	
	@Override
	protected Integer doInBackground() throws Exception {
		engine.getImportWindow().setVisible(true);
		setProgress(0);
		// First we clean up the list 
		publish (Messages.getString("ImportSwingWorker.0")); //$NON-NLS-1$
					
		// We copy the file to the rom folder
		cleanUpListPathsAndCopy();
		
		// We scan those files (and only them)
		// The given file paths may contain archive file, we have to scan inside those files to get ALL possible files
		// with the Virtual FileSystem
		listPaths = getAllFilePaths(listPaths);
		if (selConf.isImportScan()) {
			engine.setScanSW(new ScanSwingWorker(engine, jProgressBar, jTextArea, listPaths));
			engine.getScanSW().execute();
		
			// We wait for the scanSW to complete
			List<Game> listFoundGames = engine.getScanSW().get();
			
			if (selConf.isImportRename()) {
				// Now we may have to rename those files
				engine.setRenameSW(new RenameSwingWorker(engine,
						jProgressBar,
						jTextArea,
						listFoundGames));
				engine.getRenameSW().execute();
				engine.getRenameSW().get();
			}
			
			if (selConf.isImportClean()) {
				// Now we clean those files
				engine.setCleanSW(new CleanSwingWorker(engine,
						jProgressBar,
						jTextArea,
						listFoundGames));
				engine.getCleanSW().execute();
				engine.getCleanSW().get();
			}
		}
		
		publish (Messages.getString("ImportSwingWorker.1"));		 //$NON-NLS-1$
		//engine.getMainWindow().getJTable().updateUI();
		setProgress(100);
		
		return 0;
	}
	
	/**
	 * Look inside the listPaths and search for archive files
	 * If archive files are found, we remove this entry from the list
	 * and we add the files contained in this archive to the list
	 *
	 * @return list<String> modified list of all paths
	 */
	private List<String> getAllFilePaths (List<String> listPaths) {
		Iterator<String> iterPaths = listPaths.iterator();
		List<String> listAddPaths = new ArrayList<String>();
		while (iterPaths.hasNext()) {
			String path = iterPaths.next();
			File f = new File(path);
			if (f.isArchive()) {
				// We remove this entry from the list
				iterPaths.remove();
				// We look inside this file
				String [] tabPaths = f.list();
				for (int i=0; i<tabPaths.length; i++) {
					listAddPaths.add(path+File.separator+tabPaths[i]);
				}
			}
		}
		if (listAddPaths.size()>0) listAddPaths = getAllFilePaths(listAddPaths); 
		listPaths.addAll(listAddPaths);
		return listPaths;
	}
	
	/**
	 * Cleans the list of paths from the file which cannot possibly be a rom
	 * for this collection
	 * If the file is valid, we copy it to the rom directory
	 *
	 */
	private void cleanUpListPathsAndCopy() {
		// First we get all the file paths (we look inside directories, but not inside archives)
		List<String> allPaths = new ArrayList<String>();
		Iterator<String> iterPaths = listPaths.iterator();
		while (iterPaths.hasNext()) {
			String path = iterPaths.next();
			allPaths.addAll(FileToolkit.getAllFilePaths(path));
		}
		
		// Now we have all the possible filepaths
		int nbTotalFiles = allPaths.size();
		int nbFiles = 0;
		publish(Messages.getString("ImportSwingWorker.2")+nbTotalFiles+Messages.getString("ImportSwingWorker.3")); //$NON-NLS-1$ //$NON-NLS-2$
		setProgress(0);
		// We purge the list of filepaths, we add the new destination while copying them
		listPaths.clear();
		iterPaths = allPaths.iterator();
		while (iterPaths.hasNext()) {
			String path = iterPaths.next();
			File f = new File(path);
			// We check if it's a valid files
			String ext = FileToolkit.getFileExtension(path).toLowerCase();
			if (!GlobalConfiguration.allowedCompressedExtensions.contains(ext) 
					&& !selConf.getAllowedExtensions().contains(ext)) {
				// This file is invalid for this configuration we remove it
				iterPaths.remove();
				publish (f.getName()+Messages.getString("ImportSwingWorker.4")); //$NON-NLS-1$
			} else {
				// This is a valid file for this configuration we copy it to the rom folder
				publish(Messages.getString("ImportSwingWorker.5")+f.getName()+Messages.getString("ImportSwingWorker.6")+selConf.getRomFolder()); //$NON-NLS-1$ //$NON-NLS-2$
				String destPath = selConf.getRomFolder()+File.separator+f.getName();
				// We MUST use archiveCopyAll copy or zip files are not Copied !
				f.archiveCopyAllTo(new File(destPath));
				listPaths.add(destPath);
			}
			nbFiles++;
			setProgress(nbFiles*100/nbTotalFiles);
		}
		try {
			File.umount(true, true, true, true);
		} catch (ArchiveException e) {
			e.printStackTrace();
		}
		publish (Messages.getString("ImportSwingWorker.7")); //$NON-NLS-1$
	}

	public JProgressBar getJProgressBar() {
		return jProgressBar;
	}
}
