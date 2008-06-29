package rofage.ihm;

import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelRomHeader extends JPanel {
	
	private JLabel labelTitle 			= null;
	private JLabel labelIconWifi 		= null;
	private JLabel labelIconCleanDump 	= null;
	
	
	public PanelRomHeader() {
		super();
		setLayout(new FlowLayout(FlowLayout.LEADING, 20, 5));
		this.add(getLabelTitle());
		this.add(Box.createHorizontalStrut(20));
		this.add(getLabelIconCleanDump());
		this.add(Box.createHorizontalStrut(20));
		this.add(getLabelIconWifi());
	}


	public JLabel getLabelTitle() {
		if (labelTitle==null) {
			labelTitle = new JLabel();
		}
		return labelTitle;
	}


	public void setLabelTitle(JLabel labelTitle) {
		this.labelTitle = labelTitle;
	}


	public JLabel getLabelIconCleanDump() {
		if (labelIconCleanDump==null) {
			labelIconCleanDump = new JLabel();
		}
		return labelIconCleanDump;
	}


	public void setLabelIconCleanDump(JLabel labelIconCleanDump) {
		this.labelIconCleanDump = labelIconCleanDump;
	}


	public JLabel getLabelIconWifi() {
		if (labelIconWifi==null) {
			labelIconWifi = new JLabel();
		}
		return labelIconWifi;
	}


	public void setLabelIconWifi(JLabel labelIconWifi) {
		this.labelIconWifi = labelIconWifi;
	}
}
