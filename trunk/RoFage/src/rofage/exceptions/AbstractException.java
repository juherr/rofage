package rofage.exceptions;

import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Pierre Chastagner
 */
public abstract class AbstractException extends Exception {
    Log logger = LogFactory.getLog(getClass());
    String msg;
    Throwable cause;
    StackTraceElement[] tabStackTraceElement;

    /**
     * Constructs an instance of <code>CommonException</code> with the specified detail message.
     * @param msg the detail message.
     * @param cause cause of the exception
     */
    public AbstractException(String msg, Throwable cause) {
        super(msg, cause);
        this.msg = msg;
        this.cause = cause;
        
        if (cause == null) {
            logger.error(msg);
        } else {
            logger.error(msg+" : "+cause.getCause());
            tabStackTraceElement = cause.getStackTrace();
            StringBuffer strBuff = new StringBuffer();
            for (int i=0; i<tabStackTraceElement.length; i++) {
                strBuff.append(tabStackTraceElement[i]);
                strBuff.append("\n");
            }
            logger.error(strBuff);
        }
        showMessage();
    }

    abstract void showMessage ();
}
