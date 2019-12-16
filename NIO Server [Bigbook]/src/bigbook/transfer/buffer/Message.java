package bigbook.transfer.buffer;

import java.nio.ByteBuffer;

/**
 * Hanle Message buffer handel partition devided by message buffer
 * 
 * @author donly
 *
 */
public class Message implements NIOPackage {
	public byte[] header = new byte[PAR_HEADER];
	public byte[] footer = new byte[PAR_FOOTER];
	private byte[] data = null;

	public Message(byte[] data) {
		super();
		this.data = data;
	}

	public Message(Object header, byte[] data, byte[] footer) {
		super();
		this.footer = footer;
		this.data = data;
		this.setHeader(header);
	}

	public Message(Object header, byte[] data) {
		super();
		this.data = data;
		this.setHeader(header);
	}

	public static Message createMessage(byte[] root) {
		if (root.length < PAR_FOOTER + PAR_HEADER)
			return null;
		byte[] head = cutArray(root, 0, PAR_HEADER);
		byte[] data = cutArray(root, head.length, root.length - PAR_FOOTER);
		byte[] foot = cutArray(root, root.length - PAR_FOOTER, root.length);
		return new Message(head, data, foot);
	}

	public static Message createMessage(ByteBuffer bufferReader) {
		byte[] data = new byte[bufferReader.limit()];
		bufferReader.position(0);
		int i = 0;
		while (bufferReader.hasRemaining()) {
			data[i++] = bufferReader.get();
		}
		return createMessage(data);
	}

	public static Message createMessage(Object header, byte[] data) {
		Message mes = new Message(data);

		if (header == null)
			throw new NullPointerException("Header can't null");
		else
			mes.setHeader(header);
		return mes;
	}

	public static Message createMessage(Object header, byte[] data, byte[] footer) {
		if (header == null)
			throw new NullPointerException("Header can't null");
		return new Message(header, data, footer);
	}

	public int lenght() {
		return data.length + header.length + footer.length;
	}

	@Override
	public void put(byte[] data) {
		this.data = data;
	}

	@Override
	public void setHeader(Object object) {
		byte[] data = object.toString().getBytes();

		for (int i = 0; i < Math.min(data.length, PAR_HEADER); i++) {
			header[i] = data[i];
		}
	}

	@Override
	public String toString() {
		String head = new String(header).trim();
		String food = new String(footer).trim();
		String body = new String(data).trim();
		return "Message [header=" + head+ ", footer=" + food + ", data="
				+ body + "]";
	}

	@Override
	public void setFooter(Object object) {
		byte[] data = object.toString().getBytes();

		for (int i = 0; i < Math.min(data.length, PAR_FOOTER); i++) {
			header[i] = data[i];
		}
	}

	@Override
	public String header() {
		String code = new String(header);
		code.trim();
		return code;
	}

	@Override
	public byte[] footer() {
		return footer;
	}

	@Override
	public byte[] data() {
		return data;
	}

	@Override
	public byte[] pack() {
		int size = header.length + data.length + footer.length;
		byte[] pack = new byte[size];

		arrayCopy(pack, 0, header, header.length);
		if (data != null)
			arrayCopy(pack, header.length, data, data.length);
		arrayCopy(pack, data.length + header.length, footer, footer.length);

		return pack;
	}

	public static byte[] cutArray(byte[] arr, int start, int end) {
		byte[] data = new byte[end - start];
		int index = 0;

		while (start < end) {
			data[index] = arr[start];
			start++;
			index++;
		}
		return data;
	}

	public static void arrayCopy(byte[] data, int start, byte[] copy, int lenght) {
		int index = 0;
		while (index < lenght) {
			data[start++] = copy[index++];
		}
	}

	public byte[] getHeader() {
		return header;
	}

	public void setHeader(byte[] header) {
		this.header = header;
	}

	public byte[] getFooter() {
		return footer;
	}

	public void setFooter(byte[] footer) {
		this.footer = footer;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}
