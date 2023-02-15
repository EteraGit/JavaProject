package design;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class StylizedButton extends JButton {
    
    private Color backgroundColor;
    private Color hoverColor = new Color(195,177,196);
    private Color textColor = new Color(50, 50, 50);
    private Font font;
    
    public StylizedButton(String text, int fontSize, Color backgroundColor, int borderSize) {
        super(text);
        font = new Font("Arial", Font.BOLD, fontSize);
        //setPreferredSize(new Dimension(120, 40));
        this.backgroundColor = backgroundColor;
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        Border border = BorderFactory.createLineBorder(new Color(74,20,78), borderSize);
        setBorder(border);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isRollover()) {
            g.setColor(hoverColor);
        } else {
            g.setColor(backgroundColor);
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(textColor);
        g.setFont(font);
        g.drawString(getText(), getWidth() / 2 - g.getFontMetrics().stringWidth(getText()) / 2, getHeight() / 2 + g.getFontMetrics().getAscent() / 3);
    }
}
