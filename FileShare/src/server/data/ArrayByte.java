package server.data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import server.element.FileItem;
import server.parent.Platform;

public class ArrayByte implements Serializable, Platform
{
	private static final long serialVersionUID = 1L;
	private Object code;
	private String sender;
	private byte[] buff;
	private List<FileItem> lis;
	private int status;

	public ArrayByte( String sender, Object code, byte[] buff )
	{
		super();
		this.sender	= sender;
		this.code	= code;
		this.buff	= buff;
		lis = null;
	}
	

	public ArrayByte( Object code )
	{
		super();
		this.code = code;
	}



	public ArrayByte( Object code, byte[] buff )
	{
		super();
		this.code	= code;
		this.buff	= buff;
	}

	public ArrayByte( Object code, byte[] buff, int status )
	{
		super();
		this.code	= code;
		this.buff	= buff;
		this.status	= status;
	}
	
	public byte[] toByteArray( ) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(this);
		oos.flush();
		oos.close();
		baos.close();
		return baos.toByteArray();
	}

	public static byte[] OBJtoByte( Object obj) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(obj);
		oos.flush();
		oos.close();
		baos.close();
		return baos.toByteArray();
	}

	public static Object getObject( byte[] arr) throws ClassNotFoundException, IOException
	{
		ByteArrayInputStream bais = new ByteArrayInputStream(arr);
		ObjectInputStream ois = new ObjectInputStream(bais);
		Object tran = ois.readObject();
		ois.close();
		bais.close();
		return tran;
	}
	
	public String getSender( )
	{ return sender; }

	public void setSender( String sender)
	{ this.sender = sender; }

	public Object getCode( )
	{ return code; }

	public void setCode( Object code)
	{ this.code = code; }

	public byte[] getData( )
	{ return buff; }

	public void setBuff( byte[] buff)
	{ this.buff = buff; }

	public int getStatus( )
	{ return status; }

	public void setStatus( int status)
	{ this.status = status; }


	public List<FileItem> getItem( )
	{ return lis; }


	public void setItem( List<FileItem> lisItem)
	{ this.lis = lisItem; }
	
	
}
