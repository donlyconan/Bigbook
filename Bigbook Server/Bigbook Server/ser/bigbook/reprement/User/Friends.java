package bigbook.reprement.User;

import java.io.Serializable;
import java.util.Hashtable;

import bigbook.Platform.Platform;

public class Friends implements Platform, Serializable
{
	private static final long serialVersionUID = 1L;
	private String iduser;
	private Hashtable<String,User> friend;
	
	
	public Friends(String iduser, Hashtable<String,User> friend)
	{
		this.iduser = iduser;
		this.friend = friend;
	}
	
	public String getIduser() { return iduser; }
	
	public void setIduser(String iduser) { this.iduser = iduser; }
	
	public Hashtable<String, User> getFriend( ) { return friend; }

	public void setFriend( Hashtable<String, User> friend) { this.friend = friend; }
}
