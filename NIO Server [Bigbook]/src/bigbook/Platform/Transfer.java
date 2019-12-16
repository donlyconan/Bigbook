package bigbook.Platform;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

//Mã hóa lưu truyền file
public interface Transfer extends Platform, Serializable {
	public static final int KB = 1024;
	public static final int MB = (int) Math.pow(KB, 2);
	public static final int GB = (int) Math.pow(MB, 2);
	public static final int CAPACITY_SMALL = 4 * KB;
	public static final int CAPACITY_MEDIUM = 64 * KB;
	public static final int CAPACITY_LARGE = 1024 * KB;
	public static final int QUATER_OF_KB = KB / 4;
	public static final int HAFT_OF_KB = KB / 2;

	public String code();

	public byte[] data();

	public ByteBuffer toByteBuffer() throws Exception;

	public static ByteBuffer toByteBuffer(Object object) throws IOException {
		byte[] data = toByteArray(object);
		ByteBuffer buffer = ByteBuffer.allocate(data.length);
		buffer.put(data);
		return buffer;
	}
	
	public static byte[] toByteArray(Object object) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(object);
		oos.flush();
		oos.close();
		baos.close();
		return baos.toByteArray();
	}

	public static Object getObject(byte[] arr) throws ClassNotFoundException, IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(arr);
		ObjectInputStream ois = new ObjectInputStream(bais);
		Object tran = ois.readObject();
		ois.close();
		bais.close();
		return tran;
	}
}
