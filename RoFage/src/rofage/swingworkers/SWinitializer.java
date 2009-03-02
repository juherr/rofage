package rofage.swingworkers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingWorker;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import rofage.tasks.Task;
import rofage.tasks.TaskCheckJavaVersion;
import rofage.tasks.TaskFirstLaunch;
import rofage.tasks.TaskInitDB;
import rofage.ui.InitializerFrame;

/**
 * This SwingWorker checks the requirements for the application
 * If a requirement is not met, a fatal exception is thrown
 * @author Pierre Chastagner
 */
public class SWinitializer extends SwingWorker<Integer, String> {
    private final static Log logger = LogFactory.getLog(SWinitializer.class);
    private final static List<Task> listTasks = new ArrayList<Task>();
    private InitializerFrame initFrame;

    static {
        // We build the initialization tasks
        listTasks.add(new TaskCheckJavaVersion());
        listTasks.add(new TaskInitDB());
        listTasks.add(new TaskFirstLaunch());
    }

    public InitializerFrame getInitFrame() {
        return initFrame;
    }

    public SWinitializer (InitializerFrame initFrame) {
        super();
        this.initFrame = initFrame;

        addPropertyChangeListener(new PropertyChangeListener() {
            @Override
			public void propertyChange(PropertyChangeEvent evt) {
				if("progress".equals(evt.getPropertyName())) { //$NON-NLS-1$
					getInitFrame().pgrSplash.setValue((Integer) evt.getNewValue());
				}
            }
		});
        
    }

    @Override
    protected Integer doInBackground() throws Exception {
        logger.info("Starting SWInitializer");
        setProgress(0);

        for (int i=0; i<listTasks.size(); i++) {
            Task task = listTasks.get(i);
            publish(task.getTitle());
            Task newTask = task.runTask();
            // If we get a new task as a result, it means we have to execute it immediately
            if (newTask!=null) {
                listTasks.add(i+1, newTask);
            }
            setProgress((i+1)*100/listTasks.size());
        }
        setProgress(100);
        initFrame.dispose();
        return 0;
    }

    @Override
    protected void process(List<String> strings) {
        for(String s : strings)
	            initFrame.pgrSplash.setString(s);
    }





}
