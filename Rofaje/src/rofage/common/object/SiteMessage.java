package rofage.common.object;

import javax.swing.JOptionPane;

import rofage.ihm.Messages;

public abstract class SiteMessage {
	protected final static String KEY_OK = "O";
	protected boolean error = true;
	protected String messageKey = "";
	
	public boolean isError() {
		return error;
	}

	public String getMessageKey() {
		return messageKey;
	}
	
	/** 
	 * Displays the content of the message in a JOptionPane
	 */
	public void display () {
		String title;
		int icon;
		if (isError()) {
			title = Messages.getString("Community.operationKO");
			icon = JOptionPane.ERROR_MESSAGE;
		} else {
			title = Messages.getString("Community.operationOK");
			icon = JOptionPane.INFORMATION_MESSAGE;
		}
		JOptionPane.showMessageDialog(null, Messages.getString(messageKey), title, icon);
	}
}
