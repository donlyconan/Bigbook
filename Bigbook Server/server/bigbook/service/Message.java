package bigbook.service;

import bigbook.Platform.Platform;
import bigbook.Platform.Transfer;

public class Message implements Transfer
{
	private static final long serialVersionUID = 1L;
	
	private int code;
	private String sender;
	private String reciever;
	private String content;
	
	public Message(int code, String reciever, String content)
	{
		this.code = code;
		this.reciever = reciever;
		this.content = content;
	}

	public Message()
	{
		code = Platform.MSxSERVER;
	}

	public Message(int code, String sender, String reciever, String content)
	{
		this.code = code;
		this.sender = sender;
		this.reciever = reciever;
		this.content = content;
	}

	public int getCode() { return code; }

	public void setCode(int code) { this.code = code; }

	public String getSender() { return sender; }

	public void setSender(String sender) { this.sender = sender; }

	public String getReciever() { return reciever; }

	public void setReciever(String reciever) { this.reciever = reciever; }

	public String getContent() { return content; }

	public void setContent(String content) { this.content = content; }
	
	
	
}
