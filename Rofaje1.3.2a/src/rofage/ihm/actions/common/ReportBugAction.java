package rofage.ihm.actions.common;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.AbstractAction;

@SuppressWarnings("serial") //$NON-NLS-1$
public class ReportBugAction extends AbstractAction {
	private static String bugReportURL = "http://code.google.com/p/rofage/issues/list";

	public ReportBugAction () {
	}
	
	public void actionPerformed(ActionEvent e) {
		// We check that the Desktop classes is supported
		if (Desktop.isDesktopSupported()) {
			// We get the desktop
			Desktop desktop = Desktop.getDesktop();
			
			// We check that the browse function is supported
			if (desktop.isSupported(Desktop.Action.BROWSE)) {
				try {
					desktop.browse(new URI(bugReportURL));
				} catch (URISyntaxException ex) {
					ex.printStackTrace();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			} else {
				System.out.println("Desktop.BROWSE is not supported, please report this manually");
			}
		} else {
			System.out.println("Desktop is not supported, please report this manually");
		}
	}

}
