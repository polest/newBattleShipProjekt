package Tools;

import java.awt.Color;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class StyleConstant {


	private static final int FONTSIZE = 16;
	private static final String FONTFAMILY = "Helvetica";

	public static SimpleAttributeSet BLUE = new SimpleAttributeSet();
	public static SimpleAttributeSet MAGENTA = new SimpleAttributeSet();
	public static SimpleAttributeSet GREEN = new SimpleAttributeSet();

	static {
		StyleConstants.setForeground(BLUE, Color.blue);
		StyleConstants.setFontFamily(BLUE, FONTFAMILY);
		StyleConstants.setFontSize(BLUE, FONTSIZE);

		StyleConstants.setForeground(MAGENTA, Color.magenta);
		StyleConstants.setFontFamily(MAGENTA, FONTFAMILY);
		StyleConstants.setFontSize(MAGENTA, FONTSIZE);
		
		StyleConstants.setForeground(GREEN, Color.green);
		StyleConstants.setFontFamily(GREEN, FONTFAMILY);
		StyleConstants.setFontSize(GREEN, FONTSIZE);
	}
}
