package lines;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SettingsFile {
	
	public static final String PATH = "resources/";
	
	public static final String NAME = "config.txt";
	
	public static void write(Settings settings) {

		try {

			FileOutputStream fos = new FileOutputStream(PATH + NAME);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(settings);
			oos.flush();
			oos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static Settings read() {

		try {

			FileInputStream fis = new FileInputStream(PATH + NAME);
			ObjectInputStream oin = new ObjectInputStream(fis);
			Settings settings = (Settings) oin.readObject();

			return settings;

		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
