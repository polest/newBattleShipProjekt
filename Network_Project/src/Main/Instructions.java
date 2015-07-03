package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
	
	public class Instructions {
		
		private JFrame frame;
		private Label instructions;
		private JScrollPane scrollBar;
		
		
		public Instructions(){
			initFrame();
			//initPanel();
			addText();
		}
		
		private void initFrame(){
			Dimension screenWidth = Toolkit.getDefaultToolkit().getScreenSize();
			double width = screenWidth.getWidth() - 400;
			
			
			this.frame = new JFrame("Instructions");
			this.frame.setLocation((int) width, 0);
			String currentDirectory = System.getProperty("user.dir");
			try{
				this.frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File(currentDirectory + "/Resources/Panel.png")))));
			}catch(Exception e){
				System.out.println("Datei nicht gefunden.");
			}
			this.frame.pack();
			this.frame.setResizable(true);
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
