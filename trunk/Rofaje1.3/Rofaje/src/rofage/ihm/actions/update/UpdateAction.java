package rofage.ihm.actions.update;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;

import rofage.common.Engine;
import rofage.common.object.Configuration;
import rofage.common.update.UpdateSwingWorker;

/**
 * Class to trigger the update
 * @author pierrot
 *
 */
@SuppressWarnings("serial")
public class UpdateAction extends AbstractAction {
	private Engine engine = null;
	
	public UpdateAction(Engine engine) {
		this.engine = engine;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		// We update all configurations
		List<Configuration> listConfigs = new ArrayList<Configuration>(engine.getGlobalConf().getMapDatConfigs().values());
		engine.setUpdateSW(new UpdateSwingWorker(engine, listConfigs));
		engine.getUpdateSW().execute();
	}

}
