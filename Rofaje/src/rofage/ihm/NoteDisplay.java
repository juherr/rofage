package rofage.ihm;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import rofage.common.images.ImageToolkit;


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
				Image newImgJaugeIn = ImageToolkit.scale(imgJaugeIn, newWidth, imgJaugeIn.getHeight(null));
				// Then we merge the jaugeStart and the newJaugeIn
				BufferedImage bufIn = ImageToolkit.toBufferedImage(newImgJaugeIn);
				BufferedImage bufJauge = ImageToolkit.toBufferedImage(imgJaugeStart);
				BufferedImage jauge = ImageToolkit.addImage(bufJauge, bufIn, START_X, START_Y);
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
