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
		clientSocket = socket;

		this.clientZahl = player;
		this.destroyer = destroyer;
		this.frigate = frigate;
		this.corvette = corvette;
		this.submarine = submarine;
		this.fieldSize = fieldSize;
		
		connection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		connection.setSize(200, 200);
		connection.setVisible(true);
		connection.add(status);
		status.setText("angemeldet... warten auf weitere Spieler");
		status.setBounds(10,0, 100, 30);
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
		System.out.println("schicke nachrichten");
		out.println("changeInitView");
		String values = clientZahl + ";" + destroyer + ";" + frigate + ";" + corvette + ";" + submarine + ";" + fieldSize + ";";
		System.out.println("Sending: " + values);
		out.println("" + values);
		out.flush();
	}


	public void startGame(){
		out.println("startGame");
	}

	public void start() {
		String input = "";

		// Begr��ungsnachricht an den Client senden
		//out.println("Server bereit");

		// Hauptschleife zur wiederholten Abwicklung der Kommunikation

		do{
			try {
				input = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
			if(input == null){
				input = "quit";
			}else if(input.equals("fertig")){
				server.setPlayerReadyToPlay(this);
			}
			//Wenn der Client 
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
			else if(input.equals("fertig")){
				server.setReady();
			}
		}while(!(input.equals("quit")));
		try {
			clientSocket.close();
		} catch (IOException e2) {

		}
	}

	@Override
	public void run() {
		
	}


}
