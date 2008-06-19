package rofage.ihm.actions.common;

import java.io.File;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import rofage.common.Consts;
import rofage.common.Engine;
import rofage.common.helper.GameDisplayHelper;
import rofage.common.object.Configuration;
import rofage.common.object.Game;
import rofage.common.update.UpdateSingleImageSwingWorker;
import rofage.common.url.URLToolkit;
import rofage.ihm.GameListTableModel;
import rofage.ihm.Messages;

public class GameListSelectionListener implements ListSelectionListener {
	private Engine engine = null;
	
	public GameListSelectionListener (Engine engine) {
		this.engine = engine;
	}
	
	public void valueChanged(ListSelectionEvent e) {
		// We get the game
		Configuration conf = engine.getGlobalConf().getSelectedConf();
		if (!e.getValueIsAdjusting()) {
			e.getSource();
			int index = engine.getMainWindow().getJTable().getSelectedRow();
			Game game = ((GameListTableModel) engine.getMainWindow().getJTable().getModel()).getGameAt(index);
			
			// If we asked for downloading images we have to do it here
			if (conf.isInAppUpdate()) {
				// Does the image files exist ?
				String folderPath = Consts.HOME_FOLDER+File.separator+conf.getImageFolder()+File.separator;
				File image1 = new File (folderPath+game.getReleaseNb()+"a.png");
				File image2 = new File (folderPath+game.getReleaseNb()+"b.png");
				File icon = new File (folderPath+Consts.ICO_FOLDER+File.separator+GameDisplayHelper.constructFileName(game, URLToolkit.TYPE_ICON));
				
				if (!image1.exists()) {
					// We download this image
					UpdateSingleImageSwingWorker sw = new UpdateSingleImageSwingWorker(engine, game, URLToolkit.TYPE_IMAGE_1, engine.getMainWindow().getProgressBarImage());
					sw.execute();
				}
				if (!image2.exists()) {
					// We download this image
					UpdateSingleImageSwingWorker sw = new UpdateSingleImageSwingWorker(engine, game, URLToolkit.TYPE_IMAGE_2, engine.getMainWindow().getProgressBarImage());
					sw.execute();
				}
				if (!icon.exists()) {
//					 We download this image
					if (conf.getIcoUrl()!=null && !conf.getIcoUrl().isEmpty()) {
						UpdateSingleImageSwingWorker sw = new UpdateSingleImageSwingWorker(engine, game, URLToolkit.TYPE_ICON, engine.getMainWindow().getProgressBarImage());
						sw.execute();
					}
				}
				
				
			}
			
			// We update the images
			engine.getMainWindow().getJPanelImage1().loadImage(Consts.HOME_FOLDER+File.separator+conf.getImageFolder()+File.separator+game.getReleaseNb()+"a.png"); //$NON-NLS-1$
			engine.getMainWindow().getJPanelImage2().loadImage(Consts.HOME_FOLDER+File.separator+conf.getImageFolder()+File.separator+game.getReleaseNb()+"b.png"); //$NON-NLS-1$
			
			// We update the game infos
			StringBuffer str = new StringBuffer();
			str.append(game.getTitle()).append("\n\n"); //$NON-NLS-1$
			if (game.getReleaseNb()!=null && !game.getReleaseNb().isEmpty())
				str.append(Messages.getString("GameListSelectionListener.3")).append(game.getReleaseNb()).append("\n"); //$NON-NLS-1$ //$NON-NLS-2$
			if (game.getRomSize()!=null && !game.getRomSize().isEmpty())
				str.append(Messages.getString("GameListSelectionListener.5")).append(game.getRomSize()).append("\n"); //$NON-NLS-1$ //$NON-NLS-2$
			if (game.getCrc()!=null && !game.getCrc().isEmpty())
				str.append(Messages.getString("GameListSelectionListener.2")).append(game.getCrc()).append("\n"); //$NON-NLS-1$ //$NON-NLS-2$
			if (game.getLocation()!=null && !game.getLocation().isEmpty())
				str.append(Messages.getString("GameListSelectionListener.9")).append(GameDisplayHelper.getLocation(game)).append("\n"); //$NON-NLS-1$ //$NON-NLS-2$
			if (game.getLanguage()!=null && !game.getLanguage().isEmpty())
				str.append(Messages.getString("GameListSelectionListener.11")).append(GameDisplayHelper.getLanguage(game)).append("\n"); //$NON-NLS-1$ //$NON-NLS-2$
			if (game.getPublisher()!=null && !game.getPublisher().isEmpty())
				str.append(Messages.getString("GameListSelectionListener.13")).append(game.getPublisher()).append("\n"); //$NON-NLS-1$ //$NON-NLS-2$
			if (game.getSourceRom()!=null && !game.getSourceRom().isEmpty())
				str.append(Messages.getString("GameListSelectionListener.15")).append(game.getSourceRom()).append("\n"); //$NON-NLS-1$ //$NON-NLS-2$
			
			engine.getMainWindow().getJTextPane().setText(str.toString());
			
		}
	} 
}
