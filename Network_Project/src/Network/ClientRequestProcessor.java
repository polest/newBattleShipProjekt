package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Hashtable;

public class ClientRequestProcessor {

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

	public void verarbeiteAnfragen(){
		System.out.println("Verbindung zu " + clientSocket.getInetAddress()
				+ ":" + clientSocket.getPort() + " durch Client abgebrochen");

		out.println("setShips");
		out.println(this.destroyer);
		out.println(this.frigate);
		out.println(this.corvette);
		out.println(this.submarine);
		out.println(this.fieldSize);


	}

	public void run() {

		String input = "";

		// Begr��ungsnachricht an den Client senden
		out.println("Server bereit");

		// Hauptschleife zur wiederholten Abwicklung der Kommunikation
		do {
			// Beginn der Benutzerinteraktion:
			// Aktion vom Client einlesen [dann ggf. weitere Daten einlesen ...]
			try {
				input = in.readLine();
			} catch (Exception e) {
				System.out.println("--->Fehler beim Lesen vom Client (Aktion): ");
				System.out.println(e.getMessage());
				continue;
			}

			// Eingabe bearbeiten:
			if (input == null) {
				// input wird von readLine() auf null gesetzt, wenn Client Verbindung abbricht
				// Einfach behandeln wie ein "quit"
				input = "quit";
			}
			else if (input.equals("quit")) {
				// nichts tun, Schleifenende
			} else if (input.equals("suchen")){
				suchen();
			} else if(input.equals("ueberprüfen")){
				ueberprüfen();
			} else if(input.equals("Spielerzahl")){
				//BattleShipServer s = new BattleShipServer();
				//s.setClientZahl(5);
			}
		} while (!(input.equals("quit")));

		System.out.println("Verbindung zu " + clientSocket.getInetAddress()
				+ ":" + clientSocket.getPort() + " durch Client abgebrochen");

		try {
			clientSocket.close();
		} catch (IOException e2) {
		}
	}




	private void suchen() {
		String name = "";
		// lese Namen ein, zu dem die Adressdaten ermittelt werden sollen
		try {
			name = in.readLine();
		} catch (Exception e) {
			System.out.println("--->Fehler beim Lesen vom Client (Name): ");
			System.out.println(e.getMessage());
		}
		/*
			 Suche nach Adresse-Objekt in Hashtable
			if (adressen.containsKey(name)) {
				Adresse adresse = adressen.get(name);
				// Stra�e senden
				out.println(adresse.getStrasse());
				// PLZ senden
				out.println(adresse.getPlz());
				// Wohnort senden
				out.println(adresse.getWohnort());
			} else {
				out.println("Fehler");
			}
		 */
	}

	private void ueberprüfen(){
		String name = "";
		System.out.println("Hallo");
		try{
			name = in.readLine();
		}catch (Exception e) {
			System.out.println("--->Fehler beim Lesen vom Client (Name): ");
			System.out.println(e.getMessage());
		}

		int x = 0;
		try {
			x = Integer.parseInt(in.readLine());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
			if(spielfeld.containsKey(name)){
				Spielfeld battlefield = spielfeld.get(name);
				battlefield.setHilfe(x);
				out.println(battlefield.getHilfe1());
			}
		 */
	}


	public void setShipsOnField(String destroyer, String frigate, String corvette, String submarine, String size){

	}
}
