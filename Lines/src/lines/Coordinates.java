package lines;

class Coordinates {
	
	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	private int x;
	
	private int y;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public void incX(){
		this.x = this.x + 1;
	}
	
	public void incY(){
		this.y = this.y + 1;
	}
	
	public void decX(){
		this.x = this.x - 1;
	}
	
	public void decY(){
		this.y = this.y - 1;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinates other = (Coordinates) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Coordinates [x=" + x + ", y=" + y + "]";
	}

}