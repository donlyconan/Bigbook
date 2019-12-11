package test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import bigbook.Platform.Platform;
import bigbook.Platform.Transfer;
import bigbook.reprement.Account;
import bigbook.transfer.data.DataTransfer;

public class SocketClient implements Platform {
	
	public static void main(String[] args) throws InterruptedException, IOException {
		InetSocketAddress crunchifyAddr = new InetSocketAddress("localhost", 8888);
		SocketChannel crunchifyClient = SocketChannel.open(crunchifyAddr);
 
		Logger.getAnonymousLogger().log(Level.CONFIG,"Connecting to Server on port 1111...");
 
		ArrayList<String> companyDetails = new ArrayList<String>();
 
		// create a ArrayList with companyName list
		companyDetails.add("Facebook");
		companyDetails.add("Twitter");
		companyDetails.add("IBM");
		companyDetails.add("Google");
		companyDetails.add("Crunchify");
		
		Thread thread = new Thread(()-> {
//			while(true)
//			{
				ByteBuffer buff = ByteBuffer.allocate(1024);
				try {
					crunchifyClient.read(buff);
					DataTransfer data = DataTransfer.valuesOf(buff.array());
					System.out.println(data);
					buff.clear();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
//				}
			}
		});
		
		thread.start();
		Account acc = new Account("donlyconan", "root");
		DataTransfer data = new DataTransfer(Command.RQxLogin, Transfer.toByteArray(acc));
		crunchifyClient.write(data.toByteBuffer());
// 
//		for (int i = 0; i <= 3; i++) {
// 
//			byte[] message = new String(companyDetails.get(i%companyDetails.size())).getBytes();
//			DataTransfer  data = new DataTransfer(Command.RQxSMessage, message);
//			crunchifyClient.write(data.toByteBuffer());
// 
//			System.out.println("sending: " + companyDetails.get(i%companyDetails.size()));
 
			Thread.sleep(5000);
//		}
		crunchifyClient.finishConnect();
		crunchifyClient.close();
	}
}
