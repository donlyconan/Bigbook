package bigbook.reprement.User;

import bigbook.Platform.Represent;
import javafx.collections.ObservableMap;

public class Friends implements Represent
{
	private static final long serialVersionUID = 1L;
	
	private String iduser;
	private ObservableMap<String,User> friend;
	
	/**
	 * @param iduser
	 * @param friend
	 */
	public Friends(String iduser, ObservableMap<String,User> friend)
	{
		this.iduser = iduser;
		this.friend = friend;
	}
	
	public String getIduser() { return iduser; }
	
	public void setIduser(String iduser) { this.iduser = iduser; }
	
	public ObservableMap<String,User> getFriend() { return friend; }
	
	public void setFriend(ObservableMap<String,User> friend) { this.friend = friend; }

	@Override
	public boolean Checked(Object object) { // TODO Auto-generated method stub
	return false; }
	
}
