package bigbook.transfer;

import java.nio.ByteBuffer;

import bigbook.Platform.Transfer;

public class Message implements Transfer {
	private static final long serialVersionUID = 1L;
	public int offset = 0;
	public int capacity = 0;
	public int length = 0;
	public byte[] data = null;
	private MessageBuffer messageBuffer = null;
	public byte[] sharedArray;

	public Message(MessageBuffer messageBuffer) {
		this.messageBuffer = messageBuffer;
	}

	public int writeToMessage(ByteBuffer byteBuffer) {
		int remaining = byteBuffer.remaining();

		while (this.length + remaining > capacity) {
//			if (!this.messageBuffer.expandMessage(this)) {
//				return -1;
//			}
		}

		int bytesToCopy = Math.min(remaining, this.capacity - this.length);
		byteBuffer.get(this.data, this.offset + this.length, bytesToCopy);
//		this.length += bytesToCopy;

		return bytesToCopy;
	}

	public int writeToMessage(byte[] byteArray) {
		return writeToMessage(byteArray, 0, byteArray.length);
	}

	public int writeToMessage(byte[] byteArray, int offset, int length) {
		int remaining = length;

		while (this.length + remaining > capacity) {
//			if (!this.messageBuffer.expandMessage(this)) {
//				return -1;
//			}
		}

		int bytesToCopy = Math.min(remaining, this.capacity - this.length);
		System.arraycopy(byteArray, offset, this.data, this.offset + this.length, bytesToCopy);
//		this.length += bytesToCopy;
		return bytesToCopy;
	}

	public void writePartialMessageToMessage(Message message, int endIndex) {
		int startIndexOfPartialMessage = message.offset + endIndex;
		int lengthOfPartialMessage = (message.offset + message.length) - endIndex;

		System.arraycopy(message.data, startIndexOfPartialMessage, this.data, this.offset, lengthOfPartialMessage);
	}

	public int writeToByteBuffer(ByteBuffer byteBuffer) {
		return 0;
	}

	@Override
	public String code() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] data() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ByteBuffer toByteBuffer() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
