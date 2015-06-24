package Tools;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class RotateLabel extends JLabel {

	private static final long serialVersionUID = 1L;

	private int angle = 0;


	public RotateLabel( ImageIcon icon, int x, int y ) {
		super( icon );
		int width = getPreferredSize().width;
		int height = getPreferredSize().height;
		setBounds( x, y, width, height );
	}

	public RotateLabel(ImageIcon icon){
		super( icon );
	}
	
	
	
	@Override
	public void paintComponent( Graphics g ) {

		Graphics2D gx = (Graphics2D) g;
        gx.rotate(this.angle);
	    super.paintComponent(g);
	}

	public void setRotation( int angle ) { 
		this.angle = angle; 
	}
	
	public void setHorizontal(){
		this.angle = 0;
		int oldWidth = this.WIDTH;
		int oldHeight = this.HEIGHT;

		//this.setSize(oldHeight, oldWidth);
		}


	public void setVertical(){
		this.angle = -180;
		int oldWidth = this.WIDTH;
		int oldHeight = this.HEIGHT;

		//this.setSize(oldHeight, oldWidth);
	}
}
