package rofage.common;

import java.util.List;

import javax.swing.SwingWorker;

import rofage.ihm.GameListTableModel;

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
		
		gameListTableModel.setTitlePattern(engine.getGlobalConf().getSelectedConf().getTitlePattern());
		gameListTableModel.setGameCollectionAndDatas(engine.getGameDB().getGameCollections().get(engine.getGlobalConf().getSelectedConf().getConfName()));
		
		engine.getMainWindow().getJTable().updateUI();
		return 0;
	}
}
