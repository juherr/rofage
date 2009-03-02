package rofage;

import javax.swing.SwingUtilities;
import rofage.swingworkers.SWinitializer;
import rofage.ui.InitializerFrame;

/**
 *
 * @author Pierre Chastagner
 */
public class Main {

	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
			public void run() {
				InitializerFrame initFrame = new InitializerFrame();
                initFrame.setVisible(true);
                SWinitializer swInitializer = new SWinitializer(initFrame);
                swInitializer.execute();
			}
		});
        
	}
}
