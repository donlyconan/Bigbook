package fun;

import java.nio.ByteBuffer;

/**
 * 
 * @author donly
 * @version 1.0.0
 */
public interface BaseBuffer {

	/**
	 * kich thuoc mang da dung
	 */
	public int length();
	
	/**
	 * Lay kich thuoc mang hien tai
	 * @return
	 */
	public int capacity();

	/**
	 * Mo rong kich thuoc
	 */
	public void extend(int limit);
	/**
	 * Day du lieu tu buffer nguon vao dich
	 * @param buffer
	 * @param off
	 * @param lenght
	 */
	public void put(ByteBuffer buffer, int off, int length);
	
	/**
	 * Day toan bo du lieu tu buffer nguon vao dich tu pos -> limit
	 * @param buffer
	 */
	public void put(ByteBuffer buffer);

	/**
	 * Day du lieu vau
	 * 
	 * @param data
	 * @param off
	 * @param lenght
	 */
	public void put(byte[] bytes, int off, int length);

	/**
	 * day mot mang byte vao
	 * 
	 * @param bytes
	 */
	public void put(byte[] bytes);
	
	/**
	 * Lay mot phan du lieu vao mang
	 * @param bytes
	 * @param off
	 * @param lenght
	 */
	public void get(byte[] bytes, int off, int length);
	
	/**
	 * Do du lieu vao mang
	 * @param bytes
	 */
	public void get(byte[] bytes);
	
	/**
	 * lay mang bytebuffer da cho
	 * @return
	 */
	public ByteBuffer buffer();
}
