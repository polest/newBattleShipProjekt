package Network;




import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Game.InitGame;



/**
 * Serverseitige Anwendung, die Verbindungsanfragen von Client-Prozessen 
 * entgegennimmt.
 * Falls sich ein Client �ber einen Socket verbindet, wird ein "ClientRequestProcessor"-
 * Objekt als eigener Prozess (Thread) gestartet, der dann (in seiner run()-Methode) 
 * die weitere Kommunikation mit dem Client �ber das mitgegebene Socket-Objekt 
 * �bernimmt. 
 * Danach wartet der Server weiter auf Verbindungen und wiederholt obigen
 * Prozess.
 * 
 * @author teschke
 */				
public class BattleShipServer {
	//	public final static int DEFAULT_PORT = 6789;
	public final static int DEFAULT_PORT = 4477;
	public final static int DEFAULT_CLIENTS = 2;

	protected int port;
	protected int clientZahl = 3;
	protected int destroyer = 1;
	protected int frigate = 1;
	protected int corvette = 1;
	protected int submarine = 1;
	protected int fieldSize = 10;

	protected ServerSocket serverSocket;
	private ClientRequestProcessor[] crp;
	private JFrame connection = new JFrame();
	private JLabel wait = new JLabel("Warte auf Spieler...");


	/**
	 * Konstruktor zur Erzeugung des Adressbuch-Servers.
	 * 
	 * @param port Portnummer, auf der auf Verbindungen gewartet werden soll
	 * (wenn 0, wird Default-Port verwendet)
	 */


	public void setClientZahl(int clientZahl) {
		this.clientZahl = clientZahl;
	}


	public BattleShipServer(int port, int clientZahl, int destroyer, int frigate, int corvette, int submarine, int fieldSize) {

		connection.setVisible(true);
		connection.setSize(200, 200);
		
		wait.setBounds(0,0, 100, 30);
		connection.add(wait);

		if (port == 0)
			port = DEFAULT_PORT;
		this.port = port;

		if(clientZahl == 0){
			clientZahl = DEFAULT_CLIENTS;
		}
		this.clientZahl = clientZahl;

		this.destroyer = destroyer;
		this.frigate = frigate;
		this.corvette = corvette;
		this.submarine = submarine;
		this.fieldSize = fieldSize;
		this.crp = new ClientRequestProcessor[clientZahl];
		
		try {
			// Server-Socket anlegen
			serverSocket = new ServerSocket(port, clientZahl);

			// Serverdaten ausgeben
			InetAddress ia = InetAddress.getLocalHost();
			System.out.println("Host: " + ia.getHostName());
			System.out.println("Server *" + ia.getHostAddress()	+ "* lauscht auf Port " + port);
			System.out.println(this.clientZahl);
		} catch (IOException e) {
			System.err.println("Eine Ausnahme trat beim Anlegen des Server-Sockets auf: " + e);
			System.exit(1);
		}
	}

	/**
	 * Methode zur Entgegennahme von Verbindungsw�nschen durch Clients.
	 * Die Methode fragt wiederholt ab, ob Verbindungsanfragen vorliegen
	 * und erzeugt dann jeweils ein ClientRequestProcessor-Objekt mit dem 
	 * fuer diese Verbindung erzeugten Client-Socket.
	 */
	public void acceptClientConnectRequests() {
		try {
			int i = 0;
			while (i < clientZahl) {
				// Auf Verbindungsw�nsche warten...
				Socket clientSocket = serverSocket.accept();
				// ... und dann Verarbeitung von Dienstanfragen starten:
				//ClientAdressRequestProcessor c = new ClientAdressRequestProcessor(clientSocket, adressen, spielfeld);
				//c.verarbeiteAnfragen();
				crp[i] = new ClientRequestProcessor(clientSocket, clientZahl, destroyer, frigate, corvette, submarine, fieldSize);
				ClientRequestProcessor c = crp[i];
				Thread s = new Thread(c);
				s.start();
				i++;
				wait.setText("Spieler " + i + " ist beigetreten...");
				System.out.println(i);
			}
		} catch (IOException e) {
			System.err.println("Fehler w�hrend des Wartens auf Verbindungen: " + e);
			System.exit(1);
		}
		for(int i = 0; i < clientZahl; i++){
			crp[i].verarbeiteAnfragen();
		}

	}


	/**
	 * main()-Methode zum Starten des Servers
	 * 
	 * @param args kann optional Portnummer enthalten, auf der Verbindungen entgegengenommen werden sollen
	 */
	public static void main(String[] args) {
		int port = 0;
		int clientZahl = 0;
		if (args.length == 2) {
			try {
				port = Integer.parseInt(args[0]);
				clientZahl = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				port = 0;
			}
		}
		BattleShipServer server = new BattleShipServer(port, clientZahl, 1, 0, 0, 1, 5);
		//		 Ab jetzt auf eingehende Verbindungsw�nsche von Clients warten
		server.acceptClientConnectRequests();
	}
}



//// in gesondertem Thread starten:
//ClientAdressRequestProcessor c 
//	= new ClientAdressRequestProcessor(clientSocket, adressen);
//Thread t = new Thread(c);
//t.start();
