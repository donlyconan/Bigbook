package bigbook.transfer.buffer;

import bigbook.Platform.Platform;

/**
 * Buffer class basics
 * @author donly
 *
 */
public interface Buffer extends Platform {

	/**
	 * put array data into buffer 
	 * @param data
	 */
	void put(byte[] data);
   
   /**
    * Put array data form off to leg into buffer
    * @param data
    * @param off
    * @param lenght
    */
   public void put(byte[] data, int off, int lenght);
   
   
   /**
    * Flip pos to limit
    */
   public void flip();

   /**
    * Checking has next elem
    * @return
    */
   public boolean hasNext();

   /**
    * return elem check
    * @return
    */
   public byte[] next();

   /**
    * get elem data form 0 to lenght
    * @param data
    */
   public void get(byte[] data);
   
   /**
    * get data form start to end
    * @param data
    * @param start
    * @param end
    */
   public void get(byte[] data, int start, int end);
   
   /**
    * Get data form start to end of buffers
    * @param data
    * @param lenght
 * @return 
    */
   public byte[] get(int start, int lenght);
   
   /**
    * Get size data remmaining of arrary byte
 * @return 
    */
   public int remaining();
   
   /**
    * set Pos = 0 && limit = cap
    */
   public void reset();

   
}
