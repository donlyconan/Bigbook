package bigbook.listen.Running.Task;

import bigbook.listen.Listen;
import bigbook.listen.Running.SocketRunning;
import bigbook.service.Message;

public class Login
{
	public static boolean CheckedLogin(String user, String pass)
	{
		return true;
	}
	
	public static void comfirm(Object obj, SocketRunning socket) {
		Message sms = (Message) obj;
		Listen.getUserOnline().put(sms.getReciever(), socket);
	}
}
