package rofage.ihm.actions.common;

import java.io.File;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import rofage.common.Consts;
import rofage.common.Engine;
import rofage.common.helper.GameDisplayHelper;
import rofage.common.object.Configuration;
import rofage.common.object.Game;
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
			int index = engine.getMainWindow().getJTable().getSelectedRow();
			Game game = ((GameListTableModel) engine.getMainWindow().getJTable().getModel()).getGameAt(index);
			
			// We update the images
			engine.getMainWindow().getJPanelImage1().loadImage(Consts.HOME_FOLDER+File.separator+conf.getImageFolder()+File.separator+game.getReleaseNb()+"a.png"); //$NON-NLS-1$
			engine.getMainWindow().getJPanelImage2().loadImage(Consts.HOME_FOLDER+File.separator+conf.getImageFolder()+File.separator+game.getReleaseNb()+"b.png"); //$NON-NLS-1$
			
			// We update the game infos
			StringBuffer str = new StringBuffer();
			str.append(game.getTitle()).append("\n\n"); //$NON-NLS-1$
			str.append(Messages.getString("GameListSelectionListener.3")).append(game.getReleaseNb()).append("\n"); //$NON-NLS-1$ //$NON-NLS-2$
			str.append(Messages.getString("GameListSelectionListener.5")).append(game.getRomSize()).append("\n"); //$NON-NLS-1$ //$NON-NLS-2$
			str.append(Messages.getString("GameListSelectionListener.2")).append(game.getCrc()).append("\n"); //$NON-NLS-1$ //$NON-NLS-2$
			str.append(Messages.getString("GameListSelectionListener.9")).append(GameDisplayHelper.getLocation(game)).append("\n"); //$NON-NLS-1$ //$NON-NLS-2$
			str.append(Messages.getString("GameListSelectionListener.11")).append(GameDisplayHelper.getLanguage(game)).append("\n"); //$NON-NLS-1$ //$NON-NLS-2$
			str.append(Messages.getString("GameListSelectionListener.13")).append(game.getPublisher()).append("\n"); //$NON-NLS-1$ //$NON-NLS-2$
			str.append(Messages.getString("GameListSelectionListener.15")).append(game.getSourceRom()).append("\n"); //$NON-NLS-1$ //$NON-NLS-2$
			
			engine.getMainWindow().getJTextPane().setText(str.toString());
			
		}
	} 
}
