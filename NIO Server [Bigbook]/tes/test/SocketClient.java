package test;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import bigbook.Platform.Platform;
import bigbook.transfer.PackageData;

public class SocketClient extends JFrame implements Platform {
	private static final long serialVersionUID = 1L;
	private JTextArea text;
	private JButton connect;
	SocketChannel socket;
	
	public SocketClient() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Client");
		setSize(400,600);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		
		connect = new JButton("Connect");
		add(connect,BorderLayout.SOUTH);
		
		text = new JTextArea();
		JScrollPane scroll = new JScrollPane(text);
		add(scroll, BorderLayout.CENTER);
		
		connect.addActionListener((e)->{
			try {
				open();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});		
	}
	
	public void open() throws IOException
	{
		socket = SocketChannel.open(new InetSocketAddress("localhost",8888));
		text.setText(socket.toString());
		String content = "Data transfer Packeage: ";
		
		Thread thread1 = new Thread(()-> {	
			for(int i=1; i<=1000;i++)
			{
				PackageData data = new PackageData(Request.RQxSMessage, content + i);
				try {
					socket.write(data.toByteBuffer());
					Thread.sleep(1500);
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
					break;
				}
				text.append(data.toString() + "\n");
			}
		});
		
		Thread thread2 = new Thread(()-> {
			ByteBuffer buff = ByteBuffer.allocate(1000);
			
			while(true)
			{
				PackageData data = null;
				try {
					socket.read(buff);
					data = PackageData.valuesOf(buff.array());
					text.append(data.toString() + "\n");
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
					break;
				}
			}
		});
		
		thread1.start();
		thread2.start();
	}
	
	
	
	
	
	
	
	public static void main(String[] args) {
		new SocketClient().setVisible(true);
	}
}
