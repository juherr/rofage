package rofage.ihm.images;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import rofage.common.images.ImageToolkit;

@SuppressWarnings("serial")
public class JPanelImage extends JPanel {
	BufferedImage image = null;
	
	public JPanelImage () {
		super();
		setVisible(true);
	}
	
	// On redéfinit paint pour afficher l'image
	public void paintComponent (Graphics g) {
		if (image!=null) {
			g.drawImage(image, 0, 0, null);
		}
	}
	
	public void loadImage (String path) {
		image = ImageToolkit.loadImagefromPath(path);
		if (image==null) {
			try {
				image = ImageIO.read(JPanelImage.class.getResource("0.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		setSize(image.getWidth(), image.getHeight());
	    updateUI();
	}
}
