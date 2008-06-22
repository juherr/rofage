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
import java.util.List;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import rofage.common.Engine;
import rofage.common.files.FileToolkit;
import rofage.common.object.Configuration;
import rofage.common.object.Game;
import rofage.common.swingworker.StoppableSwingWorker;
import rofage.common.url.URLToolkit;
import rofage.ihm.Messages;

public abstract class DownloadSwingWorker extends StoppableSwingWorker<Integer, String> { // <Return type, Type published>
	
	/** JprogressBar used to show the progression */
	private JProgressBar jProgressBar;
	/** JTextArea where the publish text will be displayed */
	private JTextArea jTextArea;
	
	public DownloadSwingWorker (Engine engine, JProgressBar jProgressBar, JTextArea jTextArea) {
		super();
		this.engine = engine;
		this.jProgressBar = jProgressBar;
		this.jTextArea = jTextArea;
		
		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if("progress".equals(evt.getPropertyName())) { //$NON-NLS-1$
					getJProgressBar().setValue((Integer) evt.getNewValue());
				}
            }
		});
	}
	
	@Override
    protected void process(List<String> strings) {
        /* Affichage des publications reçues dans le textarea. */
		if (jTextArea != null) {
	        for(String s : strings)
	            jTextArea.append(s + '\n');
		}
    }
	
	/**
	 * Checks if the file exists. If it exists, we check its CRC against the supposed CRC
	 * if they are different or if the file doesn't exist, we download it 
	 * @param folderPath: full path to the file to check
	 * @param fileName : name of the file
	 * @param game to which this file is related
	 * @param type : type of the file to check / download
	 * @param baseUrl : base Url to get the file if needed
	 * @see URLToolkit
	 * @see Configuration
	 */
	protected void checkAndDownloadFile (String folderPath, String fileName, Game game, int type, String baseUrl) {
		String fullPath = folderPath + File.separator + fileName;
		File file = new File(fullPath);
		boolean downloadFile = false;
		
		// We check if the file exists
		if (!file.exists()) {
			downloadFile = true;
		} else {
			// The file doesn't exist, we check its CRC
			// First we calculate the CRC of the file located on the filesystel
			String physicalCRC = FileToolkit.getCRC32(fullPath);
			// Then we retrieve the crc in the game
			String dBCRC = "";
			switch (type) {
				case URLToolkit.TYPE_ICON : 
					dBCRC = game.getIcoCrc();
					break;
				case URLToolkit.TYPE_IMAGE_1 :
					dBCRC = game.getImage1crc();
					break;
				case URLToolkit.TYPE_IMAGE_2 :
					dBCRC = game.getImage2crc();
					break;
			}
			
			// We check whether we have the good file locally
			if (!physicalCRC.equalsIgnoreCase(dBCRC)) {
				downloadFile = true;
			}
		}
		
		// Let's download the file if needed
		if (downloadFile) {
			String urlToFile = URLToolkit.constructURL(baseUrl, game, type);
			downloadFile(urlToFile, false, fullPath);
		}
	}
	
	/**
	 * Downloads a file from the internet
	 * @param webAddress : web address of the file to download
	 * @param useCache : indicates whether it can uses cache
	 * @param outputPath : name of the file downloaded
	 */
	protected Integer downloadFile (String webAddress, boolean useCache, String outputPath) {
		try {
			setProgress(0);
			publish (Messages.getString("UpdateSwingWorker.13")+outputPath); //$NON-NLS-1$
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

	public JProgressBar getJProgressBar() {
		return jProgressBar;
	}
}
