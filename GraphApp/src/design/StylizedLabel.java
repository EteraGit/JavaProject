package design;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class StylizedLabel extends JLabel {
    
    private Color textColor = new Color(50, 50, 50);
    private int fontSize;
    private Font font;
    
    public StylizedLabel(String text, int fontSize_) {
        super(text);
        setForeground(textColor);
        this.fontSize = fontSize_;
        this.font = new Font("Arial", Font.BOLD, this.fontSize);
        setFont(font);
    }
    
    public void setTextColor(Color color) {
        textColor = color;
        setForeground(textColor);
    }
    
    public void setFontStyle(int style) {
        font = new Font(font.getFontName(), style, font.getSize());
        setFont(font);
    }
}