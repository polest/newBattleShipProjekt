package Game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;


public class Options implements Serializable{
	
	private static final long serialVersionUID = 6599149630474669593L;
	private int player;
	private boolean[] playerIsKi;
	private int ki;
	private String[] playerNames;
	private int destroyer;
	private int frigate;
	private int corvette;
	private int submarine;
	private int totalShips;
	private int battlefieldSize;
	private Options_View optionsView;
	private int port = 0;

	public int getPort() {
		try{
			port = Integer.parseInt(optionsView.getPort().getText());
			return port;
		}catch(Exception e){
			return 0;
		}
		
		
	}

	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @param width
	 * @param height
	 * speichert die Namen der Spieler in einem String Array
	 * ruft optionsView auf
	 * ruft add Listener auf
	 */
	public Options(int width,int height){
		this.totalShips = 0;
		this.playerNames = new String[6];
		for(int i = 0; i < this.playerNames.length; i++){
			this.playerNames[i] = new String("Spieler "+ (i+1));
		}
		this.optionsView =  new Options_View(width, height);
		addListener();
	}

	/**
	 * Getter
	 * player
	 * playerNames
	 * destroyer
	 * frigate
	 * corvette
	 * submarine
	 * totalShips
	 * battleFieldSize
	 * optionsViewPanel
	 * ki
	 * optionsView
	 */
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
	
	public int getKi(){
		return this.ki;
	}
	/**
	 * Einstellungen der Spieleranzahl
	 * speichert die Spieleranzahl
	 * initialiert dementsprechend Ki Bools
	 * setzt die Spieler Buttons in initView auf "number"
	 */
	private void setPlayer(String number){
		int count = Integer.parseInt(number); 
		this.player = count;
		this.optionsView.setPlayerCount(count);
		this.playerIsKi = new boolean[count-1];
		this.optionsView.setPlayerToggle(count);
	}

	
	
	/**
	 * @param chkbx
	 * je nachdem wie viele Ki Checkboxes ausgewählt sind werden Spieler auf isKi true gesetzt.
	 */
	private void setKi(){
		playerIsKi = new boolean[optionsView.getKiBox().getSelectedIndex()];
		this.ki = optionsView.getKiBox().getSelectedIndex();
	}


	/**
	 * @param cmbbox
	 * liest die comboBox aktion des Nutzers aus und speichert die Schiffsanzahl
	 * 
	 */
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

	/**
	 * @param preferredSize
	 * übergibt die minimale Spielfeldgröße an optionsView
	 * speichert minimale Spielfeldgröße in battleFieldSize
	 */
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
	
	private void setKiSize(int preferredSize){
		int maxSize = optionsView.getPlayerCount() - 1;
		if(maxSize < preferredSize){
			this.optionsView.setKiSize(maxSize);
		}
		else{
			this.optionsView.setKiSize(preferredSize);
		}
	}
	

	/**
	 * @param name
	 * Speichert die einzelnen SpielerNamen die in Optionsview im NamensFeld gespeichert sind.
	 */
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
	}

	
	/**
	 * Inneren Listener Klassen implementieren das Interface ActionListener
	 */
	class SetPlayerListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			setPlayer(e.getActionCommand());
			optionsView.getKiBox().setEnabled(true);
			setKiSize(0);
			optionsView.checkOkButton();
			
		}
	}

	private class SetKiListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			setKi();
			JComboBox setKiSize = (JComboBox)e.getSource();
			int size = setKiSize.getSelectedIndex();
			setKiSize(size);
			optionsView.checkOkButton();
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
