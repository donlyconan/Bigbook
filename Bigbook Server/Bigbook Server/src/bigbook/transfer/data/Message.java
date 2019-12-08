package bigbook.transfer.data;

import java.io.Serializable;

import bigbook.Platform.Platform;

public class Message implements Platform, Serializable {
	private static final long serialVersionUID = 1L;
	private String sender;
	private String reciver;
	private String content;
	private String time;

	public Message(String sender, String reciver, String content) {
		super();
		this.sender = sender;
		this.reciver = reciver;
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "Message [sender=" + sender + ", reciver=" + reciver + ", content=" + content + ", time=" + time + "]";
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReciver() {
		return reciver;
	}

	public void setReciver(String reciver) {
		this.reciver = reciver;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getTime() {
		return Long.valueOf(time);
	}

	public void setTime(long time) {
		this.time = String.valueOf(time);
	}
}
