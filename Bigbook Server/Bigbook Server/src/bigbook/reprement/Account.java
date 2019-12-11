package bigbook.reprement;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Types;

import bigbook.Platform.Platform;
import bigbook.connect.database.SQLManager;

public class Account implements Platform, Serializable {
	public static enum Status {
		ACCxNOT_EXIST, ACCxLOGIN_FAILE, ACCxLOGIN_SUCCESS
	}

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;

	public Account(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public static String checkLogin(String username, String password) {
		try {
			CallableStatement call = SQLManager.getConnect().prepareCall("{CALL sp_CheckLogin(?,?,?)}");
			call.setNString(1, username);
			call.setNString(2, password);
			call.registerOutParameter(3, Types.NVARCHAR);
			call.execute();
			return call.getString(3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
