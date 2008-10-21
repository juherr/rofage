package rofage.ihm.windows.community;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextArea;

import net.java.swingfx.waitwithstyle.InfiniteProgressPanel;

import rofage.common.Engine;
import rofage.common.object.Comment;
import rofage.common.object.Game;
import rofage.ihm.Messages;
import rofage.ihm.actions.common.DisposeAction;
import rofage.ihm.actions.common.community.SaveVoteAction;

public class AddVoteWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private Engine engine 	= null;
	private Game game		= null;
	
	private JPanel contentPane 		= null;
	
	private JSlider sliderNote		= null;
	private JScrollPane scrollPane  = null;
	
	private JTextArea textComment	= null;
	
	private JButton buttonCancel	= null;
	private JButton buttonOK		= null;
	
	private Comment comment	= null; // The comment to be displayed
	
	private InfiniteProgressPanel progressPanel = new InfiniteProgressPanel();
	

	/**
	 * This is the default constructor
	 */
	public AddVoteWindow(Engine engine, Game game, Comment comment) {
		super();
		this.comment = comment;
		this.engine = engine;
		this.game = game;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		if (comment==null) {
			comment = new Comment();
		}
		this.setGlassPane(progressPanel);
		this.setSize(400, 260);
		this.setContentPane(getJContentPane());
		this.setTitle(Messages.getString("Community.addVoteTitle")+game.getTitle()); //$NON-NLS-1$
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(false);
		Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource("rofage/ihm/images/rom.png"));
		this.setIconImage(image);
		this.setVisible(true);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (contentPane == null) {
			contentPane = new JPanel();
			contentPane.setLayout(new FlowLayout());
			
			Box vBox = Box.createVerticalBox();
			vBox.add(getSliderNote());
			vBox.add(getScrollPane());
			
			Box buttonBox = Box.createHorizontalBox();
			buttonBox.add(getButtonCancel());
			buttonBox.add(Box.createHorizontalGlue());
			buttonBox.add(getButtonOK());
			
			vBox.add(Box.createVerticalStrut(5));
			vBox.add(new JSeparator(JSeparator.HORIZONTAL));
			vBox.add(Box.createVerticalStrut(5));
			vBox.add(buttonBox);
			contentPane.add(vBox);
			
		}
		return contentPane;
	}

	public JButton getButtonCancel() {
		if (buttonCancel == null) {
			buttonCancel = new JButton(Messages.getString("Cancel"));
			buttonCancel.addActionListener(new DisposeAction(this));
		}
		return buttonCancel;
	}

	public JButton getButtonOK() {
		if (buttonOK == null) {
			buttonOK = new JButton(Messages.getString("Community.confirm"));
			buttonOK.addActionListener(new SaveVoteAction(this, engine));
		}
		return buttonOK;
	}

	public JSlider getSliderNote() {
		if (sliderNote==null) {
			sliderNote = new JSlider(1, 9, 9);
			sliderNote.setBorder(BorderFactory.createTitledBorder(Messages.getString("Community.myNote")));
			sliderNote.setValue(comment.getNote().intValue());
			sliderNote.setMajorTickSpacing(4);
			sliderNote.setMinorTickSpacing(1);
			sliderNote.setPaintTicks(true);
			sliderNote.setPaintLabels(true);
			sliderNote.setVisible(true);
		}
		return sliderNote;
	}

	public JTextArea getTextComment() {
		if (textComment==null) {
			textComment = new JTextArea(5, 30);
			textComment.setBorder(BorderFactory.createTitledBorder(Messages.getString("Community.myComment")));
			textComment.setText(comment.getText());
			textComment.setLineWrap(true);
			textComment.setWrapStyleWord(true);
		}
		return textComment;
	}

	public JScrollPane getScrollPane() {
		if (scrollPane==null) {
			scrollPane = new JScrollPane(getTextComment());
			scrollPane.setAutoscrolls(true);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		}
		return scrollPane;
	}

	public Game getGame() {
		return game;
	}

	public InfiniteProgressPanel getProgressPanel() {
		return progressPanel;
	}
}
