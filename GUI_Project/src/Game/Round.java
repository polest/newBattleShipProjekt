package Game;

import SaveGame.Save;
import Ships.Ship;
import Tools.ColoredPrint;
import Tools.IO;
import Tools.ColoredPrint.EPrintColor;

public class Round{

	/**
	 * * @author ML, JL
	 * @version 25.03.15
	 */
	private Player[] player;
	private ColoredPrint colorPrint;
	private Save save;
	private int fieldSize;
	private Round_View roundView;

	public Round(Player[] player, int fieldSize){
		this.player = player;
		this.colorPrint = new ColoredPrint();
		this.fieldSize = fieldSize;
		this.save = new Save();
		this.roundView = new Round_View(this.fieldSize, player);
		//this.play();
	}
	
	public Round(){
	}
		
	public Round_View getRoundView(){
		return this.roundView;
	}

	public Player[] getPlayer() {
		return player;
	}

	public void setPlayer(Player[] player) {
		this.player = player;
	}

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

							System.out.println(player[i].getPlayerName() + " ist an der Reihe.");
							//player[i].printPrivateField();
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

							gegner = IO.readEnemyInt();

							while( (gegner < 0) || ( (gegner-1) == i) || (gegner > player.length)){
								this.colorPrint.println(EPrintColor.RED, "Ungültige Eingabe! Bitte Zahl ihres Wunschgegners auswählen!");
								gegner = IO.readEnemyInt();
							}

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
								schiff = IO.readShipInt();

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

							player[gegner-1].printPublicField();

							//Koordinaten wählen und schießen

							System.out.println("Geben sie nun die Koordinaten ein, auf die sie schießen möchten. (X,Y)");

							String pos = IO.readString();
							int[] koordinaten = checkPos(pos);

							while(koordinaten == null){
								System.out.println("Bitte geben sie die X,Y Koordinaten ein, auf die sie schießen möchten.");
								pos = IO.readString();
								koordinaten = checkPos(pos);
							}
							Ship ship = null;

							if(schiff == 1){
								System.out.println("Horizontal (h) oder Vertikal(v)?");
								orientation = IO.readChar();

								while(orientation != 'h' && orientation != 'v'){
									this.colorPrint.println(EPrintColor.RED,"Fehler! Bitte h oder v eingeben!");
									orientation = IO.readChar();
								}
								ship = player[i].getAvailableDestroyer();

							}else if(schiff == 2){
								System.out.println("Horizontal (h) oder Vertikal(v)?");
								orientation = IO.readChar();

								while(orientation != 'h' && orientation != 'v'){
									this.colorPrint.println(EPrintColor.RED,"Fehler! Bitte h oder v eingeben!");
									orientation = IO.readChar();
								}
								ship = player[i].getAvailableFrigate();
							} else if(schiff == 3){
								orientation = 'h';
								ship = player[i].getAvailableCorvette();

							} else if(schiff == 4){
								orientation = 'h';
								ship = player[i].getAvailableSubmarine();
							}

							if(ship != null){
								player[gegner-1].getPrivateField().setAttack(ship, koordinaten,orientation, player[gegner-1]);
								player[i].setShipIsntReady(ship);
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
