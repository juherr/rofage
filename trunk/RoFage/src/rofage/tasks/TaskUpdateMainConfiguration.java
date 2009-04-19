package rofage.tasks;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import rofage.exceptions.CommonException;
import rofage.objects.MainConfiguration;
import rofage.toolkits.DerbyDBToolkit;

/**
 * Update the MainConfiguration Object and saves it into the DB
 * @author Pierre Chastagner
 */
public class TaskUpdateMainConfiguration extends AbstractTask{
    private final static Log logger = LogFactory.getLog(TaskUpdateMainConfiguration.class);
    private String folderIcon;
    private String folderImage;
    private String folderNfo;
    private String folderDat;
    private Integer lastOpenedId;

    public TaskUpdateMainConfiguration (String folderIcon, String folderImage,
            String folderNfo, String folderDat, Integer lastOpenedId) {
        super ("Updating Main Configuration");
        this.folderIcon = folderIcon;
        this.folderImage = folderImage;
        this.folderNfo = folderNfo;
        this.folderDat = folderDat;
        this.lastOpenedId = lastOpenedId;
    }

    @Override
    public Task runTask() {
        try {
            logger.info("Updating Main Configuration");
            MainConfiguration mainConf = MainConfiguration.getInstance();
            if (folderIcon != null) {
                mainConf.setFolderIcon(folderIcon);
            }
            if (folderImage != null) {
                mainConf.setFolderImage(folderImage);
            }
            if (folderNfo != null) {
                mainConf.setFolderNfo(folderNfo);
            }
            if (folderDat != null) {
                mainConf.setFolderDat(folderDat);
            }
            if (lastOpenedId != null) {
                mainConf.setLastOpenedDatId(lastOpenedId);
            }
            // Once we saved the java object, we have to save it into the DB
            // First we check if the config exists
            String sql;
            ResultSet rs = DerbyDBToolkit.executeSQLQuery("SELECT COUNT(*) FROM CONFIG");
            rs.next();
            if (rs.getInt(1) == 0) {
                sql = "INSERT INTO CONFIG (IMAGEFOLDER, NFOFOLDER, ICONFOLDER, DATFOLDER) VALUES (?, ?, ?, ?)";
            } else {
                sql = "UPDATE CONFIG SET IMAGEFOLDER=?, NFOFOLDER=?, ICONFOLDER=?, DATFOLDER=?";
            }
            Connection conn = DerbyDBToolkit.connect();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, folderImage);
            ps.setString(2, folderNfo);
            ps.setString(3, folderIcon);
            ps.setString(4, folderDat);
            ps.executeUpdate();
            conn.close();
        } catch (SQLException ex) {
            new CommonException(DerbyDBToolkit.ERROR_EXECUTING, ex);
        }
        return null;
    }
}
