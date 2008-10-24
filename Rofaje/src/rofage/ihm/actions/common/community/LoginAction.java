package rofage.ihm.actions.common.community;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import rofage.common.Engine;
import rofage.common.community.SiteConnector;
import rofage.common.helper.MD5;
import rofage.common.helper.SessionHelper;
import rofage.common.object.Credentials;
import rofage.common.object.sitemessage.SiteSimpleMessage;
import rofage.ihm.windows.community.LoginWindow;

@SuppressWarnings("serial") //$NON-NLS-1$
public class LoginAction extends AbstractAction {
	
	Engine engine = null;
	
	public LoginAction (Engine engine) {
		this.engine = engine;
	}
	
	public void actionPerformed(ActionEvent e) {
		// A Login does not create a session on the server. 
		// Instead we save the login information in a file so we can reload them next time
		// we connect.
		LoginWindow win = engine.getLoginWindow();
		// First we perform the check for the form
		String login = win.getFieldLogin().getText();
		String pwd 	 = new String(win.getFieldPwd().getPassword());
		Credentials creds = new Credentials(login, MD5.encode(pwd));
		
		// First we check the credentials against the database
		SiteSimpleMessage siteMessage = SiteConnector.checkCredentials(creds);
		siteMessage.display();
		if (!siteMessage.isError()) {
			win.setVisible(false);
			SessionHelper.login(engine, creds);
		}
	}

}
