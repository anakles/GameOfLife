
public class Cell {
	private int x;
	private int y;
	private boolean isAlive;
	
	public Cell (int x, int y, boolean alive) {
		this.x = x;
		this.y = y;
		this.isAlive = alive;
	}
	
	
	/**Returns true, if the cell is alive,
	 * and false if it isn't */
	public boolean isAlive() {
		return isAlive;
	}
	
	public void revive() {
		this.isAlive = true;
	}
	
	public void kill() {
		this.isAlive = false;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void toggleLife() {
		isAlive = !isAlive;
	}
	
}
