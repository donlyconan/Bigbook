package server.api.search;

import javax.swing.JFrame;

public class BasicSearch extends JFrame{
	private static final long serialVersionUID = 1L;

	public BasicSearch() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Search");
		setSize(800, 500);
	}
	
	public static void main(String[] args) {
		new BasicSearch().setVisible(true);
	}

}
