package rofage.common.object;

import java.io.Serializable;

/**
 * Represents a game
 * @author pierrot
 *
 */
public class Game implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 472389048397070141L;
	private String imageNb;
	private String releaseNb;
	private String title = "";
	private String romSize = "0";
	private String location = "";
	private String publisher = "";
	private String sourceRom = "";
	private String language = "";
	private String crc = "";
	private String image1crc = "";
	private String image2crc = "";
	private String icoCrc = "";
	private String genre = "";
	private String internalName = "";
	private String serial = "";
	private String duplicateId = "";
	private String comment = "";
	private Boolean wifi = null;
	private boolean scannedFromSerial = false;
	
	
	private boolean gotRom = false;
	private boolean goodName = false;
	private boolean ownedRom = false;
	
	/** fullPath to the file containing the rom. It can either be the rom itself or a zip file */
	private String containerPath;
	/** This is the fullPath to the entry. It uses the TrueZip format */
	private String entryFullPath = null;
	
	public String getCrc() {
		return crc;
	}
	public void setCrc(String crc) {
		this.crc = crc;
	}
	public String getImage1crc() {
		return image1crc;
	}
	public void setImage1crc(String image1crc) {
		this.image1crc = image1crc;
	}
	public String getImage2crc() {
		return image2crc;
	}
	public void setImage2crc(String image2crc) {
		this.image2crc = image2crc;
	}
	public String getImageNb() {
		return imageNb;
	}
	public void setImageNb(String imageNb) {
		this.imageNb = imageNb;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getReleaseNb() {
		return releaseNb;
	}
	public void setReleaseNb(String releaseNb) {
		this.releaseNb = releaseNb;
	}
	public String getRomSize() {
		return romSize;
	}
	public void setRomSize(String romSize) {
		this.romSize = romSize;
	}
	public String getSourceRom() {
		return sourceRom;
	}
	public void setSourceRom(String sourceRom) {
		this.sourceRom = sourceRom;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isGoodName() {
		return goodName;
	}

	public void setGoodName(boolean goodName) {
		this.goodName = goodName;
	}

	public boolean isGotRom() {
		return gotRom;
	}

	public void setGotRom(boolean gotRom) {
		this.gotRom = gotRom;
	}
	public String getContainerPath() {
		return containerPath;
	}
	public void setContainerPath(String containerPath) {
		this.containerPath = containerPath;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getIcoCrc() {
		return icoCrc;
	}
	public void setIcoCrc(String icoCrc) {
		this.icoCrc = icoCrc;
	}
	public String getInternalName() {
		return internalName;
	}
	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public void setWifi(Boolean wifi) {
		this.wifi = wifi;
	}
	public boolean isScannedFromSerial() {
		return scannedFromSerial;
	}
	public void setScannedFromSerial(boolean scannedFromSerial) {
		this.scannedFromSerial = scannedFromSerial;
	}
	public String getEntryFullPath() {
		return entryFullPath;
	}
	public void setEntryFullPath(String entryFullPath) {
		this.entryFullPath = entryFullPath;
	}
	public Boolean getWifi() {
		return wifi;
	}
	public String getDuplicateId() {
		return duplicateId;
	}
	public void setDuplicateId(String duplicateId) {
		this.duplicateId = duplicateId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public boolean isOwnedRom() {
		return ownedRom;
	}
	public void setOwnedRom(boolean ownedRom) {
		this.ownedRom = ownedRom;
	}
}
