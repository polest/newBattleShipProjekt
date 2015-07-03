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
	public final static int DEFAULT_PORT = 6789;
	public final static int DEFAULT_CLIENTS = 3;
	
	protected int port;
	protected int clientZahl;
	protected ServerSocket serverSocket;

	// Datenstruktur f�r das Adressbuch
	//private Hashtable<String, Adresse> adressen;
	//private Hashtable<String, Spielfeld> spielfeld;

	
	/**
	 * Konstruktor zur Erzeugung des Adressbuch-Servers.
	 * 
	 * @param port Portnummer, auf der auf Verbindungen gewartet werden soll
	 * (wenn 0, wird Default-Port verwendet)
	 */
	
	public BattleShipServer(){
		
	}
	
	
	public void setClientZahl(int clientZahl) {
		this.clientZahl = clientZahl;
	}


	public BattleShipServer(int port, int clientZahl) {

		if (port == 0)
			port = DEFAULT_PORT;
		this.port = port;
		
		if(clientZahl == 0){
			clientZahl = DEFAULT_CLIENTS;
		}
		this.clientZahl = clientZahl;
		
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

		// Interne Adress(test)daten erzeugen
		/*
		adressen = new Hashtable<String, Adresse>();
		adressen.put("Meier", new Adresse("Flughafenallee 10", 28199, "Bremen"));
		adressen.put("Schmidt", new Adresse("Hauptstra�e 28", 28357, "Bremen"));
		adressen.put("Hinz", new Adresse("Elbchaussee 101", 20123, "Hamburg"));
		adressen.put("Kunz", new Adresse("Weinsteige 12", 70711, "Stuttgart"));
		
		spielfeld = new Hashtable<String, Spielfeld>();
		
		int[][] array = {{7,1},{4,2}};
		spielfeld.put("Spieler1", new Spielfeld(2,1,1,1,1,7,array));
		*/
	}

	/**
	 * Methode zur Entgegennahme von Verbindungsw�nschen durch Clients.
	 * Die Methode fragt wiederholt ab, ob Verbindungsanfragen vorliegen
	 * und erzeugt dann jeweils ein ClientRequestProcessor-Objekt mit dem 
	 * fuer diese Verbindung erzeugten Client-Socket.
	 */
	public void acceptClientConnectRequests() {

		try {
			while (true) {
				// Auf Verbindungsw�nsche warten...
				Socket clientSocket = serverSocket.accept();
				// ... und dann Verarbeitung von Dienstanfragen starten:
				//ClientAdressRequestProcessor c = new ClientAdressRequestProcessor(clientSocket, adressen, spielfeld);
				//c.verarbeiteAnfragen();
				ClientRequestProcessor c = new ClientRequestProcessor(clientSocket);
				//Thread t = new Thread(c);
				//t.start();
			}
		} catch (IOException e) {
			System.err.println("Fehler w�hrend des Wartens auf Verbindungen: " + e);
			System.exit(1);
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
		BattleShipServer server = new BattleShipServer(port, clientZahl);
		// Ab jetzt auf eingehende Verbindungsw�nsche von Clients warten
		server.acceptClientConnectRequests();
	}
}



//// in gesondertem Thread starten:
//ClientAdressRequestProcessor c 
//	= new ClientAdressRequestProcessor(clientSocket, adressen);
//Thread t = new Thread(c);
//t.start();
