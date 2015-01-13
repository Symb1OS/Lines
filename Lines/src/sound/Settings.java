package sound;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Settings implements Runnable{

	public static final String PATH = "resources/sound/";

	public static final String TRACK = "Move.wav";
	
	public static void main(String[] args) {
		Thread bang = new Thread(new Settings());
		bang.start();
	}

	@Override
	public void run() {
		try {
			Player sound = new Player(new FileInputStream(getClass()
					.getClassLoader().getResource(PATH + TRACK).getPath()));
			sound.play();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
		
	}

}