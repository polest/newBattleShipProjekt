package Main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import Game.InitGame;
import Game.InitGame_Client;
import Game.InitGame_View_Client;
import Game.Options;
import Game.Options_View;
import Game.Round;
import Game.Round_View;
import Network.BattleShipServer;
import Network.Client;
import SaveGame.Load;
import SaveGame.Save;
import Tools.ImagePanel;

public class Main_Controler implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2959797632378263001L;
	private Main_View main_view;
	private int width;
	private int height;
	private InitGame_Client initGameClient;
	private InitGame initGame;
	private Options gameOptions;
	private Options_View gameOptionsView;
	private InitGame_View_Client initGameView;
	private Round round;
	private Client client;
	private BattleShipServer server;
	private String ipAdress;
	private int port;
	
	private JFrame connection;
	private JLabel portLabel;
	private JTextField portField;
	private JLabel ipLabel;
	private JTextField ipField;
	private JButton button;
	

	public Main_Controler(){

		this.width = 800;
		this.height = 600;

		this.main_view = new Main_View(this.width, this.height);
		addMainViewListener();
	}

	/**
	 * Die Listener, die wir aus den Internen Klassen generieren
	 * werden der View bekannt gemacht, sodass diese mit
	 * uns (dem Controller) kommunizieren kann
	 */
	private void addMainViewListener(){
		this.main_view.setNewGameSelectionListener(new StartNewGameListener());
		this.main_view.setJoinSelectionListener(new JoinGameListener());
		this.main_view.setLoadSelectionListener(new LoadGameListener());
		this.main_view.setInstructionsSelectionListener(new InstructionsListener());
		this.main_view.setSaveSelectionListener(new SaveGameListener());
		this.main_view.setExitSelectionListener(new ExitGameListener());
	}

	private void addOptionsListener(){
		this.gameOptions.getView().setOkSelectionListener(new SetOptionsOkListener());
		this.gameOptions.getView().setBackSelectionListener(new SetOptionsBackListener());
	}

	public void ChangeShipsView(){

		main_view.changeShownPan("placeShipsPan");
		initGameView.setNextSelectionListener(new StartRoundListener());
		main_view.getSave().setEnabled(true);

	}

	public void startServerAndGame(){
		int player = gameOptions.getPlayer();
		System.out.println(player);
		int destroyer = gameOptions.getDestroyer();
		System.out.println(destroyer);
		int frigate = gameOptions.getFrigate();
		System.out.println(frigate);
		int corvette = gameOptions.getCorvette();
		System.out.println(corvette);
		int submarine = gameOptions.getSubmarine();
		System.out.println(submarine);
		int size = gameOptions.getBattlefieldSize();
		System.out.println(size);
		
		initGameView =  new InitGame_View_Client(player);
		initGameClient = new InitGame_Client(destroyer, frigate, corvette, submarine, size, initGameView);
		initGame = new InitGame(player, destroyer, frigate, corvette, submarine, size);
		main_view.addPanel(initGameView.getPanel(), "placeShipsPan");

		Thread t = new Thread(server = new BattleShipServer(4477, player, initGame, destroyer, frigate, corvette, submarine, size, this) );
		t.start();
		
		Thread s = new Thread(client = new Client("localhost", 4477, this) );
		s.start();
		initGameClient.addClient(client);
	}
	
	public void startInitGameView(int player, int destroyer, int frigate, int corvette, int submarine, int fieldSize){
		System.out.println("auswerten fertig!");
		int totalShips;

		totalShips = destroyer + frigate + corvette + submarine;

		 initGameView =  new InitGame_View_Client(player);
	     initGameClient = new InitGame_Client(destroyer, frigate, corvette, submarine, fieldSize, initGameView);

	     initGameClient.addClient(client);
	     main_view.addPanel(initGameView.getPanel(), "placeShipsPan");
	     ChangeShipsView();
		
	}

	public void addClientToGame(){
		
		Thread s = new Thread(client = new Client(ipAdress, port, this) );
		s.start();
	}
	
	public void changeToRoundView(){
		int fieldSize = initGame.getFieldSize();
		round = new Round(initGame.getPlayer(), fieldSize);
		Round_View roundView = round.getRoundView();
		main_view.addPanel(roundView.getPanel(), "roundView"); 
		main_view.changeShownPan("roundView");
	}

	/**
	 * Inneren Listener Klassen implementieren das Interface ActionListener
	 */
	private class StartNewGameListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			gameOptions = new Options(width, height);
			main_view.addPanel(gameOptions.getPanel(), "optionsPanel");
			addOptionsListener();
			main_view.changeShownPan("optionsPanel");
		}
	}
	
	private class JoinGameListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			connection = new JFrame("Anmelden");
			JPanel pan = new JPanel();
			pan.setLayout(null);
			portLabel = new JLabel();
			portField = new JTextField();
			ipLabel = new JLabel();
			ipField = new JTextField();
			button = new JButton("OK");
			
			connection.setLocationRelativeTo(null);
			connection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			connection.setSize(300, 200);
			connection.setVisible(true);
			connection.add(pan);
			pan.setBounds(0,0,300,200);
			pan.add(portLabel);
			pan.add(portField);
			pan.add(ipLabel);
			pan.add(ipField);
			
			ipLabel.setText("Ip Adresse");
			ipLabel.setBounds(10, 20, 80, 30);
			ipLabel.setVisible(true);
		
			ipField.setText("localhost");
			ipField.setBounds(100, 20, 150, 30);
			ipField.setVisible(true);
			
			portLabel.setText("Port");
			portLabel.setBounds(10, 50, 80, 30);
			portLabel.setVisible(true);
		
			portField.setText("4477");
			portField.setBounds(100, 50, 100, 30);
			portField.setVisible(true);
			
			button.setBounds(150, 100, 50, 50);
			pan.add(button);
			button.addActionListener(new AddressOkListener());
			
		}
	}
	
	private class AddressOkListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			ipAdress = ipField.getText();
			String portS = portField.getText();
			try{
				port = Integer.parseInt(portS);
				connection.dispose();
				System.out.println("" + ipAdress + ", " + portS);
				addClientToGame();
			}
			catch(Exception f){
				portField.setBorder(BorderFactory.createLineBorder(Color.red));
			}
			
		}
	}

	private class ExitGameListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.exit(-1);
		}
	}

	private class LoadGameListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Speicherstand", "save");
			File f = new File(System.getProperty("user.dir", "save"));
			jfc.setCurrentDirectory(f);
			jfc.setFileFilter(filter);
			int returnVal = jfc.showOpenDialog(null);
			File file = jfc.getSelectedFile();
			if(returnVal == JFileChooser.APPROVE_OPTION){
				Load load = new Load();
				load.loadGame(file.getPath());
				round = load.getRound();
				Round_View roundView = round.getRoundView();
				main_view.addPanel(roundView.getPanel(), "roundView"); 
				main_view.changeShownPan("roundView");
			}else if(returnVal == JFileChooser.CANCEL_OPTION){
				System.out.println("Es wurde keine Datei ausgewählt.");
			}
		}
	}

	private class InstructionsListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Instructions ins = new Instructions();
		}

	}

	private class SetOptionsBackListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			main_view.addPanel(main_view.getWelcomePan(), "welcomePan");
			main_view.changeShownPan("welcomePan");
		}

	}

	private class SetOptionsOkListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			startServerAndGame();
		}
	}

	private class StartRoundListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			client.setReady();
		}

	}

	private class SaveGameListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			File f = new File(System.getProperty("user.dir", "save"));
			jfc.setCurrentDirectory(f);
			int returnVal = jfc.showSaveDialog(null);
			File file = jfc.getSelectedFile();
			if(returnVal == JFileChooser.APPROVE_OPTION){
				Save save = new Save();
				save.saveGame(file.getPath(), round);
			}else if(returnVal == JFileChooser.CANCEL_OPTION){
				System.out.println("Es wurde keine Datei ausgewählt.");
			}
		}

	}
	
	
}
