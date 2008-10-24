package rofage.common.object.sitemessage;


/**
 * A site message is what the server returns to us
 * A version message indicates the current version number
 * and where this latest version can be found
 * @author Pierrot
 *
 */
public class SiteVersionMessage {
	
	private Integer versionNb;
	private String remotePath;
	
	public SiteVersionMessage (String message) {
		String [] strs = message.split(":;:");
		versionNb = new Integer(strs[0]);
		remotePath = new String (strs[1]);
		if (remotePath.endsWith("\n")) {
			remotePath = remotePath.substring(0, remotePath.length()-1);
		}
	}

	public Integer getVersionNb() {
		return versionNb;
	}

	public String getRemotePath() {
		return remotePath;
	}
}
