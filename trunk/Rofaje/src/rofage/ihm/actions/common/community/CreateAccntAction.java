package rofage.ihm.actions.common.community;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import rofage.common.Engine;
import rofage.common.community.SiteConnector;
import rofage.common.helper.MD5;
import rofage.common.helper.SessionHelper;
import rofage.common.helper.StringHelper;
import rofage.common.object.Credentials;
import rofage.common.object.SiteSimpleMessage;
import rofage.ihm.Messages;
import rofage.ihm.windows.community.CreateAccntWindow;

@SuppressWarnings("serial") //$NON-NLS-1$
public class CreateAccntAction extends AbstractAction {
	private final static int LOGIN_LENGTH 	= 15;
	private final static int EMAIL_LENGTH	= 100;
	private final static int MIN_LENGTH		= 4;

	Engine engine = null;
	
	public CreateAccntAction (Engine engine) {
		this.engine = engine;
	}
	
	public void actionPerformed(ActionEvent e) {
		CreateAccntWindow win = engine.getAccntWindow();
		// First we perform the check for the form
		String login = win.getFieldLogin().getText();
		String pwd 	 = new String(win.getFieldPwd().getPassword());
		String pwd2	 = new String(win.getFieldPwd2().getPassword());
		String email = win.getFieldEmail().getText();
		
		String errors = "";
		if (login.length()<MIN_LENGTH) {
			errors += Messages.getString("Community.loginMinLength")+"\n";
		}
		if (pwd.length()<MIN_LENGTH) {
			errors += Messages.getString("Community.pwdMinLength")+"\n";
		}
		if (!pwd.equals(pwd2)) {
			errors += Messages.getString("Community.samePasswords")+"\n";
		}
		if (login.length()>LOGIN_LENGTH) {
			errors += Messages.getString("Community.loginLength")+"\n";
		}
		if (email.length()>EMAIL_LENGTH) {
			errors += Messages.getString("Community.emailLength")+"\n";
		}
		if (!StringHelper.isEMail(email)) {
			errors += Messages.getString("Community.notEmail")+"\n";
		}
		if ("".equals(errors)) {
			pwd = MD5.encode(pwd);
			SiteSimpleMessage siteMessage = SiteConnector.createAccount(login, pwd, email);
			siteMessage.display();
			// If the operation was successfull, we close this window
			if (!siteMessage.isError()) {
				win.setVisible(false);
				SessionHelper.login(engine, new Credentials(login, pwd));
			}
		} else {
			JOptionPane.showMessageDialog(null, errors, Messages.getString("AddConfAction.4"), JOptionPane.ERROR_MESSAGE);
		}
	}

}
