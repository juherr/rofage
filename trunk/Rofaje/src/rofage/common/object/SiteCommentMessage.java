package rofage.common.object;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import rofage.ihm.Messages;

/**
 * A site message is what the server returns to us
 * A message indicates if everything went well (error boolean)
 * and the message we should display (messageKey)
 * @author Pierrot
 *
 */
public class SiteCommentMessage extends SiteMessage {
	private List<Comment> listComments = new ArrayList<Comment>();
		
	/**
	 * The String message should be formatted this way : 
	 * L:;:message_key[:;:login1:;:note1:;:message1[:;:login2:;:note2:;:message2]]
	 * L stands for the result itself (KEY_OK or KEY_KO) which indicates if an 
	 * error occured on the remote server
	 * @param message
	 */
	public SiteCommentMessage (String message) {
		String [] strs = message.split(":;:");
		if (KEY_OK.equals(strs[0])) {
			error = false;
		} else {
			error = true;
		}
		messageKey = strs [1];
		
		// Now we have to deal with the potential comments
		for (int i=2; i<strs.length && i+1<strs.length; i+=4) { // We also test the i+1 case because a separator is always added at the end of the message
			String login = strs[i];
			Float note;
			try {
				note = Float.valueOf(strs[i+1]);
			} catch (NumberFormatException e) {
				note = Float.valueOf(0);
			}
			String text = "";
			if (i+2<strs.length) {// If no comment was entered we may reach the end of the table !!
				text = new String (strs[i+2]);
			}
			// We always have a crc
			Comment comment = new Comment(login, note, text, strs[i+3]);
			listComments.add(comment);
		}
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

	public boolean isError() {
		return error;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public List<Comment> getListComments() {
		return listComments;
	}
}
