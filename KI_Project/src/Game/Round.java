package Game;




import java.io.File;
import java.util.Random;

import SaveGame.Save;
import Tools.ColoredPrint;
import Tools.EShipType;
import Tools.IO;
import Tools.ColoredPrint.EPrintColor;

public class Round{

	/**
	 * 
	 */
	private Player[] player;
	private int aiPlayer;
	private ColoredPrint colorPrint;
	private Save save;
	private int fieldSize;
	Random rn = new Random();

	public Round(Player[] player, int fieldSize, int aiPlayer){
		this.player = player;
		this.aiPlayer = aiPlayer;
		this.colorPrint = new ColoredPrint();
		this.fieldSize = fieldSize;
		this.save = new Save();
		this.play();
	}

	public Player[] getPlayer() {
		return player;
	}

	public void setPlayer(Player[] player) {
		this.player = player;
	}

	public void play(){
		char orientation;
		String eingabe;
		int gegner = 0;
		int schiff = 0;
		int counter = 1;
		int max = this.fieldSize;
		
		while(ende() > 1){

			for(int i = 0; i < player.length; i++){
				if(player[i].getIsActive()){

					if(player[i].getIsAlive()){

						if(player[i].checkIfAnyShipIsReady()){

							System.out.println(player[i].getPlayerName() + " ist an der Reihe.");
							player[i].printPrivateField();
							System.out.println("Gegen welchen Spieler möchten sie spielen?");

							for(int j = 0; j < player.length; j++){
								counter = j+1;
								if(j != i){
									if(player[j].getIsAlive()){
										System.out.println(player[j].getPlayerName() + "\t Spieler: " + counter);
										player[j].printPublicField();
									}else{
										System.out.println(player[j].getPlayerName() + "\t ist Tot");
									}
								}
							}

							System.out.println("Geben sie nun die Zahl ihres Wunschgegners ein.");
							
							// Start Änderung für AI
							
							
							if(player.length - this.aiPlayer <= i){
								if(player[i].getEnemyNumber() == 0){
									gegner = rn.nextInt(player.length - 1 + 1) + 1;
								} else {
									//if(){
									gegner = player[i].getEnemyNumber();
									//}
								}
							} else {
								gegner = IO.readEnemyInt();
							}
			
							
							while( (gegner < 0) || ( (gegner-1) == i) || (gegner > player.length)){
								this.colorPrint.println(EPrintColor.RED, "Ungültige Eingabe! Bitte Zahl ihres Wunschgegners auswählen!");
								
								if(player.length - this.aiPlayer <= i){
									gegner = rn.nextInt(player.length - 1 + 1) + 1;
								} else {
									gegner = IO.readEnemyInt();
								}
							}
							
							player[i].setEnemyNumber(gegner);
							System.out.println(gegner);
							
							// Ende Änderung für AI
							
							//Schiff zum angreifen wählen
							System.out.println("Sie spielen nun gegen "+ player[gegner-1].getPlayerName());

							//Schiff zum angreifen wählen

							System.out.println("Mit welchem Schiff möchten sie schießen?");

							if(player[i].getDestroyer().length > 0){
								if(player[i].checkIfShipIsReady("D")){
									System.out.println("Zerstörer\t (1)");
								}
							}
							if(player[i].getFrigate().length > 0){
								if(player[i].checkIfShipIsReady("F")){
									System.out.println("Frigatte\t (2)");
								}
							}
							if(player[i].getCorvette().length > 0){
								if(player[i].checkIfShipIsReady("C")){
									System.out.println("Korvette\t (3)");
								}
							}
							if(player[i].getSubmarine().length > 0){
								if(player[i].checkIfShipIsReady("S")){
									System.out.println("U-Boot\t\t (4)");	
								}
							}


							boolean go = false;
							while(!go){
								// Start Änderung für AI
								if(player[i].isBot()){
									if(player[i].checkIfShipIsReady("D")){
										schiff = 1;
									} else if(player[i].checkIfShipIsReady("F")){
										schiff = 2;
									} else if(player[i].checkIfShipIsReady("C")){
										schiff = 3;
									} else if(player[i].checkIfShipIsReady("S")){
										schiff = 4;
									}
								} else {
									schiff = IO.readShipInt();
								}
								
								// Ende Änderung für AI
								
								if(schiff == 1){
									go = player[i].checkIfShipIsReady("D");
								} else if(schiff == 2){
									go = player[i].checkIfShipIsReady("F");
								} else if(schiff == 3){
									go = player[i].checkIfShipIsReady("C");
								} else if(schiff == 4){
									go = player[i].checkIfShipIsReady("S");
								}

								if(!go){
									this.colorPrint.println(EPrintColor.RED, "Ungültige Eingabe! Bitte wählen sie ein Schiff aus!");
								}

							}
							
							System.out.println(schiff);
							player[gegner-1].printPublicField();

						
							// Start - Änderung für AI
							
							int[][] gegnerPublicField = player[gegner-1].getPublicField().getField();
							int[][] gegnerPrivateField = player[gegner-1].getPrivateField().getField();
							int xCoordinateForShooting = 0;
							int yCoordinateForShooting = 0;
							boolean goContinue = true;
							char botOrientation;
							String curShipString;
							
							// wenn spieler ein bot ist
							if(player[i].isBot()){
								
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
												if(player[gegner-1].checkIfSunk(curShipString, false) == false){
													
													
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
							
							}
							// Ende - Änderung für AI
							
							

							
							//Koordinaten wählen und schießen

							System.out.println("Geben sie nun die Koordinaten ein, auf die sie schießen möchten. (X,Y)");
							
							// test
							String pos;
							
							if(player[i].isBot()){
								pos = ""+xCoordinateForShooting+","+yCoordinateForShooting+"";
								System.out.println(pos);
							} else {
								pos = IO.readString();
							}
							// ---
							
							int[] koordinaten = checkPos(pos);

							while(koordinaten == null){
								System.out.println("Bitte geben sie die X,Y Koordinaten ein, auf die sie schießen möchten.");
								
								if(player[i].isBot()){
									int randomX = rn.nextInt(max - 1 + 1) + 1;
									int randomY = rn.nextInt(max - 1 + 1) + 1;
									pos = ""+randomX+","+randomY+"";
									System.out.println(pos);
								} else {
									pos = IO.readString();
								}
								
								koordinaten = checkPos(pos);
							}


							if(schiff == 1){
								System.out.println("Horizontal (h) oder Vertikal(v)?");
								
								if(player[i].isBot()){
									int randomInt = rn.nextInt(2 - 1 + 1) + 1;
									if(randomInt == 1){
										orientation = 'h';
									} else {
										orientation = 'v';
									}
								} else {
									orientation = IO.readChar();
								}

								while(orientation != 'h' && orientation != 'v'){
									this.colorPrint.println(EPrintColor.RED,"Fehler! Bitte h oder v eingeben!");
									
									if(player[i].isBot()){
										int randomInt = rn.nextInt(2 - 1 + 1) + 1;
										if(randomInt == 1){
											orientation = 'h';
										} else {
											orientation = 'v';
										}
									} else {
										orientation = IO.readChar();
									}
								}

								System.out.println(orientation);
								
								player[gegner-1].getPrivateField().setAttack(EShipType.DESTROYER, koordinaten,orientation, player[gegner-1]);
								player[i].setDestroyerIsntReady();
								player[gegner-1].checkIfSunk("D", true);
							} else if(schiff == 2){
								System.out.println("Horizontal (h) oder Vertikal(v)?");
								
								if(player[i].isBot()){
									int randomInt = rn.nextInt(2 - 1 + 1) + 1;
									if(randomInt == 1){
										orientation = 'h';
									} else {
										orientation = 'v';
									}
								} else {
									orientation = IO.readChar();
								}

								while(orientation != 'h' && orientation != 'v'){
									this.colorPrint.println(EPrintColor.RED,"Fehler! Bitte h oder v eingeben!");

									if(player[i].isBot()){
										int randomInt = rn.nextInt(2 - 1 + 1) + 1;
										if(randomInt == 1){
											orientation = 'h';
										} else {
											orientation = 'v';
										}
									} else {
										orientation = IO.readChar();
									}
								}

								System.out.println(orientation);
								
								player[gegner-1].getPrivateField().setAttack(EShipType.FRIGATE,koordinaten,orientation,player[gegner-1]);
								player[gegner-1].checkIfSunk("F", true);
								player[i].setFrigateIsntReady();
							} else if(schiff == 3){
								orientation = 'h';
								player[gegner-1].getPrivateField().setAttack(EShipType.CORVETTE,koordinaten,orientation,player[gegner-1]);
								player[gegner-1].checkIfSunk("C", true);
								player[i].setCorvetteIsntReady();
							} else if(schiff == 4){
								orientation = 'h';
								player[gegner-1].getPrivateField().setAttack(EShipType.SUBMARINE,koordinaten,orientation,player[gegner-1]);
								player[gegner-1].checkIfSunk("S", true);
								player[i].setSubmarineIsntReady();
							}

							//Überprüft, ob der Gegner noch am Leben ist, wenn nicht wird isAlive auf false gesetzt.

							if(player[gegner-1].getIsAlive() == false){
								this.colorPrint.println(EPrintColor.PURPLE,"Herzlichen Glückwunsch, sie haben " + player[gegner-1].getPlayerName() + " besiegt.");
							}


							//überprüft, ob noch Spieler am leben sind. Wenn nicht wird Spiel beendet.
							if(ende() == 1){
								this.colorPrint.println(EPrintColor.GREEN, "Herzlichen Glückwunsch, Spieler " + player[i].getPlayerName() + " hat gewonnen.");
								System.exit(-1);
							}

							player[i].setActive(false);
							if(i == player.length-1){
								player[0].setActive(true);
							}else{
								player[i+1].setActive(true);
							}



							System.out.println("Drücken sie (n) damit der nächste Spieler an der Reihe ist.");
							System.out.println("Drücken sie (x) um das Spiel zu beenden ohne zu speichern.");
							System.out.println("Drücken sie (s) um das Spiel zu speichern.");

							eingabe = IO.readString();

							while((!(eingabe.equals("n"))) && (!(eingabe.equals("x"))) &&  (!(eingabe.equals("s")))){
								System.out.println("Sie müssen eine dieser Auswahlmöglichkeiten wählen.");
								eingabe = IO.readString();
							}
							if(eingabe.equals("n")){
								for(int q = 0; q < 150; q++){
									System.out.println("");
								}
							}else if(eingabe.equals("x")){
								System.exit(0);
							}else if(eingabe.equals("s")){
								System.out.println("Bitte geben sie ihrem Spiel einen Namen unter dem sie es abspeichern möchten.");
								eingabe = IO.readString();
								save.saveGame(eingabe, player);
								System.out.println("Wenn sie das Spiel jetzt beenden möchten, drücken sie (x) um weiter zu spielen drücken sie eine andere Taste.");
								eingabe = IO.readString();
								if(eingabe.equals("x")){
									System.exit(0);
								}
							}
						}else{
							colorPrint.println(EPrintColor.BLUE, "Spieler " + player[i].getPlayerName() + " hat keine geladenen Schiffe zur verfügung! "
									+ "Sie müssen diese Runde leider aussetzen.");
							System.out.println("");

							player[i].setActive(false);
							if(i == player.length-1){
								player[0].setActive(true);
							}else{
								player[i+1].setActive(true);
							}

							System.out.println("Drücken sie (n) damit der nächste Spieler an der Reihe ist.");
							System.out.println("Drücken sie (x) um das Spiel zu beenden ohne zu speichern.");
							System.out.println("Drücken sie (s) um das Spiel zu speichern.");

							eingabe = IO.readString();

							while((!(eingabe.equals("n"))) && (!(eingabe.equals("x"))) &&  (!(eingabe.equals("s")))){
								System.out.println("Sie müssen eine dieser Auswahlmöglichkeiten wählen.");
								eingabe = IO.readString();
							}
							if(eingabe.equals("n")){
								for(int q = 0; q < 150; q++){
									System.out.println("");
								}
							}else if(eingabe.equals("x")){
								System.exit(0);
							}else if(eingabe.equals("s")){
								System.out.println("Bitte geben sie ihrem Spiel einen Namen unter dem sie es abspeichern möchten.");
								eingabe = IO.readString();
								save.saveGame(eingabe, player);
								System.out.println("Wenn sie das Spiel jetzt beenden möchten, drücken sie (x) um weiter zu spielen drücken sie eine andere Taste.");
								eingabe = IO.readString();
								if(eingabe.equals("x")){
									System.exit(0);
								}
							}

						}
					}
					else{
						player[i].setActive(false);
						if(i == player.length-1){
							player[0].setActive(true);
						}else{
							player[i+1].setActive(true);
						}
					}
				}
			}
			//Bei allen Schiffen die laden, wird die reloadTime um einen verringert. Ist diese = 0 sind sie wieder verfügbar.
			for(int j = 0; j < player.length; j++){
				player[j].reloadTimeCountdown();
			}
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



}
