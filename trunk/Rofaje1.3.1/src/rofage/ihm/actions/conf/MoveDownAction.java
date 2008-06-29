package rofage.ihm.actions.conf;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JList;

@SuppressWarnings("serial")
public class MoveDownAction extends AbstractAction {
	
	private JList jList = null;
	
	public MoveDownAction (JList jList) {
		this.jList = jList;
	}

	public void actionPerformed(ActionEvent arg0) {
		int index = jList.getSelectedIndex();
		DefaultListModel model = (DefaultListModel) jList.getModel();
		if (index!=-1 && index!=model.size()-1) {
			// A row has been selected and it's not the first one
			Object o = jList.getSelectedValue();
			model.remove(index);
			model.add(index+1, o);
			jList.setSelectedIndex(index+1);
			jList.ensureIndexIsVisible(index+1);
		}
	}

}
