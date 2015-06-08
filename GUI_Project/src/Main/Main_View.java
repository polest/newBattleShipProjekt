package Main;

import javax.swing.*; 

import Tools.ImagePanel;

import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main_View extends JFrame{

	private JFrame frame; 
	private ImagePanel welcomePan;	

	private JLabel welcome_text;
	private JButton newGame;
	private JButton loadGame;
	private int width;
	private int height;
	private int selection = 0;
	private JPanel cards;
	private String welcomeTextIcon;

	public Main_View(int width, int height){
		super();
		this.width = width;
		this.height = height;
		this.cards = new JPanel(new CardLayout());
		this.cards.setVisible(true);
		this.initFrame();
		this.initStartPanel();
		this.welcomeTextIcon = "Resources/welcomeText.png";
		changeShownPan("welcomePan");
		doStartAnim();
	}

	private void initFrame(){
		this.frame = new JFrame("BattleShip Galactica");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(this.width, this.height);
		this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
		this.frame.add(this.cards);

		//TODO Frame Icon hinzufügen
	}

	private void initStartPanel(){
		this.welcomePan = new ImagePanel("Resources/unterwasser.jpg");
		this.welcomePan.setSize(this.width, this.height);
		this.welcomePan.setLayout(null);
		this.welcomePan.setVisible(true);
		this.setWelcomeText();
		this.setButtons();
		this.welcomePan.repaint();

		this.cards.add(welcomePan);
	}

	public void addPanel(JPanel panel, String name){
		this.cards.add(panel, name);
	}

	public void changeShownPan(String cardName){
		CardLayout cardLayout = (CardLayout) cards.getLayout();
		cardLayout.show(cards, cardName);
		cards.revalidate();

	}

	private void setWelcomeText(){

		this.welcome_text = new JLabel("", SwingConstants.CENTER);
		//this.welcome_text.setText("<HTML><BODY>Willkommen bei BATTLESHIP special LARS edition</BODY></HTML>");
		//this.welcome_text.setForeground(Color.black);	
		//this.welcome_text.setFont(new Font(this.welcome_text.getFont().getName(), Font.BOLD, 2));
		this.welcome_text.setIcon(new ImageIcon(""+welcomeTextIcon));
		int labelWidth = 800;
		this.welcome_text.setBounds((this.width-labelWidth)/2, 100, labelWidth, 100);
		this.welcome_text.setVisible(true);
		this.welcomePan.add(welcome_text);
	}

	private void setButtons(){
		int buttonWidth = 250;

		//Button um ein neues Spiel zu starten
		this.newGame = new JButton();
		this.newGame.setIcon(new ImageIcon("Resources/newGame.png"));
		this.newGame.setBounds((this.width-buttonWidth)/2, 200, buttonWidth, 100);
		//this.newGame.setText("Neues Spiel");
		this.newGame.setOpaque(false);
		this.newGame.setContentAreaFilled(false);

		this.newGame.setBorderPainted(false);
		//this.newGame.setForeground(Color.BLUE);
		this.newGame.setVisible(true);

		//Button um ein Spiel zu laden
		this.loadGame = new JButton();
		//this.loadGame.setText("Spiel Laden");
		this.loadGame.setIcon(new ImageIcon("Resources/loadGame.png"));
		this.loadGame.setBounds((this.width-buttonWidth)/2, 275, buttonWidth, 100);
		//this.loadGame.setForeground(Color.BLUE);
		this.loadGame.setOpaque(false);
		this.loadGame.setContentAreaFilled(false);

		this.loadGame.setBorderPainted(false);
		this.loadGame.setVisible(true);

		this.welcomePan.add(this.newGame);
		this.welcomePan.add(this.loadGame);

	}

	private void doStartAnim(){
		//increase the opacity and repaint
		int sizeX = 80;
		while(sizeX <= 800) {
			sizeX += 4;
			//this.welcome_text.setSize();
			ImageIcon img = new ImageIcon(""+welcomeTextIcon);
            Image newimg = img.getImage().getScaledInstance(sizeX, 100, java.awt.Image.SCALE_SMOOTH);  
            ImageIcon icon = new ImageIcon(newimg);
            this.welcome_text.setIcon(icon);
            repaint();

//			//sleep for a bit
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//
//				e.printStackTrace();
//			}
		}
		while(sizeX >= 700) {
			sizeX -= 3;
			ImageIcon img = new ImageIcon(""+welcomeTextIcon);
            Image newimg = img.getImage().getScaledInstance(sizeX, 100, java.awt.Image.SCALE_SMOOTH);  
            ImageIcon icon = new ImageIcon(newimg);
            this.welcome_text.setIcon(icon); 
            
			repaint();
		}


	}
	
	

	/**
	 * Funktionen bereitstellen, mit denen man später aus
	 * dem Controller die nötigen Listener hinzufügen kann
	 */
	public void setNewGameSelectionListener(ActionListener l){
		this.newGame.addActionListener(l);
	}

	/**
	 * Funktionen bereitstellen, mit denen man später aus
	 * dem Controller die nötigen Listener hinzufügen kann
	 */
	public void setLoadSelectionListener(ActionListener l){
		this.loadGame.addActionListener(l);
	}



}