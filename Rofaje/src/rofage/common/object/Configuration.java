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
	
	private String romFolder;
	private String romFolderMove;
	private String titlePattern = DEFAULT_TITLEPATTERN;
	private boolean moveUnknownRoms = false;
	private boolean updateAtStartup = false;
		
	// Internal configuration data retrieved from the datFile
	private String confName; // Name of the dat 
	private String imageFolder = null; // Folder name where images are locally stored
	private int version; // Version of the file
	private int screenShotsWidthA; 
	private int screenShotsHeightA;
	private int screenShotsWidthB; 
	private int screenShotsHeightB;
	private String newDatUrl; // URL to download the latest version
	private String imageUrl; // Base URL to download images
	private String newVersionUrl; // URL to check the latest version
	private String fileName; // Filename of the dat file
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
	public int getScreenShotsHeightA() {
		return screenShotsHeightA;
	}
	public void setScreenShotsHeightA(int screenShotsHeightA) {
		this.screenShotsHeightA = screenShotsHeightA;
	}
	public int getScreenShotsHeightB() {
		return screenShotsHeightB;
	}
	public void setScreenShotsHeightB(int screenShotsHeightB) {
		this.screenShotsHeightB = screenShotsHeightB;
	}
	public int getScreenShotsWidthA() {
		return screenShotsWidthA;
	}
	public void setScreenShotsWidthA(int screenShotsWidthA) {
		this.screenShotsWidthA = screenShotsWidthA;
	}
	public int getScreenShotsWidthB() {
		return screenShotsWidthB;
	}
	public void setScreenShotsWidthB(int screenShotsWidthB) {
		this.screenShotsWidthB = screenShotsWidthB;
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
	
}
