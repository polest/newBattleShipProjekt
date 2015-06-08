package Game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;


public class Options implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6599149630474669593L;
	private int player;
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


	/**
	 * Einstellungen der Spieleranzahl
	 */

	private void setPlayer(String number){
		int count = Integer.parseInt(number); 
		this.player = count;
		this.optionsView.setPlayerToggle(count);
	}
	
	private void setKi(JCheckBox chkbx){
		optionsView.setKi(chkbx);
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
		}
	}

	class SetKiListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			setKi((JCheckBox) e.getSource());
		}
	}
	
	class SetShipsListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			setShips((JComboBox) e.getSource());
		}
	}
	
	class SetSizeListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JComboBox sizeBox =  (JComboBox)e.getSource();
			int size = Integer.parseInt( sizeBox.getSelectedItem().toString() );
			setSize(size);
		}
	}
}
