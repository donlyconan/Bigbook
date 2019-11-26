package gui.control;

import java.io.File;

import javafx.scene.image.ImageView;

public class TreeCell extends javafx.scene.control.TreeCell<File>
{
	private ImageView imgfd;
	private ImageView imgif;
	
	public TreeCell( ImageView imgfd, ImageView imgif )
	{
		super();
		this.imgfd	= imgfd;
		this.imgif	= imgif;
	}

	@Override
	protected void updateItem( File item, boolean empty) { 
		super.updateItem(item, empty); 
		if(!item.isFile())
		{
			
		}
	}

	public ImageView getImgfd( ) { return imgfd; }

	public void setImgfd( ImageView imgfd) { this.imgfd = imgfd; }

	public ImageView getImgif( ) { return imgif; }

	public void setImgif( ImageView imgif) { this.imgif = imgif; }
	
	
}
