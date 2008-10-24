package rofage.ihm.windows.community;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import net.java.swingfx.waitwithstyle.PerformanceInfiniteProgressPanel;
import rofage.common.Engine;
import rofage.ihm.Messages;
import rofage.ihm.actions.common.HideAction;
import rofage.ihm.actions.common.community.RefreshBestOfAction;
import rofage.ihm.helper.ComboFilterHelper;

public class BestOfWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private Engine engine 	= null;
	
	private JPanel contentPane 		= null;
	private JPanel bestOfPanel		= null;
	
	private JScrollPane scrollPane  = null;
	
	private JButton buttonRefresh	= null;
	private JButton buttonClose		= null;
	
	private JLabel labelGenre 		= null;
	private JLabel labelLocation 	= null;
	
	private JComboBox comboGenre 	= null;
	private JComboBox comboLocation = null;
	
	private PerformanceInfiniteProgressPanel progressPane = new PerformanceInfiniteProgressPanel(true);
	

	/**
	 * This is the default constructor
	 */
	public BestOfWindow(Engine engine) {
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
		this.setSize(600, 480);
		this.setGlassPane(progressPane);
		this.setContentPane(getJContentPane());
		this.setTitle(Messages.getString("Community.bestOf"));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(true);
		this.setAlwaysOnTop(false);
		Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource("rofage/ihm/images/rom.png"));
		this.setIconImage(image);
		this.setVisible(false);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (contentPane == null) {
			contentPane = new JPanel();
			contentPane.setLayout(new BorderLayout(5,5));
			
			Box headerBox = Box.createHorizontalBox();
			
			Box boxLabels = Box.createVerticalBox();
			boxLabels.add(getLabelLocation());
			boxLabels.add(Box.createVerticalStrut(10));
			boxLabels.add(getLabelGenre());
			
			Box boxCombos = Box.createVerticalBox();
			boxCombos.add(getComboLocation());
			boxCombos.add(getComboGenre());
			
			headerBox.add(Box.createHorizontalStrut(10));
			headerBox.add(boxLabels);
			headerBox.add(Box.createHorizontalStrut(10));
			headerBox.add(boxCombos);
			headerBox.add(Box.createHorizontalGlue());
			headerBox.add(getButtonRefresh());
			headerBox.add(Box.createHorizontalStrut(10));
			
			Box headerVBox = Box.createVerticalBox();
			headerVBox.add(headerBox);
			headerVBox.add(Box.createVerticalStrut(5));
			headerVBox.add(new JSeparator(JSeparator.HORIZONTAL));
			headerVBox.add(Box.createVerticalStrut(5));
			
			contentPane.add(headerVBox, BorderLayout.NORTH);
			
			contentPane.add(getScrollPane(), BorderLayout.CENTER);
			
			Box buttonBox = Box.createHorizontalBox();
			buttonBox.add(Box.createHorizontalGlue());
			buttonBox.add(getButtonClose());
			
			Box buttonVBox = Box.createVerticalBox();
			buttonVBox.add(Box.createVerticalStrut(5));
			buttonVBox.add(new JSeparator(JSeparator.HORIZONTAL));
			buttonVBox.add(Box.createVerticalStrut(5));
			buttonVBox.add(buttonBox);
			
			contentPane.add(buttonVBox, BorderLayout.SOUTH);
		}
		return contentPane;
	}

	public JScrollPane getScrollPane() {
		if (scrollPane==null) {
			scrollPane = new JScrollPane(getBestOfPanel());
			scrollPane.setAutoscrolls(true);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		}
		return scrollPane;
	}

	public JButton getButtonRefresh() {
		if (buttonRefresh==null) {
			buttonRefresh = new JButton(Messages.getString("Refresh"));
			buttonRefresh.addActionListener(new RefreshBestOfAction(this, engine));
		}
		return buttonRefresh;
	}

	public JButton getButtonClose() {
		if (buttonClose==null) {
			buttonClose = new JButton (Messages.getString("Close"));
			buttonClose.addActionListener(new HideAction(this));
		}
		return buttonClose;
	}

	public JLabel getLabelGenre() {
		if (labelGenre == null) {
			labelGenre = new JLabel(Messages.getString("Genre"));
		}
		return labelGenre;
	}

	public JComboBox getComboGenre() {
		if (comboGenre==null) {
			comboGenre = new JComboBox();
			ComboFilterHelper.setFilterGenre(engine, comboGenre);			
		}
		return comboGenre;
	}

	public JPanel getBestOfPanel() {
		if (bestOfPanel==null) {
			bestOfPanel = new JPanel();
		}
		return bestOfPanel;
	}

	public PerformanceInfiniteProgressPanel getProgressPane() {
		return progressPane;
	}

	public JComboBox getComboLocation() {
		if (comboLocation==null) {
			comboLocation = new JComboBox();
			ComboFilterHelper.setFilterLocation(engine, comboLocation);
		}
		return comboLocation;
	}

	public JLabel getLabelLocation() {
		if (labelLocation==null) {
			labelLocation = new JLabel(Messages.getString("Location"));
		}
		return labelLocation;
	}
	
	
}
