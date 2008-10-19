package rofage.ihm.actions.common;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.AbstractAction;

@SuppressWarnings("serial") //$NON-NLS-1$
public class OpenWebLinkAction extends AbstractAction {
	public final static String urlBugReport = "http://code.google.com/p/rofage/issues/list";
	public final static String urlDonate = "https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=517060";
	
	private String url;

	public OpenWebLinkAction (String url) {
		this.url = url;
	}
	
	public void actionPerformed(ActionEvent e) {
		// We check that the Desktop classes is supported
		if (Desktop.isDesktopSupported()) {
			// We get the desktop
			Desktop desktop = Desktop.getDesktop();
			
			// We check that the browse function is supported
			if (desktop.isSupported(Desktop.Action.BROWSE)) {
				try {
					desktop.browse(new URI(url));
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
