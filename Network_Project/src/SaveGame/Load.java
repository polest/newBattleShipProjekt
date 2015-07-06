package SaveGame;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import Game.Player;
import Game.Round;

public class Load {

	private Round round;

	public Load(){
		
	}
	
	
	/**
	 * @param path
	 * Lädt ein Spiel aus dem übergegebenen Path
	 */
	public void loadGame(String path){
		ObjectInputStream input = null;
		
		try {
			input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(path)));
			round = (Round) input.readObject();
			input.close();


		} catch (FileNotFoundException e) {
			System.err.println("Datei nicht gefunden.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Datei nicht gefunden.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("Datei nicht gefunden.");
			e.printStackTrace();
		}
	}


	public Round getRound() {
		return round;
	}
	
	
	
}
