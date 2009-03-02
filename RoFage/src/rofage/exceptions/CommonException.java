package rofage.exceptions;

import javax.swing.JOptionPane;

/**
 *
 * @author Pierre Chastagner
 */
public class CommonException extends AbstractException {
    
    /**
     * Constructs an instance of <code>CommonException</code> with the specified detail message.
     * @param msg the detail message.
     * @param cause cause of the exception
     */
    public CommonException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CommonException(String msg) {
        super(msg, null);
    }

    @Override
    void showMessage() {
        JOptionPane.showMessageDialog(null, msg,
					"Error !", JOptionPane.ERROR_MESSAGE);
    }
}
