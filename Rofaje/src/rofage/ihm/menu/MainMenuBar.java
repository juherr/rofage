package rofage.ihm.menu;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.util.Iterator;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuBar;

import rofage.common.Engine;
import rofage.common.helper.SessionHelper;
import rofage.common.object.Configuration;
import rofage.ihm.MainWindow;
import rofage.ihm.Messages;
import rofage.ihm.actions.common.ChangeConfInMainListener;
import rofage.ihm.helper.IconHelper;

@SuppressWarnings("serial")
public class MainMenuBar extends JMenuBar {
	private Engine engine;
	private MainWindow mainWindow;
	
	private MenuConf menuConf			= null;
	private MenuRomMgt menuRomMgt 		= null;
	private MenuCommunity menuCommunity = null;
	private MenuAbout menuAbout 		= null;
	private JLabel labelOnline			= null;
	private JComboBox comboConf			= null;

	public MainMenuBar (Engine engine, MainWindow mainWindow) {
		this.engine = engine;
		this.mainWindow = mainWindow;
		
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		add(getMenuRomMgt());
		add(getMenuConf());
		add(getMenuCommunity());
		add(getMenuAbout());
		add(getComboConf());
		add(Box.createRigidArea(new Dimension(10,10)));
		add(getLabelOnline());
		add(Box.createRigidArea(new Dimension(10,10)));
		
		// We enable / disable the login / logout menu items
		if (engine.getGlobalConf().getCreds()!=null) {
			getMenuCommunity().getMItemLogout().setEnabled(true);
			getMenuCommunity().getMItemLogin().setEnabled(false);
		} else {
			getMenuCommunity().getMItemLogout().setEnabled(false);
			getMenuCommunity().getMItemLogin().setEnabled(true);
		}
	}
	
	public JLabel getLabelOnline () {
		if (labelOnline==null) {
			labelOnline = new JLabel();
			if (SessionHelper.isLogged(engine)) {
				String username = engine.getGlobalConf().getCreds().getUsername();
				labelOnline.setText(username);
				labelOnline.setToolTipText(Messages.getString("Community.tooltip.onlineIcon")+username);
			} else {
				labelOnline.setText(Messages.getString("Community.offline"));
				labelOnline.setToolTipText(Messages.getString("Community.tooltip.offlineIcon"));
			}
			labelOnline.setIcon(IconHelper.getOnlineIcon(engine));
		}
		return labelOnline;
	}

	public MenuConf getMenuConf() {
		if (menuConf==null) {
			menuConf = new MenuConf(engine);
		}
		return menuConf;
	}
	
	public MenuCommunity getMenuCommunity() {
		if (menuCommunity==null) {
			menuCommunity = new MenuCommunity(engine);
		}
		return menuCommunity;
	}

	public MenuRomMgt getMenuRomMgt() {
		if (menuRomMgt==null) {
			menuRomMgt = new MenuRomMgt(engine);
		}
		return menuRomMgt;
	}

	public MenuAbout getMenuAbout() {
		if (menuAbout==null) {
			menuAbout = new MenuAbout(engine, mainWindow);
		}
		return menuAbout;
	}

	public JComboBox getComboConf() {
		if (comboConf==null) {
			comboConf = new JComboBox();
			// We add the items into the list
			Iterator<Configuration> iterConfigs = engine.getGlobalConf().getMapDatConfigs().values().iterator();
			while (iterConfigs.hasNext()) {
				String curConfName = iterConfigs.next().getConfName();
				comboConf.addItem(curConfName);
			}
			Configuration selectedConf = engine.getGlobalConf().getSelectedConf();
			if (selectedConf!=null) {
				String selectedConfName = selectedConf.getConfName();
				comboConf.setSelectedItem(selectedConfName);
			}
			comboConf.addItemListener(new ChangeConfInMainListener(engine));
			comboConf.setVisible(true);
		}
		return comboConf;
	}
	
	
}
