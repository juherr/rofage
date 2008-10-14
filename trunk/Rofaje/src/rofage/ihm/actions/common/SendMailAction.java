package rofage.ihm.actions.common;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.AbstractAction;

import rofage.ihm.Messages;

@SuppressWarnings("serial") //$NON-NLS-1$
public class SendMailAction extends AbstractAction {
	private static String mail 		= "schyzo99@hotmail.com";
	private static String subject 	= Messages.getString("AppTitle"); 

	public SendMailAction () {
	}
	
	public void actionPerformed(ActionEvent e) {
		// We check that the Desktop classes is supported
		if (Desktop.isDesktopSupported()) {
			// We get the desktop
			Desktop desktop = Desktop.getDesktop();
			
			// We check that the browse function is supported
			if (desktop.isSupported(Desktop.Action.MAIL)) {
				try {
					desktop.mail(new URI("mailto:"+mail+"?subject="+subject+"&body=App%20version%20:%20"+Messages.getString("Version")));
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
