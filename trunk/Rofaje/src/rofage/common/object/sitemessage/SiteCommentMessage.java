package rofage.common.object.sitemessage;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import rofage.common.object.Comment;
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
		if (message.endsWith("\n")) {
			message = message.substring(0, message.length()-1);
		}
		String [] strs = message.split(":;:");
		if (KEY_OK.equals(strs[0])) {
			error = false;
		} else {
			error = true;
		}
		
		messageKey = strs [1];
		
		// Now we have to deal with the potential comments
		for (int i=2; i<strs.length && i+1<strs.length; i+=6) { // We also test the i+1 case because a separator is always added at the end of the message
			String login = strs[i];
			Float note;
			try {
				note = Float.valueOf(strs[i+1]);
			} catch (NumberFormatException e) {
				note = Float.valueOf(0);
			}
			String text = "";
			text = new String (strs[i+2]);
			// We always have a crc and a isSpoiler flag (strs[i+3] and strs[i+4] but we have to convert
			// this string value to a boolean)
			boolean isSpoiler = "1".equals(strs[i+4]);
			
			long idComment = 0;
			if (i+5<strs.length && strs[i+5]!=null && !"".equals(strs[i+5].trim())) {
				idComment = Long.valueOf(strs[i+5]);
			}
			Comment comment = new Comment(login, note, text, strs[i+3], isSpoiler, idComment);
			listComments.add(comment);
		}
	}
	
	public List<Comment> getListComments() {
		return listComments;
	}
}
