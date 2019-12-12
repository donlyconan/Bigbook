package bigbook.transfer;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;

import bigbook.Platform.Transfer;

public class DataTransfer implements Transfer, Serializable {
	private static final long serialVersionUID = 1L;
	private String code = null;
	private String sender = null;
	private String reciever = null;
	private byte[] data = null;
	private Object object = null;
	private long time = 0;

	public DataTransfer(Object code) {
		super();
		this.code = code.toString();
	}

	public DataTransfer(String code, String sender, String reciever, Object struct, long time) {
		super();
		this.code = code;
		this.sender = sender;
		this.reciever = reciever;
		this.time = time;
	}

	public DataTransfer(Object code, Object struct) {
		super();
		this.code = code.toString();
	}

	public DataTransfer(String code, String sender, String reciever, ByteBuffer buffer, long time) {
		super();
		this.code = code;
		this.sender = sender;
		this.reciever = reciever;
		this.time = time;
	}

	public DataTransfer(Object code, String send, String rev, byte[] data) {
		super();
		this.code = code.toString();
		this.sender = send;
		this.reciever = rev;
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
		this.sender = send;
		this.reciever = rev;
		this.data = data;
		this.time = time;
	}

	public static DataTransfer valuesOf(Object value) {
		return (DataTransfer) value;
	}

	public static DataTransfer valuesOf(byte[] data) throws ClassNotFoundException, IOException {
		return (DataTransfer) Transfer.getObject(data);
	}

	@Override
	public String code() {
		return code;
	}

	public Request request() {
		return Request.valueOf(code);
	}

	public Respone respone() {
		return Respone.valueOf(code);

	}

	public byte[] data() {
		return data;
	}

	public Object toObject() throws ClassNotFoundException, IOException {
		return Transfer.getObject(data);
	}

	@Override
	public ByteBuffer toByteBuffer() throws IOException {
		return Transfer.toByteBuffer(this);
	}

	public String getCode() {
		return code;
	}

	public void setCode(Object code) {
		this.code = code.toString();
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReciever() {
		return reciever;
	}

	public void setReciever(String reciever) {
		this.reciever = reciever;
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

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public byte[] getData() {
		return data;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
