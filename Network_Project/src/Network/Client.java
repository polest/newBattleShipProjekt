package Network;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;

import Game.InitGame_Client;
import Game.InitGame_View_Client;
import Game.Player;
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
	private Ship choosenShip;
	private int destroyerCount;
	private int frigateCount;
	private int corvetteCount;
	private int submarineCount;
	private boolean shipCanBePlaced;
	private int totalShips;
	private InitGame_Client initGame;
	private InitGame_View_Client initGameView;
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

		String message = "lalalala";
		while( !(message.equals("dead") ) ){
			System.out.println("client run");
			try {
				message = in.readLine();
				System.out.println(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(message.equals("changeInitView") ){
				initGameOptionsAndView();
				
				mainControler.startInitGameView(initGameView.getPanel());
			}
			else if(message.equals("startGame") ){
				mainControler.changeToRoundView();
			}
		}
		
		//TODO Quit
	}


	private void initGameOptionsAndView(){
		int player = 0;
		int fieldSize = 0;
		try {
			player = Integer.parseInt( in.readLine() );
			this.destroyerCount = Integer.parseInt( in.readLine() );
			this.frigateCount = Integer.parseInt( in.readLine() );
			this.corvetteCount =  Integer.parseInt( in.readLine() );
			this.submarineCount = Integer.parseInt( in.readLine() );
			fieldSize = Integer.parseInt( in.readLine() );

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.totalShips = this.destroyerCount + this.frigateCount + this.corvetteCount + this.submarineCount;

		initGameView =  new InitGame_View_Client(player);
		initGame = new InitGame_Client(this.destroyerCount, this.frigateCount, this.corvetteCount, this.submarineCount, fieldSize, initGameView);

		this.initGameView.getBattleFieldView().setBattleFieldMouseListener(new BattleFieldMouseListener());

	}


	private class BattleFieldMouseListener implements MouseListener{
		public void mouseClicked(MouseEvent e) {
			System.out.println("totalShips: " + totalShips);
			boolean checked;
			choosenShip = initGame.getChoosenShip();
			shipCanBePlaced = initGame.getCanBePlaced();

			if(choosenShip != null){
				if(shipCanBePlaced == true){
					JButton btn = (JButton)e.getSource();
					String pos = btn.getActionCommand();

					if(choosenShip instanceof Destroyer){
						checked = initGame.setShipToField(choosenShip, pos);

						if(checked == true){
							//TODO CLIENTREQUESTPROZEESOR!
							out.println("setShipFromClient");
							out.println("destroyer");
							out.println(pos);

							destroyerCount--;
							initGameView.decrementDestroyer(destroyerCount);
							if(destroyerCount <= 0){
								initGameView.setDestroyerDisabled();
								choosenShip = null;
							}
						}
					}
					else if(choosenShip instanceof Frigate){
						checked = initGame.setShipToField(choosenShip, pos);

						if(checked == true){
							out.println("setShipFromClient");
							out.println("frigate");
							out.println(pos);
							frigateCount--;
							initGameView.decrementFrigate(frigateCount);
							if(frigateCount <= 0){
								initGameView.setFrigateDisabled();
								choosenShip = null;
							}
						}
					}
					else if(choosenShip instanceof Corvette){
						checked = initGame.setShipToField(choosenShip, pos);

						if(checked == true){
							out.println("setShipFromClient");
							out.println("corvette");
							out.println(pos);

							corvetteCount--;
							initGameView.decrementCorvette(corvetteCount);
							if(corvetteCount <= 0){
								initGameView.setCorvetteDisabled();
								choosenShip = null;
							}
						}
					}

					else if(choosenShip instanceof Submarine){
						checked = initGame.setShipToField(choosenShip, pos);
						if(checked == true){
							out.println("setShipFromClient");
							out.println("destroyer");
							out.println(pos);

							submarineCount--;
							initGameView.decrementSubmarine(submarineCount);
							if(submarineCount <= 0){
								initGameView.setSubmarineDisabled();
								choosenShip = null;
							}
						}
					}
					totalShips--;
					System.out.println("totalShips: " + totalShips);
					if(totalShips <= 0){
						initGameView.enableFinish();
					}
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	}
}
