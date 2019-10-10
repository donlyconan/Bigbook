package bigbook.service;

import java.awt.image.BufferedImage;
import java.sql.Date;
import java.util.Arrays;

import bigbook.Platform.Transfer;

public class Message implements Transfer
{
	private static final long serialVersionUID = 1L;
	
	private int code;
	private String sender;
	private String reciever;
	private String content;
	private byte[] buff;
	private Date time;
	private BufferedImage image;
	private int status;
	
	public Message(int code, String sender, String reciever, byte[] buff, Date time, int status)
	{
		super();
		this.code = code;
		this.sender = sender;
		this.reciever = reciever;
		this.buff = buff;
		this.time = time;
		this.status = status;
	}
	
	public Message(int code, String sender, String reciever, String content, Date time, int status)
	{
		super();
		this.code = code;
		this.sender = sender;
		this.reciever = reciever;
		this.content = content;
		this.time = time;
		this.status = status;
	}
	
	
	public Message(int code, String sender, String reciever,Date time, int status)
	{
		super();
		this.code = code;
		this.sender = sender;
		this.reciever = reciever;
		this.time = time;
		this.status = status;
	}
	

	public Message(int code, String sender, String reciever, Date time, BufferedImage image, int status)
	{
		super();
		this.code = code;
		this.sender = sender;
		this.reciever = reciever;
		this.time = time;
		this.image = image;
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "Message [code=" + code + ", sender=" + sender + ", reciever=" + reciever + ", content=" + content
		        + ", buff=" + Arrays.toString(buff) + ", time=" + time + ", image=" + image + ", status=" + status
		        + "]";
	}

	public int getCode() { return code; }
	
	public void setCode(int code) { this.code = code; }
	
	public String getSender() { return sender; }
	
	public void setSender(String sender) { this.sender = sender; }
	
	public String getReciever() { return reciever; }
	
	public BufferedImage getImage() { return image; }

	public void setImage(BufferedImage image) { this.image = image; }

	public void setReciever(String reciever) { this.reciever = reciever; }
	
	public String getContent() { return content; }
	
	public void setContent(String content) { this.content = content; }
	
	public byte[] getBuff() { return buff; }
	
	public void setBuff(byte[] buff) { this.buff = buff; }
	
	public Date getTime() { return time; }
	
	public void setTime(Date time) { this.time = time; }
	
	public int getStatus() { return status; }
	
	public void setStatus(int status) { this.status = status; }
}
