package rofage.common.update;

import java.io.File;

import javax.swing.JProgressBar;

import rofage.common.Consts;
import rofage.common.Engine;
import rofage.common.files.FileToolkit;
import rofage.common.helper.GameDisplayHelper;
import rofage.common.object.Configuration;
import rofage.common.object.Game;
import rofage.common.url.URLToolkit;
import rofage.ihm.GameListTableModel;

public class UpdateSingleImageSwingWorker extends DownloadSwingWorker {
	private int type;
	private Game game;
		
	/**
	 * Constructs a SW to update the configuration files contained in listConfToUpdate
	 * @param engine
	 * @param game
	 * @param firstImage
	 */
	public UpdateSingleImageSwingWorker (Engine engine, Game game, int type, JProgressBar jProgressBar) {
		super(engine, jProgressBar, null);
		this.engine = engine;
		this.game = game;
		this.type = type;
	}
	
	@Override
	protected Integer doInBackground() throws Exception {
		Configuration conf = engine.getGlobalConf().getSelectedConf();
		
		String folder = "";
		String baseUrl = "";
		switch (type) {
			case URLToolkit.TYPE_ICON : 
				folder = Consts.HOME_FOLDER+File.separator+conf.getImageFolder()+File.separator+Consts.ICO_FOLDER;
				baseUrl = conf.getIcoUrl();
				break;
			case URLToolkit.TYPE_IMAGE_1 : 
				folder = Consts.HOME_FOLDER+File.separator+conf.getImageFolder();
				baseUrl = conf.getImageUrl();
				break;
			case URLToolkit.TYPE_IMAGE_2 : 
				folder = Consts.HOME_FOLDER+File.separator+conf.getImageFolder();
				baseUrl = conf.getImageUrl();
				break;
		}
		
		FileToolkit.checkAndCreateFolder(folder);
		
		String fileName = GameDisplayHelper.constructFileName(game, type);
		checkAndDownloadFile(folder, fileName, 
				game, type, baseUrl);
		
		// We update the window if we haven't changed the game selected !
		int index = engine.getMainWindow().getJTable().getSelectedRow();
		Game selGame = ((GameListTableModel) engine.getMainWindow().getJTable().getModel()).getGameAt(index);
		if (game.getTitle().equals(selGame.getTitle())) {
			if (type==URLToolkit.TYPE_IMAGE_1) {
				engine.getMainWindow().getJPanelImage1().loadImage(Consts.HOME_FOLDER+File.separator+conf.getImageFolder()+File.separator+game.getReleaseNb()+"a.png"); //$NON-NLS-1$
			} else if (type==URLToolkit.TYPE_IMAGE_2) {
				engine.getMainWindow().getJPanelImage2().loadImage(Consts.HOME_FOLDER+File.separator+conf.getImageFolder()+File.separator+game.getReleaseNb()+"b.png"); //$NON-NLS-1$
			} 
		}
		engine.getMainWindow().getJTable().updateUI();
		return 0;
	}
}
