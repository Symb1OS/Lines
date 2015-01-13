package lines;

import java.io.Serializable;

public class Settings  implements Serializable{
	
	public static final String PATH = "resources/";
	
	public static final String NAME = "config.txt";
	
	private static final long serialVersionUID = 286028521304124988L;

	private boolean music;
	
	private boolean sound;
	
	private int delay;
	
	public boolean isMusic() {
		return music;
	}

	public void setMusic(boolean music) {
		this.music = music;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public boolean isSound() {
		return sound;
	}

	public void setSound(boolean sound) {
		this.sound = sound;
	}
	
	@Override
	public String toString() {
		return "Settings [music=" + music + ", sound=" + sound + ", delay="
				+ delay + "]";
	}
}