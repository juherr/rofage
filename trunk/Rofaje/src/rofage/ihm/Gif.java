package rofage.ihm;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Gif extends JPanel {
	private Image img;
	
	public Gif (String path) {
		try {
			img = ImageIO.read(getClass().getClassLoader().getResourceAsStream(path));
			setSize(img.getWidth(null), img.getHeight(null));
			repaint();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 
	protected void paintComponent(Graphics g) {
		// efface le contenu précédent :
		g.setColor(Color.RED);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		// dessine le gif :
		g.drawImage(img, 1, 1, img.getWidth(null), img.getHeight(null), this);
   }
	 
	}

