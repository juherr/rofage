package rofage.common.helper;

import rofage.common.Consts;
import rofage.common.object.Configuration;
import rofage.common.object.Game;
import rofage.common.parser.DatParser;
import rofage.common.url.URLToolkit;

public abstract class GameDisplayHelper {

	/**
	 * Constructs a filename depending on the game and the type of the file
	 * @param game
	 * @param type
	 * @return
	 */
	public static String constructFileName (Game game, int type) {
		StringBuffer str = new StringBuffer(game.getReleaseNb());
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
		// The release nb may be based on different fields
		if (DatParser.XML_NODE_COMMENT.equals(conf.getReleaseNbField())) {
			pattern = pattern.replace("%u", buildFormattedReleaseNb(game.getComment()));
		} else { // We always keep this as the "default value" (ie the configuration has no value for this attribute)
			pattern = pattern.replace("%u", buildFormattedReleaseNb(game.getReleaseNb()));
		}
		
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
		return Consts.COUNTRY_CODES.get(game.getLocation());
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
		// TODO check that filering still works
		// TODO check the population of romsize too !
		String loc = Consts.COUNTRY_NAMES.get(locationCode);
		if (loc==null || "".equals(loc.trim())) {
			loc = "-";
		}
		return loc;
	}
}
