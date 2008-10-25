package rofage.ihm.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import rofage.common.Engine;
import rofage.common.SerializationHelper;
import rofage.common.object.Game;
import rofage.ihm.GameListTableModel;

/**
 * Class to trigger the update
 * @author pierrot
 *
 */
@SuppressWarnings("serial")
public class MarkAsOwnedAction extends AbstractAction {
	private Engine engine = null;
	
	public MarkAsOwnedAction (Engine engine) {
		this.engine = engine;
	}
	
	public void actionPerformed(ActionEvent e) {
		int [] selRows = engine.getMainWindow().getJTable().getSelectedRows();
		GameListTableModel model = (GameListTableModel)engine.getMainWindow().getJTable().getModel();
		
		// First we get the status we have to apply to the selection
		// We "Mark as owned" when we have at least on game which is not owned
    	// We "Mark as not owned" otherwise
		int i = 0;
		boolean markAsOwned = false;
		while (i<selRows.length && !markAsOwned) {    		
			if (!model.getGameAt(selRows[i]).isOwnedRom()) {
				markAsOwned = true;
			}
			i++;
		}
		
		for (i=0; i<selRows.length; i++) {
			// For each game, we mark them as owned or not owned
			Game game = model.getGameAt(selRows[i]);
			game.setOwnedRom(markAsOwned);
		}
		// We save the GameDB
		SerializationHelper.saveGameDB(engine.getGameDB());
		// We update the UI
		engine.getMainWindow().getJTable().updateUI();
	}
}
