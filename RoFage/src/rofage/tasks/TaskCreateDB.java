package rofage.tasks;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import rofage.toolkits.DerbyDBToolkit;

/**
 *
 * @author Pierre Chastagner
 */
public class TaskCreateDB extends AbstractTask {
    private final static Log logger = LogFactory.getLog(TaskCreateDB.class);

    public TaskCreateDB () {
        super("Creates Database structure...");
    }

    @Override
    public Task runTask() {
        logger.info("Creates Database structure...");
        logger.debug("Creating tables DATS");
        DerbyDBToolkit.executeSQL("create table DATS " +
                "(" +
                "ID BIGINT not null primary key GENERATED ALWAYS AS IDENTITY," +
                "NAME VARCHAR(255) not null," +
                "VERSION INTEGER not null," +
                "SYSTEMNAME VARCHAR(255) not null," +
                "SCREENSHOTHEIGHT SMALLINT," +
                "SCREENSHOTWIDTH SMALLINT," +
                "DATVERSIONURL VARCHAR(1000) not null," +
                "DATURL VARCHAR(1000) not null," +
                "IMAGEURL VARCHAR(1000) not null," +
                "ICOURL VARCHAR(1000)," +
                "IPSURL VARCHAR(1000)," +
                "ROMTITLE VARCHAR(20)," +
                "DEFAULTROMTITLE VARCHAR(20)" +
                ")");

        logger.debug("Creating tables CONFIG");
        DerbyDBToolkit.executeSQL("create table CONFIG " +
                "(" +
                "ID SMALLINT not null primary key GENERATED ALWAYS AS IDENTITY," +
                "ROFAGEVERSION INTEGER not null" +
                ")");

        logger.debug("Creating tables SAVEDDATS");
        
        return null;
    }
}
