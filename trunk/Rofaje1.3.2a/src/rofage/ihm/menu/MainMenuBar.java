package rofage.ihm.menu;

import java.awt.ComponentOrientation;
import java.util.Iterator;

import javax.swing.JComboBox;
import javax.swing.JMenuBar;

import rofage.common.Engine;
import rofage.common.object.Configuration;
import rofage.ihm.MainWindow;
import rofage.ihm.actions.common.ChangeConfInMainListener;

@SuppressWarnings("serial")
public class MainMenuBar extends JMenuBar {
	private Engine engine;
	private MainWindow mainWindow;
	
	private MenuConf menuConf		= null;
	private MenuRomMgt menuRomMgt 	= null;
	private MenuAbout menuAbout 	= null;
	private JComboBox comboConf		= null;

	public MainMenuBar (Engine engine, MainWindow mainWindow) {
		this.engine = engine;
		this.mainWindow = mainWindow;
		
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		add(getMenuRomMgt());
		add(getMenuConf());
		add(getMenuAbout());
		add(getComboConf());
	}

	public MenuConf getMenuConf() {
		if (menuConf==null) {
			menuConf = new MenuConf(engine);
		}
		return menuConf;
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
