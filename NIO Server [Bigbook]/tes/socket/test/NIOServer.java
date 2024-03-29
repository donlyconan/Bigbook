package socket.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.security.Key;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

	private static SelectionKey myKey;

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {

		// Selector: multiplexor of SelectableChannel objects
		Selector selector = Selector.open(); // selector is open here

		// ServerSocketChannel: selectable channel for stream-oriented listening sockets
		ServerSocketChannel crunchifySocket = ServerSocketChannel.open();
		InetSocketAddress crunchifyAddr = new InetSocketAddress("localhost", 8888);

		// Binds the channel's socket to a local address and configures the socket to
		// listen for connections
		crunchifySocket.bind(crunchifyAddr);

		// Adjusts this channel's blocking mode.
		crunchifySocket.configureBlocking(false);

		int ops = crunchifySocket.validOps();
		SelectionKey selectKy = crunchifySocket.register(selector, ops, null);

		// Infinite loop..
		// Keep server running
		while (true) {

//			log("i'm a server and i'm waiting for new connection and buffer select...");
			// Selects a set of keys whose corresponding channels are ready for I/O
			// operations
			int z = selector.selectNow();

			// token representing the registration of a SelectableChannel with a Selector
			Set<SelectionKey> crunchifyKeys = selector.selectedKeys();
			Iterator<SelectionKey> crunchifyIterator = crunchifyKeys.iterator();

			while (crunchifyIterator.hasNext()) {
				myKey = crunchifyIterator.next();
				crunchifyIterator.remove();
				System.out.println("Index="+z);

				try {
					
					if(!myKey.isValid()) continue;
					
					// Tests whether this key's channel is ready to accept a new socket connection
					if (myKey.isAcceptable()) {
						SocketChannel crunchifyClient = crunchifySocket.accept();
						
						// Adjusts this channel's blocking mode to false
						crunchifyClient.configureBlocking(false);
						
						// Operation-set bit for read operations
						crunchifyClient.register(selector, SelectionKey.OP_READ);
						log("Connection Accepted: " + crunchifyClient.getLocalAddress() + "\n");
						
						// Tests whether this key's channel is ready for reading
					} else if (myKey.isReadable()) {
						
						SocketChannel crunchifyClient = (SocketChannel) myKey.channel();
						System.out.println("Open=" + crunchifyClient.isOpen());
						System.out.println("connected=" + crunchifyClient.isConnected());
						System.out.println("isConnectionPending=" + crunchifyClient.isConnectionPending());
						System.out.println("isBlocking=" + crunchifyClient.isBlocking());
						
						if(!crunchifyClient.isOpen() )
						{
							crunchifyClient.finishConnect();
							crunchifyClient.close();
						}
						else {
							ByteBuffer crunchifyBuffer = ByteBuffer.allocate(256);
							crunchifyClient.read(crunchifyBuffer);
							String result = new String(crunchifyBuffer.array()).trim();
							
							log("Message received: " + result);
						}
						
//					if (result.equals("Crunchify")) {
//						crunchifyClient.close();
//						log("\nIt's time to close connection as we got last company name 'Crunchify'");
//						log("\nServer will keep running. Try running client again to establish new connection");
////					}
					}
				} catch (Exception e) {
					myKey.cancel();
					myKey.channel().close();
					e.printStackTrace();
				}
			}
		}
	}

	private static void log(String str) {
		System.out.println(str);
	}
}
