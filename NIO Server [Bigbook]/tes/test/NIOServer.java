package test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import bigbook.transfer.PackageData;

public class NIOServer {
	private ServerSocketChannel server;
	private Selector seletor;

	public NIOServer() throws IOException {
		seletor = Selector.open();
		server = ServerSocketChannel.open();
		server.bind(new InetSocketAddress("localhost", 1111));
		server.configureBlocking(false);
		server.register(seletor, SelectionKey.OP_ACCEPT);
	}

	public void run() throws ClassNotFoundException, IOException {
		System.out.println("Start server!");
		while (true) {
			try {
				seletor.select();
				Set<SelectionKey> keys = seletor.selectedKeys();
				Iterator<SelectionKey> iter = keys.iterator();

				while (iter.hasNext()) {
					SelectionKey key = iter.next();
					iter.remove();

					if (key.isAcceptable()) {
						SocketChannel chanle = server.accept();
						chanle.configureBlocking(false);
						chanle.register(seletor, SelectionKey.OP_READ);
						System.out.println("Accept " + chanle.getLocalAddress());
					}

					if (key.isReadable()) {
						ByteBuffer buff = ByteBuffer.allocate(1000);
						SocketChannel chanel = (SocketChannel) key.channel();
						chanel.read(buff);
						System.out.println(new String(buff.array()));
					}

					if (key.isConnectable()) {
						key.cancel();
						SocketChannel chanel = (SocketChannel) key.channel();
						chanel.close();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		NIOServer server = new NIOServer();
		server.run();
	}
}
