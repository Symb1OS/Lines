package lines;

import java.util.Arrays;

import bolls.Boll;

public class IntField {
	
	public static final int MAX_ROWS = 10;
	
	public static final int MAX_COLUMNS = 10;
	
	public static final int FREE_CELL = 1;
	
	public static final int NULL_BOLL = 0;
	
	int [][] field = new int[MAX_ROWS][MAX_COLUMNS];
			
	public IntField(Boll[][] boll) {
		for (int row = 0; row < MAX_ROWS; row++) {
			for (int column = 0; column < MAX_COLUMNS; column++) {
				if (boll[row][column].getColor() != NULL_BOLL){
					field[row][column] = NULL_BOLL;
				} else {
					field[row][column] = FREE_CELL;
				}
			}
		}
		
	}
	
	public IntField(int [][] labirint){
		this.field = labirint;
	}
	
	@Override
	public String toString() {
		return "IntField [field=" + Arrays.toString(field) + "]";
	}

	public int[][] getField() {
		return field;
	}

	public void setField(int[][] field) {
		this.field = field;
	}

	public void print() {

		for (int i = 0; i < MAX_ROWS; i++) {
			for (int j = 0; j < MAX_COLUMNS; j++) {
				System.out.print(field[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void main(String [] args){
		
		int [][] primer = {
				{1,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0},
				{0,0,0,1,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0},
				{1,0,0,0,0,0,0,0,0,0},
				{1,0,0,0,0,0,0,0,0,0},
				{1,0,0,0,0,0,0,0,0,0},
				
		};
		
		IntField intField = new IntField(primer);
		intField.print();
	}
}