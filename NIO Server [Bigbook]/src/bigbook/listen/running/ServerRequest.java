package bigbook.listen.running;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

import bigbook.Platform.Platform;
import bigbook.listen.action.NIOSocketChannelID;
import bigbook.reprement.Account;
import bigbook.transfer.DataPackage;
import bigbook.transfer.DataReader;
import bigbook.transfer.buffer.Message;
import ui.Print;
import ui.Print.Content;

public class ServerRequest implements Platform {
	private NIOSocketChannelID channel = null;
	private DataReader dataReader = null;
	private Account account = null;
	private Message message;
	private DataPackage data;
	private ServerResponse response;

	public ServerRequest(NIOSocketChannelID socketID) {
		super();
		this.channel = socketID;
		response = new ServerResponse(socketID);
	}

	public ServerRequest(NIOSocketChannelID socket, ByteBuffer bufferReader) {
		super();
		this.channel = socket;
		this.message = Message.createMessage(bufferReader);
		response = new ServerResponse(socket);
	}

	public void handle() {
		synchronized (channel) {
			try {
				Print.out(Content.MODExREAD, new String(message.data()));
				channel.attach(message.pack());
				Print.out(new String(message.pack()));
				response.handle();

				switch (Request.RQxSMessage) {
				case RQxLogin:
//					account = (Account) Transfer.getObject(data.data());
//					Print.out(Content.ACCOUNT, account);
//					data.setResponse(Response.RPxSMessage);
//					IDchannel.flip();

					break;
				case RQxMAccount:

					break;
				case RQxSMessage:
					channel.attach("123444");

					break;
				case RQxSConnectVoiceChat:

					break;
				case RQxSFile:

					break;
				case RQxSFinishVoiceChat:

					break;
				case RQxSImage:

					break;
				case RQxLogout:
					Print.out(channel.channel().getRemoteAddress() + " is quiting");
					channel.enableConnectMode();
					break;
				default:
					break;
				}

			} catch (Exception e) {
				Print.out(Content.ERROR, e.getMessage() + " code=" + (data != null ? data.getCode() : null));
				channel.enableConnectMode();
				e.printStackTrace();
			}
			
			channel.enableWriteMode();
		}
	}

	public DataReader getDataReader() {
		return dataReader;
	}

	public void setDataReader(DataReader dataReader) {
		this.dataReader = dataReader;
	}

	public DataPackage getData() {
		return data;
	}

	public void setData(DataPackage data) {
		this.data = data;
	}

	public NIOSocketChannelID getChannel() {
		return channel;
	}

	public void setChannel(NIOSocketChannelID channel) {
		this.channel = channel;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public ServerResponse getResponse() {
		return response;
	}

	public void setResponse(ServerResponse response) {
		this.response = response;
	}

}
