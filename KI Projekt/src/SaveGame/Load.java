package SaveGame;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import Game.Player;
import Tools.ColoredPrint;
import Tools.ColoredPrint.EPrintColor;

public class Load {
	
	private Player[] player;
	private ColoredPrint colorPrint = new ColoredPrint();
	
	public Load(){
		
	}
	
	public boolean loadGame(String name){
		ObjectInputStream input = null;
		
		try {
			input = new ObjectInputStream(new BufferedInputStream(new FileInputStream("./data/" + name + ".save")));
			player = (Player[]) input.readObject();
			input.close();
			return true;

		} catch (FileNotFoundException e) {
			this.colorPrint.println(EPrintColor.RED, "Datei nicht gefunden.");
			//e.printStackTrace();
		} catch (IOException e) {
			this.colorPrint.println(EPrintColor.RED, "Datei nicht gefunden.");
			//e.printStackTrace();
		} catch (ClassNotFoundException e) {
			this.colorPrint.println(EPrintColor.RED, "Datei nicht gefunden.");
			//e.printStackTrace();
		} catch (NullPointerException e){
			this.colorPrint.println(EPrintColor.RED, "Datei nicht gefunden.");
			//e.printStackTrace();
		}
		return false;
	}
	
	public Player[] getPlayer() {
		return player;
	}
	
}
