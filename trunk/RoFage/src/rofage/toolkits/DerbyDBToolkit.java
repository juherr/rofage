package rofage.toolkits;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import rofage.exceptions.CommonException;
import rofage.exceptions.FatalException;

/**
 * This toolkit is used to manage the connection and the transaction to
 * the derby database
 * @author Pierre Chastagner
 */
public abstract class DerbyDBToolkit {
    private final static String DRIVER     = "org.apache.derby.jdbc.EmbeddedDriver";
    private final static String PROTOCOL   = "jdbc:derby:";
    private final static String DB_NAME    = "RoFageDB";

    private final static String USERNAME = PropertiesToolkit.getProperty("user");
    private final static String PASSWORD = PropertiesToolkit.getProperty("password");

    private final static Log logger = LogFactory.getLog(DerbyDBToolkit.class);

    /**
     * Try to connect to the database. If create is set to true, we will try to 
     * create the database and its structure
     * @param create
     * @return true if the connection was successful
     * @throws rofage.exceptions.FatalException
     */
    public static Connection connect (boolean create) {
        logger.info("Trying to connect to the database "+DB_NAME);
        Connection conn = null;
        try {
            conn = internalConnect(create);
        } catch (Exception ex) {
            if (create) {
                new FatalException("Connection to database failed", ex);
            }
        }
        return conn;
    }

    /**
     * This method is used to connect to the database
     * If the database does not exist, we will create it
     * @param create : do we create the database if needed ?
     */
    private static Connection internalConnect (boolean create) throws Exception {

        // Set the db system directory.
        System.setProperty("derby.system.home", "./derby");

        /*
           The driver is installed by loading its class.
           In an embedded environment, this will start up Derby, since it is not already running.
         */
        Connection conn = null;
        Properties props = new Properties();
        props.put("user", USERNAME);
        props.put("password", PASSWORD);

        /*
           The connection specifies create=true in the url to cause
           the database to be created. To remove the database,
           remove the directory derbyDB and its contents.
           The directory derbyDB will be created under
           the directory that the system property
           derby.system.home points to, or the current
           directory if derby.system.home is not set.
         */

        Class.forName(DRIVER).newInstance();
        logger.debug("Loaded the appropriate driver.");

        if (create) {
            conn = DriverManager.getConnection(PROTOCOL + DB_NAME +
                ";create=true", props);
        } else {
            conn = DriverManager.getConnection(PROTOCOL +
                DB_NAME, props);
        }

        logger.debug("Connected to the DB");
        conn.setAutoCommit(true);

        return conn;
    }

    /**
     * Execute an SQL statement with the given connection
     * This method should only be used when creating / dropping table
     * For SELECT query, please use executeSQLQuery
     * For UPDATE, INSERT or DELETE queries, please use executeSQLUpdate
     * @param sql : sql statement to execute
     */
    public static boolean executeSQL (String sql) {
        try {
            Connection conn = connect(true);
            Statement s = conn.createStatement();
            logger.debug("Executing the sql statement : "+sql);
            return s.execute(sql);
        } catch (SQLException ex) {
            new CommonException("An error occured while executing a statement...", ex);
        }
        return false;
    }

     /**
     * Execute an SQL statement with the given connection
     * This method should only be used when using SELECT queries
     * For UPDATE, INSERT or DELETE queries, please use executeSQLUpdate
     * For creating / dropping tables use executeSQL
     * @param sql : sql statement to execute
     */
    public static ResultSet executeSQLQuery (String sql) {
        try {
            Connection conn = connect(false);
            Statement s = conn.createStatement();
            logger.debug("Executing the sql statement : "+sql);
            return s.executeQuery(sql);
        } catch (SQLException ex) {
            new CommonException("An error occured while executing a statement...", ex);
        }
        return null;
    }

     /**
     * Execute an SQL statement with the given connection
     * This method should only be used when using UPDATE, INSERT or DELETE queries
     * For SELECT query, please use executeSQLQuery
     * For creating / dropping tables use executeSQL
     * @param sql : sql statement to execute
     */
    public static boolean executeSQLUpdate (String sql) {
        try {
            Connection conn = connect(false);
            Statement s = conn.createStatement();
            logger.debug("Executing the sql statement : "+sql);
            s.executeUpdate(sql);
        } catch (SQLException ex) {
            new CommonException("An error occured while executing a statement...", ex);
        }
        return false;
    }
}
