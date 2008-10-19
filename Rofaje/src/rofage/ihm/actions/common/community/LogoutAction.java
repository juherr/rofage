package rofage.ihm.actions.common.community;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import rofage.common.Engine;
import rofage.common.helper.SessionHelper;
import rofage.ihm.Messages;

@SuppressWarnings("serial") //$NON-NLS-1$
public class LogoutAction extends AbstractAction {
	
	Engine engine = null;
	
	public LogoutAction (Engine engine) {
		this.engine = engine;
	}
	
	public void actionPerformed(ActionEvent e) {
		SessionHelper.logout(engine);
		JOptionPane.showMessageDialog(null, Messages.getString("Community.logoutPerformed"), Messages.getString("Community.operationOK"), JOptionPane.INFORMATION_MESSAGE);
	}

}
