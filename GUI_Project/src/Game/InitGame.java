package Game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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
	private int totalShips;
	private int destroyerCount;
	private int frigateCount;
	private int corvetteCount;
	private int submarineCount;
	private char orientation;
	private int mouseY;
	private int oldBtnY;
	private boolean shipCanBePlaced;



	public InitGame(Options_View optionsView, Options gameOptions, InitGame_View initGameView){
		this.gameOptionsView = optionsView;
		this.gameOptions = gameOptions;
		this.initGameView = initGameView;
		this.playerId = 0;
		this.orientation = 'h';
		this.mouseY = 0;
		this.oldBtnY = 0;
		this.shipCanBePlaced = false;
		this.configureGame();
	}


	public JPanel getOptionsPanel(){
		return this.gameOptions.getPanel();
	}

	public Player[] getPlayer() {
		return player;
	}

	public int getFieldSize(){
		return this.fieldSize;
	}

	public void configureGame(){
		this.player = new Player[this.gameOptions.getPlayer()];
		this.fieldSize = this.gameOptions.getBattlefieldSize();
		this.initGameView.setFieldSize(this.fieldSize);

		if(playerId < player.length){

			initPlayerBattleShip();
		}


	}

	public void incrementPlayerId(){
		this.playerId++;
	}

	public int getPlayerId(){
		return this.playerId;
	}

	public void initPlayerBattleShip(){
		this.initGameView.initFields(player.length);

		int i = this.playerId;
		BattleField battlefield = new BattleField(this.fieldSize);

		if(i == 0){
			player[i] = new Player(true, this.gameOptions.getTotalShips(), this.gameOptions.getDestroyer(), 
					this.gameOptions.getFrigate(), this.gameOptions.getCorvette(),this.gameOptions.getSubmarine(),this.gameOptions.getPlayerNames()[i], battlefield, false);
			addListener(i);
			this.initGameView.getBattleFieldView(i).getView().setVisible(true);
			this.initGameView.getPanel().repaint();
		}else{
			player[i] = new Player(false, this.gameOptions.getTotalShips(), this.gameOptions.getDestroyer(), 
					this.gameOptions.getFrigate(), this.gameOptions.getCorvette(),this.gameOptions.getSubmarine(),this.gameOptions.getPlayerNames()[i], battlefield, this.gameOptions.getPlayerKi(i-1));
			addListener(i);
			this.initGameView.getBattleFieldView(i).getView().setVisible(true);
			this.initGameView.getBattleFieldView(i-1).getView().setVisible(false);
			
			this.initGameView.getBattleFieldView(i).getView().repaint();
			
			this.initGameView.getPanel().repaint();
			this.initGameView.getPanel().revalidate();
			
		}
		
		this.initShips();
	}

	/**
	 *  Positionierung der Schiffe von jedem Spieler (nacheinander)
	 */
	private void initShips(){
		this.destroyer = player[playerId].getDestroyer();
		this.frigate = player[playerId].getFrigate();
		this.corvette = player[playerId].getCorvette();
		this.submarine = player[playerId].getSubmarine();

		this.destroyerCount = this.destroyer.length;
		this.frigateCount = this.frigate.length;
		this.corvetteCount = this.corvette.length;
		this.submarineCount = this.submarine.length;

		this.totalShips = this.destroyerCount + this.frigateCount + this.corvetteCount + this.submarineCount;

		String name = player[playerId].getPlayerName();
		this.initGameView.setPlayerName(name);

		this.initGameView.setDestroyer( player[playerId].getDestroyer().length );
		this.initGameView.setFrigate( player[playerId].getFrigate().length );
		this.initGameView.setCorvette( player[playerId].getCorvette().length );
		this.initGameView.setSubmarine( player[playerId].getSubmarine().length );

		boolean start = true;

		if(playerId > 0){
			start = false;
		}
		this.initGameView.initPlayerField(player[playerId], playerId);
		//		rounds = new Round(this.player, this.fieldSize);
		//		rounds.play();

	}

	private void addListener(int i){
//		this.initGameView.setBattleFieldMouseListener(new BattleFieldMouseListener());
//		this.initGameView.setBattleFieldMouseMotionListener(new BattleFieldMouseMotionListener());
//		
		this.initGameView.getBattleFieldView(i).setBattleFieldMouseListener(new BattleFieldMouseListener());
		this.initGameView.getBattleFieldView(i).setBattleFieldMouseMotionListener(new BattleFieldMouseMotionListener());
	
		this.initGameView.setShipsSelectionListener(new ShipsListener());
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
			player.printShipOnField(ship,  initGameView.getBattleFieldView(playerId));
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


	private void setShipsOrientation(int x, int y){
		JButton[][] field = initGameView.getBattleField(playerId);
		int length = 0;
		int count = 0;
		if(choosenShip instanceof Destroyer){
			length = destroyer[0].getShipSize();
		}
		else if(choosenShip instanceof Frigate){
			length = frigate[0].getShipSize();
		}
		else if(choosenShip instanceof Corvette){
			length = corvette[0].getShipSize();
		}
		else if(choosenShip instanceof Submarine){
			length = submarine[0].getShipSize();
		}

		for(int i = 0; i < field.length; i++){
			for(int j = 0; j < field.length; j++){
				field[i][j].setBorder(BorderFactory.createLineBorder(Color.gray));	
			}	
		}

		if(choosenShip == null){
			field[x-1][y-1].setBorder(BorderFactory.createLineBorder(Color.red));	
		}
		else{
			boolean free = checkBorder(x, y, length);
			if(free == true){
				if(orientation == 'h'){
					for(int i = (x-1); i < (x-1)+length; i++){
						field[i][y-1].setBorder(BorderFactory.createLineBorder(Color.green));
					}
				}
				else{
					for(int i = (y-1); i < (y-1)+length; i++){
						field[x-1][i].setBorder(BorderFactory.createLineBorder(Color.green));
					}

				}
			}
			else{
				field[x-1][y-1].setBorder(BorderFactory.createLineBorder(Color.red));	
			}
		}
	}

	private boolean checkBorder(int x, int y, int length){
		int[][] field = this.player[playerId].getPrivateField().getField();
		x--;
		y--;
		if(orientation == 'h'){
			if(x+length >= field.length){
				return false;
			}

			int xVal = 0;
			int yVal = 0;
			int lengthX = length;
			int lengthY = 1;

			if(x > 0){
				xVal = -1;
			}

			if( (x+length-1) < field.length){
				lengthX = length+1;
			}

			if(y > 0){
				yVal = -1;
			}

			if(y < field.length){
				lengthY = 2;
			}

			for(int i = xVal; i < lengthX; i++){
				for(int j = yVal; j < lengthY; j++){
					//System.out.println("X, Y: " + (y + j) + "," + (x + i));
					if(field[y + j][x + i] != 0){
						shipCanBePlaced = false;
						return false;
					}
				}
			}
		}
		else{
			if(y+length >= field.length){
				shipCanBePlaced = false;
				return false;
			}

			int xVal = 0;
			int yVal = 0;
			int lengthX = 1;
			int lengthY = length;

			if(y > 0){
				yVal = -1;
			}

			if( (y+length-1) < field.length){
				lengthY = length+1;
			}

			if(x > 0){
				xVal = -1;
			}

			if(x < field.length){
				lengthX = 2;
			}
			for(int i = yVal; i < lengthY; i++){
				for(int j = xVal; j < lengthX; j++){
					if(field[y + i][x + j] != 0){
						shipCanBePlaced = false;
						return false;
					}
				}
			}
		}
		shipCanBePlaced = true;
		return true;
	}


	private class BattleFieldMouseMotionListener implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			try{
				int mouse2 = e.getY();
				//				int mouseX = e.getX();
				JButton btn = (JButton)e.getSource();
				String ac = btn.getActionCommand();
				ac = ac.trim();
				String[] pos = ac.split(",");
				String xString = pos[0];
				String yString = pos[1];
				int btnX = Integer.parseInt(""+xString);
				//System.out.println(btnX);
				int btnY = Integer.parseInt(yString);

				mouse2 += ( btnY - 1) * initGameView.getCellSize();

				if(btnY == oldBtnY){
					if( (mouseY - 5) >= mouse2){
						orientation = 'h';
					}
					else if( (mouseY + 5) <=  mouse2){
						orientation = 'v';	
					}
				}
				oldBtnY = btnY;
				mouseY = mouse2;
				setShipsOrientation(btnX, btnY);
			}
			catch(Exception f){
				//
				System.out.println(f.getMessage());
			}
		}
	}


	private class BattleFieldMouseListener implements MouseListener{
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			Player playerOnTurn = player[playerId];

			boolean checked;

			if(choosenShip != null){
				if(shipCanBePlaced == true){
					JButton btn = (JButton)e.getSource();
					String pos = btn.getActionCommand();

					if(choosenShip instanceof Destroyer){
						checked = setShipToField(choosenShip, playerOnTurn, pos);
						if(checked == true){
							destroyerCount--;
							initGameView.decrementDestroyer(destroyerCount);
							if(destroyerCount <= 0){
								initGameView.setDestroyerDisabled();
								choosenShip = null;
							}
						}
					}
					else if(choosenShip instanceof Frigate){
						checked = setShipToField(choosenShip, playerOnTurn, pos);
						if(checked == true){
							frigateCount--;
							initGameView.decrementFrigate(frigateCount);
							if(frigateCount <= 0){
								initGameView.setFrigateDisabled();
								choosenShip = null;
							}
						}

					}
					else if(choosenShip instanceof Corvette){
						checked = setShipToField(choosenShip, playerOnTurn, pos);
						if(checked == true){
							corvetteCount--;
							initGameView.decrementCorvette(corvetteCount);
							if(corvetteCount <= 0){
								initGameView.setCorvetteDisabled();
								choosenShip = null;
							}
						}
					}

					else if(choosenShip instanceof Submarine){
						checked = setShipToField(choosenShip, playerOnTurn, pos);
						if(checked == true){
							submarineCount--;
							initGameView.decrementSubmarine(submarineCount);
							if(submarineCount <= 0){
								initGameView.setSubmarineDisabled();
								choosenShip = null;
							}
						}
					}
					totalShips--;
					if(totalShips <= 0){
						initGameView.enableFinish();
					}
				}
			}
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
				choosenShip = destroyer[destroyer.length - destroyerCount];
			}
			else if(btn.getActionCommand().equals("frigate")){
				initGameView.setChoosenShip(btn);
				choosenShip = frigate[frigate.length - frigateCount];
			}
			else if(btn.getActionCommand().equals("corvette")){
				initGameView.setChoosenShip(btn);
				choosenShip = corvette[corvette.length - corvetteCount];
			}
			else if(btn.getActionCommand().equals("submarine")){
				initGameView.setChoosenShip(btn);
				choosenShip = submarine[submarine.length - submarineCount];
			}
		}

	}



}
