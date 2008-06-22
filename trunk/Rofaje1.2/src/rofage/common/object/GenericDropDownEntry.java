package rofage.common.object;

import javax.swing.ImageIcon;

public class GenericDropDownEntry {
	private String value;
	private String text;
	private ImageIcon icon;
	
	public GenericDropDownEntry (String value, String text) {
		this.value = value;
		this.text = text;
	}
	
	public GenericDropDownEntry (String value, String text, ImageIcon icon) {
		this.value = value;
		this.text = text;
		this.icon = icon;
	}
	
	public ImageIcon getIcon() {
		return icon;
	}
	
	public String toString() {
		return text;
	}

	public String getValue() {
		return value;
	}
	
	public String getText() {
		return text;
	}

	public void setValue(String romSize) {
		this.value = romSize;
	}
	
}
