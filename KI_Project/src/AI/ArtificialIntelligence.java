package AI;

import java.util.Random;

import Game.Player;

public class ArtificialIntelligence {

	private boolean isDestroyerRdy;
	private boolean isFrigateRdy;
	private boolean isCorvetteRdy;
	private boolean isSubmarineRdy;
	char botOrientation = ' ';
	Random rn = new Random();
	
	
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

	public void setBotOrientation(char botOrientation) {
		this.botOrientation = botOrientation;
	}

	public String chooseWhereToShoot(Player gegnerMinusEins, int[][] gegnerPublicField, int[][] gegnerPrivateField, int max){

		int xCoordinateForShooting = 0;
		int yCoordinateForShooting = 0;
		boolean goContinue = true;
		
		String curShipString;
		
		// geht die matrix durch
		for(int y = 0; y < gegnerPublicField[0].length; y++){
			for(int x = 0; x < gegnerPublicField[0].length; x++){
				
				// guckt ob das aktuelle feld ein schiffstreffer ist
				if(gegnerPublicField[y][x] == 2){
					System.out.println("Ein hitted Field!");
					
					int curShip = gegnerPrivateField[y][x];
					
					
					switch(curShip){
					case 1:
						curShipString = "D";
						break;
					case 2:
						curShipString = "F";
						break; 
					case 3:
						curShipString = "C";
						break;
					case 4:
						curShipString = "S";
						break;
					default:
						curShipString = "0";
						break;
					}
					
						System.out.println("das Feld ist ein " + curShipString);
						
						// prüft ob das schiff gesunken ist, wenn nicht dann führt er den code aus
						if(gegnerMinusEins.checkIfSunk(curShipString, false) == false){
							
							
							System.out.println("schiff lebt noch");
							
							// methode schuss für bot
							
							// prüfe ob drum herum ein schiff getroffen ist
							if(gegnerPublicField[y-1][x] == 2 || gegnerPublicField[y+1][x] == 2 || gegnerPublicField[y][x-1] == 2 || gegnerPublicField[y][x+1] == 2){
								
								// wenn ja dann gucke wo schon getroffen wurde und prüfe ob die gegenseite frei ist, wenn ja dann schieße auf die gegenseite
								// 1
								if(gegnerPublicField[y-1][x] == 2){
									// prüfe ob gegenseite nur wasser ist
									if(gegnerPublicField[y+1][x] == 0){
										// wenn ja, dann schieße dort hin!
										xCoordinateForShooting = x;
										yCoordinateForShooting = y+1;
										goContinue = false;
										this.setBotOrientation('v');
									} 
								
								} else if(goContinue){
									//###
									// 2
									if(gegnerPublicField[y+1][x] == 2){
										// prüfe ob gegenseite nur wasser ist
										if(gegnerPublicField[y-1][x] == 0){
											// wenn ja, dann schieße dort hin!
											xCoordinateForShooting = x;
											yCoordinateForShooting = y-1;
											goContinue = false;
											this.setBotOrientation('v');
										} 
									} else if (goContinue){
										// 3
										if(gegnerPublicField[y][x-1] == 2){
											// prüfe ob gegenseite nur wasser ist
											if(gegnerPublicField[y][x+1] == 0){
												// wenn ja, dann schieße dort hin!
												xCoordinateForShooting = x+1;
												yCoordinateForShooting = y;
												goContinue = false;
												this.setBotOrientation('h');
											} 
										} else if (goContinue){
											
											// 4
											if(gegnerPublicField[y][x+1] == 2){
												// prüfe ob gegenseite nur wasser ist
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
								
								
								
							} else {
								
								// prüfe jede seite ob wasser getroffen wurde, wenn nicht dann schieß darauf
								// 1
								if(goContinue){
									// prüfe ob hier wasser ist, wenn ja dann schieß drauf
									if(gegnerPublicField[y-1][x] == 0){
										xCoordinateForShooting = x;
										yCoordinateForShooting = y-1;
										goContinue = false;
									} else if(goContinue){
										
										if(gegnerPublicField[y+1][x] == 0){
											xCoordinateForShooting = x;
											yCoordinateForShooting = y+1;
											goContinue = false;
										} else if (goContinue){
											if(gegnerPublicField[y][x-1] == 0){
												xCoordinateForShooting = x-1;
												yCoordinateForShooting = y;
												goContinue = false;
											} else if(goContinue){
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
							
							
						} else {
							System.out.println("schiff is gesunken");
						}
				}		
			}
		}
		
		
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
			
			System.out.println("frei x -> " + randomX);
			System.out.println("frei y -> " + randomY);
			
			
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
		if(isDestroyerRdy()){
			return 1;
		} else if(isFrigateRdy()){
			return 2;
		} else if(isCorvetteRdy()){
			return 3;
		} else if(isSubmarineRdy()){
			return 4;
		}
		return 0;
	}
}
