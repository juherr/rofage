package rofage.ihm;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import rofage.common.Engine;
import rofage.common.helper.SessionHelper;
import rofage.ihm.actions.common.community.ShowAddVoteAction;
import rofage.ihm.actions.common.community.ShowSeeVotesAction;

@SuppressWarnings("serial")
public class PanelRomHeader extends JPanel {
	
	private Engine engine;
	
	private JPanel panelRomInfo		= null;
	private JPanel panelCommunity	= null;
	
	private JLabel labelTitle 			= null;
	private JLabel labelIconWifi 		= null;
	private JLabel labelIconCleanDump 	= null;
	
	// Community
	private NoteDisplay panelAvgNote= null;
	private NoteDisplay panelMyNote	= null;
	private JLabel labelMyNote		= null;
	private JLabel labelAvgNote		= null;
	private JButton buttonAvgNote	= null;
	private JButton buttonMyNote	= null;
	
	
	public PanelRomHeader(Engine engine) {
		super();
		this.engine = engine;
		setLayout(new BorderLayout());
		this.add(getPanelRomInfo(), BorderLayout.WEST);
		this.add(getPanelCommunity(), BorderLayout.EAST);
		
		
	}
	
	public JPanel getPanelCommunity () {
		if (panelCommunity==null) {
			panelCommunity = new JPanel (new FlowLayout(FlowLayout.RIGHT));
			panelCommunity.add(getLabelMyNote());
			panelCommunity.add(getPanelMyNote());
			panelCommunity.add(getButtonMyNote());
			panelCommunity.add(getLabelAvgNote());
			panelCommunity.add(getPanelAvgNote());
			panelCommunity.add(getButtonAvgNote());
		}
		return panelCommunity;
	}
	
	public JPanel getPanelRomInfo () {
		if (panelRomInfo==null) {
			panelRomInfo = new JPanel();
			panelRomInfo.add(getLabelTitle());
			panelRomInfo.add(getLabelIconCleanDump());
			panelRomInfo.add(getLabelIconWifi());
		}
		return panelRomInfo;
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
	
	public NoteDisplay getPanelAvgNote() {
		if (panelAvgNote==null) {
			panelAvgNote = new NoteDisplay (new Float(0));
		}
		return panelAvgNote;
	}

	public NoteDisplay getPanelMyNote() {
		if (panelMyNote == null) {
			panelMyNote = new NoteDisplay(new Float(0));
		}
		return panelMyNote;
	}

	public JButton getButtonAvgNote() {
		if (buttonAvgNote==null) {
			buttonAvgNote = new JButton ("+");
			buttonAvgNote.setToolTipText(Messages.getString("Community.avgVote"));
			if (!SessionHelper.isLogged(engine)) {
				buttonAvgNote.setEnabled(false);
			}
			buttonAvgNote.addActionListener(new ShowSeeVotesAction(engine));
		}
		return buttonAvgNote;
	}

	public JButton getButtonMyNote() {
		if (buttonMyNote==null) {
			buttonMyNote = new JButton ("+");
			buttonMyNote.setToolTipText(Messages.getString("Community.myVote"));
			if (!SessionHelper.isLogged(engine)) {
				buttonMyNote.setEnabled(false);
			}
			buttonMyNote.addActionListener(new ShowAddVoteAction(engine));
		}
		return buttonMyNote;
	}

	public JLabel getLabelMyNote() {
		if (labelMyNote==null) {
			labelMyNote = new JLabel(Messages.getString("Community.myNote"));
			labelMyNote.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return labelMyNote;
	}

	public JLabel getLabelAvgNote() {
		if (labelAvgNote==null) {
			labelAvgNote = new JLabel(Messages.getString("Community.avgNote"));
			labelAvgNote.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return labelAvgNote;
	}
}
