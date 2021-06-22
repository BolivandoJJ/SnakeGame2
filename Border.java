import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

public class Border implements Serializable {
	private ArrayList <Cell> borderCells = new ArrayList <Cell> ();
	private Color color = Color.BLACK;
	
	public Border (int xSizeOfField, int ySizeOfField) {
		for (int i = 0; i < xSizeOfField; i++) {
			borderCells.add(new Cell(i, 0, color));
			borderCells.add(new Cell(i, ySizeOfField - 1, color));
		}
		for (int i = 1; i < ySizeOfField - 1; i++) {
			borderCells.add(new Cell(0, i, color));
			borderCells.add(new Cell(xSizeOfField - 1, i, color));
		}
	}
	
	public ArrayList<Cell> getBorderCells() {
		return borderCells;
	}
}
