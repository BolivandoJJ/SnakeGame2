import java.awt.Color;
import java.io.Serializable;

public class Cell implements Serializable {
	private int xCoord;
	private int yCoord;
	public static final int SIZE = 10;
	private Color color;
	
	public Cell(int x, int y, Color col) {
		xCoord = x;
		yCoord = y;
		color = col;
	}
	
	public Color getColor() {
		return color;
	}
	public int getXCoord() {
		return xCoord;
	}
	public int getYCoord() {
		return yCoord;
	}
	
	public void setColor(Color c) {
		color = c;
	}
	public boolean checkCollision(Cell c) {
		if ((c.getXCoord() == xCoord) && (c.getYCoord() == yCoord)) {
			return true;
		} else {
			return false;
		}
	}
	
	
}
