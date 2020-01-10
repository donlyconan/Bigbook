package IO.Audio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

public class UDPTransfer {
	private DatagramChannel datagram;
	private InetSocketAddress inet;
	
	public UDPTransfer() throws IOException {
		datagram = DatagramChannel.open();
		datagram.bind(inet);
	}
	
	public static DatagramChannel open() throws IOException {
		DatagramChannel data = DatagramChannel.open();
		data.configureBlocking(false);
		return data;
	}
}
