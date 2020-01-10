package IO.Audio;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class AudioInputStream extends Thread {
	private SourceDataLine source;
	private AudioFormat format;

	private AudioInputStream() {
		format = AudioOutputStream.getAudioFormat();
	}

	public SourceDataLine open() throws LineUnavailableException {
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		if (AudioSystem.isLineSupported(info)) {
			source = (SourceDataLine) AudioSystem.getLine(info);
			source.open();
			source.start();
		} else {
			Logger.getAnonymousLogger().log(Level.WARNING,"System not support audio!");
		}
		return source;
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
