package rofage.common;

import java.util.List;
import java.util.TreeMap;

import javax.swing.SwingWorker;

import rofage.common.object.Game;
import rofage.ihm.GameListTableModel;
import rofage.ihm.helper.StatusBarHelper;

public class MainSwingWorker extends SwingWorker<Integer, String> { // <Return type, Type published>
	private Engine engine;
	
	public MainSwingWorker (Engine engine) {
		super();
		this.engine = engine;
	}
	
	@Override
    protected void process(List<String> strings) {
        /* Affichage des publications reçues dans le textarea. */
        //for(String s : strings)
            //mainWindow.getJTextArea().append(s + '\n');
    }

	@Override
	protected Integer doInBackground() throws Exception {
		GameListTableModel gameListTableModel = (GameListTableModel) engine.getMainWindow().getJTable().getModel();
		
		String titlePattern = "";
		String confName = "";
		if (engine.getGlobalConf().getSelectedConf()!=null) {
			titlePattern = engine.getGlobalConf().getSelectedConf().getTitlePattern();
			confName = engine.getGlobalConf().getSelectedConf().getConfName();
		}
		TreeMap<Integer, Game> gameCollection = engine.getGameDB().getGameCollections().get(confName);
		gameListTableModel.setTitlePattern(titlePattern);
		gameListTableModel.setGameCollectionAndDatas(gameCollection);
		
		StatusBarHelper.updateStatusBar(gameCollection, engine);
		
		engine.getMainWindow().getJTable().updateUI();
		return 0;
	}
}
