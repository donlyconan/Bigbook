package bigbook.transfer.buffer;

import bigbook.Platform.Platform;

/**
 * Package contains Group data
 * Header : info key, enum
 * Foolter : info part, data integrity
 * @author donly
 *
 */
public interface NIOPackage extends Platform {
	public static final int PAR_HEADER = 20;
	public static final int PAR_FOOTER = 12;
	
	/**
	 * Put arr byte into 
	 * @param data
	 */
	public void put(byte[] data);
	
	/**
	 * Set footer from object
	 * @param object
	 */
	public void setHeader(Object object);
	
	/**
	 * Set header from object
	 * @param object
	 */
	public void setFooter(Object object);
	
	/**
	 * return header
	 * @return
	 */
	public String header();
	
	/**
	 * Return foodter
	 * @return
	 */
	public byte[] footer();
	
	/**
	 * Return data
	 * @return
	 */
	
	public byte[] data();
	
	/**
	 * Pack data into groups
	 * @return
	 */
	public byte[] pack();
}
