package rofage.common.url;

import rofage.common.object.Game;

/**
 * Helps constructing urls
 * @author pierrot
 *
 */
public abstract class URLToolkit {
	/**
	 * Constructs the url to download the necessary image file
	 * indeed, we get the image by this mean for instance : 
	 * http://www.advanscene.com/offline/imgs/ADVANsCEne_NDS/1-500/2b.png
	 * @param baseUrlImage : base URL to get images
	 * @param game : the game for which we want to download images
	 * @param firstOne : is it the first image or the second one ?
	 * @return String : url to get the image
	 */
	public static String constructImageURL (String baseUrlImage, Game game, boolean firstOne) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append(baseUrlImage);
		int imageNb = Integer.parseInt(game.getImageNb());
		int rangeStart = ((imageNb-1)/500)*500+1; 
		int rangeEnd = rangeStart+499;
		
		strBuff.append(rangeStart)
			.append("-")
			.append(rangeEnd)
			.append("/")
			.append(game.getImageNb());
		
	    if (firstOne) {
	    	strBuff.append("a");
	    } else {
	    	strBuff.append("b");
	    }
	    strBuff.append(".png");
		
		return strBuff.toString();
	}
}
