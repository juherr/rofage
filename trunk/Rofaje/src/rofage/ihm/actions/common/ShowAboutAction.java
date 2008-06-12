package rofage.ihm.actions.common;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import rofage.ihm.MainWindow;
import rofage.ihm.Messages;

@SuppressWarnings("serial") //$NON-NLS-1$
public class ShowAboutAction extends AbstractAction {

	MainWindow mainWindow = null;
	public ShowAboutAction (MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}
	
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(mainWindow, Messages.getString("ShowAboutAction.1")); //$NON-NLS-1$
	}

}
