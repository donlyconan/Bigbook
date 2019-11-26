package gui.control;

import java.io.File;

public class FileView 
{
	private File file;
	
	public FileView(File file )
	{
		this.file = file;
	}
	
	public File[] getList() {return file.listFiles();}

	@Override
	public String toString( ) { return file.getName(); }

	public File getFile( ) { return file; }

	public void setFile( File file) { this.file = file; }
	
}
