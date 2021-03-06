package SaveGame;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import Game.Round;

public class Save {


	
	/**
	 * @param fileName
	 * @param round
	 * 
	 * Speichert ein Spiel unter dem übergegebenen Path incl. Namen
	 * Speichert ein Object von Round
	 */
	public void saveGame(String fileName, Round round){
		ObjectOutputStream output = null;
		
		try {
			output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName + ".save")));
				output.writeObject(round);
			output.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
