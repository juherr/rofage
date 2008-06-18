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
	private String romSize = "";
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
	private boolean wifi = false;
	
	
	private boolean gotRom = false;
	private boolean goodName = false;
	
	/** fullPath to the file containing the rom. It can either be the rom itself or a zip file */
	private String containerPath;
	
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
	public boolean isWifi() {
		return wifi;
	}
	public void setWifi(boolean wifi) {
		this.wifi = wifi;
	}
}
