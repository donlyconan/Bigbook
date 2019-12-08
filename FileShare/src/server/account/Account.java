package server.account;

import java.util.Timer;

import org.apache.commons.net.ftp.FTPClient;

import server.platform.Platform;

public class Account implements Platform {
	private Timer timer;
	private String user;
	private String password;
	private FTPClient ftpClient;

	public Account(String user, String password) {
		super();
		this.user = user;
		this.password = password;
	}
	
	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public FTPClient getFtpClient() {
		return ftpClient;
	}

	public void setFtpClient(FTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}

}
