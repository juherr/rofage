package rofage.ihm.actions.common.community;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import rofage.common.Engine;
import rofage.common.community.SiteConnector;
import rofage.common.object.Game;
import rofage.common.object.sitemessage.SiteCommentMessage;
import rofage.ihm.GameListTableModel;
import rofage.ihm.Messages;
import rofage.ihm.windows.community.SeeVotesWindow;

@SuppressWarnings("serial")
public class ShowSeeVotesAction extends AbstractAction {

	private Engine engine = null;
	private int index;
	
	public ShowSeeVotesAction (Engine engine) {
		this.engine = engine;
	}
	public void actionPerformed(ActionEvent e) {
		index = engine.getMainWindow().getJTable().getSelectedRow();
		if (index!=-1) { // We check that a row is selected !!
			engine.getMainWindow().getProgressPanel().setVisible(true);
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					Game game = ((GameListTableModel) engine.getMainWindow().getJTable().getModel()).getGameAt(index);
					
					// We have to retrieve the existing comments
					SiteCommentMessage siteCommentMsg = SiteConnector.getAllVotes(engine.getGlobalConf().getCreds(), game.getCrc());
					engine.getMainWindow().getProgressPanel().setVisible(false);
					new SeeVotesWindow(engine, game.getTitle(), siteCommentMsg.getListComments());
				}
			});
		} else {
			JOptionPane.showMessageDialog(engine.getMainWindow(), Messages.getString("SelectGame"), Messages.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}

}
