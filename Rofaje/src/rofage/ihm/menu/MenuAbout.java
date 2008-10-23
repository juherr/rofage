package rofage.ihm.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import rofage.common.Engine;
import rofage.ihm.MainWindow;
import rofage.ihm.Messages;
import rofage.ihm.actions.common.OpenWebLinkAction;
import rofage.ihm.actions.common.SendMailAction;
import rofage.ihm.actions.common.ShowAboutAction;
import rofage.ihm.actions.common.ShowAction;

@SuppressWarnings("serial")
public class MenuAbout extends JMenu {
	private JMenuItem mItemUpdate 	= null;
	private JMenuItem mItemReportBug= null;
	private JMenuItem mItemSendMail	= null;
	private JMenuItem mItemAbout 	= null;
	private JMenuItem mItemDonate 	= null;
	
	private Engine engine;
	private MainWindow mainWindow	= null;
	
	public MenuAbout (Engine engine, MainWindow mainWindow) {
		this.engine = engine;
		this.mainWindow = mainWindow;
		
		setText("?");
		add(getMItemUpdate());
		add(new JSeparator(JSeparator.HORIZONTAL));
		add(getMItemAbout());
		add(getMItemDonate());
		add(new JSeparator(JSeparator.HORIZONTAL));
		add(getMItemReportBug());
		add(getMItemSendMail());
	}

	public JMenuItem getMItemSendMail() {
		if (mItemSendMail==null) {
			mItemSendMail = new JMenuItem();
			mItemSendMail.addActionListener(new SendMailAction());
			mItemSendMail.setText(Messages.getString("SendMail")); //$NON-NLS-1$
			mItemSendMail.setVisible(true);
		}
		return mItemSendMail;
	}
	
	public JMenuItem getMItemReportBug() {
		if (mItemReportBug==null) {
			mItemReportBug = new JMenuItem();
			mItemReportBug.addActionListener(new OpenWebLinkAction(OpenWebLinkAction.urlBugReport));
			mItemReportBug.setText(Messages.getString("ReportBug")); //$NON-NLS-1$
			mItemReportBug.setVisible(true);
		}
		return mItemReportBug;
	}
	
	public JMenuItem getMItemDonate() {
		if (mItemDonate==null) {
			mItemDonate = new JMenuItem();
			mItemDonate.addActionListener(new OpenWebLinkAction(OpenWebLinkAction.urlDonate));
			mItemDonate.setText(Messages.getString("Donate")); //$NON-NLS-1$
			mItemDonate.setVisible(true);
		}
		return mItemDonate;
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
			mItemUpdate.addActionListener(new ShowAction(engine.getUpdateWindow()));
			mItemUpdate.setText(Messages.getString("MainWindow.6")); //$NON-NLS-1$
			mItemUpdate.setVisible(true);
		}
		return mItemUpdate;
	}
}
