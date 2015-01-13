package lines;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bolls.Boll;

public class Lab {

	public static final int I_WAS_HERE = 9;
	public static final int FREE_CELL = 1;
	public static final int STEP = 1;
	public static final int START_FIELD = 0;
	public static final int END_FIELD = 9;

	// static int [] [] field = new int[Lines.MAX_ROWS][Lines.MAX_COLUMNS];
	static int[][] field = {
							 { 1, 1, 1, 0, 1, 0, 1, 1, 1, 1 },
							 { 1, 1, 1, 0, 1, 0, 1, 1, 1, 1 }, 
							 { 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 },
							 { 1, 1, 1, 0, 1, 0, 1, 1, 1, 1 },
							 { 1, 1, 1, 0, 1, 1, 1, 0, 1, 1 },
							 { 1, 1, 1, 0, 1, 0, 1, 1, 1, 1 }, 
							 { 1, 1, 0, 0, 0, 0, 0, 0, 1, 1 },
							 { 1, 1, 0, 1, 1, 1, 1, 0, 1, 1 }, 
							 { 1, 1, 0, 1, 0, 1, 1, 0, 1, 1 },
							 { 1, 1, 0, 1, 0, 1, 1, 1, 1, 1 },
							 };
	
	static int[][] startField;

	
	public int[][] getField() {
		return field;
	}

	public static void setField(int[][] field) {
		Lab.field = field;
	}

	private List<Coordinates> playerCords = new ArrayList<Coordinates>();
	private List<Coordinates> forks = new ArrayList<Coordinates>();
	private Set<Coordinates> singleForks = new HashSet<Coordinates>();

	public Set<Coordinates> getSingleForks() {
		return singleForks;
	}

	public void setSingleForks(Set<Coordinates> singleForks) {
		this.singleForks = singleForks;
	}

	private int playerX = 0;
	private int playerY = 0;
	private int lastPlayerX;
	private int lastPlayerY;
	private int forkX;
	private int forkY;
	private int whereX = 0;
	private int whereY = 9;
	private int variableDirection = 0;

	private boolean stuck = false;
	private boolean end = true;
	private boolean fork;
	private boolean finish;
	private boolean south;
	private boolean north;
	private boolean east;
	private boolean west;
	private boolean notMove;
	private boolean possible;

	private boolean southIndexNotBounds;
	private boolean northIndexNotBounds;
	private boolean eastIndexNotBounds;
	private boolean westIndexNotBounds;

	public Lab() {

	}
	
	private void initStartField(){
		startField = field;
	}
	
	public Lab(Boll[][] fieldBolls) {

		field = new int[Lines.MAX_ROWS][Lines.MAX_COLUMNS];

		for (int row = 0; row < Lines.MAX_ROWS; row++) {
			for (int column = 0; column < Lines.MAX_COLUMNS; column++) {
				field[row][column] = fieldBolls[row][column].getColor();
				if (field[row][column] == Lines.NULL_BOLL)
					field[row][column] = FREE_CELL;
				else
					field[row][column] = Lines.NULL_BOLL;
			}
		}
		
		startField = field;

	}

	private void printField(int playerX, int playerY) {
		field[playerX][playerY] = I_WAS_HERE;
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field.length; j++) {
				if (field[i][j] == 2)
					System.out.print("1 ");
				else
					System.out.print(field[i][j] + " ");
			}
			System.out.println();
		}
		field[playerX][playerY] = I_WAS_HERE;
		System.out.println();
	}
	
	public void printField() {
		for (int i = 0; i < Lines.MAX_ROWS; i++) {
			for (int j = 0; j < Lines.MAX_COLUMNS; j++) {
				System.out.print(field[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public List<Coordinates> getPlayerCords() {
		return playerCords;
	}

	public void setPlayerCords(List<Coordinates> playerCords) {
		this.playerCords = playerCords;
	}

	private void initDirection() {

		boolean notMove = (lastPlayerX == playerX && lastPlayerY == playerY);
		if (notMove)
			stuck = true;

		boolean checkBorderSouth = playerX < END_FIELD;
		if (checkBorderSouth) {
			south = field[playerX + 1][playerY] == FREE_CELL;
		}

		boolean checkBorderNorth = playerX > START_FIELD;
		if (checkBorderNorth) {
			north = field[playerX - 1][playerY] == FREE_CELL;
		}

		boolean checkBorderEast = playerY < END_FIELD;
		if (checkBorderEast) {
			System.out.println(playerX + " " + playerY);
			east = field[playerX][playerY + 1] == FREE_CELL;
		}

		boolean checkBorderWest = playerY > START_FIELD;
		if (checkBorderWest) {
			west = field[playerX][playerY - 1] == FREE_CELL;
		}

		int path = 0;
		if (north)
			path++;
		if (south)
			path++;
		if (west)
			path++;
		if (east)
			path++;

		fork = path > 1;
		if (fork) {
			Coordinates coordinateFork = new Coordinates(playerX, playerY);
			forks.add(coordinateFork);
		}

		finish = (playerX == whereX && playerY == whereY);

	}

	private void unstuck() {

		for (Coordinates coordinates : forks) {

			boolean forkNotUsed = !singleForks.contains(coordinates);
			if (forkNotUsed) {
				playerX = coordinates.getX();
				playerY = coordinates.getY();
				singleForks.add(coordinates);
				stuck = false;
				break;
			}

		}
	}

	private int move = 0;
	private void step() {

		try {

			Coordinates player = new Coordinates(playerX, playerY);

			if (finish && move > 0) {
				end = false;
				possible = true;
				variableDirection = 0;
				System.out.println("Ã˚ ‚˚·‡ÎËÒ¸ ËÁ Î‡·ËËÌÚ‡!");
				System.out.println(variableDirection);

			}

			if (move > 100) {
				singleForks.clear();
				variableDirection++;
				field = startField;
				move = 0;
				//playerCords = new ArrayList<Coordinates>();
				/*possible = false;
				end = false;*/

			}
			
			if (variableDirection > 24){
				possible = false;
				end = false;
			}

			initDirection();

			printField(playerX, playerY);
			lastPlayerX = playerX;
			lastPlayerY = playerY;

			if (stuck)
				unstuck();

			southIndexNotBounds = playerX < END_FIELD;
			northIndexNotBounds = playerX > START_FIELD;
			eastIndexNotBounds = playerY < END_FIELD;
			westIndexNotBounds = playerY > START_FIELD;

			switch (variableDirection) {
			case 0:
				nsweDirection();
				playerCords.add(player);
				break;

			case 1:
				nsewDirection();
				playerCords.add(player);
				break;

			case 2:
				neswDirection();
				playerCords.add(player);
				break;
			case 3:
				newsDirection();
				playerCords.add(player);
				break;
			case 5:
				nwesDirection();
				playerCords.add(player);
				break;
			case 6:
				nwseDirection();
				playerCords.add(player);
				break;

			case 7:
				snweDirection();
				playerCords.add(player);
				break;

			case 8:
				snewDirection();
				playerCords.add(player);
				break;

			case 9:
				swenDirection();
				playerCords.add(player);
				break;

			case 10:
				swneDirection();
				playerCords.add(player);
				break;

			case 11:
				sewnDirection();
				playerCords.add(player);
				break;

			case 12:
				senwDirection();
				playerCords.add(player);
				break;

			case 13:
				wsenDirection();
				playerCords.add(player);
				break;

			case 14:
				wsneDirection();
				playerCords.add(player);
				break;

			case 15:
				wesnDirection();
				playerCords.add(player);
				break;

			case 16:
				wensDirection();
				playerCords.add(player);
				break;

			case 17:
				wnseDirection();
				playerCords.add(player);
				break;

			case 18:
				wnesDirection();
				playerCords.add(player);
				break;

			case 19:
				enswDirection();
				playerCords.add(player);
				break;

			case 20:
				enwsDirection();
				playerCords.add(player);
				break;

			case 21:
				eswnDirection();
				playerCords.add(player);
				break;

			case 22:
				esnwDirection();
				playerCords.add(player);
				break;

			case 23:
				ewsnDirection();
				playerCords.add(player);
				break;

			case 24:
				ewnsDirection();
				playerCords.add(player);
				break;

			default:
				break;
			}

		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			System.out.println("œ»◊¿À‹, ¿Õ¿À»“» ¿ —À¿Ã¿À¿—‹(((");
			end = false;
		}

		move++;
	}

	private void nsweDirection() {

		if (north && northIndexNotBounds) {
			playerX = playerX - STEP;
		} else if (south && southIndexNotBounds) {
			playerX = playerX + STEP;
		} else if (west && westIndexNotBounds) {
			playerY = playerY - STEP;
		} else if (east && eastIndexNotBounds) {
			playerY = playerY + STEP;
		}

	}

	private void nsewDirection() {

		if (north && northIndexNotBounds) {
			playerX = playerX - STEP;
		} else if (south && southIndexNotBounds) {
			playerX = playerX + STEP;
		} else if (east && eastIndexNotBounds) {
			playerY = playerY + STEP;
		} else if (west && westIndexNotBounds) {
			playerY = playerY - STEP;
		}

	}

	private void neswDirection() {
		if (north && northIndexNotBounds) {
			playerX = playerX - STEP;
		} else if (east && eastIndexNotBounds) {
			playerY = playerY + STEP;
		} else if (south && southIndexNotBounds) {
			playerX = playerX + STEP;
		} else if (west && westIndexNotBounds) {
			playerY = playerY - STEP;
		}
	}

	private void newsDirection() {
		if (north && northIndexNotBounds) {
			playerX = playerX - STEP;
		} else if (east && eastIndexNotBounds) {
			playerY = playerY + STEP;
		} else if (west && westIndexNotBounds) {
			playerY = playerY - STEP;
		} else if (south && southIndexNotBounds) {
			playerX = playerX + STEP;
		}
	}

	private void nwesDirection() {
		if (north && northIndexNotBounds) {
			playerX = playerX - STEP;
		} else if (west && westIndexNotBounds) {
			playerY = playerY - STEP;
		} else if (east && eastIndexNotBounds) {
			playerY = playerY + STEP;
		} else if (south && southIndexNotBounds) {
			playerX = playerX + STEP;
		}
	}

	private void nwseDirection() {
		if (north && northIndexNotBounds) {
			playerX = playerX - STEP;
		} else if (west && westIndexNotBounds) {
			playerY = playerY - STEP;
		} else if (south && southIndexNotBounds) {
			playerX = playerX + STEP;
		} else if (east && eastIndexNotBounds) {
			playerY = playerY + STEP;
		}
	}

	private void swenDirection() {
		if (south && southIndexNotBounds) {
			playerX = playerX + STEP;
		} else if (west && westIndexNotBounds) {
			playerY = playerY - STEP;
		} else if (east && eastIndexNotBounds) {
			playerY = playerY + STEP;
		} else if (north && northIndexNotBounds) {
			playerX = playerX - STEP;
		}
	}

	private void swneDirection() {
		if (south && southIndexNotBounds) {
			playerX = playerX + STEP;
		} else if (west && westIndexNotBounds) {
			playerY = playerY - STEP;
		} else if (north && northIndexNotBounds) {
			playerX = playerX - STEP;
		} else if (east && eastIndexNotBounds) {
			playerY = playerY + STEP;
		}
	}

	private void senwDirection() {
		if (south && southIndexNotBounds) {
			playerX = playerX + STEP;
		} else if (east && eastIndexNotBounds) {
			playerY = playerY + STEP;
		} else if (north && northIndexNotBounds) {
			playerX = playerX - STEP;
		} else if (west && westIndexNotBounds) {
			playerY = playerY - STEP;
		}
	}

	private void wsenDirection() {
		if (west && westIndexNotBounds) {
			playerY = playerY - STEP;
		} else if (south && southIndexNotBounds) {
			playerX = playerX + STEP;
		} else if (east && eastIndexNotBounds) {
			playerY = playerY + STEP;
		} else if (north && northIndexNotBounds) {
			playerX = playerX - STEP;
		}
	}

	private void wsneDirection() {
		if (west && westIndexNotBounds) {
			playerY = playerY - STEP;
		} else if (south && southIndexNotBounds) {
			playerX = playerX + STEP;
		} else if (north && northIndexNotBounds) {
			playerX = playerX - STEP;
		} else if (east && eastIndexNotBounds) {
			playerY = playerY + STEP;
		}
	}

	private void wensDirection() {
		if (west && westIndexNotBounds) {
			playerY = playerY - STEP;
		} else if (east && eastIndexNotBounds) {
			playerY = playerY + STEP;
		} else if (north && northIndexNotBounds) {
			playerX = playerX - STEP;
		} else if (south && southIndexNotBounds) {
			playerX = playerX + STEP;
		}

	}

	private void wnseDirection() {
		if (west && westIndexNotBounds) {
			playerY = playerY - STEP;
		} else if (north && northIndexNotBounds) {
			playerX = playerX - STEP;
		} else if (south && southIndexNotBounds) {
			playerX = playerX + STEP;
		} else if (east && eastIndexNotBounds) {
			playerY = playerY + STEP;
		}
	}

	private void wnesDirection() {
		if (west && westIndexNotBounds) {
			playerY = playerY - STEP;
		} else if (north && northIndexNotBounds) {
			playerX = playerX - STEP;
		} else if (east && eastIndexNotBounds) {
			playerY = playerY + STEP;
		} else if (south && southIndexNotBounds) {
			playerX = playerX + STEP;
		}
	}

	private void enswDirection() {
		if (east && eastIndexNotBounds) {
			playerY = playerY + STEP;
		} else if (north && northIndexNotBounds) {
			playerX = playerX - STEP;
		} else if (south && southIndexNotBounds) {
			playerX = playerX + STEP;
		} else if (west && westIndexNotBounds) {
			playerY = playerY - STEP;
		}
	}

	private void enwsDirection() {
		if (east && eastIndexNotBounds) {
			playerY = playerY + STEP;
		} else if (north && northIndexNotBounds) {
			playerX = playerX - STEP;
		} else if (west && westIndexNotBounds) {
			playerY = playerY - STEP;
		} else if (south && southIndexNotBounds) {
			playerX = playerX + STEP;
		}
	}

	private void eswnDirection() {
		if (east && eastIndexNotBounds) {
			playerY = playerY + STEP;
		} else if (south && southIndexNotBounds) {
			playerX = playerX + STEP;
		} else if (west && westIndexNotBounds) {
			playerY = playerY - STEP;
		} else if (north && northIndexNotBounds) {
			playerX = playerX - STEP;
		}
	}

	private void esnwDirection() {
		if (east && eastIndexNotBounds) {
			playerY = playerY + STEP;
		} else if (south && southIndexNotBounds) {
			playerX = playerX + STEP;
		} else if (north && northIndexNotBounds) {
			playerX = playerX - STEP;
		} else if (west && westIndexNotBounds) {
			playerY = playerY - STEP;
		}
	}

	private void ewsnDirection() {
		if (east && eastIndexNotBounds) {
			playerY = playerY + STEP;
		} else if (west && westIndexNotBounds) {
			playerY = playerY - STEP;
		} else if (south && southIndexNotBounds) {
			playerX = playerX + STEP;
		} else if (north && northIndexNotBounds) {
			playerX = playerX - STEP;
		}
	}

	private void wesnDirection() {
		if (west && westIndexNotBounds) {
			playerY = playerY - STEP;
		} else if (east && eastIndexNotBounds) {
			playerY = playerY + STEP;
		} else if (south && southIndexNotBounds) {
			playerX = playerX + STEP;
		} else if (north && northIndexNotBounds) {
			playerX = playerX - STEP;
		}
	}

	private void ewnsDirection() {
		if (east && eastIndexNotBounds) {
			playerY = playerY + STEP;
		} else if (west && westIndexNotBounds) {
			playerY = playerY - STEP;
		} else if (north && northIndexNotBounds) {
			playerX = playerX - STEP;
		} else if (south && southIndexNotBounds) {
			playerX = playerX + STEP;
		}
	}

	private void snweDirection() {
		if (south && southIndexNotBounds) {
			playerX = playerX + STEP;
		} else if (north && northIndexNotBounds) {
			playerX = playerX - STEP;
		} else if (west && westIndexNotBounds) {
			playerY = playerY - STEP;
		} else if (east && eastIndexNotBounds) {
			playerY = playerY + STEP;
		}

	}

	private void snewDirection() {
		if (south && southIndexNotBounds) {
			playerX = playerX + STEP;
		} else if (north && northIndexNotBounds) {
			playerX = playerX - STEP;
		} else if (east && eastIndexNotBounds) {
			playerY = playerY + STEP;
		} else if (west && westIndexNotBounds) {
			playerY = playerY - STEP;
		}
	}

	private void sewnDirection() {
		if (south && southIndexNotBounds) {
			playerX = playerX + STEP;
		} 
		else if (east && eastIndexNotBounds) {
			playerY = playerY + STEP;
		}
		else if (west && westIndexNotBounds) {
			playerY = playerY - STEP;
		}
		else if (north && northIndexNotBounds) {
			playerX = playerX - STEP;
		} 
		
	}

	public void setCurrent(int x, int y) {
		this.playerX = x;
		this.playerY = y;
		forks.add(new Coordinates(x, y));
	}

	public void setWhere(int x, int y) {
		this.whereX = x;
		this.whereY = y;
	}

	@Override
	public String toString() {
		return "Labirint [playerX=" + playerX + ", playerY=" + playerY
				+ ", lastPlayerX=" + lastPlayerX + ", lastPlayerY="
				+ lastPlayerY + ", \n forkX=" + forkX + ", forkY=" + forkY
				+ ", stuck=" + stuck + ", finish=" + finish + ", end=" + end
				+ ", south=" + south + ", north=" + north + ", east=" + east
				+ ", west=" + west + ", move=" + move + "]";
	}

	
	public boolean isPossibleMove() {

		while (end) {
			System.out.println(toString());
			step();
		}
		Coordinates player = new Coordinates(whereX, whereY);
		playerCords.add(player);
		System.out.println(variableDirection);
		return possible;
	}

	public static void main(String[] args) {
		Lab lab = new Lab();
		lab.setCurrent(9, 0);
		lab.setWhere(9, 3);
		
		lab.initStartField();
		
		lab.isPossibleMove();
		System.out.println(lab.getSingleForks());

	}
}