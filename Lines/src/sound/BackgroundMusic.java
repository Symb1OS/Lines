package sound;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class BackgroundMusic implements Runnable {

	public static final String PATH = "resources/sound/";

	public static final String TRACK = "WarCraft2.mp3";

	public static void main(String[] args) {
		Thread audio = new Thread(new BackgroundMusic());
		audio.start();
	}

	@Override
	public void run() {

		try {
			Player sound = new Player(new FileInputStream(getClass().getClassLoader().getResource(PATH + TRACK).getPath()));
			sound.play();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}

	}
}