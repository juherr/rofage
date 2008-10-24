package rofage.ihm.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import rofage.common.Engine;
import rofage.ihm.Messages;
import rofage.ihm.actions.common.ShowAction;
import rofage.ihm.actions.common.community.LogoutAction;
import rofage.ihm.actions.common.community.ShowLoginAction;
import rofage.ihm.actions.common.community.ShowResetPwdAction;
import rofage.ihm.windows.community.BestOfWindow;

@SuppressWarnings("serial")
public class MenuCommunity extends JMenu {
	
	private Engine engine	= null;
	
	private JMenuItem mItemLogin 	= null;
	private JMenuItem mItemLogout	= null;
	private JMenuItem mItemResetPwd = null;
	private JMenuItem mItemBestOf	= null;
		
	public MenuCommunity (Engine engine) {
		this.engine = engine;
		setText(Messages.getString("Community.community"));
		
		add(getMItemLogin());
		add(getMItemLogout());
		add(getMItemResetPwd());
		add(new JSeparator(JSeparator.HORIZONTAL));
		add(getMItemBestOf());
		
	}
	
	public JMenuItem getMItemBestOf () {
		if (mItemBestOf==null) {
			mItemBestOf = new JMenuItem();
			mItemBestOf.setText(Messages.getString("Community.bestOf"));
			mItemBestOf.addActionListener(new ShowAction(new BestOfWindow(engine)));
		}
		return mItemBestOf;
	}

	public JMenuItem getMItemResetPwd() {
		if (mItemResetPwd==null) {
			mItemResetPwd = new JMenuItem();
			mItemResetPwd.addActionListener(new ShowResetPwdAction(engine));
			mItemResetPwd.setText(Messages.getString("Community.resetPwd")); //$NON-NLS-1$
			mItemResetPwd.setVisible(true);
		}
		return mItemResetPwd;
	}
	
	public JMenuItem getMItemLogin() {
		if (mItemLogin==null) {
			mItemLogin = new JMenuItem();
			mItemLogin.addActionListener(new ShowLoginAction(engine));
			mItemLogin.setText(Messages.getString("Community.login")); //$NON-NLS-1$
			mItemLogin.setVisible(true);
		}
		return mItemLogin;
	}
	
	public JMenuItem getMItemLogout() {
		if (mItemLogout==null) {
			mItemLogout = new JMenuItem();
			mItemLogout.addActionListener(new LogoutAction(engine));
			mItemLogout.setText(Messages.getString("Community.logout")); //$NON-NLS-1$
			mItemLogout.setVisible(true);
		}
		return mItemLogout;
	}
}
