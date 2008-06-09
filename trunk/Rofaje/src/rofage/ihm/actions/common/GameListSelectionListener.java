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
			engine.getMainWindow().getJPanelImage1().loadImage(Consts.HOME_FOLDER+File.separator+conf.getImageFolder()+File.separator+game.getReleaseNb()+"a.png");
			engine.getMainWindow().getJPanelImage2().loadImage(Consts.HOME_FOLDER+File.separator+conf.getImageFolder()+File.separator+game.getReleaseNb()+"b.png");
			
			// We update the game infos
			StringBuffer str = new StringBuffer();
			str.append(game.getTitle()).append("\n\n");
			str.append("Release : ").append(game.getReleaseNb()).append("\n");
			str.append("Taille de la rom : ").append(game.getRomSize()).append("\n");
			str.append("CRC : ").append(game.getCrc()).append("\n");
			str.append("Origine : ").append(GameDisplayHelper.getLocation(game)).append("\n");
			str.append("Langue(s) : ").append(GameDisplayHelper.getLanguage(game)).append("\n");
			str.append("Editeur : ").append(game.getPublisher()).append("\n");
			str.append("Groupe : ").append(game.getSourceRom()).append("\n");
			
			engine.getMainWindow().getJTextPane().setText(str.toString());
			
		}
	} 
}
