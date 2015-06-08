package Main;

import Game.InitGame;
import Tools.EShipType;
import Tools.IO;
import Tools.ColoredPrint.EPrintColor;

public class Main {
	public static void main(String[] args){
		boolean start = true;
		int eingabe;
		System.out.println("Willkommen bei BATTLESHIP special LARS edition!\n"
				+ "um das Spiel zu beginnen, wählen sie bitte die (1). Um ein zuvor\ngespeichertes Spiel zu "
				+ "laden drücken sie bitte die (2)");
		
		eingabe = IO.readInt();
		while(eingabe < 1 || eingabe > 2){
			System.out.println("Bitte wählen sie eine der beiden Optionen!");
			eingabe = IO.readInt();
		}
		if(eingabe == 1){
			start = true;
		}else if(eingabe == 2){
			start = false;
		}
		InitGame spielen = new InitGame(start);
		
		//TODO PImmelkopf
		
	}
}