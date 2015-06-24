package Ships;

import java.awt.Image;

import javax.swing.ImageIcon;

import Tools.RotateLabel;

public class Ship_View {
	private int cellSize;
	private int shipLength;
	private RotateLabel shipImage;
	private ImageIcon shipIcon;

	public Ship_View(String icon, int cellSize, int shipLength){
		this.cellSize = cellSize;
		this.shipLength = shipLength;
		this.shipIcon = new ImageIcon(icon);
		this.shipIcon.setImage(shipIcon.getImage().getScaledInstance(cellSize*shipLength, cellSize,Image.SCALE_DEFAULT)); 
		this.shipImage = new RotateLabel(shipIcon);
		this.shipImage.setBounds(0,0,cellSize*shipLength,cellSize);
		this.shipImage.setVisible(false);
	}

	public RotateLabel getShipIcon(){
		return this.shipImage;
	}

	public void setIconVisibility(boolean state){
		this.shipImage.setVisible(state);
		System.out.println("" + state);
	}
	
	public void setIcon(int mouseX, int mouseY, char orientation){
		int cellsizeLength = cellSize*5;
		//		int offsetLeft = this.startX;
		//		int offsetTop = this.startY;
		if(orientation == 'h'){
			shipIcon.setImage(shipIcon.getImage().getScaledInstance(cellSize*shipLength, cellSize,Image.SCALE_DEFAULT)); 
			shipImage.setIcon(shipIcon);
			shipImage.setBounds(mouseX, mouseY,cellsizeLength,cellSize);
			shipImage.setHorizontal();
			System.out.println("mouseX: " + mouseX);
			System.out.println("mouseY: " + mouseY);
		}
		else{
			shipIcon.setImage(shipIcon.getImage().getScaledInstance(cellSize*5,cellSize,Image.SCALE_DEFAULT)); 
			shipImage.setIcon(shipIcon);
			shipImage.setBounds(mouseX, mouseY, cellsizeLength,cellSize);
			shipImage.setVertical();
			System.out.println("mouseX: " + mouseX);
			System.out.println("mouseY: " + mouseY);
		}
	}





}
