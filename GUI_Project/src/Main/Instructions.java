package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
	
	public class Instructions {
		
		private JFrame frame;
		private JPanel panel;
		private Label instructions;
		private JLabel bild;
		private Icon background;
		
		
		private int width = 400;
		private int heigth = 600;
		
		
		public Instructions(){
			initFrame();
			//initPanel();
			addText();
		}
		
		private void initFrame(){
			this.frame = new JFrame("Instructions");
			this.frame.setLocation(0, 0);
			String currentDirectory = System.getProperty("user.dir");
			try{
				this.frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File(currentDirectory + "/Resources/Panel.png")))));
			}catch(Exception e){
				System.out.println("Datei nicht gefunden.");
			}
			this.frame.pack();
			this.frame.setResizable(false);
			this.frame.setVisible(true);
		}

		
		private void addText(){
			this.instructions = new Label("Instructions");
			Font schrift = new Font("Helvetica",Font.BOLD,30);
			
			this.instructions.setBounds(110, 20, 180, 30);
			this.instructions.setBackground(Color.WHITE);
			this.instructions.setForeground(Color.BLUE);
			this.instructions.setFont(schrift);
			this.instructions.setVisible(true);
			
			this.frame.add(this.instructions);
		}
		
		
	}
