package bigbook.Platform;

import java.io.Serializable;

//Cha của các đối User, Group
public interface Represent extends Platform, Serializable
{
	public static final int STATUSxONLINE = 100;
	public static final int STATUSxOFFLINE = 101;
	public static final int STATUSxBUSY = 102;
	
	
	// Kiểm tra đối tượng
	public static boolean Verification(Object object) { return true; }
	
	public boolean Checked(Object object);
	
	
}
