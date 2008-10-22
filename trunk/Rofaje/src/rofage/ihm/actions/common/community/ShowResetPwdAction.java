package rofage.ihm.actions.common.community;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import rofage.common.Engine;
import rofage.ihm.windows.community.ResetPwdWindow;

@SuppressWarnings("serial")
public class ShowResetPwdAction extends AbstractAction {

	private Engine engine = null;
	
	public ShowResetPwdAction (Engine engine) {
		this.engine = engine;
	}
	public void actionPerformed(ActionEvent e) {
		ResetPwdWindow win = new ResetPwdWindow(engine);
		win.setVisible(true);
	}

}
