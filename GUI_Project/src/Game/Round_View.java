package Game;


import javax.swing.JLabel;
import javax.swing.JPanel;

import Tools.ImagePanel;

public class Round_View {


	private ImagePanel roundPan;
	private int fieldSize;
	private JPanel enemyFieldsPan;
	private BattleField_View shownField;
	private Player[] player;

	public Round_View(int fieldSize, Player[] player){
		this.roundPan = new ImagePanel("Resources/unterwasser.jpg");
		this.roundPan.setLayout(null);
		this.fieldSize = fieldSize;
		this.player = player;
		initRoundView();
	}

	private void initRoundView(){
		//Erster Spieler in groß anzeigen
		JLabel shownFieldPlayerName = new JLabel(player[0].getPlayerName());

		shownFieldPlayerName.setBounds(30, 70, 200, 30);
		this.roundPan.add(shownFieldPlayerName);

		this.shownField =  new BattleField_View();
		this.shownField = player[0].getBattleFieldView();
		this.roundPan.add(shownField.getView());

		this.shownField.setSize(30,100, 400);
		this.shownField.getView().setVisible(true);
		
		//Restlichen Spieler in klein
		this.enemyFieldsPan = new JPanel();
		this.enemyFieldsPan.setLayout(null);
		this.enemyFieldsPan.setBounds(430, 70, 340, 450);
		this.enemyFieldsPan.setOpaque(false);
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

		int labelY = 0;
		int x = 30;
		int fieldY = 30;

		for(int i = 1; i <= enemysCount; i++){
			if(i == 4){
				x = miniFieldSize + 50;
				labelY = 0;
				fieldY = 30;
			}
			
			JLabel playerName = new JLabel(player[i].getPlayerName());

			playerName.setBounds(x, labelY, 200, 30);
			this.enemyFieldsPan.add(playerName);

			this.enemyFieldsPan.add(player[i].getBattleFieldView().getView());
			player[i].getBattleFieldView().setSize(x, fieldY, miniFieldSize);
			player[i].getBattleFieldView().getView().setBounds(x, fieldY, miniFieldSize, miniFieldSize);
			player[i].getBattleFieldView().getView().setVisible(true);

			labelY = fieldY + miniFieldSize;
			fieldY = labelY + 30;
			//
			//			this.enemyFieldsPan.repaint();
			//			this.enemyFieldsPan.revalidate();
		}
		this.roundPan.add(enemyFieldsPan);

	}

	public ImagePanel getPanel(){
		return this.roundPan;
	}


}

