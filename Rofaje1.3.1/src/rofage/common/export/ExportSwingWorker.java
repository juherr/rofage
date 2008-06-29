package rofage.common.export;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rofage.common.Engine;
import rofage.common.object.Game;
import rofage.common.swingworker.StoppableSwingWorker;
import rofage.ihm.GameListTableModel;
import rofage.ihm.Messages;
import de.schlichtherle.io.ArchiveException;
import de.schlichtherle.io.File;

public class ExportSwingWorker extends StoppableSwingWorker<Integer, String> {
		
	private String destPath;
	
	public ExportSwingWorker (Engine engine, File destFile) {
		this.engine = engine;
		this.destPath = destFile.getAbsolutePath()+File.separator;
				
		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if("progress".equals(evt.getPropertyName())) { //$NON-NLS-1$
					getEngine().getExportWindow().getJProgressBar().setValue((Integer) evt.getNewValue());
				}
            }
		});
	}
	
	@Override
	protected Integer doInBackground() throws Exception {
		// Now we scan folders
		engine.getExportWindow().getButtonStop().setEnabled(true);
		setProgress(0);
		engine.getExportWindow().setVisible(true);
		publish(Messages.getString(Messages.getString("ExportSwingWorker.0"))); //$NON-NLS-1$
		
		int[] selRows = engine.getMainWindow().getJTable().getSelectedRows();
		GameListTableModel model = (GameListTableModel)engine.getMainWindow().getJTable().getModel();
		List<Game> listGamesToExport = new ArrayList<Game>();
		for (int i=0; i<selRows.length; i++) {
			listGamesToExport.add(model.getGameAt(selRows[i]));
		}
				
		publish (listGamesToExport.size()+Messages.getString("ExportSwingWorker.1")); //$NON-NLS-1$
		
		exportGamesInList(listGamesToExport);
		
		setProgress(100);
		publish (Messages.getString("ExportSwingWorker.2")); //$NON-NLS-1$
		engine.getExportWindow().getButtonStop().setEnabled(false);	
		return 0;
	}
	
	private void exportGamesInList(List<Game> listGamesToExport)  {
		int nbFilesExported = 0;
		Iterator<Game> iterGames = listGamesToExport.iterator();
		while (iterGames.hasNext() && !stopAction) {
			Game game = iterGames.next();
			exportGame(game);
			nbFilesExported++;
			setProgress(nbFilesExported*100/listGamesToExport.size());
		}
	}
	
	private void exportGame (Game game) {
		if (!game.isGotRom()) {
			publish (Messages.getString("ExportSwingWorker.3")+game.getTitle()+Messages.getString("ExportSwingWorker.4")); //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			File file = new File(game.getContainerPath());
			if (file.isArchive()) {
				file = new File(game.getEntryFullPath());
			} 
			publish(Messages.getString("ExportSwingWorker.5")+file.getName()); //$NON-NLS-1$
			file.copyTo(new File(destPath+file.getName()));
			try {
				File.umount();
			} catch (ArchiveException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
    protected void process(List<String> strings) {
        /* Affichage des publications reçues dans le textarea. */
        for(String s : strings)
        	engine.getExportWindow().getJTextArea().append(s + '\n');
    }
}
