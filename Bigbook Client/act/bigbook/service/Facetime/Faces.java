package bigbook.service.Facetime;

import java.awt.Dimension;

import javax.swing.JPanel;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

public class Faces extends JPanel
{
	private static final long serialVersionUID = 1L;
	private Webcam webcam;
	private WebcamPanel myself;
	private WebcamPanel opposite;
	
	public Faces(Dimension sizemyface, Dimension sizeopposite)
	{
		webcam = Webcam.getDefault();
		webcam.setViewSize(sizemyface);
		myself = new WebcamPanel(webcam);
		opposite = new WebcamPanel(webcam);
	}
	
	public void close() { webcam.close(); };
	
	public Webcam getWebcam() { return webcam; }
	
	public void setWebcam(Webcam webcam) { this.webcam = webcam; }

	public WebcamPanel getOpposite() { return opposite; }

	public void setOpposite(WebcamPanel opposite) { this.opposite = opposite; }

	public WebcamPanel getMyself() { return myself; }

	public void setMyself(WebcamPanel myself) { this.myself = myself; }
	
	
	
	
	
}
