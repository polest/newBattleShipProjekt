package Tools;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
/**
 * Write a description of class ImagePanel here.
 * 
 * @author ML
 * @version 25.12.14
 */
public class ImagePanel extends JPanel{

	/**
	 * 
	 */
	private Image img;

	public ImagePanel(String img) {
		this(new ImageIcon(img).getImage());
	}

	public ImagePanel() {

	}

	public ImagePanel(Image img) {
		this.img = img;
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);

	}

	public void setImageSize(int x, int y){

		this.img.getScaledInstance( x ,y, Image.SCALE_DEFAULT);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

}