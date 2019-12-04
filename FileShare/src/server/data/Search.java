package server.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import load.LoadResource;

public class Search {
	public static final List<String> PREFERED_LIST = new ArrayList<String>();
	private FileFilter filter;
	private File root;
	private List<String> res;
	private long time;
	private int sl;

	public Search(FileFilter filter, File root) {
		super();
		this.filter = filter;
		this.root = root;
		res = new ArrayList<String>();
		sl = 1;
	}

	@SuppressWarnings("deprecation")
	public void search() {
		time = 0;
		long stime = System.currentTimeMillis();
		for (String item : PREFERED_LIST)
		{
			if (find(new File(item), 0)) {
				return;
			}
		}
		File files[] = root.listFiles(filter);
		for (File item : files) {
			if (find(item, res.size()))
				return;
		}
		time = System.currentTimeMillis()-stime;
		System.out.println(res.get(0));
		File file = new File(res.get(0));
		JFileChooser fc = new JFileChooser(file);
		fc.setFileFilter(new javax.swing.filechooser.FileFilter() {
			@Override
			public String getDescription() {
				return null;
			}
			@Override
			public boolean accept(File f) {
				return f.getName().equalsIgnoreCase(file.getName());
			}
		});
		fc.showOpenDialog(new JFrame("Time: "+time+"s"));
	}


	public boolean find(File file, int d) {
		if(d == sl)
			return true;
		else
		{
			if (file.isFile()) {
				res.add(file.getAbsolutePath());
			} else if (d < sl) {
				File files[] = file.listFiles(filter);
				if (files != null)
				{
					for (File item : files) {
						if (item.isFile()) {
							res.add(item.getAbsolutePath());
							d++;
						} else {
							find(item, d);
						}
					System.out.println(item);
					}
				}
			}
			return res.size() == sl ? true : false;
		}
	}
	
	static
	{
		try {
			InputStreamReader isr = new InputStreamReader(LoadResource.getInputStream("txt\\Search.inp"));
			BufferedReader br = new BufferedReader(isr);
			
			String line = null;
			while((line = br.readLine()) != null)
			{
				PREFERED_LIST.add(line);
			}
			isr.close();
			br.close();
			PREFERED_LIST.sort(new Comparator<String>() {

				@Override
				public int compare(String o1, String o2) {
					return o1.contains(o2) ? -1 : 1;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	public void fillResut(TreeItem<FileItem> tree, int i, int j) {
//
//
//	}
//
//	public static List<String> getItem(String pathname) {
//		List<String> lis = new ArrayList<String>();
//		String paths[] = pathname.split("\\\\");
//		for(String path : paths)
//		{
//			if(path != "")
//			{
//				if(lis.size() == 0)
//					lis.add(path);
//				else
//					lis.add(lis.get(lis.size()-1) + "\\" + path); 
//			}
//		}
//		return lis;
//	}

	public void setFilter(FileFilter filter) {
		this.filter = filter;
	}

	public File getRoot() {
		return root;
	}

	public void setRoot(File root) {
		this.root = root;
	}

	public List<String> getRes() {
		return res;
	}

	public void setRes(List<String> res) {
		this.res = res;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	
}
