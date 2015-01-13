package lines;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import sound.BackgroundMusic;
import sound.ExplosionBoll;
import bolls.Boll;
import bolls.CyanBoll;
import bolls.GreenBoll;
import bolls.PinkBoll;
import bolls.PreviewBoll;
import bolls.RedBoll;

public class Lines extends JFrame  implements ActionListener{

	private static final long serialVersionUID = -5691603408727068871L;
	public static final int BORDER_WIDTH = 880;
	public static final int BORDER_HEIGHT = 760;
	public static final int COLLECT = 5;
	public static final int MAX_ROWS = 10;
	public static final int MAX_COLUMNS = 10;
	public static final int START_COL_BOLLS = 10;
	public static final int NULL_BOLL = 0;
	public static final int STEP = 0;
	
	private PreviewBoll firstPreviewButton;
	private PreviewBoll secondPreviewButton;
	private PreviewBoll thirdPreviewButton;
	
	private JLabel scoreLabel = new JLabel();
	private JPanel gamePanel;
	
	private static Boll[][] field;
	
	private static Boll selectedBollButton;
	private static Boll moveButton;
	
	private int selectedColor;
	
	private List<Point> collectMove = new ArrayList<Point>();
	private List<Coordinates> collectDestroy = new ArrayList<Coordinates>();
	
	private boolean bollSelected;
	private boolean selectedSmall;
	
	private int score;
	
	private Timer timerMove    = new Timer(25,this);
	private Timer timerBoll    = new Timer(40, new BollUpDown());
	private Timer timerDestroy = new Timer(50, new BollDestroyed());
	
	private boolean animation;
	private boolean needDestroy;
	private boolean sound;
	
	private boolean endGame;
	
	private int saveSmallColor;
	private int selectedColumn;
	private int selectedRow;
	
	public int getScore() {
		return score;
	}
	
	public void setScore() {
		this.score += 10;
	}
	
	private void defaultSettings(){
		
		Settings settings = new Settings();
		
		settings.setDelay(25);
		settings.setMusic(true);
		settings.setSound(true);
		
		SettingsFile.write(settings);
		
		timerMove.setDelay(settings.getDelay());

		Thread audio = new Thread(new BackgroundMusic());
		audio.start();
		
		sound = true;
		
	}
	
	private void initSettings() {
		
		Settings settings = SettingsFile.read();

		if (settings != null) {

			timerMove.setDelay(settings.getDelay());

			if (settings.isMusic()) {
				Thread audio = new Thread(new BackgroundMusic());
				audio.start();
			}

			if (settings.isSound()) {
				sound = true;

			}
		} else {

			defaultSettings();

		}

	}
	
	public Lines(){
		
		super("Lines");
		setBounds(300,50,400,400);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		initSettings();
		
		gamePanel = new JPanel();
		gamePanel.setBackground(Color.LIGHT_GRAY);
		gamePanel.setLayout(new FlowLayout());
		
		field = new Boll[MAX_ROWS][MAX_COLUMNS];
		score = 0;
		bollSelected = false;
		
		initStartField();
			
		for (int row = 0; row < MAX_ROWS; row++) {
			for (int column = 0; column < MAX_COLUMNS; column++) {
				final Boll currentButton = field[row][column];
				currentButton.setBackground(Color.LIGHT_GRAY);;
				ActionListener actionPerformed = this;
				currentButton.addActionListener(actionPerformed);
				gamePanel.add(currentButton);
			}
		}
		
		add(gamePanel);
		
		JPanel informationPanel = new JPanel();
		informationPanel.setBackground(Color.BLACK);
		add(informationPanel, BorderLayout.NORTH);
		informationPanel.setLayout(new GridLayout(0, 5, 0, 0));
		
		TimerLabel timerLabel = new TimerLabel(new java.util.Timer());
		timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timerLabel.setForeground(Color.RED);
		timerLabel.setFont(new Font("Times New Roman", Font.BOLD, 60));
		informationPanel.add(timerLabel);
		
		firstPreviewButton = new PreviewBoll();
		firstPreviewButton.setBackground(Color.BLACK);
		informationPanel.add(firstPreviewButton);
		
		secondPreviewButton =new PreviewBoll();
		secondPreviewButton.setBackground(Color.BLACK);
		informationPanel.add(secondPreviewButton);
		
		thirdPreviewButton = new PreviewBoll();
		thirdPreviewButton.setBackground(Color.BLACK);
		informationPanel.add(thirdPreviewButton);
		
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scoreLabel.setForeground(Color.RED);
		scoreLabel.setFont(new Font("Tahoma", Font.BOLD, 60));
		scoreLabel.setBackground(Color.ORANGE);
		informationPanel.add(scoreLabel);
			
		ScoreView statusScore  = new ScoreView();
		statusScore.start();
		
		showPreviewBolls();
		
		setSize(BORDER_WIDTH, BORDER_HEIGHT);
		setResizable(false);
		setVisible(true);
		
	}
	
	//TODO
	private void illsuionMove() {
		
		if (!collectMove.isEmpty()) {
			
			Point newCoordinateCurrentBoll  = collectMove.get(STEP);
			int row = newCoordinateCurrentBoll.getY();
			int column = newCoordinateCurrentBoll.getX();
			
			field[selectedRow][selectedColumn].setColor(NULL_BOLL);
			field[selectedBollButton.getRow()][selectedBollButton.getColumn()].setColor(NULL_BOLL);
			
			moveButton.setPosition(row, column);
			move(selectedBollButton, moveButton);
			
			selectedBollButton = moveButton;
			collectMove.remove(STEP);
			repaint();
			
		}else{
			
			animation = false;
			bollSelected = false;
			
			smallToBig();
		    check();
			addInFielThirdPreviewBolls();
			check();
			showPreviewBolls();
			repaint();
			timerMove.stop();
		}
		
	}
	
	private void move(Boll previous, Boll current){
		
		int currentColor = selectedColor;
		int previousColor = previous.getColor();
		
		field[current.getRow()][current.getColumn()].setColor(previousColor);
		field[previous.getRow()][previous.getColumn()].setColor(currentColor);
		repaint();
		
	}
	
	private void addNullBalls() {

		for (int row = 0; row < MAX_ROWS; row++) {
			for (int column = 0; column < MAX_COLUMNS; column++) {
				field[row][column] = new Boll(row, column, NULL_BOLL);
			}
		}
	}
	
	private void newRandomBall(){
		
		int randomRow;
		int randomColumn;
		int color;
		
		boolean add = false;
		while (!add){
			
			Random random = new Random();
			randomRow = random.nextInt(MAX_ROWS);
			randomColumn = random.nextInt(MAX_COLUMNS);
			color = random.nextInt(4) + 1;
			
			boolean free = field[randomRow][randomColumn].getColor() == NULL_BOLL;
			if (free){
				field[randomRow][randomColumn].setColor(color);
				field[randomRow][randomColumn].setSmallBoll(false);
				repaint();
				add = true;
				break;
			}
			
			add = checkEndGame();
		}
		
	}
	
	private void showPreviewBolls(){
		Random random = new Random();
		int color = random.nextInt(4) + 1;
		firstPreviewButton.setColor(color);
		
		color = random.nextInt(4) + 1;
		secondPreviewButton.setColor(color);
		
		color = random.nextInt(4) + 1;
		thirdPreviewButton.setColor(color);
	}

	private void addInFielThirdPreviewBolls(){
		
		newPreviewBoll(firstPreviewButton.getColor());
		newPreviewBoll(secondPreviewButton.getColor());
		newPreviewBoll(thirdPreviewButton.getColor());
	}
	
	private void smallToBig(){
		
		int color;
		for (int row = 0; row < MAX_ROWS; row++) {
			for (int column = 0; column < MAX_COLUMNS; column++) {
				
				color = field[row][column].getColor();
				switch (color) {
				case Boll.SMALL_RED:
					field[row][column].setColor(Boll.BIG_RED);
					field[row][column].setSmallBoll(false);
					break;
					
				case Boll.SMALL_GREEN:
					field[row][column].setColor(Boll.BIG_GREEN);
					field[row][column].setSmallBoll(false);
					break;
					
				case Boll.SMALL_CYAN:
					field[row][column].setColor(Boll.BIG_CYAN);
					field[row][column].setSmallBoll(false);
					break;
					
				case Boll.SMALL_PINK:
					field[row][column].setColor(Boll.BIG_PINK);
					field[row][column].setSmallBoll(false);
					break;

				default:
					break;
				}
			}
			
		}
	}
	
	private void newPreviewBoll(int color){
		
		int randomRow;
		int randomColumn;
		
		int newColor = 0;
		switch (color) {
		
		case Boll.BIG_RED:
			newColor = Boll.SMALL_RED;
			break;

		case Boll.BIG_GREEN:
			newColor = Boll.SMALL_GREEN;
			break;

		case Boll.BIG_CYAN:
			newColor = Boll.SMALL_CYAN;
			break;

		case Boll.BIG_PINK:
			newColor = Boll.SMALL_PINK;
			break;

		default:
			break;
		}
		
		boolean add = false;
		while (!add){
			
			Random random = new Random();
			randomRow = random.nextInt(MAX_ROWS);
			randomColumn = random.nextInt(MAX_COLUMNS);
			
			boolean free = field[randomRow][randomColumn].getColor() == NULL_BOLL;
			if (free){
				field[randomRow][randomColumn].setColor(newColor);
				field[randomRow][randomColumn].setSmallBoll(true);
				repaint();
				add = true;
				break;
			}
			
			add = checkEndGame();
		}
	}
	
	private boolean checkEndGame() {
		
		for (int row = 0; row < MAX_ROWS; row++) {
			for (int column = 0; column < MAX_COLUMNS; column++) {
				if (field[row][column].getColor() == NULL_BOLL)
					return false;
			}
		}
		
		if (!endGame){
			EndGame gameOver = new EndGame(getScore());
			dispose();
			endGame = true;
		}
		
		return true;
	}

	private void soundBangBoll(){
		
		if (sound){

			Thread bangBoll = new Thread(new ExplosionBoll());
			bangBoll.start();
		}
		
	}
	
	private void deleteFiveRow(int cntRow, int cntColumn){
		
		int deleteBalls = cntRow;
		while (deleteBalls < cntRow + COLLECT){
			
			collectDestroy.add(new Coordinates(deleteBalls, cntColumn));
			deleteBalls++;
		}
		
		needDestroy = true;
		setScore();
		repaint();
	}
	
	private void deleteFiveColumn(int cntRow, int cntColumn){
		
		int deleteBalls = cntColumn;
		while (deleteBalls < cntColumn + COLLECT){
			
			collectDestroy.add(new Coordinates(cntRow, deleteBalls));
			deleteBalls++;
		}
		
		needDestroy = true;
		setScore();
		repaint();
	}
	
	private void checkRow(int row,int column){
		boolean isRow;
		boolean isBoll;
		
		isRow = row <= MAX_ROWS - COLLECT;
		isBoll = field[row][column].getColor() != NULL_BOLL;

		if(isRow && isBoll){
			boolean fiveBallRow = field[row][column].getColor() == field[row + 1][column].getColor() &&
					 field[row][column].getColor() == field[row + 2][column].getColor() &&
							 field[row][column].getColor() == field[row + 3][column].getColor() &&
									 field[row][column].getColor() == field[row + 4][column].getColor();
			
			if (fiveBallRow) {
				deleteFiveRow(row, column);
			}
				
		}
	}
	
	private void checkColumn(int row,int column){
		boolean isColumn;
		boolean isBoll;
		
		isColumn =  column <= MAX_COLUMNS - COLLECT;
		isBoll = field[row][column].getColor() != NULL_BOLL;
		if (isColumn && isBoll){
			boolean fiveBallColumn = field[row][column].getColor() == field[row][column + 1].getColor()
					&& field[row][column].getColor() == field[row][column + 2].getColor()
					&& field[row][column].getColor() == field[row][column + 3].getColor()
					&& field[row][column].getColor() == field[row][column + 4].getColor();
			
			if (fiveBallColumn) {
				deleteFiveColumn(row, column);
			}
		}
	}
	
	private void checkUpLeftDiagonal(int row, int column){
		boolean upLeft;
		boolean isBoll;
		
		upLeft =  ( (column >= COLLECT) &&  (row <= MAX_ROWS - COLLECT) );
		isBoll = field[row][column].getColor() != NULL_BOLL;
		if (upLeft && isBoll){
			boolean fiveBallUpLeftDiagonal = field[row][column].getColor() == field[row + 1][column - 1].getColor()
					&& field[row][column].getColor() == field[row + 2][column - 2].getColor()
					&& field[row][column].getColor() == field[row + 3][column - 3].getColor()
					&& field[row][column].getColor() == field[row + 4][column - 4].getColor();
			
			if (fiveBallUpLeftDiagonal){
				delefeFiveUpLeftDiagonal(row, column);
			}
			
		}
	}
	
	private void checkUpRightDiagonal(int row, int column){
		boolean upRight; 
		boolean isBoll;
		
		upRight = ( (column <= MAX_COLUMNS - COLLECT) && (row <= MAX_ROWS - COLLECT) );
		isBoll = field[row][column].getColor() != NULL_BOLL;
		if(upRight && isBoll){
			boolean fiveBollUpRightDiagonal = field[row][column].getColor() == field[row + 1][column + 1].getColor()
					&& field[row][column].getColor() == field[row + 2][column + 2].getColor()
					&& field[row][column].getColor() == field[row + 3][column + 3].getColor()
					&& field[row][column].getColor() == field[row + 4][column + 4].getColor();
			if (fiveBollUpRightDiagonal){
				delefeFiveUpRightDiagonal(row, column);
			}
			
		}
	}
	
	private void delefeFiveUpLeftDiagonal(int row, int column) {
		
		int count = 0;
		while (count < COLLECT){
			
			collectDestroy.add(new Coordinates(row, column));
			row++;
			column--;
			count++;
		}
		
		needDestroy = true;
		setScore();
		repaint();
		
	}

	private void delefeFiveUpRightDiagonal(int row, int column) {
		
		int count = 0;
		while (count < COLLECT){
			
			collectDestroy.add(new Coordinates(row, column));
			row++;
			column++;
			count++;
		}
		
		needDestroy = true;
		setScore();
		repaint();
		
	}

	private void check() {
		
		for (int row = 0; row < MAX_ROWS; row++) {
			for (int column = 0; column < MAX_COLUMNS; column++) {

				checkRow(row, column);
				checkColumn(row, column);
				checkUpLeftDiagonal(row, column);
				checkUpRightDiagonal(row, column);
			}
		}
		
		if (needDestroy){
			timerDestroy.start();
			soundBangBoll();
		}
		
	}

	@SuppressWarnings("unused")
	private void printField() {
		for (int row = 0; row < field.length; row++) {
			for (int column = 0; column < field.length; column++) {
				System.out.print(field[row][column].getColor() + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	private void initStartField(){
		
		addNullBalls();
		
		for (int startRow = 0; startRow < START_COL_BOLLS; startRow++){
			newRandomBall();
		}
		
	}

	public static void main(String[] args) {

		Lines lines = new Lines();
		
	}
	
	class ScoreView extends Thread {

		private Thread gettime;

		public void start() {
			gettime = new Thread(this);
			gettime.start();
		}

		public void run() {
			while (true) {
				scoreLabel.setText(String.valueOf(getScore()));
				try {
					gettime.sleep(1000);
				} catch (InterruptedException ie) {
					continue;
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (animation)	illsuionMove();
		else {

			if ( e.getSource() instanceof Boll ){
				moveButton = (Boll) e.getSource();
			}

			boolean pressed = (moveButton != null);
			if (pressed) {

				boolean isBoll = ((moveButton.getColor() != NULL_BOLL) && (!moveButton.isSmallBoll()));
				if (isBoll) {
					timerMove.stop();
					timerBoll.start();
					bollSelected = true;
					collectDestroy = new ArrayList<Coordinates>();
					selectedBollButton = moveButton;
					selectedColor = selectedBollButton.getColor();
					System.out.println("selectedColor" + selectedColor);
					System.out.println("это шар " + selectedBollButton.getRow() + " " + selectedBollButton.getColumn());
				}

				if ((bollSelected && !isBoll) || (bollSelected && moveButton.isSmallBoll())) {
					
					timerBoll.stop();
					
					int moveRow = moveButton.getRow();
					int moveColumn = moveButton.getColumn();

					selectedRow = selectedBollButton.getRow();
					selectedColumn = selectedBollButton.getColumn();

					System.out.println("Откуда " + selectedRow + " " + selectedColumn);
					System.out.println("Куда надо переместить " + moveRow + " " + moveColumn);
					
					//TODO
					boolean isSmal = (field[moveRow][moveColumn].isSmallBoll());
					if (isSmal) {

						selectedSmall = true;
						saveSmallColor = field[moveRow][moveColumn].getColor();
						field[moveRow][moveColumn].setColor(NULL_BOLL);
						field[moveRow][moveColumn].setSmallBoll(false);
						
					}
					
					IntField labirint = new IntField(field);
					PathFinder pathFinder = new PathFinder(labirint.getField());
					Point start = new Point(selectedColumn,selectedRow); //Hачальная точка
			        Point end = new Point(moveColumn, moveRow);	
			        
					Point[] path = pathFinder.find(start,end);
					
			        printField();
			        
					boolean isOpenPath = (path != null); 
			        if (isOpenPath) {
						animation = true;
						bollSelected = false;
						collectMove.addAll(Arrays.asList(path));
						timerMove.start();
					}
			        
				}
			}
		}
	}
	
	class BollDestroyed implements ActionListener{
		
		private ImageIcon image;
		
		private int END = 9;
		
		private int count = 0;

		@Override
		public void actionPerformed(ActionEvent e) {

			if (needDestroy) {
				
				switch (selectedColor) {
				
				case Boll.BIG_RED:
					destroyRed();
					break;

				case Boll.BIG_GREEN:
					destroyGreen();
					break;

				case Boll.BIG_CYAN:
					destroyCyan();
					break;

				case Boll.BIG_PINK:
					destroyPink();
					break;

				default:
					break;
				}
			}
	
		}

		private void destroyRed() {

			for (Coordinates cord : collectDestroy) {
				image = new ImageIcon(RedBoll.destroy[count]);
				field[cord.getX()][cord.getY()].setIcon(image);
			}

			endDestroy();
			
		}

		private void destroyGreen() {

			for (Coordinates cord : collectDestroy) {
				image = new ImageIcon(GreenBoll.destroy[count]);
				field[cord.getX()][cord.getY()].setIcon(image);
			}

			endDestroy();

		}

		private void destroyCyan() {
			
			for (Coordinates cord : collectDestroy) {
				image = new ImageIcon(CyanBoll.destroy[count]);
				field[cord.getX()][cord.getY()].setIcon(image);
			}
			
			endDestroy();
			
		}

		private void endDestroy() {
			if (count == END){
				needDestroy = false;
				timerDestroy.stop();
				count = 0;
				for (Coordinates cord : collectDestroy) {
					field[cord.getX()][cord.getY()].setColor(NULL_BOLL);;
				}
			}
			
			count++;
		}

		private void destroyPink() {

			for (Coordinates cord : collectDestroy) {
				image = new ImageIcon(PinkBoll.destroy[count]);
				field[cord.getX()][cord.getY()].setIcon(image);
			}

			endDestroy();
			
		}
		
	}
	
	class BollUpDown implements ActionListener {
		
		public static final String PATH = "resources/animated-bolls/";
		
		private static final int END_UP = 0;
		private static final int END_DOWN = 6;
	
		private ImageIcon image;
		
		private int count = 0;

		private boolean needDown;

		private boolean needUp;

		private boolean startAnimated = true;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (bollSelected){
				switch (selectedColor) {
				
				case Boll.BIG_RED:
					illusionRed();
					break;

				case Boll.BIG_GREEN:
					illusionGreen();
					break;

				case Boll.BIG_CYAN:
					illusionCyan();
					break;

				case Boll.BIG_PINK:
					illusionPink();
					break;

				default:
					System.out.println("АНАЛИТИКА СЛАМАЛАСЬ((((");
					break;
				}
				
			}
		}

		private void illusionPink() {

			image = new ImageIcon(PinkBoll.animation[count]);
			selectedBollButton.setIcon(image);
			
			changeCount();
			
		}

		private void illusionCyan() {
			
			image = new ImageIcon(CyanBoll.animation[count]);
			selectedBollButton.setIcon(image);
			
			changeCount();
			
		}

		private void illusionGreen() {
			
			image = new ImageIcon(GreenBoll.animation[count]);
			selectedBollButton.setIcon(image);
			
			changeCount();
			
		}

		private void illusionRed() {

			image = new ImageIcon(RedBoll.animation[count]);
			selectedBollButton.setIcon(image);

			changeCount();
		}
		
	private void changeCount() {
			
			if (count == END_DOWN){
				startAnimated = false;
				needDown = true;
				needUp = false;
			
			}else if (count == END_UP){
				needDown = false;
				needUp = true;
			}
		
			if (startAnimated){
				count++;
			}
			
			if (needUp){
				count++;
			}
			
			if (needDown){
				count--;
			}
		}

	}
}