package rofage.exceptions;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Pierre Chastagner
 */
public class FatalException extends AbstractException {
    /**
     * Constructs an instance of <code>FatalException</code> with the specified detail message.
     * @param msg the detail message.
     * @param cause cause of the exception
     */
    public FatalException(String msg, Throwable cause) {
        super(msg, cause);
    }

    @Override
    void showMessage() {
        JOptionPane.showMessageDialog(null, msg + "\nRoFage will now quit... Sorry!",
                    "Error!", JOptionPane.ERROR_MESSAGE);
        System.exit(-1);
    }
}
