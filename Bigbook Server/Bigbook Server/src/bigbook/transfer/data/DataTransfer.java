package bigbook.transfer.data;

import java.io.IOException;
import java.nio.ByteBuffer;

import bigbook.Platform.Transfer;

public class DataTransfer implements Transfer {
	private static final long serialVersionUID = 1L;

	private String code;
	private String send;
	private String rev;
	private byte[] data;
	private long time;

	public DataTransfer(Object code) {
		super();
		this.code = code.toString();
	}

	public DataTransfer(Object code, String send, String rev, byte[] data) {
		super();
		this.code = code.toString();
		this.send = send;
		this.rev = rev;
		this.data = data;
	}

	public DataTransfer(Object code, byte[] data) {
		super();
		this.code = code.toString();
		this.data = data;
	}

	public DataTransfer(Object code, String send, String rev, byte[] data, long time) {
		super();
		this.code = code.toString();
		this.send = send;
		this.rev = rev;
		this.data = data;
		this.time = time;
	}
	
	public DataTransfer createDataTransfer(Object code, byte[] data) {
		return new DataTransfer(code.toString(), data);
	}
	
	
	public static DataTransfer valuesOf(Object value)
	{
		return (DataTransfer) value;
	}
	
	public static DataTransfer valuesOf(byte[] data) throws ClassNotFoundException, IOException
	{
		return (DataTransfer) Transfer.getObject(data);
	}

	@Override
	public Command get() {
		return Command.valueOf(code);
	}
	
	public Object toObject() throws ClassNotFoundException, IOException
	{
		return Transfer.getObject(data);
	}

	public byte[] getData() {
		return data;
	}

	@Override
	public ByteBuffer toByteBuffer() throws IOException {
		return Transfer.toByteBuffer(this);
	}

	@Override
	public String toString() {
		return "Message [code=" + code + ", send=" + send + ", rev=" + rev + ", data=" + data.length
				+ ", time=" + time + "]";
	}

	public String getCode() {
		return code;
	}

	public void setCode(Object code) {
		this.code = code.toString();
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
