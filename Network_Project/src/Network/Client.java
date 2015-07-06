package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Game.Round_Client;
import Main.Main_Controler;

public class Client implements Runnable{
	// Datenstrukturen f�r die Kommunikation
	private Socket socket = null;
	private BufferedReader in; // server-input stream
	private PrintStream out; // server-output stream
	private Main_Controler mainControler;
	private String[] playerNames;
	private Round_Client roundClient;
	private JFrame connection = new JFrame();
	private JLabel status = new JLabel();

	/**
	 * Konstruktor, der die Verbindung zum Server aufbaut (Socket) und dieser
	 * Grundlage Eingabe- und Ausgabestreams f�r die Kommunikation mit dem
	 * Server erzeugt.
	 * 
	 * @param host Rechner, auf dem der Server l�uft
	 * @param port Port, auf dem der Server auf Verbindungsanfragen warten
	 */
	public Client(String host, int port, Main_Controler mainControler) {
		try {
			connection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			connection.setLayout(null);
			connection.setSize(300, 100);
			connection.setVisible(true);
			connection.add(status);
			status.setText("angemeldet... warten auf weitere Spieler");
			status.setBounds(10,30, 300, 30);
			status.setVisible(true);

			// Socket-Objekt fuer die Kommunikation mit Host/Port erstellen
			socket = new Socket(host, port);
			this.mainControler = mainControler;

			// Stream-Objekt fuer Text-I/O ueber Socket erzeugen
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintStream(socket.getOutputStream());
		} catch (IOException e) {
			System.err.println("Fehler beim Socket-Stream �ffnen: " + e);
			// Wenn im "try"-Block Fehler auftreten, dann Socket schlie�en:
			if (socket != null) {
				try {
					socket.close();
					System.err.println("Socket geschlossen");
				} catch (IOException ioe) { /* Fehlerbehandlung */ }
			}
			System.exit(1);
		}

		// Verbindung erfolgreich hergestellt: IP-Adresse und Port ausgeben
		System.err.println("Verbunden mit Server " 
				+ socket.getInetAddress() + ":" + socket.getPort());
	}

	public void sendShipToServer(String ship){
		out.println("sendShipToServer");
		out.println(ship);
	}

	public void setReady(String name){
		out.println("readyToPlay");
		out.println(name);
	}

	private void quit() {
		out.println("quit");

		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getPlayerNames(){
		out.println("getPlayerNames");
	}

	public void addRoundClient(Round_Client roundClient){
		this.roundClient = roundClient;
	}

	public void setAttack(String ship, String gegner, String pos, String orientation){
		out.println("setAttack");
		out.println(ship + ";" + gegner + ";" + pos + ";" + orientation);
	}



	@Override
	public void run() {
		//this.mainControler.ChangeShipsView();

		String message = "";


		while(!(message.equals("dead") ) ){
			try {
				message = in.readLine();

			} catch (IOException e) {
				e.printStackTrace();
			}		
			if(message.equals("changeInitView") ){
				status.setText("Schiffe werden platziert...");
				String values = "";
				try {
					values = in.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				String attributes[] = values.split(";");
				int player = Integer.parseInt(attributes[0]);
				int	destroyer = Integer.parseInt(attributes[1]);
				int frigate = Integer.parseInt(attributes[2]);
				int corvette = Integer.parseInt(attributes[3]);
				int submarine = Integer.parseInt(attributes[4]);
				int fieldSize = Integer.parseInt(attributes[5]);
				mainControler.startInitGameView(player, destroyer, frigate, corvette, submarine, fieldSize);

			}
			else if(message.equals("startGame") ){
				status.setText("Bereit... warte auf die anderen Spieler...");
				mainControler.changeToRoundView();
			}
			else if(message.equals("setPlayerNames") ){
				try {
					String txt = in.readLine();
					String[] names = txt.split(";");
					this.playerNames = names; 
					this.mainControler.startRoundView(this.playerNames);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if(message.equals("setId") ){
				try {
					String id = in.readLine();
					int playerId = Integer.parseInt(id);
					this.mainControler.setPlayerId(playerId);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if(message.equals("setMove") ){
				try {
					String id = in.readLine();
					int playerId = Integer.parseInt(id);

					String name = in.readLine();

					this.roundClient.setPlayerOnTurn(playerId, name);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if(message.equals("setDead") ){
				try {
					String id = in.readLine();
					int playerId = Integer.parseInt(id);

					this.roundClient.setPlayerDead(playerId);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if(message.equals("setActive") ){
				try {
					String id = in.readLine();
					int playerId = Integer.parseInt(id);

					this.roundClient.setActive(playerId, true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if(message.equals("attackReply") ){
				try {
					String gegner = in.readLine();
					String reply = in.readLine();
					String pos = in.readLine();
					String orientation = in.readLine();
					System.out.println(reply);
					String[] values = reply.split(";");

					this.roundClient.setAttackReply(gegner, values, pos, orientation);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if(message.equals("reloadShips")){
				roundClient.reloadTime();
			}
			else if(message.equals("attackFailed")){
				this.status.setText("Angriff fehlgeschlagen! Erneut versuchen!");
			}
			else if(message.equals("setWinner")){
				try {
					String name = in.readLine();
					this.status.setText("Spieler "+ name + " hat gewonnen!!!");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if(message.equals("playerHasNoShips")){
				try {
					String name = in.readLine();
					this.status.setText("Spieler "+ name + " muss aussetzen!");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//TODO Quit

		}
	}
}
