package bigbook.Platform;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


//Mã hóa lưu truyền file
public interface Transfer extends Platform, Serializable{
	public static final int HAFT_OF_KILOBYTE = 512;
	public static final int KILOBYTE = 1024;
	public static final int MEGABYTE = (int) Math.pow(KILOBYTE, 2);
	public static final int GIGABYTE = (int) Math.pow(MEGABYTE, 2);
	public static final int PETABYTE = (int) Math.pow(GIGABYTE, 2);
	
	public Command get();
	
	public byte[] getData();
	
	public byte[] toBytes() throws Exception;
	
	public static byte[] toBytes( Object obj) throws IOException
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
}
