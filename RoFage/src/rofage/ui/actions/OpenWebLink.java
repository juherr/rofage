package rofage.ui.actions;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import rofage.exceptions.CommonException;

@SuppressWarnings("serial")
public class OpenWebLink {
    private final static Log logger = LogFactory.getLog(OpenWebLink.class);

	public OpenWebLink (String url) {
        logger.debug("Opening url : "+url);
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
				new CommonException ("Desktop.BROWSE is not supported, please report this manually");
			}
		} else {
			new CommonException ("Desktop is not supported, please report this manually");
		}
	}

}
