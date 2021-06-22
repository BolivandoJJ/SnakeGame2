import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class GameHandler {
	private Field gameField;
	private int [] usersInput;
	private GameServer server;
	private int delay;
	
	public GameHandler(GameServer serv, int gameSpeed, int numOfSnakes, int xSizeOfField, int ySizeOfField) {
		server = serv;
		delay = 100 * (11 - gameSpeed); 
		gameField = new Field(numOfSnakes, xSizeOfField, ySizeOfField);
		usersInput = new int [numOfSnakes];
	}
	
	public void setUserInput(int elem, int input) {
		usersInput [elem] = input;
	}
	
	public void play() {
		Arrays.fill(usersInput, 0);
		defaultingSnakes();
		gameField.getFood().generateFood();
		do {
			server.getUserInput();
			doStep();
			doAction();
			server.sendField(gameField);
			try {
				Thread.sleep(delay);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		} while (!checkVictory());
	}
	
	private void doAction() {
		ArrayList<Snake> snakes = gameField.getSnakes();
		Food food = gameField.getFood();
		Border border = gameField.getBorder();
		for (Snake itSnake : snakes) {
			for (Snake iterSnake : snakes) {
				itSnake.doAction(iterSnake);
			}
			itSnake.doAction(border);
			itSnake.doAction(food);
		}
	}
	
	private void doStep() {
		ArrayList<Snake> snakes = gameField.getSnakes();
		for (Snake itSnake : snakes) {
			switch (usersInput[itSnake.getID()]) {
			case KeyEvent.VK_UP: {
				itSnake.moveUp();
				break;
			}
			case KeyEvent.VK_LEFT: {
				itSnake.moveLeft();
				break;
			}
			case KeyEvent.VK_RIGHT: {
				itSnake.moveRight();
				break;
			}
			case KeyEvent.VK_DOWN: {
				itSnake.moveDown();
				break;
			}
			}
		}
	}
	
	private boolean checkVictory() {
		int numOfAliveSnakes = 0;
		ArrayList<Snake> snakes = gameField.getSnakes();
		for (Snake itSnake : snakes) {
			if (itSnake.snakeIsAlive()) {
				numOfAliveSnakes++;
			}
		}
		if (numOfAliveSnakes < 2) {
			return true;
		} else {
			return false;
		}
	}
	
	private void defaultingSnakes() {
		for (Snake itSnake : gameField.getSnakes()) {
			itSnake.setDefault();
		}
	}
}
