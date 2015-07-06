
package Game;

import java.io.Serializable;
import Network.BattleShipServer;
import SaveGame.Save;
import Ships.Ship;
import Tools.ColoredPrint;
import Tools.ColoredPrint.EPrintColor;


public class Round implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6295162987841937031L;
	/**
	 * * @author ML, JL
	 * @version 25.03.15
	 */
	private Player[] player;
	private ColoredPrint colorPrint;
	private int fieldSize;
	private int gegner;
	private int playerOnTurn;
	private char orientation;
	private int alivePlayer;
	private BattleShipServer server;

	public Round(Player[] player, int fieldSize, BattleShipServer server){
		this.player = player;
		this.playerOnTurn = 0;
		this.alivePlayer = player.length;
		this.gegner = 0;
		this.colorPrint = new ColoredPrint();
		this.fieldSize = fieldSize;
		this.orientation = 'h';
		this.player[0].setActive(true);
		this.server = server;
		//		this.play();
	}

	public Player[] getPlayer() {
		return player;
	}

	public void setPlayer(Player[] player) {
		this.player = player;
	}

	//TODO! KI SCHUSS AUSWAHL
//	public void setKiShoot(){
//  out.println(ship + ";" + gegner + ";" + pos + ";" + orientation);
//		server.setAttack(STRING TEXT);
//	}
	
	
	public void play(){
		char orientation = 'h';
		String eingabe;
		int gegner = 0;
		int schiff = 0;
		int counter = 1;
		while(ende() > 1){
			for(int i = 0; i < player.length; i++){
				if(player[i].getIsActive()){
					
					if(player[i].getIsAlive()){

						if(player[i].checkIfAnyShipIsReady()){
							server.setPlayerMove(i);
						}else{
							player[i+1].setActive(true);
						}
					}
				}
			}
		}
		//Bei allen Schiffen die laden, wird die reloadTime um einen verringert. Ist diese = 0 sind sie wieder verfügbar.
		for(int j = 0; j < player.length; j++){
			player[j].reloadTimeCountdown();
		}
	}


	
	/**
	 * @param pos - die zu überprüfenden Koordinaten 
	 * @return Gibt zurück, ob die eingegebenen Koordinaten korrekt sind
	 */
	private int[] checkPos(String pos){
		try{
			pos = pos.replaceAll("\\s+", "");

			String[] sKoordinaten = pos.split(",");
			int[] iKoordinaten = new int[2];

			if(sKoordinaten.length != 2){
				return null;
			}
			for(int i = 0; i < 2; i++){
				int toInt = Integer.parseInt(sKoordinaten[i]);
				if(toInt < 0 || toInt > fieldSize){
					return null;
				}
				else{
					iKoordinaten[i] = toInt;
				}
			}

			return iKoordinaten;
		}
		catch(Exception e){
			this.colorPrint.println(EPrintColor.RED, "Ungültige Eingabe");

		}
		return null;
	}


	public int ende(){
		int counter = 0;
		for(int i = 0; i < player.length; i++){
			if(player[i].getIsAlive()){
				counter++;
			}
		}
		return counter;
	}


	public void setAttack(String shipString, String gegnerString, String pos, String orientationString){
		int index = playerOnTurn % player.length;
		
		gegner = Integer.parseInt(gegnerString);
		orientation = orientationString.charAt(0);

		if(gegner != index){

			int coords[] = new int[2];
			coords = checkPos(pos);
			Ship ship = null;

			if(shipString.equals("destroyer") ){
				ship = player[index].getAvailableDestroyer();
			}
			else if(shipString.equals("frigate") ){
				ship = player[index].getAvailableFrigate();
			}
			else if(shipString.equals("corvette") ){
				ship = player[index].getAvailableCorvette();
			}
			else if(shipString.equals("submarine") ){
				ship = player[index].getAvailableSubmarine();
			}
			else{
				server.attackFailed();
			}

			if(ship != null){
				String reply = player[gegner].getPrivateField().setAttack(ship, coords , orientation, player[gegner]);
				player[index].setShipIsntReady(ship);
				server.replyAttack(gegner, reply, pos, orientation);
				//Überprüft, ob der Gegner noch am Leben ist, wenn nicht wird isAlive auf false gesetzt.
				if(player[gegner].getIsAlive() == false){
					server.setPlayerIsDead(gegner);
					alivePlayer--;
					if(alivePlayer == 1){
						server.playerWins(index);
					}
				}

				player[index].setActive(false);
				System.out.println("playerOnTurn: " + playerOnTurn);
				playerOnTurn++;	
				index = playerOnTurn % player.length;

				while(player[index].getIsAlive() == false){
					if(player[index].checkIfAnyShipIsReady() == false){
						server.PlayerHasNoLoadedShips(index);
						playerOnTurn++;
						index = playerOnTurn % player.length;
					}
				}
				player[index].setActive(true);
				
				if(player[index].isPlayerBot()){
					//VORRÜBER GEHEN DIE KI ÜBERSPRINGEN!
					playerOnTurn++;
					index = playerOnTurn % player.length;
					//TODO! KI
					//player[index].MACH:KI:METHoDE!!
				}
				
				server.setActive(index);
				System.out.println("playerOnTurn: " + playerOnTurn);

			}
		}
	}
}

