package rofage.common.helper;

import java.text.DecimalFormat;

import rofage.common.Consts;
import rofage.common.object.Configuration;
import rofage.common.object.Game;
import rofage.common.url.URLToolkit;
import rofage.ihm.Messages;

public abstract class GameDisplayHelper {

	/**
	 * Constructs a filename depending on the game and the type of the file
	 * @param game
	 * @param type
	 * @return
	 */
	public static String constructFileName (Game game, int type) {
		StringBuffer str = new StringBuffer(game.getImageNb());
		switch (type) {
			case URLToolkit.TYPE_ICON : 
				// The icon name has 4 digits
				while (str.length()<4) {
					str.insert(0, "0");
				}
				break;
			case URLToolkit.TYPE_IMAGE_1 :
				str.append("a");
				break;
			case URLToolkit.TYPE_IMAGE_2 :
				str.append("b");
				break;
		}
		str.append(".png");
		
		return str.toString();
	}
	
	/**
	 * builds the title with the given Pattern
	 * %n N° de la release
	 * %t titre
	 * %s Taille de la rom
	 * %l Location
	 * %p Editeur
	 * %S source
	 * %L langues
	 * %c CRC
	 * @param game
	 * @param pattern
	 * @param conf : We need the configuration to know which field is used for the release nb
	 * @return new Title
	 */
	public static String buildTitle (Game game, String pattern, Configuration conf) {
		pattern = pattern.replace("%u", buildFormattedReleaseNb(game.getReleaseNb()));
		
		// Other fields
		if (game.getTitle()!=null) pattern = pattern.replace("%n", game.getTitle());
		if (game.getRomSize()!=null) pattern = pattern.replace("%s", game.getRomSize());
		if (game.getLocation()!=null) pattern = pattern.replace("%o", getLocation(game));
		if (game.getPublisher()!=null) pattern = pattern.replace("%p", game.getPublisher());
		if (game.getSourceRom()!=null) pattern = pattern.replace("%g", game.getSourceRom());
		if (game.getLanguage()!=null) pattern = pattern.replace("%a", getLanguage(game));
		if (game.getCrc()!=null) pattern = pattern.replace("%c", game.getCrc());
		if (game.getLanguage()!=null) pattern = pattern.replace("%m", getMulti(game));
		if (game.getLocation()!=null) pattern = pattern.replace("%o", getCountryCode(game));
		if (game.getComment()!=null) pattern = pattern.replace("%e", game.getComment());
		
		return pattern.trim();
	}
	
	private static String getCountryCode (Game game) {
		String countryCode = Consts.COUNTRY_CODES.get(game.getLocation());
		if (countryCode==null) {
			countryCode = "XX";
		}
		return countryCode;
	}
	
	private static String buildFormattedReleaseNb (String rawReleaseNb) {
		if (rawReleaseNb==null) {
			return "?";
		}
		int RelNblength = 4; // Length of a release number
		StringBuffer strBuffer = new StringBuffer();
		for (int i=0; i<RelNblength-rawReleaseNb.length(); i++) {
			strBuffer.append("0");
		}
		strBuffer.append(rawReleaseNb);
		return strBuffer.toString();
	}
	
	/**
	 * returns the string (MnbLanguage) if there is more than one language, otherwise it returns a blank string
	 * @param game
	 * @return
	 */
	public static String getMulti (Game game) {
		String languageInBits = Integer.toBinaryString(Integer.parseInt(game.getLanguage()));
		int nbLanguage = 0;
		for (int i=0; i<languageInBits.length(); i++) {
			if (languageInBits.charAt(i)=='1') {
				nbLanguage++;
			}
		}
		if (nbLanguage>1) return "(M"+nbLanguage+")";
		return "";
	}
	
	/**
	 * Returns the language name in an human understanding
	 * @param game
	 * @return
	 */
	public static String getLanguage (Game game) {
		if (!game.getLanguage().isEmpty()) {
			String languageInBits = Integer.toBinaryString(Integer.parseInt(game.getLanguage()));
			StringBuffer str = new StringBuffer();
			int nbLanguage = 0;
			for (int i=0; i<languageInBits.length(); i++) {
				if (languageInBits.charAt(i)=='1') {
					if (nbLanguage>=1) {
						str.append(" / ");
					}
					str.append(Consts.LANG_NAMES.get(languageInBits.length()-1-i));
					nbLanguage++;
				}
			}
			return str.toString();
		} else {
			return "";
		}
	}
	
	/**
	 * Returns the location name in an human understanding
	 * @param game
	 * @return
	 */
	public static String getLocation (Game game) {
		return getLocation(game.getLocation());
	}
	
	/**
	 * Returns the location name in an human understanding
	 * @param locationCode
	 * @return
	 */
	public static String getLocation (String locationCode) {
		String loc = Consts.COUNTRY_NAMES.get(locationCode);
		if (loc==null || "".equals(loc.trim())) {
			loc = Messages.getString("Unknown");
		}
		return loc;
	}
	
	/**
	 * Construct the full size String in a human understandable language
	 * It is either returned in KB or MB depending on the size
	 * If a romSize is null, we return the string "unknown" (localized)
	 * @param romSize
	 * @return
	 */
	public static String buildRomSize (String romSize) {
		if (romSize!=null) {
			DecimalFormat df = (DecimalFormat)DecimalFormat.getNumberInstance();
			df.applyPattern("0.#");
			Float realSize = new Float(romSize);
			realSize = realSize/1024; // Some older dat with old roms have very small values
			if (realSize.intValue()<1024) {
				return df.format(realSize)+ " KB";
			} else {
				return df.format(realSize/1024)+ " MB";
			}
		} else {
			return Messages.getString("Unknown");
		}
	}
}
