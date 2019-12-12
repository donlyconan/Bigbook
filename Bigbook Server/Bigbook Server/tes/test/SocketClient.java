package test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import bigbook.Platform.Platform;
import bigbook.reprement.Account;
import bigbook.transfer.DataTransfer;

public class SocketClient implements Platform {
	
	public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
		InetSocketAddress inet = new InetSocketAddress("localhost", 8888);
		SocketChannel socketClient = SocketChannel.open(inet);
		
		Logger.getAnonymousLogger().log(Level.CONFIG,"Connecting to Server on port 8888...");
		ArrayList<String> companyDetails = new ArrayList<String>();
 
		// create a ArrayList with companyName list
		companyDetails.add("Facebook");
		companyDetails.add("Twitter");
		companyDetails.add("IBM");
		companyDetails.add("Google");
		companyDetails.add("Crunchify");
		
		Thread thread = new Thread(()-> {
			while(true)
			{
				ByteBuffer buff = ByteBuffer.allocate(1024);
				try {
					socketClient.read(buff);
					DataTransfer data = DataTransfer.valuesOf(buff.array());
					System.out.println("Reciver:" + data);
					buff.clear();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
		
		Account acc = new Account("donlyconan", "root");
		DataTransfer data = new DataTransfer(Request.RQxLogin, acc);
		socketClient.write(data.toByteBuffer());
		
		Thread.sleep(2000);
 
		for (int i = 0; i <= 1000; i++) {
 
			String content = companyDetails.get(i%companyDetails.size());
			DataTransfer  rename = new DataTransfer(Request.RQxSMessage, content);
			socketClient.write(rename.toByteBuffer());
 
			System.out.println("sending: " + rename);
 
			Thread.sleep(2000);
			
			if(!socketClient.isConnected())
				break;
		}
		socketClient.finishConnect();
		socketClient.close();
	}
}
