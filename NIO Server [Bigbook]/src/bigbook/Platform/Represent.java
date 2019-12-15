package bigbook.Platform;

import java.io.Serializable;

//Cha của các đối User, Group
public interface Represent extends Platform, Serializable
{
	public static boolean Verification(Object object) { return true; }
	
	public boolean Checked(Object object);
}
