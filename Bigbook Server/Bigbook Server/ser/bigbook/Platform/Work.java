package bigbook.Platform;

import java.io.Serializable;

public interface Work extends Kernel, Runnable, Serializable{
	
	//Giử một thông điệp, yêu cầu
	public void Send(Object data) throws Exception;
	
	//Trả lời một thông điệp hay yêu cầu nào đó.
	public Object Recieve() throws Exception;
	
	
}
