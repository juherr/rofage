package rofage.common.helper;

import javax.swing.JLabel;

import rofage.common.Engine;
import rofage.common.SerializationHelper;
import rofage.common.object.Credentials;
import rofage.ihm.Messages;
import rofage.ihm.helper.IconHelper;

public abstract class SessionHelper {
	
	public static boolean isLogged (Engine engine) {
		if (engine.getGlobalConf().getCreds()!=null) return true;
		return false;
	}
	
	/**
	 * Logs out the user :
	 * - Reset the credentials and save the configuration
	 * - Update the online icon
	 */
	public static void logout (Engine engine) {
		engine.getGlobalConf().setCreds(null);
		SerializationHelper.saveGlobalConfiguration(engine.getGlobalConf());
		JLabel labelOnline = engine.getMainWindow().getMainMenuBar().getLabelOnline();
		labelOnline.setText(Messages.getString("Community.offline"));
		labelOnline.setIcon(IconHelper.getOnlineIcon(engine));
		labelOnline.setToolTipText(Messages.getString("Community.tooltip.offlineIcon"));
		// We disable the logout icon
		engine.getMainWindow().getMainMenuBar().getMenuCommunity().getMItemLogout().setEnabled(false);
		engine.getMainWindow().getMainMenuBar().getMenuCommunity().getMItemLogin().setEnabled(true);
		
		// We disable the buttons for voting
		engine.getMainWindow().getPanelRomHeader().getButtonAvgNote().setEnabled(false);
		engine.getMainWindow().getPanelRomHeader().getButtonMyNote().setEnabled(false);
	}
	
	/**
	 * Logs in the user :
	 * - save the credentials in the configuration
	 * - Indicate the username
	 * - Update the online icon
	 * @param engine
	 * @param creds
	 */
	public static void login (Engine engine, Credentials creds) {
		engine.getGlobalConf().setCreds(creds);
		SerializationHelper.saveGlobalConfiguration(engine.getGlobalConf());
		JLabel labelOnline = engine.getMainWindow().getMainMenuBar().getLabelOnline();
		labelOnline.setText(creds.getUsername());
		labelOnline.setIcon(IconHelper.getOnlineIcon(engine));
		labelOnline.setToolTipText(Messages.getString("Community.tooltip.onlineIcon")+creds.getUsername());
		engine.getMainWindow().getMainMenuBar().getMenuCommunity().getMItemLogout().setEnabled(true);
		engine.getMainWindow().getMainMenuBar().getMenuCommunity().getMItemLogin().setEnabled(false);
		
		// We enable the buttons for voting
		engine.getMainWindow().getPanelRomHeader().getButtonAvgNote().setEnabled(true);
		engine.getMainWindow().getPanelRomHeader().getButtonMyNote().setEnabled(true);
		engine.getMainWindow().update(engine.getMainWindow().getGraphics());
	}
}
