package Tools;
import java.io.*;

import Game.Player;
import Ships.Corvette;
import Ships.Destroyer;
import Ships.Frigate;
import Ships.Ship;
import Ships.Submarine;

/**
 * Write a description of class MatrixTools here.
 * 
 * @author ML
 * @version 22.03.15
 */
public class MatrixTools implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6993189195389536759L;
	private final char black ='\u25A0';
	private final char wave ='\u223C';
	//private ColoredPrint colorPrint =  new ColoredPrint();

	/**
	 * @param matrix 
	 * Druckt die Matrix als Spielfeld 
	 * mit Grafischen Extras auf der Konsole aus
	 * Die Matrix mit verschönerungen wird nur ausgedruckt und 
	 * nicht gespeichert oder zurückgegeben!
	 */
	public void printPrivateField(int[][] matrix, String name){

		int[][] printMatrix = new int[matrix.length][matrix.length];

		IO.println("Spielfeld von \"" + name + "\"");

		for(int y = 0; y < printMatrix.length; y++){
			String leftAlignFormat = "| %-3s ";
			boolean isSmallField = true;

			if(matrix.length > 9){
				isSmallField  = true;
			}
			else{
				isSmallField = false;
			}

			for(int f = 0; f < printMatrix.length; f++){

				System.out.format("+-----");
			}

			System.out.print("+ \n");

			for(int x = 0; x < printMatrix.length; x++){


				if( (x == 0) && (y == 0)){
					System.out.format(leftAlignFormat, "y\\x");
				}
				else if(y == 0){
					if(isSmallField == true && x < 10){

						System.out.format(leftAlignFormat, " " + x);
					}
					else{
						System.out.format(leftAlignFormat, x);
					}
				}
				else if(x == 0){
					if(isSmallField == true && x < 10){

						System.out.format(leftAlignFormat, " " + y);
					}
					else{
						System.out.format(leftAlignFormat, y);
					}
				}
				else{
					if(matrix[y][x] == 0){
						System.out.format(leftAlignFormat, " " + wave);
					}
					else{
						System.out.format(leftAlignFormat, " " + black);
					}

				}

			}
			System.out.println("");
		}

		for(int f = 0; f < printMatrix.length; f++){

			System.out.format("+-----");
		}

		System.out.print("+ \n");

	}


	/**
	 * @param matrix - Spielfeld Matrix
	 * @return gibt die grafische Ausgangs Matrix (Spielfeld) zurück
	 * Das Spielfeld wird standardmäßig mit Tilden für nicht belegte Felder gefüllt
	 * 
	 */
	public int[][] initMatrix(int[][] matrix){	

		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix.length; j++){
				//Setze -1 um bei der Ausgabe dort die Koordinaten Beschriftung auszugeben
				if(j == 0){
					matrix[i][j] = -1;
				}
				else if(i == 0){
					matrix[i][j] = -1;
				}
				//Ansonsten wird Matrix mit Tilde Symbol gefüllt
				matrix[i][j] = 0;
			}
		}
		return matrix;
	}

	/**
	 * @param ival - Benutzereingabe X-Koordinate
	 * @param jval - Benutzereingabe Y-Koordinate
	 * @param orientation - Benutzereingabe Richtung der Schiffspositionierung
	 * @param matrix - Spielfeld des jeweiligen Spielers
	 * @param ship - ausgewählte Schiff
	 * @return Gibt das mit dem ausgewähltem Schiff positionierte Spielfeld zurück 
	 */
	public int[][] addMatrix(int xVal, int yVal, char orientation, int[][]matrix, Ship ship ){	

		int shipSymbol = 0;
		int length = ship.getShipSize();

		/*Je nach ausgewähltem Schiffstyp 
		 *ein Kennzeichnungssymbol sowie die Schiffslänge setze
		 */
		if(ship instanceof Destroyer){
			shipSymbol = 1;
		}
		else if(ship instanceof Frigate){
			shipSymbol = 2;
		}
		else if(ship instanceof Corvette){
			shipSymbol = 3;
		}
		else if(ship instanceof Submarine){
			shipSymbol = 4;
		}


		//Bei vertikaler Ausrichtung des Schiffes
		if(orientation == 'h'){
			for(int x = xVal; x < xVal + length; x++){
				//Füllt Matrix mit dem Symbol des jeweiligen Schiffes
				matrix[yVal][x] = shipSymbol;
			}
		}
		//Bei horizontaler Ausrichtung des Schiffes
		else{

			for(int y = yVal; y < yVal+length; y++){
				//Füllt Matrix mit dem Symbol des jeweiligen Schiffes
				matrix[y][xVal] = shipSymbol;
			}
		}
		return matrix;
	}


	/**
	 * @param ival - Benutzereingabe X-Koordinate
	 * @param jval - Benutzereingabe Y-Koordinate
	 * @param orientation - Benutzereingabe Richtung der Schiffspositionierung
	 * @param matrix - Spielfeld des jeweiligen Spielers
	 * @param ship - ausgewählte Schiff
	 * @return Ein boolschen Wert, ob das Schiff an der ausgewählten Position und Ausrichtung
	 * basierend auf den Spielregeln positioniert werden kann oder nicht
	 */
	public boolean checkPrivateFields(int xVal, int yVal, char orientation, int[][]matrix, Ship ship){
		int length = ship.getShipSize();

		int yIndex = yVal;
		int xIndex = xVal;
		int yMarginTop = 1;
		int xMarginLeft = 1;
		int yMarginBottom = 1;
		int xMarginRight = 1;

		//Bei vertikaler Ausrichtung
		if(orientation == 'v'){

			/*
			 * Da die Umgebenen Positionen des Schiffes geprüft werden,
			 * muss der Abstand nach links, rechts, oben oder unten
			 * auf 0 gesetzt werden, sofern es in einer der Ecken beginnt oder
			 * die Länge einer dieser Ecken erreicht
			 * 
			 * Ansonsten wird der Abstand als 1 Feld links/oben, das eigene Feld sowie 1 Feld rechts/unten
			 * mitüberprüft
			 * -> sonst ArrayOutOfBounds
			 */

			//obere Ecke
			if(yIndex == 1){
				yMarginTop = 0;
			}
			//linke Ecke
			if(xIndex == 1){
				xMarginLeft = 0;
			}
			//untere Ecke
			if( (yIndex + length) == matrix.length){
				yMarginBottom = 0;
			}
			//rechte Ecke
			if(xIndex == (matrix.length - 1) ){
				xMarginRight = 0;
			}

			//Koordinaten Länge des Schiffes ab der gewählten Position
			int shipLength = yIndex + length;

			/*Wenn die Schiffslänge ab den gewählten Startkoordinaten 
			 * länger ist als die Feldlänge, ist eine positionierung nicht möglich
			 */
			if(shipLength > matrix.length){
				return false;
			}

			/*
			 * Ansonsten, prüfe nun ob das Schiff in Vertikaler ausrichtung 
			 * an ein anderes Schiff anrenzt
			 */
			for(int y = (yIndex - yMarginTop); y < (shipLength + yMarginBottom); y++ ){
				for( int x = (xIndex - xMarginLeft); x <= (xIndex + xMarginRight); x++){
					if(matrix[y][x] > 0){
						return false;
					}
				}
			}
		}
		else{

			//obere Ecke
			if(yIndex == 1){
				yMarginTop = 0;
			}
			//linke Ecke
			if(xIndex == 1){
				xMarginLeft = 0;
			}
			//untere Ecke
			if( yIndex == (matrix.length - 1) ){
				yMarginBottom = 0;
			}
			//rechte Ecke
			if( (xIndex + length ) == matrix.length ){
				xMarginRight = 0;
			}
			int shipLength = xIndex + length;

			/*Wenn die Schiffslänge ab der gewählten Startkoordinaten 
			 * länger ist als die Feldlänge, ist eine positionierung nicht möglich
			 */
			if( (shipLength) > matrix.length){
				return false;
			}

			/*
			 * Ansonsten, prüfe nun ob das Schiff in Vertikaler ausrichtung 
			 * an ein anderes Schiff anrenzt
			 */
			for(int y = (yIndex - yMarginTop); y <= (yIndex + yMarginBottom); y++ ){
				for( int x = (xIndex - xMarginLeft); x < (shipLength + xMarginRight); x++){
					if(matrix[y][x] > 0){
						return false;
					}
				}
			}
		}
		return true;

	}

	public void attackField(Ship ship, int[] coordinates, char orientation, Player player){
		int[][] publicField = player.getPublicField().getField();
		int[][] privateField = player.getPrivateField().getField();


		int shootLength = ship.getShootArea();

		int x = coordinates[0];
		int y = coordinates[1];
		boolean shipIsSwimming = true;
		int hitWater = 0;
		int hitShip = 0;

		if(orientation == 'h'){

			/*Solange wie die Schussweite, für ist jedes Kästchen
			 * die Koordinaten prüfen und setzen
			 */
			for(int z = x; z < x+shootLength; z++){

				/*Nur wenn die gewählte Koordinaten Position
				 * kleiner ist als die Spielfeldbreite
				 */
				if(z < privateField.length){

					/*Wenn an dieser Position auf dem Spielfeld des Spielers
					 * eine 0 = Wasser ist, so setze auf dem öffentlichen Feld 
					 * eine 1 für getroffenes Wasser (wird dann als X ausgedruckt)
					 */
					if(privateField[y][z] == 0){
						if(publicField[y][z] != 3){
							publicField[y][z] = 1;
						}
						hitWater++;
					}
					/*Ansonsten setze eine 2 für ein getroffenes Schiff
					 * privatesFeld: 
					 * 1 = Zerstörer
					 * 2 = Fregatte
					 * 3 = Korvette
					 * 4 = UBoot
					 * 
					 * öffentliches Feld:
					 * 2 egal um welches Schiff es sich handelt		
					 */

					else if(privateField[y][z] == 1){
						publicField[y][z] = 2;

						for(int i = 0; i < player.getDestroyer().length; i++){
							checkShipIsSunk(player,  player.getDestroyer()[i], z, y);
						}
						player.checkIfSunk("D");
						hitShip++;

					}
					else if(privateField[y][z] == 2){
						publicField[y][z] = 2;

						for(int i = 0; i < player.getFrigate().length; i++){
							checkShipIsSunk(player,  player.getFrigate()[i], z, y);
						}
						player.checkIfSunk("F");
						hitShip++;
					}
					else if(privateField[y][z] == 3){
						publicField[y][z] = 2;

						for(int i = 0; i < player.getCorvette().length; i++){
							checkShipIsSunk(player,  player.getCorvette()[i], z, y);
						}
						player.checkIfSunk("C");
						hitShip++;
					}
					else if(privateField[y][z] == 4){
						publicField[y][z] = 2;

						for(int i = 0; i < player.getSubmarine().length; i++){
							checkShipIsSunk(player,  player.getSubmarine()[i], z, y);
						}
						player.checkIfSunk("S");
						hitShip++;
					}
				}
			}
		}
		else{

			for(int q = y; q < y+shootLength; q++){

				/*Nur wenn die gewählte Koordinaten Position
				 * kleiner ist als die Spielfeldlänge
				 */
				if(q < privateField.length){

					/*Wenn an dieser Position auf dem Spielfeld des Spielers
					 * eine 0 = Wasser ist, so setze auf dem öffentlichen Feld 
					 * eine 1 für getroffenes Wasser (wird dann als X ausgedruckt)
					 */
					if(privateField[q][x] == 0){
						if(publicField[q][x] != 3){
							publicField[q][x] = 1;
						}

						hitWater++;
					}
					/*Ansonsten setze eine 2 für ein getroffenes Schiff
					 * privatesFeld: 
					 * 1 = Zerstörer
					 * 2 = Fregatte
					 * 3 = Korvette
					 * 4 = UBoot
					 * 
					 * öffentliches Feld:
					 * 2 egal um welches Schiff es sich handelt		
					 */else if(privateField[q][x] == 1){
						 publicField[q][x] = 2;

						 for(int i = 0; i < player.getDestroyer().length; i++){
							 checkShipIsSunk(player,  player.getDestroyer()[i], x, q);
						 }
						 player.checkIfSunk("D");

						 hitShip++;
					 }
					 else if(privateField[y][x] == 2){
						 publicField[q][x] = 2;

						 for(int i = 0; i < player.getFrigate().length; i++){
							 checkShipIsSunk(player, player.getFrigate()[i], x, q);
						 }
						 player.checkIfSunk("F");
						 hitShip++;
					 }
					 else if(privateField[q][x] == 3){
						 publicField[q][x] = 2;

						 for(int i = 0; i < player.getCorvette().length; i++){
							 checkShipIsSunk(player,  player.getCorvette()[i], x, q);
						 }
						 player.checkIfSunk("C");
						 hitShip++;
					 }
					 else if(privateField[q][x] == 4){
						 publicField[q][x] = 2;

						 for(int i = 0; i < player.getSubmarine().length; i++){
							 checkShipIsSunk(player,  player.getSubmarine()[i], x, q);
						 }
						 player.checkIfSunk("S");
						 hitShip++;
					 }

				}
			}
		}

		printPublicField(publicField, player.getPlayerName());

		System.out.println(hitShip + "x Schiff getroffen\n" + hitWater + "x Wasser getroffen\n");
	}

	private void checkShipIsSunk(Player player, Ship ship, int x, int y){
		boolean shipIsSwimming = ship.setCoordinatesIfHitted(x, y);

		if(shipIsSwimming == false){
			int[] coordsSunkenShip = ship.getStartCoordsAndOrientation();
			setPublicFieldSunkenShip(player, ship);
		}
	}

	private void setPublicFieldSunkenShip(Player player, Ship ship){
		int[][] publicField = player.getPublicField().getField();

		int shipLength = ship.getShipSize();
		// [x][y] koordinate
		int sunkenKoords[][] = new int[shipLength][2];

		//[0] = x, [1] = y, [2] = bei 0 ist es horizontal und bei 1 ist es verikal
		int[] shipStartCoordsAndOrientation = ship.getStartCoordsAndOrientation();

		int xStart = shipStartCoordsAndOrientation[0];
		int yStart = shipStartCoordsAndOrientation[1];
		int orientation = shipStartCoordsAndOrientation[2];

		//Wenn das Schiff horizontal positioniert ist...
		if(orientation == 0){
			//Setze eine 4 für ein gesunkenes Schiff
			for(int i = xStart; i < (xStart + shipLength ); i++){
				publicField[yStart][i] = 4;
				//Setze drumherum eine 3 für umliegendes Wasser eines gesunkenen Schiffes
				//Oberhalb des Schiffes
				if(yStart > 1){
					publicField[ (yStart-1)][i] = 3;
				}
				//unterhalb des Schiffes
				if(yStart+1 < publicField.length){
					publicField[(yStart+1)][i] = 3;
				}
			}
			//links vom Schiff
			if(xStart > 1){
				int leftStart = yStart;
				int leftEnd = yStart;

				//wenn es nicht oben links in der Ecke ist
				if(yStart > 1){
					leftStart -= 1;
				}

				//wenn es nicht unten links in der Ecke ist
				if( (yStart + 1) < publicField.length){
					leftEnd += 1;
				}
				for(int j = leftStart; j <= leftEnd; j++){
					publicField[j][xStart-1] = 3;
				}
			}
			//rechts vom Schiff
			if( (xStart + ship.getShipSize()) < publicField.length){

				int rightStart = yStart;
				int rightEnd = yStart;

				//wenn es nicht oben links in der Ecke ist
				if(yStart > 1){
					rightStart -= 1;
				}

				//wenn es nicht unten links in der Ecke ist
				if( (yStart) < (publicField.length-1) ){
					rightEnd += 1;
				}

				for(int j = rightStart; j <= rightEnd; j++){
					publicField[j][xStart+ship.getShipSize()] = 3;
				}
			}
		}
		else{
			//Setze eine 4 für ein gesunkenes Schiff
			for(int i = yStart; i < (yStart + shipLength); i++){
				publicField[i][xStart] = 4;
				//Setze drumherum eine 3 für umliegendes Wasser eines gesunkenen Schiffes
				//Links vom Schiff
				if(xStart > 1){
					publicField[ (i)][xStart-1] = 3;
				}
				//rechts vom Schiff
				if(xStart < publicField.length-1){
					publicField[i][(xStart+1)] = 3;
				}
			}
			//obenerhalb vom Schiff
			if(yStart > 1){
				int leftStart = xStart;
				int leftEnd = xStart;

				//wenn es nicht links aussen ist
				if(xStart > 1){
					leftStart -= 1;
				}

				//wenn es nicht rechts aussen ist
				if(( xStart+1) < publicField.length-1){
					leftEnd += 1;
				}

				for(int j = leftStart; j <= leftEnd; j++){
					publicField[yStart-1][j] = 3;
				}
			}
			//unterhalb vom Schiff
			if((yStart + ship.getShipSize()) < publicField.length){

				int rightStart = xStart;
				int rightEnd = xStart;

				//wenn es links am Rand ist
				if(xStart > 1){
					rightStart -= 1;
				}

				//wenn es unten rechts am Rand ist
				if( (xStart + 1) < publicField.length){
					rightEnd += 1;
				}

				for(int j = rightStart; j <= rightEnd; j++){
					publicField[yStart+ship.getShipSize()][j] = 3;
				}
			}
		}
	}


	public void printPublicField(int[][] publicField, String name){

		int[][] printMatrix = new int[publicField.length][publicField.length];

		System.out.println("Spielfeld von \"" + name + "\"");

		for(int y = 0; y < printMatrix.length; y++){
			String leftAlignFormat = "| %-3s ";
			boolean isSmallField = true;

			if(publicField.length > 9){
				isSmallField  = true;
			}
			else{
				isSmallField = false;
			}

			for(int f = 0; f < printMatrix.length; f++){

				System.out.format("+-----");
			}

			System.out.print("+ \n");

			for(int x = 0; x < printMatrix.length; x++){


				if( (x == 0) && (y == 0)){
					System.out.format(leftAlignFormat, "y\\x");
				}
				else if(y == 0){
					if(isSmallField == true && x < 10){

						System.out.format(leftAlignFormat, " " + x);
					}
					else{
						System.out.format(leftAlignFormat, x);

					}

				}
				else if(x == 0){
					if(isSmallField == true && x < 10){

						System.out.format(leftAlignFormat, " " + y);
					}
					else{
						System.out.format(leftAlignFormat, y);
					}
				}
				else{

					if(publicField[y][x] == 0){
						System.out.format(leftAlignFormat," " + wave);

					}
					else if(publicField[y][x] == 1){
						System.out.format(leftAlignFormat, " X" );
					}
					else if(publicField[y][x] == 2){
						System.out.format(leftAlignFormat, " " + black);
					}
					else if(publicField[y][x] == 3){
						System.out.format(leftAlignFormat, "s" + black );
					}
					else{
						System.out.format(leftAlignFormat, "XX");
					}

				}

			}
			System.out.println("");
		}

		for(int f = 0; f < printMatrix.length; f++){

			System.out.format("+-----");
		}

		System.out.print("+ \n");

	}




}
