package Game;

import java.io.File;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import SaveGame.Load;
import Ships.Corvette;
import Ships.Destroyer;
import Ships.Frigate;
import Ships.Ship;
import Ships.Submarine;
import Tools.ColoredPrint;
import Tools.ColoredPrint.EPrintColor;
import Tools.IO;

public class InitGame implements Serializable{

	/**
	 * @author ML
	 * @version 21.03.15
	 */
	private static final long serialVersionUID = 988834907748712441L;

	private Options gameOptions;
	private Options_View gameOptionsView;
	private InitGame_View initGameView;
	private Load load = new Load();
	private Player[] player;
	private ColoredPrint colorPrint = new ColoredPrint();
	private int fieldSize;
	String[] listFileNames;
	private Round rounds;
	private int playerId;
	private Ship choosenShip;
	private Destroyer[] destroyer;
	private Frigate[] frigate;
	private Corvette[] corvette;
	private Submarine[] submarine;
	//private boolean placingShip = false;

	private int destroyerCount;
	private int frigateCount;
	private int corvetteCount;
	private int submarineCount;
	private char orientation;
	private boolean isPressed;



	public InitGame(Options_View optionsView, Options gameOptions, InitGame_View initGameView){
		this.gameOptionsView = optionsView;
		this.gameOptions = gameOptions;
		this.initGameView = initGameView;
		this.playerId = 0;
		this.isPressed  = false;
		this.configureGame();
		//if(start){
		//this.gameOptions = new Options(width, height);
		//addOkListener();
		//Round rounds = new Round(this.player, this.fieldSize);
		//rounds.play();
		/*}else{
			System.out.println("Bitte geben sie den Namen von ihrem Spiel ein, welches sie laden möchten.");
			String eingabe = IO.readString();
			load.loadGame(eingabe);
			this.player = load.getPlayer();
			this.fieldSize = player[0].getPrivateField().getSize();
			Round rounds = new Round(this.player,this.fieldSize);

}else{
			boolean go = false;
			while(!go){
				System.out.println("Bitte geben sie die Zahl von ihrem Spiel ein, welches sie laden möchten.");

				File f = new File(System.getProperty("user.dir") + "/data");
				File[] l = f.listFiles(); 

				listFileNames = new String[0];

				for (int x = 0; x < l.length; x++) 
				{
					try{

						String fileName = l[x].getName();
						String[] fileArray = fileName.split("\\.");

						if(fileArray[1].equals("save")){

							String[] newArray = new String[listFileNames.length + 1];

							for(int i = 0; i < listFileNames.length; i++){
								newArray[i] = listFileNames[i];
							}

							newArray[newArray.length-1] = fileArray[0];

							listFileNames = newArray;

							System.out.println("("+ newArray.length +") " + fileArray[0]);
						}
					} catch (ArrayIndexOutOfBoundsException e){
						e.getStackTrace();
					}
				}

				if(listFileNames.length == 0){
					this.colorPrint.println(EPrintColor.RED, "Keine Datei zum laden gefunden.");
					System.exit(0);
				}

				int eingabe = IO.readInt();

				while(eingabe < 1 || eingabe > listFileNames.length){
					this.colorPrint.println(EPrintColor.RED, "Falsche Eingabe");
					System.out.println("Bitte geben sie die Zahl von ihrem Spiel ein, welches sie laden möchten.");
					eingabe = IO.readInt();
				}

				if(load.loadGame(listFileNames[eingabe-1])){
					go = true;
				}
			}
			this.player = load.getPlayer();
			this.fieldSize = player[0].getPrivateField().getSize();
			Round rounds = new Round(this.player,this.fieldSize);
		}

		}
		 */

	}


	public JPanel getOptionsPanel(){
		return this.gameOptions.getPanel();
	}

	public Player[] getPlayer() {
		return player;
	}

	public void configureGame(){
		this.player = new Player[this.gameOptions.getPlayer()];
		this.fieldSize = this.gameOptions.getBattlefieldSize();
		this.initGameView.setFieldSize(this.fieldSize);

		for(int i = 0; i < player.length; i++){
			BattleField battlefield = new BattleField(this.fieldSize);

			if(i == 0){
				player[i] = new Player(true, this.gameOptions.getTotalShips(), this.gameOptions.getDestroyer(), 
						this.gameOptions.getFrigate(), this.gameOptions.getCorvette(),this.gameOptions.getSubmarine(),this.gameOptions.getPlayerNames()[i], battlefield, false);
			}else{
				player[i] = new Player(false, this.gameOptions.getTotalShips(), this.gameOptions.getDestroyer(), 
						this.gameOptions.getFrigate(), this.gameOptions.getCorvette(),this.gameOptions.getSubmarine(),this.gameOptions.getPlayerNames()[i], battlefield, this.gameOptions.getPlayerKi(i-1));
			}
		}

		this.setShipsToField();
	}



	/**
	 *  Positionierung der Schiffe von jedem Spieler (nacheinander)
	 */
	private void setShipsToField(){
		this.destroyer = player[playerId].getDestroyer();
		this.frigate = player[playerId].getFrigate();
		this.corvette = player[playerId].getCorvette();
		this.submarine = player[playerId].getSubmarine();

		this.destroyerCount = 0;
		this.frigateCount = 0;
		this.corvetteCount = 0;
		this.submarineCount = 0;

		String name = player[playerId].getPlayerName();
		this.initGameView.setPlayerName(name);

		this.initGameView.setDestroyer( player[playerId].getDestroyer().length );
		this.initGameView.setFrigate( player[playerId].getFrigate().length );
		this.initGameView.setCorvette( player[playerId].getCorvette().length );
		this.initGameView.setSubmarine( player[playerId].getSubmarine().length );

		this.initGameView.initField();

		this.initGameView.setBattleFieldMouseListener(new BattleFieldMouseListener());
		//this.initGameView.setBattleFieldMouseMotionListener(new BattleFieldMouseMotionListener());

		this.initGameView.setShipsSelectionListener(new ShipsListener());

		//		rounds = new Round(this.player, this.fieldSize);
		//		rounds.play();

	}

	private boolean setShipToField(Ship ship, Player player, String pos) {

		int[] koordinaten = checkPos(pos);

		if(orientation == 'h'){
			player.saveShipCoordinatesH(koordinaten[0], koordinaten[1], ship);
		}
		else{
			player.saveShipCoordinatesV(koordinaten[0], koordinaten[1], ship);
		}

		if(player.getPrivateField().setShips(ship, koordinaten[0], koordinaten[1], orientation) == true){
			player.getPrivateField().printPrivateField(initGameView.getBattleField(), player.getPlayerName());


			return true;
		}
		else{
			this.colorPrint.println(EPrintColor.RED, "Schiff kann dort nicht positioniert werden!\nBitte erneut Koordinaten eingeben");
			return false;
		}

	}


	/**
	 * @param pos - die zu überprüfenden Koordinaten 
	 * @return Gibt zurück, ob die eingegebenen Koordinaten korrekt sind
	 */
	private int[] checkPos(String pos){
		try{
			pos = pos.replaceAll("\\s+", "");

			String[] sKoordinaten = pos.split(",");
			int[] iKoordinaten = new int[2];

			if(sKoordinaten.length != 2){
				return null;
			}
			for(int i = 0; i < 2; i++){
				int toInt = Integer.parseInt(sKoordinaten[i]);

				if(toInt < 0 || toInt > fieldSize){
					return null;
				}
				else{
					iKoordinaten[i] = toInt;
				}
			}

			return iKoordinaten;
		}
		catch(Exception e){
			this.colorPrint.println(EPrintColor.RED, "Ungültige Eingabe");

		}
		return null;
	}

	private void setChoosenShipVisibility(boolean state){
		if(choosenShip instanceof Destroyer){
			this.initGameView.setDestroyerIconVisible(state);
		}

	}

	/*private class BattleFieldMouseMotionListener implements MouseMotionListener{
		int mouseY = 0;

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if(placingShip == true){
				//int mouse2 = MouseInfo.getPointerInfo().getLocation().y;
				//int mouseX = MouseInfo.getPointerInfo().getLocation().x;
				int mouse2 = e.getY();
				int mouseX = e.getX();
				JButton btn = (JButton)e.getSource();
				String pos = btn.getActionCommand();
				int btnX = Integer.parseInt(pos.substring(1, 2) );
				int btnY = Integer.parseInt(pos.substring(3) );
				int offsetX = initGameView.getCellSize() * (btnX-1);
				int offsetY = initGameView.getCellSize() * (btnY-1);

				if(mouseY >= mouse2){
					orientation = 'h';
					System.out.println("h");
					initGameView.setDestroyerIcon( (mouseX)+offsetX, (mouse2)+offsetY, 'h');
				}
				else{
					orientation = 'v';	
					System.out.println("v");
					initGameView.setDestroyerIcon( (mouseX)+offsetX, (mouse2)+offsetY, 'h');

				}
				mouseY = mouse2;
			}
		}
	}
	 */

	private class BattleFieldMouseListener implements MouseListener{
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			isPressed = true;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			isPressed = false;
			Player playerOnTurn = player[playerId];

			boolean checked;

			if(choosenShip != null){
				JButton btn = (JButton)e.getSource();
				String pos = btn.getActionCommand();

				if(choosenShip instanceof Destroyer){
					checked = setShipToField(choosenShip, playerOnTurn, pos);
					if(checked == true){
						destroyerCount++;

						if(destroyerCount >= destroyer.length){
							initGameView.setDestroyerDisabled();
							setShipToField(choosenShip, playerOnTurn, pos);
							choosenShip = null;
						}
					}
				}
				else if(choosenShip instanceof Frigate){
					checked = setShipToField(choosenShip, playerOnTurn, pos);
					if(checked == true){
						frigateCount++;

						if(frigateCount >= frigate.length){
							initGameView.setFrigateDisabled();
							setShipToField(choosenShip, playerOnTurn, pos);
							choosenShip = null;
						}
					}

				}
				else if(choosenShip instanceof Corvette){
					checked = setShipToField(choosenShip, playerOnTurn, pos);
					if(checked == true){
						corvetteCount++;

						if(corvetteCount >= corvette.length){
							initGameView.setCorvetteDisabled();
							choosenShip = null;
						}
					}
				}

				else if(choosenShip instanceof Submarine){
					checked = setShipToField(choosenShip, playerOnTurn, pos);
					if(checked == true){
						submarineCount++;

						if(submarineCount >= submarine.length){
							initGameView.setSubmarineDisabled();
							choosenShip = null;
						}
					}
				}
			}
			//placingShip = false;
			//setChoosenShipVisibility(false);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	}

	private class ShipsListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JToggleButton btn = (JToggleButton)e.getSource();

			if(btn.getActionCommand().equals("destroyer")){
				initGameView.setChoosenShip(btn);
				choosenShip = destroyer[destroyerCount];
				initGameView.setDestroyerIconVisible(true);
			}
			else if(btn.getActionCommand().equals("frigate")){
				initGameView.setChoosenShip(btn);
				choosenShip = frigate[frigateCount];
			}
			else if(btn.getActionCommand().equals("corvette")){
				initGameView.setChoosenShip(btn);
				choosenShip = corvette[corvetteCount];
			}
			else if(btn.getActionCommand().equals("submarine")){
				initGameView.setChoosenShip(btn);
				choosenShip = submarine[submarineCount];
			}

			//			if(btn.isSelected() == true){
			//				setChoosenShipVisibility(true);
			//				placingShip = true;
			//			}
			//			else{
			//				setChoosenShipVisibility(false);
			//				placingShip = false;
			//			}

		}

	}
}
