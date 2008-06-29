package rofage.common.url;

import rofage.common.object.Game;

/**
 * Helps constructing urls
 * @author pierrot
 *
 */
public abstract class URLToolkit {
	/** First image of a game */
	public static final int TYPE_IMAGE_1 = 1;
	/** Second image of a game */
	public static final int TYPE_IMAGE_2 = 2;
	/** Icon of a game */
	public static final int TYPE_ICON	 = 3;
	
	
	/**
	 * Constructs the url to download the necessary image file
	 * indeed, we get the image by this mean for instance : 
	 * http://www.advanscene.com/offline/imgs/ADVANsCEne_NDS/1-500/2b.png
	 * @param baseUrlImage : base URL to get images
	 * @param game : the game for which we want to download images
	 * @param type : type of element to download
	 * @return String : url to get the image
	 */
	public static String constructURL (String baseUrlImage, Game game, int type) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append(baseUrlImage);
		
		switch (type) {
			case 1 : strBuff.append(constructRange(game.getImageNb()));
				break;
			case 2 : strBuff.append(constructRange(game.getImageNb()));
				break;
			case 3 : strBuff.append(constructRange(game.getImageNb()));
				break;
		}
		
		strBuff.append("/");
		
	    switch (type) {
	    	case 1 :
	    		strBuff.append(game.getImageNb())
	    			.append("a");
	    		break;
	    	case 2 : 
	    		strBuff.append(game.getImageNb())
	    			.append("b");
    			break;
	    	case 3 :
	    		// The icon file name is based on 4 digits
	    		String iconName = game.getReleaseNb();
	    		while (iconName.length()<4) {
	    			iconName = "0".concat(iconName);
	    		}
	    		strBuff.append(iconName);
	    }
		strBuff.append(".png");
		
		return strBuff.toString();
	}
	
	/** Constructs the "range" part of the URL
	 * indeed, we get the image by this mean for instance : 
	 * http://www.advanscene.com/offline/imgs/ADVANsCEne_NDS/1-500/2b.png
	 * The range part is : "1-500"
	 * The range is constructed on the gameValue basis
	 * It could either be a releaseNb for the icons, or the imageNb for the images
	 * @param String gameValue : the game for which we construct the range
	 * @return the range
	 */
	private static String constructRange (String gameValue) {
		StringBuffer strBuff = new StringBuffer();
		int value = Integer.parseInt(gameValue);
		int rangeStart = ((value-1)/500)*500+1; 
		int rangeEnd = rangeStart+499;
		
		strBuff.append(rangeStart)
			.append("-")
			.append(rangeEnd);
		
		return strBuff.toString();
	}
}
