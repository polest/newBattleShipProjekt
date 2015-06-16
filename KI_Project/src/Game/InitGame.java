package Game;

import java.io.File;
import java.io.Serializable;
import java.util.Random;

import AI.ArtificialIntelligence;
import SaveGame.Load;
import Tools.ColoredPrint;
import Tools.ColoredPrint.EPrintColor;
import Tools.EShipType;
import Tools.IO;

public class InitGame implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 988834907748712441L;

	private Options gameOptions;
	private Load load = new Load();
	private Player[] player;
	private int aiPlayer;
	private ColoredPrint colorPrint = new ColoredPrint();
	private int fieldSize;
	String[] listFileNames;
	private ArtificialIntelligence ai = new ArtificialIntelligence(); 


	public InitGame(boolean start){
		if(start){
			System.out.println("Um das Spiel direkt zu starten, drücken sie bitte die (1)");
			System.out.println("Um die Spielanleitung zu sehen, drücken sie bitte die (2)");
			int auswahl = IO.readInt();
			while(auswahl < 1 || auswahl > 2){
				this.colorPrint.println(EPrintColor.RED, "Sie müssen bitte eine der beiden Auswahlmöglichkeiten wählen.");
				auswahl = IO.readInt();
			}
			if(auswahl == 2){
				instructions();
			}

			this.gameOptions = new Options();
			this.configureGame();
			Round rounds = new Round(this.player, this.fieldSize);
		}else{
			boolean go = false;
			while(!go){
				System.out.println("Bitte geben sie die Zahl von ihrem Spiel ein, welches sie laden möchten.");
				
				File f = new File(System.getProperty("user.dir") + "/data");
				File[] l = f.listFiles(); 
				
				listFileNames = new String[0];
				
				for (int x = 0; x < l.length; x++) 
				{
					try{
						
						String fileName = l[x].getName();
						String[] fileArray = fileName.split("\\.");
						
						if(fileArray[1].equals("save")){
							
							String[] newArray = new String[listFileNames.length + 1];
							
							for(int i = 0; i < listFileNames.length; i++){
								newArray[i] = listFileNames[i];
							}
							
							newArray[newArray.length-1] = fileArray[0];
							
							listFileNames = newArray;
							
							System.out.println("("+ newArray.length +") " + fileArray[0]);
						}
					} catch (ArrayIndexOutOfBoundsException e){
						e.getStackTrace();
					}
				}
				
				if(listFileNames.length == 0){
					this.colorPrint.println(EPrintColor.RED, "Keine Datei zum laden gefunden.");
					System.exit(0);
				}
				
				int eingabe = IO.readInt();
				
				while(eingabe < 1 || eingabe > listFileNames.length){
					this.colorPrint.println(EPrintColor.RED, "Falsche Eingabe");
					System.out.println("Bitte geben sie die Zahl von ihrem Spiel ein, welches sie laden möchten.");
					eingabe = IO.readInt();
				}
				
				if(load.loadGame(listFileNames[eingabe-1])){
					go = true;
				}
			}
			this.player = load.getPlayer();
			this.fieldSize = player[0].getPrivateField().getSize();
			Round rounds = new Round(this.player,this.fieldSize);
		}

	}

	public Player[] getPlayer() {
		return player;
	}

	private void configureGame(){
		this.player = new Player[this.gameOptions.getPlayer()];
		this.fieldSize = this.gameOptions.getBattlefieldSize();

		int allPlayer = this.gameOptions.getPlayer();
		this.aiPlayer = this.gameOptions.getAiPlayer();
		
		for(int i = 0; i < player.length; i++){
			BattleField battlefield = new BattleField(fieldSize);
			
			if(allPlayer - aiPlayer <= i){
				if(i == 0){
					player[i] = new Player(true, this.gameOptions.getTotalShips(), this.gameOptions.getDestroyer(), 
							this.gameOptions.getFrigate(), this.gameOptions.getCorvette(),this.gameOptions.getSubmarine(),this.gameOptions.getPlayerNames()[i], battlefield, true);
				}else{
					player[i] = new Player(false, this.gameOptions.getTotalShips(), this.gameOptions.getDestroyer(), 
							this.gameOptions.getFrigate(), this.gameOptions.getCorvette(),this.gameOptions.getSubmarine(),this.gameOptions.getPlayerNames()[i], battlefield, true);
				}
			} else {
				if(i == 0){
					player[i] = new Player(true, this.gameOptions.getTotalShips(), this.gameOptions.getDestroyer(), 
							this.gameOptions.getFrigate(), this.gameOptions.getCorvette(),this.gameOptions.getSubmarine(),this.gameOptions.getPlayerNames()[i], battlefield, false);
				}else{
					player[i] = new Player(false, this.gameOptions.getTotalShips(), this.gameOptions.getDestroyer(), 
							this.gameOptions.getFrigate(), this.gameOptions.getCorvette(),this.gameOptions.getSubmarine(),this.gameOptions.getPlayerNames()[i], battlefield, false);
				}
			}
			
		}

		this.setShipsToField();

	}



	/**
	 *  Positionierung der Schiffe von jedem Spieler (nacheinander)
	 */
	private void setShipsToField(){
		IO.println("Der Reihe nach platziert jeder sein/e Schiff/e!\n");

		for(int i = 0; i < player.length; i++){
			
			int max = this.fieldSize;
			Random rn = new Random();
				
			/*
			 * Das Spielfeld des Spieler wird auf der Konsole ausgedruckt
			 * und seine Schiffe vorbereitet
			 */
			//IO.println(player[i].getPlayerName() + " : ");
			player[i].getPrivateField().printPrivateField(player[i].getPlayerName());
			int destroyer = player[i].getDestroyer().length;
			int frigate = player[i].getFrigate().length;
			int corvette = player[i].getCorvette().length;
			int submarine = player[i].getSubmarine().length;

			IO.println("Spieler \"" + player[i].getPlayerName() + "\" ist an der Reihe. \n Bitte geben sie die Koordinaten ein (X,Y)");

			/*
			 * der Benutzer aufgefordert seine Schiffe zu positionieren
			 * Solange wie die eingegebenen Koordinaten ungültig sind,
			 * wird erneut aufgefordert das Schiff zu positionieren
			 */

			//ZERSTÖRER
			for(int d = 0; d < destroyer; d++){
				int id = d+1;
				IO.println("Zerstörer (" + id + ")");
				boolean checked = false;

				while(checked == false){
					
					// Start Änderung für AI
					String pos;
					
					if(player[i].isBot()){
						pos = ai.setShip(this.fieldSize);
						System.out.println(pos);
					} else {
						pos = IO.readString();
					}
					// Ende Änderung für AI
					
					int[] koordinaten = checkPos(pos);

					if(koordinaten == null){
						this.colorPrint.println(EPrintColor.RED, "Fehler in der Eingabe! (X, Y)");
					} else {
						IO.println("Horizontal h \nVertikal v");
						
						
						// Start Änderung für AI
						char orientation;
						
						if(player[i].isBot()){
							orientation = ai.setShipOrientation();
						} else {
							orientation = IO.readChar();
						}
						// Ende Änderung für AI
						
						while(orientation != 'h' && orientation != 'v'){
							this.colorPrint.println(EPrintColor.RED,"Fehler! Bitte h oder v eingeben!");
							orientation = IO.readChar();
						}
						
						System.out.println(orientation);
						
						if(orientation == 'h'){
							this.player[i].saveShipCoordinatesH(koordinaten[0], koordinaten[1], 5, d);
						}
						else{
							this.player[i].saveShipCoordinatesV(koordinaten[0], koordinaten[1], 5, d);
						}

						if(player[i].getPrivateField().setShips(EShipType.DESTROYER, koordinaten[0], koordinaten[1], orientation) == true){
							player[i].getPrivateField().printPrivateField(player[i].getPlayerName());
							checked = true;
						}
						else{
							this.colorPrint.println(EPrintColor.RED, "Schiff kann dort nicht positioniert werden!\nBitte erneut Koordinaten eingeben");
						}
					}
				}
			}

			//FREGATTE
			for(int f = 0; f < frigate; f++){
				int id = f+1;
				IO.println("Fregatte (" + id + ")");
				boolean checked = false;

				while(checked == false){

					// Start Änderung für AI
					String pos;
					
					if(player[i].isBot()){
						pos = ai.setShip(this.fieldSize);
						System.out.println(pos);
					} else {
						pos = IO.readString();
					}
					// Ende Änderung für AI
					
					int[] koordinaten = checkPos(pos);

					if(koordinaten == null){
						this.colorPrint.println(EPrintColor.RED, "Fehler in der Eingabe! (X, Y)");
					}
					else{
						IO.println("Horizontal h \nVertikal v");

						// Start Änderung für AI
						char orientation;
						
						if(player[i].isBot()){
							orientation = ai.setShipOrientation();
						} else {
							orientation = IO.readChar();
						}
						// Ende Änderung für AI

						while(orientation != 'h' && orientation != 'v'){
							this.colorPrint.println(EPrintColor.RED,"Fehler! Bitte h oder v eingeben!");
							orientation = IO.readChar();
						}
						if(orientation == 'h'){
							this.player[i].saveShipCoordinatesH(koordinaten[0], koordinaten[1], 4, f);
						}
						else{
							this.player[i].saveShipCoordinatesV(koordinaten[0], koordinaten[1], 4, f);
						}
						if(player[i].getPrivateField().setShips(EShipType.FRIGATE, koordinaten[0], koordinaten[1], orientation) == true){
							player[i].getPrivateField().printPrivateField(player[i].getPlayerName());
							checked = true;
						}
						else{
							this.colorPrint.println(EPrintColor.RED, "Schiff kann dort nicht positioniert werden!\nBitte erneut Koordinaten eingeben");
						}
					}
				}

			}

			//KORVETTE
			for(int k = 0; k < corvette; k++){
				int id = k+1;
				IO.println("Korvette (" + id + ")");
				boolean checked = false;

				while(checked == false){

					// Start Änderung für AI
					String pos;
					
					if(player[i].isBot()){
						pos = ai.setShip(this.fieldSize);
						System.out.println(pos);
					} else {
						pos = IO.readString();
					}
					// Ende Änderung für AI
					
					int[] koordinaten = checkPos(pos);

					if(koordinaten == null){
						this.colorPrint.println(EPrintColor.RED,"Fehler in der Eingabe! (X, Y)");
					}
					else{
						IO.println("Horizontal h \nVertikal v");

						// Start Änderung für AI
						char orientation;
						
						if(player[i].isBot()){
							orientation = ai.setShipOrientation();
						} else {
							orientation = IO.readChar();
						}
						// Ende Änderung für AI
						
						while(orientation != 'h' && orientation != 'v'){
							this.colorPrint.println(EPrintColor.RED,"Fehler! Bitte h oder v eingeben!");
							orientation = IO.readChar();
						}
						
						if(orientation == 'h'){
							this.player[i].saveShipCoordinatesH(koordinaten[0], koordinaten[1], 3, k);
						}
						else{
							this.player[i].saveShipCoordinatesV(koordinaten[0], koordinaten[1], 3, k);
						}
						if(player[i].getPrivateField().setShips(EShipType.CORVETTE, koordinaten[0], koordinaten[1], orientation) == true){
							player[i].getPrivateField().printPrivateField(player[i].getPlayerName());
							checked = true;
						}
						else{
							this.colorPrint.println(EPrintColor.RED, "Schiff kann dort nicht positioniert werden!\nBitte erneut Koordinaten eingeben");
						}
					}
				}

			}

			//UBOOT
			for(int s = 0; s < submarine; s++){
				int id = s+1;
				IO.println("UBoot (" + id + ")");
				boolean checked = false;

				while(checked == false){

					// Start Änderung für AI
					String pos;
					
					if(player[i].isBot()){
						pos = ai.setShip(this.fieldSize);
						System.out.println(pos);
					} else {
						pos = IO.readString();
					}
					// Ende Änderung für AI
					
					int[] koordinaten = checkPos(pos);

					if(koordinaten == null){
						this.colorPrint.println(EPrintColor.RED,"Fehler in der Eingabe! (X, Y)");
					}
					else{
						IO.println("Horizontal h \nVertikal v");

						// Start Änderung für AI
						char orientation;
						
						if(player[i].isBot()){
							orientation = ai.setShipOrientation();
						} else {
							orientation = IO.readChar();
						}
						// Ende Änderung für AI

						while(orientation != 'h' && orientation != 'v'){
							this.colorPrint.println(EPrintColor.RED,"Fehler! Bitte h oder v eingeben!");
							orientation = IO.readChar();
						}
						if(orientation == 'h'){
							this.player[i].saveShipCoordinatesH(koordinaten[0], koordinaten[1], 2, s);
						}
						else{
							this.player[i].saveShipCoordinatesV(koordinaten[0], koordinaten[1], 2, s);
						}
						if(player[i].getPrivateField().setShips(EShipType.SUBMARINE, koordinaten[0], koordinaten[1], orientation) == true){
							player[i].getPrivateField().printPrivateField(player[i].getPlayerName());
							checked = true;
						}
						else{
							this.colorPrint.println(EPrintColor.RED, "Schiff kann dort nicht positioniert werden!\nBitte erneut Koordinaten eingeben");
						}
					}
				}
			}
			if(i+1 < player.length){
				System.out.println("Drücken sie (n) damit der nächste Spieler an der Reihe ist.");
			}else{
				System.out.println("Drücken sie (n) um das Spiel zu starten.");
			}


			String eingabe = IO.readString();
			while(!eingabe.equals("n")){
				System.out.println("Sie müssen bitte (n) drücken, damit der nächste Spieler an der Reihe ist.");
				eingabe = IO.readString();
			}
			if(eingabe.equals("n")){
				for(int q = 0; q < 150; q++){
					System.out.println("");
				}

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
		}
		return null;
	}

	private void instructions(){
		for(int i = 0; i < 10; i++){
			System.out.println("");
		}
		this.colorPrint.println(EPrintColor.BLUE, "Schiffe versenken Regelwerk");
		System.out.println("- Lars Edition -");
		System.out.println("");
		this.colorPrint.println(EPrintColor.BLUE, "Spielerzahl:");
		System.out.println("Du kannst ein Spiel mit 2-6 Spielern starten.");
		System.out.println("");
		this.colorPrint.println(EPrintColor.BLUE, "Ziel des Spiels:");
		System.out.println("Jeder versteckt eine Flotte von Schiffen vor seinen/m Gegner/n.");
		System.out.println("Derjenige, der zuerst alle Schiffe des/der Gegner/s komplett getroffen und versenkt hat gewinnt.");
		System.out.println("");
		this.colorPrint.println(EPrintColor.BLUE, "Die Flotte besteht aus folgenden Schiffen:");
		System.out.println("");
		System.out.println("Die Anzahl der verschiedenen Schiffe kann frei gewählt werden.");
		System.out.println("Zerstörern \t (5 Felder lang, 3 Felder Schusslänge, 3 Runden nachladen)");
		System.out.println("Fregatten \t (4 Felder lang, 2 Felder Schusslänge, 2 Runden nachladen)");
		System.out.println("Korvetten \t (3 Felder lang, 1 Feld Schusslänge, 1 Runde nachladen)");
		System.out.println("U-Botten \t (2 Felder lang, 1 Feld Schusslänge, 1 Runde nachladen)");
		System.out.println("");
		this.colorPrint.println(EPrintColor.BLUE, "Das Einsetzen der Schiffe:");
		System.out.println("");
		System.out.println("Vor dem ersten Spielzug muss jeder Spieler seine Schiffe einsetzen.");
		System.out.println("Die Schiffe müssen so eingesetzt werden, dass sie");
		System.out.println("immer ein Feld Abstand zu einem anderen Schiff haben.");
		System.out.println("Die Schiffe dürfen horizontal oder vertikal gesetzt werden.");
		System.out.println("Die Größe des Spielfelds hängt dabei von der Anzahl der Schiffe ab.");
		System.out.println("");
		this.colorPrint.println(EPrintColor.BLUE, "Auf Felder schießen:");
		System.out.println("");
		System.out.println("Zum schießen wählst du zuerst einen Gegner aus, den du angreifen möchtest.");
		System.out.println("Daraufhin wählst du ein Schiff, mit dem du schießen möchtest");
		System.out.println("und wählst dann Koordinaten auf die du schießen möchtest.");
		System.out.println("Danach wird dir angezeigt, wie viele Schiffsteile du getroffen hast und wie viele Wassertreffer.");
		System.out.println("Danach ist dein Schiff je nach Nachladezeit nicht verfügbar.");
		System.out.println("Der nächste Spieler ist an der Reihe.");


		System.out.println("");
		this.colorPrint.println(EPrintColor.BLUE ,"Drücken sie (n) um nun das Spiel zu starten.");


		String eingabe = IO.readString();
		while(!eingabe.equals("n")){
			this.colorPrint.println(EPrintColor.RED ,"Sie müssen bitte (n) drücken um das Spiel zu starten!");
			eingabe = IO.readString();
		}
		for(int i = 0; i < 10; i++){
			System.out.println("");
		}
	}

}
