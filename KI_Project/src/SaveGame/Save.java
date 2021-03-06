package SaveGame;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import Game.Player;

public class Save {

	public void saveGame(String fileName, Player[] player){
		ObjectOutputStream output = null;
		
		
		
		try {
			File dir = new File(System.getProperty("user.dir") + "/data"); 
			dir.mkdir();
			
			output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("./data/" + fileName + ".save")));
			output.writeObject(player);
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
