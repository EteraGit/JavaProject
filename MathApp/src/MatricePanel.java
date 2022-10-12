import javax.swing.*;
import java.awt.*;


public class MatricePanel extends JPanel 
{
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.red);
        g.fillRect(10,100,970,450);
    }
}
