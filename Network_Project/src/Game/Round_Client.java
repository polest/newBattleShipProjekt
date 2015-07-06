
package Game;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;

import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToggleButton;

import Network.Client;
import SaveGame.Save;
import Ships.Corvette;
import Ships.Destroyer;
import Ships.Frigate;
import Ships.Ship;
import Ships.Submarine;
import Tools.ColoredPrint;
import Tools.ColoredPrint.EPrintColor;


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
	private ColoredPrint colorPrint;
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

	public Round_Client(Player player, int fieldSize, int playerLength, String[] playerNames, Client client){
		this.player = player;
		this.playerOnTurn = 0;
		this.alivePlayer = playerLength;
		this.gegner = 0;
		this.schiff = 0;
		this.colorPrint = new ColoredPrint();
		this.fieldSize = fieldSize;
		this.orientation = 'h';
		this.playerLength = playerLength;
		this.playerNames = playerNames;
		this.client = client;
		this.roundView = new Round_View(player, this.fieldSize, playerLength, playerNames);
		//this.play();
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

	public void setPlayerOnTurn(int id){
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
			this.colorPrint.println(EPrintColor.RED, "Ungültige Eingabe");

		}
		return null;
	}

	private class ShipListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			JToggleButton ship = (JToggleButton)e.getSource();

			if(ship.getActionCommand().equals("destroyer") ){
				schiff = 1;
			}
			else if(ship.getActionCommand().equals("frigate") ){
				schiff = 2;
			}
			else if(ship.getActionCommand().equals("corvette") ){
				schiff = 3;
			}
			else if(ship.getActionCommand().equals("submarine") ){
				schiff = 4;
			}
		}
	}


	public void setPlayerDead(int id) {
		roundView.setPlayerDead(id);
	}

	public void setActive(int id) {
		if(this.player.getId() == id){
			roundView.setActive(id);
		}
	}


	public void setAttackReply(String gegner, String[] values, String pos, String orientation) {
		int[] coords = checkPos(pos);	
		roundView.setAttackReply(gegner, values, coords, orientation);
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

		if(player.getId() == playerOnTurn){

			if(gegner != playerOnTurn){

				if(schiff != 0){

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
				else{
					field[x-1][y-1].setBorder(BorderFactory.createLineBorder(Color.red));	
				}
			}
			else{
				field[x-1][y-1].setBorder(BorderFactory.createLineBorder(Color.red));
			}
		}
		else{
			field[x-1][y-1].setBorder(BorderFactory.createLineBorder(Color.red));
		}
	}

	private class PositionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(gegner != playerOnTurn){
				JButton btn = (JButton)e.getSource();

				String pos = btn.getActionCommand();
				int koords[] = new int[2];
				koords = checkPos(pos);
				Ship ship = null;
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

				if(ship != null){
					client.setAttack(shipString, (""+gegner), pos, (""+orientation) );
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
				//System.out.println(btnX);
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
			System.out.println("Gegner ist Spieler: " + gegner);
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

}

