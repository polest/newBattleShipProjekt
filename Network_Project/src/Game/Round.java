
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
import SaveGame.Save;
import Ships.Ship;
import Tools.ColoredPrint;
import Tools.ColoredPrint.EPrintColor;


public class Round implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6295162987841937031L;
	/**
	 * * @author ML, JL
	 * @version 25.03.15
	 */
	private Player[] player;
	private ColoredPrint colorPrint;
	private int fieldSize;
	private int gegner;
	private int schiff;
	private int playerOnTurn;
	private char orientation;
	private int mouseY;
	private int oldBtnY;
	private int alivePlayer;

	public Round(Player[] player, int fieldSize){
		this.player = player;
		this.playerOnTurn = 0;
		this.alivePlayer = player.length;
		this.gegner = 0;
		this.schiff = 0;
		this.colorPrint = new ColoredPrint();
		this.fieldSize = fieldSize;
		this.orientation = 'h';
//		this.roundView = new Round_View(this.fieldSize, player);
		//this.play();
	}

	public Player[] getPlayer() {
		return player;
	}

	public void setPlayer(Player[] player) {
		this.player = player;
	}

	public void play(){
		char orientation = 'h';
		String eingabe;
		int gegner = 0;
		int schiff = 0;
		int counter = 1;

		while(ende() > 1){
			for(int i = 0; i < player.length; i++){
				if(player[i].getIsActive()){

					if(player[i].getIsAlive()){

						if(player[i].checkIfAnyShipIsReady()){


							//TODO in den Event einbauen
							//						
						}else{
							player[i+1].setActive(true);
						}
					}
				}
			}
		}
		//Bei allen Schiffen die laden, wird die reloadTime um einen verringert. Ist diese = 0 sind sie wieder verfügbar.
		for(int j = 0; j < player.length; j++){
			player[j].reloadTimeCountdown();
		}

	}

//	/**
//	 * @param pos - die zu überprüfenden Koordinaten 
//	 * @return Gibt zurück, ob die eingegebenen Koordinaten korrekt sind
//	 */
//	private int[] checkPos(String pos){
//		try{
//			pos = pos.replaceAll("\\s+", "");
//
//			String[] sKoordinaten = pos.split(",");
//			int[] iKoordinaten = new int[2];
//
//			if(sKoordinaten.length != 2){
//				return null;
//			}
//			for(int i = 0; i < 2; i++){
//				int toInt = Integer.parseInt(sKoordinaten[i]);
//				if(toInt < 0 || toInt > fieldSize){
//					return null;
//				}
//				else{
//					iKoordinaten[i] = toInt;
//				}
//			}
//
//			return iKoordinaten;
//		}
//		catch(Exception e){
//			this.colorPrint.println(EPrintColor.RED, "Ungültige Eingabe");
//
//		}
//		return null;
//	}
//

	public int ende(){
		int counter = 0;
		for(int i = 0; i < player.length; i++){
			if(player[i].getIsAlive()){
				counter++;
			}
		}
		return counter;
	}



//	private class PositionListener implements ActionListener{
//		public void actionPerformed(ActionEvent e) {
//			int index = playerOnTurn % player.length;
//			if(gegner != index){
//				JButton btn = (JButton)e.getSource();
//
//				String pos = btn.getActionCommand();
//				int koords[] = new int[2];
//				koords = checkPos(pos);
//				Ship ship = null;
//
//				if(schiff == 1){
//					ship = player[index].getAvailableDestroyer();
//				}
//				else if(schiff == 2){
//					ship = player[index].getAvailableFrigate();
//				}
//				else if(schiff == 3){
//					ship = player[index].getAvailableCorvette();
//				}
//				else if(schiff == 4){
//					ship = player[index].getAvailableSubmarine();
//				}
//				else{
//					roundView.setMessage("Schiff muss ausgewählt werden!");
//				}
//
//				if(ship != null){
//					player[gegner].getPrivateField().setAttack(ship, koords,orientation, player[gegner]);
//					player[gegner].getPublicBattleFieldView().setAttack(ship, koords,orientation);
//					roundView.getPanel().repaint();
//					roundView.getPanel().revalidate();
//
//					player[index].setShipIsntReady(ship);
//
//
//					//Überprüft, ob der Gegner noch am Leben ist, wenn nicht wird isAlive auf false gesetzt.
//					if(player[gegner].getIsAlive() == false){
//						roundView.setMessage("GEGNER WURDE BESIEGT!!!");
//						roundView.setPlayerDead(gegner);
//						alivePlayer--;
//						if(alivePlayer == 1){
//							roundView.setMessage("Spieler " + player[playerOnTurn].getPlayerName() + "hat gewonnen!");
//						}
//					}
//					//			
//					//			if(ende() == 1){
//					//				this.colorPrint.println(EPrintColor.GREEN, "Herzlichen Glückwunsch, Spieler " + player[i].getPlayerName() + " hat gewonnen.");
//					//				System.exit(-1);
//					//			}
//
//					player[index].setActive(false);
//					System.out.println("playerOnTurn: " + playerOnTurn);
//					playerOnTurn++;	
//					index = playerOnTurn % player.length;
//
//					while(player[index].getIsAlive() == false){
//						if(player[index].checkIfAnyShipIsReady() == false){
//							roundView.setMessage("Spieler " + player[index].getPlayerName() + "hat keine geladenen Schiffe!");
//							playerOnTurn++;
//							index = playerOnTurn % player.length;
//						}
//					}
//					player[index].setActive(true);
//					roundView.setActive(index);
//
//					System.out.println("playerOnTurn: " + playerOnTurn);
//
//
//				}
//			}
//		}
//	}
	
//	private class ChangePlayerListener implements MouseListener{
//		@Override
//		public void mouseClicked(MouseEvent e) {
//			JLabel selectedLabel = (JLabel)e.getSource();
//			JLabel[] switchableLabels = roundView.getSwitchLabel();
//
//			for(int i = 0; i < switchableLabels.length; i++){
//				if(selectedLabel == switchableLabels[i]){
//					roundView.changePlayer(i);
//				}
//			}
//
//			gegner = roundView.getChoosenPlayer();
//			System.out.println("Gegner ist Spieler: " + gegner);
//		}
//
//		@Override
//		public void mousePressed(MouseEvent e) {
//			// TODO Auto-generated method stub	
//		}
//
//		@Override
//		public void mouseReleased(MouseEvent e) {
//			// TODO Auto-generated method stub
//		}
//
//		@Override
//		public void mouseEntered(MouseEvent e) {
//			// TODO Auto-generated method stub
//		}
//
//		@Override
//		public void mouseExited(MouseEvent e) {
//			// TODO Auto-generated method stub
//
//		}
//	}

}
