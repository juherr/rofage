package rofage.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import net.iharder.dnd.FileDrop;
import rofage.common.Engine;
import rofage.common.MainSwingWorker;
import rofage.common.dnd.RomListener;
import rofage.common.object.Configuration;
import rofage.ihm.actions.CompressAction;
import rofage.ihm.actions.ExportAction;
import rofage.ihm.actions.common.FilterGameCollectionAction;
import rofage.ihm.actions.common.GameListSelectionListener;
import rofage.ihm.actions.common.PopupListener;
import rofage.ihm.helper.ComboFilterHelper;
import rofage.ihm.images.JPanelImage;
import rofage.ihm.menu.MainMenuBar;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private JFileChooser folderChooser = null;
	
	private JPanel jContentPane = null;

	private MainMenuBar mainMenuBar = null;

	private JPopupMenu popMenu = null;
	private JMenu popMenuExport = null;
	private JMenuItem popMenuItemExportToFolder = null;
	private JMenuItem popMenuItemCompress = null;
	
	private JSplitPane jSplitPane = null;

	private GameListTable jTable = null;

	private JPanel jPanel = null;

	private JPanelImage jPanelImage1 = null;
	private JPanelImage jPanelImage2 = null;
	private JPanel jPanelImages = null;
	private JPanel panelFilter = null;
	private JPanel panelStatusBar = null;
	private PanelRomHeader panelRomHeader = null;
	private PanelGameInfos panelGameInfos = null;

	private JScrollPane jScrollPane = null;
	private JScrollPane scrollImages = null;

	private JComboBox comboRomSize = null;
	private JComboBox comboLocation = null;
	private JComboBox comboLanguage = null;
	private JComboBox comboGenre = null;
	
	private JCheckBox CBOwned = null;
	private JCheckBox CBBadNamed = null;
	private JCheckBox CBNotOwned = null;
	private JCheckBox CBWifi = null;
	private JCheckBox CBDemo = null;
	private JCheckBox CBNotClean = null;
	
	private JTextField fieldTitle = null;
	private JTextField fieldPublisher = null;
	private JTextField fieldSource = null;
	
	private JLabel labelTitle = null;
	private JLabel labelRomSize = null;
	private JLabel labelLocation = null;
	private JLabel labelPublisher = null;
	private JLabel labelSource = null;
	private JLabel labelLanguage = null;
	private JLabel labelGenre = null;
	private JLabel labelStatusIconOwned = null;
	private JLabel labelStatusIconBadNamed = null;
	private JLabel labelStatusIconNotOwned = null;
	private JLabel labelStatusIconClean = null;
	private JLabel labelStatusIconNotClean = null;
	
	private JProgressBar progressBarImage = null;
	
	
	private JButton buttonFilter = null;
		
	private Engine engine;
	
	/**
	 * This is the default constructor
	 */
	public MainWindow(Engine engine) {
		super();
		this.engine = engine;
		initialize();
		MainSwingWorker sw = new MainSwingWorker(engine);
		sw.execute();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(1024, 800);
		this.setJMenuBar(getMainMenuBar());
		this.setPreferredSize(new Dimension(1024, 800));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle(Messages.getString("AppTitle") +" "+ Messages.getString("Version")); //$NON-NLS-1$
		this.setLocationRelativeTo(null);
		Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource("rofage/ihm/images/rom.png"));
		this.setIconImage(image);
		this.setVisible(false);
		
		// We add the drop file support
		new FileDrop(this, true, new RomListener(engine));
		
		
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
			jContentPane.add(getPanelRomHeader(), BorderLayout.NORTH);
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
			jContentPane.add(getPanelStatusBar(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	public JPopupMenu getPopMenu() {
		if (popMenu == null) {
			popMenu = new JPopupMenu();
			popMenu.add(getPopMenuExport());
			popMenu.add(getPopMenuItemCompress());
		}
		return popMenu;
	}
	
	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setLeftComponent(getJScrollPane());
			jSplitPane.setRightComponent(getJPanel());
			jSplitPane.setDividerLocation(250);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable
	 */
	public JTable getJTable() {
		if (jTable == null) {
			if (engine.getGlobalConf().getSelectedConf()!=null) {
				jTable = new GameListTable(engine.getGlobalConf().getSelectedConf().getTitlePattern(), engine);
			} else {
				jTable = new GameListTable(Configuration.DEFAULT_TITLEPATTERN, engine);
			}
			jTable.addMouseListener(new PopupListener(engine));
			ListSelectionModel listSelectionModel = jTable.getSelectionModel();
			listSelectionModel.addListSelectionListener(new GameListSelectionListener(engine));
			jTable.setShowVerticalLines(false);
		}
		return jTable;
	}
	
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane(getJTable());
			jScrollPane.setAutoscrolls(true);
		}
		return jScrollPane;
	}
	
	public JScrollPane getScrollImages() {
		if (scrollImages == null) {
			scrollImages = new JScrollPane(getJPanelImages());
			scrollImages.setAutoscrolls(true);
		}
		return scrollImages;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			Box hBox = Box.createHorizontalBox();
			hBox.add(getPanelGameInfos());
			hBox.add(getPanelFilter());
			jPanel.add(hBox, BorderLayout.SOUTH);
			jPanel.add(getScrollImages(), BorderLayout.CENTER);
		}
		return jPanel;
	}
	
	public JPanel getPanelStatusBar () {
		if (panelStatusBar==null) {
			panelStatusBar = new JPanel();
			panelStatusBar.setLayout(new BoxLayout(panelStatusBar, BoxLayout.LINE_AXIS));
			panelStatusBar.add(getLabelStatusIconNotOwned());
			panelStatusBar.add(Box.createRigidArea(new Dimension(20,20)));
			panelStatusBar.add(getLabelStatusIconBadNamed());
			panelStatusBar.add(Box.createRigidArea(new Dimension(20,20)));
			panelStatusBar.add(getLabelStatusIconOwned());
			panelStatusBar.add(Box.createRigidArea(new Dimension(20,20)));
			panelStatusBar.add(new JSeparator(JSeparator.VERTICAL));
			panelStatusBar.add(Box.createRigidArea(new Dimension(20,20)));
			panelStatusBar.add(getLabelStatusIconClean());
			panelStatusBar.add(Box.createRigidArea(new Dimension(20,20)));
			panelStatusBar.add(getLabelStatusIconNotClean());
			panelStatusBar.add(Box.createRigidArea(new Dimension(20,20)));
			panelStatusBar.add(new JSeparator(JSeparator.VERTICAL));
			// We add the progress bar
			panelStatusBar.add(Box.createHorizontalGlue());
			panelStatusBar.add(getProgressBarImage());
			panelStatusBar.add(Box.createRigidArea(new Dimension(10,20)));
			//panelStatusBar.setPreferredSize(new Dimension(1024,20));
			panelStatusBar.setVisible(true);
		}
		return panelStatusBar;
	}

	public JPanel getJPanelImages() {
		if (jPanelImages == null) {
			jPanelImages = new JPanel();
			jPanelImages.setPreferredSize(new Dimension(10, 400));
			jPanelImages.setLayout(new FlowLayout());
			jPanelImages.add(getJPanelImage1());
			jPanelImages.add(getJPanelImage2());
			jPanelImages.setMinimumSize(new Dimension(530, 10));
		}
		return jPanelImages;
	}
	
	public JPanel getPanelFilter() {
		if (panelFilter == null) {
			panelFilter = new JPanel();
			panelFilter.setLayout(new FlowLayout());
			panelFilter.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0)), Messages.getString("FiltersTitle")));
			Box vBox = Box.createVerticalBox();
			vBox.add(getLabelTitle());
			vBox.add(getFieldTitle());
			vBox.add(getLabelRomSize());
			vBox.add(getComboRomSize());
			vBox.add(getLabelLocation());
			vBox.add(getComboLocation());
			vBox.add(getLabelPublisher());
			vBox.add(getFieldPublisher());
			vBox.add(getLabelSource());
			vBox.add(getFieldSource());
			vBox.add(getLabelLanguage());
			vBox.add(getComboLanguage());
			vBox.add(getLabelGenre());
			vBox.add(getComboGenre());
			Box vBox2 = Box.createVerticalBox();
			vBox2.add(getCBDemo());
			vBox2.add(getCBOwned());
			vBox2.add(getCBBadName());
			vBox2.add(getCBNotOwned());
			vBox2.add(getCBWifi());
			vBox2.add(getCBNotClean());
			vBox2.add(getButtonFilter());
			Box hBox = Box.createHorizontalBox();
			hBox.add(vBox);
			hBox.add(new JSeparator(JSeparator.VERTICAL));
			hBox.add(vBox2);
			panelFilter.add(hBox);
			panelFilter.setVisible(true);
		}
		return panelFilter;
	}
	
	public JPanelImage getJPanelImage1() {
		if (jPanelImage1 == null) {
			jPanelImage1 = new JPanelImage();
			Configuration conf = engine.getGlobalConf().getSelectedConf();
			int width = 0;
			int height = 0;
			if (conf!=null) {
				width = conf.getScreenshotWidth();
				height = conf.getScreenshotHeight();
			} 
			jPanelImage1.setPreferredSize(new Dimension(width, height));
		}
		return jPanelImage1;
	}
	
	public JPanelImage getJPanelImage2() {
		if (jPanelImage2 == null) {
			jPanelImage2 = new JPanelImage();
			Configuration conf = engine.getGlobalConf().getSelectedConf();
			int width = 0;
			int height = 0;
			if (conf!=null) {
				width = conf.getScreenshotWidth();
				height = conf.getScreenshotHeight();
			} 
			jPanelImage2.setPreferredSize(new Dimension(width, height));
		}
		return jPanelImage2;
	}

	
	
	private JMenu getPopMenuExport() {
		if (popMenuExport == null) {
			popMenuExport = new JMenu();
			popMenuExport.add(getPopMenuItemExportToFolder());
			popMenuExport.setText(Messages.getString("Export")); //$NON-NLS-1$
		}
		return popMenuExport;
	}
	
	
	
	private JMenuItem getPopMenuItemExportToFolder() {
		if (popMenuItemExportToFolder == null) {
			popMenuItemExportToFolder = new JMenuItem();
			popMenuItemExportToFolder.addActionListener(new ExportAction(engine));
			popMenuItemExportToFolder.setText(Messages.getString("ExportToFolder")); //$NON-NLS-1$
			popMenuItemExportToFolder.setVisible(true);
		}
		return popMenuItemExportToFolder;
	}
	
	private JMenuItem getPopMenuItemCompress() {
		if (popMenuItemCompress == null) {
			popMenuItemCompress = new JMenuItem();
			popMenuItemCompress.addActionListener(new CompressAction(engine));
			popMenuItemCompress.setText(Messages.getString("MainWindow.14")); //$NON-NLS-1$
			popMenuItemCompress.setVisible(true);
		}
		return popMenuItemCompress;
	}
	
	public JTextField getFieldTitle () {
		if (fieldTitle==null) {
			fieldTitle = new JTextField();
			fieldTitle.setVisible(true);
		}
		return fieldTitle;
	}
	
	public JTextField getFieldPublisher () {
		if (fieldPublisher==null) {
			fieldPublisher = new JTextField();
			fieldPublisher.setVisible(true);
		}
		return fieldPublisher;
	}
	
	public JTextField getFieldSource () {
		if (fieldSource==null) {
			fieldSource = new JTextField();
			fieldSource.setVisible(true);
		}
		return fieldSource;
	}
	
	public JComboBox getComboRomSize () {
		if (comboRomSize==null) {
			comboRomSize = new JComboBox();
			// We add the items into the list (we have to retrieve any possible game size)
			ComboFilterHelper.setFilterSize(engine, comboRomSize);
			comboRomSize.setVisible(true);
		}
		return comboRomSize;
	}
	
	public JComboBox getComboLocation () {
		if (comboLocation==null) {
			comboLocation = new JComboBox();
			ComboFilterHelper.setFilterLocation(engine, comboLocation);
			comboLocation.setVisible(true);
		}
		return comboLocation;
	}
	
	public JComboBox getComboLanguage () {
		if (comboLanguage==null) {
			comboLanguage = new JComboBox();
			ComboFilterHelper.setFilterLanguage(comboLanguage);
			comboLanguage.setVisible(true);
		}
		return comboLanguage;
	}
	
	public JComboBox getComboGenre () {
		if (comboGenre==null) {
			comboGenre = new JComboBox();
			ComboFilterHelper.setFilterGenre(engine, comboGenre);
			comboGenre.setVisible(true);
		}
		return comboGenre;
	}
	
	public JCheckBox getCBBadName () {
		if (CBBadNamed==null) {
			CBBadNamed = new JCheckBox();
			CBBadNamed.setText(Messages.getString("BadNamed"));
			CBBadNamed.setSelected(true);
			CBBadNamed.setVisible(true);
		}
		return CBBadNamed;
	}
	
	public JCheckBox getCBOwned () {
		if (CBOwned==null) {
			CBOwned = new JCheckBox();
			CBOwned.setText(Messages.getString("Owned"));
			CBOwned.setSelected(true);
			CBOwned.setVisible(true);
		}
		return CBOwned;
	}
	
	public JCheckBox getCBNotOwned () {
		if (CBNotOwned==null) {
			CBNotOwned = new JCheckBox();
			CBNotOwned.setText(Messages.getString("NotOwned"));
			CBNotOwned.setSelected(true);
			CBNotOwned.setVisible(true);
		}
		return CBNotOwned;
	}
	
	public JCheckBox getCBWifi () {
		if (CBWifi==null) {
			CBWifi = new JCheckBox();
			CBWifi.setText(Messages.getString("ShowWifi"));
			CBWifi.setSelected(true);
			CBWifi.setVisible(true);
		}
		return CBWifi;
	}
	
	public JCheckBox getCBDemo () {
		if (CBDemo==null) {
			CBDemo = new JCheckBox();
			CBDemo.setText(Messages.getString("Demo"));
			CBDemo.setSelected(true);
			CBDemo.setVisible(true);
		}
		return CBDemo;
	}
	
	public JCheckBox getCBNotClean () {
		if (CBNotClean==null) {
			CBNotClean = new JCheckBox();
			CBNotClean.setText(Messages.getString("NotClean"));
			CBNotClean.setSelected(true);
			CBNotClean.setVisible(true);
		}
		return CBNotClean;
	}

	private JLabel getLabelLanguage() {
		if (labelLanguage==null) {
			labelLanguage = new JLabel();
			labelLanguage.setText(Messages.getString("Language"));
			labelLanguage.setVisible(true);
		}
		return labelLanguage;
	}
	
	private JLabel getLabelGenre() {
		if (labelGenre==null) {
			labelGenre = new JLabel();
			labelGenre.setText(Messages.getString("Genre"));
			labelGenre.setVisible(true);
		}
		return labelGenre;
	}
	
	public JLabel getLabelStatusIconNotOwned() {
		if (labelStatusIconNotOwned==null) {
			labelStatusIconNotOwned = new JLabel();
			labelStatusIconNotOwned.setIcon(new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/no_rom.png")));
			labelStatusIconNotOwned.setText("0");
			labelStatusIconNotOwned.setToolTipText(Messages.getString("IconHelper.11"));
			labelStatusIconNotOwned.setVisible(true);
		}
		return labelStatusIconNotOwned;
	}
	
	public JLabel getLabelStatusIconClean() {
		if (labelStatusIconClean==null) {
			labelStatusIconClean = new JLabel();
			labelStatusIconClean.setIcon(new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/rom_clean.png")));
			labelStatusIconClean.setToolTipText(Messages.getString("IconHelper.17"));
			labelStatusIconClean.setText("0");
			labelStatusIconClean.setVisible(true);
		}
		return labelStatusIconClean;
	}
	
	public JLabel getLabelStatusIconNotClean() {
		if (labelStatusIconNotClean==null) {
			labelStatusIconNotClean = new JLabel();
			labelStatusIconNotClean.setIcon(new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/rom_notclean.png")));
			labelStatusIconNotClean.setToolTipText(Messages.getString("IconHelper.15"));
			labelStatusIconNotClean.setText("0");
			labelStatusIconNotClean.setVisible(true);
		}
		return labelStatusIconNotClean;
	}
	
	
	
	public JLabel getLabelStatusIconOwned() {
		if (labelStatusIconOwned==null) {
			labelStatusIconOwned = new JLabel();
			labelStatusIconOwned.setIcon(new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/rom.png")));
			labelStatusIconOwned.setText("0");
			labelStatusIconOwned.setToolTipText(Messages.getString("IconHelper.7"));
			labelStatusIconOwned.setVisible(true);
		}
		return labelStatusIconOwned;
	}
	
	public JLabel getLabelStatusIconBadNamed() {
		if (labelStatusIconBadNamed==null) {
			labelStatusIconBadNamed = new JLabel();
			labelStatusIconBadNamed.setIcon(new ImageIcon(getClass().getClassLoader().getResource("rofage/ihm/images/rom_badname.png")));
			labelStatusIconBadNamed.setText("0");
			labelStatusIconBadNamed.setToolTipText(Messages.getString("IconHelper.9"));
			labelStatusIconBadNamed.setVisible(true);
		}
		return labelStatusIconBadNamed;
	}

	private JLabel getLabelLocation() {
		if (labelLocation==null) {
			labelLocation = new JLabel();
			labelLocation.setText(Messages.getString("Location"));
			labelLocation.setVisible(true);
		}
		return labelLocation;
	}

	private JLabel getLabelPublisher() {
		if (labelPublisher==null) {
			labelPublisher = new JLabel();
			labelPublisher.setText(Messages.getString("Publisher"));
			labelPublisher.setVisible(true);
		}
		return labelPublisher;
	}

	private JLabel getLabelRomSize() {
		if (labelRomSize==null) {
			labelRomSize = new JLabel();
			labelRomSize.setText(Messages.getString("RomSize"));
			labelRomSize.setVisible(true);
		}
		return labelRomSize;
	}

	private JLabel getLabelSource() {
		if (labelSource==null) {
			labelSource = new JLabel();
			labelSource.setText(Messages.getString("Source"));
			labelSource.setVisible(true);
		}
		return labelSource;
	}

	private JLabel getLabelTitle() {
		if (labelTitle==null) {
			labelTitle = new JLabel();
			labelTitle.setText(Messages.getString("Title"));
			labelTitle.setVisible(true);
		}
		return labelTitle;
	}
	
	public JProgressBar getProgressBarImage() {
		if (progressBarImage==null) {
			progressBarImage = new JProgressBar();
			progressBarImage.setName("progressBar"); //$NON-NLS-1$
			progressBarImage.setPreferredSize(new Dimension(20, 20));
			progressBarImage.setStringPainted(true);
			progressBarImage.setVisible(true);
		}
		return progressBarImage;
	}
	
	private JButton getButtonFilter() {
		if (buttonFilter==null) {
			buttonFilter = new JButton();
			buttonFilter.setText(Messages.getString("Filter"));
			buttonFilter.addActionListener(new FilterGameCollectionAction(engine));
			buttonFilter.setVisible(true);
		}
		return buttonFilter;
	}
	
	public JFileChooser getFolderChooser() {
		if (folderChooser==null) {
			folderChooser = new JFileChooser();
			folderChooser.setVisible(true);
			folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}
		return folderChooser;
	}

	public PanelRomHeader getPanelRomHeader() {
		if (panelRomHeader==null) {
			panelRomHeader = new PanelRomHeader(engine);
			panelRomHeader.setMaximumSize(new Dimension(jContentPane.getWidth(), 50));
			panelRomHeader.setMinimumSize(new Dimension(jContentPane.getWidth(), 50));
			panelRomHeader.setVisible(true);
		}
		return panelRomHeader;
	}
	
	public PanelGameInfos getPanelGameInfos() {
		if (panelGameInfos==null) {
			panelGameInfos = new PanelGameInfos();
			panelGameInfos.setVisible(true);
		}
		return panelGameInfos;
	}

	public MainMenuBar getMainMenuBar() {
		if (mainMenuBar==null) {
			mainMenuBar = new MainMenuBar(engine, this);
		}
		return mainMenuBar;
	}
}
