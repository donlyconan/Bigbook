package bigbook.reprement.user;

import java.io.Serializable;
import java.util.Hashtable;

import bigbook.Platform.Platform;

public class Friends implements Platform, Serializable
{
	private static final long serialVersionUID = 1L;
	private String iduser;
	private Hashtable<Object,String> friends;
	
	public Friends(String iduser, Hashtable<Object, String> friends) {
		super();
		this.iduser = iduser;
		this.friends = friends;
	}

	public String getIduser() {
		return iduser;
	}

	public void setIduser(String iduser) {
		this.iduser = iduser;
	}

	public Hashtable<Object, String> getFriends() {
		return friends;
	}

	public void setFriends(Hashtable<Object, String> friends) {
		this.friends = friends;
	}
}
