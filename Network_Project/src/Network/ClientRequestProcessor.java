package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class ClientRequestProcessor implements Runnable{

	// Referenz auf das serverseitige Adressbuch
	//private Hashtable<String, Adresse> adressen;
	//private Hashtable<String, Spielfeld> spielfeld;

	// Datenstrukturen f�r die Kommunikation
	private Socket clientSocket;
	private BufferedReader in;
	private PrintStream out;
	private int clientZahl;
	private int destroyer;
	private int frigate;
	private int corvette;
	private int submarine;
	private int fieldSize;
	private BattleShipServer server;
	private JFrame connection = new JFrame();
	private JLabel status = new JLabel();


	/**
	 * @param socket
	 * @param bibVerw
	 */
	public ClientRequestProcessor(Socket socket, int player, int destroyer, int frigate, int corvette, int submarine, int fieldSize) {
		//Verbindungsdaten �bernehmen
		this.clientSocket = socket;

		this.clientZahl = player;
		this.destroyer = destroyer;
		this.frigate = frigate;
		this.corvette = corvette;
		this.submarine = submarine;
		this.fieldSize = fieldSize;

		connection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		connection.setLayout(null);
		connection.setSize(300, 100);
		connection.setVisible(true);
		connection.add(status);
		status.setText("angemeldet... warten auf weitere Spieler");
		status.setBounds(10,30, 300, 30);
		status.setVisible(true);

		System.out.println("Daten sind: " + this.clientZahl + "," + this.destroyer  + "," + this.frigate   + "," + this.corvette   + "," + this.submarine   + "," + this.fieldSize) ; 		
		// I/O-Streams initialisieren:
		try {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			try {
				clientSocket.close();
			} catch (IOException e2) {
			}
			System.err.println("Ausnahme bei Bereitstellung des Streams: " + e);
			return;
		}

		System.out.println("Verbunden mit Client " 
				+ clientSocket.getInetAddress() + ":" + clientSocket.getPort());
	}

	/**
	 * Methode zur Abwicklung der Kommunikation mit dem Client gem�� dem
	 * vorgebenen Kommunikationsprotokoll.
	 */

	public void verarbeiteAnfragen(BattleShipServer server){
		this.server = server;
		status.setText("Schiffe werden platziert...");
		out.println("changeInitView");
		String values = clientZahl + ";" + destroyer + ";" + frigate + ";" + corvette + ";" + submarine + ";" + fieldSize + ";";
		out.println("" + values);
		out.flush();
	}


	public void startGame(){
		out.println("startGame");
	}

	public void start() {

	}

	@Override
	public void run() {
		String input = "";
		// Hauptschleife zur wiederholten Abwicklung der Kommunikation

		do{
			try {
				input = in.readLine();
				System.out.println(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}

			if(input == null){
				input = "quit";
			}
			else if(input.equals("sendShipToServer")){

				try {
					String values = in.readLine();
					String[] splitted = values.split(";");

					server.setShipsToPlayer(splitted[0], splitted[1], this);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			else if(input.equals("readyToPlay")){
				String name = "";
				try {
					name = in.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				server.setPlayerReadyToPlay(this, name);
			}
			else if(input.equals("getPlayerNames")){
				String names = server.getPlayerNames();
				out.println("setPlayerNames");
				out.println(names);
				
			}
		}while(!(input.equals("quit")));
		try {
			clientSocket.close();
		} catch (IOException e2) {

		}
	}


}
