package rofage.common.object.sitemessage;

import java.util.ArrayList;
import java.util.List;

import rofage.common.Engine;
import rofage.common.helper.GameDisplayHelper;
import rofage.common.object.BestOfEntry;
import rofage.common.object.Configuration;
import rofage.common.object.Game;
import rofage.common.object.GameDB;

/**
 * A site message is what the server returns to us
 * A message indicates if everything went well (error boolean)
 * and the message we should display (messageKey)
 * @author Pierrot
 *
 */
public class SiteBestOfMessage extends SiteMessage {
	private List<BestOfEntry> listBestOfEntries = new ArrayList<BestOfEntry>();
		
	/**
	 * The String message should be formatted this way : 
	 * L:;:message_key[:;:crc1:;:note1:;:nbComment1;:;nbVotes1[:;:crc2:;:note2:;:nbComment2:;:nbVotes2]]
	 * L stands for the result itself (KEY_OK or KEY_KO) which indicates if an 
	 * error occured on the remote server
	 * @param message
	 */
	public SiteBestOfMessage (String message, Engine engine) {
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
		
		Configuration conf = engine.getGlobalConf().getSelectedConf();
		GameDB gameDB = engine.getGameDB();
		for (int i=2; i<strs.length; i+=4) {
			String crc = strs[i];
			// We get the game related to this crc
			Game game = gameDB.findGameInCollectionFromCRC(conf.getConfName(), crc);
			if (game!=null) {
				BestOfEntry entry = new BestOfEntry(GameDisplayHelper.buildTitle(game, conf.getTitlePattern(), conf),
						Float.valueOf(strs[i+1]),
						Integer.valueOf(strs[i+2]), 
						crc,
						Integer.valueOf(strs[i+3]));
				listBestOfEntries.add(entry);
			}
		}
		
		
		
		
	}

	public List<BestOfEntry> getListBestOfEntries() {
		return listBestOfEntries;
	}
	
	
}
