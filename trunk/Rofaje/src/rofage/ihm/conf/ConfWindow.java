package rofage.ihm.conf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
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
import rofage.ihm.actions.common.HideAction;
import rofage.ihm.actions.conf.AddConfAction;
import rofage.ihm.actions.conf.ChangeConfInConfListener;
import rofage.ihm.actions.conf.OpenFileChooserAction;
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
	private JLabel labelAutoUpdate = null;
	private JLabel labelTitlePattern = null;
	
	private JTextField fieldRomFolder = null;
	private JTextField fieldUnknownRomFolder = null;
	private JTextField fieldTitlePattern = null;
	
	private JButton buttonOpenRomFolder = null;
	private JButton buttonOpenUnknownRomFolder = null;
	private JButton buttonSave = null;
	private JButton buttonCancel = null;
	private JButton buttonAddConf = null;
	
	private JCheckBox CBmoveUnknownRoms = null;
	private JCheckBox CBAutoUpdate = null;
	
	private JTextPane textPaneTitlePattern = null;
	
	private JComboBox comboConf = null;
	
	private FileChooserFilter xmlFilter = null;
	
	private Engine engine = null;
	private boolean panesCreated = false;

	/**
	 * This is the default constructor
	 */
	public ConfWindow(Engine engine) {
		super();
		this.engine = engine; 
		xmlFilter = new FileChooserFilter(new String[]{".xml"}, "Fichiers XML (*.xml)");
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
		this.setTitle("Configuration");
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
			if (engine.getGlobalConf().getMapDatConfigs().size()>0) {
				// We add aditionnal panes only when at least one DAT has been addded
				jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
				jContentPane.add(getPanelGlobalButtons(), BorderLayout.SOUTH);
				panesCreated = true;
			}
		}
		return jContentPane;
	}
	
	/**
	 * Adds the panes that has not been created if the global configuration didn't exist.
	 *
	 */
	public void addAdditionalPanes () {
		jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
		jContentPane.add(getPanelGlobalButtons(), BorderLayout.SOUTH);
	}

	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("Dossiers", getPanelFolder());
			jTabbedPane.addTab("Titre des roms", getPanelTitlePattern());
			jTabbedPane.addTab("Mise à jour", getPanelAutoUpdate());
			jTabbedPane.setSize(500, 300);
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
			hBox.add(getLabelTitlePattern());
			hBox.add(getFieldTitlePattern());
			vBox.add(hBox);
			vBox.add(getTextPaneTitlePattern());
			panelTitlePattern.add(vBox);
			panelTitlePattern.setVisible(true);
		}
		return panelTitlePattern;
	}
	
	private JPanel getPanelGlobalButtons () {
		if (panelGlobalButtons==null) {
			panelGlobalButtons = new JPanel();
			panelGlobalButtons.add(getButtonCancel());
			panelGlobalButtons.add(getButtonSave());
			panelGlobalButtons.setVisible(true);
		}
		return panelGlobalButtons;
	}
	
	private JPanel getPanelGlobalConf () {
		if (panelGlobalConf==null) {
			panelGlobalConf = new JPanel();
			panelGlobalConf.add(getComboConf());
			panelGlobalConf.add(getButtonAddConf());
			panelGlobalConf.setVisible(true);
		}
		return panelGlobalConf;
	}
	
	private JPanel getPanelAutoUpdate() {
		if (panelAutoUpdate==null) {
			panelAutoUpdate = new JPanel();
			panelAutoUpdate.add(getCBAutoUpdate());
			panelAutoUpdate.add(getLabelAutoUpdate());
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
			labelRomFolder.setText("Répertoire des roms");
		}
		return labelRomFolder;
	}
	
	private JLabel getLabelUnknownRomFolder () {
		if (labelUnknownRomFolder==null) {
			labelUnknownRomFolder = new JLabel();
			labelUnknownRomFolder.setVisible(true);
			labelUnknownRomFolder.setText("vers");
		}
		return labelUnknownRomFolder;
	}
	
	private JLabel getLabelAutoUpdate () {
		if (labelAutoUpdate==null) {
			labelAutoUpdate = new JLabel();
			labelAutoUpdate.setText("Mettre à jour automatiquement le dat et les images");
			labelAutoUpdate.setVisible(true);
		}
		return labelAutoUpdate;
	}
	
	private JLabel getLabelTitlePattern () {
		if (labelTitlePattern==null) {
			labelTitlePattern = new JLabel();
			labelTitlePattern.setText("Pattern d'écriture des roms");
			labelTitlePattern.setVisible(true);
		}
		return labelTitlePattern;
	}
	
	public JTextField getFieldRomFolder() {
		if (fieldRomFolder==null) {
			fieldRomFolder = new JTextField();
			fieldRomFolder.setVisible(true);
			fieldRomFolder.setSize(200, 20);
			fieldRomFolder.setPreferredSize(new Dimension(200,20));
			
			if (engine.getGlobalConf().getSelectedConf().getRomFolder()!=null 
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
			if (engine.getGlobalConf().getSelectedConf().getRomFolderMove()!=null 
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
			if (engine.getGlobalConf().getSelectedConf().getTitlePattern()!=null 
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
			buttonOpenRomFolder.setText("Ouvrir...");
			buttonOpenRomFolder.setVisible(true);
		}
		return buttonOpenRomFolder;
	}
	
	public JButton getButtonOpenUnknownRomFolder() {
		if (buttonOpenUnknownRomFolder==null) {
			buttonOpenUnknownRomFolder = new JButton();
			buttonOpenUnknownRomFolder.addActionListener(new OpenFileChooserAction(engine));
			buttonOpenUnknownRomFolder.setText("Ouvrir...");
			buttonOpenUnknownRomFolder.setVisible(true);
		}
		return buttonOpenUnknownRomFolder;
	}
	
	private JButton getButtonSave () {
		if (buttonSave==null) {
			buttonSave = new JButton();
			buttonSave.addActionListener(new SaveConfigurationAction (engine));
			buttonSave.setText("Sauver");
			buttonSave.setVisible(true);
		}
		return buttonSave;
	}
	
	private JButton getButtonCancel () {
		if (buttonCancel==null) {
			buttonCancel = new JButton();
			buttonCancel.addActionListener(new HideAction(this));
			buttonCancel.setText("Annuler");
			buttonCancel.setVisible(true);
		}
		return buttonCancel;
	}
	
	private JButton getButtonAddConf () {
		if (buttonAddConf==null) {
			buttonAddConf = new JButton();
			buttonAddConf.addActionListener(new AddConfAction(engine));
			buttonAddConf.setText("Ajouter");
			buttonAddConf.setVisible(true);
		}
		return buttonAddConf;
	}
	
	public JCheckBox getCBmoveUnknownRoms() {
		if (CBmoveUnknownRoms==null) {
			CBmoveUnknownRoms = new JCheckBox();
			CBmoveUnknownRoms.setVisible(true);
			CBmoveUnknownRoms.setSelected(engine.getGlobalConf().getSelectedConf().isMoveUnknownRoms());			
		}
		return CBmoveUnknownRoms;
	}
	
	public JCheckBox getCBAutoUpdate() {
		if (CBAutoUpdate==null) {
			CBAutoUpdate = new JCheckBox();
			CBAutoUpdate.setSelected(engine.getGlobalConf().getSelectedConf().isUpdateAtStartup());
			CBAutoUpdate.setVisible(true);
		}
		return CBAutoUpdate;
	}
	
	private JTextPane getTextPaneTitlePattern () {
		if (textPaneTitlePattern==null) {
			textPaneTitlePattern = new JTextPane();
			textPaneTitlePattern.setText("%n N° de la release\t%t titre\t\t%s Taille de la rom\n%l Location\t\t%p Editeur\t\t%S source\n%L langues\t\t%c CRC\t\t%M Multi\n%C Code Pays");
			textPaneTitlePattern.setBackground(null);
			textPaneTitlePattern.setMargin(new Insets(20,0,0,0));
			textPaneTitlePattern.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0)), "Patterns disponibles"));
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

	public boolean isPanesCreated() {
		return panesCreated;
	}

	public void setPanesCreated(boolean panesCreated) {
		this.panesCreated = panesCreated;
	}
}
