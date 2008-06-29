package rofage.ihm.importroms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import rofage.ihm.Messages;
import rofage.ihm.actions.common.HideAction;

public class ImportWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JProgressBar jProgressBar = null;

	private JTextArea jTextArea = null;

	private JPanel jPanel = null;

	private JButton jButton1 = null;
	
	private JScrollPane jScrollPane = null;
	
	/**
	 * This is the default constructor
	 */
	public ImportWindow() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(500, 300);
		this.setName("importWindow"); //$NON-NLS-1$
		this.setPreferredSize(new Dimension(500, 300));
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle(Messages.getString("ImportWindowTitle"));
		this.setLocationRelativeTo(null);
		this.setVisible(false);
		this.setAlwaysOnTop(true);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJProgressBar(), BorderLayout.SOUTH);
			jContentPane.add(getJTextArea(), BorderLayout.CENTER);
			jContentPane.add(getJPanel(), BorderLayout.EAST);
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jProgressBar	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	public JProgressBar getJProgressBar() {
		if (jProgressBar == null) {
			jProgressBar = new JProgressBar();
			jProgressBar.setName("progressBar"); //$NON-NLS-1$
			jProgressBar.setPreferredSize(new Dimension(148, 30));
			jProgressBar.setStringPainted(true);
		}
		return jProgressBar;
	}

	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	public JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setLineWrap(true);
			jTextArea.setEditable(false);
			jTextArea.setWrapStyleWord(true);
			jTextArea.setName("updateText"); //$NON-NLS-1$
		}
		return jTextArea;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new FlowLayout());
			jPanel.setPreferredSize(new Dimension(100, 50));
			jPanel.add(getJButton1(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setAction(new HideAction(this));
			jButton1.setFont(new Font("Dialog", Font.PLAIN, 10)); //$NON-NLS-1$
			jButton1.setName("jButton1"); //$NON-NLS-1$
			jButton1.setPreferredSize(new Dimension(100, 23));
			jButton1.setText(Messages.getString("RenameWindow.5")); //$NON-NLS-1$
		}
		return jButton1;
	}
	
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane(getJTextArea());
		}
		return jScrollPane;
	}
}
