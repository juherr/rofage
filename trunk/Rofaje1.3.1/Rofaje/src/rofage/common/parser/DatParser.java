package rofage.common.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import rofage.common.object.Game;

public class DatParser {
	private final static String XML_NODE_DATVERSION 	= "datVersion";
	private final static String XML_NODE_DATNAME 		= "datName";
	private final static String XML_NODE_IMFOLDER		= "imFolder";
	private final static String XML_NODE_NEWDATURL		= "datURL";
	private final static String XML_NODE_IMAGEURL		= "imURL";
	private final static String XML_NODE_DATVERSIONURL	= "datVersionURL";
	private final static String XML_NODE_CONFIGURATION	= "configuration";
	private final static String XML_NODE_NEWDAT			= "newDat";
	private final static String XML_NODE_GAMES			= "games";
	private final static String XML_NODE_GAME			= "game";
	private final static String XML_NODE_IMAGENB			= "imageNumber";
	private final static String XML_NODE_RELEASENB		= "releaseNumber";
	private final static String XML_NODE_TITLE			= "title";
	private final static String XML_NODE_ROMSIZE			= "romSize";
	private final static String XML_NODE_PUBLISHER		= "publisher";
	private final static String XML_NODE_LOCATION		= "location";
	private final static String XML_NODE_SOURCEROM		= "sourceRom";
	private final static String XML_NODE_LANGUAGE		= "language";
	private final static String XML_NODE_FILES			= "files";
	private final static String XML_NODE_ROMCRC			= "romCRC";
	private final static String XML_NODE_IMAGE1CRC		= "im1CRC";
	private final static String XML_NODE_IMAGE2CRC		= "im2CRC";
	private final static String XML_NODE_GUI				= "gui";
	private final static String XML_NODE_IMAGES			= "images";
	private final static String XML_NODE_IMAGE			= "image";
	private final static String XML_NODE_CANOPEN		= "canOpen";
	private final static String XML_NODE_EXTENSION		= "extension";
	
	private final static String XML_ATTR_FILENAME		= "fileName";
	private final static String XML_ATTR_WIDTH			= "width";
	private final static String XML_ATTR_HEIGHT			= "height";
	
	Element racine;
	Element confNode;
	Element guiNode;
	Element gamesNode;
	Element newDatNode;
	Element canOpenNode;
		
	/**
	 * Creation of a datParser. It initializes the basic root nodes
	 * - racine (root node)
	 * - confNode (configuration node)
	 * - guiNode (gui node)
	 * - gamesNode (games node)
	 * @param fullPath to the dat File
	 */
	public DatParser (String fullPath) {
		try {
			SAXBuilder sxb = new SAXBuilder();
			Document document = sxb.build(new File(fullPath));
			racine = document.getRootElement();
			confNode = racine.getChild(XML_NODE_CONFIGURATION);
			guiNode = racine.getChild(XML_NODE_GUI);
			gamesNode = racine.getChild(XML_NODE_GAMES);
			newDatNode = confNode.getChild(XML_NODE_NEWDAT);
			canOpenNode = confNode.getChild(XML_NODE_CANOPEN);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retrieves the name of the DAT file provided. 
	 * The name is a specific xml node of the dat file
	 * @param fullPath
	 * @return datName
	 */
	public String getDatName () {
		return confNode.getChildText(XML_NODE_DATNAME);
	}
	
	public String getImageFolderName () {
		String imFolder = confNode.getChildText(XML_NODE_IMFOLDER);
		if (imFolder!=null && !imFolder.trim().isEmpty()) {
			return imFolder;
		} else {
			// In some DATs the image folder may be missing
			// We replace it by the conf name
			return getDatName();
		}
	}
	
	public int getScreenshotsHeightA () {
		Element imagesNode = guiNode.getChild(XML_NODE_IMAGES);
		return Integer.parseInt(imagesNode.getChild(XML_NODE_IMAGE).getAttributeValue(XML_ATTR_HEIGHT));
	}
	
	public int getScreenshotsWidthA () {
		Element imagesNode = guiNode.getChild(XML_NODE_IMAGES);
		return Integer.parseInt(imagesNode.getChild(XML_NODE_IMAGE).getAttributeValue(XML_ATTR_WIDTH));
	}
	
	public int getScreenshotsHeightB () {
		Element imagesNode = guiNode.getChild(XML_NODE_IMAGES);
		
		// We have to manually construct the list to avoid unchecked warnings
		List<Element> listImageNodes = new ArrayList<Element>();
		Iterator iterImageNodes = imagesNode.getChildren(XML_NODE_IMAGE).iterator();
		while (iterImageNodes.hasNext()) {
			listImageNodes.add((Element) iterImageNodes.next());
		}
		
		// Then we get the height attribute of the last image node
		return Integer.parseInt(listImageNodes.get(listImageNodes.size()-1).getAttributeValue(XML_ATTR_HEIGHT));
	}
	
	public int getScreenshotsWidthB () {
		Element imagesNode = guiNode.getChild(XML_NODE_IMAGES);
		
		// We have to manually construct the list to avoid unchecked warnings
		List<Element> listImageNodes = new ArrayList<Element>();
		Iterator iterImageNodes = imagesNode.getChildren(XML_NODE_IMAGE).iterator();
		while (iterImageNodes.hasNext()) {
			listImageNodes.add((Element) iterImageNodes.next());
		}
		
		// Then we get the height attribute of the last image node
		return Integer.parseInt(listImageNodes.get(listImageNodes.size()-1).getAttributeValue(XML_ATTR_WIDTH));
	}
	
	public int getVersion () {
		return Integer.parseInt(confNode.getChildText(XML_NODE_DATVERSION));
	}
	
	public String getNewDatUrl () {
		return newDatNode.getChildText(XML_NODE_NEWDATURL);
	}
	
	public String getImageUrl () {
		return newDatNode.getChildText(XML_NODE_IMAGEURL);
	}
	
	public String getNewDatVersionUrl () {
		return newDatNode.getChildText(XML_NODE_DATVERSIONURL);
	}
	
	public String getFileName () {
		return newDatNode.getChild(XML_NODE_NEWDATURL).getAttributeValue(XML_ATTR_FILENAME);
	}
	
	public List<String> getAllowedExtensions () {
		List<String> listExtensions = new ArrayList<String>();
		
		Iterator iterExtensionNodes = canOpenNode.getChildren(XML_NODE_EXTENSION).iterator();
		while (iterExtensionNodes.hasNext()) {
			Element curExtensionNode = (Element) iterExtensionNodes.next();
			listExtensions.add(curExtensionNode.getText());
		}
		
		return listExtensions;
	}
	
	/**
	 * Retrieves the games collection and returns it as a HashTable where 
	 * the key is the release number (String) and the Value is a Game Bean
	 * @return the Game Collection (Hashtable <Integer:releaseNb, Game>)
	 */
	public HashMap<Integer, Game> retrieveGameCollection () {
		HashMap<Integer, Game> gameCollection = new HashMap<Integer, Game> ();
		// First of all let's get the games node
		Element gamesNode = racine.getChild(XML_NODE_GAMES);

		// Then we get all the game nodes
		// We have to construct an iterator an get the element 1 by 1 to avoid "unchecked warnings"
		List<Element> listGameNodes = new ArrayList<Element>();
		Iterator iterElement = gamesNode.getChildren(XML_NODE_GAME).iterator();
		while (iterElement.hasNext()) {
			listGameNodes.add((Element) iterElement.next());
		}
					
		Iterator<Element> iterGame = listGameNodes.iterator();
		while (iterGame.hasNext()) {
			Element gameNode = iterGame.next();
			// Let's create the game !
			Game game = new Game();
			game.setCrc(gameNode.getChild(DatParser.XML_NODE_FILES).getChildText(DatParser.XML_NODE_ROMCRC));
			game.setImage1crc(gameNode.getChildText(DatParser.XML_NODE_IMAGE1CRC));
			game.setImage2crc(gameNode.getChildText(DatParser.XML_NODE_IMAGE2CRC));
			game.setImageNb(gameNode.getChildText(DatParser.XML_NODE_IMAGENB));
			game.setLanguage(gameNode.getChildText(DatParser.XML_NODE_LANGUAGE));
			game.setLocation(gameNode.getChildText(DatParser.XML_NODE_LOCATION));
			game.setPublisher(gameNode.getChildText(DatParser.XML_NODE_PUBLISHER));
			game.setReleaseNb(gameNode.getChildText(DatParser.XML_NODE_RELEASENB));
			game.setRomSize(gameNode.getChildText(DatParser.XML_NODE_ROMSIZE));
			game.setSourceRom(gameNode.getChildText(DatParser.XML_NODE_SOURCEROM));
			game.setTitle(gameNode.getChildText(DatParser.XML_NODE_TITLE));
			
			gameCollection.put(Integer.parseInt(game.getReleaseNb()), game);
		}
		
		return gameCollection;
	}
	
}
