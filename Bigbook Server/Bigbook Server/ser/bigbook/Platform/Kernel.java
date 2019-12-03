package bigbook.Platform;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

//Khởi chạy một đối tượng được xác định
public interface Kernel extends Platform {
	
	public static byte[] OBJtoByte(Object obj) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(obj);
		oos.flush();
		oos.close();
		return baos.toByteArray();
	}
	
	public static Object BytetoOBJ(byte[] buff)
	{
		return new byte[1024];
	}
	
	//Khoi chay 1 doi tuong
	public void Start() throws Exception;
	
	//Cham dut moi hanh dong cua mot doi tuong
	public void Stop() throws Exception;
	
	//Khoi dong lai tien trinh
	public void Restart() throws Exception;
	
	//Dong doi tuong 
	public void Close() throws Exception;
}
