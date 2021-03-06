package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import Tools.ImagePanel;

public class InitGame_View_Client {

	private ImagePanel initGamePan;
	private int width;
	private int height;
	private int fieldSize;
	private BattleField_View battleFieldView;
	private int destroyer;
	private int frigate;
	private int corvette;
	private int submarine;
	private JToggleButton destroyerBtn;
	private JToggleButton frigateBtn;
	private JToggleButton corvetteBtn;
	private JToggleButton submarineBtn;
	private JButton nextPlayer;
	private JTextField name;
	
	public InitGame_View_Client(){
		this.initGamePan = new ImagePanel("Resources/unterwasser.jpg");
		this.width = 600;
		this.height = 800;
		//this.battleFieldView = new BattleField_View();

		initPan();
		this.initGamePan.repaint();
	}

	/**
	 * initialisiert das InitGame Panel
	 */
	private void initPan(){
		Font schrift = new Font("Times New Roman", Font.BOLD, 15);
		this.initGamePan.setVisible(true);
		this.initGamePan.setLayout(null);
		this.initGamePan.setSize(this.width, this.height);

		

		this.destroyerBtn = new JToggleButton();
		this.destroyerBtn.setBounds(550, 80, 150, 50);
		this.destroyerBtn.setVisible(true);
		this.destroyerBtn.setText("Zerstörer ");
		this.destroyerBtn.setActionCommand("destroyer");
		this.initGamePan.add(this.destroyerBtn);

		this.frigateBtn = new JToggleButton();
		this.frigateBtn.setBounds(550, 130, 150, 50);
		this.frigateBtn.setVisible(true);
		this.frigateBtn.setText("Fregatten ");
		this.frigateBtn.setActionCommand("frigate");
		this.initGamePan.add(this.frigateBtn);

		this.corvetteBtn = new JToggleButton();
		this.corvetteBtn.setBounds(550, 180, 150, 50);
		this.corvetteBtn.setVisible(true);
		this.corvetteBtn.setText("Korvetten ");
		this.corvetteBtn.setActionCommand("corvette");
		this.initGamePan.add(this.corvetteBtn);

		this.submarineBtn = new JToggleButton();
		this.submarineBtn.setBounds(550, 230, 150, 50);
		this.submarineBtn.setVisible(true);
		this.submarineBtn.setText("UBoote ");
		this.submarineBtn.setActionCommand("submarine");
		this.initGamePan.add(this.submarineBtn);

		this.nextPlayer = new JButton("Fertig");
		this.nextPlayer.setBounds(550, 490, 150, 50);
		this.nextPlayer.setVisible(true);
		this.nextPlayer.setEnabled(false);
		this.nextPlayer.setActionCommand("next");
		this.initGamePan.add(this.nextPlayer);
		
		JLabel nameLabel = new JLabel("Spieler Name");
		nameLabel.setFont(schrift);
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setBounds(555, 300, 150, 40);
		nameLabel.setVisible(true);
		nameLabel.setEnabled(true);
		this.initGamePan.add(nameLabel);
		
		this.name = new JTextField("");
		this.name.setBounds(550, 330, 150, 30);
		this.name.setVisible(true);
		this.name.setEnabled(true);
		this.initGamePan.add(this.name);
		
	}

	public JButton[][] getBattleField(){
		return this.battleFieldView.getBattleField();
	}

	public BattleField_View getBattleFieldView(){
		return this.battleFieldView;
	}

	public void setBattleFieldView(BattleField_View battleFieldView){
		this.battleFieldView = battleFieldView;
	}

	public void setFieldSize(int fieldSize){
		this.fieldSize = fieldSize;
	}
	

	public void setDestroyer(int destroyer){
		this.destroyer = destroyer;
	}

	public void setFrigate(int frigate){
		this.frigate = frigate;
	}

	public void setCorvette(int corvette){
		this.corvette = corvette;
	}

	public void setSubmarine(int submarine){
		this.submarine = submarine;
	}

	public void setBattleFieldMouseListener(MouseListener l){
		this.battleFieldView.setBattleFieldMouseListener(l);
	}

	public void setBattleFieldMouseMotionListener(MouseMotionListener l){
		this.battleFieldView.setBattleFieldMouseMotionListener(l);
	}

	public String getName(){
		return this.name.getText();
	}
	public int getCellSize(){
		return this.width/this.fieldSize;
	}

	/**
	 * @param s
	 * added den SchiffsButtons die Listener
	 */
	public void setShipsSelectionListener(ActionListener s){
		this.destroyerBtn.addActionListener(s);
		this.frigateBtn.addActionListener(s);
		this.corvetteBtn.addActionListener(s);
		this.submarineBtn.addActionListener(s);
	}

	public void setNextSelectionListener(ActionListener n){
		this.nextPlayer.addActionListener(n);
	}


	/**
	 * disabled den Fertig Button
	 */
	public void disableNext(){
		this.nextPlayer.setEnabled(false);
	}

	/**
	 * @param ship
	 * wenn kein Schiff ausgewählt ist, werden die Buttons auf nicht ausgewählt gesetzt
	 */
	public void setChoosenShip(JToggleButton ship){
		if(ship == null){
			this.destroyerBtn.setSelected(false);
			this.frigateBtn.setSelected(false);
			this.corvetteBtn.setSelected(false);
			this.submarineBtn.setSelected(false);
		}

		if(ship != this.destroyerBtn){
			this.destroyerBtn.setSelected(false);
		}

		if(ship != this.frigateBtn){
			this.frigateBtn.setSelected(false);
		}

		if(ship != this.corvetteBtn){
			this.corvetteBtn.setSelected(false);
		}

		if(ship != this.submarineBtn){
			this.submarineBtn.setSelected(false);
		}

	}

	public void setDestroyerDisabled(){
		this.destroyerBtn.setEnabled(false);
	}

	public void setFrigateDisabled(){
		this.frigateBtn.setEnabled(false);
	}

	public void setCorvetteDisabled(){
		this.corvetteBtn.setEnabled(false);
	}

	public void setSubmarineDisabled(){
		this.submarineBtn.setEnabled(false);
	}

	public void decrementDestroyer(int count){
		this.destroyerBtn.setText("Zerstörer " + count);
	}

	public void decrementFrigate(int count){
		this.frigateBtn.setText("Fregatten " + count);
	}

	public void decrementCorvette(int count){
		this.corvetteBtn.setText("Korvetten " + count);
	}

	public void decrementSubmarine(int count){
		this.submarineBtn.setText("UBoote " + count);
	}

	public void enableFinish(){
		this.nextPlayer.setEnabled(true);
	}

	public void initFields(){
		this.battleFieldView = new BattleField_View(this.initGamePan, this.fieldSize, 510, 30, 30);
		initGamePan.add(this.battleFieldView.getView());
	}


	/**
	 * @param player
	 * @param id
	 * initialisiert die Felder der Spieler
	 * setzt die Schiffsbuttons auf enabled oder disabled, je nachdem ob und wie viele Schiffe ausgewählt sind
	 */
	public void initPlayerField(Player player, int id){

		this.destroyerBtn.setSelected(false);
		this.frigateBtn.setSelected(false);
		this.corvetteBtn.setSelected(false);
		this.submarineBtn.setSelected(false);

		if(this.destroyer <= 0 ){
			this.destroyerBtn.setEnabled(false);
		}
		else{
			this.destroyerBtn.setEnabled(true);
		}

		if(this.frigate <= 0 ){
			this.frigateBtn.setEnabled(false);
		}
		else{
			this.frigateBtn.setEnabled(true);
		}

		if(this.corvette <= 0 ){
			this.corvetteBtn.setEnabled(false);
		}
		else{
			this.corvetteBtn.setEnabled(true);
		}

		if(this.submarine <= 0 ){
			this.submarineBtn.setEnabled(false);
		}
		else{
			this.submarineBtn.setEnabled(true);
		}

		this.destroyerBtn.setText("Zerstörer " + this.destroyer);
		this.frigateBtn.setText("Fregatten " + this.frigate);
		this.corvetteBtn.setText("Korvetten " + this.corvette);
		this.submarineBtn.setText("UBoote " + this.submarine);
	}

	public ImagePanel getPanel(){
		return this.initGamePan;
	}
}
