package rofage.ihm;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JWindow;

@SuppressWarnings("serial")
public class SplashScreen extends JWindow {
	private BufferedImage imageSplash;
	
	public SplashScreen() {
		// Affichage du splashScreen
	
		try {
			// On charge l'image
			imageSplash = ImageIO.read(SplashScreen.class.getResourceAsStream("images/splash.png"));
	        setSize(imageSplash.getWidth(), imageSplash.getHeight());
	        setLocationRelativeTo(null);
	        setVisible(true);
	        update(getGraphics());
	        // On gère l'attente
	        waitEndSplash();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// On redéfinit paint pour afficher l'image
	public void paint (Graphics g) {
		if (imageSplash!=null) {
			g.drawImage(imageSplash, 0, 0, null);
		}
	}
	
	// Affiche le splash pendant un temps donné
	private void waitEndSplash () {
		
		try {
			Thread.sleep(2000); // On attend 2 secondes
			dispose();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
