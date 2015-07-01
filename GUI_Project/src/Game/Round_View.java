package Game;


import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import Tools.ImagePanel;

public class Round_View {


	private ImagePanel roundPan;
	private int fieldSize;
	//private JPanel enemyFieldsPan;
	private BattleField_View[] enemies;
	private BattleField_View shownField;
	private Player[] player;
	private JLabel[] clickLabel;
	private JLabel[] playerName;
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
		this.order = new int[player.length-1];
		for(int o = 1; o < player.length; o++){
			this.order[o-1] = o;
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

		//Restlichen Spieler in klein
		int enemysCount = player.length - 1;

		this.enemies = new BattleField_View[enemysCount];
		this.playerName = new JLabel[enemysCount];
		clickLabel = new JLabel[player.length - 1];

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

		int labelY = 70;
		int x = 530;
		int fieldY = 100;

		for(int i = 1; i <= enemysCount; i++){
			if(i == 4){
				x = 530 + miniFieldSize + 50;
				labelY = 70;
				fieldY = 100;
			}

			playerName[i-1] = new JLabel(player[i].getPlayerName());

			playerName[i-1].setBounds(x, labelY, 200, 30);
			this.roundPan.add(playerName[i-1]);

			clickLabel[i-1] = new JLabel();
			clickLabel[i-1].setBounds(x, fieldY, miniFieldSize, miniFieldSize);
			this.roundPan.add(clickLabel[i-1]);


			enemies[i-1] = player[i].getBattleFieldView();
			this.roundPan.add(enemies[i-1].getView());

			player[i].getBattleFieldView().setSize(x, fieldY, miniFieldSize);
			player[i].getBattleFieldView().getView().setBounds(x, fieldY, miniFieldSize, miniFieldSize);
			player[i].getBattleFieldView().getView().setVisible(true);

			player[i].getBattleFieldView().removeListener();

			labelY = fieldY + miniFieldSize;
			fieldY = labelY + 30;
		}
		this.destroyer = new JToggleButton();
		this.destroyer.setText("Zerstörer ");
		this.destroyer.setBounds(30, 450, 120, 30);
		this.roundPan.add(this.destroyer);

		this.frigate = new JToggleButton();
		this.frigate.setText("Fregatten ");
		this.frigate.setBounds(160, 450, 120, 30);
		this.roundPan.add(this.frigate);

		this.corvette = new JToggleButton();
		this.corvette.setText("Korvetten ");
		this.corvette.setBounds(290, 450, 120, 30);
		this.roundPan.add(this.corvette);

		this.submarine = new JToggleButton();
		this.submarine.setText("UBoote ");
		this.submarine.setBounds(420, 450, 120, 30);
		this.roundPan.add(this.submarine);

		this.messages = new JLabel();
		this.messages.setText("Speiler " + player[0].getPlayerName() + " ist an der Reihe");
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

	public void changePlayer(int selectedIndex){
		int selectedPlayerId = order[selectedIndex];
		int oldId = choosen;

		this.order[selectedIndex] = choosen;
		this.choosen = selectedPlayerId;

		System.out.println("Player: "+choosen);

		BattleField_View selectedPlayer = enemies[selectedIndex];
		BattleField_View oldPlayer = shownField;

		JLabel selectedName = playerName[selectedIndex];
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

		smallBounds[0] = selectedPlayer.getView().getX();
		smallBounds[1] = selectedPlayer.getView().getY();
		smallBounds[2] = selectedPlayer.getView().getWidth();
		smallBounds[3] = selectedPlayer.getView().getHeight();

		bigBounds[0] = oldPlayer.getView().getX();
		bigBounds[1] = oldPlayer.getView().getY();
		bigBounds[2] = oldPlayer.getView().getWidth();
		bigBounds[3] = oldPlayer.getView().getHeight();


		shownField = selectedPlayer;
		enemies[selectedIndex] = oldPlayer;

		shownField.setSize(bigBounds[0], bigBounds[1], bigBounds[2]);
		enemies[selectedIndex].setSize(smallBounds[0], smallBounds[1], smallBounds[2]);

		shownFieldPlayerName = selectedName;
		playerName[selectedIndex] = oldName;

		shownFieldPlayerName.setBounds(labelBigBounds[0], labelBigBounds[1], labelBigBounds[2], labelBigBounds[3]);
		playerName[selectedIndex].setBounds(labelSmallBounds[0], labelSmallBounds[1], labelSmallBounds[2], labelSmallBounds[3]);

		roundPan.repaint();
		roundPan.revalidate();
	}

	public JLabel[] getSwitchLabel(){
		return clickLabel;
	}

	public ImagePanel getPanel(){
		return this.roundPan;
	}

	public void setChangePlayerViewListener(MouseListener l) {
		for(int i = 0; i < clickLabel.length; i++){
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
		for(int i = 0; i < enemies.length; i++){
			for(int j = 0; j < enemies[i].getField().length; j++){
				for(int k = 0; k < enemies[i].getField().length; k++){
					enemies[i].getField()[j][k].addActionListener(l);
				}
			}
		}

		for(int j = 0; j < shownField.getField().length; j++){
			for(int k = 0; k < shownField.getField().length; k++){
				shownField.getField()[j][k].addActionListener(l);
			}
		}
	}

}
