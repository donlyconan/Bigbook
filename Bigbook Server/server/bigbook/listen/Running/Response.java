package bigbook.listen.Running;

import bigbook.Platform.Platform;
import bigbook.listen.Listen;
import bigbook.service.Message;
import javafx.collections.ObservableMap;

public class Response implements Platform
{
	private static final ObservableMap<String,SocketRunning> USER_ONLINE = Listen.getUserOnline();
	
	public void sendUser(Message sms) {
		SocketRunning socket = USER_ONLINE.get(sms.getReciever());
		sms.setCode(CMxMS_RECIEVE);
		
		int key = 0;
		
		switch (key)
		{
			case 1:
				
				break;
			
			default:
				break;
		}
	}
	
	public void sendGroup(Message sms) {
		SocketRunning socket = USER_ONLINE.get(sms.getReciever());
		sms.setCode(CMxMS_RECIEVE);
		
		int key = 0;
		switch (key)
		{
			case 1:
				
				break;
			
			default:
				break;
		}
	}
}
