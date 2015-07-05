package Network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Game.InitGame;
import Main.Main_Controler;



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
public class BattleShipServer implements Runnable{
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
	protected int playerReady = 0;
	JFrame connection = new JFrame();
	JLabel wait = new JLabel();

	protected ServerSocket serverSocket;
	private ClientRequestProcessor[] crp;
	private InitGame initGame;
	private Main_Controler mainControler;

	/**
	 * Konstruktor zur Erzeugung des Adressbuch-Servers.
	 * 
	 * @param port Portnummer, auf der auf Verbindungen gewartet werden soll
	 * (wenn 0, wird Default-Port verwendet)
	 */

	public BattleShipServer(int port, int clientZahl, InitGame initGame, int destroyer, int frigate, int corvette, int submarine, int fieldSize, Main_Controler mainControler) {
		this.mainControler = mainControler;

		this.initGame = initGame;

		connection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		connection.setSize(200, 200);
		connection.setVisible(true);
		connection.add(wait);
		wait.setText("warten auf Spieler...");
		wait.setBounds(10,0, 100, 30);
		wait.setVisible(true);
		System.out.println("laaabeeel");

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
		int i = 0;
		try {

			while (i < clientZahl) {
				// Auf Verbindungsw�nsche warten...
				Socket clientSocket = serverSocket.accept();
				// ... und dann Verarbeitung von Dienstanfragen starten:
				//ClientAdressRequestProcessor c = new ClientAdressRequestProcessor(clientSocket, adressen, spielfeld);
				//c.verarbeiteAnfragen();
				Thread s = new Thread(crp[i] =  new ClientRequestProcessor(clientSocket, clientZahl, destroyer, frigate, corvette, submarine, fieldSize) );
				s.start();
	
				i++;
				wait.setText("Spieler " + i + " ist beigetreten...");
				System.out.println("Spieler " + i + " ist beigetreten...");


			}
		} catch (IOException e) {
			System.err.println("Fehler w�hrend des Wartens auf Verbindungen: " + e);
			System.exit(1);
		}

	}

	public void setShipsToPlayer(String ship, String pos, ClientRequestProcessor crp){
		for(int i = 0; i < this.clientZahl; i++){
			if(this.crp[i] == crp){
				this.initGame.setShipToField(ship, i, pos);
			}
		}

	}

	public void setPlayerReadyToPlay(ClientRequestProcessor crp) {
		playerReady++;
		if(playerReady >= clientZahl){
			for(int i = 0; i < this.clientZahl; i++){
				this.crp[i].startGame();
			}
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
		BattleShipServer server = new BattleShipServer(port, clientZahl, null, 1, 0, 0, 1, 5, null);
		//		 Ab jetzt auf eingehende Verbindungsw�nsche von Clients warten

	}


	@Override
	public void run() {
		acceptClientConnectRequests();
		System.out.println("alle clients nun da");
		for(int j = 0; j < clientZahl; j++){
			crp[j].verarbeiteAnfragen(this);
		}
		//mainControler.ChangeShipsView();
	}

}

//// in gesondertem Thread starten:
//ClientAdressRequestProcessor c 
//	= new ClientAdressRequestProcessor(clientSocket, adressen);
//Thread t = new Thread(c);
//t.start();
