package Game;


import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToggleButton;

import Tools.ImagePanel;

public class Round_View implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -2275181958920926173L;
	private ImagePanel roundPan;
	private int fieldSize;
	//private JPanel enemyFieldsPan;
	//private BattleField_View[] enemies;
	//private BattleField_View shownField;
	private JButton[][] clickButtons;
	//private Player[] playerGroup;
	private int playerLength;
	private JLabel[] clickLabel;
	private JLabel[] enemiesName;
	private JLabel shownFieldPlayerName;
	private int[] order;
	private int choosen;
	private JToggleButton destroyer;
	private JToggleButton frigate;
	private JToggleButton corvette;
	private JToggleButton submarine;
	private JLabel messages;
	private Player player;
	private String[] playerNames;
	private BattleField_View[] playerGroup;

	public Round_View(Player player, int fieldSize, int playerLength, String[] playerNames){
		this.roundPan = new ImagePanel("Resources/unterwasser.jpg");
		this.roundPan.setLayout(null);
		this.fieldSize = fieldSize;
		this.playerLength = playerLength;
		this.playerNames = playerNames;
		this.player = player;
		this.playerGroup = new BattleField_View[playerLength];
		//		this.player = player;
		initRoundView();
	}

	private void initRoundView(){
		this.playerGroup[0] = this.player.getBattleFieldView();
		
		int cellSize = 400/this.fieldSize;
		this.clickButtons = new JButton[this.fieldSize][this.fieldSize];
		for(int i = 0; i < this.fieldSize; i++){
			for(int j = 0; j < this.fieldSize; j++){
				this.clickButtons[i][j] = new JButton();
				this.clickButtons[i][j].setOpaque(false);
				this.clickButtons[i][j].setBorderPainted(true);
				this.clickButtons[i][j].setBounds(30 + (cellSize*i), 100 + (cellSize*j) , cellSize, cellSize);
				this.clickButtons[i][j].setVisible(true);
				this.clickButtons[i][j].setContentAreaFilled(false);
				this.clickButtons[i][j].setBorder(BorderFactory.createLineBorder(Color.gray));
				this.clickButtons[i][j].setActionCommand(" " + (i+1) + "," + (j+1));
				this.roundPan.add(this.clickButtons[i][j]);

			}
		}

		//Restlichen Spieler in klein
		int enemysCount = playerLength - 1;


		int miniFieldSize;
		if(enemysCount > 3){
			miniFieldSize = 300 / 3;
		}
		else if(enemysCount > 1){
			miniFieldSize = 300/enemysCount;
		}
		else{
			miniFieldSize = 200;
		}

		int xx = 530;
		int yy = 100;

		this.clickLabel = new JLabel[playerLength];

		for(int k = 1; k < playerLength; k++){
			if(k == 4){
				xx = 530 + miniFieldSize + 50;
				yy = 100;
			}
			clickLabel[k] = new JLabel();
			this.roundPan.add(clickLabel[k]);
			clickLabel[k].setBounds(xx, yy, miniFieldSize, miniFieldSize);
			yy += miniFieldSize  + 30;
		}

		this.order = new int[playerLength];
		for(int o = 0; o < playerLength; o++){
			this.order[o] = o;
		}
		this.choosen = 0;
		//Erster Spieler in groß anzeigen
		shownFieldPlayerName = new JLabel(player.getPlayerName());

		shownFieldPlayerName.setBounds(30, 70, 200, 30);
		this.roundPan.add(shownFieldPlayerName);

		this.roundPan.add(playerGroup[0].getView());
		this.roundPan.add(playerGroup[0].getView());

		playerGroup[0].setSize(30,100, 400);
		playerGroup[0].getView().setVisible(true);
		playerGroup[0].removeListener();


		//this.enemies = new BattleField_View[enemysCount];
		this.enemiesName = new JLabel[enemysCount];

		int labelY = 70;
		int x = 530;
		int fieldY = 100;

		for(int i = 1; i <= enemysCount; i++){
			
			if(i == 4){
				x = 530 + miniFieldSize + 50;
				labelY = 70;
				fieldY = 100;
			}

			enemiesName[i-1] = new JLabel(playerNames[i]);

			enemiesName[i-1].setBounds(x, labelY, 200, 30);
			
			this.playerGroup[i] = new BattleField_View(roundPan, fieldSize, miniFieldSize, x, fieldY);
			
			this.roundPan.add(enemiesName[i-1]);
			this.roundPan.add(playerGroup[i].getView());

			playerGroup[i].setSize(x, fieldY, miniFieldSize);
			playerGroup[i].getView().setBounds(x, fieldY, miniFieldSize, miniFieldSize);
			playerGroup[i].getView().setVisible(true);
			
			playerGroup[i].removeListener();

			labelY = fieldY + miniFieldSize;
			fieldY = labelY + 30;
		}

		this.destroyer = new JToggleButton();
		this.destroyer.setText("Zerstörer ");
		this.destroyer.setBounds(30, 500, 120, 30);
		this.roundPan.add(this.destroyer);

		this.frigate = new JToggleButton();
		this.frigate.setText("Fregatten ");
		this.frigate.setBounds(160, 500, 120, 30);
		this.roundPan.add(this.frigate);

		this.corvette = new JToggleButton();
		this.corvette.setText("Korvetten ");
		this.corvette.setBounds(290, 500, 120, 30);
		this.roundPan.add(this.corvette);

		this.submarine = new JToggleButton();
		this.submarine.setText("UBoote ");
		this.submarine.setBounds(420, 500, 120, 30);
		this.roundPan.add(this.submarine);

		this.messages = new JLabel();
		this.messages.setText("Spieler " + player.getPlayerName() + " ist an der Reihe");
		this.messages.setForeground(Color.red);
		this.messages.setBounds(30, 20, 500, 60);
		this.roundPan.add(messages);
	}

	public void setDestroyer(int count){
		this.destroyer.setText("Zerstörer " + count);
		this.destroyer.setActionCommand("destroyer");
		if(count <= 0){
			this.destroyer.setEnabled(false);
		}
		else{
			this.destroyer.setEnabled(true);
		}
	}

	public void setFrigate(int count){
		this.frigate.setText("Fregatte " + count);
		this.frigate.setActionCommand("frigate");
		if(count <= 0){
			this.frigate.setEnabled(false);
		}
		else{
			this.frigate.setEnabled(true);
		}
	}

	public void setCorvette(int count){
		this.corvette.setText("Korvette " + count);
		this.corvette.setActionCommand("corvette");
		if(count <= 0){
			this.corvette.setEnabled(false);
		}
		else{
			this.corvette.setEnabled(true);
		}
	}

	public void setSubmarine(int count){
		this.submarine.setText("UBoot " + count);
		this.submarine.setActionCommand("submarine");
		if(count <= 0){
			this.submarine.setEnabled(false);
		}
		else{
			this.submarine.setEnabled(true);
		}
	}

	public int getShownPanCellSize(){
		return playerGroup[order[0]].getCellSize();
	}

	public JButton[][] getShownPanField(){
		return this.playerGroup[order[0]].getField();
	}

	public JButton[][] getClickField(){
		return this.clickButtons;
	}

	public void setMessage(String txt){
		this.messages.setText(txt);
	}

	public void setPlayerDead(int i){
		//this.player[i].getBattleFieldView().getView().setEnabled(false);
		for(int k = 0; k < this.playerGroup[i].getField().length; k++){
			for(int j = 0; j < this.playerGroup[i].getField().length; j++){
				this.playerGroup[i].getField()[k][j].setEnabled(false);
			}
		}
	}

	public void setActive(int i){
		for(int j = 0; j < order.length; j++){
			if(order[j] == i){
				nextRound(j);
			}
		}
	}

	public int getEnemyIndex(){
		return this.choosen;
	}


	public void changePlayer(int selectedIndex){
		int[] smallBounds = new int[4];
		int[] bigBounds = new int[4];

		smallBounds[0] = clickLabel[selectedIndex].getX();
		smallBounds[1] = clickLabel[selectedIndex].getY();
		smallBounds[2] = clickLabel[selectedIndex].getWidth();
		smallBounds[3] = clickLabel[selectedIndex].getHeight();

		bigBounds[0] = 30;
		bigBounds[1] = 100;
		bigBounds[2] = 400;
		bigBounds[3] = 400;

		//ausgewähtlen Spieler aus der Gegner Liste als Gegner setzen bzw zur Hauptansicht
		int selectedPlayerId = order[selectedIndex];
		System.out.println("Spieler " + selectedPlayerId + " wurde ausgewählt!");
		//Spieler aus der Großansicht sichern, um diesen später 
		//in die richtige reinhenfolge der Gegner hinzuzufügen
		int oldId = this.choosen;
		System.out.println("Vorheriger spieler: " + oldId);
		this.order[0] = selectedPlayerId;
		this.order[selectedIndex] = oldId;
		this.choosen = selectedPlayerId;
		System.out.println("Gegner ist: "+choosen);

		String selectedName = playerNames[this.order[0]];
		String oldName = shownFieldPlayerName.getText();


		//		shownField = selectedPlayer;
		//		enemies[selectedIndex-1] = oldPlayerField;

		enemiesName[selectedIndex-1].setText(oldName);
		shownFieldPlayerName.setText(selectedName);


		playerGroup[order[selectedIndex]].setSize(smallBounds[0], smallBounds[1], smallBounds[2]);
		playerGroup[order[selectedIndex]].getView().repaint();
		playerGroup[order[selectedIndex]].getView().revalidate();

	
		playerGroup[order[0]].setSize(bigBounds[0], bigBounds[1], bigBounds[2]);
		playerGroup[order[0]].getView().repaint();
		playerGroup[order[0]].getView().revalidate();
		roundPan.repaint();
		roundPan.revalidate();
	}


	public void nextRound(int selectedIndex){

		int[] smallBoundsY = new int[clickLabel.length-1];
		for(int i = 1; i <= smallBoundsY.length; i++){
			smallBoundsY[i-1] = clickLabel[i].getY();
		}

		int[] smallBoundsX = new int[clickLabel.length-1];
		for(int i = 1; i <= smallBoundsX.length; i++){
			smallBoundsX[i-1] = clickLabel[i].getX();
		}

		int smallWidth = clickLabel[1].getWidth();

		int[] bigBounds = new int[4];

		bigBounds[0] = 30;
		bigBounds[1] = 100;
		bigBounds[2] = 400;
		bigBounds[3] = 400;


		int index = order[selectedIndex];
		
		shownFieldPlayerName.setText(playerNames[index] );
		order[0] = index;
		playerGroup[index].setSize(bigBounds[0], bigBounds[1], bigBounds[2]);
		

		this.setMessage("Spieler " + playerNames[index] + " ist an der Reihe!");

		int counter = 0;
		int orderCounter = 1;

		for(int i = 0; i < playerLength; i++){
			if(i != index){
				order[orderCounter] = i;

				playerGroup[i].setSize(smallBoundsX[counter], smallBoundsY[counter], smallWidth);
				playerGroup[i].getView().setVisible(true);
				playerGroup[i].getView().setVisible(false);
				enemiesName[counter].setText(playerNames[i] );
				System.out.println("c: "+ counter + " name: "+playerNames[i]);
				counter++;
				orderCounter++;

			}
		}

		//		shownField.setSize(bigBounds[0], bigBounds[1], bigBounds[2]);

		roundPan.repaint();
		roundPan.revalidate();
	}

	public JLabel[] getSwitchLabel(){
		return clickLabel;
	}

	public ImagePanel getPanel(){
		return this.roundPan;
	}

	public int getChoosenPlayer(){
		return this.choosen;
	}

	public void setChangePlayerViewListener(MouseListener l) {
		for(int i = 1; i < clickLabel.length; i++){
			clickLabel[i].addMouseListener(l);
		}
	}

	public void setShipsListener(ActionListener l){
		this.destroyer.addActionListener(l);
		this.corvette.addActionListener(l);
		this.frigate.addActionListener(l);
		this.submarine.addActionListener(l);
	}

	public void setPositionListener(ActionListener l){
		//		for(int i = 0; i < enemies.length; i++){
		//			for(int j = 0; j < enemies[i].getField().length; j++){
		//				for(int k = 0; k < enemies[i].getField().length; k++){
		//					enemies[i].getField()[j][k].addActionListener(l);
		//				}
		//			}
		//		}

		for(int j = 0; j < clickButtons.length; j++){
			for(int k = 0; k < clickButtons.length; k++){
				clickButtons[j][k].addActionListener(l);
			}
		}
	}

	public void setOrientationListener(MouseMotionListener l){
		//		for(int i = 0; i < enemies.length; i++){
		//			for(int j = 0; j < enemies[i].getField().length; j++){
		//				for(int k = 0; k < enemies[i].getField().length; k++){
		//					enemies[i].getField()[j][k].addMouseMotionListener(l);
		//				}
		//			}
		//		}

		for(int j = 0; j < clickButtons.length; j++){
			for(int k = 0; k < clickButtons.length; k++){
				clickButtons[j][k].addMouseMotionListener(l);
			}
		}
	}
}
