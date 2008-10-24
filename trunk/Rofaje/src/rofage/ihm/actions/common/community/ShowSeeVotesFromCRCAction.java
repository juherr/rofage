package rofage.ihm.actions.common.community;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import rofage.common.Engine;
import rofage.common.community.SiteConnector;
import rofage.common.object.sitemessage.SiteCommentMessage;
import rofage.ihm.windows.community.SeeVotesWindow;

@SuppressWarnings("serial")
public class ShowSeeVotesFromCRCAction extends AbstractAction {

	private Engine engine = null;
	private String crc;
	private String gameTitle;
	
	public ShowSeeVotesFromCRCAction (String crc, Engine engine, String gameTitle) {
		this.engine = engine;
		this.crc = crc;
		this.gameTitle = gameTitle;
	}
	public void actionPerformed(ActionEvent e) {
		SiteCommentMessage siteCommentMsg = SiteConnector.getAllVotes(engine.getGlobalConf().getCreds(), crc);
		new SeeVotesWindow(engine, gameTitle, siteCommentMsg.getListComments());
	}

}
