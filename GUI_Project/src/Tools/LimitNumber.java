package Tools;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.text.AttributeSet;
import java.math.BigDecimal;
 
import javax.swing.*;
import java.awt.*;

public class LimitNumber extends PlainDocument {

	private int limit;

  public LimitNumber(int limit) {
   super();
   this.limit = limit;
   }

  public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException {
    if (str == null) return;

    if (((getLength() + str.length()) <= limit) && (isNumber(str))) {
      super.insertString(offset, str, attr);
    }
  }
   protected boolean isNumber (final String text) {
      try {
         if (text.length() > 0){
            // Versuchen eine Zahl zu erzeugen:
            new BigDecimal(text);  
         }
         return true;
      }catch (final NumberFormatException e){
         // Hat nicht geklappt, also keine Zahl
      }
      return false;
   }
}