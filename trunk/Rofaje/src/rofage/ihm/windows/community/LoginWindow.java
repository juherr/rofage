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
import rofage.ihm.actions.common.community.LoginAction;
import rofage.ihm.actions.common.community.ShowCreateAccntAction;

public class LoginWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private Engine engine = null;
	
	private JPanel contentPane 		= null;
	
	private JTextField fieldLogin 	= null;
	
	private JPasswordField fieldPwd = null;
	
	private JLabel labelLogin		= null;
	private JLabel labelPwd			= null;
	
	private JButton buttonCancel		= null;
	private JButton buttonOK			= null;
	private JButton buttonCreateAccnt = null;

	/**
	 * This is the default constructor
	 */
	public LoginWindow(Engine engine) {
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
		this.setSize(300, 150);
		this.setContentPane(getJContentPane());
		this.setTitle(Messages.getString("Community.login")); //$NON-NLS-1$
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
			JPanel fieldPanel = new JPanel(new GridLayout(2,2,3,3));
			fieldPanel.add(getLabelLogin());
			fieldPanel.add(getFieldLogin());
			fieldPanel.add(getLabelPwd());
			fieldPanel.add(getFieldPwd());
			Box buttonBox = Box.createHorizontalBox();
			buttonBox.add(getButtonCancel());
			buttonBox.add(Box.createHorizontalGlue());
			buttonBox.add(getButtonOK());
			Box vBox = Box.createVerticalBox();
			vBox.add(fieldPanel);
			vBox.add(buttonBox);
			vBox.add(Box.createVerticalStrut(5));
			vBox.add(new JSeparator(JSeparator.HORIZONTAL));
			vBox.add(Box.createVerticalStrut(5));
			vBox.add(getButtonCreateAccount());
			
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

	public JPasswordField getFieldPwd() {
		if (fieldPwd == null) {
			fieldPwd = new JPasswordField();
		}
		return fieldPwd;
	}

	public JLabel getLabelLogin() {
		if (labelLogin == null) {
			labelLogin = new JLabel(Messages.getString("Community.login"));
		}
		return labelLogin;
	}

	public JLabel getLabelPwd() {
		if (labelPwd == null) {
			labelPwd = new JLabel(Messages.getString("Community.password"));
		}
		return labelPwd;
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
			buttonOK = new JButton(Messages.getString("Community.connect"));
			buttonOK.addActionListener(new LoginAction(engine));
		}
		return buttonOK;
	}

	public JButton getButtonCreateAccount() {
		if (buttonCreateAccnt == null) {
			buttonCreateAccnt = new JButton(Messages.getString("Community.createAccnt"));
			buttonCreateAccnt.addActionListener(new ShowCreateAccntAction(this.engine));
		}
		return buttonCreateAccnt;
	}
}
