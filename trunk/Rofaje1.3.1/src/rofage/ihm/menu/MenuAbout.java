package rofage.ihm.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import rofage.common.Engine;
import rofage.ihm.MainWindow;
import rofage.ihm.Messages;
import rofage.ihm.actions.common.ShowAboutAction;
import rofage.ihm.actions.common.ShowUpdateAction;

@SuppressWarnings("serial")
public class MenuAbout extends JMenu {
	private JMenuItem mItemAbout 	= null;
	private JMenuItem mItemUpdate 	= null;
	
	private Engine engine;
	private MainWindow mainWindow	= null;
	
	public MenuAbout (Engine engine, MainWindow mainWindow) {
		this.engine = engine;
		this.mainWindow = mainWindow;
		
		setText("?");
		add(getMItemUpdate());
		add(getMItemAbout());
	}

	public JMenuItem getMItemAbout() {
		if (mItemAbout==null) {
			mItemAbout = new JMenuItem();
			mItemAbout.addActionListener(new ShowAboutAction(mainWindow));
			mItemAbout.setText(Messages.getString("MainWindow.4")); //$NON-NLS-1$
			mItemAbout.setVisible(true);
		}
		return mItemAbout;
	}
	
	public JMenuItem getMItemUpdate() {
		if (mItemUpdate==null) {
			mItemUpdate = new JMenuItem();
			mItemUpdate.addActionListener(new ShowUpdateAction(engine));
			mItemUpdate.setText(Messages.getString("MainWindow.6")); //$NON-NLS-1$
			mItemUpdate.setVisible(true);
		}
		return mItemUpdate;
	}
}
