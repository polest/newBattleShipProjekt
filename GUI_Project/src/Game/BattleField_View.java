package Game;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Tools.RotateLabel;

public class BattleField_View {

	private JButton[][] field;
	private int fieldSize;
	private JLabel iconLabel;
	private JPanel panel;
	private int width;
	private int height;
	private int startX;
	private int startY;
	private int cellSize;

	public BattleField_View(JPanel panel, int fieldSize, int width, int startX, int startY){
		this.panel = panel;
		this.width = width;
		this.height = width;
		this.startX = startX;
		this.startY = startY;
		this.fieldSize = fieldSize;
		initField();
	}
	
	public void clear(){
		for(int i = 0; i < this.fieldSize; i++){
			for(int j = 0; j < this.fieldSize; j++){
				this.field[i][j].setBackground(null);
				this.field[i][j].setOpaque(false);
				this.field[i][j].setEnabled(true);
			}
		}
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
		this.field = new JButton[this.fieldSize][this.fieldSize];

		cellSize = width/this.fieldSize;
		//		destroyerView = new Destroyer_View(cellSize);
		//		this.panel.add(destroyerView.getShipIcon());

		//vertical 
		for(int i = 0; i < this.fieldSize; i++){
			//horizontal
			for(int j = 0; j < this.fieldSize; j++){
				this.field[i][j] = new JButton();
				this.field[i][j].setBounds(startX+(cellSize*i), startY+(cellSize*j), cellSize, cellSize);
				this.field[i][j].setVisible(true);
				this.field[i][j].setOpaque(false);
				this.field[i][j].setContentAreaFilled(false);
				this.field[i][j].setBorderPainted(true);
				this.field[i][j].setBorder(BorderFactory.createLineBorder(Color.gray));
				this.field[i][j].setActionCommand(" " + (i+1) + "," + (j+1));
				this.panel.add(this.field[i][j]);
			}
		}


		ImageIcon icon = new ImageIcon("Resources/Meer.jpg");
		int size = this.fieldSize*this.cellSize;
		icon.setImage(icon.getImage().getScaledInstance( size ,size, Image.SCALE_DEFAULT)); 
		iconLabel = new JLabel(icon);
		iconLabel.setBounds(startX,startY,size,size);
		this.panel.add(iconLabel);
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
