import javax.swing.*;
import java.awt.*;


public class NumerickaPanel extends JPanel 
{
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.yellow);
        g.fillRect(10,100,970,450);
    }
}
