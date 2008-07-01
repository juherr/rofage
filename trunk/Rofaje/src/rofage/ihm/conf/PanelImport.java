package rofage.ihm.conf;

import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import rofage.common.Engine;
import rofage.common.object.Configuration;
import rofage.ihm.actions.conf.ImportScanCBListener;

@SuppressWarnings("serial")
public class PanelImport extends JPanel {
	private JCheckBox CBScan = null;
	private JCheckBox CBRename = null;
	private JCheckBox CBClean = null;
	private JCheckBox CBCompress = null;
	
	private Configuration selConf = null;
	
	public PanelImport (Engine engine) {
		this.selConf = engine.getGlobalConf().getSelectedConf();
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		Box vBox = Box.createVerticalBox();
		vBox.add(getCBScan());
		vBox.add(getCBRename());
		vBox.add(getCBClean());
		vBox.add(getCBCompress());
		add(vBox);
		setVisible(true);
	}

	public JCheckBox getCBScan() {
		if (CBScan == null) {
			CBScan = new JCheckBox();
			CBScan.setText("Scan during import");
			if (selConf!=null) {
				CBScan.setSelected(selConf.isImportScan());
			} else {
				CBScan.setSelected(true);
			}
			CBScan.addItemListener(new ImportScanCBListener(this));
		}
		return CBScan;
	}

	public JCheckBox getCBRename() {
		if (CBRename == null) {
			CBRename = new JCheckBox();
			CBRename.setText("Rename during import");
			if (selConf!=null) {
				if (!selConf.isImportScan()) {
					CBRename.setSelected(false);
					CBRename.setEnabled(false);
				} else {
					CBRename.setSelected(selConf.isImportRename());
				}
			}
		}
		return CBRename;
	}
	
	public JCheckBox getCBClean() {
		if (CBClean == null) {
			CBClean = new JCheckBox();
			CBClean.setText("Clean during import");
			if (selConf!=null) {
				if (!selConf.isImportScan()) {
					CBClean.setSelected(false);
					CBClean.setEnabled(false);
				} else {
					CBClean.setSelected(selConf.isImportClean());
				}
			}
		}
		return CBClean;
	}
	
	public JCheckBox getCBCompress() {
		if (CBCompress == null) {
			CBCompress = new JCheckBox();
			CBCompress.setText("Compress during import");
			if (selConf!=null) {
				if (!selConf.isImportCompress()) {
					CBCompress.setSelected(false);
					CBCompress.setEnabled(false);
				} else {
					CBCompress.setSelected(selConf.isImportCompress());
				}
			}
		}
		return CBClean;
	}
}
