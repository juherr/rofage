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
import rofage.ihm.PanelGameInfos;
import rofage.ihm.PanelRomHeader;
import rofage.ihm.helper.IconHelper;

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
						
			// If we asked for downloading images we have to do it here
			if (conf.isInAppUpdate() && index!=-1) {
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
			
			if (index!=-1) {
			
				// We update the images
				engine.getMainWindow().getJPanelImage1().loadImage(Consts.HOME_FOLDER+File.separator+conf.getImageFolder()+File.separator+game.getReleaseNb()+"a.png"); //$NON-NLS-1$
				engine.getMainWindow().getJPanelImage2().loadImage(Consts.HOME_FOLDER+File.separator+conf.getImageFolder()+File.separator+game.getReleaseNb()+"b.png"); //$NON-NLS-1$
				
				// We update the game infos
				PanelGameInfos p = engine.getMainWindow().getPanelGameInfos();
				p.getLabelReleaseNb().setText(game.getReleaseNb());
				p.getLabelSize().setText( Integer.parseInt(game.getRomSize())/(1024*1024)+ " MB");
				p.getLabelCRC().setText(game.getCrc().toUpperCase());
				p.getLabelOrigin().setText(GameDisplayHelper.getLocation(game));
				p.getLabelLanguage().setText(GameDisplayHelper.getLanguage(game));
				p.getLabelPublisher().setText(game.getPublisher());
				p.getLabelGroup().setText(game.getSourceRom());
				p.getLabelGenre().setText(game.getGenre());
				p.getCBWifi().setSelected(game.getWifi()==Boolean.TRUE);
				p.getCBWifi().setVisible(true);
				p.updateUI();			
				
				// We update the rom header panel
				PanelRomHeader header = engine.getMainWindow().getPanelRomHeader();
				header.getLabelTitle().setText(GameDisplayHelper.buildTitle(game, conf.getTitlePattern()));
				header.getLabelTitle().setIcon(IconHelper.getRomIcon(game, conf));
				header.getLabelIconCleanDump().setIcon(IconHelper.getCleanDumpIcon(game));
				header.getLabelIconWifi().setIcon(IconHelper.getWifiIcon(game));
				header.updateUI();
			}
		}
	} 
}
