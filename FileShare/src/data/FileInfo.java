package data;

import java.io.File;
import java.io.Serializable;

import org.apache.commons.net.ftp.FTPClient;

public class FileInfo implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static final int buff = 25000;

	private String name;
	private String path;
	private long size;
	private int part;
	private int sizeLast;

	public FileInfo( File file )
	{
		name		= file.getName();
		path		= file.getAbsolutePath();
		size		= file.length();
		part		= (int) (file.length() / buff);
		sizeLast	= (int) (file.length() % buff);
	}
		
	
	@Override
	public String toString( ) {
		return "FileInfo [name=" + name + ", path=" + path + ", size=" + size + ", part=" + part + ", sizeLast="
				+ sizeLast + "]";
	}

	public String getName( ) { return name; }

	public void setName( String name) { this.name = name; }

	public long getSize( ) { return size; }

	public void setSize( long size) { this.size = size; }

	public int getPart( ) { return part; }

	public void setPart( int part) { this.part = part; }

	public int getSizeLast( ) { return sizeLast; }

	public void setSizeLast( int sizeLast) { this.sizeLast = sizeLast; }

	public String getPath( ) { return path; }

	public void setPath( String path) { this.path = path; }
}
