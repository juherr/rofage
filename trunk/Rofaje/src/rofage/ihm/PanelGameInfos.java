package rofage.ihm;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelGameInfos extends JPanel {

	private JLabel labelReleaseNb 	= null;
	private JLabel labelSize		= null;
	private JLabel labelCRC			= null;
	private JLabel labelOrigin		= null;
	private JLabel labelLanguage	= null;
	private JLabel labelPublisher	= null;
	private JLabel labelGenre 		= null;
	private JLabel labelGroup 		= null;
	
	private JCheckBox cBWifi 	= null;
	
	public PanelGameInfos () {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(BorderFactory.createTitledBorder(Messages.getString("MainWindow.5")));
		Box vBox = Box.createVerticalBox();
		vBox.add(getLabelReleaseNb());
		vBox.add(getLabelSize());
		vBox.add(getLabelCRC());
		vBox.add(getLabelOrigin());
		vBox.add(getLabelLanguage());
		vBox.add(getLabelPublisher());
		vBox.add(getLabelGenre());
		vBox.add(getCBWifi());
		
		this.add(vBox);
	}

	public JLabel getLabelGroup() {
		if (labelGroup==null) {
			labelGroup = new JLabel();
			labelGroup.setIcon(new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/group.png")));
		}
		return labelGroup;
	}
	
	public JLabel getLabelCRC() {
		if (labelCRC==null) {
			labelCRC = new JLabel();
			labelCRC.setIcon(new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/crc.png")));
		}
		return labelCRC;
	}

	public JLabel getLabelGenre() {
		if (labelGenre==null) {
			labelGenre = new JLabel();
			labelGenre.setIcon(new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/genre.png")));
		}
		return labelGenre;
	}

	public JLabel getLabelLanguage() {
		if (labelLanguage==null) {
			labelLanguage = new JLabel();
			labelLanguage.setIcon(new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/language.png")));
		}
		return labelLanguage;
	}

	public JLabel getLabelOrigin() {
		if (labelOrigin==null) {
			labelOrigin = new JLabel();
			labelOrigin.setIcon(new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/location.png")));
		}
		return labelOrigin;
	}

	public JLabel getLabelPublisher() {
		if (labelPublisher==null) {
			labelPublisher = new JLabel();
			labelPublisher.setIcon(new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/publisher.png")));
		}
		return labelPublisher;
	}

	public JLabel getLabelReleaseNb() {
		if (labelReleaseNb==null) {
			labelReleaseNb = new JLabel();
			labelReleaseNb.setIcon(new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/releasenb.png")));
		}
		return labelReleaseNb;
	}

	public JLabel getLabelSize() {
		if (labelSize==null) {
			labelSize = new JLabel();
			labelSize.setIcon(new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/size.png")));
		}
		return labelSize;
	}

	public JCheckBox getCBWifi() {
		if (cBWifi==null) {
			cBWifi = new JCheckBox();
			cBWifi.setText("Wifi");
			cBWifi.setVisible(false);
		}
		return cBWifi;
	}
}
