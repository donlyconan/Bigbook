package bigbook.transfer.data;

import java.io.IOException;
import java.util.Arrays;

import bigbook.Platform.Transfer;

public class Message implements Transfer {
	private static final long serialVersionUID = 1L;

	private String code;
	private String send;
	private String rev;
	private byte[] data;
	private long time;

	public Message(Object code) {
		super();
		this.code = code.toString();
	}

	public Message(Object code, String send, String rev, byte[] data) {
		super();
		this.code = code.toString();
		this.send = send;
		this.rev = rev;
		this.data = data;
	}

	public Message(Object code, byte[] data) {
		super();
		this.code = code.toString();
		this.data = data;
	}

	public Message(Object code, String send, String rev, byte[] data, long time) {
		super();
		this.code = code.toString();
		this.send = send;
		this.rev = rev;
		this.data = data;
		this.time = time;
	}

	@Override
	public Command get() {
		return Command.valueOf(code);
	}

	public byte[] getData() {
		return data;
	}

	@Override
	public byte[] toBytes() throws IOException {
		return Transfer.toBytes(this);
	}

	@Override
	public String toString() {
		return "Message [code=" + code + ", send=" + send + ", rev=" + rev + ", data=" + Arrays.toString(data)
				+ ", time=" + time + "]";
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSend() {
		return send;
	}

	public void setSend(String send) {
		this.send = send;
	}

	public String getRev() {
		return rev;
	}

	public void setRev(String rev) {
		this.rev = rev;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}
