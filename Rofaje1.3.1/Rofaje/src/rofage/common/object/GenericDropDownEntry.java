package rofage.common.object;

public class GenericDropDownEntry {
	String value;
	String text;
	
	public GenericDropDownEntry (String value, String text) {
		this.value = value;
		this.text = text;
	}
	
	public String toString() {
		return text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String romSize) {
		this.value = romSize;
	}
	
}
