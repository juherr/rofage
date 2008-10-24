package rofage.ihm.windows.community;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import net.java.swingfx.waitwithstyle.PerformanceInfiniteProgressPanel;
import rofage.common.Engine;
import rofage.common.object.Comment;
import rofage.ihm.Messages;
import rofage.ihm.NoteDisplay;
import rofage.ihm.actions.common.DisposeAction;
import rofage.ihm.actions.common.ShowAction;
import rofage.ihm.actions.common.community.AddUsefulCommentAction;

public class SeeVotesWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private Engine engine 	= null;
	private String gameTitle		= null;
	
	private JPanel contentPane 		= null;
	
	private JScrollPane scrollPane  = null;
	
	//private JTextPane textPane		= null;
	private JPanel panelComments 	= null;
	
	private JButton buttonClose		= null;
	
	private List<Comment> listComments = new ArrayList<Comment>(); // The comments to be displayed
	
	private PerformanceInfiniteProgressPanel progressPane = new PerformanceInfiniteProgressPanel(true);
	
	/**
	 * This is the default constructor
	 */
	public SeeVotesWindow(Engine engine, String gameTitle, List<Comment> listComments) {
		super();
		this.listComments = listComments;
		this.engine = engine;
		this.gameTitle = gameTitle;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(400, 260);
		this.setContentPane(getJContentPane());
		this.setTitle(Messages.getString("Community.seeVotes")+gameTitle);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(true);
		this.setAlwaysOnTop(false);
		Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource("rofage/ihm/images/rom.png"));
		this.setIconImage(image);
		this.setGlassPane(progressPane);
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
			vBox.add(getScrollPane());
			
			vBox.add(Box.createVerticalStrut(5));
			vBox.add(new JSeparator(JSeparator.HORIZONTAL));
			vBox.add(Box.createVerticalStrut(5));
			vBox.add(getButtonClose());
			contentPane.add(vBox);
			
		}
		return contentPane;
	}

	public JButton getButtonClose() {
		if (buttonClose == null) {
			buttonClose = new JButton(Messages.getString("Close"));
			buttonClose.addActionListener(new DisposeAction(this));
		}
		return buttonClose;
	}

	/*public JTextPane getTextPane() {
		if (textPane==null) {
			textPane = new JTextPane();
			textPane.setPreferredSize(new Dimension(375, 180));
			textPane.setEditable(false);
			// We initialize the styles
			Style stDefault = textPane.getStyle("default");
			Style stLogin = textPane.addStyle("stLogin", stDefault);
			StyleConstants.setFontFamily(stLogin, "Courier New");
			StyleConstants.setForeground(stLogin, Color.BLUE);
			StyleConstants.setItalic(stLogin, true);
			Style stNote = textPane.addStyle("stNote", stDefault);
			StyleConstants.setBold(stNote, true);
			StyledDocument stDoc = textPane.getStyledDocument();
			
			// We set the default content
			int pos = 0;
			try {
				Iterator<Comment> iterComments = listComments.iterator();
				while (iterComments.hasNext()) {
					Comment comment = iterComments.next();
					NoteDisplay noteDisplay = new NoteDisplay(comment.getNote());
					
					Style stIcon = stDoc.addStyle("icon", stDefault);
					StyleConstants.setIcon(stIcon, new ImageIcon(noteDisplay.getImg()));
					
					// We insert the login
					stDoc.insertString(stDoc.getLength(), comment.getLogin(), stLogin);
					DecimalFormat df = (DecimalFormat)DecimalFormat.getNumberInstance();
					df.applyPattern("0.#");
					stDoc.insertString(stDoc.getLength(), "  ("+df.format(comment.getNote())+")        ", stNote);
					// We insert the image
					stDoc.insertString(stDoc.getLength(), ":;:", stIcon);
					
					// And the comment
					stDoc.insertString(stDoc.getLength(), "\n"+comment.getText(), stDefault);
					// We finish with a separator
					stDoc.insertString(stDoc.getLength(), "\n\n\n", stDefault);
				}
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
		return textPane;
	}*/

	public JScrollPane getScrollPane() {
		if (scrollPane==null) {
			scrollPane = new JScrollPane(getPanelComments());
			scrollPane.setPreferredSize(new Dimension(375,180));
			scrollPane.setAutoscrolls(true);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		}
		return scrollPane;
	}

	public String getGameTitle() {
		return gameTitle;
	}

	public JPanel getPanelComments() {
		if (panelComments==null) {
			panelComments = new JPanel(new GridLayout(listComments.size(), 1, 0, 0));
			Iterator<Comment> iterComments = listComments.iterator();
			while (iterComments.hasNext()) {
				JPanel panelComment = new JPanel(new BorderLayout());
				Comment comment = iterComments.next();
				NoteDisplay noteDisplay = new NoteDisplay(comment.getNote());
				DecimalFormat df = (DecimalFormat)DecimalFormat.getNumberInstance();
				df.applyPattern("0.#");
				
				JLabel labelLogin = new JLabel(comment.getLogin()+"  ("+df.format(comment.getNote())+")");
				labelLogin.setIcon(new ImageIcon(noteDisplay.getImg()));
				panelComment.add(labelLogin, BorderLayout.WEST);
								
				JTextArea labelCommentText = new JTextArea(5,1);
				labelCommentText.setBackground(Color.DARK_GRAY);
				labelCommentText.setText(comment.getText());
				labelCommentText.setEnabled(false);
				labelCommentText.setLineWrap(true);
				labelCommentText.setWrapStyleWord(true);
				panelComment.add(labelCommentText, BorderLayout.SOUTH);
				
				Box boxBttns = Box.createVerticalBox();
				
				JButton bSpoil = new JButton(Messages.getString("Community.spoiler"));
				bSpoil.addActionListener(new ShowAction(labelCommentText));
				bSpoil.setPreferredSize(new Dimension(130,20));
				bSpoil.setSize(new Dimension(130,20));
				bSpoil.setMinimumSize(new Dimension(130,20));
				bSpoil.setMaximumSize(new Dimension(130,20));
				boxBttns.add(bSpoil);
				if (!comment.isSpoiler()) {
					bSpoil.setEnabled(false);
				} else {
					labelCommentText.setVisible(false);
				}

				JButton bUseful = new JButton(Messages.getString("Community.usefulComment"));
				bUseful.setPreferredSize(new Dimension(130,20));
				bUseful.setSize(new Dimension(130,20));
				bUseful.setMinimumSize(new Dimension(130,20));
				bUseful.setMaximumSize(new Dimension(130,20));
				bUseful.addActionListener(new AddUsefulCommentAction(this, comment.getIdComment(), engine));
				boxBttns.add(bUseful);
				boxBttns.setSize(new Dimension(130,40));
				boxBttns.setPreferredSize(new Dimension(130,40));
				panelComment.add(boxBttns, BorderLayout.EAST);
				
				panelComments.add(panelComment);
			}
		}
		return panelComments;
	}

	public PerformanceInfiniteProgressPanel getProgressPane() {
		return progressPane;
	}
}
