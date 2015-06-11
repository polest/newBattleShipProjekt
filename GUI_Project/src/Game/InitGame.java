package Game;

import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import SaveGame.Load;
import Ships.Corvette;
import Ships.Destroyer;
import Ships.Frigate;
import Ships.Ship;
import Ships.Submarine;
import Tools.ColoredPrint;
import Tools.ColoredPrint.EPrintColor;
import Tools.IO;

public class InitGame implements Serializable{

	/**
	 * @author ML
	 * @version 21.03.15
	 */
	private static final long serialVersionUID = 988834907748712441L;

	private Options gameOptions;
	private Options_View gameOptionsView;
	private Load load = new Load();
	private Player[] player;
	private ColoredPrint colorPrint = new ColoredPrint();
	private int fieldSize;
	String[] listFileNames;
	private Round rounds;


	public InitGame(Options_View optionsView, Options gameOptions){
		this.gameOptionsView = optionsView;
		this.gameOptions = gameOptions;
		this.configureGame();
		//if(start){
		//this.gameOptions = new Options(width, height);
		//addOkListener();
		//Round rounds = new Round(this.player, this.fieldSize);
		//rounds.play();
		/*}else{
			System.out.println("Bitte geben sie den Namen von ihrem Spiel ein, welches sie laden möchten.");
			String eingabe = IO.readString();
			load.loadGame(eingabe);
			this.player = load.getPlayer();
			this.fieldSize = player[0].getPrivateField().getSize();
			Round rounds = new Round(this.player,this.fieldSize);

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
		 */

	}


	public JPanel getOptionsPanel(){
		return this.gameOptions.getPanel();
	}

	public Player[] getPlayer() {
		return player;
	}

	public void configureGame(){
		this.player = new Player[this.gameOptions.getPlayer()];
		this.fieldSize = this.gameOptions.getBattlefieldSize();


		for(int i = 0; i < player.length; i++){
			BattleField battlefield = new BattleField(this.fieldSize);

			if(i == 0){
				player[i] = new Player(true, this.gameOptions.getTotalShips(), this.gameOptions.getDestroyer(), 
						this.gameOptions.getFrigate(), this.gameOptions.getCorvette(),this.gameOptions.getSubmarine(),this.gameOptions.getPlayerNames()[i], battlefield, false);
			}else{
				player[i] = new Player(false, this.gameOptions.getTotalShips(), this.gameOptions.getDestroyer(), 
						this.gameOptions.getFrigate(), this.gameOptions.getCorvette(),this.gameOptions.getSubmarine(),this.gameOptions.getPlayerNames()[i], battlefield, this.gameOptions.getPlayerKi(i-1));
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

			/*
			 * Das Spielfeld des Spieler wird auf der Konsole ausgedruckt
			 * und seine Schiffe vorbereitet
			 */
			//IO.println(player[i].getPlayerName() + " : ");
			player[i].getPrivateField().printPrivateField(player[i].getPlayerName());

			Destroyer destroyer[] = player[i].getDestroyer();
			Frigate frigate[] = player[i].getFrigate();
			Corvette corvette[] = player[i].getCorvette();
			Submarine submarine[] = player[i].getSubmarine();

			IO.println("Spieler \"" + player[i].getPlayerName() + "\" ist an der Reihe. \n Bitte geben sie die Koordinaten ein (X,Y)");

			/*
			 * der Benutzer aufgefordert seine Schiffe zu positionieren
			 * Solange wie die eingegebenen Koordinaten ungültig sind,
			 * wird erneut aufgefordert das Schiff zu positionieren
			 */

			//ZERSTÖRER
			for(int d = 0; d < destroyer.length; d++){
				int id = d+1;
				IO.println("Zerstörer (" + id + ")");

				setShipToField(destroyer[d], player[i]);

			}

			//FREGATTE
			for(int f = 0; f < frigate.length; f++){
				int id = f+1;
				IO.println("Fregatte (" + id + ")");
				setShipToField(frigate[f], player[i]);
			}

			//KORVETTE
			for(int k = 0; k < corvette.length; k++){
				int id = k+1;
				IO.println("Korvette (" + id + ")");
				setShipToField(corvette[k], player[i]);
			}

			//UBOOT
			for(int s = 0; s < submarine.length; s++){
				int id = s+1;
				IO.println("UBoot (" + id + ")");
				setShipToField(submarine[s], player[i]);
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
		rounds = new Round(this.player, this.fieldSize);
		rounds.play();

	}

	private void setShipToField(Ship ship, Player player) {
		boolean checked = false;

		while(checked == false){

			String pos = IO.readString();
			int[] koordinaten = checkPos(pos);

			if(koordinaten == null){
				this.colorPrint.println(EPrintColor.RED, "Fehler in der Eingabe! (X, Y)");
			}
			else{
				IO.println("Horizontal h \nVertikal v");
				char orientation = IO.readChar();

				while(orientation != 'h' && orientation != 'v'){
					this.colorPrint.println(EPrintColor.RED,"Fehler! Bitte h oder v eingeben!");
					orientation = IO.readChar();
				}

				if(orientation == 'h'){
					player.saveShipCoordinatesH(koordinaten[0], koordinaten[1], ship);
				}
				else{
					player.saveShipCoordinatesV(koordinaten[0], koordinaten[1], ship);
				}

				if(player.getPrivateField().setShips(ship, koordinaten[0], koordinaten[1], orientation) == true){
					player.getPrivateField().printPrivateField(player.getPlayerName());
					checked = true;
				}
				else{
					this.colorPrint.println(EPrintColor.RED, "Schiff kann dort nicht positioniert werden!\nBitte erneut Koordinaten eingeben");
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
			this.colorPrint.println(EPrintColor.RED, "Ungültige Eingabe");

		}
		return null;
	}

}
