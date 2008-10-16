package rofage.ihm.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeMap;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import rofage.common.Engine;
import rofage.common.helper.GameDisplayHelper;
import rofage.common.object.Configuration;
import rofage.common.object.Game;

@SuppressWarnings("serial")
public class ExportListAction extends AbstractAction {
	
	public static int EXPORT_ALL		= 0;
	public static int EXPORT_BADNAMED 	= 1;
	public static int EXPORT_GOODNAMED 	= 2;
	public static int EXPORT_MISSING 	= 3;
	
	private Engine engine	= null;
	private int typeExport;
	private String exportFileName = "";
	
	public ExportListAction (Engine engine, int typeExport) {
		this.engine = engine;
		this.typeExport = typeExport;
		
		// We build the export fileName once and for all
		exportFileName = "export_";
		switch (typeExport) {
			case 0 : exportFileName+="all"; break;
			case 1 : exportFileName+="badnamed"; break;
			case 2 : exportFileName+="goodnamed"; break;
			case 3 : exportFileName+="missing"; break;
		}
		exportFileName += ".txt";
	}

	public void actionPerformed(ActionEvent arg0) {
		JFileChooser folderChooser = new JFileChooser();
		folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		folderChooser.setVisible(true);
		
		int returnVal = folderChooser.showOpenDialog(engine.getMainWindow());
		folderChooser.setVisible(false);
		if (returnVal==JFileChooser.APPROVE_OPTION) {
			// We retrieve where we have to store the file
			File file = folderChooser.getSelectedFile();
			
			// Then we actually do the export !
			writeExportFile(file);
		}
	}
	
	private void writeExportFile (File folder) {
		String filePath = folder.getAbsolutePath()+File.separator+exportFileName;
		try {
			FileWriter fWriter = new FileWriter(filePath);
			Configuration selConf = engine.getGlobalConf().getSelectedConf();
			TreeMap<Integer, Game> gameCollection = engine.getGameDB().getGameCollections().get(selConf.getConfName());
			
			if (EXPORT_GOODNAMED == typeExport || EXPORT_ALL == typeExport) {
				// We write the good named
				fWriter.write("GOOD NAMED");
				fWriter.write(System.getProperty("line.separator"));
				Iterator<Game> iterGames = gameCollection.values().iterator();
				while (iterGames.hasNext()) {
					Game game = iterGames.next();
					if (game.isGoodName()) {
						fWriter.write(GameDisplayHelper.buildTitle(game, selConf.getTitlePattern(), selConf));
						fWriter.write(System.getProperty("line.separator"));
					}
				}
				fWriter.write(System.getProperty("line.separator"));
			}
			
			if (EXPORT_BADNAMED == typeExport || EXPORT_ALL == typeExport) {
				// We write the good named
				fWriter.write("BAD NAMED");
				fWriter.write(System.getProperty("line.separator"));
				Iterator<Game> iterGames = gameCollection.values().iterator();
				while (iterGames.hasNext()) {
					Game game = iterGames.next();
					if (!game.isGoodName()) {
						fWriter.write(GameDisplayHelper.buildTitle(game, selConf.getTitlePattern(), selConf));
						fWriter.write(System.getProperty("line.separator"));
					}
				}
				fWriter.write(System.getProperty("line.separator"));
			}
			
			if (EXPORT_MISSING == typeExport || EXPORT_ALL == typeExport) {
				// We write the good named
				fWriter.write("MISSING");
				fWriter.write(System.getProperty("line.separator"));
				Iterator<Game> iterGames = gameCollection.values().iterator();
				while (iterGames.hasNext()) {
					Game game = iterGames.next();
					if (!game.isGotRom()) {
						fWriter.write(GameDisplayHelper.buildTitle(game, selConf.getTitlePattern(), selConf));
						fWriter.write(System.getProperty("line.separator"));
					}
				}
				fWriter.write(System.getProperty("line.separator"));
			}
			fWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
