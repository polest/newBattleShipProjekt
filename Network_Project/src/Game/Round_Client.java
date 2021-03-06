
package Game;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;

import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToggleButton;

import Network.Client;
import SaveGame.Save;
import Ships.Corvette;
import Ships.Destroyer;
import Ships.Frigate;
import Ships.Submarine;


public class Round_Client implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6295162987841937031L;
	/**
	 * * @author ML, JL
	 * @version 25.03.15
	 */
	private Player player;
	private int fieldSize;
	private Round_View roundView;
	private int gegner;
	private int schiff;
	private int playerOnTurn;
	private char orientation;
	private int mouseY;
	private int oldBtnY;
	private int alivePlayer;
	private int playerLength;
	private String[] playerNames;
	private Client client;
	private int ship;

	public Round_Client(Player player, int fieldSize, int playerLength, String[] playerNames, Client client){
		this.player = player;
		this.playerOnTurn = 0;
		this.alivePlayer = playerLength;
		this.gegner = 0;
		this.schiff = 0;
		this.fieldSize = fieldSize;
		this.orientation = 'h';
		this.playerLength = playerLength;
		this.playerNames = playerNames;
		this.client = client;
		this.roundView = new Round_View(player, this.fieldSize, playerLength, playerNames);
		this.ship = -1;
		addListener();
		setShipText();
	}


	public Round_View getRoundView(){
		return this.roundView;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	private void addListener(){
		this.roundView.setChangePlayerViewListener(new ChangePlayerListener());
		this.roundView.setShipsListener(new ShipListener());
		this.roundView.setPositionListener(new PositionListener());
		this.roundView.setOrientationListener(new OrientationListener());
	}

	private void setShipText(){
		roundView.setDestroyer(player.getDestroyer().length);
		roundView.setFrigate(player.getFrigate().length);
		roundView.setCorvette(player.getCorvette().length);
		roundView.setSubmarine(player.getSubmarine().length);
	}

	public void setPlayerOnTurn(int id, String name){
		this.roundView.setMessage("Spieler " + name + " ist an der Reihe");
		this.playerOnTurn = id;
		this.roundView.setActive(id);
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
			return null;
		}
	}

	private class ShipListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			JToggleButton shipBtn = (JToggleButton)e.getSource();

			if(shipBtn.getActionCommand().equals("destroyer") ){
				if(shipBtn.isSelected() == true){
					schiff = 1;
					ship = player.getAvailableDestroyer();
					roundView.setFrigateSelected(false);
					roundView.setCorvetteSelected(false);
					roundView.setSubmarineSelected(false);
				}
				else{
					schiff = 0;
					ship = -1;	
				}
			}
			else if(shipBtn.getActionCommand().equals("frigate") ){
				if(shipBtn.isSelected() == true){
					schiff = 2;
					ship = player.getAvailableFrigate();
					roundView.setDestroyerSelected(false);
					roundView.setCorvetteSelected(false);
					roundView.setSubmarineSelected(false);
				}
				else{
					schiff = 0;
					ship = -1;	
				}
			}
			else if(shipBtn.getActionCommand().equals("corvette") ){
				if(shipBtn.isSelected() == true){
					schiff = 3;
					ship = player.getAvailableCorvette();
					roundView.setFrigateSelected(false);
					roundView.setDestroyerSelected(false);
					roundView.setSubmarineSelected(false);
				}
				else{
					schiff = 0;
					ship = -1;
				}
			}
			else if(shipBtn.getActionCommand().equals("submarine") ){
				if(shipBtn.isSelected() == true){
					schiff = 4;
					ship = player.getAvailableSubmarine();
					roundView.setFrigateSelected(false);
					roundView.setCorvetteSelected(false);
					roundView.setDestroyerSelected(false);
				}
				else{
					schiff = 0;
					ship = -1;

				}
			}
		}
	}


	public void setPlayerDead(int id) {
		roundView.setPlayerDead(id);
	}

	public void setActive(int id, boolean nextPlayer) {
		this.playerOnTurn = id;
		this.roundView.setMessage("Spieler " + this.playerNames[playerOnTurn] + " ist an der Reihe!");
	}

	public void reloadTime(){
		player.reloadTimeCountdown();
		setShipReadyOrNot();
	}

	public void setAttackReply(String gegner, String[] values, String pos, String orientation) {
		int[] coords = checkPos(pos);	
		roundView.setAttackReply(gegner, values, coords, orientation);
		this.ship = 0;
		setShipReadyOrNot();
	}

	public void setPlayerShipIsntReady(String shipName, int shipId){
		this.player.setShipIsntReady(shipName, shipId);
		setShipReadyOrNot();
	}

	public void setShipReadyOrNot(){
		if(this.player.getAvailableDestroyer() == -1){
			roundView.setDestroyerEnabled(false);
		}else{
			roundView.setDestroyerEnabled(true);
		}

		if(this.player.getAvailableFrigate() == -1){
			roundView.setFrigateEnabled(false);
		}else{
			roundView.setFrigateEnabled(true);
		}

		if(this.player.getAvailableCorvette() == -1){
			roundView.setCorvetteEnabled(false);
		}else{
			roundView.setCorvetteEnabled(true);
		}

		if(this.player.getAvailableSubmarine() == -1){
			roundView.setSubmarineEnabled(false);
		}else{
			roundView.setSubmarineEnabled(true);
		}
	}

	private void setShipsOrientation(int x, int y ){
		JButton[][] field = roundView.getClickField();
		int length = 0;
		int count = 0;
		gegner = roundView.getEnemyIndex();

		for(int i = 0; i < field.length; i++){
			for(int j = 0; j < field.length; j++){
				field[i][j].setBorder(BorderFactory.createLineBorder(Color.gray));	
			}	
		}

		boolean setGreen = false;

		if(player.getId() == playerOnTurn){
			if(gegner != playerOnTurn){

				if(schiff != 0){
					setGreen = true;
					if(schiff == 1){
						length = Destroyer.shootArea;
					}
					else if(schiff == 2){
						length = Frigate.shootArea;
					}
					else if(schiff == 3){
						length = Corvette.shootArea;
					}
					else if(schiff == 4){
						length = Submarine.shootArea;
					}

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
			}
		}

		if(setGreen == false){
			field[x-1][y-1].setBorder(BorderFactory.createLineBorder(Color.red));	
		}
	}


	private class PositionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(player.getIsAlive() == true){
				if(playerOnTurn == player.getId()){
					if(gegner != playerOnTurn){
						JButton btn = (JButton)e.getSource();

						String pos = btn.getActionCommand();
						int koords[] = new int[2];
						koords = checkPos(pos);
						String shipString = "";

						if(schiff == 1){
							ship = player.getAvailableDestroyer();
							shipString = "destroyer";
						}
						else if(schiff == 2){
							ship = player.getAvailableFrigate();
							shipString = "frigate";
						}
						else if(schiff == 3){
							ship = player.getAvailableCorvette();
							shipString = "corvette";
						}
						else if(schiff == 4){
							ship = player.getAvailableSubmarine();
							shipString = "submarine";
						}
						else{
							roundView.setMessage("Schiff muss ausgewählt werden!");
						}

						if(schiff > 0){
							client.setAttack(shipString, (""+gegner), pos, (""+orientation) );
							roundView.setDestroyerSelected(false);
							roundView.setFrigateSelected(false);
							roundView.setCorvetteSelected(false);
							roundView.setSubmarineSelected(false);
//							if(ship != -1){
//								player.setShipIsntReady(shipString, ship);
//							}
						}

						schiff = 0;
					}
				}
			}
		}
	}

	private class OrientationListener implements MouseMotionListener{

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
				int btnY = Integer.parseInt(yString);

				mouse2 += ( btnY - 1) * roundView.getShownPanCellSize();

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

	private class ChangePlayerListener implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			JLabel selectedLabel = (JLabel)e.getSource();
			JLabel[] switchableLabels = roundView.getSwitchLabel();

			for(int i = 0; i < switchableLabels.length; i++){
				if(selectedLabel == switchableLabels[i]){
					roundView.changePlayer(i);
				}
			}
			gegner = roundView.getChoosenPlayer();
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


	public void setShipIsSunk(String shipName) {
		if(shipName.equals("destroyer")){
			this.player.destroyerSunk();
			this.roundView.setDestroyerBtn();
		}
		else if(shipName.equals("frigate")){
			this.player.frigateSunk();
			this.roundView.setFrigateBtn();
		}
		else if(shipName.equals("corvette")){
			this.player.corvetteSunk();
			this.roundView.setCorvetteBtn();
		}
		if(shipName.equals("submarine")){
			this.player.submarineSunk();
			this.roundView.setSubmarineBtn();
		}
	}

}

