package rofage.tasks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import rofage.toolkits.PropertiesToolkit;
import rofage.ui.DownloadJavaFrame;

/**
 * This class checks the Java version installed
 * @author Pierre Chastagner
 */
public class TaskCheckJavaVersion extends AbstractTask{
    private final static Log logger = LogFactory.getLog(TaskCheckJavaVersion.class);
    private final static String REQ_JAVA_VERSION = PropertiesToolkit.getProperty("JAVA_VERSION");

    public TaskCheckJavaVersion () {
        super("Checking Java Version");
    }

    @Override
    /**
     * This method will check the Java Version used
     * If it does not met the requirements an exception is thrown
     * @return a boolean which may indicates the result of an operation
     */
    public Task runTask() {
        logger.debug("Checking Java Version");
        String version = System.getProperty("java.specification.version");
        float numVersion = Float.parseFloat(version);
        logger.debug("Java Version is "+numVersion);
        if (numVersion<Float.parseFloat(REQ_JAVA_VERSION)) {
            DownloadJavaFrame dlJavaFrame = new DownloadJavaFrame(null, true);
            dlJavaFrame.setVisible(true);
        }
        return null;
    }
}
