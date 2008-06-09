package rofage.common.helper;

import rofage.common.Consts;
import rofage.common.object.Game;

public abstract class GameDisplayHelper {

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
	 * @return new Title
	 */
	public static String buildTitle (Game game, String pattern) {
		pattern = pattern.replace("%n", buildFormattedReleaseNb(game.getReleaseNb()));
		pattern = pattern.replace("%t", game.getTitle());
		pattern = pattern.replace("%s", game.getRomSize());
		pattern = pattern.replace("%l", getLocation(game));
		pattern = pattern.replace("%p", game.getPublisher());
		pattern = pattern.replace("%S", game.getSourceRom());
		pattern = pattern.replace("%L", getLanguage(game));
		pattern = pattern.replace("%c", game.getCrc());
		pattern = pattern.replace("%M", getMulti(game));
		pattern = pattern.replace("%C", getCountryCode(game));
		
		return pattern.trim();
	}
	
	private static String getCountryCode (Game game) {
		return Consts.COUNTRY_CODES.get(game.getLocation());
	}
	
	private static String buildFormattedReleaseNb (String rawReleaseNb) {
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
	}
	
	/**
	 * Returns the location name in an human understanding
	 * @param game
	 * @return
	 */
	public static String getLocation (Game game) {
		return Consts.COUNTRY_NAMES.get(game.getLocation());
	}
}
