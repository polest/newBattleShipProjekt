package Game;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JLabel;

import Ships.Ship;
import Tools.MatrixTools;



public class BattleField implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5337826198270063377L;
	private int[][] field;
	private int size;
	private boolean isBlocked;
	private boolean isFree;
	private MatrixTools matrixTools;
	private String fieldId;

	/**
	 * Konstruktor eins Spielfeldes
	 * @param size Seitengröße des Spielfeldes (quadratisch)
	 */
	public BattleField(int size) {
		this.size = size;
		this.field = new int[this.size+1][this.size+1];
		this.matrixTools = new MatrixTools();
		initField();
	}


	/**
	 * Initialisiert Matrix in ausgewälter größe und platziert 
	 * Tilden als Startwerte (Leeres Spielfeld)
	 */
	private void initField(){
		field = matrixTools.initMatrix(field);
	}

	/**
	 * @return gibt das Spielfeld zurück
	 */
	public int[][] getField() {
		return this.field;
	}

	/**
	 * @return gibt die Größe des Spielfeldes zurück
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * @return gibt die Id des Spielfeldes zurück
	 */
	public String getId() {
		return this.fieldId;
	}

	/**
	 * @return gibt zurück, ob das Spielfeld blockiert ist oder nicht
	 */
	public boolean isBlocked() {
		return this.isBlocked;
	}

	/**
	 * @return gibt zurück, ob ein Feld frei ist, also ob 
	 * auf den umliegenden Feldern ein Schiff ist oder nicht.
	 */
	public boolean isFree() {
		return this.isFree;
	} 

	/**
	 * Druckt das private Spielfeld auf der Konsole aus
	 */
	public void printPrivateField(String name){
		matrixTools.printPrivateField(this.field,name);

	}

	/**
	 * Druckt das öffentliche Spielfeld auf der Konsole aus
	 */
	public void printPublicField(String name){
		matrixTools.printPublicField(this.field, name);
	}

	/**
	 * @param String  - setzt die Id des Spielfeldes 
	 */
	public void setId(String id) {
		this.fieldId = id;
	}

	/**
	 * Setzen der Schiffe auf dem Spielfeld, sofern möglich
	 * @param ship - Schifftyp
	 * @param iPos - X-Koordinaten des Startfeldes
	 * @param jPos - Y-Koordinate des Startfeldes
	 * @param orientation - Ausrichtung v oder h
	 * @return gibt zurück ob das Schiff platziert werden konnte
	 */
	public boolean checkFree(int length, int iPos, int jPos, char orientation){
		return matrixTools.checkPrivateFields(iPos, jPos, orientation, field, length);
	}


	/**
	 * Setzen der Schiffe auf dem Spielfeld, sofern möglich
	 * @param ship - Schifftyp
	 * @param iPos - X-Koordinaten des Startfeldes
	 * @param jPos - Y-Koordinate des Startfeldes
	 * @param orientation - Ausrichtung v oder h
	 * @return gibt zurück ob das Schiff platziert werden konnte
	 */
	public boolean setShips(int length, int iPos, int jPos, char orientation){
		boolean isFree = true;
		if( (isFree = matrixTools.checkPrivateFields(iPos, jPos, orientation, field, length)) == true){
			field = matrixTools.addMatrix(iPos, jPos, orientation, field, length);
		}
		return isFree;

	}



	/**
	 * Attacke auf ein Spielfeld
	 * @param ship - Schiffstyp
	 * @param coordinates - X und Y Koordinaten des Startfeldes
	 * @param orientation - h oder v ausrichtung
	 * @param player - anzugreifender Spieler
	 */
	public String setAttack(Ship ship, int[] coordinates, char orientation, Player player){
		String results = matrixTools.attackField(ship, coordinates, orientation, player);
		return results;
	}
}

