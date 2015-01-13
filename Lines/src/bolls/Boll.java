package bolls;

import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Boll extends JButton {

	private static final long serialVersionUID = 415766589474534038L;

	public static final int WIDTH = 20;
	public static final int HEIGHT = 20;
	
	public static final String PATH = "resources/";
	public static final String NULL_BOLL = PATH + "default.PNG";
	public static final String BIG_RED_BOLL = PATH + "red.PNG";
	public static final String BIG_GREEN_BOLL = PATH + "green.PNG";
	public static final String BIG_CYAN_BOLL = PATH + "cyan.PNG";
	public static final String BIg_PINK_BOLL = PATH + "pink.PNG";
	
	public static final String SMALL_BOLLS = "small-bolls/";
	public static final String SMALL_RED_BOLL = PATH + SMALL_BOLLS + "red.PNG";
	public static final String SMALL_GREEN_BOLL = PATH + SMALL_BOLLS + "green.PNG";
	public static final String SMALL_CYAN_BOLL = PATH + SMALL_BOLLS + "cyan.PNG";
	public static final String SMALL_PINK_BOLL = PATH + SMALL_BOLLS + "pink.PNG";
	
	public static final int BIG_RED = 1;
	public static final int BIG_GREEN = 2;
	public static final int BIG_CYAN = 3;
	public static final int BIG_PINK = 4;
	
	public static final int EMPTY_BOLL = 0;
	
	public static final int SMALL_RED = -1;
	public static final int SMALL_GREEN = -2;
	public static final int SMALL_CYAN = -3;
	public static final int SMALL_PINK = -4;
	
	private int row;
	
	private int column;
	
	private ImageIcon image;

	private int color;
	
	private boolean isSmallBoll; 

	public Boll() {
		
		setSize(WIDTH, HEIGHT);
		Random random = new Random();
		this.color = random.nextInt(4) + 1;
		setColor(color);
	}

	public Boll(int color) {
		this.color = color;
		setColor(color);
	}
	
	public Boll(int row, int column) {
		setSize(WIDTH, HEIGHT);
		this.row = row;
		this.column = column;
		Random random = new Random();
		this.color = random.nextInt(4) + 1;
		setColor(this.color);
	}
	
	public Boll(int row, int column, int color) {
		this.row = row;
		this.column = column;
		this.color = color;
		setColor(color);
	}

	public void setColor(int color) {
		this.color = color;
		switch (color) {

		case EMPTY_BOLL:
			image = new ImageIcon(NULL_BOLL);
			break;

		case BIG_RED:
			image = new ImageIcon(BIG_RED_BOLL);
			break;

		case BIG_GREEN:
			image = new ImageIcon(BIG_GREEN_BOLL);
			break;

		case BIG_CYAN:
			image = new ImageIcon(BIG_CYAN_BOLL);
			break;

		case BIG_PINK:
			image = new ImageIcon(BIg_PINK_BOLL);
			break;
		
		case SMALL_RED:
			image = new ImageIcon(SMALL_RED_BOLL);
			break;
			
		case SMALL_GREEN:
			image = new ImageIcon(SMALL_GREEN_BOLL);
			break;
			
		case SMALL_CYAN:
			image = new ImageIcon(SMALL_CYAN_BOLL);
			break;
			
		case SMALL_PINK:
			image = new ImageIcon(SMALL_PINK_BOLL);
			break;

		default:
			System.out.println("Error");
			break;
		}
		
		setIcon(image);
	}
	
	public int getColor() {
		return color;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	public void setPosition(int row, int column){
		this.row = row;
		this.column = column;
	}
	
	@Override
	public String toString() {
		return "Boll [row=" + row + ", column=" + column + ", color=" + color
				+ "]";
	}

	
	public boolean isSmallBoll() {
		return isSmallBoll;
	}

	public void setSmallBoll(boolean isSmallBoll) {
		this.isSmallBoll = isSmallBoll;
	}

	public static void main(String[] args) {

	/*	Boll []  boll = new Boll[10];
		for (Boll boll2 : boll) {
			System.out.println(boll2.toString());
		}*/
	
	}
}