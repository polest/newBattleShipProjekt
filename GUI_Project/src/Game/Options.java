package Game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.Serializable;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;


public class Options implements Serializable{
	/**
	 * * @author ML
	 * @version 21.03.15
	 */
	private static final long serialVersionUID = 6599149630474669593L;
	private int player;
	private boolean[] playerIsKi;
	private String[] playerNames;
	private int destroyer;
	private int frigate;
	private int corvette;
	private int submarine;
	private int totalShips;
	private int battlefieldSize;
	private Options_View optionsView;

	public Options(int width,int height){
		this.totalShips = 0;
		this.playerNames = new String[6];
		for(int i = 0; i < this.playerNames.length; i++){
			this.playerNames[i] = new String("Spieler "+ (i+1));
		}
		this.optionsView =  new Options_View(width, height);
		addListener();
	}

	public int getPlayer() {
		return player;
	}

	public String[] getPlayerNames() {
		return playerNames;
	}

	public int getDestroyer() {
		return destroyer;
	}

	public int getFrigate() {
		return frigate;
	}

	public int getCorvette() {
		return corvette;
	}

	public int getSubmarine() {
		return submarine;
	}

	public int getTotalShips() {
		return totalShips;
	}

	public int getBattlefieldSize() {
		return battlefieldSize;
	}

	public JPanel getPanel(){
		return this.optionsView.getPanel();
	}

	public boolean getPlayerKi(int index){
		return this.playerIsKi[index];
	}

	public Options_View getView(){
		return this.optionsView;
	}
	/**
	 * Einstellungen der Spieleranzahl
	 */

	private void setPlayer(String number){
		int count = Integer.parseInt(number); 
		this.player = count;
		this.optionsView.setPlayerCount(count);
		this.playerIsKi = new boolean[count-1];
		this.optionsView.setPlayerToggle(count);
	}

	private void setKi(JCheckBox chkbx){
		optionsView.setKi(chkbx);
		playerIsKi = new boolean[this.player];

		for(int i = 0; i < this.playerIsKi.length; i++){
			this.playerIsKi[i] = this.optionsView.getKiBox()[i].isSelected();
		}
	}


	private void setShips(JComboBox cmbbox){
		if(cmbbox.getActionCommand() == "destroyer"){
			this.destroyer = Integer.parseInt( cmbbox.getSelectedItem().toString() );
		}
		else if(cmbbox.getActionCommand() == "frigate"){
			this.frigate = Integer.parseInt( cmbbox.getSelectedItem().toString() );
		}
		else if(cmbbox.getActionCommand() == "corvette"){
			this.corvette = Integer.parseInt( cmbbox.getSelectedItem().toString() );
		}
		else if(cmbbox.getActionCommand() == "submarine"){
			this.submarine = Integer.parseInt( cmbbox.getSelectedItem().toString() );
		}
		this.optionsView.setShips(cmbbox);
		int minSize = setBattleFieldSize();
		this.battlefieldSize = minSize;
		this.optionsView.setSize(minSize);
	}

	private void setSize(int preferredSize){
		int minSize = setBattleFieldSize();
		if(minSize > preferredSize){
			this.optionsView.setSize(minSize);
			this.battlefieldSize = minSize;
		}
		else{
			this.optionsView.setSize(preferredSize);
			this.battlefieldSize = preferredSize;
		}
	}

	private void setName(JTextField name){
		JTextField[] textFields = this.optionsView.getNamesFields();
		for(int i = 0; i < textFields.length; i++){
			if(name == textFields[i]){
				this.playerNames[i] = name.getText();
				System.out.println(""+name.getText());
			}
		}
	}

	/**
	 * 
	 * Methode berechnet anhand der Anzahl der Schiffe, wie groß das Spielfeld sein muss und gibt diese zurück.
	 * 
	 * @return size (größe des Spielfelds)
	 */

	public int setBattleFieldSize(){
		int zahl = 1;
		int destroyerSize = 21;
		int frigateSize = 18;
		int corvetteSize = 15;
		int submarineSize = 12;
		int totalShipSize;

		totalShipSize = (this.destroyer*destroyerSize)+(this.corvette*corvetteSize)+(this.frigate*frigateSize)+(this.submarine*submarineSize);
		this.totalShips = this.destroyer + this.corvette + this.frigate + this.submarine;
		while((zahl * zahl) < totalShipSize){
			zahl++;
		}
		return zahl;
	}

	/**
	 * Die Listener, die wir aus den Internen Klassen generieren
	 * werden der View bekannt gemacht, sodass diese mit
	 * uns (dem Controller) kommunizieren können
	 */
	private void addListener(){
		this.optionsView.setPlayerSelectionListener(new SetPlayerListener());
		this.optionsView.setKiSelectionListener(new SetKiListener());
		this.optionsView.setShipsSelectionListener(new SetShipsListener() );
		this.optionsView.setSizeSelectionListener(new SetSizeListener() );
		this.optionsView.setNameSelectionListener(new SetNameListener() );
	}

	
	/**
	 * Inneren Listener Klassen implementieren das Interface ActionListener
	 */
	class SetPlayerListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			setPlayer(e.getActionCommand());
			optionsView.checkOkButton();
		}
	}

	private class SetKiListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			setKi((JCheckBox) e.getSource());
		}
	}

	private class SetShipsListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			setShips((JComboBox) e.getSource());
			optionsView.checkOkButton();
		}
	}

	private class SetSizeListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JComboBox sizeBox =  (JComboBox)e.getSource();
			int size = Integer.parseInt( sizeBox.getSelectedItem().toString() );
			setSize(size);
			optionsView.checkOkButton();
		}
	}

	private class SetNameListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void focusLost(FocusEvent e) {
			JTextField name =  (JTextField)e.getSource();
			setName(name);
		}
	}
}
