package bigbook.listen.Running;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import bigbook.Platform.Work;
import bigbook.service.Message;

public class SocketRunning extends Thread implements Work
{
	private static final long serialVersionUID = 1L;
	private Socket client;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public SocketRunning( Socket client ) throws Exception
	{
		super();
		this.client = client;
		this.Start();
	}

	@Override
	public void run( ) {
		try {
			Message sms = (Message) Recieve();
			Handle(sms);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				Close();
			} catch (Exception e) {}
		}
	}

	public void Handle( Message mes) {

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

	@SuppressWarnings( "deprecation" )
	@Override
	public void Stop( ) throws Exception {
		this.stop();
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
