package rofage.common.object;


/**
 * A site message is what the server returns to us
 * A message indicates if everything went well (error boolean)
 * and the message we should display (messageKey)
 * @author Pierrot
 *
 */
public class SiteSimpleMessage extends SiteMessage {
	
	private String additionalParam = "";
	
	public SiteSimpleMessage (String message) {
		String [] strs = message.split("\\|");
		if (KEY_OK.equals(strs[0])) {
			error = false;
		} else {
			error = true;
		}
		messageKey = strs [1];
		
		if (strs.length>2) {
			additionalParam = strs [2];
		}
	}

	public String getAdditionalParam() {
		return additionalParam;
	}
}
