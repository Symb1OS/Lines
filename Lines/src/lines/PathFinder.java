package lines;

/*
���� �p���p �������p�p��� ����� �p��������� ���� � ����p����.
��� �� �������������� p��������� ��������� ����p����, �
�p����������� ��� ������ ��� �������p���� ��� �p�������.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PathFinder {
	
	public static final int MAX_ROWS = 10;
	
	public static final int MAX_COLUMNS = 10;
	
	public static final int START_VALUE = 0;
	
	int[][] fillmap = new int[MAX_ROWS][MAX_COLUMNS]; // P����p == p����p� ����p���� !
	int[][] labyrinth;
	List buf = new ArrayList();

	PathFinder(int[][] labyrinth) {
		this.labyrinth = labyrinth;
	}

	/*
	 * ��� ������� �p���p��� �������� �� �p���������� ���� � ����� �����
	 * ��p�����, ��� �������� p����, � ���� ��, �� ���������� ����� � buf.
	 */

	void push(Point p, int n) {
		if (fillmap[p.getY()][p.getX()] <= n)
			return; // ���� ����� ���� �� ��p���, �� �� ��� �� �����
		fillmap[p.getY()][p.getX()] = n; // ����� ���������� ����� ����� ����
		buf.add(p); // ���������� �����
	}

	/* ����� ��p���� ������ ����� �� buf, ���� ��� ���� */
	Point pop() {
		if (buf.isEmpty())
			return null;
		return (Point) buf.remove(0);
	}

	Point[] find(Point start, Point end) {
		
		int tx = 0, ty = 0, n = 0, t = 0;
		Point p;
		// ������� fillmap ����������� max ���������
		for (int i = 0; i < fillmap.length; i++)
			Arrays.fill(fillmap[i], Integer.MAX_VALUE);
		push(start, 0); // ���� � ��������� ����� =0, ������� ?
		while ((p = pop()) != null) { // ����, ���� ���� ����� � ����p�
			if (p.equals(end)) {
				System.out.print("H����� ���� ����� ");
				System.out.println(n);
			}
			// n=����� ���� �� ����� �������� ������
			n = fillmap[p.getY()][p.getX()] + labyrinth[p.getY()][p.getX()];

			// ��p���p 4-� �������� ������
			if ((p.getY() + 1 < labyrinth.length)
					&& labyrinth[p.getY() + 1][p.getX()] != 0)
				push(new Point(p.getX(), p.getY() + 1), n);
			if ((p.getY() - 1 >= 0) && (labyrinth[p.getY() - 1][p.getX()] != 0))
				push(new Point(p.getX(), p.getY() - 1), n);
			if ((p.getX() + 1 < labyrinth[p.getY()].length)
					&& (labyrinth[p.getY()][p.getX() + 1] != 0))
				push(new Point(p.getX() + 1, p.getY()), n);
			if ((p.getX() - 1 >= 0) && (labyrinth[p.getY()][p.getX() - 1] != 0))
				push(new Point(p.getX() - 1, p.getY()), n);
		}
		if (fillmap[end.getY()][end.getX()] == Integer.MAX_VALUE) {
			System.err.println("���� �� ���������� !!!");
			return null;
		} else
			System.out.println("����� ��������, �p������� �� ���� !!!");
		
		List<Point> path = new ArrayList<Point>();
		
		path.add(end);
		int x = end.getX();
		int y = end.getY();
		n = Integer.MAX_VALUE; // �� ������ ������� �� ������ ����, ������ ��
								// ���� �p������ ���� �� �����
		
		
		int lastX = -1;
		int lastY = -1;
		while ((x != start.getX()) || (y != start.getY())) { // ���� �� �p���� �
					
			// ������ ����
			// ����� ������ ��������
			if (y < MAX_ROWS - 1){
				if (fillmap[y + 1][x] < n) {
					tx = x;
					ty = y + 1;
					t = fillmap[y + 1][x];
				}
			}
		
			// ������, ����p�����
			if (y > START_VALUE){
				if (fillmap[y - 1][x] < n) {
					tx = x;
					ty = y - 1;
					t = fillmap[y - 1][x];
				}
			}
			
			// ����������� ��������
			if (x < MAX_COLUMNS - 1){
				if (fillmap[y][x + 1] < n) {
					tx = x + 1;
					ty = y;
					t = fillmap[y][x + 1];
				}
			}
			
			if (x > START_VALUE){
				if (fillmap[y][x - 1] < n) {
					tx = x - 1;
					ty = y;
					t = fillmap[y][x - 1];
				}
			}
			
			x = tx;
			y = ty;
			n = t; // ��p������ � �������� ������
			
			//pp = new Point(x, y);
			if (lastX == x && lastY == y){
				break;
			}
			
			lastX = x;
			lastY = y;
			
			Point pp = new Point(x, y);
			path.add(pp);
			if (x == start.getX() && y == start.getY()){
				break;
			}
		
		}
		// �� �������� ����, ������ ����� �������, ������ ����� ��� �����������
		Point[] result = new Point[path.size()];
		t = path.size();
		for (Object point : path)
			result[--t] = (Point) point;
		
		for (Point point : result) {
			System.out.println(point.toString());
		}
		
		path.clear();
		
		return result;
	}

	public static Coordinates[] toCoordinates(Point[] point){
		
		Coordinates[] coordinates = new Coordinates[point.length];
		int i = 0;
		for (Point p : point) {
			Coordinates c = new Coordinates(p.getX(), p.getY());
			coordinates[i] = c;
			i++;
		}
		
		return coordinates;
	}
	
	private static int[][] newRandomLab(){
		
		Random random = new Random();
		int[][] buf = new int[MAX_ROWS][MAX_COLUMNS];
		for (int i = 0; i < MAX_ROWS; i++) {
			for (int j = 0; j < MAX_COLUMNS; j++) {
				buf[i][j] = random.nextInt(2)  ;
			}
		}
		return buf;
		
	}
	
	private static Point newRandomPoint(){
		
		Random random = new Random();

		return new Point(random.nextInt(9), random.nextInt(9));
		
	}
	
	public static void main(String[] args) {
		int[][] labyrinth = { 
				 { 0, 1, 1, 1, 1, 0, 1, 1, 0, 1 },
				 { 1, 1, 0, 0, 0, 0, 0, 1, 0, 1 }, 
				 { 1, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
				 { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				 { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				 { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
				 { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				 { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				 { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				 { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, };

		
		try {

			PathFinder pathFinder = new PathFinder(labyrinth);
			Point start = new Point(0, 0);
			Point end = new Point(0, 2);
			Point[] path = pathFinder.find(start, end); // H����� ����

			System.out.println("������ " + start);
			System.out.println("���� " + end);

			for (int i = 0; i < MAX_ROWS; i++) {
				for (int j = 0; j < MAX_COLUMNS; j++) {
					System.out.print(pathFinder.labyrinth[i][j] + " ");
				}
				System.out.println();
			}

			if (path != null) {
				for (Point point : path)
					System.out.println(point.toString());
			}

			System.out.println();

		} catch (OutOfMemoryError e) {
				System.gc();
			}

	}
}

class Point {

	private int x;
	
	private int y;

	Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Point))
			return false;
		return (((Point) o).getX() == x) && (((Point) o).getY() == y);
	}

	@Override
	public int hashCode() {
		return Integer.valueOf(x) ^ Integer.valueOf(y);
	}

	@Override
	public String toString() {
		return "column:" + Integer.valueOf(x).toString() + " row:"
				+ Integer.valueOf(y).toString();
	}
	
};