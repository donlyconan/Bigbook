package server.element;

import java.io.File;
import java.io.Serializable;

public class FileItem implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String pathfile;
	private long size;
	private boolean isfile;
	
	public FileItem(String pathfile) {
		super();
		File file = new File(pathfile);
		this.pathfile = file.getAbsolutePath();
		size = file.length();
		isfile = file.isFile();
	}

	public FileItem(File file) {
		this.pathfile = file.getAbsolutePath();
		size = file.length();
		isfile = file.isFile();
	}
	
	public File getItem() {
		return new File(pathfile);
	}

	public String getPathfile() {
		return pathfile;
	}

	public void setPathfile(String pathfile) {
		this.pathfile = pathfile;
	}

	@Override
	public String toString( )
	{ 
		String[] names = pathfile.split("\\\\");
		String name = names[names.length-1];
 		return name; 
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public boolean isIsfile() {
		return isfile;
	}

	public void setIsfile(boolean isfile) {
		this.isfile = isfile;
	}
	
	
}
