package rofage.ihm.actions.common.community;

import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import rofage.common.Engine;
import rofage.common.community.SiteConnector;
import rofage.common.object.BestOfEntry;
import rofage.common.object.Configuration;
import rofage.common.object.Game;
import rofage.common.object.sitemessage.SiteBestOfMessage;
import rofage.ihm.Messages;
import rofage.ihm.NoteDisplay;
import rofage.ihm.windows.community.BestOfWindow;

@SuppressWarnings("serial")
public class RefreshBestOfAction extends AbstractAction {

	private BestOfWindow win = null;
	private Engine engine = null;
		
	public RefreshBestOfAction (BestOfWindow win, Engine engine) {
		this.win = win;
		this.engine = engine;
	}
	
	public void actionPerformed(ActionEvent e) {
		win.getProgressPane().setVisible(true);
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				fillInBestOfPanel();
				win.getProgressPane().setVisible(false);
			}
		});
	}
	
	private void fillInBestOfPanel () {
		win.getBestOfPanel().removeAll();
		List<BestOfEntry> listEntries = buildListEntries();
		Iterator<BestOfEntry> iterEntries = listEntries.iterator();
		Box vBoxEntries = Box.createVerticalBox();
		while (iterEntries.hasNext()) {
			BestOfEntry entry = iterEntries.next();
			Box entryBox = Box.createHorizontalBox();
			// We create the title with the note
			JLabel labelTitle = new JLabel("("+entry.getGameNote()+") "+entry.getGameTitle()+
					" ("+entry.getNbVotes()+Messages.getString("Community.votes")+")");
			NoteDisplay noteDisplay = new NoteDisplay(entry.getGameNote());
			DecimalFormat df = (DecimalFormat)DecimalFormat.getNumberInstance();
			df.applyPattern("0.#");
			labelTitle.setIcon(new ImageIcon(noteDisplay.getImg()));
			entryBox.add(labelTitle);
			entryBox.add(Box.createHorizontalGlue());
			JButton commentButton = new JButton(Messages.getString("Community.seeComments")+" ("+entry.getNbComments()+")");
			commentButton.addActionListener(new ShowSeeVotesFromCRCAction(entry.getGameCRC(), engine, entry.getGameTitle()));
			entryBox.add(commentButton);
			vBoxEntries.add(entryBox);
		}
		win.getBestOfPanel().add(vBoxEntries);
		win.getBestOfPanel().updateUI();
	}
	
	private List<BestOfEntry> buildListEntries () {
		List<BestOfEntry> listEntries = new ArrayList<BestOfEntry>();
		// We get the genre
		String genre = win.getComboGenre().getSelectedItem().toString();
		// We get the related games
		Configuration conf = engine.getGlobalConf().getSelectedConf();
		TreeMap<Integer, Game> gameCollection = engine.getGameDB().getGameCollections().get(conf.getConfName());
		// We filter the game collection with the genre
		String inList = "";
		Iterator<Game> iterGames = gameCollection.values().iterator();
		while (iterGames.hasNext()) {
			Game game = iterGames.next();
			if (game.getGenre().equals(genre) || "".equals(genre.trim())) {
				inList += "'"+game.getCrc()+"',";
			}
		}
		// We remove the last ,
		inList = inList.substring(0, inList.length()-1);
		SiteBestOfMessage siteMsg = SiteConnector.getBestOf(engine.getGlobalConf().getCreds(), inList, engine);
		if (siteMsg.isError()) {
			siteMsg.display();
		} else {
			// We build the list entries
			listEntries = siteMsg.getListBestOfEntries();
		}
		return listEntries;
	}

}
