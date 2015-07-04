package Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;
import Game.InitGame;
import Game.InitGame_View;
import Game.Options;
import Game.Options_View;
import Game.Player;
import Game.Round;
import Game.Round_View;
import Network.BattleShipServer;
import SaveGame.Load;
import SaveGame.Save;

public class Main_Controler implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2959797632378263001L;
	private Main_View main_view;
	private int width;
	private int height;
	private InitGame initGame;
	private Options gameOptions;
	private Options_View gameOptionsView;
	private InitGame_View initGameView;
	private Round round;


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
		this.main_view.setLoadSelectionListener(new LoadGameListener());
		this.main_view.setInstructionsSelectionListener(new InstructionsListener());
		this.main_view.setSaveSelectionListener(new SaveGameListener());
		this.main_view.setExitSelectionListener(new ExitGameListener());
	}

	private void addOptionsListener(){
		this.gameOptions.getView().setOkSelectionListener(new SetOptionsOkListener());
		this.gameOptions.getView().setBackSelectionListener(new SetOptionsBackListener());
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
			int player = gameOptions.getPlayer();
			int destroyer = gameOptions.getDestroyer();
			int frigate = gameOptions.getFrigate();
			int corvette = gameOptions.getCorvette();
			int submarine = gameOptions.getSubmarine();
			int size = gameOptions.getBattlefieldSize();
			
			JFrame connection = new JFrame();
			connection.setVisible(true);
			connection.setSize(200, 200);
			connection.add(new JLabel("Warte auf Spieler..."));

			BattleShipServer server = new BattleShipServer(4477, player, destroyer, frigate, corvette, submarine, size);
			server.acceptClientConnectRequests();
			
			//initGameView =  new InitGame_View(width, height, gameOptions.getPlayer());
			initGame = new InitGame(gameOptionsView, gameOptions, initGameView);
			main_view.addPanel(initGameView.getPanel(), "placeShipsPan");
			main_view.changeShownPan("placeShipsPan");
			initGameView.setNextSelectionListener(new NextPlayerListener());
			main_view.getSave().setEnabled(true);
		}
	}

	private class NextPlayerListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			initGame.incrementPlayerId();
			int playerId = initGame.getPlayerId();
			Player[] player = initGame.getPlayer();


			player[playerId-1].setBattleFieldView(initGameView.getBattleFieldView( (playerId-1) ) );
			player[playerId-1].setPublicBattleFieldView(initGameView.getPublicBattleFieldView( (playerId-1) ) );
			player[playerId-1].getBattleFieldView().clearBorder();
			//			player[playerId-1].setBattleFieldView(initGameView.getBattleFieldView());


			if(playerId < player.length){
				//initGameView.clearField();
				initGame.initPlayerBattleShip();
				initGameView.setPlayerName(player[playerId].getPlayerName());
				initGameView.disableNext();
			}
			else{
				//start Game

				int fieldSize = initGame.getFieldSize();
				round = new Round(player, fieldSize);
				Round_View roundView = round.getRoundView();
				main_view.addPanel(roundView.getPanel(), "roundView"); 
				main_view.changeShownPan("roundView");
			}

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
