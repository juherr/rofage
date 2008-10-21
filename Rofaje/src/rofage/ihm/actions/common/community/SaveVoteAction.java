package rofage.ihm.actions.common.community;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import rofage.common.Engine;
import rofage.common.SerializationHelper;
import rofage.common.community.SiteConnector;
import rofage.common.object.Comment;
import rofage.common.object.SiteSimpleMessage;
import rofage.ihm.windows.community.AddVoteWindow;

@SuppressWarnings("serial")
public class SaveVoteAction extends AbstractAction {

	private AddVoteWindow win = null;
	private Engine engine = null;
	
	public SaveVoteAction (AddVoteWindow win, Engine engine) {
		this.win = win;
		this.engine = engine;
	}
	public void actionPerformed(ActionEvent e) {
		win.getProgressPanel().start();
		String crc = win.getGame().getCrc();
		Float myNote = new Float(win.getSliderNote().getValue());
		Comment comment = new Comment(myNote, win.getTextComment().getText().replaceAll("\n", System.getProperty("line.separator")));
		SiteSimpleMessage siteMessage = SiteConnector.addVote(engine.getGlobalConf().getCreds(), comment, crc);
		siteMessage.display();
		if (!siteMessage.isError()) {
			// We have to save the notes
			Float avgNote = new Float(siteMessage.getAdditionalParam());
			engine.getCommunityDB().getAvgNotes().put(crc, avgNote);
			engine.getCommunityDB().getMyNotes().put(crc, myNote);
			SerializationHelper.saveCommunityDB(engine.getCommunityDB());
			
			// And update the current window 
			engine.getMainWindow().update(engine.getMainWindow().getGraphics());
			engine.getMainWindow().getPanelRomHeader().getPanelAvgNote().setAvgNote(avgNote);
			engine.getMainWindow().getPanelRomHeader().getPanelMyNote().setAvgNote(myNote);
			win.dispose();
		}
		win.getProgressPanel().stop();
	}

}
