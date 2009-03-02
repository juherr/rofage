package rofage.tasks;

/**
 *
 * @author Pierre Chastagner
 */
public interface Task {
    /**
     * If a task returns an other task, it means that this new Task should
     * be run after this one
     * @return
     */
    Task runTask();
    String getTitle();
}
