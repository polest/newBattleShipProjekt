package Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

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
        this.main_view.setInstructionsSelectionListener(new InstructionsListener());
     }

	 /**
     * Inneren Listener Klassen implementieren das Interface ActionListener
     */
    private class StartNewGameListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
        	InitGame initGame = new InitGame(true, width, height);
        	main_view.addPanel(initGame.getOptionsPanel(), "optionsPanel");
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

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Instructions ins = new Instructions();
		}
    	
    }
    
}
