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

		//this.shownField =  new BattleField_View();
		//this.shownField = player[0].getBattleFieldView();
		//this.roundPan.add(shownField.getView());
		this.roundPan.add(player[0].getBattleFieldView().getView());
		this.roundPan.add(player[0].getPublicBattleFieldView().getView());
		
		player[0].getBattleFieldView().setSize(30,100, 400);
		player[0].getBattleFieldView().getView().setVisible(true);
		player[0].getBattleFieldView().removeListener();

		player[0].getPublicBattleFieldView().setSize(30,100, 400);
		player[0].getPublicBattleFieldView().getView().setVisible(false);
		player[0].getPublicBattleFieldView().removeListener();

		
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

			enemiesName[i-1] = new JLabel(player[i].getPlayerName());

			enemiesName[i-1].setBounds(x, labelY, 200, 30);
			this.roundPan.add(enemiesName[i-1]);

			//enemies[i-1] = player[i].getPublicBattleFieldView();
			this.roundPan.add(player[i].getPublicBattleFieldView().getView());
			this.roundPan.add(player[i].getBattleFieldView().getView());

			player[i].getPublicBattleFieldView().setSize(x, fieldY, miniFieldSize);
			player[i].getPublicBattleFieldView().getView().setBounds(x, fieldY, miniFieldSize, miniFieldSize);
			player[i].getPublicBattleFieldView().getView().setVisible(true);

			player[i].getBattleFieldView().setSize(x, fieldY, miniFieldSize);
			player[i].getBattleFieldView().getView().setBounds(x, fieldY, miniFieldSize, miniFieldSize);
			player[i].getBattleFieldView().getView().setVisible(false);

			
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
		return this.player[order[0]].getBattleFieldView().getCellSize();
	}

	public JButton[][] getShownPanField(){
		return this.player[order[0]].getBattleFieldView().getField();
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
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		System.out.println("Reihenfolge: " + this.order[0] + ", " + this.order[1] + ", " + this.order[2]);
		this.choosen = selectedPlayerId;
		System.out.println("Gegner ist: "+choosen);


		//		BattleField_View selectedPlayer = enemies[selectedIndex-1];
		//		BattleField_View oldPlayerField = shownField;


		String selectedName = player[this.order[0]].getPlayerName();
		String oldName = shownFieldPlayerName.getText();


		//		shownField = selectedPlayer;
		//		enemies[selectedIndex-1] = oldPlayerField;

		enemiesName[selectedIndex-1].setText(oldName);
		shownFieldPlayerName.setText(selectedName);

		
		if(player[order[selectedIndex]].getIsActive()){
			player[order[selectedIndex]].getPublicBattleFieldView().getView().setVisible(false);
			player[order[selectedIndex]].getBattleFieldView().getView().setVisible(true);
			
			player[order[selectedIndex]].getBattleFieldView().setSize(smallBounds[0], smallBounds[1], smallBounds[2]);
			player[order[selectedIndex]].getBattleFieldView().getView().repaint();
			player[order[selectedIndex]].getBattleFieldView().getView().revalidate();

		}
		else{
			player[order[selectedIndex]].getPublicBattleFieldView().getView().setVisible(true);
			player[order[selectedIndex]].getBattleFieldView().getView().setVisible(false);
			
			player[order[selectedIndex]].getPublicBattleFieldView().setSize(smallBounds[0], smallBounds[1], smallBounds[2]);
			player[order[selectedIndex]].getPublicBattleFieldView().getView().repaint();
			player[order[selectedIndex]].getPublicBattleFieldView().getView().revalidate();

		}

		if(player[order[0]].getIsActive()){
			player[order[0]].getPublicBattleFieldView().getView().setVisible(false);
			player[order[0]].getBattleFieldView().getView().setVisible(true);
			
			player[order[0]].getBattleFieldView().setSize(bigBounds[0], bigBounds[1], bigBounds[2]);
			player[order[0]].getBattleFieldView().getView().repaint();
			player[order[0]].getBattleFieldView().getView().revalidate();
		}
		else{
			player[order[0]].getPublicBattleFieldView().getView().setVisible(true);
			player[order[0]].getBattleFieldView().getView().setVisible(false);
			
			
			player[order[0]].getPublicBattleFieldView().setSize(bigBounds[0], bigBounds[1], bigBounds[2]);
			player[order[0]].getPublicBattleFieldView().getView().repaint();
			player[order[0]].getPublicBattleFieldView().getView().revalidate();
		}
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
		//		shownField = player[index].getBattleFieldView();
		//		shownField.getView().repaint();
		//		shownField.getView().revalidate();


		shownFieldPlayerName.setText(player[index].getPlayerName() );
		order[0] = index;
		player[index].getBattleFieldView().setSize(bigBounds[0], bigBounds[1], bigBounds[2]);
		player[index].getBattleFieldView().getView().setVisible(true);
		player[index].getPublicBattleFieldView().getView().setVisible(false);

		
		this.setMessage("Spieler " + player[index].getPlayerName() + " ist an der Reihe!");

		int counter = 0;
		int orderCounter = 1;

		for(int i = 0; i < player.length; i++){
			if(i != index){
				order[orderCounter] = i;

				player[i].getPublicBattleFieldView().setSize(smallBoundsX[counter], smallBoundsY[counter], smallWidth);
				player[i].getPublicBattleFieldView().getView().setVisible(true);
				player[i].getBattleFieldView().getView().setVisible(false);
				enemiesName[counter].setText(player[i].getPlayerName() );
				System.out.println("c: "+ counter + " name: "+player[i].getPlayerName());
				//				enemies[counter].setSize(smallBoundsX[0], smallBoundsY[counter], smallWidth);
				//				enemies[counter].getView().setVisible(true);
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
