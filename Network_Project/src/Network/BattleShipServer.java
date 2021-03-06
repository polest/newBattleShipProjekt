package Network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Game.InitGame;
import Game.Round;
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

	public final static int DEFAULT_CLIENTS = 1;

	protected int port;
	protected int clientZahl = 3;
	protected int destroyer = 1;
	protected int frigate = 1;
	protected int corvette = 1;
	protected int submarine = 1;
	protected int fieldSize = 10;
	protected int playerReady = 0;
	private JFrame connection = new JFrame();
	private JLabel status = new JLabel();
	private String playerNames;
	protected ServerSocket serverSocket;
	private ClientRequestProcessor[] crp;
	private InitGame initGame;
	private Main_Controler mainControler;
	private Round round;
	private int loggedPlayer;
	private String[] sortedNames;
	private int playerOnTurn;
	private int player;
	private int ki;


	/**
	 * Konstruktor zur Erzeugung des Adressbuch-Servers.
	 * 
	 * @param port Portnummer, auf der auf Verbindungen gewartet werden soll
	 * (wenn 0, wird Default-Port verwendet)
	 */

	public BattleShipServer(int port, int player, int clientZahl, InitGame initGame, int destroyer, int frigate, int corvette, int submarine, int fieldSize, Main_Controler mainControler) {
		this.mainControler = mainControler;
		this.initGame = initGame;
		this.playerNames = "";
		this.loggedPlayer = 0;
		this.player = player;
		this.sortedNames = new String[player];
		this.ki = player - clientZahl;
		connection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		connection.setResizable(false);
		connection.setSize(200, 200);
		connection.setVisible(true);
		connection.add(status);
		status.setText("warten auf Spieler...");
		status.setBounds(20,0, 100, 30);
		status.setVisible(true);

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
			while (loggedPlayer < clientZahl) {
				// Auf Verbindungsw�nsche warten...
				Socket clientSocket = serverSocket.accept();
				crp[loggedPlayer] =  new ClientRequestProcessor(clientSocket, player, destroyer, frigate, corvette, submarine, fieldSize);
				Thread s = new Thread(crp[loggedPlayer]);
				s.start();
				loggedPlayer++;
				status.setText("Spieler " + loggedPlayer + " ist beigetreten...");
			}
		} catch (IOException e) {
			System.err.println("Fehler w�hrend des Wartens auf Verbindungen: " + e);
			System.exit(1);
		}

		start();
	}

	public void addRound(Round round){
		this.round = round;
		this.round.addPlayerNames(sortedNames);
	}

	public void setShipsToPlayer(String ship, String pos, ClientRequestProcessor crp){
		for(int i = 0; i < this.clientZahl; i++){
			if(this.crp[i] == crp){
				this.initGame.setShipToField(ship, i, pos);
			}
		}

	}

	public void setPlayerMove(int id){
		this.playerOnTurn = id;
		for(int i = 0; i < this.clientZahl; i++){
			this.crp[i].setMove(id, this.sortedNames[i]);
		}
	}

	public void setPlayerReadyToPlay(ClientRequestProcessor crp, String name) {
		if(name.equals("")){
			Random rand1 = new Random();
			int zahl1 = rand1.nextInt(30)+1;
			Random rand2 = new Random();
			int zahl2 = rand1.nextInt(30)+1;

			int zahl3 = zahl1*zahl2;
			name = "Spieler " + zahl3;
		}

		boolean found = false;
		for(int i = 0; i < this.clientZahl; i++){
			if(found == false){
				if(crp == this.crp[i]){
					sortedNames[i] = name;
					this.crp[i].setPlayerId(i);
					found = true;
				}
			}
		}

		playerReady++;
		if(playerReady >= clientZahl){
			for(int i = 0; i < clientZahl; i++){
				playerNames = playerNames.concat(sortedNames[i] + ";");
			}

			if(playerReady < player){
				int counter = 1;
				for(int i = playerReady; i < player; i++){
					playerNames = playerNames.concat("Computer " + counter + ";");
					counter++;
				}
			}
			for(int i = 0; i < this.clientZahl; i++){
				this.crp[i].startGame();
			}
		}

	}

	public void setAttack(String txt){
		txt = txt.trim();
		String[] values = txt.split(";");
		String ship = values[0];
		String gegner = values[1];
		String pos = values[2];
		String orientation = values[3];
		round.setAttack(ship, gegner, pos, orientation);

	}

	public void attackFailed(){
		crp[playerOnTurn].attackFailed();
	}

	public String getPlayerNames(){
		return playerNames;
	}


	@Override
	public void run() {
		acceptClientConnectRequests();
	}

	public void start(){
		//Jeden Spieler und Spielfelder anlegen
		for(int j = 0; j < player; j++){
			this.initGame.initPlayerBattleShip();
			this.initGame.incrementPlayerId();
			if(j < clientZahl){
				crp[j].verarbeiteAnfragen(this);
			}
		}
		connection.dispose();
		//mainControler.ChangeShipsView();
	}

	public void setPlayerIsDead(int gegner) {
		for(int i = 0; i < this.clientZahl; i++){
			this.crp[i].setDead(gegner);
		}
	}

	public void playerWins(int index) {
		for(int i = 0; i < this.clientZahl; i++){
			this.crp[i].setWinner(this.sortedNames[index]);
		}
	}

	public void PlayerHasNoLoadedShips(int index) {
		for(int i = 0; i < this.clientZahl; i++){
			this.crp[i].playerHasNoLoadedShips(index, this.sortedNames[index]);
		}
	}

	public void setActive(int index) {
		for(int i = 0; i < this.clientZahl; i++){
			this.crp[i].setActive(index);
		}
	}

	public void replyAttack(int gegner, String reply, String coords, char orientation) {
		for(int i = 0; i < this.clientZahl; i++){
			this.crp[i].setAttackReply(gegner, reply, coords, orientation);
		}
	}

	public void reloadShips(int index) {
		this.crp[index].reloadShips();
	}

	public void setPlayerShipIsntReady(int playerId, String shipString, int ship) {
		this.crp[playerId].setShipIsntReady(shipString, ship);
	}

	public void setplayerShipSunk(int gegner, String sunkenShip) {
		this.crp[gegner].setSunkenShip(sunkenShip);
	}
	
	
	public void addToChat(String txt) {
		for(int i = 0; i < this.crp.length; i++){
			this.crp[i].addMessageToChat(txt);
		}
	}
}