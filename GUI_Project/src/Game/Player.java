package Game;
import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import Ships.Corvette;
import Ships.Destroyer;
import Ships.Frigate;
import Ships.Ship;
import Ships.Submarine;
import Tools.ColoredPrint;
/**
 * Write a description of class MatrixTools here.
 * 
 * @author JL, ML
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
	private int totalShips;
	private String playerName;
	private BattleField privateField;
	private BattleField publicField;
	private ColoredPrint colorPrint;
	private boolean isBot;
	private ImageIcon[] destroyerIconH;
	private ImageIcon[] frigateIconH;
	private ImageIcon[] corvetteIconH;
	private ImageIcon[] submarineIconH;
	private ImageIcon[] destroyerIconV;
	private ImageIcon[] frigateIconV;
	private ImageIcon[] corvetteIconV;
	private ImageIcon[] submarineIconV;
	private BattleField_View battleFieldView;

	/**
	 * Konstruktor der Klasse Player
	 * setzt isAlive auf true wenn er erstellt wird.
	 * erstellt alle Schiffe eines Spielers
	 * 
	 */

	public Player(boolean isActive, int totalShips, int destroyer, int frigate,
			int corvette, int submarine, String playerName, BattleField field, boolean isKi) {
		this.isAlive = true;
		this.isActive = isActive;
		this.totalShips = totalShips;
		this.playerName = playerName;
		this.colorPrint = new ColoredPrint();
		this.isBot = isKi;
		//this.battleFieldView;

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

	public boolean isPlayerBot(){
		return this.isBot;
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

	public void setPlayerBot(boolean state){
		this.isBot = state;
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


	public void setBattleFieldView(BattleField_View battlefield){
		this.battleFieldView = battlefield;
	}


	public BattleField_View getBattleFieldView(){
		return battleFieldView;
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
	 * setzt das Schiff auf isReady = false und setzt die NachladeZeit.
	 */

	public void setShipIsntReady(Ship ship){
		if(ship.isReady() == true){
			ship.setReady(false);
			ship.setReloadTimeLeft(ship.getReloadTime());
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


	private void initShipIconH(int cellSize){
		setDestroyerIconsH(cellSize);
		setCorvetteIconsH(cellSize);
		setFrigateIconsH(cellSize);
		setSubmarineIconsH(cellSize);
	}

	private void setDestroyerIconsH(int cellSize){
		this.destroyerIconH = new ImageIcon[5];
		this.destroyerIconH[0] = new ImageIcon("Resources/ShipsNeu/Destroyer/destroyer5.png", "Resources/ShipsNeu/Destroyer/destroyer5.pngg");
		this.destroyerIconH[0].setImage(this.destroyerIconH[0].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 

		this.destroyerIconH[1] = new ImageIcon("Resources/ShipsNeu/Destroyer/destroyer4.png", "Resources/ShipsNeu/Destroyer/destroyer4.png");
		this.destroyerIconH[1].setImage(this.destroyerIconH[1].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 

		this.destroyerIconH[2] = new ImageIcon("Resources/ShipsNeu/Destroyer/destroyer3.png", "Resources/ShipsNeu/Destroyer/destroyer3.png");
		this.destroyerIconH[2].setImage(this.destroyerIconH[2].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 

		this.destroyerIconH[3] = new ImageIcon("Resources/ShipsNeu/Destroyer/destroyer2.png", "Resources/ShipsNeu/Destroyer/destroyer2.png");
		this.destroyerIconH[3].setImage(this.destroyerIconH[3].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 

		this.destroyerIconH[4] = new ImageIcon("Resources/ShipsNeu/Destroyer/destroyer1.png", "Resources/ShipsNeu/Destroyer/destroyer1.png");
		this.destroyerIconH[4].setImage(this.destroyerIconH[4].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 
	}

	private void setFrigateIconsH(int cellSize){
		this.frigateIconH = new ImageIcon[4];
		this.frigateIconH[0] = new ImageIcon("Resources/ShipsNeu/Frigate/frigate4.png", "Resources/ShipsNeu/Frigate/frigate4.png");
		this.frigateIconH[0].setImage(this.destroyerIconH[0].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 

		this.frigateIconH[1] = new ImageIcon("Resources/ShipsNeu/Frigate/frigate3.png", "Resources/ShipsNeu/Frigate/frigate3.png");
		this.frigateIconH[1].setImage(this.frigateIconH[1].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 

		this.frigateIconH[2] = new ImageIcon("Resources/ShipsNeu/Frigate/frigate2.png", "Resources/ShipsNeu/Frigate/frigate2.png");
		this.frigateIconH[2].setImage(this.frigateIconH[2].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 

		this.frigateIconH[3] = new ImageIcon("Resources/ShipsNeu/Frigate/frigate1.png", "Resources/ShipsNeu/Frigate/frigate1.png");
		this.frigateIconH[3].setImage(this.frigateIconH[3].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 
	}

	private void setCorvetteIconsH(int cellSize){
		this.corvetteIconH = new ImageIcon[3];
		this.corvetteIconH[0] = new ImageIcon("Resources/ShipsNeu/Corvette/corvette3.png", "Resources/ShipsNeu/Corvette/corvette3.png");
		this.corvetteIconH[0].setImage(this.corvetteIconH[0].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 

		this.corvetteIconH[1] = new ImageIcon("Resources/ShipsNeu/Corvette/corvette2.png", "Resources/ShipsNeu/Corvette/corvette2.png");
		this.corvetteIconH[1].setImage(this.corvetteIconH[1].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 

		this.corvetteIconH[2] = new ImageIcon("Resources/ShipsNeu/Corvette/corvette1.png", "Resources/ShipsNeu/Corvette/corvette1.png");
		this.corvetteIconH[2].setImage(this.corvetteIconH[2].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 
	}

	private void setSubmarineIconsH(int cellSize){
		this.submarineIconH = new ImageIcon[2];
		this.submarineIconH[0] = new ImageIcon("Resources/ShipsNeu/Submarine/submarine2.png", "Resources/ShipsNeu/Submarine/submarine2.png");
		this.submarineIconH[0].setImage(this.submarineIconH[0].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 

		this.submarineIconH[1] = new ImageIcon("Resources/ShipsNeu/Submarine/submarine1.png", "Resources/ShipsNeu/Submarine/submarine1.png");
		this.submarineIconH[1].setImage(this.submarineIconH[1].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 
	}


	private void initShipIconV(int cellSize){
		setDestroyerIconsV(cellSize);
		setCorvetteIconsV(cellSize);
		setFrigateIconsV(cellSize);
		setSubmarineIconsV(cellSize);
	}

	private void setDestroyerIconsV(int cellSize){
		this.destroyerIconV = new ImageIcon[5];
		this.destroyerIconV[0] = new ImageIcon("Resources/ShipsNeu/Destroyer/destroyer1_verti.png", "Resources/ShipsNeu/Destroyer/destroyer1_verti.png");
		this.destroyerIconV[0].setImage(this.destroyerIconV[0].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 

		this.destroyerIconV[1] = new ImageIcon("Resources/ShipsNeu/Destroyer/destroyer2_verti.png", "Resources/ShipsNeu/Destroyer/destroyer2_verti.png");
		this.destroyerIconV[1].setImage(this.destroyerIconV[1].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 

		this.destroyerIconV[2] = new ImageIcon("Resources/ShipsNeu/Destroyer/destroyer3_verti.png", "Resources/ShipsNeu/Destroyer/destroyer3_verti.png");
		this.destroyerIconV[2].setImage(this.destroyerIconV[2].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 

		this.destroyerIconV[3] = new ImageIcon("Resources/ShipsNeu/Destroyer/destroyer4_verti.png", "Resources/ShipsNeu/Destroyer/destroyer4_verti.png");
		this.destroyerIconV[3].setImage(this.destroyerIconV[3].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 

		this.destroyerIconV[4] = new ImageIcon("Resources/ShipsNeu/Destroyer/destroyer5_verti.png", "Resources/ShipsNeu/Destroyer/destroyer5_verti.png");
		this.destroyerIconV[4].setImage(this.destroyerIconV[4].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 
	}

	private void setFrigateIconsV(int cellSize){
		this.frigateIconV = new ImageIcon[4];
		this.frigateIconV[0] = new ImageIcon("Resources/ShipsNeu/Frigate/frigate1_verti.png", "Resources/ShipsNeu/Frigate/frigate1_verti.png");
		this.frigateIconV[0].setImage(this.frigateIconV[0].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 

		this.frigateIconV[1] = new ImageIcon("Resources/ShipsNeu/Frigate/frigate2_verti.png", "Resources/ShipsNeu/Frigate/frigate2_verti.png");
		this.frigateIconV[1].setImage(this.frigateIconV[1].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 

		this.frigateIconV[2] = new ImageIcon("Resources/ShipsNeu/Frigate/frigate3_verti.png", "Resources/ShipsNeu/Frigate/frigate3_verti.png");
		this.frigateIconV[2].setImage(this.frigateIconV[2].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 

		this.frigateIconV[3] = new ImageIcon("Resources/ShipsNeu/Frigate/frigate4_verti.png", "Resources/ShipsNeu/Frigate/frigate4_verti.png");
		this.frigateIconV[3].setImage(this.frigateIconV[3].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 
	}

	private void setCorvetteIconsV(int cellSize){
		this.corvetteIconV = new ImageIcon[3];
		this.corvetteIconV[0] = new ImageIcon("Resources/ShipsNeu/Corvette/corvette1_verti.png", "Resources/ShipsNeu/Corvette/corvette1_verti.png");
		this.corvetteIconV[0].setImage(this.corvetteIconV[0].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 

		this.corvetteIconV[1] = new ImageIcon("Resources/ShipsNeu/Corvette/corvette2_verti.png", "Resources/ShipsNeu/Corvette/corvette2_verti.png");
		this.corvetteIconV[1].setImage(this.corvetteIconV[1].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 

		this.corvetteIconV[2] = new ImageIcon("Resources/ShipsNeu/Corvette/corvette3_verti.png", "Resources/ShipsNeu/Corvette/corvette3_verti.png");
		this.corvetteIconV[2].setImage(this.corvetteIconV[2].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 
	}

	private void setSubmarineIconsV(int cellSize){
		this.submarineIconV = new ImageIcon[2];
		this.submarineIconV[0] = new ImageIcon("Resources/ShipsNeu/Submarine/submarine1_verti.png", "Resources/ShipsNeu/Submarine/submarine1_verti.png");
		this.submarineIconV[0].setImage(this.submarineIconV[0].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 

		this.submarineIconV[1] = new ImageIcon("Resources/ShipsNeu/Submarine/submarine2_verti.png", "Resources/ShipsNeu/Submarine/submarine2_verti.png");
		this.submarineIconV[1].setImage(this.submarineIconV[1].getImage().getScaledInstance(cellSize,cellSize,Image.SCALE_DEFAULT)); 
	}

	/**
	 * überprüft, ob ein Schiff verfügbar ist.
	 * @param whichShip
	 * @return true wenn whichShip isReady
	 */
	public Destroyer getAvailableDestroyer(){
		for(int i = 0; i < destroyer.length; i++){
			if(destroyer[i].isReady()){
				return destroyer[i];
			}
		}
		return null;
	}

	public Frigate getAvailableFrigate(){
		for(int i = 0; i < frigate.length; i++){
			if(frigate[i].isReady()){
				return frigate[i];
			}
		}
		return null;
	}


	public Corvette getAvailableCorvette(){
		for(int i = 0; i < corvette.length; i++){
			if(corvette[i].isReady()){
				return corvette[i];
			}
		}
		return null;
	}


	public Submarine getAvailableSubmarine(){
		for(int i = 0; i < submarine.length; i++){
			if(submarine[i].isReady()){
				return submarine[i];
			}
		}
		return null;
	}



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


	public void saveShipCoordinatesH(int x, int y, Ship ship){
		int length = ship.getShipSize();
		int[][] coordinates = new int[2][length];
		int[] startCoordsAndOrientation = new int[]{x, y, 0};

		for(int i = 0; i < length; i++){
			coordinates[0][i] = x+i;
			coordinates[1][i] = y;
		}
		ship.setCoordinates(coordinates);
		ship.setStartCoordsAndOrientation(startCoordsAndOrientation);
	}

	/**
	 * Weist jedem Schiff seine Koordinaten zu (vertikal)
	 * @param x
	 * @param y
	 * @param length
	 * @param shipNumber
	 */

	public void saveShipCoordinatesV(int x, int y, Ship ship){
		int length = ship.getShipSize();
		int[][] coordinates = new int[2][length];
		int[] startCoordsAndOrientation = new int[]{x, y, 1};

		for(int i = 0; i < length; i++){
			coordinates[0][i] = x;
			coordinates[1][i] = y+i;
		}
		ship.setCoordinates(coordinates);
		ship.setStartCoordsAndOrientation(startCoordsAndOrientation);
	}

	/**
	 * Überprüft, ob ein Schiff gesunken ist.
	 * @param x
	 * @param y
	 * @param shipSymbol
	 * @return true wenn das Schiff untergegangen ist.
	 */

	public boolean checkIfSunk(String shipSymbol){
		if(shipSymbol.equals("D")){
			for(int i = 0; i < destroyer.length; i++){
				if(destroyer[i].checkIfIsSwimming() == false){
					System.out.println("Herzlichen Glückwunsch, du hast den Zerstörer von " + this.playerName + " versenkt.");
					this.totalShips--;
					return true;
				}
			}
		}else if(shipSymbol.equals("F")){
			for(int i = 0; i < frigate.length; i++){
				if(frigate[i].checkIfIsSwimming() == false){
					System.out.println("Herzlichen Glückwunsch, du hast die Frigatte von " + this.playerName + " versenkt.");

					this.totalShips--;
					return true;
				}
			}
		}else if(shipSymbol.equals("C")){
			for(int i = 0; i < corvette.length; i++){
				if(corvette[i].checkIfIsSwimming() == false){
					System.out.println("Herzlichen Glückwunsch, du hast die Corvette von " + this.playerName + " versenkt.");
					this.totalShips--;

					return true;
				}
			}
		}else if(shipSymbol.equals("S")){
			for(int i = 0; i < submarine.length; i++){
				if(submarine[i].checkIfIsSwimming() == false){
					System.out.println("Herzlichen Glückwunsch, du hast das U-Boot von " + this.playerName + " versenkt.");

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
		for(int i = 0; i < this.destroyer.length; i++){
			if(this.destroyer[i].isReady()){
				return true;
			}
		}
		for(int i = 0; i < this.frigate.length; i++){
			if(this.frigate[i].isReady()){
				return true;
			}
		}
		for(int i = 0; i < this.corvette.length; i++){
			if(this.corvette[i].isReady()){
				return true;
			}
		}
		for(int i = 0; i < this.submarine.length; i++){
			if(this.submarine[i].isReady()){
				return true;
			}
		}
		return false;
	}

	public void printShipOnField(Ship ship, BattleField_View battleFieldView){
		int length = ship.getShipSize();
		int[] coords = ship.getStartCoordsAndOrientation();
		int x = coords[0];
		int y = coords[1];
		int orientation = coords[2];
		JButton[][] field = battleFieldView.getBattleField();
		initShipIconH(battleFieldView.getCellSize());
		initShipIconV(battleFieldView.getCellSize());
		
		if(orientation == 0){
			if(ship instanceof Destroyer){
				for(int i = 0; i < length; i++){
					field[x+i-1][y-1].setIcon(destroyerIconH[i]);
				}
			}
			else if(ship instanceof Frigate){
				for(int i = 0; i < length; i++){
					field[x+i-1][y-1].setIcon(frigateIconH[i]);
				}
			}
			else if(ship instanceof Corvette){
				for(int i = 0; i < length; i++){
					field[x+i-1][y-1].setIcon(corvetteIconH[i]);
				}
			}
			else if(ship instanceof Submarine){
				for(int i = 0; i < length; i++){
					field[x+i-1][y-1].setIcon(submarineIconH[i]);
				}
			}


		}
		else{
			if(ship instanceof Destroyer){
				for(int i = 0; i < length; i++){
					field[x-1][y+i-1].setIcon(destroyerIconV[i]);
				}
			}
			else if(ship instanceof Frigate){
				for(int i = 0; i < length; i++){
					field[x-1][y+i-1].setIcon(frigateIconV[i]);
				}
			}
			else if(ship instanceof Corvette){
				for(int i = 0; i < length; i++){
					field[x-1][y+i-1].setIcon(corvetteIconV[i]);
				};
			}
			else if(ship instanceof Submarine){
				for(int i = 0; i < length; i++){
					field[x-1][y+i-1].setIcon(submarineIconV[i]);
				}
			}
			
		}
		privateField.printPrivateField(field, playerName);
	}

	public void printPublicField(){
		publicField.printPublicField(playerName);
	}

	public boolean checkIfShipIsReady(String shipSymbol){

		switch(shipSymbol){
		case "D":
			for(int i = 0; i < destroyer.length; i++){
				if(destroyer[i].checkIfIsSwimming()){
					if(destroyer[i].isReady()){
						return true;
					}
				}
			}
			break;
		case "F":
			for(int i = 0; i < frigate.length; i++){
				if(frigate[i].checkIfIsSwimming()){
					if(frigate[i].isReady()){
						return true;
					}
				}
			}
			break;
		case "C":
			for(int i = 0; i < corvette.length; i++){
				if(corvette[i].checkIfIsSwimming()){
					if(corvette[i].isReady()){
						return true;
					}
				}
			}
			break;
		case "S":
			for(int i = 0; i < submarine.length; i++){
				if(submarine[i].checkIfIsSwimming()){
					if(submarine[i].isReady()){
						return true;
					}
				}
			}
			break;
		}
		return false;
	}


}
