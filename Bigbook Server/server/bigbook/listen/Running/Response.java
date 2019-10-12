package bigbook.listen.Running;

import java.io.IOException;

import bigbook.Platform.Platform;
import bigbook.listen.Listen;
import bigbook.service.Message;
import javafx.collections.ObservableMap;

public class Response implements Platform
{
	private static final ObservableMap<String,SocketRunning> USER_ONLINE = Listen.getUserOnline();
	
	public static void sendUser(Message sms) throws IOException {
		SocketRunning running = USER_ONLINE.get(sms.getReciever());
		sms.setCode(CMxMS_RECIEVE);
		running.Send(sms);
	}
	
	public static void sendGroup(Message sms) {
		
	}
}
