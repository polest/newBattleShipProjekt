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

import Network.Client;
import SaveGame.Load;
import Ships.Corvette;
import Ships.Destroyer;
import Ships.Frigate;
import Ships.Ship;
import Ships.Submarine;
import Tools.ColoredPrint;
import Tools.ColoredPrint.EPrintColor;

public class InitGame_Client implements Serializable{

	/**
	 * @author ML
	 * @version 21.03.15
	 */
	private static final long serialVersionUID = 988834907748712441L;

	//	private Options gameOptions;
	//	private Options_View gameOptionsView;
	private InitGame_View_Client initGameView;
	private Load load = new Load();
	private Player player;
	private String playerName;
	private int fieldSize;
	String[] listFileNames;
	private Round rounds;
	private int playerId;
	private Destroyer[] destroyer;
	private Frigate[] frigate;
	private Corvette[] corvette;
	private Submarine[] submarine;
	private int totalShips;
	private int destroyerCount;
	private int frigateCount;
	private int corvetteCount;
	private int submarineCount;
	private char orientation;
	private int mouseY;
	private int oldBtnY;
	private Ship choosenShip;
	private boolean shipCanBePlaced;
	private Client client;
	private int playerLength;


	public InitGame_Client(int playerLength, int destroyer, int frigate, int corvette, int submarine, int fieldSize, InitGame_View_Client initGameView){
		this.playerLength = playerLength;
		this.destroyerCount = destroyer;
		this.frigateCount =  frigate;
		this.corvetteCount = corvette;
		this.submarineCount = submarine;

		this.totalShips = this.destroyerCount + this.frigateCount + this.corvetteCount + this.submarineCount;

		this.initGameView = initGameView;
		this.playerId = 0;
		this.orientation = 'h';
		this.mouseY = 0;
		this.oldBtnY = 0;
		this.shipCanBePlaced = false;
		this.fieldSize = fieldSize;
		this.configureGame();
	}

	public Player getPlayer() {
		return player;
	}

	public int getPlayerLength(){
		return this.playerLength;
	}
	
	public int getFieldSize(){
		return this.fieldSize;
	}

	public void configureGame(){
		this.initGameView.setFieldSize(this.fieldSize);
		this.initGameView.initFields();
		initPlayerBattleShip();
	}

	public void incrementPlayerId(){
		this.playerId++;
	}

	public int getPlayerId(){
		return this.playerId;
	}

	public InitGame_View_Client getInitGameView(){
		return this.initGameView;
	}

	public Ship getChoosenShip(){
		return this.choosenShip;
	}

	public boolean getCanBePlaced(){
		return this.shipCanBePlaced;
	}
	
	public int getDestroyerCount(){
		return this.destroyerCount;
	}

	public int getFrigateCount(){
		return this.frigateCount;
	}
	
	public int getCorvetteCount(){
		return this.corvetteCount;
	}
	
	public int getSubmarineCount(){
		return this.submarineCount;
	}
	
	public void initPlayerBattleShip(){
		int i = this.playerId;
		BattleField battlefield = new BattleField(this.fieldSize);
		player = new Player(false, this.totalShips, this.destroyerCount, 
				this.frigateCount, this.corvetteCount,this.submarineCount, this.playerName, battlefield, false);
		addListener();
		this.initShips();
	}

	/**
	 *  Positionierung der Schiffe von jedem Spieler (nacheinander)
	 */
	private void initShips(){
		this.destroyer = player.getDestroyer();
		this.frigate = player.getFrigate();
		this.corvette = player.getCorvette();
		this.submarine = player.getSubmarine();

		String name = player.getPlayerName();
		this.initGameView.setPlayerName(name);

		this.initGameView.setDestroyer( player.getDestroyer().length );
		this.initGameView.setFrigate( player.getFrigate().length );
		this.initGameView.setCorvette( player.getCorvette().length );
		this.initGameView.setSubmarine( player.getSubmarine().length );

		boolean start = true;

		if(playerId > 0){
			start = false;
		}
		this.initGameView.initPlayerField(player, playerId);
	}

	private void addListener(){
		this.initGameView.getBattleFieldView().setBattleFieldMouseMotionListener(new BattleFieldMouseMotionListener());
		this.initGameView.setShipsSelectionListener(new ShipsListener());
		this.initGameView.getBattleFieldView().setBattleFieldMouseListener(new BattleFieldMouseListener());
	}

	public boolean setShipToField(Ship ship, String pos) {
		int length = ship.getShipSize();
		int[] koordinaten = checkPos(pos);

		if(player.getPrivateField().setShips(length, koordinaten[0], koordinaten[1], orientation) == true){
			if(orientation == 'h'){
				player.saveShipCoordinatesH(koordinaten[0], koordinaten[1], length);
				player.printShipOnField(ship,initGameView.getBattleFieldView(), koordinaten, 0);
				
			}
			else{
				player.saveShipCoordinatesV(koordinaten[0], koordinaten[1], length);
				player.printShipOnField(ship,initGameView.getBattleFieldView(), koordinaten, 1 );
				
			}
			return true;
		}
		else{
			return false;
		}

	}
	
	public void addClient(Client client){
		this.client = client;
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
		}
		return null;
	}



	private void setShipsOrientation(int x, int y){
		JButton[][] field = initGameView.getBattleField();
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
		shipCanBePlaced =  this.player.getPrivateField().checkFree(length, x, y, orientation);
		return shipCanBePlaced;
	}
	private class BattleFieldMouseListener implements MouseListener{
		public void mouseClicked(MouseEvent e) {
			System.out.println("totalShips: " + totalShips);
			boolean checked;
	
			if(choosenShip != null){
				if(shipCanBePlaced == true){
					JButton btn = (JButton)e.getSource();
					String pos = btn.getActionCommand();

					if(choosenShip instanceof Destroyer){
						checked = setShipToField(choosenShip, pos);

						if(checked == true){
							client.sendShipToServer("destroyer; " + pos);
							
//							TODO CLIENTREQUESTPROZEESOR!
//							out.println("setShipFromClient");
//							out.println("destroyer");
//							out.println(pos);
							destroyerCount--;
							initGameView.decrementDestroyer(destroyerCount);
							if(destroyerCount <= 0){
								initGameView.setDestroyerDisabled();
								choosenShip = null;
							}
						}
					}
					else if(choosenShip instanceof Frigate){
//						checked = initGame.setShipToField(choosenShip, pos);
//
//						if(checked == true){
//							out.println("setShipFromClient");
//							out.println("frigate");
//							out.println(pos);
//							frigateCount--;
//							initGameView.decrementFrigate(frigateCount);
//							if(frigateCount <= 0){
//								initGameView.setFrigateDisabled();
//								choosenShip = null;
//							}
//						}
					}
					else if(choosenShip instanceof Corvette){
//						checked = initGame.setShipToField(choosenShip, pos);
//
//						if(checked == true){
//							out.println("setShipFromClient");
//							out.println("corvette");
//							out.println(pos);
//
//							corvetteCount--;
//							initGameView.decrementCorvette(corvetteCount);
//							if(corvetteCount <= 0){
//								initGameView.setCorvetteDisabled();
//								choosenShip = null;
//							}
//						}
					}

					else if(choosenShip instanceof Submarine){
//						checked = initGame.setShipToField(choosenShip, pos);
//						if(checked == true){
//							out.println("setShipFromClient");
//							out.println("destroyer");
//							out.println(pos);
//
//							submarineCount--;
//							initGameView.decrementSubmarine(submarineCount);
//							if(submarineCount <= 0){
//								initGameView.setSubmarineDisabled();
//								choosenShip = null;
//							}
//						}
					}
					totalShips--;
					System.out.println("totalShips: " + totalShips);
					if(totalShips <= 0){
						initGameView.enableFinish();
					}
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

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
	private class BattleFieldMouseMotionListener implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			try{
				int mouse2 = e.getY();
				//int mouseX = e.getX();
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
				System.out.println(f.getMessage());
			}
		}
	}

	private class ShipsListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JToggleButton btn = (JToggleButton)e.getSource();

			if(btn.getActionCommand().equals("destroyer")){
				if(btn.isSelected() == true){
					initGameView.setChoosenShip(btn);
					choosenShip = destroyer[destroyer.length - destroyerCount];
				}
				else{
					initGameView.setChoosenShip(null);
					choosenShip = null;
				}
			}
			else if(btn.getActionCommand().equals("frigate")){
				if(btn.isSelected() == true){
					initGameView.setChoosenShip(btn);
					choosenShip = frigate[frigate.length - frigateCount];
				}
				else{
					initGameView.setChoosenShip(null);
					choosenShip = null;
				}

			}
			else if(btn.getActionCommand().equals("corvette")){
				if(btn.isSelected() == true){
					initGameView.setChoosenShip(btn);
					choosenShip = corvette[corvette.length - corvetteCount];
				}
				else{
					initGameView.setChoosenShip(null);
					choosenShip = null;
				}
			}
			else if(btn.getActionCommand().equals("submarine")){
				if(btn.isSelected() == true){
					initGameView.setChoosenShip(btn);
					choosenShip = submarine[submarine.length - submarineCount];
				}
				else{
					initGameView.setChoosenShip(null);
					choosenShip = null;
				}
			}
		}
	}
}
