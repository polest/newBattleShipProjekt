package Main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import Tools.ImagePanel;

public class Main_View{

	private JFrame frame; 
	private ImagePanel welcomePan;	

	private JLabel welcome_text;
	private JButton newGame;
	private JButton join;
	private JButton loadGame;
	private JButton instructions;
	private MenuBar menubar;
	private Menu datei;
	private MenuItem save;
	private MenuItem anleitung;
	private MenuItem exit;
	private int width;
	private int height;
	private int selection = 0;
	private JPanel cards;
	private String welcomeTextIcon;

	public Main_View(int width, int height){
		this.width = width;
		this.height = height;
		this.cards = new JPanel(new CardLayout());
		this.cards.setVisible(true);
		this.initFrame();
		this.initStartPanel();
		this.initMenu();
		this.welcomeTextIcon = "Resources/welcomeText.png";
		changeShownPan("welcomePan");
		//doStartAnim();
	}

	/**
	 * Getter
	 * welcome Panel
	 * Setter 
	 * welcome Panel
	 * @return
	 */
	public ImagePanel getWelcomePan() {
		return welcomePan;
	}

	public void setWelcomePan(ImagePanel welcomePan) {
		this.welcomePan = welcomePan;
	}

	/**
	 *Frame wird initialisiert 
	 */
	private void initFrame(){
		this.frame = new JFrame("BattleShip Galactica");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(this.width, this.height);
		this.frame.setResizable(false);
		this.frame.setLocationRelativeTo(null);
		this.frame.setResizable(false);
		this.frame.setVisible(true);
		this.frame.add(this.cards);
	}

	/**
	 * Menu wird initialisiert und dem Frame hinzugefügt
	 */
	private void initMenu(){
		this.menubar = new MenuBar();
		this.datei = new Menu("Datei");
		this.save = new MenuItem("Speichern");
		this.anleitung = new MenuItem("Anleitung");
		this.exit = new MenuItem("Schließen");
		this.save.setEnabled(false);
		this.datei.add(save);
		this.datei.add(anleitung);
		this.datei.add(exit);
		this.menubar.add(datei);
		this.frame.setMenuBar(this.menubar);
	}

	/**
	 * Getter save
	 * @return
	 */
	public MenuItem getSave() {
		return save;
	}

	/**
	 * initialisert Startpanel
	 * wird cardPanel geaddet
	 */
	private void initStartPanel(){
		this.welcomePan = new ImagePanel("Resources/unterwasser2.png");
		this.welcomePan.setSize(this.width, this.height);
		this.welcomePan.setLayout(null);
		this.welcomePan.setVisible(true);
		this.setWelcomeText();
		this.setButtons();
		this.welcomePan.repaint();

		this.cards.add(this.welcomePan);
	}

	
	/**
	 * @param panel
	 * @param name
	 * Methode um ein Panel dem CardPanel zu adden
	 */
	public void addPanel(JPanel panel, String name){
		this.cards.add(panel, name);
	}

	/**
	 * wechselt das Panel zu dem übergegebenen Panel
	 * @param cardName
	 */
	public void changeShownPan(String cardName){
		CardLayout cardLayout = (CardLayout) cards.getLayout();
		cardLayout.show(cards, cardName);
		cards.revalidate();

	}

	
	/**
	 * setzt den Willkommenstext und added ihn dem welcome Panel
	 */
	private void setWelcomeText(){
		this.welcome_text = new JLabel("", SwingConstants.CENTER);
		this.welcome_text.setIcon(new ImageIcon("Resources/welcomeText.png"));
		int labelWidth = 800;
		this.welcome_text.setBounds((this.width-labelWidth)/2, 100, labelWidth, 100);
		this.welcome_text.setVisible(true);
		this.frame.repaint();
		this.welcomePan.add(welcome_text);
	}

	/**
	 * Setzt alle Buttons auf das Panel
	 */
	private void setButtons(){
		int buttonWidth = 250;

		//Button um ein neues Spiel zu starten
		this.newGame = new JButton();
		this.newGame.setIcon(new ImageIcon("Resources/Spiel/newGame.png"));
		this.newGame.setBounds((this.width-buttonWidth)/2, 200, buttonWidth, 100);
		//this.newGame.setText("Neues Spiel");
		this.newGame.setOpaque(false);
		this.newGame.setContentAreaFilled(false);

		this.newGame.setBorderPainted(false);
		//this.newGame.setForeground(Color.BLUE);
		this.newGame.setVisible(true);

		//Button um ein Spiel zu laden
		this.join = new JButton();
		//this.loadGame.setText("Spiel Laden");
		this.join.setIcon(new ImageIcon("Resources/Spiel/joinGame.png"));
		this.join.setBounds((this.width-buttonWidth)-320, 275, buttonWidth + 100, 100);
		//this.loadGame.setForeground(Color.BLUE);
		this.join.setOpaque(false);
		this.join.setContentAreaFilled(false);

		this.join.setBorderPainted(false);
		this.join.setVisible(true);

		//Button um ein Spiel zu laden
		this.loadGame = new JButton();
		//this.loadGame.setText("Spiel Laden");
		this.loadGame.setIcon(new ImageIcon("Resources/Spiel/loadGame.png"));
		this.loadGame.setBounds((this.width-buttonWidth)/2, 350, buttonWidth, 100);
		//this.loadGame.setForeground(Color.BLUE);
		this.loadGame.setOpaque(false);
		this.loadGame.setContentAreaFilled(false);

		this.loadGame.setBorderPainted(false);
		this.loadGame.setVisible(true);

		this.instructions = new JButton();
		this.instructions.setIcon(new ImageIcon("Resources/Spiel/instructions.png"));
		this.instructions.setBounds((this.width-buttonWidth)/2, 425, buttonWidth, 100);
		this.instructions.setOpaque(false);
		this.instructions.setContentAreaFilled(false);
		this.instructions.setBorderPainted(false);
		this.instructions.setVisible(true);

		this.welcomePan.add(this.newGame);
		this.welcomePan.add(this.join);
		this.welcomePan.add(this.loadGame);
		this.welcomePan.add(this.instructions);

	}

	/**
	 * Funktionen bereitstellen, mit denen man später aus
	 * dem Controller die nötigen Listener hinzufügen kann
	 */
	public void setNewGameSelectionListener(ActionListener l){
		this.newGame.addActionListener(l);
	}

	public void setLoadSelectionListener(ActionListener l){
		this.loadGame.addActionListener(l);
	}
	
	public void setJoinSelectionListener(ActionListener l){
		this.join.addActionListener(l);
	}

	public void setInstructionsSelectionListener(ActionListener l){
		this.instructions.addActionListener(l);
		this.anleitung.addActionListener(l);
	}

	public void setSaveSelectionListener(ActionListener l){
		this.save.addActionListener(l);
	}

	public void setExitSelectionListener(ActionListener l){
		this.exit.addActionListener(l);
	}


}
