package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Tools.ImagePanel;

public class BattleField_View {

	private JButton[][] field;
	private int fieldSize;
	private JLabel iconLabel;
	private JPanel panel;	
	private JPanel battleFieldViewPanel;
	private JPanel test;
	private int width;
	private int height;
	private int startX;
	private int startY;
	private int cellSize;
	private ImageIcon empty;
	private JLabel background;

	public BattleField_View(JPanel panel, int fieldSize, int width, int startX, int startY){
		this.panel = panel;
		this.width = width;
		this.height = width;
		this.startX = startX;
		this.startY = startY;
		this.fieldSize = fieldSize;
		this.empty = new ImageIcon("Resources/empty.png");
		initField();
	}

	public BattleField_View() {

		for(int i = 0; i < this.fieldSize; i++){
			for(int j = 0; j < this.fieldSize; j++){
				this.field[i][j].setIcon(empty);
				this.field[i][j].setOpaque(false);
				this.field[i][j].setEnabled(true);
			}
		}

	}

	public JPanel getView(){
		return battleFieldViewPanel;
	}

	public JButton[][] getField(){
		return this.field;
	}
	
	public void clearBorder(){
		for(int i = 0; i < this.fieldSize; i++){
			for(int j = 0; j < this.fieldSize; j++){
				this.field[i][j].setBorder(BorderFactory.createLineBorder(Color.gray));
			}
		}
	}

	public void setSize(int x, int y, int width){
		this.battleFieldViewPanel.setBounds(x, y, width, width);
		this.cellSize = width/this.fieldSize;
		int size = this.fieldSize*this.cellSize;
		//vertical 
		for(int i = 0; i < this.fieldSize; i++){
			//horizontal
			for(int j = 0; j < this.fieldSize; j++){
				this.field[i][j].setBounds(cellSize*i, cellSize*j, cellSize, cellSize);
				Icon icon = this.field[i][j].getIcon();
				Image img = ((ImageIcon) icon).getImage() ;  
				 Image newimg = img.getScaledInstance( cellSize, cellSize,  java.awt.Image.SCALE_SMOOTH ) ;  
				 icon = new ImageIcon( newimg );
				 this.field[i][j].setIcon(icon);
				
			}
		}
		
		this.background.setSize(size, size);
	}


	public JButton[][] getBattleField(){
		return this.field;
	}

	public int getCellSize(){
		return this.cellSize;
	}

	public int getStartX(){
		return this.startX;
	}

	public int getStartY(){
		return this.startY;
	}

	private void initField(){
		cellSize = this.width/this.fieldSize;
		int size = this.fieldSize*this.cellSize;
		battleFieldViewPanel = new JPanel();
		battleFieldViewPanel.setLayout(null);
		battleFieldViewPanel.setOpaque(false);
		this.field = new JButton[this.fieldSize][this.fieldSize];

		empty.setImage(empty.getImage().getScaledInstance( cellSize ,cellSize, Image.SCALE_DEFAULT)); 

		//vertical 
		for(int i = 0; i < this.fieldSize; i++){
			//horizontal
			for(int j = 0; j < this.fieldSize; j++){
				this.field[i][j] = new JButton();
				this.field[i][j].setBounds(cellSize*i, cellSize*j, cellSize, cellSize);
				this.field[i][j].setVisible(true);
				this.field[i][j].setOpaque(false);
				this.field[i][j].setContentAreaFilled(false);
				this.field[i][j].setBorderPainted(true);
				this.field[i][j].setIcon(empty);
				this.field[i][j].setBorder(BorderFactory.createLineBorder(Color.gray));
				this.field[i][j].setActionCommand(" " + (i+1) + "," + (j+1));
				this.battleFieldViewPanel.add(this.field[i][j]);
			}
		}

		ImageIcon meer = new ImageIcon("Resources/Meer2.jpg");
		meer.setImage(meer.getImage().getScaledInstance(size,size, Image.SCALE_DEFAULT));
		background = new JLabel(meer);
		background.setSize(size, size);
		battleFieldViewPanel.setVisible(true);
		this.battleFieldViewPanel.add(background);

		//		ImageIcon icon = new ImageIcon("Resources/Meer.jpg");
		//		//int size = this.fieldSize*this.cellSize;
		//		icon.setImage(icon.getImage().getScaledInstance( size ,size, Image.SCALE_DEFAULT)); 
		//		iconLabel = new JLabel(icon);
		//		iconLabel.setBounds(startX,startY,size,size);
		//		this.battleFieldViewPanel.add(iconLabel);
		//		this.battleFieldViewPanel.setBorder(BorderFactory.createLineBorder(Color.pink));
		this.battleFieldViewPanel.setBounds(30,30,size,size);
		this.panel.add(this.battleFieldViewPanel);
	}

	
	public void setBattleFieldMouseListener(MouseListener l){
		//vertical 
		for(int i = 0; i < this.fieldSize; i++){
			//horizontal
			for(int j = 0; j < this.fieldSize; j++){
				this.field[i][j].addMouseListener(l);
			}
		}
	}

	public void setBattleFieldMouseMotionListener(MouseMotionListener l){
		//		//vertical 
		for(int i = 0; i < this.fieldSize; i++){
			//horizontal
			for(int j = 0; j < this.fieldSize; j++){
				this.field[i][j].addMouseMotionListener(l);
			}
		}
		//		this.mouseLabel.addMouseMotionListener(l);

	}
}
