package server.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;

import server.platform.Platform;

public class APIFTPFile implements Platform {
	private static final APIFTPFile Root = new APIFTPFile();
	private FTPFile file;
	private String directory;
	private String pathname;

	public APIFTPFile() {
		directory = "\\";
		pathname = "\\";
		file = new FTPFile();
	}

	public APIFTPFile(String directory, FTPFile ftpFile) {
		this.directory = directory;
		this.file = ftpFile;
		this.pathname = directory + "\\" + ftpFile.getName();
	}

	public APIFTPFile(APIFTPFile root, FTPFile file) {
		this.directory = root.getPathname();
		this.file = file;
		this.pathname = directory + "\\" + file.getName();
	}

	public APIFTPFile(APIFTPFile root, APIFTPFile file) {
		this.directory = root.getPathname();
		this.file = file.getFile();
		this.pathname = directory + "\\" + file.getName();
	}

	public boolean rename(FTPClient user, String new_name) throws IOException {
		return user.rename(pathname, directory + "\\" + new_name);
	}

	public boolean delete(FTPClient user) throws IOException {
		if (isFile()) {
			return user.deleteFile(pathname);
		} else {
			return removeFolder(user);
		}
	}

	public boolean removeFolder(FTPClient user) throws IOException {
		removeAll(user, listFile(user));
		return user.removeDirectory(pathname);
	}

	public void removeAll(FTPClient user, List<APIFTPFile> files) throws IOException {
		for (APIFTPFile file : files) {
			if (file.isFile()) {
				file.delete(user);
			} else {
				removeAll(user, file.listFile(user));
				user.removeDirectory(file.getAbsolutepath());
			}
		}
	}

	public List<APIFTPFile> listFile(FTPClient ftp) throws IOException {
		List<APIFTPFile> list = new ArrayList<APIFTPFile>();
		FTPFile[] files = ftp.listFiles(pathname);
		for (FTPFile item : files) {
			APIFTPFile temp = new APIFTPFile(pathname, item);
			list.add(temp);
		}
		return list;
	}

	public List<APIFTPFile> listFile(FTPClient ftp, FTPFileFilter filter) throws IOException {
		List<APIFTPFile> list = new ArrayList<APIFTPFile>();
		FTPFile[] files = ftp.listFiles(pathname, filter);
		for (FTPFile item : files) {
			APIFTPFile temp = new APIFTPFile(pathname, item);
			list.add(temp);
		}
		return list;
	}

	public List<APIFTPFile> lisDirectory(FTPClient ftp) throws IOException {
		if (!isFile()) {
			List<APIFTPFile> list = new ArrayList<APIFTPFile>();
			FTPFile[] files = ftp.listDirectories(pathname);
			for (FTPFile item : files) {
				list.add(new APIFTPFile(pathname, item));
			}
			return list;
		}
		return null;
	}

	public List<APIFTPFile> getAllChildren(FTPClient ftp) throws IOException {
		List<APIFTPFile> list = new ArrayList<APIFTPFile>();
		System.out.println(this);
		Tree(listFile(ftp), list, ftp);
		return list;
	}

	public static void Tree(List<APIFTPFile> root, List<APIFTPFile> list, FTPClient ftp) throws IOException {
		if (list != null) {
			for (APIFTPFile item : root) {
				list.add(item);
				System.out.println(item.getAbsolutepath() + " isFolder" + item.isFolder());
				if (item.isFolder()) {
					Tree(item.listFile(ftp), list, ftp);
				}
			}
		}
	}

	public FTPFile find(FTPClient user, String name) throws IOException {
		FTPFile[] list = user.listFiles(pathname);
		for (FTPFile item : list) {
			System.out.println(item);
			if (item.getName().equals(name)) {
				return item;
			}
		}
		return null;
	}

	public static APIFTPFile getFTPFile(FTPClient user, String pathname) throws IOException {
		FTPFile file = user.mlistFile(pathname);
		int index = pathname.lastIndexOf("\\");
		if (index != -1 && (file.isFile() || !file.isDirectory() || !file.isFile())) {
			pathname = pathname.substring(0, index);
			return new APIFTPFile(pathname, file);
		}
		return null;
	}

	public long getSize() {
		return file.getSize();
	}

	public String getName() {
		return file.getName();
	}

	public boolean isFolder() {
		return !file.isFile();
	}

	public boolean isFile() {
		return file.isFile();
	}
//
//	public static void main(String[] args) throws IOException {
//		FTPClient ftpClient = new FTPClient();
//		ftpClient.connect(InetAddress.getLocalHost(), 21);
//		ftpClient.login("donly", "root");
//		System.out.println("Run");
//		List<APIFTPFile> file = new APIFTPFile(getRoot(), ftpClient.mlistFile("/hash_algorithms")).listFile(ftpClient);
//		for(APIFTPFile item : file)
//			System.out.println(item);
//	}

	@Override
	public String toString() {
		return "APIFTPFile [directory=" + directory + ", pathname=" + pathname + " isfile="
				+ (isFile() ? "File" : "Folder") + "]";
	}

	public FTPFile getFile() {
		return file;
	}

	public void setFile(FTPFile file) {
		this.file = file;
	}

	public static final APIFTPFile rootFile() {
		return Root;
	}

	public String getPathname() {
		return pathname;
	}

	public void setPathname(String pathname) {
		this.pathname = pathname;
	}

	public String getAbsolutepath() {
		return pathname;
	}

	public void setAbsolutepath(String absolutepath) {
		this.pathname = absolutepath;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public static APIFTPFile getRoot() {
		return Root;
	}

}
