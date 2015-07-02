package Game;


import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToggleButton;

import Tools.ImagePanel;

public class Round_View {


	private ImagePanel roundPan;
	private int fieldSize;
	//private JPanel enemyFieldsPan;
	private BattleField_View[] enemies;
	private BattleField_View shownField;
	private JButton[][] clickButtons;
	private Player[] player;
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


	public Round_View(int fieldSize, Player[] player){
		this.roundPan = new ImagePanel("Resources/unterwasser.jpg");
		this.roundPan.setLayout(null);
		this.fieldSize = fieldSize;
		this.player = player;
		initRoundView();
	}

	private void initRoundView(){

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
		int enemysCount = player.length - 1;


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

		this.clickLabel = new JLabel[player.length];
		
		for(int k = 1; k < player.length; k++){
			if(k == 4){
				xx = 530 + miniFieldSize + 50;
				yy = 100;
			}
			clickLabel[k] = new JLabel();
			this.roundPan.add(clickLabel[k]);
			clickLabel[k].setBounds(xx, yy, miniFieldSize, miniFieldSize);
			yy += miniFieldSize  + 30;
		}

		this.order = new int[player.length];
		for(int o = 0; o < player.length; o++){
			this.order[o] = o;
		}
		this.choosen = 0;
		//Erster Spieler in groß anzeigen
		shownFieldPlayerName = new JLabel(player[0].getPlayerName());

		shownFieldPlayerName.setBounds(30, 70, 200, 30);
		this.roundPan.add(shownFieldPlayerName);

		this.shownField =  new BattleField_View();
		this.shownField = player[0].getBattleFieldView();
		this.roundPan.add(shownField.getView());

		player[0].getBattleFieldView().setSize(30,100, 400);
		player[0].getBattleFieldView().getView().setVisible(true);
		player[0].getBattleFieldView().removeListener();

		this.enemies = new BattleField_View[enemysCount];
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

			enemiesName[i-1] = new JLabel(player[i].getPlayerName());

			enemiesName[i-1].setBounds(x, labelY, 200, 30);
			this.roundPan.add(enemiesName[i-1]);

			enemies[i-1] = player[i].getPublicBattleFieldView();
			this.roundPan.add(enemies[i-1].getView());

			player[i].getPublicBattleFieldView().setSize(x, fieldY, miniFieldSize);
			player[i].getPublicBattleFieldView().getView().setBounds(x, fieldY, miniFieldSize, miniFieldSize);
			player[i].getPublicBattleFieldView().getView().setVisible(true);

			player[i].getBattleFieldView().removeListener();

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
		this.messages.setText("Spieler " + player[0].getPlayerName() + " ist an der Reihe");
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
		return this.shownField.getCellSize();
	}

	public JButton[][] getShownPanField(){
		return this.shownField.getField();
	}

	public JButton[][] getClickField(){
		return this.clickButtons;
	}

	public void setMessage(String txt){
		this.messages.setText(txt);
	}

	public void setPlayerDead(int i){
		this.player[i].getBattleFieldView().getView().setEnabled(false);
	}

	public void setActive(int i){
		int playerOnTurn;

		for(int j = 0; j < order.length; j++){
			if(order[j] == i){
				playerOnTurn = j;
				changePlayer(playerOnTurn, true);
			}
		}
	}

	public int getEnemyIndex(){
		return this.choosen;
	}


	public void changePlayer(int selectedIndex, boolean newRound){
		//ausgewähtlen Spieler aus der Gegner Liste als Gegner setzen bzw zur Hauptansicht
		int selectedPlayerId = order[selectedIndex];
		//Spieler aus der Großansicht sichern, um diesen später 
		//in die richtige reinhenfolge der Gegner hinzuzufügen
		int oldId = this.choosen;

		this.order[selectedIndex] = this.choosen;
		this.choosen = selectedPlayerId;

		System.out.println("Gegner: "+choosen);

		BattleField_View selectedPlayer = enemies[selectedIndex-1];
		BattleField_View oldPlayerField = shownField;

		JLabel selectedName = enemiesName[selectedIndex-1];
		JLabel oldName = shownFieldPlayerName;

		int[] labelSmallBounds = new int[4];
		int[] labelBigBounds = new int[4];

		labelSmallBounds[0] = selectedName.getX();
		labelSmallBounds[1] = selectedName.getY();
		labelSmallBounds[2] = selectedName.getWidth();
		labelSmallBounds[3] = selectedName.getHeight();

		labelBigBounds[0] = oldName.getX();
		labelBigBounds[1] = oldName.getY();
		labelBigBounds[2] = oldName.getWidth();
		labelBigBounds[3] = oldName.getHeight();

		int[] smallBounds = new int[4];
		int[] bigBounds = new int[4];

		smallBounds[0] = enemies[selectedIndex-1].getView().getX();
		smallBounds[1] = enemies[selectedIndex-1].getView().getY();
		smallBounds[2] = enemies[selectedIndex-1].getView().getWidth();
		smallBounds[3] = enemies[selectedIndex-1].getView().getHeight();

		bigBounds[0] = shownField.getView().getX();
		bigBounds[1] = shownField.getView().getY();
		bigBounds[2] = shownField.getView().getWidth();
		bigBounds[3] = shownField.getView().getHeight();

		if(newRound == true){
			shownField = player[selectedIndex].getBattleFieldView();
			shownFieldPlayerName.setText(player[selectedIndex].getPlayerName() );

			for(int i = 0; i < player.length; i++){
				int counter = 0;
				if(i != selectedIndex){
					enemies[counter] = player[i].getPublicBattleFieldView();
					enemiesName[counter].setText(player[i].getPlayerName() );

					enemies[counter].setSize(smallBounds[0], smallBounds[1], smallBounds[2]);
					enemiesName[counter].setBounds(labelSmallBounds[0], labelSmallBounds[1], labelSmallBounds[2], labelSmallBounds[3]);
				}
			}

		}else{
			shownField = selectedPlayer;
			enemies[selectedIndex-1] = oldPlayerField;

			enemiesName[selectedIndex-1] = oldName;
			shownFieldPlayerName = selectedName;

			enemies[selectedIndex-1].setSize(smallBounds[0], smallBounds[1], smallBounds[2]);
			enemiesName[selectedIndex-1].setBounds(labelSmallBounds[0], labelSmallBounds[1], labelSmallBounds[2], labelSmallBounds[3]);
		}


		shownField.setSize(bigBounds[0], bigBounds[1], bigBounds[2]);
		shownFieldPlayerName.setBounds(labelBigBounds[0], labelBigBounds[1], labelBigBounds[2], labelBigBounds[3]);

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
