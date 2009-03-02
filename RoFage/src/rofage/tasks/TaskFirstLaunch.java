package rofage.tasks;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import rofage.exceptions.FatalException;
import rofage.toolkits.DerbyDBToolkit;
import rofage.wizards.WizardFirstUse;

/**
 *
 * @author Pierre Chastagner
 */
public class TaskFirstLaunch extends AbstractTask {
    private final static Log logger = LogFactory.getLog(TaskFirstLaunch.class);
    private static final ResourceBundle i18n = ResourceBundle.getBundle("rofage/i18n/i18n");

    public TaskFirstLaunch () {
        super (i18n.getString("Testing_first_use..."));
    }

    @Override
    public Task runTask() {
        try {
            logger.info("Testing first use...");
            ResultSet rs = DerbyDBToolkit.executeSQLQuery("SELECT COUNT(*) FROM CONFIG");
            // We move the cursor on the result
            rs.next();
            if (rs.getInt(1)==0) {
                logger.info(i18n.getString("Launching_first_use_Wizard"));
                WizardFirstUse wzdFirstUse = new WizardFirstUse(null, true);
                wzdFirstUse.setVisible(true);
            }
        } catch (SQLException ex) {
            new FatalException(i18n.getString("An_error_occured_while_communicating_with_the_Database"), ex);
        }
        return null;
    }
}
