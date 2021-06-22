import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

public class Snake implements Serializable {
	private ArrayList <Cell> bodyCells;
	private Color color;
	private String colorName;
	private static int numOfSnakes = 0;
	private int snakeID;
	private boolean isAlive;
	private Cell tail;
	private int xSizeOfField;
	private int ySizeOfField;
	
	public Snake(int xSize, int ySize) {
		snakeID = numOfSnakes;
		numOfSnakes++;
		setColor();
		xSizeOfField = xSize;
		ySizeOfField = ySize;
		setDefault();
	}
	
	private void setColor() {
		switch (snakeID) {
		case 0: {
			color = Color.RED;
			colorName = "RED";
			break;
		}
		case 1: {
			color = Color.YELLOW;
			colorName = "YELLOW";
			break;
		}
		case 2: {
			color = Color.BLUE;
			colorName = "BLUE";
			break;
		}
		case 3: {
			color = Color.MAGENTA;
			colorName = "MAGENTA";
			break;
		}
		}
	}
	
	public void setDefault() {
		isAlive = true;
		tail = null;
		bodyCells = new ArrayList <Cell> ();
		switch (snakeID) {
		case 0: {
			bodyCells.add(new Cell(3, 2, color));
			bodyCells.add(new Cell(2, 2, color));
			break;
		}
		case 1: {
			bodyCells.add(new Cell(xSizeOfField - 4, ySizeOfField - 3, color));
			bodyCells.add(new Cell(xSizeOfField - 3, ySizeOfField - 3, color));
			break;
		}
		case 2: {
			bodyCells.add(new Cell(3, ySizeOfField - 3, color));
			bodyCells.add(new Cell(2, ySizeOfField - 3, color));
			break;
		}
		case 3: {
			bodyCells.add(new Cell(xSizeOfField - 4, 2, color));
			bodyCells.add(new Cell(xSizeOfField - 3, 2, color));
			break;
		}
		}
	}
	
	public boolean snakeIsAlive() {
		return isAlive;
	}
	
	public ArrayList<Cell> getBodyCells() {
		return bodyCells;
	}
	
	public int getID() {
		return snakeID;
	}
	
	public String getColorName() {
		return colorName;
	}
	
	private void death() {
		isAlive = false;
		for (Cell itCell : bodyCells) {
			itCell.setColor(Color.BLACK);
		}
	}
	
	private void eat(Food f) {
		bodyCells.add(tail);
		f.generateFood();
	}
	
	private void moveBody() {
		System.out.println("Snake moving");
		tail = bodyCells.get(bodyCells.size() - 1);
		for (int i = (bodyCells.size() - 2); i >= 0; i--) {
			bodyCells.set(i + 1, bodyCells.get(i));
		}
	}
	public void moveUp() {
		if (isAlive) {
			if (bodyCells.get(0).getYCoord() ==  bodyCells.get(1).getYCoord() + 1) {
				Cell snakeHead = bodyCells.get(0);
				snakeHead = new Cell(snakeHead.getXCoord(), snakeHead.getYCoord() - 1, color);
				moveBody();
				bodyCells.remove(0);
				bodyCells.add(0, snakeHead);	
			}
		}
	}
	public void moveLeft() {
		if (isAlive) {
			if (bodyCells.get(0).getXCoord() ==  bodyCells.get(1).getXCoord() + 1) {
				Cell snakeHead = bodyCells.get(0);
				snakeHead = new Cell(snakeHead.getXCoord() - 1, snakeHead.getYCoord(), color);
				moveBody();
				bodyCells.remove(0);
				bodyCells.add(0, snakeHead);
			}
		}
	}
	public void moveRight() {
		if (isAlive) {
			if (bodyCells.get(0).getXCoord() ==  bodyCells.get(1).getXCoord() - 1) {
				Cell snakeHead = bodyCells.get(0);
				snakeHead = new Cell(snakeHead.getXCoord() + 1, snakeHead.getYCoord(), color);
				moveBody();
				bodyCells.remove(0);
				bodyCells.add(0, snakeHead);
			}
		}
	}
	public void moveDown() {
		if (isAlive) {
			if (bodyCells.get(0).getYCoord() ==  bodyCells.get(1).getYCoord() - 1) {
				Cell snakeHead = bodyCells.get(0);
				snakeHead = new Cell(snakeHead.getXCoord(), snakeHead.getYCoord() + 1, color);
				moveBody();
				bodyCells.remove(0);
				bodyCells.add(0, snakeHead);
			}
		}
	}
	
	public void doAction(Food f) {
		if (isAlive) {
			Cell foodCell = f.getFoodCell();
			Cell snakeHead = bodyCells.get(0);
			if (snakeHead.checkCollision(foodCell)) {
				eat(f);
			}
		}
	}
	public void doAction(Border b) {
		if (isAlive) {
			ArrayList <Cell> borderCells = b.getBorderCells();
			Cell snakeHead = bodyCells.get(0);
			for (Cell itCell : borderCells) {
				if (snakeHead.checkCollision(itCell)) {
					death();
					break;
				}
			}
		}
	}
	public void doAction(Snake s) {
		if (isAlive) {
			ArrayList <Cell> snakeCells = s.getBodyCells();
			Cell snakeHead = bodyCells.get(0);
			for (Cell itCell : snakeCells) {
				if (snakeHead.checkCollision(itCell)) {
					if (!snakeHead.equals(itCell)) {
						death();
						break;
					}
				}
			}
		}
	}
}
