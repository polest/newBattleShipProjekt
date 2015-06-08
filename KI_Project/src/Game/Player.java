package Game;
import java.io.Serializable;

import Ships.Corvette;
import Ships.Destroyer;
import Ships.Frigate;
import Ships.Submarine;
import Tools.ColoredPrint;
import Tools.ColoredPrint.EPrintColor;

/**
 * Write a description of class MatrixTools here.
 * 
 * @author JL
 * @version 07.05.14
 */

public class Player implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7380768092350433487L;

	private Destroyer[] destroyer;
	private Frigate[] frigate;
	private Corvette[] corvette;
	private Submarine[] submarine;
	private boolean isActive;
	private boolean isAlive;
	private boolean isBot;
	private int totalShips;
	private String playerName;
	private BattleField privateField;
	private BattleField publicField;
	private ColoredPrint colorPrint;
	private int enemyNumber;

	/**
	 * Konstruktor der Klasse Player
	 * setzt isAlive auf true wenn er erstellt wird.
	 * erstellt alle Schiffe eines Spielers
	 * 
	 */

	public Player(boolean isActive, int totalShips, int destroyer, int frigate,
			int corvette, int submarine, String playerName, BattleField field, boolean isBot) {
		this.isAlive = true;
		this.isActive = isActive;
		this.isBot = isBot;
		this.totalShips = totalShips;
		this.playerName = playerName;
		this.colorPrint = new ColoredPrint();
		this.enemyNumber = 0;
		
		this.destroyer = new Destroyer[destroyer];
		for(int i = 0; i < destroyer; i++){
			this.destroyer[i] = new Destroyer();
		}
		this.frigate = new Frigate[frigate];
		for(int i = 0; i < frigate; i++){
			this.frigate[i] = new Frigate();
		}
		this.corvette = new Corvette[corvette];
		for(int i = 0; i < corvette; i++){
			this.corvette[i] = new Corvette();
		}
		this.submarine = new Submarine[submarine];
		for(int i = 0; i < submarine; i++){
			this.submarine[i] = new Submarine();
		}
		this.privateField = field;
		this.publicField = new BattleField(field.getSize());

	}

	
	public int getEnemyNumber() {
		return enemyNumber;
	}


	public void setEnemyNumber(int enemyNumber) {
		this.enemyNumber = enemyNumber;
	}
	
	
	
	public boolean isBot() {
		return isBot;
	}


	public void setBot(boolean isBot) {
		this.isBot = isBot;
	}


	/**
	 * Getter für totalShips
	 * @return
	 */

	public int getTotalShips() {
		return totalShips;
	}
	
	
	/**
	 * Getter für den Playernamen
	 * @return
	 */

	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Setter Playername
	 * @param playerName
	 */

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * setter isActive
	 * @param isActive
	 */

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * Setter isAlive
	 * @param isAlive
	 */

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	
	
	/**
	 * getter für ein openField
	 * @return
	 */

	/**
	 * @return Gibt das öffentliche Spielfeld des Spielers zurück
	 */
	public BattleField getPublicField() {
		return this.publicField;
	}

	/**
	 * @return Gibt das private Spielfeld des Spielers zurück
	 */
	public BattleField getPrivateField() {
		return this.privateField;
	}

	/**
	 * @param field setzt das private Spielfeld des Spielers
	 */
	public void setPrivateField(BattleField field) {
		this.privateField = field;
	}

	/**
	 * @param field setzt das öffentliche Spielfeld des Spielers
	 */
	public void setPublicField(BattleField field) {
		this.publicField = field;
	}

	/**
	 * Getter isActive
	 * @return
	 */

	public boolean getIsActive(){
		return this.isActive;
	}

	/**
	 * Getter isAlive
	 * @return
	 */

	public boolean getIsAlive(){
		if(this.totalShips > 0){
			this.isAlive = true;
		}
		else{
			this.isAlive = false;
		}
		
		return this.isAlive;
	}

	/**
	 * @return Gibt die Anzahl an Zerstörer an, die der Spieler besitzt
	 */
	public Destroyer[] getDestroyer() {
		return destroyer;
	}

	/**
	 * @return Gibt die Anzahl an Fregatten an, die der Spieler besitzt
	 */
	public Frigate[] getFrigate() {
		return frigate;
	}

	/**
	 * @return Gibt die Anzahl an Korvetten an, die der Spieler besitzt
	 */
	public Corvette[] getCorvette() {
		return corvette;
	}

	/**
	 * @return Gibt die Anzahl an UBooten an, die der Spieler besitzt
	 */
	public Submarine[] getSubmarine() {
		return submarine;
	}

	/**
	 * setzt den Zerstörer auf isReady = false und setzt die NachladeZeit.
	 */

	public void setDestroyerIsntReady(){
		int counter = 0;
		for(int i = 0; i < this.destroyer.length; i++){
			if(counter == 0){
				if(destroyer[i].isReady() == true){
					destroyer[i].setReady(false);
					destroyer[i].setReloadTimeLeft(destroyer[i].getReloadTime());
					counter++;
				}
			}

		}
	}


	/**
	 * Zählt die NachladeZeit herunter
	 */

	public void reloadTimeCountdown(){
		for(int i = 0; i < destroyer.length; i++){
			if(destroyer[i].getReloadTimeLeft() > 0){
				destroyer[i].setReloadTimeLeft(destroyer[i].getReloadTimeLeft()-1);
			}else{
				destroyer[i].setReady(true);
			}
		}
		for(int i = 0; i < frigate.length; i++){
			if(frigate[i].getReloadTimeLeft() > 0){
				frigate[i].setReloadTimeLeft(frigate[i].getReloadTimeLeft()-1);
			}else{
				frigate[i].setReady(true);
			}
		}
		for(int i = 0; i < corvette.length; i++){
			if(corvette[i].getReloadTimeLeft() > 0){
				corvette[i].setReloadTimeLeft(corvette[i].getReloadTimeLeft()-1);
			}else{
				corvette[i].setReady(true);
			}
		}
		for(int i = 0; i < submarine.length; i++){
			if(submarine[i].getReloadTimeLeft() > 0){
				submarine[i].setReloadTimeLeft(submarine[i].getReloadTimeLeft()-1);
			}else{
				submarine[i].setReady(true);
			}
		}
	}


	/**
	 * setzt die Frigatte auf isReady = false und setzt die NachladeZeit.
	 */

	public void setFrigateIsntReady(){
		int counter = 0;
		for(int i = 0; i < this.frigate.length; i++){
			if(counter == 0){
				if(frigate[i].isReady() == true){
					frigate[i].setReady(false);
					frigate[i].setReloadTimeLeft(frigate[i].getReloadTime());
					counter++;
				}
			}
		}
	}

	/**
	 * Setzt die Corvette auf inn´t ready
	 * und setzt die Nachladezeit
	 */

	public void setCorvetteIsntReady(){
		int counter = 0;
		for(int i = 0; i < this.corvette.length; i++){
			if(counter == 0){
				if(corvette[i].isReady() == true){
					corvette[i].setReady(false);
					corvette[i].setReloadTimeLeft(corvette[i].getReloadTime());
					counter++;
				}
			}

		}
	}

	/**
	 * setzt das U-Boot auf nicht ready
	 * und setzt die Nachladezeit
	 */

	public void setSubmarineIsntReady(){
		int counter = 0;
		for(int i = 0; i < this.submarine.length; i++){
			if(counter == 0){
				if(submarine[i].isReady() == true){
					submarine[i].setReady(false);
					submarine[i].setReloadTimeLeft(submarine[i].getReloadTime());
					counter++;
				}
			}

		}
	}



	/**
	 * überprüft, ob ein Schiff verfügbar ist.
	 * @param whichShip
	 * @return true wenn whichShip isReady
	 */

	public boolean isAvailable(int whichShip){
		if(whichShip == 1){
			for(int i = 0; i < destroyer.length; i++){
				if(destroyer[i].isReady()){
					return true;
				}
			}
		}else if(whichShip == 2){
			for(int i = 0; i < frigate.length; i++){
				if(frigate[i].isReady()){
					return true;
				}
			}
		}else if(whichShip == 3){
			for(int i = 0; i < corvette.length; i++){
				if(corvette[i].isReady()){
					return true;
				}
			}
		}else if(whichShip == 4){
			for(int i = 0; i < submarine.length; i++){
				if(submarine[i].isReady()){
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Weist jedem Schiff seine Koordinaten zu (Horizontal)
	 * @param x
	 * @param y
	 * @param length
	 * @param shipNumber
	 */


	public void saveShipCoordinatesH(int x, int y, int length, int shipNumber){
		int[][] coordinates = new int[2][length];
		for(int i = 0; i < length; i++){
			coordinates[0][i] = x+i;
			coordinates[1][i] = y;
		}
		if(length == 5){
			destroyer[shipNumber].setCoordinates(coordinates);
		}else if(length == 4){
			frigate[shipNumber].setCoordinates(coordinates);
		}else if(length == 3){
			corvette[shipNumber].setCoordinates(coordinates);
		}else if(length == 2){
			submarine[shipNumber].setCoordinates(coordinates);
		}

	}

	/**
	 * Weist jedem Schiff seine Koordinaten zu (vertikal)
	 * @param x
	 * @param y
	 * @param length
	 * @param shipNumber
	 */

	public void saveShipCoordinatesV(int x, int y, int length, int shipNumber){
		int[][] coordinates = new int[2][length];
		for(int i = 0; i < length; i++){
			coordinates[0][i] = y+i;
			coordinates[1][i] = x;
		}
		if(length == 5){
			destroyer[shipNumber].setCoordinates(coordinates);
		}else if(length == 4){
			frigate[shipNumber].setCoordinates(coordinates);
		}else if(length == 3){
			corvette[shipNumber].setCoordinates(coordinates);
		}else if(length == 2){
			submarine[shipNumber].setCoordinates(coordinates);
		}
	}

	/**
	 * Überprüft, ob ein Schiff gesunken ist.
	 * @param x
	 * @param y
	 * @param shipSymbol
	 * @return true wenn das Schiff untergegangen ist.
	 */

	public boolean checkIfSunk(String shipSymbol, boolean message){
		if(shipSymbol.equals("D")){
			for(int i = 0; i < destroyer.length; i++){
				if(destroyer[i].checkIfIsSwimming() == false){
					if(message){
						this.colorPrint.println(EPrintColor.BLUE,"Herzlichen Glückwunsch, du hast den Zerstörer von " + this.playerName + " versenkt.");
					}
					this.totalShips--;
					return true;
				}
			}
		}else if(shipSymbol.equals("F")){
			for(int i = 0; i < frigate.length; i++){
				if(frigate[i].checkIfIsSwimming() == false){
					this.colorPrint.println(EPrintColor.BLUE,"Herzlichen Glückwunsch, du hast die Frigatte von " + this.playerName + " versenkt.");

					this.totalShips--;
					return true;
				}
			}
		}else if(shipSymbol.equals("C")){
			for(int i = 0; i < corvette.length; i++){
				if(corvette[i].checkIfIsSwimming() == false){
					this.colorPrint.println(EPrintColor.BLUE, "Herzlichen Glückwunsch, du hast die Corvette von " + this.playerName + " versenkt.");
					this.totalShips--;

					return true;
				}
			}
		}else if(shipSymbol.equals("S")){
			for(int i = 0; i < submarine.length; i++){
				if(submarine[i].checkIfIsSwimming() == false){
					this.colorPrint.println(EPrintColor.BLUE,"Herzlichen Glückwunsch, du hast das U-Boot von " + this.playerName + " versenkt.");

					this.totalShips--;

					return true;
				}
			}
		}
		return false;
	}

	

	/**
	 * Überprüft, ob ein Spieler überhaupt ein Schiff zur verfügung hat.
	 * @return
	 */

	public boolean checkIfAnyShipIsReady(){
		for(int i = 0; i < destroyer.length; i++){
			if(destroyer[i].isReady()){
				return true;
			}
		}
		for(int i = 0; i < frigate.length; i++){
			if(frigate[i].isReady()){
				return true;
			}
		}
		for(int i = 0; i < corvette.length; i++){
			if(corvette[i].isReady()){
				return true;
			}
		}
		for(int i = 0; i < submarine.length; i++){
			if(submarine[i].isReady()){
				return true;
			}
		}
		return false;
	}
	
	public void printPrivateField(){
		privateField.printPrivateField(playerName);
	}
	
	public void printPublicField(){
		publicField.printPublicField(playerName);
	}
	
	public boolean checkIfShipIsReady(String shipSymbol){
		
		switch(shipSymbol){
		case "D":
			for(int i = 0; i < destroyer.length; i++){
				if(destroyer[i].isReady()){
					return true;
				}
			}
			break;
		case "F":
			for(int i = 0; i < frigate.length; i++){
				if(frigate[i].isReady()){
					return true;
				}
			}
			break;
		case "C":
			for(int i = 0; i < corvette.length; i++){
				if(corvette[i].isReady()){
					return true;
				}
			}
			break;
		case "S":
			for(int i = 0; i < submarine.length; i++){
				if(submarine[i].isReady()){
					return true;
				}
			}
			break;
		}
		
		return false;
	}


}
