package server.api.search;

import java.io.File;
import java.io.FileFilter;

public class MFFilter implements FileFilter {

	public static enum Mode {
		QUICK, SMART, ALL, HAFT_OF_ALL
	}

	private static final double SAFE = 2.5;

	private String pathname;
	private Mode mode;

	public MFFilter(String pathname) {
		super();
		this.pathname = pathname;
		mode = Mode.QUICK;
	}	

	@Override
	public boolean accept(File e) {
		if (e.isFile()) {
			if (e.getName().equalsIgnoreCase(pathname))
				return true;
			if (e.getName().startsWith(pathname) && (double) e.getName().length() / pathname.length() < SAFE)
				return true;
			return e.getName().endsWith(pathname);
		} else
			return true;
	}

	public String getPathname() {
		return pathname;
	}

	public void setPathname(String pathname) {
		this.pathname = pathname;
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

}
