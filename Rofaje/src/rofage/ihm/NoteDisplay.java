package rofage.ihm;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class NoteDisplay extends JPanel {
	
	private final static int MAXWIDTH 	= 36;
	private final static int START_X 	= 7; // Starting point for the internal jauge
	private final static int START_Y	= 8;
	
	private static Image imgJaugeEmpty = new ImageIcon(NoteDisplay.class.getClassLoader().getResource("rofage/ihm/images/jaugeEmpty.png")).getImage();
	private static Image imgJaugeStart = new ImageIcon(NoteDisplay.class.getClassLoader().getResource("rofage/ihm/images/jaugeStart.png")).getImage();
	private static Image imgJaugeIn 	= new ImageIcon(NoteDisplay.class.getClassLoader().getResource("rofage/ihm/images/jaugeIn.png")).getImage();
	
	private Image img; // The resulting image
	private Float avgNote = new Float(0);
	private JLabel labelNote;
	
	/**
	 * Display a note out of 10
	 * @param note
	 */
	public NoteDisplay (String note) {
		setPreferredSize(new Dimension(48,32));
		avgNote = new Float(note);
		add(getLabelNote());
		displayNote();
	}
	
	public NoteDisplay (Float note) {
		setPreferredSize(new Dimension(48,32));
		avgNote = note;
		add(getLabelNote());
		displayNote();
	}
	
	private void displayNote () {
		if (avgNote!=null && !avgNote.isNaN() && avgNote>0) {
			if (avgNote>10) avgNote = new Float(10);
			if (avgNote>0) {
				// We resize the internal part of the jauge
				int newWidth = new Float(MAXWIDTH * avgNote / 10).intValue();
				Image newImgJaugeIn = scale(imgJaugeIn, newWidth, imgJaugeIn.getHeight(null));
				// Then we merge the jaugeStart and the newJaugeIn
				BufferedImage bufIn = toBufferedImage(newImgJaugeIn);
				BufferedImage bufJauge = toBufferedImage(imgJaugeStart);
				BufferedImage jauge = addImage(bufJauge, bufIn);
				img = jauge;
			}
		} else {
			avgNote = new Float(0);
			img = imgJaugeEmpty;
		}
		DecimalFormat df = (DecimalFormat)DecimalFormat.getNumberInstance();
		df.applyPattern("0.#");
		getLabelNote().setText(df.format(avgNote));
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (img == null) return;
		int w = img.getWidth(this);  
		int h = img.getHeight(this);
		//boolean zoom = (w > getWidth() || h > getHeight());
		//if (zoom) g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		//else 
		g.drawImage(img, (getWidth()-w)/2, (getHeight()-h)/2, this);
	}
	
	public BufferedImage toBufferedImage(Image image) {
        /** On test si l'image n'est pas déja une instance de BufferedImage */
        if( image instanceof BufferedImage ) {
                /** cool, rien à faire */
                return( (BufferedImage)image );
        } else {
                /** On s'assure que l'image est complètement chargée */
                image = new ImageIcon(image).getImage();
                
                /** On crée la nouvelle image */
                BufferedImage bufferedImage = new BufferedImage(
                                                      image.getWidth(null),
                                                      image.getHeight(null),
                                                      BufferedImage.TYPE_INT_ARGB);
                Graphics g = bufferedImage.createGraphics();
                g.drawImage(image,0,0,null);
                g.dispose();
                
                return( bufferedImage );
        } 
	}
	
	public static BufferedImage addImage(BufferedImage image1, BufferedImage image2){
		Graphics2D g2d = image1.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		                RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
		                RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		 
		g2d.drawImage(image2, START_X, START_Y, null);
				
		g2d.dispose();
		
		return image1 ;
	}
	
	/** 
	 * Redimensionne une image.
	 * 
	 * @param source Image à redimensionner.
	 * @param width Largeur de l'image cible.
	 * @param height Hauteur de l'image cible.
	 * @return Image redimensionnée.
	 */
	public static Image scale(Image source, int width, int height) {
	    /* On crée une nouvelle image aux bonnes dimensions. */
	    BufferedImage buf = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

	    /* On dessine sur le Graphics de l'image bufferisée. */
	    Graphics2D g = buf.createGraphics();
	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g.drawImage(source, 0, 0, width, height, null);
	    g.dispose();

	    /* On retourne l'image bufferisée, qui est une image. */
	    return buf;
	}

	public Float getNote() {
		return avgNote;
	}

	public void setAvgNote(Float avgNote) {
		// When we set the note again, we also have to repaint the component
		this.avgNote = avgNote;
		displayNote();
	}

	public JLabel getLabelNote() {
		if (labelNote==null) {
			labelNote = new JLabel();
			if (avgNote!=null && !avgNote.isNaN()) {
				DecimalFormat df = (DecimalFormat)DecimalFormat.getNumberInstance();
				df.applyPattern("0.#");
				labelNote.setText(df.format(avgNote));
			}
		}
		return labelNote;
	}

	public Image getImg() {
		return img;
	}
}
