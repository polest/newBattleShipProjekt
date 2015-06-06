package Game;
import java.io.Serializable;

import Tools.ColoredPrint;
import Tools.ColoredPrint.EPrintColor;
import Tools.IO;


public class Options implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6599149630474669593L;
	private int player;
	private int aiPlayer;
	private String[] playerNames;
	private int destroyer;
	private int frigate;
	private int corvette;
	private int submarine;
	private int totalShips;
	private int battlefieldSize;
	private ColoredPrint coloredPrint;

	public Options(){
		this.totalShips = 0;
		this.coloredPrint = new ColoredPrint();
		this.initGame();

	}

	public int getPlayer() {
		return player;
	}

	public int getAiPlayer(){
		return aiPlayer;
	}
	
	public String[] getPlayerNames() {
		return playerNames;
	}

	public int getDestroyer() {
		return destroyer;
	}

	public int getFrigate() {
		return frigate;
	}

	public int getCorvette() {
		return corvette;
	}

	public int getSubmarine() {
		return submarine;
	}

	public int getTotalShips() {
		return totalShips;
	}

	public int getBattlefieldSize() {
		return battlefieldSize;
	}


	/**
	 * Einstellungen der Spieleranzahl und ihre Namen
	 */
	private void initPlayer(){

		IO.println("Bitte wählen sie die Anzahl der Spieler aus [2-6]: ");
		int count = IO.readInt();

		while(count < 2 || count > 6){
			IO.println("Ungültige Eingabe. Bitte zwischen 2-6 auswählen!");
			count = IO.readInt();
		}

		this.player = count;
		this.playerNames = new String[count];
		
		
		
		IO.println("Wie viele davon sind Bots [0-"+ count +"]: ");
		int aiCount = IO.readInt();
		
		while(aiCount < 0 || aiCount > count){
			IO.println("Ungültige Eingabe. Bitte zwischen 0-"+ count +" auswählen!");
			aiCount = IO.readInt();
		}
		this.aiPlayer = aiCount;
		
		int bc = 1;
		for(int i = 0; i < count; i++){
			int c = i+1;
			
			boolean nameUnique = true;
			
			if(this.player - this.aiPlayer <= i){
				IO.println("Bot " + bc + " Name: ");
				bc++;
			} else {
				IO.println("Spieler " + c + " Name: ");
			}
			
			String tempName;
			do{
				tempName= IO.readString();
				nameUnique = true;

				for(int t = 0; t < this.playerNames.length; t++){

					if(this.playerNames[t] != null){
						if(this.playerNames[t].equals(tempName)){
							System.out.println("Name schon vorhanden! Bitte erneut eingeben: ");
							nameUnique = false;	
						}
					}
				}

			}while(nameUnique == false);
			this.playerNames[i] = tempName;
		}
	}

	public void initShips(){

		System.out.println("Bitte geben sie nun die Anzahl der Schiffe ein:");
		while(totalShips == 0){
			System.out.println("Zerstörer");
			
			destroyer = IO.readShipInt();
			while(destroyer < 0){
				this.coloredPrint.println(EPrintColor.RED, "Ungültige Eingabe! Anzahl der Zerstörer?");
				destroyer = IO.readShipInt();
			}
			
			System.out.println("Frigatte");
			
			frigate = IO.readShipInt();
			while(frigate < 0){
				this.coloredPrint.println(EPrintColor.RED, "Ungültige Eingabe! Anzahl der Fregatten?");
				frigate = IO.readShipInt();
			}
			
			System.out.println("Korvette");
			corvette = IO.readShipInt();
			
			while(corvette < 0){
				this.coloredPrint.println(EPrintColor.RED, "Ungültige Eingabe! Anzahl der Korvetten?");
				corvette = IO.readShipInt();
			}
			
			System.out.println("U-Boot");
			submarine = IO.readShipInt();
			
			while(submarine < 0){
				this.coloredPrint.println(EPrintColor.RED, "Ungültige Eingabe! Anzahl der UBoote?");
				submarine = IO.readShipInt();
			}
			
			totalShips = destroyer + corvette + frigate + submarine;
			
			if(totalShips == 0){
				System.out.println("Sie müssen mindestens ein Schiff auswählen!");
			}
		}
	}
	
	/**
	 * 
	 * Methode berechnet anhand der Anzahl der Schiffe, wie groß das Spielfeld sein muss und gibt diese zurück.
	 * 
	 * @return size (größe des Spielfelds)
	 */

	public void setBattleFieldSize(){
		int zahl = 1;
		int destroyerSize = 21;
		int frigateSize = 18;
		int corvetteSize = 15;
		int submarineSize = 12;
		int totalShipSize;

		totalShipSize = (this.destroyer*destroyerSize)+(this.corvette*corvetteSize)+(this.frigate*frigateSize)+(this.submarine*submarineSize);

		while((zahl * zahl) < totalShipSize){
			zahl++;
		}
		System.out.println("Bitte geben sie nun die Spielfeldgröße ein (mindestens " + zahl + ")");
		battlefieldSize = IO.readInt();
		while(this.battlefieldSize < zahl){
			System.out.println("Ihre Eingabe muss midestens " + zahl + " betragen. Bitte wiederholen sie ihre Eingabe!");
			this.battlefieldSize = IO.readInt();
		}
	}
	
	/**
	 * Spielstart ruft die Optionen für Spieler, Spielfeld und Schiffe auf
	 */
	public void initGame(){

		initPlayer();
		initShips();
		setBattleFieldSize();
	}
}
