package sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class TheMusicPlayer {

	public static final String PATH = "resources/sound/";

	public static final String TRACK = "Move.wav";

	public static void main(String[] args) {

		try {
			
			Clip clip;
			File soundFile = new File(PATH + TRACK);
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(soundFile);
			clip = AudioSystem.getClip();

			clip.open(audioInputStream);
			clip.start();// This plays the audio
	
		
		
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}
}