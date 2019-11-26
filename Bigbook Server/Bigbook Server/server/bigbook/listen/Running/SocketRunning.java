package bigbook.listen.Running;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Hashtable;

import bigbook.Platform.Work;
import bigbook.listen.Listen;
import bigbook.reprement.Group.Group;
import bigbook.service.Message;

public class SocketRunning extends Thread implements Work
{
	private static final long serialVersionUID = 1L;
	private static final Hashtable<String, SocketRunning> USER_ONLINE = Listen.getUserOnline();
	private static final Hashtable<String, Group> GROUP_IDS = Listen.getGroupId();

	private Socket client;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public SocketRunning( Socket client ) throws Exception
	{
		super();
		this.client = client;
		Start();
	}

	@Override
	public void run( ) {
		try {
			Message sms = (Message) Recieve();
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				Close();
			} catch (Exception e) {}
		}
	}

	@Override
	public void Send( Object data) throws Exception { oos.writeObject(data); }

	@Override
	public Object Recieve( ) throws Exception { return ois.readObject(); }

	@Override
	public void Start( ) throws Exception {
		ois	= new ObjectInputStream(client.getInputStream());
		oos	= new ObjectOutputStream(client.getOutputStream());
		this.start();
	}

	@Override
	public void Stop( ) throws Exception {
		this.Stop();
		oos.close();
		ois.close();
	}

	@Override
	public void Restart( ) throws Exception {
		Stop();
		Start();
	}

	@Override
	public void Close( ) throws Exception {
		oos.close();
		ois.close();
		client.close();
	}

	public Socket getClient( ) { return client; }

	public void setClient( Socket client) { this.client = client; }

	public ObjectInputStream getOis( ) { return ois; }

	public void setOis( ObjectInputStream ois) { this.ois = ois; }

	public ObjectOutputStream getOos( ) { return oos; }

	public void setOos( ObjectOutputStream oos) { this.oos = oos; }

}
