package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import Game.InitGame;
import Game.InitGame_View;

public class Client {
	// Datenstrukturen f�r die Kommunikation
		private Socket socket = null;
		private BufferedReader in; // server-input stream
		private PrintStream out; // server-output stream
		
		private int player;
		private int destroyer;
		private int frigate;
		private int corvette;
		private int submarine;
		private int fieldSize;
		private InitGame_View initGameView;
		private InitGame initGame;
		
		

		/**
		 * Konstruktor, der die Verbindung zum Server aufbaut (Socket) und dieser
		 * Grundlage Eingabe- und Ausgabestreams f�r die Kommunikation mit dem
		 * Server erzeugt.
		 * 
		 * @param host Rechner, auf dem der Server l�uft
		 * @param port Port, auf dem der Server auf Verbindungsanfragen warten
		 */
		public Client(String host, int port) {
			try {
				// Socket-Objekt fuer die Kommunikation mit Host/Port erstellen
				socket = new Socket(host, port);
				
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

			// Begr��ungsmeldung vom Server lesen
			try {
				String message = in.readLine();
				System.out.println(message);
			} catch (IOException e) { /* Fehlerbehandlung */ }
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
					this.player = Integer.parseInt(in.readLine());
					this.destroyer = Integer.parseInt(in.readLine());
					this.frigate = Integer.parseInt(in.readLine());
					this.corvette = Integer.parseInt(in.readLine());
					this.submarine = Integer.parseInt(in.readLine());
					this.fieldSize = Integer.parseInt(in.readLine());
					
					initGameView = new InitGame_View(this.player, this.destroyer, this.frigate, this.corvette, this.submarine, this.fieldSize);					
					
					
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
			Client client = new Client(host, port);
			// Ein paar Aufrufe von Diensten zum Testen:
			
			client.verarbeiteServerAufgabe();
			
			
			Scanner scan = new Scanner(System.in);
			
			
			client.quit();
		}
}
