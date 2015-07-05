package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import Main.Main_Controler;

public class Client implements Runnable{
	// Datenstrukturen f�r die Kommunikation
	private Socket socket = null;
	private BufferedReader in; // server-input stream
	private PrintStream out; // server-output stream
	private Main_Controler mainControler;
	private String[] playerNames;

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getPlayerNames(){
		out.println("getPlayerNames");
	}

	
	@Override
	public void run() {
		//this.mainControler.ChangeShipsView();

		String message = "";


		while(!(message.equals("dead") ) ){
			try {
				message = in.readLine();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			if(message.equals("changeInitView") ){
				String values = "";
				try {
					values = in.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
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
				mainControler.changeToRoundView();
			}
			else if(message.equals("setPlayerNames") ){
				try {
					String txt = in.readLine();
					String[] names = txt.split(";");
					this.playerNames = names; 
					this.mainControler.startRoundView(this.playerNames);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		//TODO Quit
	}
}
