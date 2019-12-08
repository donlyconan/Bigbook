package bigbook.listen.running;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import bigbook.Platform.Work;
import bigbook.transfer.data.Bytes;

public class SocketRunning extends Thread implements Work {
	private String id;
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Request req;
	private Response res;

	public SocketRunning(Socket socket) throws Exception {
		super();
		this.socket = socket;
		Init();
	}

	private void Init() throws Exception {
		ois = new ObjectInputStream(socket.getInputStream());
		oos = new ObjectOutputStream(socket.getOutputStream());
		res = new Response(oos, ois);
		req = new Request(oos, ois);
	}

	@Override
	public void run() {
		Bytes data = null;
		Command cmd = null;
		while (!socket.isClosed()) {
			try {
				data = (Bytes) Work.rev(ois);
				cmd = res.handle(data);
				req.handle(cmd, data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void throwError(Error e) 
	{
		
	}

	public void start() {
		super.start();
	}

	public void restart() throws Exception {
		this.Init();
		this.start();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void xstop() throws Exception {
		super.stop();
	}

	@Override
	public void close() throws Exception {
		if (!socket.isClosed()) {
			USERS.remove(id);
			ois.close();
			oos.flush();
			oos.close();
			socket.close();
		}
	}

	@Override
	public void send(Object data) throws Exception {
	}

	@Override
	public void send(byte[] data) throws Exception {
	}

}
