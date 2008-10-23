package rofage.ihm.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import rofage.common.Engine;
import rofage.ihm.Messages;
import rofage.ihm.actions.common.ShowAction;

@SuppressWarnings("serial")
public class MenuConf extends JMenu {
	
	private Engine engine	= null;
	
	private JMenuItem mItemConf 	= null;
	private MenuLanguage mLanguage	= null;
	
	public MenuConf (Engine engine) {
		this.engine = engine;
		setText(Messages.getString("MainWindow.7"));
		add(getMItemConf());
		add(getMLanguage());
	}

	public JMenuItem getMItemConf() {
		if (mItemConf==null) {
			mItemConf = new JMenuItem();
			mItemConf.addActionListener(new ShowAction(engine.getConfWindow()));
			mItemConf.setText(Messages.getString("MainWindow.7")); //$NON-NLS-1$
			mItemConf.setVisible(true);
		}
		return mItemConf;
	}

	public MenuLanguage getMLanguage() {
		if (mLanguage==null) {
			mLanguage = new MenuLanguage(engine);
		}
		return mLanguage;
	}
}
