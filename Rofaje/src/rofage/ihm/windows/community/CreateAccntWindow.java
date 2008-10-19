package rofage.ihm.windows.community;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import rofage.common.Engine;
import rofage.ihm.Messages;
import rofage.ihm.actions.common.HideAction;
import rofage.ihm.actions.common.community.CreateAccntAction;

public class CreateAccntWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private Engine engine = null;
	
	private JPanel contentPane 		= null;
	
	private JTextField fieldLogin 	= null;
	private JTextField fieldEmail 	= null;
	
	private JPasswordField fieldPwd = null;
	private JPasswordField fieldPwd2= null;
	
	private JLabel labelLogin		= null;
	private JLabel labelEmail		= null;
	private JLabel labelPwd			= null;
	private JLabel labelPwd2		= null;
	
	private JButton buttonCancel	= null;
	private JButton buttonOK		= null;
	

	/**
	 * This is the default constructor
	 */
	public CreateAccntWindow(Engine engine) {
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
		this.setSize(300, 175);
		this.setContentPane(getJContentPane());
		this.setTitle(Messages.getString("Community.createAccnt")); //$NON-NLS-1$
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(false);
		Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource("rofage/ihm/images/rom.png"));
		this.setIconImage(image);
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
			JPanel fieldPanel = new JPanel(new GridLayout(4,2,3,3));
			fieldPanel.add(getLabelLogin());
			fieldPanel.add(getFieldLogin());
			fieldPanel.add(getLabelEmail());
			fieldPanel.add(getFieldEmail());
			fieldPanel.add(getLabelPwd());
			fieldPanel.add(getFieldPwd());
			fieldPanel.add(getLabelPwd2());
			fieldPanel.add(getFieldPwd2());
			Box buttonBox = Box.createHorizontalBox();
			buttonBox.add(getButtonCancel());
			buttonBox.add(Box.createHorizontalGlue());
			buttonBox.add(getButtonOK());
			Box vBox = Box.createVerticalBox();
			vBox.add(fieldPanel);
			vBox.add(Box.createVerticalStrut(5));
			vBox.add(new JSeparator(JSeparator.HORIZONTAL));
			vBox.add(Box.createVerticalStrut(5));
			vBox.add(buttonBox);
			contentPane.add(vBox);
			
		}
		return contentPane;
	}

	public JTextField getFieldLogin() {
		if (fieldLogin == null) {
			fieldLogin = new JTextField();
		}
		return fieldLogin;
	}
	
	public JTextField getFieldEmail() {
		if (fieldEmail == null) {
			fieldEmail = new JTextField();
		}
		return fieldEmail;
	}

	public JPasswordField getFieldPwd() {
		if (fieldPwd == null) {
			fieldPwd = new JPasswordField();
		}
		return fieldPwd;
	}
	
	public JPasswordField getFieldPwd2() {
		if (fieldPwd2 == null) {
			fieldPwd2 = new JPasswordField();
		}
		return fieldPwd2;
	}

	public JLabel getLabelLogin() {
		if (labelLogin == null) {
			labelLogin = new JLabel(Messages.getString("Community.login"));
		}
		return labelLogin;
	}
	
	public JLabel getLabelEmail() {
		if (labelEmail == null) {
			labelEmail = new JLabel(Messages.getString("Community.email"));
		}
		return labelEmail;
	}

	public JLabel getLabelPwd() {
		if (labelPwd == null) {
			labelPwd = new JLabel(Messages.getString("Community.password"));
		}
		return labelPwd;
	}
	
	public JLabel getLabelPwd2() {
		if (labelPwd2 == null) {
			labelPwd2 = new JLabel(Messages.getString("Community.confirm"));
		}
		return labelPwd2;
	}

	public JButton getButtonCancel() {
		if (buttonCancel == null) {
			buttonCancel = new JButton(Messages.getString("Cancel"));
			buttonCancel.addActionListener(new HideAction(this));
		}
		return buttonCancel;
	}

	public JButton getButtonOK() {
		if (buttonOK == null) {
			buttonOK = new JButton(Messages.getString("Community.createAccnt"));
			buttonOK.addActionListener(new CreateAccntAction(engine));
		}
		return buttonOK;
	}
}
