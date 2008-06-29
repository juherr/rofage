package rofage.ihm.clean;

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

import rofage.common.Engine;
import rofage.ihm.Messages;
import rofage.ihm.actions.common.HideAction;
import rofage.ihm.actions.common.StopAction;
import rofage.ihm.actions.compress.CompressAction;

public class CompressWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JProgressBar jProgressBar = null;

	private JTextArea jTextArea = null;

	private JPanel jPanel = null;

	private JButton jButton = null;

	private JButton jButton1 = null;
	private JButton buttonStop = null;

	private JScrollPane jScrollPane = null;
	
	private Engine engine = null;

	/**
	 * This is the default constructor
	 */
	public CompressWindow(Engine engine) {
		super();
		this.engine = engine;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(500, 300);
		this.setName("compressWindow"); //$NON-NLS-1$
		this.setPreferredSize(new Dimension(500, 300));
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle(Messages.getString("CompressWindow.1"));
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
			jTextArea.setName("compressText"); //$NON-NLS-1$
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
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getButtonStop(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setAction(new CompressAction(engine));
			jButton.setText(Messages.getString("CompressWindow.0")); //$NON-NLS-1$
			jButton.setName("jButton"); //$NON-NLS-1$
			jButton.setPreferredSize(new Dimension(100, 23));
			jButton.setFont(new Font("Dialog", Font.PLAIN, 10)); //$NON-NLS-1$
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setAction(new HideAction(this));
			jButton1.setFont(new Font("Dialog", Font.PLAIN, 10)); //$NON-NLS-1$
			jButton1.setName("jButton1"); //$NON-NLS-1$
			jButton1.setPreferredSize(new Dimension(100, 23));
			jButton1.setText(Messages.getString("CompressWindow.5")); //$NON-NLS-1$
		}
		return jButton1;
	}
	
	/**
	 * This method initializes buttonStop	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getButtonStop() {
		if (buttonStop == null) {
			buttonStop = new JButton();
			buttonStop.setAction(new StopAction(engine, StopAction.SW_CLEAN));
			buttonStop.setFont(new Font("Dialog", Font.PLAIN, 10)); //$NON-NLS-1$
			buttonStop.setName("buttonStop"); //$NON-NLS-1$
			buttonStop.setPreferredSize(new Dimension(100, 23));
			buttonStop.setText(Messages.getString("CompressWindow.8")); //$NON-NLS-1$
			buttonStop.setVisible(true);
			buttonStop.setEnabled(false);
		}
		return buttonStop;
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
