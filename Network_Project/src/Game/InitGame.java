package Game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import KI.ArtificialIntelligence;
import SaveGame.Load;
import Ships.Corvette;
import Ships.Destroyer;
import Ships.Frigate;
import Ships.Ship;
import Ships.Submarine;
import Tools.ColoredPrint;
import Tools.ColoredPrint.EPrintColor;

public class InitGame implements Serializable{


	private static final long serialVersionUID = 988834907748712441L;

	private Load load = new Load();
	private Player[] player;
	private ColoredPrint colorPrint = new ColoredPrint();
	private int fieldSize;
	String[] listFileNames;
	private Round rounds;
	private int playerId;
	private Ship choosenShip;
	private Destroyer[] destroyer;
	private Frigate[] frigate;
	private Corvette[] corvette;
	private Submarine[] submarine;
	//private boolean placingShip = false;
	private int totalShips;
	private int destroyerCount;
	private int frigateCount;
	private int corvetteCount;
	private int submarineCount;
	private char orientation;
	private int mouseY;
	private int oldBtnY;
	private boolean shipCanBePlaced;
	private int ki;
	// AI ÄNDERUNG START
	private ArtificialIntelligence ai = new ArtificialIntelligence(); 
	// AI ÄNDERUNG ENDE

	public InitGame(int player, int destroyer, int frigate, int corvette, int submarine, int fieldSize, int ki){
		this.playerId = 0;
		this.orientation = 'h';
		this.mouseY = 0;
		this.oldBtnY = 0;
		this.shipCanBePlaced = false;
		this.player = new Player[player];
		this.fieldSize = fieldSize;
		this.destroyerCount = destroyer;
		this.frigateCount = frigate;
		this.corvetteCount = corvette;
		this.submarineCount = submarine;
		this.ki = ki;
		this.totalShips = destroyer + frigate + corvette + submarine + fieldSize;

		this.configureGame();
	}

	public Player[] getPlayer() {
		return player;
	}

	public int getFieldSize(){
		return this.fieldSize;
	}

	public void configureGame(){
		if(playerId < player.length){
			initPlayerBattleShip();
		}
	}

	public void incrementPlayerId(){
		this.playerId++;
	}

	public int getPlayerId(){
		return this.playerId;
	}

	public void initPlayerBattleShip(){

		int i = this.playerId;
		BattleField battlefield = new BattleField(this.fieldSize);
		boolean isActive;
		boolean isKi = false;

		if((this.player.length - this.ki) <= i) {
			isKi = true;
		}

		if(i == 0){
			isActive = true;
		}
		else{
			isActive = false;
		}

		player[i] = new Player(isActive, this.totalShips, this.destroyerCount, 
				this.frigateCount, this.corvetteCount,this.submarineCount, ("Spieler " + (i+1) ), battlefield, isKi);

		if(player[i].isPlayerBot()){
			setShipsToFieldForAI(i);
		}
		
		this.initShips();
	}

	// AI ÄNDERUNG START
	private void setShipsToFieldForAI(int i){

		Destroyer destroyer[] = player[i].getDestroyer();
		Frigate frigate[] = player[i].getFrigate();
		Corvette corvette[] = player[i].getCorvette();
		Submarine submarine[] = player[i].getSubmarine();

		//ZERSTÖRER
		for(int d = 0; d < destroyer.length; d++){

			boolean checked;
			checked = false;
		
			while(!(checked)){
				String pos;
				pos = ai.setShip(this.fieldSize);
				orientation = ai.setShipOrientation();
				checked = setShipToField("destroyer", i, pos);
			}
		}

		//FREGATTE
		for(int f = 0; f < frigate.length; f++){

			boolean checked;
			checked = false;
		
			while(!(checked)){
				String pos;
				pos = ai.setShip(this.fieldSize);
				orientation = ai.setShipOrientation();
				checked = setShipToField("frigate", i, pos);
				
			}

		}

		//KORVETTE
		for(int k = 0; k < corvette.length; k++){

			boolean checked;
			checked = false;
		
			while(!(checked)){
				String pos;
				pos = ai.setShip(this.fieldSize);
				orientation = ai.setShipOrientation();
				checked = setShipToField("corvette", i, pos);
				
			}

		}

		//UBOOT
		for(int s = 0; s < submarine.length; s++){
			
			boolean checked;
			checked = false;
		
			while(!(checked)){
				String pos;
				pos = ai.setShip(this.fieldSize);
				orientation = ai.setShipOrientation();
				checked = setShipToField("submarine", i, pos);
			}

		}
	}
	// AI ÄNDERUNG ENDE
	
	/**
	 *  Positionierung der Schiffe von jedem Spieler (nacheinander)
	 */
	private void initShips(){
		this.destroyer = player[playerId].getDestroyer();
		this.frigate = player[playerId].getFrigate();
		this.corvette = player[playerId].getCorvette();
		this.submarine = player[playerId].getSubmarine();

		this.destroyerCount = this.destroyer.length;
		this.frigateCount = this.frigate.length;
		this.corvetteCount = this.corvette.length;
		this.submarineCount = this.submarine.length;

		this.totalShips = this.destroyerCount + this.frigateCount + this.corvetteCount + this.submarineCount;

		String name = player[playerId].getPlayerName();
		boolean start = true;

		if(playerId > 0){
			start = false;
		}
	}

	public boolean setShipToField(String shipName, int i, String pos) {
		int length = 0;
		if(shipName.equals("destroyer")){
			length = Destroyer.length;
		}
		else if(shipName.equals("frigate")){
			length = Frigate.length;
		}
		else if(shipName.equals("corvette")){
			length = Corvette.length;
		}
		else if(shipName.equals("submarine")){
			length = Submarine.length;
		}

		int[] koordinaten = checkPos(pos);

		if(player[i].getPrivateField().setShips(length, koordinaten[0], koordinaten[1], orientation) == true){
			if(orientation == 'h'){
				player[i].saveShipCoordinatesH(koordinaten[0], koordinaten[1], length);
			}
			else{
				player[i].saveShipCoordinatesV(koordinaten[0], koordinaten[1], length);
			}
			System.out.println("Schiff gesetzt!!!");
			return true;
		}
		else{
			this.colorPrint.println(EPrintColor.RED, "Schiff kann dort nicht positioniert werden!\nBitte erneut Koordinaten eingeben");
			return false;
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
