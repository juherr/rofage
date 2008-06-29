package rofage.ihm.menu;

import java.util.Locale;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

import rofage.common.Engine;
import rofage.ihm.Messages;
import rofage.ihm.actions.common.ChangeLanguageListener;

@SuppressWarnings("serial")
public class MenuLanguage extends JMenu {
	
	private Engine engine	= null;
	
	private JRadioButtonMenuItem mItemLnFr 	= null;
	private JRadioButtonMenuItem mItemLnEn 	= null;

	public MenuLanguage (Engine engine) {
		this.engine = engine;
		
		setText(Messages.getString("MainWindow.1"));
		add(getMItemLnEn());
		add(getMItemLnFr());
		ButtonGroup languageGroup = new ButtonGroup();
		languageGroup.add(getMItemLnEn());
		languageGroup.add(getMItemLnFr());
		
	}

	public JRadioButtonMenuItem getMItemLnEn() {
		if (mItemLnEn==null) {
			mItemLnEn = new JRadioButtonMenuItem();
			mItemLnEn.setText(Messages.getString("MainWindow.12"));
			if (engine.getGlobalConf().getSelectedLocale()==Locale.ENGLISH) mItemLnEn.setSelected(true);
			mItemLnEn.addItemListener(new ChangeLanguageListener(engine));
			mItemLnEn.setVisible(true);
		}
		return mItemLnEn;
	}

	public JRadioButtonMenuItem getMItemLnFr() {
		if (mItemLnFr==null) {
			mItemLnFr = new JRadioButtonMenuItem ();
			mItemLnFr.setText(Messages.getString("MainWindow.11")); //$NON-NLS-1$
			if (engine.getGlobalConf().getSelectedLocale()==Locale.FRENCH) mItemLnFr.setSelected(true);
			mItemLnFr.addItemListener(new ChangeLanguageListener(engine));
			mItemLnFr.setVisible(true);
		}
		return mItemLnFr;
	}
}
