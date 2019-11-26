package bigbook.listen.Running;

import java.io.IOException;
import java.util.Hashtable;

import bigbook.Platform.Platform;
import bigbook.listen.Listen;
import bigbook.service.Message;

public class Response implements Platform
{
	private static final Hashtable<String,SocketRunning> USER_ONLINE = Listen.getUserOnline();
	
	public static void sendUser(Message sms) throws IOException {
		SocketRunning running = USER_ONLINE.get(sms.getReciever());
		sms.setCode(CMxMS_RECIEVE);
//		running.Send(sms);
	}
	
	public static void sendGroup(Message sms) {
		
	}
}
