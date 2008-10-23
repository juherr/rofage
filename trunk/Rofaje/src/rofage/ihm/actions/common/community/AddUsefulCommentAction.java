package rofage.ihm.actions.common.community;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import rofage.common.Engine;
import rofage.common.community.SiteConnector;
import rofage.common.object.SiteSimpleMessage;
import rofage.ihm.windows.community.SeeVotesWindow;

@SuppressWarnings("serial")
public class AddUsefulCommentAction extends AbstractAction {

	private long idComment;
	private SeeVotesWindow win = null;
	private Engine engine = null;
		
	public AddUsefulCommentAction (SeeVotesWindow win, long idComment, Engine engine) {
		this.win = win;
		this.idComment = idComment;
		this.engine = engine;
	}
	public void actionPerformed(ActionEvent e) {
		win.getProgressPane().setVisible(true);
		
		SiteSimpleMessage siteMsg = SiteConnector.addUsefulComment(engine.getGlobalConf().getCreds(), idComment);
		siteMsg.display();
		win.getProgressPane().setVisible(false);
	}

}
