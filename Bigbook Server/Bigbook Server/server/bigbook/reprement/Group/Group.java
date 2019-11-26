package bigbook.reprement.Group;

import bigbook.Platform.Represent;
import javafx.collections.ObservableList;

public class Group implements Represent
{
	private static final long serialVersionUID = 1L;
	private String idgroup;
	private String idadmin;
	private ObservableList<String> idmembers;
	private boolean form;
	@Override
	
	
	public boolean Checked(Object object) {
		return true;
	}
	
	
	
}
