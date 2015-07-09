package Network;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Chat extends JScrollPane{

	private JTextArea status = new JTextArea();
	private JScrollPane scroller =  new JScrollPane(status);
	private JFrame connection = new JFrame();
	private JTextField input = new JTextField();
	private String name = "";
	private Client client;

	public Chat(Client client){
		this.client = client;
		
		this.connection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.connection.setLayout(null);
		this.connection.setSize(300, 300);
		this.connection.setVisible(true);
		this.connection.add(scroller);
		this.connection.add(input);
		this.scroller.setBounds(10,30, 280, 200);

		this.status.setLineWrap(true);
		this.status.setWrapStyleWord(true);
		this.status.setEditable(false);
		this.status.setForeground(Color.blue);
		this.status.setBounds(10,30, 280, 200);
		this.status.setVisible(true);
		this.status.setText("Start");

		this.input.setBounds(10, 235, 280, 30);
		this.input.setEditable(true);
		this.input.addKeyListener(new EnterText());
		this.addText("angemeldet... warten auf weitere Spieler");

	}

	public void addText(String text){
		//TODO TEXT AUF UMBRUCH PRÜFEN
		this.status.append("\n>>>"+text);

	}
	
	public void setName(String name){
		this.name = name;
	}

	private class EnterText implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				String text = input.getText();
				addText(text);
				client.addTextToChat(text);
				input.setText("");
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}
	}

}

