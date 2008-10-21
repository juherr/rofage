package rofage.ihm.actions.common.community;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import rofage.common.Engine;
import rofage.common.SerializationHelper;
import rofage.common.community.SiteConnector;
import rofage.common.object.Comment;
import rofage.common.object.Game;
import rofage.common.object.SiteCommentMessage;
import rofage.ihm.GameListTableModel;
import rofage.ihm.Messages;
import rofage.ihm.windows.community.AddVoteWindow;

@SuppressWarnings("serial")
public class ShowAddVoteAction extends AbstractAction {

	private Engine engine = null;
	
	public ShowAddVoteAction (Engine engine) {
		this.engine = engine;
	}
	public void actionPerformed(ActionEvent e) {
		int index = engine.getMainWindow().getJTable().getSelectedRow();
		if (index!=-1) { // We check that a row is selected !!
			engine.getMainWindow().getProgressPanel().start();
			Game game = ((GameListTableModel) engine.getMainWindow().getJTable().getModel()).getGameAt(index);
			// We have to retrieve the existing comment (and we check if it exists)
			SiteCommentMessage siteCommentMsg = SiteConnector.getSingleVote(engine.getGlobalConf().getCreds(), game.getCrc());
			Comment comment = siteCommentMsg.getListComments().get(0);
			// Since we get the note, we save it in the local DB (in case it is out of sync)
			engine.getCommunityDB().getMyNotes().put(game.getCrc(), comment.getNote());
			SerializationHelper.saveCommunityDB(engine.getCommunityDB());
			engine.getMainWindow().getProgressPanel().stop();
			new AddVoteWindow(engine, game, comment);
		} else {
			JOptionPane.showMessageDialog(engine.getMainWindow(), Messages.getString("SelectGame"), Messages.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}

}
