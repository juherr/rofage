package rofage.common.swingworker;

import javax.swing.SwingWorker;

import rofage.common.Engine;

public abstract class StoppableSwingWorker<T, V> extends SwingWorker<T, V> {

	protected boolean stopAction = false;
	protected Engine engine;
	
	protected Engine getEngine() {
		return engine;
	}

	protected boolean isStopAction() {
		return stopAction;
	}

	public void setStopAction(boolean stopAction) {
		this.stopAction = stopAction;
	}

}
