
package Game;

import java.io.Serializable;
import java.util.Random;
import KI.ArtificialIntelligence;

import Network.BattleShipServer;
import SaveGame.Save;
import Ships.Ship;


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
	private int fieldSize;
	private int gegner;
	private int playerOnTurn;
	private char orientation;
	private int alivePlayer;
	private BattleShipServer server;
	// AI ÄNDERUNG START
	Random rn = new Random();
	ArtificialIntelligence ai = new ArtificialIntelligence();
	// AI ÄNDERUNG ENDE

	public Round(Player[] player, int fieldSize, BattleShipServer server){
		this.player = player;
		this.playerOnTurn = 0;
		this.alivePlayer = player.length;
		this.gegner = 0;
		this.fieldSize = fieldSize;
		this.orientation = 'h';
		this.player[0].setActive(true);
		this.server = server;
	}

	public Player[] getPlayer() {
		return player;
	}

	public void setPlayer(Player[] player) {
		this.player = player;
	}

	public void setKiShoot(String ship, String gegner, String pos, String orientation){
		String text = ship + ";" + gegner + ";" + pos + ";" + orientation;
		setAttack(ship, gegner, pos, orientation);
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
		return null;
		}
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
				boolean nextPlayer = false;

				while(nextPlayer == false){
					if(player[index].getIsAlive() == true){
						if(player[index].checkIfAnyShipIsReady() == false){
							server.PlayerHasNoLoadedShips(index);
							playerOnTurn++;
							index = playerOnTurn % player.length;
							
							if(index == 0){
								for(int j = 0; j < player.length; j++){
									player[j].reloadTimeCountdown();
									server.reloadShips();
								}
							}
						}
						else{
							nextPlayer = true;
						}
					}
				}

				player[index].setActive(true);

				if(player[index].isPlayerBot()){
					ai.roundForAI(player, index, this.fieldSize);
					setKiShoot(ai.getShipAsString(), ai.getGegnerAsString(), ai.getPos(), ai.getOrientationAsString());
				}

				server.setActive(index);
				//Bei allen Schiffen die laden, wird die reloadTime um einen verringert. Ist diese = 0 sind sie wieder verfügbar.
				if(index == 0){
					for(int j = 0; j < player.length; j++){
						player[j].reloadTimeCountdown();
						server.reloadShips();
					}
				}
			}
		}
	}


	public void addPlayerNames(String[] sortedNames) {
		for(int i = 0; i < player.length; i++){
			player[i].setPlayerName(sortedNames[i]);
		}
	}

}

