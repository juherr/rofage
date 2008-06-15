package rofage.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;

import rofage.common.Consts;
import rofage.common.Engine;
import rofage.common.MainSwingWorker;
import rofage.common.helper.GameDisplayHelper;
import rofage.common.object.Configuration;
import rofage.common.object.Game;
import rofage.common.object.GenericDropDownEntry;
import rofage.ihm.actions.common.ChangeConfInMainListener;
import rofage.ihm.actions.common.ChangeLanguageListener;
import rofage.ihm.actions.common.FilterGameCollectionAction;
import rofage.ihm.actions.common.GameListSelectionListener;
import rofage.ihm.actions.common.ShowAboutAction;
import rofage.ihm.actions.common.ShowCleanAction;
import rofage.ihm.actions.common.ShowConfigurationAction;
import rofage.ihm.actions.common.ShowRenameAction;
import rofage.ihm.actions.common.ShowScanAction;
import rofage.ihm.actions.common.ShowUpdateAction;
import rofage.ihm.images.JPanelImage;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JMenuBar jJMenuBar = null;

	private JMenu jMenu = null;

	private JMenu jMenu1 = null;

	private JMenuItem jMenuItem = null;

	private JSplitPane jSplitPane = null;

	private GameListTable jTable = null;

	private JPanel jPanel = null;

	private JTextPane jTextPane = null;

	private JPanelImage jPanelImage1 = null;
	private JPanelImage jPanelImage2 = null;
	private JPanel jPanelImages = null;
	private JPanel panelFilter = null;

	private JScrollPane jScrollPane = null;

	private JMenuItem jMenuItem1 = null;
	private JMenuItem menuItemConf = null;
	private JMenuItem menuItemScan = null;
	private JMenuItem menuItemRename = null;
	private JMenuItem menuItemClean = null;
	private JMenu menuConf = null;
	
	private ButtonGroup languageGroup = null;  //  @jve:decl-index=0:
	private JRadioButtonMenuItem langFR = null;
	private JRadioButtonMenuItem langEN = null;
	
	private JComboBox comboConf = null;
	private JComboBox comboRomSize = null;
	private JComboBox comboLocation = null;
	private JComboBox comboLanguage = null;
	
	private JCheckBox CBOwned = null;
	private JCheckBox CBBadNamed = null;
	private JCheckBox CBNotOwned = null;
	
	private JTextField fieldTitle = null;
	private JTextField fieldPublisher = null;
	private JTextField fieldSource = null;
	
	private JLabel labelTitle = null;
	private JLabel labelRomSize = null;
	private JLabel labelLocation = null;
	private JLabel labelPublisher = null;
	private JLabel labelSource = null;
	private JLabel labelLanguage = null;
	
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
		this.setSize(1024, 768);
		this.setJMenuBar(getJJMenuBar());
		this.setPreferredSize(new Dimension(1024, 768));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle(Messages.getString("AppTitle") +" "+ Messages.getString("Version")); //$NON-NLS-1$
		this.setLocationRelativeTo(null);
		this.setVisible(false);
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
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jJMenuBar.add(getMenuConf());
			jJMenuBar.add(getJMenu());
			jJMenuBar.add(getJMenu1());
			jJMenuBar.add(getComboConf());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenu() {
		if (jMenu == null) {
			jMenu = new JMenu();
			jMenu.setText(Messages.getString("MainWindow.1")); //$NON-NLS-1$
			// We create the group	
			jMenu.add(getLangFR());
			jMenu.add(getLangEN());
			languageGroup = new ButtonGroup();
			languageGroup.add(getLangFR());
			languageGroup.add(getLangEN());
		}
		return jMenu;
	}

	/**
	 * This method initializes jMenu1	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenu1() {
		if (jMenu1 == null) {
			jMenu1 = new JMenu();
			jMenu1.setText("?"); //$NON-NLS-1$
			jMenu1.add(getJMenuItem1());
			jMenu1.add(getJMenuItem());
		}
		return jMenu1;
	}
	
	private JMenu getMenuConf () {
		if (menuConf==null) {
			menuConf = new JMenu();
			menuConf.setText(Messages.getString("MainWindow.3")); //$NON-NLS-1$
			menuConf.add(getMenuItemConf());
			menuConf.add(getMenuItemScan());
			menuConf.add(getMenuItemRename());
			menuConf.add(getMenuItemClean());
			menuConf.setVisible(true);
		}
		return menuConf;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem() {
		if (jMenuItem == null) {
			jMenuItem = new JMenuItem();
			jMenuItem.addActionListener(new ShowAboutAction(this));
			jMenuItem.setText(Messages.getString("MainWindow.4")); //$NON-NLS-1$
			jMenuItem.setVisible(true);
		}
		return jMenuItem;
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
				jTable = new GameListTable(engine.getGlobalConf().getSelectedConf().getTitlePattern());
			} else {
				jTable = new GameListTable(Configuration.DEFAULT_TITLEPATTERN);
			}
			ListSelectionModel listSelectionModel = jTable.getSelectionModel();
			listSelectionModel.addListSelectionListener(new GameListSelectionListener(engine));
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
			hBox.add(getJTextPane());
			hBox.add(getPanelFilter());
			jPanel.add(hBox, BorderLayout.SOUTH);
			jPanel.add(getJPanelImages(), BorderLayout.NORTH);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextPane	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	public JTextPane getJTextPane() {
		if (jTextPane == null) {
			jTextPane = new JTextPane();
			jTextPane.setBorder(BorderFactory.createTitledBorder(Messages.getString("MainWindow.5")));
			jTextPane.setVisible(true);
			jTextPane.setSize(new Dimension(150, 230));
			jTextPane.setPreferredSize(new Dimension(150,230));
		}
		return jTextPane;
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
			Box vBox2 = Box.createVerticalBox();
			vBox2.add(getCBOwned());
			vBox2.add(getCBBadName());
			vBox2.add(getCBNotOwned());
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
			jPanelImage1.setPreferredSize(new Dimension(256, 384));
		}
		return jPanelImage1;
	}
	
	public JPanelImage getJPanelImage2() {
		if (jPanelImage2 == null) {
			jPanelImage2 = new JPanelImage();
			jPanelImage2.setPreferredSize(new Dimension(256, 384));
		}
		return jPanelImage2;
	}

	/**
	 * This method initializes jMenuItem1	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem1() {
		if (jMenuItem1 == null) {
			jMenuItem1 = new JMenuItem();
			jMenuItem1.addActionListener(new ShowUpdateAction(engine));
			jMenuItem1.setText(Messages.getString("MainWindow.6")); //$NON-NLS-1$
			jMenuItem1.setVisible(true);
		}
		return jMenuItem1;
	}
	
	private JMenuItem getMenuItemConf() {
		if (menuItemConf==null) {
			menuItemConf = new JMenuItem();
			menuItemConf.addActionListener(new ShowConfigurationAction(engine));
			menuItemConf.setText(Messages.getString("MainWindow.7")); //$NON-NLS-1$
			menuItemConf.setVisible(true);
		}
		return menuItemConf;
	}
	
	private JMenuItem getMenuItemScan() {
		if (menuItemScan==null) {
			menuItemScan = new JMenuItem();
			menuItemScan.addActionListener(new ShowScanAction(engine));
			menuItemScan.setText(Messages.getString("MainWindow.8")); //$NON-NLS-1$
			menuItemScan.setVisible(true);
		}
		return menuItemScan;
	}
	
	private JMenuItem getMenuItemRename() {
		if (menuItemRename==null) {
			menuItemRename = new JMenuItem();
			menuItemRename.addActionListener(new ShowRenameAction(engine));
			menuItemRename.setText(Messages.getString("MainWindow.9")); //$NON-NLS-1$
			menuItemRename.setVisible(true);
		}
		return menuItemRename;
	}
	
	private JMenuItem getMenuItemClean() {
		if (menuItemClean==null) {
			menuItemClean = new JMenuItem();
			menuItemClean.addActionListener(new ShowCleanAction(engine));
			menuItemClean.setText(Messages.getString("MainWindow.10")); //$NON-NLS-1$
			menuItemClean.setVisible(true);
		}
		return menuItemClean;
	}
	
	public JRadioButtonMenuItem getLangFR() {
		if (langFR == null) {
			langFR = new JRadioButtonMenuItem();
			langFR.setText(Messages.getString("MainWindow.11")); //$NON-NLS-1$
			if (engine.getGlobalConf().getSelectedLocale()==Locale.FRENCH) langFR.setSelected(true);
			langFR.addItemListener(new ChangeLanguageListener(engine));
			langFR.setVisible(true);
		}
		return langFR;
	}
	
	public JRadioButtonMenuItem getLangEN() {
		if (langEN == null) {
			langEN = new JRadioButtonMenuItem();
			langEN.setText(Messages.getString("MainWindow.12"));
			if (engine.getGlobalConf().getSelectedLocale()==Locale.ENGLISH) langEN.setSelected(true);
			langEN.addItemListener(new ChangeLanguageListener(engine));
			langEN.setVisible(true);
		}
		return langEN;
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
	
	public JComboBox getComboConf () {
		if (comboConf==null) {
			comboConf = new JComboBox();
			// We add the items into the list
			Iterator<Configuration> iterConfigs = engine.getGlobalConf().getMapDatConfigs().values().iterator();
			while (iterConfigs.hasNext()) {
				String curConfName = iterConfigs.next().getConfName();
				comboConf.addItem(curConfName);
			}
			Configuration selectedConf = engine.getGlobalConf().getSelectedConf();
			if (selectedConf!=null) {
				String selectedConfName = selectedConf.getConfName();
				comboConf.setSelectedItem(selectedConfName);
			}
			comboConf.addItemListener(new ChangeConfInMainListener(engine));
			comboConf.setVisible(true);
		}
		return comboConf;
	}
	
	public JComboBox getComboRomSize () {
		if (comboRomSize==null) {
			comboRomSize = new JComboBox();
			// We add the items into the list (we have to retrieve any possible game size)
			Iterator<Game> iterGames = engine.getGameDB().getGameCollections().get(engine.getGlobalConf().getSelectedConf().getConfName()).values().iterator();
			// We have to sort the items before showing them
			TreeSet<Integer> treeSizes = new TreeSet<Integer>();
						
			while (iterGames.hasNext()) {
				Game game = iterGames.next();
				int size = Integer.parseInt(game.getRomSize());
				if (!treeSizes.contains(size)) {
					treeSizes.add(size);
				}
			}
			// Now we display the treeset
			comboRomSize.addItem(new GenericDropDownEntry("",""));
			Iterator<Integer> iterSizes = treeSizes.iterator();
			while (iterSizes.hasNext()) {
				int size = iterSizes.next();
				comboRomSize.addItem(new GenericDropDownEntry(String.valueOf(size), String.valueOf(size/(1024*1024)+ " MB")));
			}
			comboRomSize.setVisible(true);
		}
		return comboRomSize;
	}
	
	public JComboBox getComboLocation () {
		if (comboLocation==null) {
			comboLocation = new JComboBox();
			// We add the items into the list (we have to retrieve any possible game location)
			comboLocation.addItem(new GenericDropDownEntry("",""));
			Iterator<Game> iterGames = engine.getGameDB().getGameCollections().get(engine.getGlobalConf().getSelectedConf().getConfName()).values().iterator();
			List<String> list = new ArrayList<String>(); 
			while (iterGames.hasNext()) {
				Game game = iterGames.next();
				if (!list.contains(game.getLocation())) {
					list.add(game.getLocation());
					String locationText = GameDisplayHelper.getLocation(game);
					comboLocation.addItem(new GenericDropDownEntry(game.getLocation(), locationText));
				}
			}
			comboLocation.setVisible(true);
		}
		return comboLocation;
	}
	
	public JComboBox getComboLanguage () {
		if (comboLanguage==null) {
			comboLanguage = new JComboBox();
			
			// We sort the item in natural order for the ui
			TreeMap<String, String> langSet = new TreeMap<String, String>();
			for (int i=0; i<Consts.LANG_NAMES.size(); i++) {
				langSet.put(Consts.LANG_NAMES.get(i), String.valueOf(i));
			}
			
			comboLanguage.addItem(new GenericDropDownEntry("",""));
			Iterator<Entry<String, String>> iterLang = langSet.entrySet().iterator();
			while (iterLang.hasNext()) {
				Entry<String, String> entry = iterLang.next();
				comboLanguage.addItem(new GenericDropDownEntry(entry.getValue(), entry.getKey()));
			}
			comboLanguage.setVisible(true);
		}
		return comboLanguage;
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

	private JLabel getLabelLanguage() {
		if (labelLanguage==null) {
			labelLanguage = new JLabel();
			labelLanguage.setText(Messages.getString("Language"));
			labelLanguage.setVisible(true);
		}
		return labelLanguage;
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
	
	private JButton getButtonFilter() {
		if (buttonFilter==null) {
			buttonFilter = new JButton();
			buttonFilter.setText(Messages.getString("Filter"));
			buttonFilter.addActionListener(new FilterGameCollectionAction(engine));
			buttonFilter.setVisible(true);
		}
		return buttonFilter;
	}
}
