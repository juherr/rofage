package rofage.ihm.conf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import rofage.common.Engine;
import rofage.common.object.Configuration;
import rofage.ihm.Messages;
import rofage.ihm.actions.common.HideAction;
import rofage.ihm.actions.conf.AddConfAction;
import rofage.ihm.actions.conf.ChangeConfInConfListener;
import rofage.ihm.actions.conf.OpenFileChooserAction;
import rofage.ihm.actions.conf.RemoveConfAction;
import rofage.ihm.actions.conf.SaveConfigurationAction;

public class ConfWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JTabbedPane jTabbedPane = null;
	
	private JPanel panelFolder = null;
	private JPanel panelTitlePattern = null;
	private JPanel panelAutoUpdate = null;
	private JPanel panelGlobalButtons = null;
	private JPanel panelGlobalConf = null;
	
	private JFileChooser folderChooser = null;
	private JFileChooser xMLChooser = null;
	
	private JLabel labelRomFolder = null;
	private JLabel labelUnknownRomFolder = null;
	private JLabel labelTitlePattern = null;
	private JLabel labelRenameInside = null;
	
	private JTextField fieldRomFolder = null;
	private JTextField fieldUnknownRomFolder = null;
	private JTextField fieldTitlePattern = null;
	
	private JButton buttonOpenRomFolder = null;
	private JButton buttonOpenUnknownRomFolder = null;
	private JButton buttonSave = null;
	private JButton buttonCancel = null;
	private JButton buttonAddConf = null;
	private JButton buttonRemoveConf = null;
	
	private JCheckBox CBmoveUnknownRoms = null;
	private JCheckBox CBAutoUpdate = null;
	private JCheckBox CBRenameInside = null;
	private JCheckBox CBInAppUpdate = null;
	
	private JTextPane textPaneTitlePattern = null;
	
	private JComboBox comboConf = null;
	
	private FileChooserFilter xmlFilter = null;
	
	private Engine engine = null;

	/**
	 * This is the default constructor
	 */
	public ConfWindow(Engine engine) {
		super();
		this.engine = engine; 
		xmlFilter = new FileChooserFilter(new String[]{".xml"}, Messages.getString("ConfWindow.1")); //$NON-NLS-1$ //$NON-NLS-2$
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(500, 300);
		this.setContentPane(getJContentPane());
		this.setTitle(Messages.getString("ConfWindow.2")); //$NON-NLS-1$
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setResizable(false);
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
			jContentPane.add(getPanelGlobalConf(), BorderLayout.NORTH);
			jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
			jContentPane.add(getPanelGlobalButtons(), BorderLayout.SOUTH);
			if (engine.getGlobalConf().getMapDatConfigs().size()>0) {
				// We add aditionnal panes only when at least one DAT has been addded
				getJTabbedPane().setVisible(true);
				getPanelGlobalButtons().setVisible(true);
			}
		}
		return jContentPane;
	}
	
	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	public JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab(Messages.getString("ConfWindow.3"), getPanelFolder()); //$NON-NLS-1$
			jTabbedPane.addTab(Messages.getString("ConfWindow.4"), getPanelTitlePattern()); //$NON-NLS-1$
			jTabbedPane.addTab(Messages.getString("ConfWindow.5"), getPanelAutoUpdate()); //$NON-NLS-1$
			jTabbedPane.setSize(500, 300);
			jTabbedPane.setVisible(false);
		}
		return jTabbedPane;
	}
	
	private JPanel getPanelFolder () {
		if (panelFolder==null) {
			panelFolder = new JPanel();
			panelFolder.setVisible(true);
			panelFolder.setLayout(new FlowLayout());
			Box boxLabelsFolders = Box.createVerticalBox();
			boxLabelsFolders.add(getLabelRomFolder());
			Box boxMoveRomTo = Box.createHorizontalBox();
			boxMoveRomTo.add(getCBmoveUnknownRoms());
			boxMoveRomTo.add(getLabelUnknownRomFolder());
			boxLabelsFolders.add(boxMoveRomTo);
			
			Box boxFieldsFolders = Box.createVerticalBox();
			boxFieldsFolders.add(getFieldRomFolder());
			boxFieldsFolders.add(getFieldUnknownRomFolder());
			
			Box boxOpenButtons = Box.createVerticalBox();
			boxOpenButtons.add(getButtonOpenRomFolder());
			boxOpenButtons.add(getButtonOpenUnknownRomFolder());
			
			panelFolder.add(boxLabelsFolders);
			panelFolder.add(boxFieldsFolders);
			panelFolder.add(boxOpenButtons);
			
			
		}
		return panelFolder;
	}
	
	private JPanel getPanelTitlePattern () {
		if (panelTitlePattern==null) {
			panelTitlePattern = new JPanel();
			Box vBox = Box.createVerticalBox();
			Box hBox = Box.createHorizontalBox();
			Box hBox2= Box.createHorizontalBox();
			hBox.add(getLabelTitlePattern());
			hBox.add(getFieldTitlePattern());
			hBox2.add(getCBRenameInside());
			hBox2.add(getLabelRenameInside());
			vBox.add(hBox);
			vBox.add(getTextPaneTitlePattern());
			vBox.add(hBox2);
			panelTitlePattern.add(vBox);
			panelTitlePattern.setVisible(true);
		}
		return panelTitlePattern;
	}
	
	public JPanel getPanelGlobalButtons () {
		if (panelGlobalButtons==null) {
			panelGlobalButtons = new JPanel();
			panelGlobalButtons.add(getButtonCancel());
			panelGlobalButtons.add(getButtonSave());
			panelGlobalButtons.setVisible(false);
		}
		return panelGlobalButtons;
	}
	
	private JPanel getPanelGlobalConf () {
		if (panelGlobalConf==null) {
			panelGlobalConf = new JPanel();
			panelGlobalConf.add(getComboConf());
			panelGlobalConf.add(getButtonAddConf());
			panelGlobalConf.add(getButtonRemoveConf());
			if (engine.getGlobalConf().getMapDatConfigs().size()==0) {
				getButtonRemoveConf().setVisible(false);
			}
			panelGlobalConf.setVisible(true);
		}
		return panelGlobalConf;
	}
	
	private JPanel getPanelAutoUpdate() {
		if (panelAutoUpdate==null) {
			panelAutoUpdate = new JPanel();
			panelAutoUpdate.setLayout(new BoxLayout(panelAutoUpdate, BoxLayout.PAGE_AXIS));
			panelAutoUpdate.setAlignmentX(Component.LEFT_ALIGNMENT);
			panelAutoUpdate.add(getCBAutoUpdate());
			panelAutoUpdate.add(getCBInAppUpdate());
			panelAutoUpdate.setVisible(true);
		}
		return panelAutoUpdate;
	}
	
	public JFileChooser getXMLChooser() {
		if (xMLChooser==null) {
			xMLChooser = new JFileChooser();
			xMLChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			xMLChooser.addChoosableFileFilter(xmlFilter);
			xMLChooser.setVisible(true);
		}
		return xMLChooser;
	}
	
	public JFileChooser getFolderChooser() {
		if (folderChooser==null) {
			folderChooser = new JFileChooser();
			folderChooser.setVisible(true);
			folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}
		return folderChooser;
	}
		
	private JLabel getLabelRomFolder () {
		if (labelRomFolder==null) {
			labelRomFolder = new JLabel();
			labelRomFolder.setVisible(true);
			labelRomFolder.setText(Messages.getString("ConfWindow.6")); //$NON-NLS-1$
		}
		return labelRomFolder;
	}
	
	private JLabel getLabelUnknownRomFolder () {
		if (labelUnknownRomFolder==null) {
			labelUnknownRomFolder = new JLabel();
			labelUnknownRomFolder.setVisible(true);
			labelUnknownRomFolder.setText(Messages.getString("ConfWindow.7")); //$NON-NLS-1$
		}
		return labelUnknownRomFolder;
	}
	
	private JLabel getLabelTitlePattern () {
		if (labelTitlePattern==null) {
			labelTitlePattern = new JLabel();
			labelTitlePattern.setText(Messages.getString("ConfWindow.9")); //$NON-NLS-1$
			labelTitlePattern.setVisible(true);
		}
		return labelTitlePattern;
	}
	
	private JLabel getLabelRenameInside () {
		if (labelRenameInside==null) {
			labelRenameInside = new JLabel();
			labelRenameInside.setText(Messages.getString("ConfWindow.10")); //$NON-NLS-1$
			labelRenameInside.setVisible(true);
		}
		return labelRenameInside;
	}
	
	public JTextField getFieldRomFolder() {
		if (fieldRomFolder==null) {
			fieldRomFolder = new JTextField();
			fieldRomFolder.setVisible(true);
			fieldRomFolder.setSize(200, 20);
			fieldRomFolder.setPreferredSize(new Dimension(200,20));
			
			if (engine.getGlobalConf().getSelectedConf()!=null 
					&& !engine.getGlobalConf().getSelectedConf().getRomFolder().isEmpty()) {
				fieldRomFolder.setText(engine.getGlobalConf().getSelectedConf().getRomFolder());
			}
		}
		return fieldRomFolder;
	}
	
	public JTextField getFieldUnknownRomFolder() {
		if (fieldUnknownRomFolder==null) {
			fieldUnknownRomFolder = new JTextField();
			fieldUnknownRomFolder.setVisible(true);
			fieldUnknownRomFolder.setSize(200,20);
			fieldUnknownRomFolder.setPreferredSize(new Dimension(200,20));
			if (engine.getGlobalConf().getSelectedConf()!=null 
					&& !engine.getGlobalConf().getSelectedConf().getRomFolderMove().isEmpty()) {
				fieldUnknownRomFolder.setText(engine.getGlobalConf().getSelectedConf().getRomFolderMove());
			}
		}
		return fieldUnknownRomFolder;
	}
	
	public JTextField getFieldTitlePattern () {
		if (fieldTitlePattern==null) {
			fieldTitlePattern = new JTextField();
			fieldTitlePattern.setVisible(true);
			fieldTitlePattern.setSize(200,20);
			fieldTitlePattern.setPreferredSize(new Dimension(200,20));
			if (engine.getGlobalConf().getSelectedConf()!=null 
					&& !engine.getGlobalConf().getSelectedConf().getTitlePattern().isEmpty()) {
				fieldTitlePattern.setText(engine.getGlobalConf().getSelectedConf().getTitlePattern());
			}
		}
		return fieldTitlePattern;
	}
	
	public JButton getButtonOpenRomFolder() {
		if (buttonOpenRomFolder==null) {
			buttonOpenRomFolder = new JButton();
			buttonOpenRomFolder.addActionListener(new OpenFileChooserAction(engine));
			buttonOpenRomFolder.setText(Messages.getString("ConfWindow.11")); //$NON-NLS-1$
			buttonOpenRomFolder.setVisible(true);
		}
		return buttonOpenRomFolder;
	}
	
	public JButton getButtonOpenUnknownRomFolder() {
		if (buttonOpenUnknownRomFolder==null) {
			buttonOpenUnknownRomFolder = new JButton();
			buttonOpenUnknownRomFolder.addActionListener(new OpenFileChooserAction(engine));
			buttonOpenUnknownRomFolder.setText(Messages.getString("ConfWindow.12")); //$NON-NLS-1$
			buttonOpenUnknownRomFolder.setVisible(true);
		}
		return buttonOpenUnknownRomFolder;
	}
	
	private JButton getButtonSave () {
		if (buttonSave==null) {
			buttonSave = new JButton();
			buttonSave.addActionListener(new SaveConfigurationAction (engine));
			buttonSave.setText(Messages.getString("ConfWindow.13")); //$NON-NLS-1$
			buttonSave.setVisible(true);
		}
		return buttonSave;
	}
	
	private JButton getButtonCancel () {
		if (buttonCancel==null) {
			buttonCancel = new JButton();
			buttonCancel.addActionListener(new HideAction(this));
			buttonCancel.setText(Messages.getString("ConfWindow.14")); //$NON-NLS-1$
			buttonCancel.setVisible(true);
		}
		return buttonCancel;
	}
	
	private JButton getButtonAddConf () {
		if (buttonAddConf==null) {
			buttonAddConf = new JButton();
			buttonAddConf.addActionListener(new AddConfAction(engine));
			buttonAddConf.setText(Messages.getString("ConfWindow.15")); //$NON-NLS-1$
			buttonAddConf.setVisible(true);
		}
		return buttonAddConf;
	}
	
	public JButton getButtonRemoveConf () {
		if (buttonRemoveConf==null) {
			buttonRemoveConf = new JButton();
			buttonRemoveConf.addActionListener(new RemoveConfAction(engine));
			buttonRemoveConf.setText(Messages.getString("ConfWindow.16")); //$NON-NLS-1$
			buttonRemoveConf.setVisible(true);
		}
		return buttonRemoveConf;
	}
	
	public JCheckBox getCBmoveUnknownRoms() {
		if (CBmoveUnknownRoms==null) {
			CBmoveUnknownRoms = new JCheckBox();
			CBmoveUnknownRoms.setVisible(true);
			if (engine.getGlobalConf().getSelectedConf()!=null) {
				CBmoveUnknownRoms.setSelected(engine.getGlobalConf().getSelectedConf().isMoveUnknownRoms());			
			}
		}
		return CBmoveUnknownRoms;
	}
	
	public JCheckBox getCBAutoUpdate() {
		if (CBAutoUpdate==null) {
			CBAutoUpdate = new JCheckBox();
			if (engine.getGlobalConf().getSelectedConf()!=null) {
				CBAutoUpdate.setSelected(engine.getGlobalConf().getSelectedConf().isUpdateAtStartup());	
			}
			CBAutoUpdate.setText(Messages.getString("ConfWindow.8"));
			CBAutoUpdate.setVisible(true);
		}
		return CBAutoUpdate;
	}
	
	public JCheckBox getCBRenameInside () {
		if (CBRenameInside==null) {
			CBRenameInside = new JCheckBox();
			if (engine.getGlobalConf().getSelectedConf()!=null) {
				CBRenameInside.setSelected(engine.getGlobalConf().getSelectedConf().isRenameInside());	
			}
			CBRenameInside.setVisible(true);
		}
		return CBRenameInside;
	}
	
	public JCheckBox getCBInAppUpdate () {
		if (CBInAppUpdate==null) {
			CBInAppUpdate = new JCheckBox();
			CBInAppUpdate.setText(Messages.getString("InAppUpdate"));
			if (engine.getGlobalConf().getSelectedConf()!=null) {
				CBInAppUpdate.setSelected(engine.getGlobalConf().getSelectedConf().isInAppUpdate());	
			}
			CBInAppUpdate.setVisible(true);
		}
		return CBInAppUpdate;
	}
	
	private JTextPane getTextPaneTitlePattern () {
		if (textPaneTitlePattern==null) {
			textPaneTitlePattern = new JTextPane();
			textPaneTitlePattern.setText(Messages.getString("ConfWindow.17")); //$NON-NLS-1$
			textPaneTitlePattern.setBackground(null);
			textPaneTitlePattern.setMargin(new Insets(20,0,0,0));
			textPaneTitlePattern.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0)), Messages.getString("ConfWindow.18"))); //$NON-NLS-1$
			textPaneTitlePattern.setVisible(true);
		}
		return textPaneTitlePattern;
	}
	
	public JComboBox getComboConf () {
		if (comboConf==null) {
			comboConf = new JComboBox();
			// We add the items into the list
			Iterator<Configuration> iterConfigs = engine.getGlobalConf().getMapDatConfigs().values().iterator();
			while (iterConfigs.hasNext()) {
				comboConf.addItem(iterConfigs.next().getConfName());
			}
			comboConf.addItemListener(new ChangeConfInConfListener(engine));
			comboConf.setVisible(true);
		}
		return comboConf;
	}
}
