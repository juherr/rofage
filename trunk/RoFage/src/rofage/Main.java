package rofage;

import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import rofage.swingworkers.SWTasks;
import rofage.tasks.Task;
import rofage.tasks.TaskCheckJavaVersion;
import rofage.tasks.TaskFirstLaunch;
import rofage.tasks.db.TaskInitDB;
import rofage.ui.InitializerFrame;
import rofage.ui.MainWindow;

/**
 *
 * @author Pierre Chastagner
 */
public class Main {

	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
			public void run() {
				/*InitializerFrame initFrame = new InitializerFrame();
                initFrame.setVisible(true);

                List<Task> listTasks = new ArrayList<Task>();
                // We build the initialization tasks
                listTasks.add(new TaskCheckJavaVersion());
                listTasks.add(new TaskInitDB());
                listTasks.add(new TaskFirstLaunch());
                SWTasks swInitializer = new SWTasks(initFrame.pgrSplash, listTasks);
                swInitializer.execute();*/
                MainWindow mainW = new MainWindow();
                mainW.setVisible(true);
			}
		});
        
	}
}
