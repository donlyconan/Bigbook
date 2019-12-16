package bigbook.listen.running;

import java.io.IOException;
import java.nio.ByteBuffer;

import bigbook.Platform.Platform;
import bigbook.listen.action.NIOSocketChannelID;
import bigbook.transfer.buffer.Message;
import ui.Print;
import ui.Print.Content;

public class ServerResponse implements Platform {
	private NIOSocketChannelID channel;
	private ByteBuffer buffer;

	public ServerResponse(NIOSocketChannelID socket) {
		this.channel = socket;
	}

	public void handle() {
		synchronized (channel) {
//			Message data = Message.createMessage((byte[]) channel.attachment());

//			Response mode = Response.valueOf(data.header());
			try {
//				Print.out(new String(data.pack()));
				channel.enableReadMode();

				switch (Response.RPxSMessage) {
				case RPxLogin:
//					data.setHeader(mode);
//				socket.write(data.toByteBuffer());

					break;
				case RPxLogout:

					break;
				case RPxMAccount:

					break;
				case RPxSConnectVoiceChat:

					break;
				case RPxSFile:

					break;
				case RPxSFinishVoiceChat:

					break;
				case RPxSImage:

					break;
				case RPxSMessage:

					int write = channel.write(ByteBuffer.wrap("1hhhhhhhhhhhhhhhhh".getBytes()));
					Print.out(Content.MODExWRITE, "  Data wirte=" + write);

					break;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			
			channel.enableReadMode();
		}
	}

	public NIOSocketChannelID getSocket() {
		return channel;
	}

	public void setSocket(NIOSocketChannelID socket) {
		this.channel = socket;
	}

	public NIOSocketChannelID getChannel() {
		return channel;
	}

	public void setChannel(NIOSocketChannelID channel) {
		this.channel = channel;
	}

	public ByteBuffer getBuffer() {
		return buffer;
	}

	public void setBuffer(ByteBuffer buffer) {
		this.buffer = buffer;
	}

}
