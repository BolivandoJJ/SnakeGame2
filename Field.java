import java.io.Serializable;
import java.util.ArrayList;

public class Field implements Serializable {
	private ArrayList<Snake> snakes;
	private Border border;
	private Food food;
	private int xFieldSize;
	private int yFieldSize;
	
	public Field(int NumOfSnakes, int xSize, int ySize) {
		xFieldSize = xSize;
		yFieldSize = ySize;
		border = new Border(xFieldSize, yFieldSize);
		snakes = new ArrayList<Snake>();
		for (int i = 0; i < NumOfSnakes; i++) {
			snakes.add(new Snake(xFieldSize, yFieldSize));
		}
		food = new Food(this);
	}
	
	public ArrayList<Snake> getSnakes() {
		return snakes;
	}
	public Border getBorder() {
		return border;
	}
	public Food getFood() {
		return food;
	}
	public int getXSize() {
		return xFieldSize;
	}
	public int getYSize() {
		return yFieldSize;
	}
}
