package rofage.toolkits;


import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This toolkit provides useful method to work on File(or related) objects
 * @author Pierre Chastagner
 */
public abstract class FileToolkit {
    private final static Log logger = LogFactory.getLog(FileToolkit.class);

    FileFilter getFileChooserFilter (String description, String extension) {
        return new FileNameExtensionFilter(description, extension);
    }
}
