package bigbook.transfer;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

import bigbook.Platform.Transfer;

public class MessageBuffer implements Transfer{
	private static final long serialVersionUID = 1L;
	public int lenght;
	public int capacity;
	private Queue<byte[]> queue;

	public MessageBuffer() {
		queue = new LinkedList<byte[]>();
		capacity = 100;
	}


	@Override
	public Command get() {
		return null;
	}

	@Override
	public byte[] getData() {
		return queue.peek();
	}

	@Override
	public ByteBuffer toByteBuffer() throws Exception {
		byte[] data = queue.poll();
		ByteBuffer buffer = ByteBuffer.allocate(queue.poll().length);
		buffer.put(data);
		lenght = queue.size();
		return buffer;
	}

	public Queue<byte[]> getQueue() {
		return queue;
	}
	
	public void setQueue(Queue<byte[]> queue) {
		this.queue = queue;
	}
}
