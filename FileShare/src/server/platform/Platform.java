package server.platform;

import java.util.Hashtable;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public interface Platform {
	public static final Hashtable<Type, Object> Data = new Hashtable<Type, Object>();
	
	public static enum Size {
		X15x15, X20x20, X25x25,X30x30,X40x40,X60x60,X80x80,X200x200,X1280x720,X1100x600,X1920x1080
	}

	public static enum Type {
		FXMain_Activity, FXLogin, FXMyShare, FXMyFiles, FXController, FXComputer,FXTrash,FXSettings,
		IProgress, IImage,ITextProcess, ITextPath,IMCTextPath,
		FFTPfile, StackPane, FTPClient,
		HOME, COMPUTER, SHARE_WITH_ME, TRASH, SETTINGS
	}
	

	static enum Login {
		LOGIN, LOGOUT
	}
	
	public static enum FileType {
		EXE, JAR,CPP, CLASS, H, ICO, PNG, JPG, ZIP, RAR, FILE, JPGE, TXT, DLL
	}

	static enum Event {
		UPLOAD, UPLOAD_FILE, UPLOAD_FOLDER, DOWNLOAD, NEW_FOLDER, DELETE, SEARCH, REFRESH,
		SYNC, RENAME_FILE, RENAME_FOLDER, EDIT_FILE, MOVE_FILE,RENAME___,SHARE, RENAME, COPY_PATH
	}
	
	public static void stackAddChilden(Node node) {
		StackPane stack = (StackPane) Data.get(Type.StackPane);
		stack.getChildren().add(node);
	}

	public static void show(Type type) {
		StackPane stack = (StackPane) Data.get(Type.StackPane);
		int index = stack.getChildren().indexOf(Data.get(type));
		System.out.println(Data.get(type));
		if (index > -1)
			stack.getChildren().get(index).toFront();
	}
	
	public static Thread start(Runnable run) {
		Thread core = new Thread(run);
		core.start();
		return core;
	}



}
