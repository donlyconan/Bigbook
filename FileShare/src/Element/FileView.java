package Element;

import java.io.File;
import java.io.Serializable;

public class FileView implements Serializable
{
	private static final long serialVersionUID = 1L;
	private File file;

	public FileView( File file )
	{
		super();
		this.file = file;
	}

	public FileView( String path )
	{ file = new File(path); }

	@Override
	public String toString( )
	{ 
		String[] names = file.toString().split("\\\\");
		String name = names[names.length-1];
 		return name; 
	}

	public File getFile( )
	{ return file; }

	public void setFile( File file)
	{ this.file = file; }
}
