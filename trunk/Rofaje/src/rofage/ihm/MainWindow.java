package rofage.ihm;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;

import rofage.common.Engine;
import rofage.common.MainSwingWorker;
import rofage.common.object.Configuration;
import rofage.ihm.actions.common.ChangeConfInMainListener;
import rofage.ihm.actions.common.GameListSelectionListener;
import rofage.ihm.actions.common.ShowAboutAction;
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

	private JScrollPane jScrollPane = null;

	private JMenuItem jMenuItem1 = null;
	private JMenuItem menuItemConf = null;
	private JMenuItem menuItemScan = null;
	private JMenuItem menuItemRename = null;
	private JMenu menuConf = null;
	
	private ButtonGroup languageGroup = null;
	private JRadioButtonMenuItem langFR = null;
	
	private JComboBox comboConf = null;
		
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
		this.setSize(800, 600);
		this.setJMenuBar(getJJMenuBar());
		this.setPreferredSize(new Dimension(800, 600));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("RoFage v0.01");
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
			jMenu.setText("Langue");
			// We create the group	
			jMenu.add(getLangFR());
			languageGroup = new ButtonGroup();
			languageGroup.add(getLangFR());
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
			jMenu1.setText("?");
			jMenu1.add(getJMenuItem1());
			jMenu1.add(getJMenuItem());
		}
		return jMenu1;
	}
	
	private JMenu getMenuConf () {
		if (menuConf==null) {
			menuConf = new JMenu();
			menuConf.setText("Fichier");
			menuConf.add(getMenuItemConf());
			menuConf.add(getMenuItemScan());
			menuConf.add(getMenuItemRename());
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
			jMenuItem.setText("A Propos");
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
			jPanel.add(getJTextPane(), BorderLayout.CENTER);
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
			jTextPane.setText("Infos");
			jTextPane.setBackground(SystemColor.windowBorder);
			jTextPane.setVisible(true);
			jTextPane.setSize(new Dimension(460, 230));
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
			jMenuItem1.setText("Mise à jour");
			jMenuItem1.setVisible(true);
		}
		return jMenuItem1;
	}
	
	private JMenuItem getMenuItemConf() {
		if (menuItemConf==null) {
			menuItemConf = new JMenuItem();
			menuItemConf.addActionListener(new ShowConfigurationAction(engine));
			menuItemConf.setText("Configuration");
			menuItemConf.setVisible(true);
		}
		return menuItemConf;
	}
	
	private JMenuItem getMenuItemScan() {
		if (menuItemScan==null) {
			menuItemScan = new JMenuItem();
			menuItemScan.addActionListener(new ShowScanAction(engine));
			menuItemScan.setText("Scan");
			menuItemScan.setVisible(true);
		}
		return menuItemScan;
	}
	
	private JMenuItem getMenuItemRename() {
		if (menuItemRename==null) {
			menuItemRename = new JMenuItem();
			menuItemRename.addActionListener(new ShowRenameAction(engine));
			menuItemRename.setText("Renommer");
			menuItemRename.setVisible(true);
		}
		return menuItemRename;
	}
	
	private JRadioButtonMenuItem getLangFR() {
		if (langFR == null) {
			langFR = new JRadioButtonMenuItem();
			langFR.setText("Français");
			langFR.setSelected(true);
			langFR.setVisible(true);
		}
		return langFR;
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
}
