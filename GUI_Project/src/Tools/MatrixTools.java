package Tools;
import java.io.*;

import Game.Player;

/**
 * Write a description of class MatrixTools here.
 * 
 * @author ML
 * @version 09.12.14
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
	public int[][] addMatrix(int xVal, int yVal, char orientation, int[][]matrix, EShipType ship ){	
	
		int shipSymbol = 0;
		int length = 0;

		/*Je nach ausgewähltem Schiffstyp 
		 *ein Kennzeichnungssymbol sowie die Schiffslänge setze
		 */
		switch(ship){
		case DESTROYER:
			shipSymbol = 1;
			length = 5;
			break;

		case FRIGATE:
			shipSymbol = 2;
			length = 4;
			break;

		case CORVETTE:
			shipSymbol = 3;
			length = 3;
			break;

		case SUBMARINE:
			shipSymbol = 4;
			length = 2;
			break;
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
	public boolean checkPrivateFields(int xVal, int yVal, char orientation, int[][]matrix, EShipType ship){
		int length = 0;

		//Schifflänge anhand des Ausgewählten Schiffes
		switch(ship){
		case DESTROYER:
			length = 5;
			break;

		case FRIGATE:
			length = 4;
			break;

		case CORVETTE:
			length = 3;
			break;

		case SUBMARINE:
			length = 2;
			break;
		}

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
					//TESTAUSGABE IO.println("j: " + j + " i: " + i);
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
					//TESTAUSGABE IO.println("y: " + y + " x: " + x);
					if(matrix[y][x] > 0){
						return false;
					}
				}
			}
		}
		return true;

	}

	public void attackField(EShipType ship, int[] coordinates, char orientation, Player player){
		int[][] publicField = player.getPublicField().getField();
		int[][] privateField = player.getPrivateField().getField();


		int shootLength = 0;

		switch(ship){
		case CORVETTE:
			shootLength = 1;
			break;
		case DESTROYER:
			shootLength = 3;
			break;
		case FRIGATE:
			shootLength = 2;
			break;
		case SUBMARINE:
			shootLength = 1;
			break;
		default:
			break;

		}

		int x = coordinates[0];
		int y = coordinates[1];
		
		int hitWater = 0;
		int hitShip = 0;

		if(orientation == 'h'){

			/*Solange wie die Schussweite, für ist jedes Kästchen
			 * die Koordinaten prüfen und setzen
			 */
			for(int z = x; z <= x+shootLength; z++){
		
				/*Nur wenn die gewählte Koordinaten Position
				 * kleiner ist als die Spielfeldbreite
				 */
				if(z < privateField.length){

					/*Wenn an dieser Position auf dem Spielfeld des Spielers
					 * eine 0 = Wasser ist, so setze auf dem öffentlichen Feld 
					 * eine 1 für getroffenes Wasser (wird dann als X ausgedruckt)
					 */
					if(privateField[y][z] == 0){
						publicField[y][z] = 1;
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
							player.getDestroyer()[i].setCoordinatesIfHitted(z, y);
						}
						hitShip++;

					}
					else if(privateField[y][z] == 2){
						publicField[y][z] = 2;

						for(int i = 0; i < player.getFrigate().length; i++){
							player.getFrigate()[i].setCoordinatesIfHitted(z, y);
						}
						hitShip++;
					}
					else if(privateField[y][z] == 3){
						publicField[y][z] = 2;

						for(int i = 0; i < player.getCorvette().length; i++){
							player.getCorvette()[i].setCoordinatesIfHitted(z, y);
						}
						hitShip++;
					}
					else if(privateField[y][z] == 4){
						publicField[y][z] = 2;

						for(int i = 0; i < player.getSubmarine().length; i++){
							player.getSubmarine()[i].setCoordinatesIfHitted(z, y);
						}
						hitShip++;
					}
				}
			}
		}
		else{

			for(int q = y; q <= y+shootLength; q++){
				
				/*Nur wenn die gewählte Koordinaten Position
				 * kleiner ist als die Spielfeldlänge
				 */
				if(q < privateField.length){
					
					/*Wenn an dieser Position auf dem Spielfeld des Spielers
					 * eine 0 = Wasser ist, so setze auf dem öffentlichen Feld 
					 * eine 1 für getroffenes Wasser (wird dann als X ausgedruckt)
					 */
					if(privateField[q][x] == 0){
						publicField[q][x] = 1;

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
							player.getDestroyer()[i].setCoordinatesIfHitted(q, x);
						}

						hitShip++;
					}
					else if(privateField[y][x] == 2){
						publicField[q][x] = 2;

						for(int i = 0; i < player.getFrigate().length; i++){
							player.getFrigate()[i].setCoordinatesIfHitted(q, x);
						}
						hitShip++;
					}
					else if(privateField[q][x] == 3){
						publicField[q][x] = 2;

						for(int i = 0; i < player.getCorvette().length; i++){
							player.getCorvette()[i].setCoordinatesIfHitted(q, x);
						}
						hitShip++;
					}
					else if(privateField[q][x] == 4){
						publicField[q][x] = 2;

						for(int i = 0; i < player.getSubmarine().length; i++){
							player.getSubmarine()[i].setCoordinatesIfHitted(q, x);
						}
						hitShip++;
					}

				}
			}
		}

		printPublicField(publicField, player.getPlayerName());

		System.out.println(hitShip + "x Schiff getroffen\n" + hitWater + "x Wasser getroffen\n");
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




}
