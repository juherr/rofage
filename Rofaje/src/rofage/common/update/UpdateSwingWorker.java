package rofage.common.update;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.SwingWorker;

import rofage.common.Consts;
import rofage.common.Engine;
import rofage.common.files.FileToolkit;
import rofage.common.files.ZipToolkit;
import rofage.common.helper.ConfigurationHelper;
import rofage.common.helper.GameDBHelper;
import rofage.common.object.Configuration;
import rofage.common.object.Game;
import rofage.common.parser.DatParser;
import rofage.common.url.URLToolkit;

public class UpdateSwingWorker extends SwingWorker<Integer, String> { // <Return type, Type published>
	private Engine engine;
	private boolean stopUpdate;
	private List<Configuration> listConfToUpdate;
	
	/**
	 * Constructs a SW to update the configuration files contained in listConfToUpdate
	 * @param engine
	 * @param listConfToUpdate : List<Configuration>
	 */
	public UpdateSwingWorker (Engine engine, List<Configuration> listConfToUpdate) {
		super();
		this.engine = engine;
		this.stopUpdate = false;
		this.listConfToUpdate = listConfToUpdate;
		
		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if("progress".equals(evt.getPropertyName())) {
					getEngine().getUpdateWindow().getJProgressBar().setValue((Integer) evt.getNewValue());
				}
            }
		});
	}
	
	@Override
    protected void process(List<String> strings) {
        /* Affichage des publications reçues dans le textarea. */
        for(String s : strings)
            engine.getUpdateWindow().getJTextArea().append(s + '\n');
    }

	@Override
	protected Integer doInBackground() throws Exception {
		boolean newDATFile = updateEverything();

		// we update the ui if a new dat has been downloaded
		if (newDATFile) {
			engine.getMainWindow().getJTable().updateUI();
		}
		
		return 0;
	}
	
	/**
	 * updates the images
	 *
	 */
	private void updateImages(Configuration conf) {
		// First of all we check whether the image folder exists
		setProgress(0);
		publish ("Analyse des images");
		FileToolkit.checkAndCreateFolder(Consts.HOME_FOLDER+File.separator+conf.getImageFolder());
		
		// Now for each game in the game collection we check if the image files exist
		// If they don't we download them
		// If they do we check their CRC against the crc in the gameCollection
		// If they do not match we download the file
		Collection<Game> colGames = engine.getGameDB().getGameCollections().get(conf.getConfName()).values();
		Iterator<Game> iterGames = colGames.iterator();

		String folderPath = Consts.HOME_FOLDER+File.separator+conf.getImageFolder()+File.separator;
		while (iterGames.hasNext() && !stopUpdate) {
			setProgress(0);
			Game currentGame = iterGames.next();
			
			getImage(folderPath, currentGame, conf, true);
			getImage(folderPath, currentGame, conf, false);
		}
	}
	
	private void getImage (String folderPath, Game game, Configuration conf, boolean firstImage) {
		String suffixe;
		String crc32;
		if (firstImage) {
			suffixe = "a.png";
		} else {
			suffixe = "b.png";
		}
		File imageFile = new File(folderPath+game.getImageNb()+suffixe);
		if (!imageFile.exists()) {
			// If the file does not exist we download it
			String urlToImage = URLToolkit.constructImageURL(conf.getImageUrl(), game, true);
			downloadFile(urlToImage, false, folderPath+game.getReleaseNb()+suffixe);
		} else {
			// We check the physical CRC32 against the one contained in the dat file
			String PhysicalCRC32 = FileToolkit.getCRC32(folderPath+game.getReleaseNb()+suffixe);
			// The CRC is not the same for the first and the 2nd image
			if (firstImage) {
				crc32 = game.getImage1crc();
			} else {
				crc32 = game.getImage2crc();
			}
			if (!PhysicalCRC32.equalsIgnoreCase(crc32)) {
				String urlToImage = URLToolkit.constructImageURL(conf.getImageUrl(), game, false);
				downloadFile(urlToImage, false, folderPath+game.getReleaseNb()+suffixe);
			}
		}
	}
	
	/**
	 * updates the DAT file
	 * @return true if the dat file has been updated
	 */
	private boolean updateDATFile(Configuration conf) {
		// Let's retrieve the version number of the last release
		int latestVersionNb = getLatestVersionNb(conf);
		
		// Is this a newer version than the one we have ?
		if (latestVersionNb>conf.getVersion()) {
			publish ("Une nouvelle version du DAT "+conf.getConfName()+" est disponible (version "+latestVersionNb+")");
			publish ("Tentative de téléchargement du DAT");
			downloadFile(conf.getNewDatUrl(), false, conf.getFileName());
			
			// The file should now be uncompressed
			List<String> extractedFiles = ZipToolkit.uncompressFile(Consts.HOME_FOLDER+File.separator+conf.getFileName(), null);
			if (extractedFiles.size()>1) {
				publish ("Le fichier téléchargé contenait plus d'un fichier... Erreur fatale ! :(");
				throw new RuntimeException();
			}
			publish("Nouvelle version du DAT téléchargée");
			
			// Now we have to load this dat instead of the former one
			// So we have to renew the configuration !
			DatParser datParser = new DatParser(extractedFiles.get(0));
			Configuration newConfig = ConfigurationHelper.createConfFromDatParser(engine, datParser);
			
			// We also have to renew the gameDB for this configuration
			GameDBHelper.createGameCollectionInEngine(engine, newConfig.getConfName(), datParser);
			return true;
		} else {
			publish ("Le DAT "+conf.getConfName()+" est à jour (version "+conf.getVersion()+")");
			return false;
		}		
	}
	
	private boolean updateEverything () {
		boolean newDAT = false;
		Iterator<Configuration> iterConfs = listConfToUpdate.iterator();
		while (iterConfs.hasNext()) {
			Configuration conf = iterConfs.next();
			boolean datUpdated = updateDATFile(conf);
			if (datUpdated) {
				newDAT = true;
			}
			
			// At this point, the DAT file may have been updated, we should reload it to get
			// all the images !
			conf = engine.getGlobalConf().getMapDatConfigs().get(conf.getConfName());
			// Now we update the image files 
			updateImages(conf);
		}
		
		return newDAT;
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
			publish ("Téléchargement du fichier "+outputPath);
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
				throw new IOException("Le fichier n'a pas été lu en entier (seulement " 
					+ deplacement + " sur " + length + ")");
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

	/**
	 * Retrieves the version number of the latest dat file from the internet
	 * @param currentDat
	 * @return
	 */
	private Integer getLatestVersionNb (Configuration conf) {
		publish ("Recherche de nouveau DAT pour "+conf.getConfName());
		try {
			URL url = new URL (conf.getNewVersionUrl());
			URLConnection urlConnection = url.openConnection();
			urlConnection.setUseCaches(false);
			urlConnection.connect();
			
			InputStreamReader is = new InputStreamReader(urlConnection.getInputStream());
			// Reading page contents
			char [] cbuff = new char [4];
			is.read(cbuff);
			// Conversion into string
			StringBuffer strBuff = new StringBuffer();
			for (int i=0; i<cbuff.length; i++) {
				strBuff.append(cbuff[i]);
			}
						
			return Integer.parseInt(strBuff.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public boolean isStopUpdate() {
		return stopUpdate;
	}

	public void setStopUpdate(boolean stopUpdate) {
		this.stopUpdate = stopUpdate;
	}

	public Engine getEngine() {
		return engine;
	}
}
