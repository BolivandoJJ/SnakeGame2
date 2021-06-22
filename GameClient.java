import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameClient {
	private Socket socket;
	private DataOutputStream outputStream;
	private ObjectInputStream inputStream;
	private GamePainter painter = new GamePainter();
	private JFrame frame = new JFrame("SnakeGame2");
	private ArrayList<JLabel> scoreLabels = new ArrayList <JLabel> ();
	private int userInput;
	
	public GameClient(String ipAddress, int port) {
		try {
			connect(ipAddress, port);
			createStreams();
			new Thread(new FieldRequester()).start();
			createFrame();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Error", "Connection error", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}
	
	private void connect(String ipAddress, int port) throws IOException {
		try {
			socket = new Socket(ipAddress, port);
		} catch(UnknownHostException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error", "Invalid host address", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void waitingField() {
		while (painter.getField() == null) {
			try {
				Thread.sleep(100); 
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private void createFrame() {
		painter.setFocusable(true);
		painter.addKeyListener(new InputHandler());
		JPanel scorePanel = new JPanel();
		scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 500);
		JLabel connectLabel = new JLabel("Waiting for all players to connect");
		frame.setVisible(true);
		waitingField();
		connectLabel.setText("Connection complete");
		for (Snake itSnake : painter.getField().getSnakes()) {
			JLabel snakeLabel = new JLabel(itSnake.getColorName() + " SNAKE ");
			JLabel scoreLabel = new JLabel();
			scoreLabels.add(scoreLabel);
			JPanel snakePanel = new JPanel();
			snakePanel.add(snakeLabel);
			snakePanel.add(scoreLabel);
			scorePanel.add(snakePanel);
		}
		frame.getContentPane().add(painter);
		painter.requestFocusInWindow();
	}
	
	private void createStreams() throws IOException {
		outputStream = new DataOutputStream(socket.getOutputStream());
		inputStream = new ObjectInputStream(socket.getInputStream());
	}
	
	private void sendUserInput(int userInput) {
		try {
			outputStream.writeInt(userInput);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private void getField() throws IOException{
		try {
			Object obj = inputStream.readObject();
			painter.setField((Field) obj);
			System.out.println("Field getting");
		} catch (ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "Error", "Getting data error", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}
	
	private class InputHandler implements KeyListener {
		public void keyPressed (KeyEvent event) {
			int keyCode = event.getKeyCode();
			if (keyCode != userInput) {
				userInput = keyCode;
				if ((userInput == KeyEvent.VK_UP) || (userInput == KeyEvent.VK_LEFT) || (userInput == KeyEvent.VK_RIGHT) || (userInput == KeyEvent.VK_DOWN)) {
					sendUserInput(userInput);
				}
			}
		}
		public void keyTyped (KeyEvent event) { }
		public void keyReleased (KeyEvent event) { }
	}
	
	private class FieldRequester implements Runnable {
		public void run() {
			try {
				while (socket.isConnected()) {
					getField();
					painter.repaint();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private class GamePainter extends JPanel {
		private Field field;
		
		public Field getField() {
			return field;
		}
		
		public void setField(Field f) {
			field = f;
		}
		
		public void paintComponent(Graphics g) {
			if (field != null) {
				ArrayList<Snake> snakes = field.getSnakes();
				Border border = field.getBorder();
				Food food = field.getFood();
				g.setColor(Color.WHITE);
				g.fillRect(0,0, this.getWidth(), this.getHeight());
				g.setColor(Color.BLACK);
				g.fillRect((int) (Math.random() * 100), (int) (Math.random() * 100) , 20, 20);
				for (Cell itCell : border.getBorderCells()) {
					g.setColor(itCell.getColor());
					g.fillRect(itCell.getXCoord() * Cell.SIZE, itCell.getYCoord() * Cell.SIZE, Cell.SIZE, Cell.SIZE);
				}
				for (Snake itSnake : snakes) {
					for (Cell itCell : itSnake.getBodyCells()) {
						g.setColor(itCell.getColor());
						g.fillRect(itCell.getXCoord() * Cell.SIZE, itCell.getYCoord() * Cell.SIZE, Cell.SIZE, Cell.SIZE);
					}
				}
				Cell foodCell = food.getFoodCell();
				g.setColor(foodCell.getColor());
				g.fillRect(foodCell.getXCoord() * Cell.SIZE, foodCell.getYCoord() * Cell.SIZE, Cell.SIZE, Cell.SIZE);
			}
		}
	}
}
