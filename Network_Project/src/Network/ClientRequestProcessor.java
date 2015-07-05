package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

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


	/**
	 * @param socket
	 * @param bibVerw
	 */
	public ClientRequestProcessor(Socket socket, int player, int destroyer, int frigate, int corvette, int submarine, int fieldSize) {
		//Verbindungsdaten �bernehmen
		clientSocket = socket;

		this.clientZahl = clientZahl;
		this.destroyer = destroyer;
		this.frigate = frigate;
		this.corvette = corvette;
		this.submarine = submarine;
		this.fieldSize = fieldSize;

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
		out.println("changeInitView");
		out.println(clientZahl);
		out.println(destroyer);
		out.println(frigate);
		out.println(corvette);
		out.println(submarine);
		out.println(fieldSize);
	}


	public void startGame(){
		out.println("startGame");
	}

	public void run() {
		String input = "";

		// Begr��ungsnachricht an den Client senden
		out.println("Server bereit");

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
			else if(input.equals("setShipsFromClient")){

				try {
					String ship = in.readLine();
					String koords = in.readLine();
					server.setShipsToPlayer(ship, koords, this);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}while(!(input.equals("quit")));
		try {
			clientSocket.close();
		} catch (IOException e2) {

		}
	}


}
