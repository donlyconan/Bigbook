package gui.run.control;

import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Test
{
	public static void main(String[] args) throws Exception {
		List<Socket> lis = new ArrayList<Socket>();
		int index = 0;
		
		while(lis.size() < 100000) {
			Socket socket = new Socket(InetAddress.getLocalHost(), 10000);

			lis.add(socket);
			Thread.sleep(200);
			if(lis.size() > 1000 && index++%8==0) {
				lis.remove(0);
			}
			
		}
		
		for(Socket item : lis) {
			item.close();
		}
		
	}
}
