package IO.Audio;

import java.util.logging.Level;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import notification.print.Print;

public class AudioOutputStream extends Thread {
	private TargetDataLine target;
	private AudioFormat format;

	public AudioOutputStream() throws LineUnavailableException {
		format = getAudioFormat();
	}

	public void open() throws LineUnavailableException {
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		if (AudioSystem.isLineSupported(info)) {
			target = (TargetDataLine) AudioSystem.getLine(info);
			target.open();
			target.start();
		} else {
			Print.out(Level.WARNING, "System not support!");
		}
	}
	
	public void read(byte[] data) {
		target.read(data, 0, data.length);
	}

	public void Close() {
		target.flush();
		target.stop();
		target.close();
	}

	public static AudioFormat getAudioFormat() {
		AudioFormat audio = new AudioFormat(Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
		return audio;
	}

	public TargetDataLine getTarget() {
		return target;
	}

	public void setTarget(TargetDataLine target) {
		this.target = target;
	}

	public AudioFormat getFomat() {
		return format;
	}

	public void setFomat(AudioFormat fomat) {
		this.format = fomat;
	}
}
