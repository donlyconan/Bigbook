package fun;

import java.nio.ByteBuffer;

public class MessageBuffer implements BaseBuffer {
	public static final int SIZE_UP = 8;
	public static final int KB = 1024;
	public static final int MB = (int) Math.pow(KB, 2);
	public static final int[] SIZE = { 4 * KB, 16 * KB, 128 * KB, MB, 10 * MB };

	private int curIndex = 0;
	private ByteBuffer byteBuffer;

	private MessageBuffer(byte[] bytes) {
		byteBuffer = ByteBuffer.wrap(bytes);
	}

	private MessageBuffer(int cap) {
		byteBuffer = ByteBuffer.allocate(cap);
	}

	public MessageBuffer(ByteBuffer byteBuffer) {
		super();
		this.byteBuffer = byteBuffer;
	}

	public static MessageBuffer wrap(byte[] bytes) {
		MessageBuffer message = new MessageBuffer(bytes);
		return message;
	}

	public static MessageBuffer wrap(ByteBuffer buffer) {
		MessageBuffer message = new MessageBuffer(buffer);
		return message;
	}

	public static MessageBuffer allocate(int capacity) {
		MessageBuffer message = new MessageBuffer(capacity);
		return message;
	}

	@Override
	public void put(ByteBuffer buffer) {
		this.put(buffer.array(), buffer.position(), buffer.limit());
	}

	@Override
	public void put(ByteBuffer buffer, int off, int length) {
		this.put(buffer.array(), off, length);
	}

	@Override
	public int length() {
		return byteBuffer.remaining();
	}

	@Override
	public int capacity() {
		return byteBuffer.capacity();
	}

	@Override
	public void extend(int limit) {
		curIndex++;
		int cap = SIZE[curIndex];
		cap = Math.max(cap, limit);

		ByteBuffer buffer = ByteBuffer.allocate(cap);
		if (byteBuffer.position() != 0)
			byteBuffer.flip();

		buffer.put(byteBuffer);

		byteBuffer = buffer;
	}

	@Override
	public void put(byte[] bytes, int off, int lenght) {
		int limit = lenght - off + byteBuffer.position();

		if (limit >= byteBuffer.capacity()) {
			extend(limit);
		}

		byteBuffer.put(bytes);
	}

	@Override
	public String toString() {
		return "MessageBuffer [byteBuffer=" + byteBuffer + "]";
	}

	@Override
	public void put(byte[] bytes) {
		this.put(bytes, 0, bytes.length);
	}

	@Override
	public void get(byte[] bytes, int off, int lenght) {
		byteBuffer.get(bytes, off, lenght);
	}

	@Override
	public void get(byte[] bytes) {
		byteBuffer.get(bytes, 0, bytes.length);
	}

	@Override
	public ByteBuffer buffer() {
		return byteBuffer;
	}

	public void setByteBuffer(ByteBuffer byteBuffer) {
		this.byteBuffer = byteBuffer;
	}
}
