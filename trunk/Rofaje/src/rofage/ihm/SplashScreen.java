package rofage.ihm;

import javax.swing.JWindow;

@SuppressWarnings("serial")
public class SplashScreen extends JWindow {
	private Gif gif;
	
	public SplashScreen(String pathToImage) {
		// Affichage du splashScreen
		gif = new Gif(pathToImage);
		setSize(gif.getWidth()+20, gif.getHeight()+20);
        
        setLocationRelativeTo(null);
        setVisible(true);
        setAlwaysOnTop(true);
        //update(getGraphics());
	}

	// On redéfinit paint pour afficher l'image
	/*public void paint (Graphics g) {
		if (imageSplash!=null) {
			g.drawImage(imageSplash, 0, 0, null);
		}
	}*/
	
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
