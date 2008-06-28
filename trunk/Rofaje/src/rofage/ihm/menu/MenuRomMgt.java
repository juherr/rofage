package rofage.ihm.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import rofage.common.Engine;
import rofage.ihm.Messages;
import rofage.ihm.actions.common.ShowCleanAction;
import rofage.ihm.actions.common.ShowCompressAction;
import rofage.ihm.actions.common.ShowRenameAction;
import rofage.ihm.actions.common.ShowScanAction;
import rofage.ihm.actions.importroms.ImportAction;

@SuppressWarnings("serial")
public class MenuRomMgt extends JMenu {
	
	private Engine engine	= null;
	
	private JMenuItem mItemScan 	= null;
	private JMenuItem mItemRename 	= null;
	private JMenuItem mItemClean 	= null;
	private JMenuItem mItemCompress	= null;
	private JMenuItem mItemImport	= null;

	public MenuRomMgt (Engine engine) {
		this.engine = engine;
		setText(Messages.getString("RomMgt"));
		
		add(getMItemScan());
		add(getMItemRename());
		add(getMItemClean());
		add(getMItemCompress());
		add(getMItemImport());
	}

	public JMenuItem getMItemClean() {
		if (mItemClean==null) {
			mItemClean = new JMenuItem();
			mItemClean.addActionListener(new ShowCleanAction(engine));
			mItemClean.setText(Messages.getString("MainWindow.10")); //$NON-NLS-1$
			mItemClean.setVisible(true);
		}
		return mItemClean;
	}

	public JMenuItem getMItemCompress() {
		if (mItemCompress==null) {
			mItemCompress = new JMenuItem();
			mItemCompress.addActionListener(new ShowCompressAction(engine));
			mItemCompress.setText(Messages.getString("MainWindow.14")); //$NON-NLS-1$
			mItemCompress.setVisible(true);
		}
		return mItemCompress;
	}

	public JMenuItem getMItemImport() {
		if (mItemImport==null) {
			mItemImport = new JMenuItem(Messages.getString("ImportWindowTitle"));
			mItemImport.addActionListener(new ImportAction(engine));
		}
		return mItemImport;
	}

	public JMenuItem getMItemRename() {
		if (mItemRename==null) {
			mItemRename = new JMenuItem();
			mItemRename.addActionListener(new ShowRenameAction(engine));
			mItemRename.setText(Messages.getString("MainWindow.9")); //$NON-NLS-1$
			mItemRename.setVisible(true);
		}
		return mItemRename;
	}

	public JMenuItem getMItemScan() {
		if (mItemScan==null) {
			mItemScan = new JMenuItem();
			mItemScan.addActionListener(new ShowScanAction(engine));
			mItemScan.setText(Messages.getString("MainWindow.8")); //$NON-NLS-1$
			mItemScan.setVisible(true);
		}
		return mItemScan;
	}
}
