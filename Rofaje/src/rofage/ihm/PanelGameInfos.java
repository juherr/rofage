package rofage.ihm;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial") //$NON-NLS-1$
public class PanelGameInfos extends JPanel {

	private JLabel labelReleaseNb 	= null;
	private JLabel labelSize		= null;
	private JLabel labelCRC			= null;
	private JLabel labelOrigin		= null;
	private JLabel labelLanguage	= null;
	private JLabel labelPublisher	= null;
	private JLabel labelGenre 		= null;
	private JLabel labelGroup 		= null;
	private JLabel labelWifi 		= null;
	private JLabel labelComment		= null;
	
	public PanelGameInfos () {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		// Infos Box
		setBorder(BorderFactory.createTitledBorder(Messages.getString("MainWindow.5"))); //$NON-NLS-1$
		Box vBox = Box.createVerticalBox();
		vBox.add(getLabelReleaseNb());
		vBox.add(getLabelSize());
		vBox.add(getLabelCRC());
		vBox.add(getLabelOrigin());
		vBox.add(getLabelLanguage());
		vBox.add(getLabelPublisher());
		vBox.add(getLabelGroup());
		vBox.add(getLabelGenre());
		vBox.add(getLabelWifi());
		vBox.add(getLabelComment());
		
		this.add(vBox);
	}

	public JLabel getLabelGroup() {
		if (labelGroup==null) {
			labelGroup = new JLabel();
			labelGroup.setIcon(new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/group.png"))); //$NON-NLS-1$
			labelGroup.setToolTipText(Messages.getString("PanelGameInfos.3")); //$NON-NLS-1$
		}
		return labelGroup;
	}
	
	public JLabel getLabelCRC() {
		if (labelCRC==null) {
			labelCRC = new JLabel();
			labelCRC.setIcon(new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/crc.png"))); //$NON-NLS-1$
			labelCRC.setToolTipText(Messages.getString("PanelGameInfos.5")); //$NON-NLS-1$
		}
		return labelCRC;
	}

	public JLabel getLabelGenre() {
		if (labelGenre==null) {
			labelGenre = new JLabel();
			labelGenre.setIcon(new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/genre.png"))); //$NON-NLS-1$
			labelGenre.setToolTipText(Messages.getString("PanelGameInfos.7")); //$NON-NLS-1$
		}
		return labelGenre;
	}

	public JLabel getLabelLanguage() {
		if (labelLanguage==null) {
			labelLanguage = new JLabel();
			labelLanguage.setIcon(new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/language.png"))); //$NON-NLS-1$
			labelLanguage.setToolTipText(Messages.getString("PanelGameInfos.9")); //$NON-NLS-1$
		}
		return labelLanguage;
	}

	public JLabel getLabelOrigin() {
		if (labelOrigin==null) {
			labelOrigin = new JLabel();
			labelOrigin.setIcon(new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/location.png"))); //$NON-NLS-1$
			labelOrigin.setToolTipText(Messages.getString("PanelGameInfos.11")); //$NON-NLS-1$
		}
		return labelOrigin;
	}

	public JLabel getLabelPublisher() {
		if (labelPublisher==null) {
			labelPublisher = new JLabel();
			labelPublisher.setIcon(new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/publisher.png"))); //$NON-NLS-1$
			labelPublisher.setToolTipText(Messages.getString("PanelGameInfos.13")); //$NON-NLS-1$
		}
		return labelPublisher;
	}

	public JLabel getLabelReleaseNb() {
		if (labelReleaseNb==null) {
			labelReleaseNb = new JLabel();
			labelReleaseNb.setIcon(new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/releasenb.png"))); //$NON-NLS-1$
			labelReleaseNb.setToolTipText(Messages.getString("PanelGameInfos.15")); //$NON-NLS-1$
		}
		return labelReleaseNb;
	}

	public JLabel getLabelSize() {
		if (labelSize==null) {
			labelSize = new JLabel();
			labelSize.setIcon(new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/size.png"))); //$NON-NLS-1$
			labelSize.setToolTipText(Messages.getString("PanelGameInfos.17")); //$NON-NLS-1$
		}
		return labelSize;
	}
	
	public JLabel getLabelComment() {
		if (labelComment==null) {
			labelComment = new JLabel();
			labelComment.setIcon(new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/comment.png"))); //$NON-NLS-1$
			labelComment.setToolTipText(Messages.getString("PanelGameInfos.21")); //$NON-NLS-1$
		}
		return labelComment;
	}

	public JLabel getLabelWifi() {
		if (labelWifi==null) {
			labelWifi = new JLabel();
			labelWifi.setText("Wifi"); //$NON-NLS-1$
			labelWifi.setIcon(new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/unknown_wifi24.png"))); //$NON-NLS-1$
			labelWifi.setToolTipText(Messages.getString("PanelGameInfos.20")); //$NON-NLS-1$
		}
		return labelWifi;
	}
}
