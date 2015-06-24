package Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import Game.InitGame;
import Game.Options;
import Game.Options_View;
import Game.InitGame_View;
import Game.Round;

public class Main_Controler {

	private Main_View main_view;
	private int width;
	private int height;
	private InitGame initGame;
	private Options gameOptions;
	private Options_View gameOptionsView;
	private InitGame_View initGameView;


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
	}

	private void addOptionsListener(){
		this.gameOptions.getView().setOkSelectionListener(new SetOptionsOkListener() );
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
				System.out.println(file.getPath());
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

	private class SetOptionsOkListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			initGameView =  new InitGame_View(width, height);
			initGame = new InitGame(gameOptionsView, gameOptions, initGameView);
			main_view.addPanel(initGameView.getPanel(), "placeShipsPan");
			main_view.changeShownPan("placeShipsPan");
			//TODO Fenster des Spielbeginns hinzufügen
		}
	}
}
