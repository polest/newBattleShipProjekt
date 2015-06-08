package SaveGame;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import Game.Player;

public class Load {

	private Player[] player;

	public Load(){
		
	}
	
	
	public void loadGame(String name){
		ObjectInputStream input = null;
		
		try {
			input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(name + ".save")));
			player = (Player[]) input.readObject();
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

	public Player[] getPlayer() {
		return player;
	}
	
}
