package bigbook.listen.running;

import java.nio.channels.SocketChannel;

import bigbook.Platform.Platform;
import bigbook.reprement.Account;
import bigbook.transfer.data.DataTransfer;
import bigbook.transfer.data.Message;
import load.resource.Print;
import load.resource.Print.Content;

public class Request implements Platform, Runnable {
	private DataTransfer data;
	private String username;
	private SocketChannel socket;
	private Response response;

	public Request(DataTransfer data, SocketChannel socket) {
		super();
		this.data = data;
		this.socket = socket;
		response = new Response(socket);
		username = null;
	}

	@Override
	public void run() {
		Account acc = null;
		Message mes = null;
		SocketChannel rev = null;

		try {
			switch (data.get()) {
			case RQxLogin:
				acc = (Account) data.toObject();
				String id = Account.checkLogin(acc.getUsername(), acc.getPassword());
				if (id != null) {
					USERS_LOGIN.put(acc.getUsername(), socket);
					data.setCode(Command.RPxLogin);
					response.handle(data);
				}
				break;
			case RQxSMessage:
				mes = (Message) data.toObject();
				Print.out(Content.MESSAGE, mes);
				data.setCode(Command.RPxSMessage);
				rev = USERS_LOGIN.get(mes.getReciver());
				if (rev != null) {
					rev.write(data.toByteBuffer());
				}
				response.handle(data);
				break;
			default:
				break;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public DataTransfer getData() {
		return data;
	}

	public void setData(DataTransfer data) {
		this.data = data;
	}

	public SocketChannel getSocket() {
		return socket;
	}

	public void setSocket(SocketChannel socket) {
		this.socket = socket;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
