package Game;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.*;

import Tools.ImagePanel;

public class Options_View {

	private ImagePanel optionsView;
	private JToggleButton[] player;
	private JTextField[] playerNames;
	private JCheckBox[] kiBox;
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
		this.optionsView.setVisible(true);
		this.optionsView.setLayout(null);
		this.totalShipSize = 0;
		this.initOptionsPan(); 
	    this.optionsView.repaint();
		
	}

	private void initOptionsPan(){
		this.optionsView.setSize(this.width, this.height);
		this.optionsView.setVisible(true);
		this.initOptions();
	}

	public JPanel getPanel(){
		return this.optionsView;
	}
	
	public JTextField[] getNamesFields(){
		return this.playerNames;
	}
	
	public void setPlayerToggle(int count){
		for(int i = 2; i <= ( this.player.length + 1 ); i++){
			int arrIndex = i - 2;
			if(i != count){
				this.player[arrIndex].setSelected(false);
			}
		}
		
		//Namens und Ki Feld für ungenutze Spieler ausblenden
		for(int j = 0; j < this.playerNames.length; j++){
			if(j < count){
				this.playerNames[j].setEnabled(true);
			}
			else{
				this.playerNames[j].setEnabled(false);
			}
		}
		for(int k = 0; k < this.kiBox.length; k++){
			
			if(k < (count-1)){
				this.kiBox[k].setEnabled(true);
			}
			else{
				this.kiBox[k].setEnabled(false);
			}
		}
	}
	
	public void setKi(JCheckBox chkbx){
		for(int i = 0; i < this.kiBox.length; i++){
			if(chkbx == this.kiBox[i]){
				this.kiBox[i].setSelected( chkbx.isSelected() );
			}
		}
	}
	
	public JCheckBox[] getKiBox(){
		return this.kiBox;
	}
	
	
	public void setShips(JComboBox cmbbox){
		for(int s = 0; s < this.ships.length; s++){
			if(cmbbox == this.ships[s]){
				this.totalShipSize += Integer.parseInt( cmbbox.getSelectedItem().toString() );
			}
		}
	}
	 
	public void setSize(int minSize){
		
		
		this.size.setSelectedIndex( (minSize-1) );
		
		this.optionsView.repaint();
	}


	private void initOptions(){

		JLabel playerlb = new JLabel("Anzahl der Spieler");
		//playerlb.setIcon(new ImageIcon("Resources/countPlayer.png"));
		playerlb.setBounds(40,70, 120,30);
		this.optionsView.add(playerlb);

		this.player = new JToggleButton[5];
		for(int p = 0; p < this.player.length; p++){
			int pVal = p+2;
			this.player[p] =  new JToggleButton(""+pVal);
			this.player[p].setBounds(70,( 155+ (45*p) ), 35, 35);
			this.optionsView.add(this.player[p]);
		}

		this.playerNames = new JTextField[6];
		this.kiBox = new JCheckBox[5];

		JLabel playerNameslb = new JLabel("Namen der Spieler");
		playerNameslb.setBounds(190, 70, 120, 30);
		this.optionsView.add(playerNameslb);

		JLabel kilb = new JLabel("Computer");
		kilb.setBounds(320, 70, 100, 30);
		this.optionsView.add(kilb);



		for(int n = 0; n < this.playerNames.length; n++){
			int namesIndex = n+1;
			int kiIndex = n-1;
			
			this.playerNames[n] = new JTextField("Spieler " + namesIndex);
			this.playerNames[n].setBounds(190 ,( 110+ (45*n) ), 120, 35);
			this.playerNames[n].setActionCommand("Spieler"+n);
			this.optionsView.add(this.playerNames[n]);

			if(n > 0){
				this.kiBox[kiIndex] = new JCheckBox();
				this.kiBox[kiIndex].setBounds(320 ,( 110+ (45*n) ), 100, 35);
				this.optionsView.add(this.kiBox[kiIndex]);
			}
		}


		JLabel shipslb = new JLabel("Anzahl der Schiffe");
		shipslb.setBounds(420, 70, 150, 30);
		this.optionsView.add(shipslb);

		JLabel[] shipsLabel = {new JLabel("Zerstörer"),
				new JLabel("Fregatten"), new JLabel("Corvetten"), new JLabel("UBoote")};

		this.ships =  new JComboBox[4];
		String[] count = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
		
		for(int s = 0; s < this.ships.length; s++){
			shipsLabel[s].setBounds(420 ,( 110+ (45*s) ), 90, 35);
		    this.optionsView.add(shipsLabel[s]);
		    
		    this.ships[s] = new JComboBox(count);
		    
		    this.ships[s].setBounds(510 ,( 110+ (45*s) ), 70, 35);
		    
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
		sizeOfGame.setBounds(610, 70, 150, 30);
		this.optionsView.add(sizeOfGame);
		
		String[] sizeVal = new String[15];
		
		for(int i = 0; i < 15; i++){
			int val = i+1;
			sizeVal[i] = ""+val;
		}
		
		this.size = new JComboBox(sizeVal);
		this.size.setBounds(620, 110, 70, 35);
		this.optionsView.add(this.size);

		this.ok = new JButton("OK");
		this.ok.setBounds(590, 515, 80, 35);
		this.optionsView.add(this.ok);
		
		this.back = new JButton("Zurück");
		this.back.setBounds(690, 515, 80, 35);
		this.optionsView.add(this.back);
		
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
		for(int p = 0; p < this.kiBox.length; p++){
			this.kiBox[p].addActionListener(n);
		}
	}
	
	public void setShipsSelectionListener(ActionListener s){
		for(int t = 0; t < this.ships.length; t++){
			this.ships[t].addActionListener(s);
		}
	}
	
	public void setSizeSelectionListener(ActionListener m){
		this.size.addActionListener(m);
	}
	public void setNameSelectionListener(ActionListener n){
		for(int j = 0; j < this.playerNames.length; j++){
		this.playerNames[j].addActionListener(n);
		}
	}
	
	public void setOkSelectionListener(ActionListener o){
		this.ok.addActionListener(o);
	}
	
	public void setBackSelectionListener(ActionListener b){
		this.back.addActionListener(b);
	}
}
