package Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Game.InitGame;

public class Main_Controler {

	private Main_View main_view;
	private int width;
	private int height;
	
	public Main_Controler(){
		
		this.width = 800;
		this.height = 600;
		
		this.main_view = new Main_View(this.width, this.height);
		addListener();
	}
	
	/**
     * Die Listener, die wir aus den Internen Klassen generieren
     * werden der View bekannt gemacht, sodass diese mit
     * uns (dem Controller) kommunizieren kann
     */
    private void addListener(){
        this.main_view.setNewGameSelectionListener(new StartNewGameListener());
        this.main_view.setLoadSelectionListener(new LoadGameListener());
     }

	 /**
     * Inneren Listener Klassen implementieren das Interface ActionListener
     */
    class StartNewGameListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
        	InitGame initGame = new InitGame(true, width, height);
        	main_view.addPanel(initGame.getOptionsPanel(), "optionsPanel");
        	main_view.changeShownPan("optionsPanel");
        }
    }
    
    class LoadGameListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
        	InitGame spielen = new InitGame(false, width, height);
        }
    }
    
}
