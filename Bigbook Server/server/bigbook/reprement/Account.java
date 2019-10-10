package bigbook.reprement;

import bigbook.Platform.Represent;
import javafx.scene.image.Image;

public class Account implements Represent
{
	private static final long serialVersionUID = 1L;
	private Image profit;
	private String username;
	private String password;
	
	public Account(){}
	
	public Account(Image profit, String username, String password)
	{
		super();
		this.profit = profit;
		this.username = username;
		this.password = password;
	}
	
	
	/**
	 * @param username
	 * @param password
	 */
	public Account(String username, String password)
	{
		this.username = username;
		this.password = password;
	}

	@Override
	public boolean Checked(Object object) { return true; }
	
	public String getUsername() { return username; }
	
	public void setUsername(String username) { this.username = username; }
	
	public String getPassword() { return password; }
	
	public void setPassword(String password) { this.password = password; }
	
	public Image getProfit() { return profit; }
	
	public void setProfit(Image profit) { this.profit = profit; }
	
}
