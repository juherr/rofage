package rofage.tasks;

/**
 * This class describe an operation to be performed
 * It contains the title of the task
 * and the method runTask which should be called to execute the task
 * @author Pierre Chastagner
 */
public abstract class AbstractTask implements Task {
    protected String title;

    public String getTitle() {
        return title;
    }

    public AbstractTask (String title) {
        this.title = title;
    }
}
