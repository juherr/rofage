package rofage.ihm.actions.compress;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;

import rofage.common.Engine;
import rofage.common.compress.CompressSwingWorker;
import rofage.common.object.Game;
import rofage.ihm.GameListTableModel;

/**
 * Class to trigger the update
 * @author pierrot
 *
 */
@SuppressWarnings("serial")
public class CompressAction extends AbstractAction {
	private Engine engine = null;
	
	public CompressAction(Engine engine) {
		this.engine = engine;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(engine.getCompressWindow().getJButton())) {
			// We asked for a full recompress
			engine.setCompressSW(new CompressSwingWorker(engine, null));
		} else {
			// We come from the popMenu
			int [] selRows = engine.getMainWindow().getJTable().getSelectedRows();
			GameListTableModel model = (GameListTableModel)engine.getMainWindow().getJTable().getModel();
			List<Game> listGames = new ArrayList<Game>();
			for (int i=0; i<selRows.length; i++) {
				listGames.add(model.getGameAt(selRows[i]));
			}
			engine.getCompressWindow().setVisible(true);
			engine.setCompressSW(new CompressSwingWorker(engine, listGames));
		}
		engine.getCompressSW().execute();
	}

}
