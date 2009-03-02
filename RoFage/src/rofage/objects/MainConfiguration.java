package rofage.objects;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The MainConfiguration object is a singleton and can be accessed any time,
 * any where
 * This object should be binded with the CONFIG table in the database
 * We do not have to think about multi threading writing access for this singleton,
 * since it may only be read in multithreading mode.
 * @author Pierre Chastagner
 */
public class MainConfiguration {
    private final static Log logger = LogFactory.getLog(MainConfiguration.class);
    private static MainConfiguration instance;

    private int lastOpenedDatId;
    private String folderImage;
    private String folderIcon;
    private String folderNfo;

    public static MainConfiguration getInstance() {
        if (instance==null) {
            instance = new MainConfiguration();
        }
        return instance;
    }
    
    private MainConfiguration () {
    }

    public String getFolderIcon() {
        return folderIcon;
    }

    public void setFolderIcon(String folderIcon) {
        this.folderIcon = folderIcon;
    }

    public String getFolderImage() {
        return folderImage;
    }

    public void setFolderImage(String folderImage) {
        this.folderImage = folderImage;
    }

    public String getFolderNfo() {
        return folderNfo;
    }

    public void setFolderNfo(String folderNfo) {
        this.folderNfo = folderNfo;
    }

    public int getLastOpenedDatId() {
        return lastOpenedDatId;
    }

    public void setLastOpenedDatId(int lastOpenedDatId) {
        this.lastOpenedDatId = lastOpenedDatId;
    }
}
