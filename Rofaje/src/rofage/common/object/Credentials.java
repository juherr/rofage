package rofage.common.object;

import java.io.Serializable;

public class Credentials implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public final static String serializedName = "user.id";
	
	private String username = null;
	private String password = null;
	
	public Credentials (String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
