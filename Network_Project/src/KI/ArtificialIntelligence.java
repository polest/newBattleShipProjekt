package KI;

import java.util.Random;

import Game.Player;
import Game.Round;
import Ships.Corvette;
import Ships.Destroyer;
import Ships.Frigate;
import Ships.Ship;
import Ships.Submarine;

public class ArtificialIntelligence {
	
	private Player[] player;
	private Player gegner;
	private String pos;
	private Ship ship;
	private char botOrientation = ' ';
	
	private int gegnerNumber;
	
	private boolean isDestroyerRdy;
	private boolean isFrigateRdy;
	private boolean isCorvetteRdy;
	private boolean isSubmarineRdy;
	
	private Random rn = new Random();
	private int aliveEnemy = -1;
	
	
	
	public Player[] getPlayer() {
		return player;
	}

	public String getPos() {
		return pos;
	}

	public String getGegnerAsString() {
		return ""+this.gegnerNumber;
	}

	public Ship getShip() {
		return ship;
	}

	/**
	 * @return
	 * wandelt Shifftyp in String um
	 */
	public String getShipAsString(){
		
		if(this.ship instanceof Destroyer){
			return "destroyer";
		}
		else if(this.ship instanceof Frigate){
			return "frigate";
		}
		else if(this.ship instanceof Corvette){
			return "corvette";
		}
		else if(this.ship instanceof Submarine){
			return "submarine";
		}
		
		return "";
	}
	
	public boolean isDestroyerRdy() {
		return isDestroyerRdy;
	}

	public void setDestroyerRdy(boolean isDestroyerRdy) {
		this.isDestroyerRdy = isDestroyerRdy;
	}

	public boolean isFrigateRdy() {
		return isFrigateRdy;
	}

	public void setFrigateRdy(boolean isFrigateRdy) {
		this.isFrigateRdy = isFrigateRdy;
	}

	public boolean isCorvetteRdy() {
		return isCorvetteRdy;
	}

	public void setCorvetteRdy(boolean isCorvetteRdy) {
		this.isCorvetteRdy = isCorvetteRdy;
	}

	public boolean isSubmarineRdy() {
		return isSubmarineRdy;
	}

	public void setSubmarineRdy(boolean isSubmarineRdy) {
		this.isSubmarineRdy = isSubmarineRdy;
	}
	
	public char getBotOrientation() {
		return botOrientation;
	}

	public String getOrientationAsString() {
		return ""+botOrientation;
	}

	
	public void setBotOrientation(char botOrientation) {
		this.botOrientation = botOrientation;
	}

	public int getAliveEnemy() {
		return aliveEnemy;
	}

	public void setAliveEnemy(int aliveEnemy) {
		this.aliveEnemy = aliveEnemy;
	}

	
	/**
	 * @param max
	 * @return
	 * generiert Random koordinaten um Schiff zu setzten
	 */
	public String setShip(int max){
		int randomX = rn.nextInt(max - 1 + 1) + 1;
		int randomY = rn.nextInt(max - 1 + 1) + 1;
		return ""+randomX+","+randomY+"";
	}
	
	/**
	 * @return
	 * generiert random orientation
	 */
	public char setShipOrientation(){
		int randomInt = rn.nextInt(2 - 1 + 1) + 1;
		if(randomInt == 1){
			return 'h';
		} else {
			return 'v';
		}
	}
	
	
	public String chooseWhereToShoot(Player gegnerMinusEins, int[][] gegnerPublicField, int max){

		int xCoordinateForShooting = 0;
		int yCoordinateForShooting = 0;
		boolean goContinue = true;
		
		// geht die matrix durch
		for(int y = 0; y < gegnerPublicField[0].length; y++){
			for(int x = 0; x < gegnerPublicField[0].length; x++){
				if(goContinue){
					// guckt ob das aktuelle feld ein schiffstreffer ist
					if(gegnerPublicField[y][x] == 2){
						
						// methode schuss für bot
						
						// prüfe ob drum herum ein schiff getroffen ist
						//if(gegnerPublicField[y-1][x] == 2 || gegnerPublicField[y+1][x] == 2 || gegnerPublicField[y][x-1] == 2 || gegnerPublicField[y][x+1] == 2){
							if(checkThisField(x, y, gegnerPublicField)){
							// wenn ja dann gucke wo schon getroffen wurde und prüfe ob die gegenseite frei ist, wenn ja dann schieße auf die gegenseite
							// 1
							 // 
							if(checkSingleField(x, y-1, gegnerPublicField.length)){
								if(gegnerPublicField[y-1][x] == 2){
								//if(gegnerPublicField[y-1][x] == 2){
								// prüfe ob gegenseite nur wasser ist
									if(checkSingleField(x, y+1, gegnerPublicField.length)){
										if(gegnerPublicField[y+1][x] == 0) {
											// wenn ja, dann schieße dort hin!
											xCoordinateForShooting = x;
											yCoordinateForShooting = y+1;
											goContinue = false;
											this.setBotOrientation('v');
										}
									} 
								}
							} 
							
							if(goContinue){
								//###
								// 2
								if(checkSingleField(x, y+1, gegnerPublicField.length)){
									if(gegnerPublicField[y+1][x] == 2){
										// prüfe ob gegenseite nur wasser ist
										if(checkSingleField(x, y-1, gegnerPublicField.length)){
											if(gegnerPublicField[y-1][x] == 0){
												// wenn ja, dann schieße dort hin!
												xCoordinateForShooting = x;
												yCoordinateForShooting = y-1;
												goContinue = false;
												this.setBotOrientation('v');
											}
										}
									}
								} 
								
								if (goContinue){
									// 3
									if(checkSingleField(x-1, y, gegnerPublicField.length)){
										if(gegnerPublicField[y][x-1] == 2){
											// prüfe ob gegenseite nur wasser ist
											if(checkSingleField(x+1, y, gegnerPublicField.length)){
												if(gegnerPublicField[y][x+1] == 0){
													// wenn ja, dann schieße dort hin!
													xCoordinateForShooting = x+1;
													yCoordinateForShooting = y;
													goContinue = false;
													this.setBotOrientation('h');
												}
											} 
										}
									} 
									
									if (goContinue){
										
										// 4
										if(checkSingleField(x+1, y, gegnerPublicField.length)){
											if(gegnerPublicField[y][x+1] == 2){
											// prüfe ob gegenseite nur wasser ist
												if(checkSingleField(x-1, y, gegnerPublicField.length)){
													if(gegnerPublicField[y][x-1] == 0){
														// wenn ja, dann schieße dort hin!
														xCoordinateForShooting = x-1;
														yCoordinateForShooting = y;
														goContinue = false;
														this.setBotOrientation('h');
													}
												} 
											}
										}	
									}
								}
							}
							
							
							
						} else {
							
							// prüfe jede seite ob wasser getroffen wurde, wenn nicht dann schieß darauf
							// 1
							if(goContinue){
								// prüfe ob hier wasser ist, wenn ja dann schieß drauf
								if(checkSingleField(x, y-1, gegnerPublicField.length)){
									if(gegnerPublicField[y-1][x] == 0){
										xCoordinateForShooting = x;
										yCoordinateForShooting = y-1;
										goContinue = false;
									}
								} 
								
								if(goContinue){
									
									if(checkSingleField(x, y+1, gegnerPublicField.length)){
										if(gegnerPublicField[y+1][x] == 0){
											xCoordinateForShooting = x;
											yCoordinateForShooting = y+1;
											goContinue = false;
										}
									} 
									
									if (goContinue){
										if(checkSingleField(x-1, y, gegnerPublicField.length)){
											if(gegnerPublicField[y][x-1] == 0){
												xCoordinateForShooting = x-1;
												yCoordinateForShooting = y;
												goContinue = false;
											}
										} 
										
										if(goContinue){
											if(checkSingleField(x+1, y, gegnerPublicField.length)){
												if(gegnerPublicField[y][x+1] == 0){
													xCoordinateForShooting = x+1;
													yCoordinateForShooting = y;
													goContinue = false;
												}
											}
										}
									}
								}
							}
							
						}
						
					}	// Ende if
				
				}
			} // for2
		} // for1
		
		
		if(xCoordinateForShooting == 0 && yCoordinateForShooting == 0){
			
			//zufällig, auf ein freies Feld, koordinaten
			int randomX;
			int randomY;
			
			randomX = rn.nextInt(max - 1 + 1) + 1;
			randomY = rn.nextInt(max - 1 + 1) + 1;
			
			while(!(gegnerPublicField[randomY][randomX] == 0)){
				randomX = rn.nextInt(max - 1 + 1) + 1;
				randomY = rn.nextInt(max - 1 + 1) + 1;
			}

			xCoordinateForShooting = randomX;
			yCoordinateForShooting = randomY;
			
			
		}
		
		return ""+xCoordinateForShooting+","+yCoordinateForShooting+"";
	}

	public char chooseBotOrientation(){
		if(this.botOrientation != ' '){
			return this.botOrientation;
		} else {
			int randomInt = rn.nextInt(2 - 1 + 1) + 1;
			if(randomInt == 1){
				return 'h';
			} else {
				return 'v';
			}
		}
	}
	
	public int chooseShipToShoot(){
		if(isDestroyerRdy() == true){
			return 1;
		} else if(isFrigateRdy() == true){
			return 2;
		} else if(isCorvetteRdy() == true){
			return 3;
		} else if(isSubmarineRdy() == true){
			return 4;
		} else {
			return 0;
		}
	}
	
	
	public boolean checkThisField(int xVal, int yVal, int[][]matrix){
		
		int length = matrix.length-1;
		
		int top = -1;
		int left = -1;
		int right = +1;
		int bottom = +1;
	
	
		if( yVal <= 0){
			top = 0;
		}
	
		if( yVal > length -1 ){
			bottom = 0;	
		}
	
	
		if( xVal<= 0){
			left = 0;
		}
	
		if( xVal > length -1 ){
			right = 0;	
		}
	
		boolean isTreffer = false;
		
		for(int i = ( yVal + top);  i <= (yVal+bottom); i++){
			if(i != yVal){
				if(matrix[i][xVal] == 2){
					isTreffer = true;
				}
			}
		}
	
		for(int j = ( xVal + left);  j<= (xVal+right); j++){
			if(j != xVal){
				if(matrix[yVal][j] == 2){
					isTreffer = true;
				}
			}
		}
		
		return isTreffer;
		
	}
	
	
	public boolean checkSingleField(int x, int y, int length){
		boolean isFieldInMatrix = true;
		
		int fieldLength = length - 1;
		
		try{
			if((x >= 0 && x <= fieldLength) && (y >= 0 && y <= fieldLength)){
				isFieldInMatrix = true;
			} else {
				isFieldInMatrix = false;
			}
		} catch(Exception e) {
			isFieldInMatrix = false;
		}
		
		return isFieldInMatrix;
	}
	
	
	
	
	
	
	public void getAliveEnemys(Player[] player, int i){
		
		int counter = 1;
		
		for(int j = 0; j < player.length; j++){
			counter = j+1;
			if(j != i){
				if(player[j].getIsAlive()){
					setAliveEnemy(j);
				}
			}
		}
	}
	
	
	public int getEnemyNumber(Player[] player, int i){

		if(player[i].getEnemyNumber() != -1 && player[player[i].getEnemyNumber()].getIsAlive()){								
			return player[i].getEnemyNumber();																	
		} else {
			return getAliveEnemy();
		}
	}
	
	public void setEnemyNumberForAI(Player[] player, int i, int number){
		player[i].setEnemyNumber(number);
		this.gegner = player[number];
		this.gegnerNumber = number;
	}
	
	public void getAllRdyShips(Player[] player, int i){
		if(player[i].getDestroyer().length > 0){
			if(player[i].checkIfShipIsReady("D")){
				setDestroyerRdy(true);
			}
		}
		if(player[i].getFrigate().length > 0){
			if(player[i].checkIfShipIsReady("F")){
				setFrigateRdy(true);
			}
		}
		if(player[i].getCorvette().length > 0){
			if(player[i].checkIfShipIsReady("C")){
				setCorvetteRdy(true);
			}
		}
		if(player[i].getSubmarine().length > 0){
			if(player[i].checkIfShipIsReady("S")){
				setSubmarineRdy(true);
			}
		}
	}
	
	public Ship getAvailableShip(Player[] player, int i, int schiff){
	
		if(schiff == 1){
			this.botOrientation = chooseBotOrientation();
			return player[i].getAvailableDestroyer();
		} else if(schiff == 2){
			this.botOrientation = chooseBotOrientation();
			return player[i].getAvailableFrigate();
		} else if(schiff == 3){
			this.botOrientation = 'h';
			return player[i].getAvailableCorvette();
		} else if(schiff == 4){
			this.botOrientation = 'h';
			return player[i].getAvailableSubmarine();
		}
		
		return null;
	}
	
	public void roundForAI(Player[] player, int i, int fieldSize){

		this.player = player;

		//int gegner = 0;
		int schiff = 0;
		
		getAliveEnemys(player, i);
	
		setEnemyNumberForAI(player, i, getEnemyNumber(player, i));
		
		setDestroyerRdy(false);
		setFrigateRdy(false);
		setCorvetteRdy(false);
		setSubmarineRdy(false);
		
		getAllRdyShips(player, i);
		schiff = chooseShipToShoot();

		this.pos = chooseWhereToShoot(player[this.gegnerNumber], player[this.gegnerNumber].getPublicField().getField(), fieldSize);
		this.ship = getAvailableShip(player, i, schiff);
	}

	
}

