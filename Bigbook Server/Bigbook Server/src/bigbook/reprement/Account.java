package bigbook.reprement;

import java.io.Serializable;

import bigbook.Platform.Platform;

public class Account implements Platform, Serializable{
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;

	public Account(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public static Noti Checked(String username, String password) {
		return Noti.NTxLoginSuccess;
	}
	
	public Noti Checked() {
		return Noti.NTxLoginSuccess;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
