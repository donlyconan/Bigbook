package IO.Audio;

import java.util.logging.Level;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import Tranfer.Server.Donly.UDPRecieveData;
import notification.print.Print;

public class AudioInputStream extends Thread {
	private SourceDataLine source;
	private AudioFormat format;

	public AudioInputStream(UDPRecieveData udpRecieve) throws LineUnavailableException {
		format = AudioOutputStream.getAudioFormat();
	}

	public void open() throws LineUnavailableException {
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		if (AudioSystem.isLineSupported(info)) {
			source = (SourceDataLine) AudioSystem.getLine(info);
			source.open();
			source.start();
		} else {
			Print.out(Level.WARNING, "System not support audio!");
		}
	}

	public void write(byte[] data) {
		source.write(data, 0, data.length);
	}

	public void Close() {
		source.flush();
		source.stop();
		source.close();
	}

	public SourceDataLine getSource() {
		return source;
	}

	public void setSource(SourceDataLine source) {
		this.source = source;
	}

	public AudioFormat getFormat() {
		return format;
	}

	public void setFormat(AudioFormat format) {
		this.format = format;
	}

}
