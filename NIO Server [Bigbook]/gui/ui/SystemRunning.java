package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

public class SystemRunning extends JFrame {
	private static final long serialVersionUID = 1L;
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenu menuManager;
	private JMenu menuAbout;
	private JToolBar toolBar;
	private JButton btStatus;
	private JButton btUser;
	private Panel panel;
	private JLabel lbStatus;
	JTextArea textArea;

	public SystemRunning() {
		initialize();
	}
	
	private void initialize() {
		setBounds(100, 100, 782, 479);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuFile = new JMenu("Files");
		menuBar.add(menuFile);
		
		menuManager = new JMenu("Manager");
		menuBar.add(menuManager);
		
		JMenu menuSettings = new JMenu("Settings");
		menuBar.add(menuSettings);
		
		menuAbout = new JMenu("About");
		menuBar.add(menuAbout);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		btStatus = new JButton("Status");
		toolBar.add(btStatus);
		
		btUser = new JButton("Users Online");
		toolBar.add(btUser);
		
		textArea = new JTextArea();
		textArea.setRows(200);
		textArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(textArea);
		getContentPane().add(scroll, BorderLayout.CENTER);
		
		panel = new Panel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		
		lbStatus = new JLabel("Ready");
		panel.add(lbStatus);
		
		JMenuBar menubar = new JMenuBar();
		textArea.add(menubar);
		menubar.add(new JMenu("123"));
		
	}
	
	
	public static SystemRunning createSysteamRunning()
	{
		SystemRunning system = new SystemRunning();
		Thread thread = new Thread(()-> {
			system.setVisible(true);
		});
		thread.start();
		Print.setTable(system.textArea);
		return system;
	}

}
