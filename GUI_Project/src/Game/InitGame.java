package Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JComboBox;
import javax.swing.JPanel;

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
	private Options_View gameOptionsView;
	private Load load = new Load();
	private Player[] player;
	private ColoredPrint colorPrint = new ColoredPrint();
	private int fieldSize;


	public InitGame(boolean start, int width, int height){
		if(start){
			this.gameOptions = new Options(width, height);
			//Round rounds = new Round(this.player, this.fieldSize);
			//rounds.play();
		}else{
			System.out.println("Bitte geben sie den Namen von ihrem Spiel ein, welches sie laden möchten.");
			String eingabe = IO.readString();
			load.loadGame(eingabe);
			this.player = load.getPlayer();
			this.fieldSize = player[0].getPrivateField().getSize();
			Round rounds = new Round(this.player,this.fieldSize);
		}

	}


	public JPanel getOptionsPanel(){
		return this.gameOptions.getPanel();
	}

	public Player[] getPlayer() {
		return player;
	}

	private void configureGame(){
		this.player = new Player[this.gameOptions.getPlayer()];
		this.fieldSize = this.gameOptions.getBattlefieldSize();


		for(int i = 0; i < player.length; i++){
			BattleField battlefield = new BattleField(fieldSize);
			
			if(i == 0){
				player[i] = new Player(true, this.gameOptions.getTotalShips(), this.gameOptions.getDestroyer(), 
						this.gameOptions.getFrigate(), this.gameOptions.getCorvette(),this.gameOptions.getSubmarine(),this.gameOptions.getPlayerNames()[i], battlefield, false);
			}else{
				player[i] = new Player(false, this.gameOptions.getTotalShips(), this.gameOptions.getDestroyer(), 
						this.gameOptions.getFrigate(), this.gameOptions.getCorvette(),this.gameOptions.getSubmarine(),this.gameOptions.getPlayerNames()[i], battlefield, this.gameOptions.getPlayerKi(i-1));
			}

		}

		//this.setShipsToField();

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

					String pos = IO.readString();
					int[] koordinaten = checkPos(pos);

					if(koordinaten == null){
						this.colorPrint.println(EPrintColor.RED, "Fehler in der Eingabe! (X, Y)");
					}
					else{
						IO.println("Horizontal h \nVertikal v");
						char orientation = IO.readChar();

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

					String pos = IO.readString();
					int[] koordinaten = checkPos(pos);

					if(koordinaten == null){
						this.colorPrint.println(EPrintColor.RED, "Fehler in der Eingabe! (X, Y)");
					}
					else{
						IO.println("Horizontal h \nVertikal v");
						char orientation = IO.readChar();

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

					String pos = IO.readString();
					int[] koordinaten = checkPos(pos);

					if(koordinaten == null){
						this.colorPrint.println(EPrintColor.RED,"Fehler in der Eingabe! (X, Y)");
					}
					else{
						IO.println("Horizontal h \nVertikal v");
						char orientation = IO.readChar();

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

					String pos = IO.readString();
					int[] koordinaten = checkPos(pos);

					if(koordinaten == null){
						this.colorPrint.println(EPrintColor.RED,"Fehler in der Eingabe! (X, Y)");
					}
					else{
						IO.println("Horizontal h \nVertikal v");
						char orientation = IO.readChar();

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
				System.out.println("Sie müssen eine dieser Auswahlmöglichkeiten wählen.");
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
			this.colorPrint.println(EPrintColor.RED, "Ungültige Eingabe");

		}
		return null;
	}

	private void addOkListener(){
		this.gameOptionsView.setOkSelectionListener(new SetOkListener() );
	}

	private class SetOkListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			configureGame();
		}
	}

}
