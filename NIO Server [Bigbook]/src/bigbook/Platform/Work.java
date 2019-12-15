package bigbook.Platform;

import java.io.ObjectInputStream;

public interface Work extends Kernel {
	
	//Giử một thông điệp, yêu cầu
	public void send(Object data) throws Exception;
	
	public void send(byte[] data) throws Exception;
	
	//Trả lời một thông điệp hay yêu cầu nào đó.
	public static Object rev(ObjectInputStream ois) throws Exception
	{
		byte[] buff = new byte[10 * Transfer.HAFT_OF_KB];
		ois.read(buff);
		return Transfer.getObject(buff);
	}
	
	
}
