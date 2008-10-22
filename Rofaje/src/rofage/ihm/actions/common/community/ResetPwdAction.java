package rofage.ihm.actions.common.community;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import rofage.common.Engine;
import rofage.common.community.SiteConnector;
import rofage.common.helper.MD5;
import rofage.common.helper.SessionHelper;
import rofage.common.object.Credentials;
import rofage.common.object.SiteSimpleMessage;
import rofage.ihm.Messages;
import rofage.ihm.windows.community.ResetPwdWindow;

@SuppressWarnings("serial") //$NON-NLS-1$
public class ResetPwdAction extends AbstractAction {
	private final static int LOGIN_LENGTH 	= 15;
	private final static int MIN_LENGTH		= 4;

	ResetPwdWindow win = null;
	Engine engine = null;
	
	public ResetPwdAction (ResetPwdWindow resetPwdWindow, Engine engine) {
		this.win = resetPwdWindow;
		this.engine = engine;
	}
	
	public void actionPerformed(ActionEvent e) {
		// First we perform the check for the form
		String login = win.getFieldLogin().getText();
		String pwd 	 = new String(win.getFieldPwd().getPassword());
		String pwd2	 = new String(win.getFieldPwd2().getPassword());
		String oldPwd = new String (win.getFieldOldPwd().getPassword());
		
		String errors = "";
		if (login.length()<MIN_LENGTH) {
			errors += Messages.getString("Community.loginMinLength")+"\n";
		}
		if (oldPwd.length()<MIN_LENGTH) {
			errors += Messages.getString("Community.pwdMinLength")+"\n";
		}
		if (!pwd.equals(pwd2)) {
			errors += Messages.getString("Community.samePasswords")+"\n";
		}
		if (login.length()>LOGIN_LENGTH) {
			errors += Messages.getString("Community.loginLength")+"\n";
		}
		if ("".equals(errors)) {
			pwd = MD5.encode(pwd);
			oldPwd = MD5.encode(oldPwd);
			Credentials creds = new Credentials(login, oldPwd);
			SiteSimpleMessage siteMessage = SiteConnector.resetPwd(creds, pwd);
			siteMessage.display();
			// If the operation was successfull, we close this window
			if (!siteMessage.isError()) {
				win.dispose();
				SessionHelper.logout(engine);
			}
		} else {
			JOptionPane.showMessageDialog(null, errors, Messages.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}

}
