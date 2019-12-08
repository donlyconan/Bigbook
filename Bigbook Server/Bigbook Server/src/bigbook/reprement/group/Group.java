package bigbook.reprement.group;

import java.util.List;

import bigbook.Platform.Represent;

public class Group implements Represent {
	private static final long serialVersionUID = 1L;
	private String idgroup;
	private String idadmin;
	private List<String> members;

	public Group(String idgroup, String idadmin, List<String> members) {
		super();
		this.idgroup = idgroup;
		this.idadmin = idadmin;
		this.members = members;
	}

	@Override
	public boolean Checked(Object object) {
		return false;
	}

	public String getIdgroup() {
		return idgroup;
	}

	public void setIdgroup(String idgroup) {
		this.idgroup = idgroup;
	}

	public String getIdadmin() {
		return idadmin;
	}

	public void setIdadmin(String idadmin) {
		this.idadmin = idadmin;
	}

	public List<String> getMembers() {
		return members;
	}

	public void setMembers(List<String> members) {
		this.members = members;
	}

}
