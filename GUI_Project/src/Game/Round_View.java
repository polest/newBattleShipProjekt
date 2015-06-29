package Game;


import Tools.ImagePanel;

public class Round_View {

	
	private ImagePanel roundPan;
	private int fieldSize;
	private BattleField_View[] playerFields;
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
		this.shownField =  new BattleField_View();
		this.shownField = player[0].getBattleFieldView();
		this.shownField.setSize(30,100, 400);
		this.roundPan.add(shownField.getView());
	}
	
	public ImagePanel getPanel(){
		return this.roundPan;
	}
	
	
}

