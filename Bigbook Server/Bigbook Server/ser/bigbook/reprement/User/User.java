package bigbook.reprement.User;

import java.io.Serializable;
import java.sql.Date;

import bigbook.reprement.Account;
import javafx.scene.image.Image;

public class User extends Account implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Image profit;
	private String firstname;
	private String midname;
	private String lastname;
	private Date dateofbirth;
	private int sexs;
	private String phonenumber;
	private String email;
	private String address;
	private int status;
	
	public User(Image profit, String username, String password, String firstname, String midname, String lastname,
	        int sexs, String email)
	{
		super(username, password);
		this.firstname = firstname;
		this.midname = midname;
		this.lastname = lastname;
		this.sexs = sexs;
		this.email = email;
	}
	
	public String getName() {return firstname + " " + midname + " " + lastname;}
	
	public String getFirstname() { return firstname; }
	
	
	public void setFirstname(String firstname) { this.firstname = firstname; }
	
	public String getMidname() { return midname; }
	
	public void setMidname(String midname) { this.midname = midname; }
	
	public String getLastname() { return lastname; }
	
	public void setLastname(String lastname) { this.lastname = lastname; }
	
	public Date getDateofbirth() { return dateofbirth; }
	
	public void setDateofbirth(Date dateofbirth) { this.dateofbirth = dateofbirth; }
	
	public int getSexs() { return sexs; }
	
	public void setSexs(int sexs) { this.sexs = sexs; }
	
	public String getPhonenumber() { return phonenumber; }
	
	public void setPhonenumber(String phonenumber) { this.phonenumber = phonenumber; }
	
	public String getEmail() { return email; }
	
	public void setEmail(String email) { this.email = email; }
	
	public String getAddress() { return address; }
	
	public void setAddress(String address) { this.address = address; }

	public int getStatus() { return status; }

	public void setStatus(int status) { this.status = status; }

	public Image getProfit( ) { return profit; }

	public void setProfit( Image profit) { this.profit = profit; }	
}
