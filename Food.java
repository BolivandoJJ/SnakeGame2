import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

public class Food implements Serializable {
	private Cell foodCell;
	private Field field;
	private Color color = Color.GREEN;
	
	public Food (Field f) {
		field = f;
	}
	
	public Cell getFoodCell() {
		return foodCell;
	}
	
	public void generateFood() {
		foodCell = null;
		int xField = field.getXSize();
		int yField = field.getYSize();
		int xRand = -1;
		int yRand = -1;
		ArrayList <Snake> snakes = field.getSnakes();
		Cell testCell = null;
		boolean collision = false;
		do {
			collision = false;
			xRand = (int) ((Math.random() * (xField - 4)) + 2 );
			yRand = (int) ((Math.random() * (yField - 4)) + 2 );
			testCell = new Cell(xRand, yRand, color);
			for (Snake itSnake : snakes) {
				for (Cell itCell : itSnake.getBodyCells()) {
					if (testCell.checkCollision(itCell)) {
						collision = true;
						break;
					}
				} 
				if (collision) break;
			}
		} while (collision);
		foodCell = testCell;
	}
}
