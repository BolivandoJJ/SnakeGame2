import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameStarter {
	JFrame frame;
	JButton serverButton;
	JButton clientButton;
	JTextField ipField;
	JTextField portField;
	
	public static void main (String [] args) {
		new GameStarter().start();
	}
	
	private void start() {
		frame = new JFrame("SnakeGame2");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel labelPanel = new JPanel();
		labelPanel.add(new JLabel("IP ADDRESS"));
		labelPanel.add(new JLabel("PORT"));
		frame.getContentPane().add(labelPanel, BorderLayout.NORTH);
		JPanel textFieldPanel = new JPanel();
		textFieldPanel.add(ipField = new JTextField());
		textFieldPanel.add(portField = new JTextField());
		frame.getContentPane().add(textFieldPanel, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(clientButton = new JButton("Client"));
		buttonPanel.add(serverButton = new JButton("Server"));
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		clientButton.addActionListener(new ClientButtonListener());
		serverButton.addActionListener(new ServerButtonListener());
		frame.setSize(500,500);
		frame.setVisible(true);
	}
	
	private class ServerButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			frame.dispose();
			new GameServer(1, 2, 50, 30, 54321);
		}
	}
	
	private class ClientButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			frame.dispose();
			new GameClient("127.0.0.1", 54321);
		}
	}
	
	private class ServerStarter implements Runnable {
		public void run() {
			new GameServer(1, 2, 50, 30, 54321);
		}
	}
	
	private int getPort() throws NumberFormatException {
		String strPort = portField.getText();
		int intPort = Integer.parseInt(strPort);
		if ((intPort > 1024) && (intPort < 65535)) {
			return intPort;
		} else {
			throw new NumberFormatException();
		}
	}
}
