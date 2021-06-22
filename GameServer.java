import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class GameServer {
	private ServerSocket sSocket;
	private ArrayList <Socket> playerSockets;
	private ArrayList <DataInputStream> inputStreams;
	private ArrayList <ObjectOutputStream> outputStreams;
	private GameHandler handler;
	
	public GameServer(int gameSpeed, int numOfPlayers, int xSizeOfField, int ySizeOfField, int port) {
		try {
			handler = new GameHandler(this, gameSpeed, numOfPlayers, xSizeOfField, ySizeOfField);
			connectPlayers(numOfPlayers, port);
			createStreams();
			startGame();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Error", "Server error", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}
	
	private void createStreams() throws IOException {
		inputStreams = new ArrayList <DataInputStream> ();
		outputStreams = new ArrayList <ObjectOutputStream> ();
		for (Socket itSocket : playerSockets) {
			inputStreams.add(new DataInputStream(itSocket.getInputStream()));
			outputStreams.add(new ObjectOutputStream(itSocket.getOutputStream()));
		}
	}
	
	private void connectPlayers(int numOfPlayers, int port) throws IOException{
		sSocket = new ServerSocket(port);
		playerSockets = new ArrayList<Socket>();
		for (int i = 0; i < numOfPlayers; i++) {
			playerSockets.add(sSocket.accept());
		}
	}
	
	private void startGame() {
		while (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "All players Connected, start game?", "Start game dialog", JOptionPane.YES_NO_OPTION)) {
			handler.play();
		}
	}
	
	public void getUserInput() {
		for (DataInputStream itStream : inputStreams) {
			try {
				int numOfElem = inputStreams.indexOf(itStream);
				int readInt = 0;
				while (itStream.available() > 0) {
					readInt = itStream.readInt();
				}
				if (readInt != 0) {
					handler.setUserInput(numOfElem, readInt);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public void sendField(Field field) {
		for (ObjectOutputStream itStream : outputStreams) {
			try {
				itStream.writeObject(field);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}