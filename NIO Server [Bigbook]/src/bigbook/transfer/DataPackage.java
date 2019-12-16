package bigbook.transfer;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;

import bigbook.Platform.Transfer;

public class DataPackage implements Transfer, Serializable {
	private static final long serialVersionUID = 1L;
	private String code = null;
	private String sender = null;
	private String reciever = null;
	private byte[] data = null;
	private Object object = null;
	private long time = 0;

	public DataPackage(Object code) {
		super();
		this.code = code.toString();
	}

	public DataPackage(String code, String sender, String reciever, Object struct, long time) {
		super();
		this.code = code;
		this.sender = sender;
		this.reciever = reciever;
		this.time = time;
	}

	public DataPackage(Object code, Object struct) {
		super();
		this.code = code.toString();
		this.object = struct;
	}

	public DataPackage(String code, String sender, String reciever, ByteBuffer buffer, long time) {
		super();
		this.code = code;
		this.sender = sender;
		this.reciever = reciever;
		this.time = time;
	}

	public DataPackage(Object code, String send, String rev, byte[] data) {
		super();
		this.code = code.toString();
		this.sender = send;
		this.reciever = rev;
		this.data = data;
	}

	public DataPackage(Object code, byte[] data) {
		super();
		this.code = code.toString();
		this.data = data;
	}

	public DataPackage(Object code, String send, String rev, byte[] data, long time) {
		super();
		this.code = code.toString();
		this.sender = send;
		this.reciever = rev;
		this.data = data;
		this.time = time;
	}

	public void setRequest(Request req) {
		code = req.toString();
	}

	public void setResponse(Response res) {
		code = res.toString();
	}

	public static DataPackage valuesOf(Object value) {
		return (DataPackage) value;
	}

	public static DataPackage valuesOf(byte[] data) throws IOException {
		try {
			return (DataPackage) Transfer.getObject(data);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String code() {
		return code;
	}

	public Request request() {
		return Request.valueOf(code);
	}

	public Response respone() {
		return Response.valueOf(code);

	}

	public byte[] data() {
		return data;
	}

	@Override
	public String toString() {
		String header = "DataPackage {";
		if (code != null)
			header += "code=" + code + ",";
		if (sender != null)
			header += "sender=" + sender + ",";
		if (reciever != null)
			header += "reciever=" + reciever + ",";
		if (data != null)
			header += "data=" + data.length + "bytes,";
		if (object != null)
			header += "Object=" + object + "INFO,";

		header += "time=" + time + "0&0}";
		return header;
	}

	public Object toObject() throws IOException {
		try {
			return Transfer.getObject(data);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public byte[] toByteArrary() throws IOException {
		return Transfer.toByteArray(this);
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

	@Override
	public ByteBuffer toByteBuffer() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
