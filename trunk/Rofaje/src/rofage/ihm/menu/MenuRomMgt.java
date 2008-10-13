package rofage.ihm.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import rofage.common.Engine;
import rofage.ihm.Messages;
import rofage.ihm.actions.ExportListAction;
import rofage.ihm.actions.ImportAction;
import rofage.ihm.actions.common.ShowCleanAction;
import rofage.ihm.actions.common.ShowCompressAction;
import rofage.ihm.actions.common.ShowDuplicateAction;
import rofage.ihm.actions.common.ShowRenameAction;
import rofage.ihm.actions.common.ShowScanAction;

@SuppressWarnings("serial")
public class MenuRomMgt extends JMenu {
	
	private Engine engine	= null;
	
	private JMenuItem mItemScan 		= null;
	private JMenuItem mItemRename 		= null;
	private JMenuItem mItemClean 		= null;
	private JMenuItem mItemCompress		= null;
	private JMenuItem mItemImport		= null;
	private JMenuItem mItemDuplicate	= null;
	private JMenu mExportList 			= null;
	
	// Menu items for the export lists
	private JMenuItem mItemExportAll		= null;
	private JMenuItem mItemExportGoodNamed	= null;
	private JMenuItem mItemExportBadNamed	= null;
	private JMenuItem mItemExportMissing	= null;

	public MenuRomMgt (Engine engine) {
		this.engine = engine;
		setText(Messages.getString("RomMgt"));
		
		add(getMItemScan());
		add(getMItemRename());
		add(getMItemClean());
		add(getMItemCompress());
		add(getMItemImport());
		add(getMItemDuplicate());
		add(new JSeparator(JSeparator.HORIZONTAL));
		add(getMItemExportList());
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
	
	public JMenuItem getMItemDuplicate() {
		if (mItemDuplicate==null) {
			mItemDuplicate = new JMenuItem(Messages.getString("DuplicateWindowTitle"));
			mItemDuplicate.addActionListener(new ShowDuplicateAction(engine));
		}
		return mItemDuplicate;
	}
	
	public JMenu getMItemExportList() {
		if (mExportList==null) {
			mExportList = new JMenu(Messages.getString("ExportList"));
			mExportList.add(getMItemExportAll());
			mExportList.add(getMItemExportGoodNamed());
			mExportList.add(getMItemExportBadNamed());
			mExportList.add(getMItemExportMissing());
		}
		return mExportList;
	}
	
	public JMenuItem getMItemExportAll() {
		if (mItemExportAll==null) {
			mItemExportAll = new JMenuItem(Messages.getString("ExportListAll"));
			mItemExportAll.addActionListener(new ExportListAction(engine, ExportListAction.EXPORT_ALL));
		}
		return mItemExportAll;
	}
	
	public JMenuItem getMItemExportGoodNamed() {
		if (mItemExportGoodNamed==null) {
			mItemExportGoodNamed = new JMenuItem(Messages.getString("ExportListGoodNamed"));
			mItemExportGoodNamed.addActionListener(new ExportListAction(engine, ExportListAction.EXPORT_GOODNAMED));
		}
		return mItemExportGoodNamed;
	}
	
	public JMenuItem getMItemExportBadNamed() {
		if (mItemExportBadNamed==null) {
			mItemExportBadNamed = new JMenuItem(Messages.getString("ExportListBadNamed"));
			mItemExportBadNamed.addActionListener(new ExportListAction(engine, ExportListAction.EXPORT_BADNAMED));
		}
		return mItemExportBadNamed;
	}
	
	public JMenuItem getMItemExportMissing() {
		if (mItemExportMissing==null) {
			mItemExportMissing = new JMenuItem(Messages.getString("ExportListMissing"));
			mItemExportMissing.addActionListener(new ExportListAction(engine, ExportListAction.EXPORT_MISSING));
		}
		return mItemExportMissing;
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
