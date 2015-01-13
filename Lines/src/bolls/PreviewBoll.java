package bolls;

import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PreviewBoll extends JButton {

	private static final long serialVersionUID = -2817919916714705516L;
	public static final int DEFAULT_BOLL = 0;
	public static final int BOLL_WIDTH = 20;
	public static final int BOLL_HEIGHT = 20;
	public static final String PATH = "resources/big-bolls/";
	public static final String NULL_BOLL = PATH + "default.PNG";
	public static final String RED_BOLL = PATH + "red.PNG";
	public static final String GREEN_BOLL = PATH + "green.PNG";
	public static final String CYAN_BOLL = PATH + "cyan.PNG";
	public static final String PINK_BOLL = PATH + "pink.PNG";
	
	public PreviewBoll(){
		setSize(BOLL_WIDTH, BOLL_HEIGHT);
		Random random = new Random();
		this.color = random.nextInt(4) + 1;
		setColor(this.color);
	}
	
	ImageIcon image;

	int color;
	
	public void setColor(int color) {
		this.color = color;
		switch (color) {

		case 0:
			image = new ImageIcon(NULL_BOLL);
			break;

		case 1:
			image = new ImageIcon(RED_BOLL);
			break;

		case 2:
			image = new ImageIcon(GREEN_BOLL);
			break;

		case 3:
			image = new ImageIcon(CYAN_BOLL);
			break;

		case 4:
			image = new ImageIcon(PINK_BOLL);
			break;

		default:
			System.out.println("Не туда милок идешь");
			break;
		}
		
		setIcon(image);
	}
	
	public int getColor() {
		return color;
	}

	@Override
	public String toString() {
		return "PreviewBoll [image=" + image + ", color=" + color + "]";
	}	
}