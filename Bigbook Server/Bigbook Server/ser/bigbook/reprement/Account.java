package bigbook.reprement;

import bigbook.Platform.Platform;

public abstract class Account implements Platform
{
	private String username;
	private String password;

	public Account( String username, String password )
	{
		this.username	= username;
		this.password	= password;
	}

	public static boolean Checked( String username, String password)
	{ return true; }

	public String getUsername( )
	{ return username; }

	public void setUsername( String username)
	{ this.username = username; }

	public String getPassword( )
	{ return password; }

	public void setPassword( String password)
	{ this.password = password; }
}
