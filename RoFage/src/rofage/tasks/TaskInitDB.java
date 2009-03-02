package rofage.tasks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import rofage.toolkits.DerbyDBToolkit;

/**
 *
 * @author Pierre Chastagner
 */
public class TaskInitDB extends AbstractTask {
    private final static Log logger = LogFactory.getLog(TaskInitDB.class);

    public TaskInitDB () {
        super("Initialize Database...");
    }

    @Override
    public Task runTask() {
        logger.debug("Initializes DB...");
        if (DerbyDBToolkit.connect(false)==null) {
            // If we cannot connect to the database, it means we have to create it
            return new TaskCreateDB();
        }
        return null;
    }
}
