package bigbook.connect.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class Test {
	public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
		FTPClient ftp = new FTPClient();
		ftp.connect(InetAddress.getLocalHost(),21);
		ftp.login("donly", "root");
		ftp.dele("Donly");
		ftp.storeFile("/", new FileInputStream("E:\\Downloads\\SETUP.exe"));
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
		ftp.enterLocalPassiveMode();
		ftp.deleteFile("Donly");
		ftp.logout();
	}
	
}
