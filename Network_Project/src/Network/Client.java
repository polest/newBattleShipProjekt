package Network;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import Game.InitGame_Client;
import Game.InitGame_View_Client;
import Main.Main_Controler;
import Ships.Corvette;
import Ships.Destroyer;
import Ships.Frigate;
import Ships.Ship;
import Ships.Submarine;

public class Client implements Runnable{
	// Datenstrukturen f�r die Kommunikation
	private Socket socket = null;
	private BufferedReader in; // server-input stream
	private PrintStream out; // server-output stream
	private Main_Controler mainControler;


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
	
	public void setReady(){
		out.println("fertig");
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

	/**
	 * main()-Methode zum Starten des Clients
	 * 
	 * @param args kann optional Host und Portnummer enthalten, auf der Verbindungen entgegengenommen werden sollen
	 */
	public static void main(String[] args) {
		String host = "localhost";
		int port = 4477;
		if (args.length == 2) {
			host = args[0];
			try {
				port = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				port = 0;
			}
		}
		// Client starten und mit Server verbinden:
		//Client client = new Client(host, port,);
	}

	@Override
	public void run() {
		//this.mainControler.ChangeShipsView();

		String message = "";

		try {
			message = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(!(message.equals("dead") ) ){
			if(message.equals("changeInitView") ){
				String values = "";
				try {
					values = in.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String attributes[] = values.split(";");
				System.out.println("values = " + values);
				int player = Integer.parseInt(attributes[0]);
				int	destroyer = Integer.parseInt(attributes[1]);
				int frigate = Integer.parseInt(attributes[2]);
				int corvette = Integer.parseInt(attributes[3]);
				int submarine = Integer.parseInt(attributes[4]);
				int fieldSize = Integer.parseInt(attributes[5]);

				mainControler.startInitGameView(player, destroyer, frigate, corvette, submarine, fieldSize);
				message = "";
			}
			else if(message.equals("startGame") ){
				mainControler.changeToRoundView();
			}
		}

		//TODO Quit

	}


	private void verarbeiteServerAufgabe(){
		String message = "";
		try {
			message = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(message.equals("setShips")){
			try {

			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
