package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import Tools.ImagePanel;

public class Options_View {

	private ImagePanel optionsView;
	private JToggleButton[] player;
	private int playerCount;
	private JTextField[] playerNames;
	private JTextField port;
	private JComboBox kiBox;
	private JComboBox size;

	private JComboBox[] ships;
	private int width;
	private int height;
	private JButton ok;
	private JButton back;
	private int totalShipSize;

	public Options_View(int width, int height){
		this.width = width;
		this.height = height;
		this.optionsView = new ImagePanel("Resources/unterwasser.jpg");
		this.totalShipSize = 0;
		this.playerCount = 0;
		this.initOptionsPan(); 
		this.initOptions();
		this.optionsView.repaint();

	}

	/**
	 * Initialisiert das Options Panel
	 */
	private void initOptionsPan(){
		this.optionsView.setVisible(true);
		this.optionsView.setLayout(null);
		this.optionsView.setSize(this.width, this.height);
	}


	/**
	 * Getter
	 * optionsPanel
	 * playerNames
	 */
	/**
	 * Setter
	 * PlayerCount
	 */

	public JPanel getPanel(){
		return this.optionsView;
	}

	public JTextField[] getNamesFields(){
		return this.playerNames;
	}

	public void setPlayerCount(int i){
		this.playerCount = i;
	}


	/**
	 * @param count
	 * setzt die PlayerToggleButtons auf nicht ausgewählt, je nachdem wie viele Spieler ausgewählt sind.
	 * setzt die KI CheckBoxes auf disabled, je nachdem wie viele Spieler ausgewählt sind
	 * dasselbe für die Textfelder der Namen
	 */
	public void setPlayerToggle(int count){
		for(int i = 2; i <= ( this.player.length + 1 ); i++){
			int arrIndex = i - 2;
			if(i != count){
				this.player[arrIndex].setSelected(false);
			}
		}
	}

	/**
	 * @return KI combobox
	 */
	public JComboBox getKiBox(){
		return this.kiBox;
	}

	/**
	 * @param cmbbox
	 * speichert die gesamte Schiffsanzahl
	 */
	public void setShips(JComboBox cmbbox){
		for(int s = 0; s < this.ships.length; s++){
			if(cmbbox == this.ships[s]){
				this.totalShipSize += Integer.parseInt( cmbbox.getSelectedItem().toString() );
			}
		}
	}


	/**
	 * @param minSize - Mindestspielfeldgröße
	 * aktualisiert die Spielfeldgrößenauswahl
	 */
	public void setSize(int minSize){

		this.size.setSelectedIndex( (minSize-1) );

		this.optionsView.repaint();
	}
	
	/**
	 * @param size
	 * Setzt die Anzahl der möglich auswählbaren Bots
	 */
	public void setKiSize(int size){
		this.kiBox.setSelectedIndex(size);
	}
	


	/**
	 * @return
	 * get Player;
	 */
	public int getPlayerCount() {
		return playerCount;
	}

	/**
	 * initialisiert die Optionen
	 * füllt das optionsView Panel mit allen Elementen
	 * Labels - Anzahl Spieler, Namen der Spieler, Computer, Anzahl der Schiffe, Größe des Spielfelds, Schiffe
	 * ToggleButtons - Spieleranzahl
	 * Textfelder - Spielernamen
	 * Checkboxes - Ki
	 * ComboBoxes - einzelnen Schiffe (0-10 p Schiff), Spielfeldgröße
	 * 
	 */
	private void initOptions(){

		JLabel playerlb = new JLabel("Anzahl der Spieler");
		Font schrift = new Font("Times New Roman", Font.BOLD, 15);
		playerlb.setForeground(Color.WHITE);
		playerlb.setFont(schrift);
		//playerlb.setIcon(new ImageIcon("Resources/countPlayer.png"));
		playerlb.setBounds(15,70, 130,30);
		this.optionsView.add(playerlb);

		this.player = new JToggleButton[5];
		for(int p = 0; p < this.player.length; p++){
			int pVal = p+2;
			this.player[p] =  new JToggleButton(""+pVal);
			this.player[p].setBounds(50,( 115+ (45*p) ), 35, 35);
			this.optionsView.add(this.player[p]);
		}

		this.playerNames = new JTextField[6];


		JLabel kilb = new JLabel("Computer");
		kilb.setFont(schrift);
		kilb.setForeground(Color.WHITE);
		kilb.setBounds(190, 70, 100, 30);
		this.optionsView.add(kilb);




		String[] counter = {"0", "1", "2","3", "4","5"};
		this.kiBox = new JComboBox(counter);
		this.kiBox.setBounds(175 , 110, 100, 35);
		this.kiBox.setEnabled(false);
		this.optionsView.add(this.kiBox);


		JLabel shipslb = new JLabel("Anzahl der Schiffe");
		shipslb.setForeground(Color.WHITE);
		shipslb.setFont(schrift);
		shipslb.setBounds(330, 70, 150, 30);
		this.optionsView.add(shipslb);

		JLabel[] shipsLabel = {new JLabel("Zerstörer"), new JLabel("Fregatten"), new JLabel("Korvetten"), new JLabel("UBoote")};

		this.ships =  new JComboBox[4];
		String[] count = {"0", "1", "2", "3", "4", "5"};

		for(int s = 0; s < this.ships.length; s++){
			shipsLabel[s].setBounds(330 ,( 110+ (45*s) ), 90, 35);
			shipsLabel[s].setForeground(Color.WHITE);
			shipsLabel[s].setFont(schrift);
			this.optionsView.add(shipsLabel[s]);

			this.ships[s] = new JComboBox(count);

			this.ships[s].setBounds(410 ,( 110+ (45*s) ), 70, 35);

			if(s == 0){
				this.ships[s].setActionCommand("destroyer");
			}
			else if(s == 1){
				this.ships[s].setActionCommand("frigate");
			}
			else if(s == 2){
				this.ships[s].setActionCommand("corvette");
			}
			else if(s == 3){
				this.ships[s].setActionCommand("submarine");
			}
			this.optionsView.add(this.ships[s]);
		}


		JLabel sizeOfGame = new JLabel("Größe des Spielfeldes");
		sizeOfGame.setForeground(Color.white);
		sizeOfGame.setFont(schrift);
		sizeOfGame.setBounds(520, 70, 150, 30);
		this.optionsView.add(sizeOfGame);

		String[] sizeVal = new String[20];

		for(int i = 0; i < 20; i++){
			int val = i+1;
			sizeVal[i] = ""+val;
		}

		this.size = new JComboBox(sizeVal);
		this.size.setBounds(555, 110, 70, 35);
		this.optionsView.add(this.size);

		this.ok = new JButton("OK");
		this.ok.setEnabled(false);
		this.ok.setBounds(590, 515, 80, 35);
		this.optionsView.add(this.ok);

		this.back = new JButton("Zurück");
		this.back.setBounds(690, 515, 80, 35);
		this.optionsView.add(this.back);
		
		JLabel portLbl = new JLabel("Port");
		portLbl.setBounds(718, 75, 50, 20);
		portLbl.setForeground(Color.white);
		portLbl.setFont(schrift);
		this.optionsView.add(portLbl);
		
		this.port = new JTextField();
		this.port.setBounds(700, 110, 70, 30);
		this.optionsView.add(this.port);

	}

	public void checkOkButton(){
		boolean setEnabled = true;

		if(this.playerCount == 0){
			setEnabled = false;
		}
		else if(this.totalShipSize == 0){
			setEnabled = false;
		}
		else if(this.size.getSelectedIndex() == 0){
			setEnabled = false;
		}

		if(setEnabled == true){
			this.ok.setEnabled(true);
		}
		else{
			this.ok.setEnabled(false);
		}
	}

	/**
	 * Funktionen bereitstellen, mit denen man später aus
	 * dem Controller die nötigen Listener hinzufügen kann
	 */
	public void setPlayerSelectionListener(ActionListener l){
		for(int p = 0; p < this.player.length; p++){
			this.player[p].addActionListener(l);
		}
	}

	public void setKiSelectionListener(ActionListener n){
		this.kiBox.addActionListener(n);
	}

	public void setShipsSelectionListener(ActionListener s){
		for(int t = 0; t < this.ships.length; t++){
			this.ships[t].addActionListener(s);
		}
	}

	public void setSizeSelectionListener(ActionListener m){
		this.size.addActionListener(m);
	}


	public void setOkSelectionListener(ActionListener o){
		this.ok.addActionListener(o);
	}

	public void setBackSelectionListener(ActionListener b){
		this.back.addActionListener(b);
	}

	public JTextField getPort() {
		return port;
	}
}
