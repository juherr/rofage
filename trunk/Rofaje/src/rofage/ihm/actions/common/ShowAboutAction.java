package rofage.ihm.actions.common;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import rofage.ihm.MainWindow;

@SuppressWarnings("serial")
public class ShowAboutAction extends AbstractAction {

	MainWindow mainWindow = null;
	public ShowAboutAction (MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}
	
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(mainWindow, "Rofage v0.01\nDéveloppé par Pierre Chastagner\n\nMerci à t4ils pour son soutien :)\n\nVous pouvez envoyer vos questions ou vos bugs à schyzo99@hotmail.com");
	}

}
