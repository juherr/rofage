package rofage.common.update;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.SwingWorker;

import rofage.common.Consts;
import rofage.common.Engine;
import rofage.common.files.FileToolkit;
import rofage.common.object.Configuration;
import rofage.common.object.Game;
import rofage.common.url.URLToolkit;
import rofage.ihm.GameListTableModel;
import rofage.ihm.Messages;

public class UpdateSingleImageSwingWorker extends SwingWorker<Integer, String> { // <Return type, Type published>
	private Engine engine;
	private boolean firstImage;
	private Game game;
		
	/**
	 * Constructs a SW to update the configuration files contained in listConfToUpdate
	 * @param engine
	 * @param game
	 * @param firstImage
	 */
	public UpdateSingleImageSwingWorker (Engine engine, Game game, boolean firstImage) {
		super();
		this.engine = engine;
		this.game = game;
		this.firstImage = firstImage;
				
		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if("progress".equals(evt.getPropertyName())) { //$NON-NLS-1$
					getEngine().getMainWindow().getProgressBarImage().setValue((Integer) evt.getNewValue());
				}
            }
		});
	}
	
	@Override
	protected Integer doInBackground() throws Exception {
		Configuration conf = engine.getGlobalConf().getSelectedConf();
		
		FileToolkit.checkAndCreateFolder(Consts.HOME_FOLDER+File.separator+conf.getImageFolder());
		String folderPath = Consts.HOME_FOLDER+File.separator+conf.getImageFolder()+File.separator;
		
		String urlToImage = URLToolkit.constructImageURL(conf.getImageUrl(), game, firstImage);
		String suffixe;
		if (firstImage) {
			suffixe = "a.png";
		} else {
			suffixe = "b.png";
		}
		
		downloadFile(urlToImage, false, folderPath+game.getReleaseNb()+suffixe);
		
		// We update the window if we haven't changed the game selected !
		int index = engine.getMainWindow().getJTable().getSelectedRow();
		Game selGame = ((GameListTableModel) engine.getMainWindow().getJTable().getModel()).getGameAt(index);
		if (game.getTitle().equals(selGame.getTitle())) {
			if (firstImage) {
				engine.getMainWindow().getJPanelImage1().loadImage(Consts.HOME_FOLDER+File.separator+conf.getImageFolder()+File.separator+game.getReleaseNb()+"a.png"); //$NON-NLS-1$
			} else {
				engine.getMainWindow().getJPanelImage2().loadImage(Consts.HOME_FOLDER+File.separator+conf.getImageFolder()+File.separator+game.getReleaseNb()+"b.png"); //$NON-NLS-1$
			}
		}
		return 0;
	}
	
	
	/**
	 * Function that downloads a file from the internet
	 * @param webAddress : web address of the file to download
	 * @param useCache : indicates whether it can uses cache
	 * @param outputPath : name of the file downloaded
	 */
	private Integer downloadFile (String webAddress, boolean useCache, String outputPath) {
		try {
			setProgress(0);
			URL url = new URL(webAddress); 
			// On ouvre la connexion sur la page
			URLConnection connection = url.openConnection();
			// On désactive le cache
			connection.setUseCaches(useCache);
			
			// On récupère la taille du fichier
			int length = connection.getContentLength();
//			On récupère le stream du fichier
			InputStream is = new BufferedInputStream (connection.getInputStream());

			//On prépare le tableau de bits pour les données du fichier
			byte[] data = new byte[length];

			//On déclare les variables pour se retrouver dans la lecture du fichier
			int currentBit = 0;
			int deplacement = 0;
			
			//Tant que l'on n'est pas à la fin du fichier, on récupère des données
			while(deplacement < length){
				currentBit = is.read(data, deplacement, data.length-deplacement);	
				if(currentBit == -1)break;	
				deplacement += currentBit;
				
				setProgress(deplacement*100/length);
			}

			//Si on n'est pas arrivé à la fin du fichier, on lance une exception
			if(deplacement != length){
				throw new IOException(Messages.getString("UpdateSwingWorker.14")  //$NON-NLS-1$
					+ deplacement + Messages.getString("UpdateSwingWorker.15") + length + ")"); //$NON-NLS-1$ //$NON-NLS-2$
			}
			
			//On crée un stream sortant vers la destination
			FileOutputStream destinationFile = new FileOutputStream(outputPath); 

			//On écrit les données du fichier dans ce stream
			destinationFile.write(data);
			
			setProgress(100);

			//On vide le tampon et on ferme le stream
			destinationFile.flush();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Integer(0); // Indicates it was fine ! 
	}

	public Engine getEngine() {
		return engine;
	}
}
