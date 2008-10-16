package rofage.ihm.actions.conf;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import rofage.common.Consts;
import rofage.common.Engine;
import rofage.common.helper.GameDisplayHelper;
import rofage.common.object.GenericDropDownEntry;
import rofage.ihm.GenericDropDownEntryRenderer;
import rofage.ihm.Messages;
import rofage.ihm.helper.IconHelper;

@SuppressWarnings("serial")
public class PanelDuplicate extends JPanel {
	
	private Engine engine;
	
	private JList jListLocation 	= null;
	private JButton bUp				= null;
	private JButton bDown			= null;
	private JScrollPane scrollList 	= null;
	private JLabel labelDuplicates	= null;
	
	public PanelDuplicate (Engine engine) {
		this.engine = engine;
		
		setLayout(new FlowLayout());
		Box vBoxButtons = Box.createVerticalBox();
		vBoxButtons.add(getBUp());
		vBoxButtons.add(Box.createRigidArea(new Dimension(10,20)));
		vBoxButtons.add(getBDown());
		
		Box hBox = Box.createHorizontalBox();
		hBox.add(vBoxButtons);
		hBox.add(getScrollList());
		
		Box globalBox = Box.createVerticalBox();
		globalBox.add(getLabelDuplicates());
		globalBox.add(hBox);
		add(globalBox);
	}

	public JButton getBDown() {
		if (bDown==null) {
			bDown = new JButton();
			bDown.setText(Messages.getString("Down"));
			bDown.addActionListener(new MoveDownAction(getJListLocation()));
		}
		return bDown;
	}

	public JButton getBUp() {
		if (bUp==null) {
			bUp = new JButton();
			bUp.setText(Messages.getString("Up"));
			bUp.addActionListener(new MoveUpAction(getJListLocation()));
		}
		return bUp;
	}

	public JList getJListLocation() {
		if (jListLocation==null) {
			jListLocation = new JList();
			DefaultListModel model = new DefaultListModel();
			if (engine.getGlobalConf().getSelectedConf()!=null) {
				model = populateLocationModel(engine.getGlobalConf().getSelectedConf().getListLocations());
			} else {
				model = populateLocationModel(null);
			}
			jListLocation.setModel(model);
			jListLocation.setVisibleRowCount(14);
			jListLocation.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jListLocation.setCellRenderer(new GenericDropDownEntryRenderer());
		}
		return jListLocation;
	}
	
	private JScrollPane getScrollList () {
		if (scrollList==null) {
			scrollList = new JScrollPane(getJListLocation());
			scrollList.setAutoscrolls(false);
			scrollList.setVisible(true);
		}
		return scrollList;
	}
	
	private DefaultListModel populateLocationModel (List<String> listLocations) {
		DefaultListModel model = new DefaultListModel();
		
		if (listLocations==null) {
			// We have to build the locations from scratch
			// We order them in natural order of their screen names
			TreeMap<String, String> treeLocations = new TreeMap<String, String>();
			for (int i=0; i<Consts.COUNTRY_CODES.size(); i++) {
				treeLocations.put(Consts.COUNTRY_CODES.get(String.valueOf(i)), GameDisplayHelper.getLocation(String.valueOf(i)));
			}
//			 Now we display the treeset
			Iterator<Entry<String, String>> iterLocations = treeLocations.entrySet().iterator();
			while (iterLocations.hasNext()) {
				Entry<String, String> entry = iterLocations.next();
				ImageIcon icon = IconHelper.getLocationIconFromCountryCode(entry.getKey().toLowerCase());
				model.addElement(new GenericDropDownEntry(entry.getKey(), entry.getValue(), icon));
			}
		} else {
			// Here the list is already defined !
			for (int i=0; i<listLocations.size(); i++) {
				String countryCode = listLocations.get(i);
				String countryName = Consts.getNameFromCountryCode(countryCode);
				ImageIcon icon = IconHelper.getLocationIconFromCountryCode(countryCode.toLowerCase());
				model.addElement(new GenericDropDownEntry(countryCode, countryName, icon));
			}
		}
		return model;		
	}

	public JLabel getLabelDuplicates() {
		if (labelDuplicates==null) {
			labelDuplicates = new JLabel();
			labelDuplicates.setText(Messages.getString("DuplicateExplanation"));
		}
		return labelDuplicates;
	}			
}
