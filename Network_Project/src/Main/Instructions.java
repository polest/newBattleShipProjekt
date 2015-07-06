package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import Tools.ImagePanel;

public class Instructions {

	private JFrame frame;
	private Label instructions;
	private ImagePanel panel;
	private JScrollPane scrollBar;
	private int h;
	private int b;


	public Instructions(){
		initFrame();
		initPanel();
		addText();
		initScrollBar();
	}

	/**
	 * initialisert ein Frame
	 */
	private void initFrame(){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth() - 400;
		double height = screenSize.getHeight() - 200;
		int h = (int)height;
		this.frame = new JFrame("Instructions");
		this.frame.setLocation((int) width, 0);
		this.frame.setSize(496,720);
		this.frame.setResizable(true);
		this.frame.setLayout(null);
		this.frame.setVisible(true);
	}

	/**
	 * initialisiert ein ImagePanel
	 * added es dem Frame
	 */
	private void initPanel(){
		this.panel = new ImagePanel("Resources/Panel.png");
		this.panel.setSize(496,991);
		this.panel.setLayout(null);
		this.panel.setVisible(true);
		this.panel.repaint();
		this.frame.add(this.panel);
	}
	
	/**
	 * initialisiert die Scrollbar
	 * added es dem Frame
	 */
	private void initScrollBar(){	
		this.scrollBar = new JScrollPane(this.panel);
		this.scrollBar.setBounds(0, 0, 496,700);
		
		
		this.frame.add(this.scrollBar, BorderLayout.EAST);
	}

	/**
	 * added dem Panel den Text
	 */
	private void addText(){

		this.instructions = new Label("Instructions");
		Font schrift = new Font("Helvetica",Font.BOLD,30);

		this.instructions.setBounds((496-180)/2, 20, 180, 30);
		this.instructions.setBackground(Color.WHITE);
		this.instructions.setForeground(Color.BLUE);
		this.instructions.setFont(schrift);
		this.instructions.setVisible(true);

		this.panel.add(this.instructions);
	}

}
