package rofage.common.images;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class ImageToolkit {
	
	public static BufferedImage loadImagefromPath (String path) {
		BufferedImage image = null;
		//	 On charge l'image
		File imageFile = new File (path);
		if (imageFile.exists()) {
			try {
				image = ImageIO.read(imageFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		return image;
	}
}
