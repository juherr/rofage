package rofage.common.object;

import java.io.Serializable;
import java.util.List;

/**
 * User shall be able to modify the default configuration
 * and save his preferences
 * @author pierrot
 *
 */
public class Configuration implements Serializable {
	private static final long serialVersionUID = -7122119084096321060L;
	public static final String DEFAULT_TITLEPATTERN = "%n-%t";
	
	private String romFolder = "";
	private String romFolderMove = "";
	private String titlePattern = DEFAULT_TITLEPATTERN;
	private boolean moveUnknownRoms = false;
	private boolean updateAtStartup = false;
	private boolean renameInside = false;
	private boolean inAppUpdate = false;
	private boolean internalName = false; // Do we check with the internal name ?
	private boolean deleteSource = false;
	private int compressValue = 9;
	private boolean importScan = true;
	private boolean importRename = false;
	private boolean importClean = false;
	private boolean importCompress = false;
	private List<String> listLocations;
	
		
	// Internal configuration data retrieved from the datFile
	private String confName; // Name of the dat 
	private String imageFolder = null; // Folder name where images are locally stored
	private int version; // Version of the file
	private String newDatUrl; // URL to download the latest version
	private String imageUrl; // Base URL to download images
	private String newVersionUrl; // URL to check the latest version
	private String fileName; // Filename of the dat file
	private String icoUrl; // URL to download the icons
	private int screenshotHeight = 384; // Height of the screenshot (default value)
	private int screenshotWidth = 256; // Width of the screenshot (default value)
	
	private List<String> allowedExtensions;

	public String getRomFolder() {
		return romFolder;
	}
	public void setRomFolder(String romFolder) {
		this.romFolder = romFolder;
	}
	public String getRomFolderMove() {
		return romFolderMove;
	}
	public void setRomFolderMove(String romFolderMove) {
		this.romFolderMove = romFolderMove;
	}
	public boolean isMoveUnknownRoms() {
		return moveUnknownRoms;
	}
	public void setMoveUnknownRoms(boolean moveUnknownRoms) {
		this.moveUnknownRoms = moveUnknownRoms;
	}
	public boolean isUpdateAtStartup() {
		return updateAtStartup;
	}
	public void setUpdateAtStartup(boolean updateAtStartup) {
		this.updateAtStartup = updateAtStartup;
	}
	public String getTitlePattern() {
		return titlePattern;
	}
	public void setTitlePattern(String titlePattern) {
		this.titlePattern = titlePattern;
	}
	public String getConfName() {
		return confName;
	}
	public void setConfName(String confName) {
		this.confName = confName;
	}
	public String getImageFolder() {
		return imageFolder;
	}
	public void setImageFolder(String imageFolder) {
		this.imageFolder = imageFolder;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getNewDatUrl() {
		return newDatUrl;
	}
	public void setNewDatUrl(String newDatUrl) {
		this.newDatUrl = newDatUrl;
	}
	public String getNewVersionUrl() {
		return newVersionUrl;
	}
	public void setNewVersionUrl(String newVersionUrl) {
		this.newVersionUrl = newVersionUrl;
	}
	public List<String> getAllowedExtensions() {
		return allowedExtensions;
	}
	public void setAllowedExtensions(List<String> allowedExtensions) {
		this.allowedExtensions = allowedExtensions;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public boolean isRenameInside() {
		return renameInside;
	}
	public void setRenameInside(boolean renameInside) {
		this.renameInside = renameInside;
	}
	public boolean isInAppUpdate() {
		return inAppUpdate;
	}
	public void setInAppUpdate(boolean inAppUpdate) {
		this.inAppUpdate = inAppUpdate;
	}
	public String getIcoUrl() {
		return icoUrl;
	}
	public void setIcoUrl(String icoUrl) {
		this.icoUrl = icoUrl;
	}
	public boolean isInternalName() {
		return internalName;
	}
	public void setInternalName(boolean internalName) {
		this.internalName = internalName;
	}
	public boolean isDeleteSource() {
		return deleteSource;
	}
	public void setDeleteSource(boolean deleteSource) {
		this.deleteSource = deleteSource;
	}
	public int getCompressValue() {
		return compressValue;
	}
	public void setCompressValue(int compressValue) {
		this.compressValue = compressValue;
	}
	public boolean isImportScan() {
		return importScan;
	}
	public void setImportScan(boolean importScan) {
		this.importScan = importScan;
	}
	public boolean isImportRename() {
		return importRename;
	}
	public void setImportRename(boolean importRename) {
		this.importRename = importRename;
	}
	public boolean isImportClean() {
		return importClean;
	}
	public void setImportClean(boolean importClean) {
		this.importClean = importClean;
	}
	public List<String> getListLocations() {
		return listLocations;
	}
	public void setListLocations(List<String> listLocations) {
		this.listLocations = listLocations;
	}
	public boolean isImportCompress() {
		return importCompress;
	}
	public void setImportCompress(boolean importCompress) {
		this.importCompress = importCompress;
	}
	public int getScreenshotHeight() {
		return screenshotHeight;
	}
	public int getScreenshotWidth() {
		return screenshotWidth;
	}
	public void setScreenshotHeight(int screenshotHeight) {
		this.screenshotHeight = screenshotHeight;
	}
	public void setScreenshotWidth(int screenshotWidth) {
		this.screenshotWidth = screenshotWidth;
	}
	
}
